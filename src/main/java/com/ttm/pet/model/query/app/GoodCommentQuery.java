package com.ttm.pet.model.query.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 订单评论参数实体
 *
 * @author 姜伟
 * @date 2021/3/4
 */
@ApiModel("获取订单评论参数实体")
public class GoodCommentQuery implements Serializable {
    /**
     * 订单id
     */
    @ApiModelProperty(value = "订单id", required = true)
    @NotNull(message = "订单id不能为空")
    private Long goodId;

    /**
     * 每页显示的条数
     */
    @ApiModelProperty(value = "每页显示的条数", required = true, example = "10")
    @NotNull(message = "每页显示的条数不能为空")
    private Integer pageSize;

    /**
     * 当前页
     */
    @ApiModelProperty(value = "当前页", required = true, example = "1")
    @NotNull(message = "当前页不能为空")
    private Integer pageNo;

    public Long getGoodId() {
        return goodId;
    }

    public void setGoodId(Long goodId) {
        this.goodId = goodId;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }
}
