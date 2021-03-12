package com.ttm.pet.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.ttm.pet.model.dto.Message;
import com.ttm.pet.dao.MessageMapper;
import com.ttm.pet.model.vo.app.MessageListVo;
import com.ttm.pet.service.MessageService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 系统消息表 服务实现类
 * </p>
 *
 * @author cx
 * @since 2020-06-22
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements MessageService {

    @Autowired
    private MessageMapper messageMapper;
    @Override
    public Page<MessageListVo> findMessageListVos(Page<MessageListVo> page, String customerId, List<String> deletedMessageId) {
        List<MessageListVo> messageListVos = messageMapper.queryMessageListVos(page,customerId,deletedMessageId);
        page.setRecords(messageListVos);
        return page;
    }
}
