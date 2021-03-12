package com.ttm.pet.controller.app;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ttm.pet.enums.ConstantsEnum;
import com.ttm.pet.enums.ReturnStatusEnum;
import com.ttm.pet.model.dto.*;
import com.ttm.pet.model.pojo.DataResult;
import com.ttm.pet.model.pojo.ListDataResult;
import com.ttm.pet.model.query.app.ChatDeleteQuery;
import com.ttm.pet.model.query.app.MessageDeleteQuery;
import com.ttm.pet.model.vo.app.ChatDetailVo;
import com.ttm.pet.model.vo.app.ChatListVo;
import com.ttm.pet.model.vo.app.MessageListVo;
import com.ttm.pet.service.ChatService;
import com.ttm.pet.service.MessageRecordService;
import com.ttm.pet.service.MessageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@Api(value ="/app/message",tags = "app-message",description = "消息相关接口")
@RequestMapping("/app/message")
public class MessageController {
    private final static Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private ChatService chatService;
    @Autowired
    private MessageRecordService messageRecordService;
    @Autowired
    private MessageService messageService;

    /**
     * 查询未读消息数量
     * @return
     */
    @ApiOperation(httpMethod = "GET", value = "查询总未读消息数量和私信消息数量",notes = "返回总未读消息数量，和左上角未读私信消息数量",nickname = "1")
    @RequestMapping(value = "/messageCount" ,method = RequestMethod.GET)
    private DataResult getMessageCount(@ApiParam(value = "用户id", required = true) @RequestParam("customerId") String customerId){
        DataResult result = new DataResult();
        try {
            MessageRecord messageRecord = messageRecordService.selectOne(new EntityWrapper<MessageRecord>().eq("customer_id",customerId));
            Date lastBrowseTime = messageRecord.getLastBrowseTime();
            EntityWrapper<Message> messageEntityWrapper = new EntityWrapper<>();
            messageEntityWrapper.isNull("to_customer_id").or().eq("to_customer_id",customerId);
            messageEntityWrapper.andNew().gt("create_time",lastBrowseTime);
            int messageCount = messageService.selectCount(messageEntityWrapper);
            int chatCount = chatService.selectCount(new EntityWrapper<Chat>().eq("customer_id",customerId).eq("status",ConstantsEnum.NO_READ.getCode()));
            Map<String,Integer> map = new HashedMap(2);
            map.put("sumCount",messageCount+chatCount);
            map.put("chatCount",chatCount);
            result.setData(map);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("查询总未读消息数量和私信消息数量失败", e.getMessage());
            result.setCode(ReturnStatusEnum.RFC_ERROR.getValue());
            result.setMsg("查询总未读消息数量失败");
            return result;
        }
    }

    /**
     * 查询消息最外层列表
     * @return
     */
    @ApiOperation(httpMethod = "GET", value = "查询消息最外层列表",notes = "返回结果中，lastReadTime是最后阅读时间，列表中创建时间大于最后阅读时间的显示红点",nickname = "2")
    @RequestMapping(value = "/messageList" ,method = RequestMethod.GET)
    private ListDataResult getMessageList(@ApiParam(value = "页数", required = true) @RequestParam("page") Integer page,
                                          @ApiParam(value = "每页个数", required = true) @RequestParam("size") Integer size,
                                          @ApiParam(value = "用户id", required = true) @RequestParam("customerId") String customerId){
        ListDataResult result = new ListDataResult();
        try {
            MessageRecord messageRecord = messageRecordService.selectOne(new EntityWrapper<MessageRecord>().eq("customer_id",customerId));
            Date lastBrowseTime = messageRecord.getLastBrowseTime();
            String deleteMessageId = messageRecord.getDeleteMessageId();
            Page<MessageListVo> messagePage = new Page<>(page,size);
            List<String> deleteMessageIdList = new ArrayList<>();
            if(deleteMessageId != null && !"".equals(deleteMessageId)){
                deleteMessageIdList = Arrays.asList(deleteMessageId.split(",")).stream().map(s -> String.format(s.trim())).collect(Collectors.toList());
            }
            messagePage = messageService.findMessageListVos(messagePage,customerId,deleteMessageIdList);

            messageRecord.setLastBrowseTime(new Date());
            messageRecordService.updateById(messageRecord);

            Map<String,Object> map = new HashedMap(2);
            map.put("lastReadTime",lastBrowseTime);
            map.put("list",messagePage.getRecords());
            result.setCurrent(messagePage.getCurrent());
            result.setTotal(messagePage.getTotal());
            result.setData(map);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("查询消息最外层列表失败", e.getMessage());
            result.setCode(ReturnStatusEnum.RFC_ERROR.getValue());
            result.setMsg("查询消息失败");
            return result;
        }
    }

    /**
     * 删除某条系统消息
     * @param messageDeleteQuery
     * @return
     */
    @ApiOperation(httpMethod = "POST", value = "删除某条系统消息",notes = "传消息记录id",nickname = "3")
    @RequestMapping(value = "/deleteMessage" ,method = RequestMethod.POST)
    private DataResult deleteMessage(@RequestBody MessageDeleteQuery messageDeleteQuery){
        DataResult result = new DataResult();
        try {
            Message message = messageService.selectById(messageDeleteQuery.getId());
            if(message.getToCustomerId()!= null && message.getToCustomerId().equals(messageDeleteQuery.getCustomerId())){
                messageService.deleteById(messageDeleteQuery.getId());
            }else {
                MessageRecord messageRecord = messageRecordService.selectOne(new EntityWrapper<MessageRecord>().eq("customer_id",messageDeleteQuery.getCustomerId()));
                String deleteMessageId = messageRecord.getDeleteMessageId();
                if (deleteMessageId == null || "".equals(deleteMessageId)){
                    messageRecord.setDeleteMessageId(messageDeleteQuery.getId().toString());
                }else {
                    messageRecord.setDeleteMessageId(deleteMessageId + "," + messageDeleteQuery.getId().toString());
                }
                messageRecordService.updateById(messageRecord);
            }
            result.setMsg("删除成功");
            return result;
        }catch (Exception e){
            logger.error("删除某条系统消息失败:{}",e.getMessage());
            result.setCode(ReturnStatusEnum.EXECUTESQL_ERROR.getValue());
            result.setMsg("删除失败");
            return result;
        }
    }

    /**
     * 查询中奖消息列表
     * @return
     */
    @ApiOperation(httpMethod = "GET", value = "查询中奖消息列表",notes = "上面3个中间一个是中奖消息",nickname = "4")
    @RequestMapping(value = "/drawMessages" ,method = RequestMethod.GET)
    private ListDataResult getDrawMessages(@ApiParam(value = "页数", required = true) @RequestParam("page") Integer page,
                                          @ApiParam(value = "每页个数", required = true) @RequestParam("size") Integer size,
                                          @ApiParam(value = "用户id", required = true) @RequestParam("customerId") String customerId){
        ListDataResult result = new ListDataResult();
        try {
            Page<Message> messagePage = new Page<>(page,size);
            EntityWrapper<Message> messageEntityWrapper = new EntityWrapper<>();
            messageEntityWrapper.setSqlSelect("id","title","portrait","type","left(content,25) content","unix_timestamp(create_time) createTime");
            messageEntityWrapper.eq("type",ConstantsEnum.MESSAGE_DRAW.getCode()).eq("to_customer_id",customerId).orderBy("create_time",false);
            Page<Map<String,Object>> messages = messageService.selectMapsPage(messagePage,messageEntityWrapper);
            result.setData(messagePage.getRecords());
            result.setTotal(messagePage.getTotal());
            result.setCurrent(messagePage.getCurrent());
            return result;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("查询中奖消息列表失败", e.getMessage());
            result.setCode(ReturnStatusEnum.RFC_ERROR.getValue());
            result.setMsg("查询中奖失败");
            return result;
        }
    }

    /**
     * 查询消息详情
     * @return
     */
    @ApiOperation(httpMethod = "GET", value = "查询消息详情",notes = "type为2时，是中奖消息(中奖消息又分为中奖通知和发货通知) 中奖通知会多了一张图片和地址，drawImg和drawUrl不为空时显示就行",nickname = "5")
    @RequestMapping(value = "/messageDetail" ,method = RequestMethod.GET)
    private DataResult getMessageDetail(@ApiParam(value = "消息id", required = true) @RequestParam("id") Integer id){
        DataResult result = new DataResult();
        try {
            EntityWrapper<Message> messageEntityWrapper = new EntityWrapper<>();
            messageEntityWrapper.setSqlSelect("id","title","portrait","type","draw_img drawImg","draw_url drawUrl","content","unix_timestamp(create_time) createTime");
            messageEntityWrapper.eq("id",id);
            Map<String,Object> message = messageService.selectMap(messageEntityWrapper);
            result.setData(message);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("查询消息详情失败", e.getMessage());
            result.setCode(ReturnStatusEnum.RFC_ERROR.getValue());
            result.setMsg("查询失败");
            return result;
        }
    }

    /**
     * 查询私信列表
     * @return
     */
    @ApiOperation(httpMethod = "GET", value = "查询私信列表",nickname = "6")
    @RequestMapping(value = "/chatList" ,method = RequestMethod.GET)
    private ListDataResult getChatList(@ApiParam(value = "页数", required = true) @RequestParam("page") Integer page,
                                        @ApiParam(value = "每页个数", required = true) @RequestParam("size") Integer size,
                                        @ApiParam(value = "用户id", required = true) @RequestParam("customerId") String customerId){
        ListDataResult result = new ListDataResult();
        try {
            Page<ChatListVo> chatListVoPage = new Page<>(page,size);
            chatListVoPage = chatService.findChatListVos(chatListVoPage,customerId);
            result.setCurrent(chatListVoPage.getCurrent());
            result.setTotal(chatListVoPage.getTotal());
            result.setData(chatListVoPage.getRecords());
            return result;
        }catch (Exception e){
            logger.error("查询私信列表失败", e.getMessage());
            result.setCode(ReturnStatusEnum.RFC_ERROR.getValue());
            result.setMsg("查询失败");
            return result;
        }
    }

    /**
     * 查询私信对话框详情
     * @return
     */
    @ApiOperation(httpMethod = "GET", value = "查询私信对话框详情",nickname = "7")
    @RequestMapping(value = "/chatDetail" ,method = RequestMethod.GET)
    private ListDataResult getChatDetail(@ApiParam(value = "页数", required = true) @RequestParam("page") Integer page,
                                         @ApiParam(value = "每页个数", required = true) @RequestParam("size") Integer size,
                                         @ApiParam(value = "用户id", required = true) @RequestParam("customerId") String customerId,
                                         @ApiParam(value = "好友用户id", required = true) @RequestParam("friendCustomerId") String friendCustomerId){
        ListDataResult result = new ListDataResult();
        try {
            Page<ChatDetailVo> chatDetailVoPage = new Page<>(page,size);
            chatDetailVoPage = chatService.findChatDetailVo(chatDetailVoPage,customerId,friendCustomerId);

            Chat chat = new Chat();
            chat.setStatus(ConstantsEnum.READ.getCode());
            chatService.update(chat,new EntityWrapper<Chat>().eq("customer_id",customerId).eq("friend_customer_id",friendCustomerId).ne("status",ConstantsEnum.MESSAGE_DELETED.getCode()));

            result.setCurrent(chatDetailVoPage.getCurrent());
            result.setTotal(chatDetailVoPage.getTotal());
            result.setData(chatDetailVoPage.getRecords());
            return result;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("查询私信对话框详情失败", e.getMessage());
            result.setCode(ReturnStatusEnum.RFC_ERROR.getValue());
            result.setMsg("查询失败");
            return result;
        }
    }

    /**
     * 删除某条聊天记录
     * @param id
     * @return
     */
    @ApiOperation(httpMethod = "DELETE", value = "删除某条聊天记录",notes = "传聊天记录id",nickname = "8")
    @RequestMapping(value = "/chat/{id}" ,method = RequestMethod.DELETE)
    private DataResult deleteChat(@PathVariable(value="id") Long id){
        DataResult result = new DataResult();
        try {
            Chat chat  = new Chat();
            chat.setId(id);
            chat.setStatus(ConstantsEnum.MESSAGE_DELETED.getCode());
            chatService.updateById(chat);
            return result;
        }catch (Exception e){
            logger.error("删除某条聊天记录失败:{}",e.getMessage());
            result.setCode(ReturnStatusEnum.EXECUTESQL_ERROR.getValue());
            result.setMsg("删除失败");
            return result;
        }
    }

    /**
     * 删除整个聊天对话
     * @return
     */
    @ApiOperation(httpMethod = "POST", value = "删除整个聊天对话框",nickname = "9")
    @RequestMapping(value = "/chatDelete" ,method = RequestMethod.POST)
    private DataResult getChatDelete(@RequestBody ChatDeleteQuery chatDeleteQuery){
        DataResult result = new DataResult();
        try {
            Chat chat  = new Chat();
            chat.setStatus(ConstantsEnum.MESSAGE_DELETED.getCode());
            chatService.update(chat,new EntityWrapper<Chat>().eq("customer_id",chatDeleteQuery.getCustomerId()).eq("friend_customer_id",chatDeleteQuery.getFriendCustomerId()));
            result.setMsg("删除成功");
            return result;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("删除整个聊天对话失败", e.getMessage());
            result.setCode(ReturnStatusEnum.RFC_ERROR.getValue());
            result.setMsg("删除失败");
            return result;
        }
    }
}
