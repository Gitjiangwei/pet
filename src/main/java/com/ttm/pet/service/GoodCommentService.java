package com.ttm.pet.service;

import com.ttm.pet.model.pojo.DataResult;
import com.ttm.pet.model.pojo.ListDataResult;
import com.ttm.pet.model.query.app.GoodCommentQuery;
import com.ttm.pet.model.query.app.GoodCommentSave;

/**
 * 订单评论业务接口
 *
 * @author J
 * @date 2021/3/4
 */
public interface GoodCommentService {

    /**
     * 添加评论
     *
     * @param goodCommentSave 添加参数
     * @return 添加成功标记
     * @author J
     * @date 2021/3/4
     */
    DataResult saveGoodComment(GoodCommentSave goodCommentSave);

    /**
     * 获取订单评论列表
     *
     * @param goodCommentQuery 检索条件
     * @return 评论列表
     * @author J
     * @date 2021/3/4
     */
    ListDataResult listGoodComment(GoodCommentQuery goodCommentQuery);
}
