package com.ttm.pet.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.ttm.pet.model.query.app.GoodCommentQuery;
import com.ttm.pet.model.query.app.GoodCommentSave;
import com.ttm.pet.model.vo.app.CustomerCommentVo;
import com.ttm.pet.model.vo.app.GoodCommentVo;

import java.util.List;

/**
 * 订单评论持久层
 *
 * @author J
 * @date 2021/3/4
 */
public interface GoodCommentMapper {
    /**
     * 添加评论
     *
     * @param goodCommentSave 插入参数
     * @return 添加成功后返回的行数
     * @author J
     * @date 2021/3/4
     */
    Integer saveGoodComment(GoodCommentSave goodCommentSave);

    /**
     * 获取订单评论列表
     *
     * @param goodCommentQuery 检索条件
     * @param page 分页
     * @return 订单的评论列表
     * @author 姜伟
     * @date 2021/3/4
     */
    List< GoodCommentVo > listGoodComment(Page< GoodCommentVo > page, GoodCommentQuery goodCommentQuery);
}
