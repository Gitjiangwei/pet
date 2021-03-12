package com.ttm.pet.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 订单评论表
 *
 * @author j
 * @date 2021/3/4
 */
@ApiModel("订单评论实体")
public class GoodComment implements Serializable {
    /**
     * 主键id
     */
    @ApiModelProperty("主键id")
    private Long id;

    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private String customerId;

    /**
     * 订单id
     */
    @ApiModelProperty("订单id")
    private Long goodId;

    /**
     * 评论内容
     */
    @ApiModelProperty("评论内容")
    private String remark;

    /**
     * 评论状态 1：已通过 2：未通过
     */
    @ApiModelProperty("评论状态 1：已通过 2：未通过")
    private String commentStatus;

    /**
     * 创建时间
     */
    @ApiModelProperty("创建时间")
    private LocalDateTime createDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Long getGoodId() {
        return goodId;
    }

    public void setGoodId(Long goodId) {
        this.goodId = goodId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCommentStatus() {
        return commentStatus;
    }

    public void setCommentStatus(String commentStatus) {
        this.commentStatus = commentStatus;
    }

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }
}
