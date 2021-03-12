package com.ttm.pet.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.ttm.pet.model.dto.Chat;
import com.ttm.pet.dao.ChatMapper;
import com.ttm.pet.model.vo.app.ChatDetailVo;
import com.ttm.pet.model.vo.app.ChatListVo;
import com.ttm.pet.service.ChatService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cx
 * @since 2020-06-22
 */
@Service
public class ChatServiceImpl extends ServiceImpl<ChatMapper, Chat> implements ChatService {

    @Autowired
    private ChatMapper chatMapper;
    @Override
    public Page<ChatListVo> findChatListVos(Page<ChatListVo> page, String customerId) {
        List<ChatListVo> chatListVos = chatMapper.queryChatListVos(page,customerId);
        page.setRecords(chatListVos);
        return page;
    }

    @Override
    public Page<ChatDetailVo> findChatDetailVo(Page<ChatDetailVo> page,String customerId, String friendCustomerId) {
        List<ChatDetailVo> chatDetailVos = chatMapper.queryChatDetailVo(page,customerId,friendCustomerId);
        page.setRecords(chatDetailVos);
        return page;
    }
}
