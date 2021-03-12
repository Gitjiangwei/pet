package com.ttm.pet.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.ttm.pet.model.dto.Message;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ttm.pet.model.vo.app.MessageListVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 系统消息表 Mapper 接口
 * </p>
 *
 * @author cx
 * @since 2020-06-22
 */
public interface MessageMapper extends BaseMapper<Message> {
    //查询外层消息列表
    public List<MessageListVo> queryMessageListVos(Page<MessageListVo>page, @Param("customerId")String customerId, @Param("deletedMessageId")List<String> deletedMessageId);
}
