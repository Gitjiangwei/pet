package com.ttm.pet.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.ttm.pet.model.dto.Chat;
import com.baomidou.mybatisplus.service.IService;
import com.ttm.pet.model.vo.app.ChatDetailVo;
import com.ttm.pet.model.vo.app.ChatListVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cx
 * @since 2020-06-22
 */
public interface ChatService extends IService<Chat> {
    //查询私信列表
    public Page<ChatListVo> findChatListVos(Page<ChatListVo> page,String customerId);
    //查询私信对话框详情
    public Page<ChatDetailVo> findChatDetailVo(Page<ChatDetailVo> page,String customerId, String friendCustomerId);
}
