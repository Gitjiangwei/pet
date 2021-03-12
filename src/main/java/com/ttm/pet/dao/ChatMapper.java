package com.ttm.pet.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.ttm.pet.model.dto.Chat;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ttm.pet.model.vo.app.ChatDetailVo;
import com.ttm.pet.model.vo.app.ChatListVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cx
 * @since 2020-06-22
 */
public interface ChatMapper extends BaseMapper<Chat> {
    //查询私信列表
    public List<ChatListVo> queryChatListVos(Page<ChatListVo> page, @Param("customerId")String customerId);

    //查询私信对话框详情
    public List<ChatDetailVo> queryChatDetailVo(Page<ChatDetailVo> page, @Param("customerId")String customerId,@Param("friendCustomerId")String friendCustomerId);
}
