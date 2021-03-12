package com.ttm.pet.controller.app;


import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ttm.pet.controller.admin.v1.CustomerController;
import com.ttm.pet.enums.ConstantsEnum;
import com.ttm.pet.enums.ReturnStatusEnum;
import com.ttm.pet.model.dto.*;
import com.ttm.pet.model.pojo.DataResult;
import com.ttm.pet.model.query.app.RewardQuery;
import com.ttm.pet.model.query.app.SendChatQuery;
import com.ttm.pet.service.*;
import com.ttm.pet.util.BadWordUtil2;
import com.ttm.pet.util.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * <p>
 * 用户操作
 * </p>
 *
 * @author jmy
 * @since 2020-04-08
 */
@RestController
@Api(value ="/app/customerOperation",tags = "app-customerOperation",description = "用户操作")
@RequestMapping("/app/customerOperation")
public class CustomerOperationController {
    private final static Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    private CustomerCommentService customerCommentService;

    @Autowired
    private CustomerReportService customerReportService;

    @Autowired
    private CustomerPointService customerPointService;

    @Autowired
    private ChatService chatService;

    @Autowired
    private WorksService worksService;

    @Autowired
    private CustomerRewardService customerRewardService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private MatchPointService matchPointService;

    @Autowired
    private BaseApplyService baseApplyService;

    /**
     * 用户给作品评论
     * @param customerComment
     * @return
     */
    @ApiOperation(httpMethod = "POST", value = "评论作品",notes = "只需要传customerId，worksId，remark(评论内容)即可",nickname = "1")
    @RequestMapping(value = "/comment" ,method = RequestMethod.POST)
    private DataResult setComment(@RequestBody CustomerComment customerComment){
        DataResult result = new DataResult();
        try {
            String content = customerComment.getRemark();
            Set<String> set = BadWordUtil2.getBadWord(content, 2);
            if(set.size() > 0){
                return new DataResult(ReturnStatusEnum.ILLEGAL_PARAM_ERROR.getValue(),"您发布的内容包含敏感词，请检查后发布!",null);
            }

            Date createDate = new Date();
            customerComment.setCreateTime(createDate);
            customerCommentService.insert(customerComment);
            Map<String,Object> map = new HashedMap(2);
            map.put("id",customerComment.getId());
            map.put("createTime",DateUtil.date2long(customerComment.getCreateTime()));
            result.setData(map);
            return result;
        } catch (Exception e) {
            logger.error("评论失败:{}", e);
            result.setCode(ReturnStatusEnum.SYS_ERROR.getValue());
            result.setMsg("评论失败");
            return result;
        }
    }

    /**
     * 删除评论
     * @return
     */
    @ApiOperation(httpMethod = "DELETE", value = "删除评论",notes = "评论id  只有自己的评论才可以看到删除选项",nickname = "2")
    @RequestMapping(value = "/comment/{id}" ,method = RequestMethod.DELETE)
    private DataResult deleteComment(@PathVariable(value = "id") Long id){
        DataResult result = new DataResult();
        try {
            customerCommentService.deleteById(id);
            result.setMsg("删除成功！");
        }catch (Exception e){
            logger.error("删除评论id:" + id+",失败："+e.getMessage());
            result.setCode(ReturnStatusEnum.SYS_ERROR.getValue());
            result.setMsg("删除评论失败");
            return result;
        }
        return result;
    }
    /**
     * 用户举报作品或评论
     * @param customerReport
     * @return
     */
    @ApiOperation(httpMethod = "POST", value = "对作品或评论举报",notes = "只需要传customerId，type(0-评论 1-作品)，typeId(作品id 或者 评论id)，remark(举报内容)",nickname = "3")
    @RequestMapping(value = "/report" ,method = RequestMethod.POST)
    private DataResult setReport(@RequestBody CustomerReport customerReport){
        DataResult result = new DataResult();
        try {
            String customerId = customerReport.getCustomerId();
            Integer type = customerReport.getType();
            Long typeId = customerReport.getTypeId();
            int count = customerReportService.selectCount(new EntityWrapper<CustomerReport>().eq("type",type)
                    .eq("type_id",typeId)
                    .eq("customer_id",customerId));
            if(count != 0){
                return new DataResult(ReturnStatusEnum.SYS_ERROR.getValue(),"重复举报！",null);
            }
            customerReportService.insert(customerReport);
            result.setMsg("举报成功!");
        }catch (Exception e){
            logger.error("举报失败:" + e.getMessage());
            result.setCode(ReturnStatusEnum.SYS_ERROR.getValue());
            result.setMsg("评论失败");
            return result;
        }
        return result;
    }

    /**
     * 用户点赞作品或评论
     * @param customerPoint
     * @return
     */
    @ApiOperation(httpMethod = "POST", value = "对作品或评论点赞(再点一下点赞再点取消)",notes = "只需要传customerId，type(0-评论 1-作品)，typeId(作品id 或者 评论id)",nickname = "4")
    @RequestMapping(value = "/point" ,method = RequestMethod.POST)
    private DataResult setPoint(@RequestBody CustomerPoint customerPoint){
        DataResult result = new DataResult();
        try {
            EntityWrapper<CustomerPoint> customerPointEntityWrapper = new EntityWrapper<>();
            customerPointEntityWrapper.eq("customer_id",customerPoint.getCustomerId());
            customerPointEntityWrapper.eq("type",customerPoint.getType());
            customerPointEntityWrapper.eq("type_id",customerPoint.getTypeId());
            CustomerPoint customerPointOld = customerPointService.selectOne(customerPointEntityWrapper);

            //判断之前有没有这条记录，如果有deleted改为相反的，否则新增一条
            if(null != customerPointOld){
                if(customerPointOld.getDeleted() == ConstantsEnum.DELETED.getCode()){
                    customerPoint.setDeleted(ConstantsEnum.NOTDELETED.getCode());
                    customerPointService.update(customerPoint,customerPointEntityWrapper);
                    result.setMsg("点赞成功!");
                    if(customerPoint.getType() ==1){
                        Works works = worksService.selectById(customerPoint.getTypeId());
                        works.setPointNum(works.getPointNum() + 1);
                        worksService.updateById(works);
                    }
                }
                if(customerPointOld.getDeleted() == ConstantsEnum.NOTDELETED.getCode()){
                    customerPoint.setDeleted(ConstantsEnum.DELETED.getCode());
                    customerPointService.update(customerPoint,customerPointEntityWrapper);
                    result.setMsg("取消成功!");
                    if(customerPoint.getType() ==1){
                        Works works = worksService.selectById(customerPoint.getTypeId());
                        works.setPointNum(works.getPointNum() - 1);
                        worksService.updateById(works);
                    }
                }
            }else{
                customerPointService.insert(customerPoint);
                if(customerPoint.getType() ==1){
                    Works works = worksService.selectById(customerPoint.getTypeId());
                    works.setPointNum(works.getPointNum() + 1);
                    worksService.updateById(works);
                }
                result.setMsg("点赞成功!");
            }

            return result;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("点赞失败:" + e.getMessage());
            result.setCode(ReturnStatusEnum.SYS_ERROR.getValue());
            result.setMsg("点赞失败");
            return result;
        }
    }

    /**
     * 给比赛作品点赞
     * @param matchPoint
     * @return
     */
    @ApiOperation(httpMethod = "POST", value = "给比赛作品点赞(再点一下点赞再点取消)",notes = "只需要传customerId，workId(作品id 或者 评论id)",nickname = "4")
    @RequestMapping(value = "/matchPoint" ,method = RequestMethod.POST)
    private DataResult setMatchPoint(@RequestBody MatchPoint matchPoint){
        DataResult result = new DataResult();
        try {
            EntityWrapper<MatchPoint> matchPointEntityWrapper = new EntityWrapper<>();
            matchPointEntityWrapper.eq("customer_id",matchPoint.getCustomerId());
            matchPointEntityWrapper.eq("work_id",matchPoint.getWorkId());
            matchPointEntityWrapper.last("and to_days(create_time) = to_days(now())");
            MatchPoint MatchPointOld = matchPointService.selectOne(matchPointEntityWrapper);

            //判断之前有没有这条记录，如果有deleted改为相反的，否则新增一条
            if(null != MatchPointOld){
                if(MatchPointOld.getDeleted() == ConstantsEnum.DELETED.getCode()){
                    matchPoint.setDeleted(ConstantsEnum.NOTDELETED.getCode());
                    matchPointService.update(matchPoint,matchPointEntityWrapper);
                    result.setMsg("点赞成功!");
                    Works works = worksService.selectById(matchPoint.getWorkId());
                    works.setPointNum(works.getPointNum() + 1);
                    worksService.updateById(works);
                }
                if(MatchPointOld.getDeleted() == ConstantsEnum.NOTDELETED.getCode()){
                    matchPoint.setDeleted(ConstantsEnum.DELETED.getCode());
                    matchPointService.update(matchPoint,matchPointEntityWrapper);
                    result.setMsg("取消成功!");
                    Works works = worksService.selectById(matchPoint.getWorkId());
                    works.setPointNum(works.getPointNum() - 1);
                    worksService.updateById(works);
                }
            }else{
                matchPointService.insert(matchPoint);
                Works works = worksService.selectById(matchPoint.getWorkId());
                works.setPointNum(works.getPointNum() + 1);
                worksService.updateById(works);
                result.setMsg("点赞成功!");
            }
            return result;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("点赞失败:" + e.getMessage());
            result.setCode(ReturnStatusEnum.SYS_ERROR.getValue());
            result.setMsg("点赞失败");
            return result;
        }
    }

    /**
     * 给对方发送私信
     * @param sendChatQuery
     * @return
     */
    @ApiOperation(httpMethod = "POST", value = "给对方发送私信",nickname = "5")
    @RequestMapping(value = "/chat" ,method = RequestMethod.POST)
    private DataResult setChat(@RequestBody SendChatQuery sendChatQuery){
        DataResult result = new DataResult();
        try {
            List<Chat> chats = new ArrayList<>();
            Chat chat1 = new Chat();
            chat1.setFromCustomerId(sendChatQuery.getFromCustomerId());
            chat1.setToCustomerId(sendChatQuery.getToCustomerId());
            chat1.setCustomerId(sendChatQuery.getToCustomerId());
            chat1.setFriendCustomerId(sendChatQuery.getFromCustomerId());
            chat1.setContent(sendChatQuery.getContent());
            chats.add(chat1);

            Chat chat2 = new Chat();
            chat2.setFromCustomerId(sendChatQuery.getFromCustomerId());
            chat2.setToCustomerId(sendChatQuery.getToCustomerId());
            chat2.setCustomerId(sendChatQuery.getFromCustomerId());
            chat2.setFriendCustomerId(sendChatQuery.getToCustomerId());
            chat2.setContent(sendChatQuery.getContent());
            chat2.setStatus(ConstantsEnum.READ.getCode());
            chats.add(chat2);
            chatService.insertBatch(chats);
            result.setMsg("发送成功");
            return result;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("给对方发送私信失败:" + e.getMessage());
            result.setCode(ReturnStatusEnum.SYS_ERROR.getValue());
            result.setMsg("发送失败");
            return result;
        }
    }

    /**
     * 给作品打赏
     * @param rewardQuery
     * @return
     */
    @ApiOperation(httpMethod = "POST", value = "给作品打赏",notes = "只需要传customerId，rewardedCustomerId,workId,coin",nickname = "6")
    @RequestMapping(value = "/reward" ,method = RequestMethod.POST)
    private DataResult addCustomerReward(@RequestBody RewardQuery rewardQuery){
        DataResult result = new DataResult();
        try {
            Customer customer = customerService.selectOne(new EntityWrapper<Customer>().eq("uuid",rewardQuery.getCustomerId()));
            Integer ownerCoin = customer.getPetCoin();
            if (ownerCoin >= rewardQuery.getCoin()){
                CustomerReward customerReward = new CustomerReward();
                BeanUtils.copyProperties(rewardQuery,customerReward);
                customerReward.setToCustomerId(rewardQuery.getRewardedCustomerId());
                customerReward.setOriginalCoin(ownerCoin);

                customer.setPetCoin(ownerCoin - rewardQuery.getCoin());
                customerService.updateById(customer);

                Customer rewardedCustomer = customerService.selectOne(new EntityWrapper<Customer>().eq("uuid",rewardQuery.getRewardedCustomerId()));
                Integer rewardedCoin = rewardedCustomer.getPetCoin();
                customerReward.setToOriginalCoin(rewardedCoin);
                rewardedCustomer.setPetCoin(rewardedCoin + rewardQuery.getCoin());
                customerService.updateById(rewardedCustomer);

                customerRewardService.insert(customerReward);

                result.setMsg("打赏成功");
                return result;
            }else {
                result.setCode(ReturnStatusEnum.SYS_ERROR.getValue());
                result.setMsg("您的宠币余额不足，请充值~");
                return result;
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("给作品打赏失败:" + e.getMessage());
            result.setCode(ReturnStatusEnum.SYS_ERROR.getValue());
            result.setMsg("打赏失败");
            return result;
        }
    }

    /**
     * 基地申请
     * @param baseApply
     * @return
     */
    @ApiOperation(httpMethod = "POST", value = "基地申请",notes = "id，deleted不要传",nickname = "7")
    @RequestMapping(value = "/newBaseApply" ,method = RequestMethod.POST)
    private DataResult addBaseApply(@RequestBody BaseApply baseApply){
        DataResult result = new DataResult();
        try {
            baseApplyService.insert(baseApply);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("基地申请失败:" + e.getMessage());
            result.setCode(ReturnStatusEnum.SYS_ERROR.getValue());
            result.setMsg("申请失败");
            return result;
        }
    }

    /**
     * 获取基地信息
     * @return
     */
    @ApiOperation(httpMethod = "GET", value = "获取基地申请信息", nickname = "8")
    @RequestMapping(value = "/baseApplyInfo" ,method = RequestMethod.GET)
    private DataResult getBaseApplyInfo(@ApiParam(value = "用户id", required = false) @RequestParam(value = "customerId",required = false) String customerId){
        DataResult result = new DataResult();
        try {
            BaseApply baseApply = baseApplyService.selectOne(new EntityWrapper<BaseApply>().eq("customer_id",customerId));
            result.setData(baseApply);
        } catch (Exception e) {
            logger.error("获取基地申请信息失败:{}", e);
            result.setCode(ReturnStatusEnum.SYS_ERROR.getValue());
            result.setMsg("获取失败");
        }
        return result;
    }

    /**
     * 修改申请信息
     * @param baseApply
     * @return
     */
    @ApiOperation(httpMethod = "PUT",value = "修改申请信息",notes = "id比传，其他选传",nickname = "9")
    @RequestMapping(value="/baseApply", method=RequestMethod.PUT)
    public DataResult updateUserApi(@RequestBody BaseApply baseApply){
        try {
            baseApply.setUpdateTime(new Date());
            baseApplyService.updateById(baseApply);
            return new DataResult(ReturnStatusEnum.SUCCESS.getValue(),"修改成功",baseApply);
        }catch (Exception e) {
            logger.error("修改用户资料失败", e);
            return new DataResult(ReturnStatusEnum.SYS_ERROR.getValue(),"修改失败",null);
        }
    }
}

