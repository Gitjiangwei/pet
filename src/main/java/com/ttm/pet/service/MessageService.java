package com.ttm.pet.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.ttm.pet.model.dto.Message;
import com.baomidou.mybatisplus.service.IService;
import com.ttm.pet.model.vo.app.MessageListVo;

import java.util.List;

/**
 * <p>
 * 系统消息表 服务类
 * </p>
 *
 * @author cx
 * @since 2020-06-22
 */
public interface MessageService extends IService<Message> {
    //查询外层消息列表
    public Page<MessageListVo> findMessageListVos(Page<MessageListVo> page, String customerId, List<String> deletedMessageId);

}
