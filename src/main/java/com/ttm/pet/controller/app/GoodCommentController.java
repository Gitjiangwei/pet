package com.ttm.pet.controller.app;

import com.ttm.pet.model.pojo.DataResult;
import com.ttm.pet.model.pojo.ListDataResult;
import com.ttm.pet.model.query.app.GoodCommentQuery;
import com.ttm.pet.model.query.app.GoodCommentSave;
import com.ttm.pet.service.GoodCommentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 订单评论控制层
 *
 * @author 姜伟
 * @date 2021/3/4
 */
@Api(value = "/h5/GoodComment", tags = "h5-GoodComment", description = "订单评论")
@RestController
@RequestMapping("/h5/GoodComment")
public class GoodCommentController {
    /**
     * 订单评论业务层
     */
    @Autowired
    private GoodCommentService goodCommentService;

    /**
     * 添加评论内容
     *
     * @param goodCommentSave 添加参数
     * @return 添加成功标记
     * @author J
     * @date 2021/3/4
     */
    @ApiOperation(httpMethod = "POST", value = "添加订单评论内容")
    @PostMapping("/saveGoodComment")
    public DataResult saveGoodComment(@RequestBody @Valid GoodCommentSave goodCommentSave) {
        return goodCommentService.saveGoodComment(goodCommentSave);
    }

    /**
     * 获取订单评论内容
     *
     * @param goodCommentQuery 检索条件
     * @return 评论内容列表
     * @author J
     * @date 2021/3/4
     */
    @ApiOperation(httpMethod = "POST", value = "查询订单评论内容")
    @PostMapping("/queryGoodComment")
    public ListDataResult queryGoodComment(@RequestBody @Valid GoodCommentQuery goodCommentQuery) {
        return goodCommentService.listGoodComment(goodCommentQuery);
    }
}
