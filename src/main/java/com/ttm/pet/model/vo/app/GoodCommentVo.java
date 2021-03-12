package com.ttm.pet.model.vo.app;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 查看评论内容
 *
 * @author J
 * @date 2021/3/4
 */
@ApiModel("查看评论实体")
public class GoodCommentVo implements Serializable {
    /**
     * 主键id
     */
    @ApiModelProperty("主键id")
    private Long id;

    /**
     * 头像
     */
    @ApiModelProperty("头像")
    private String backImg;

    /**
     * 用户id
     */
    @ApiModelProperty("用户UUID")
    private String customerId;

    /**
     * 用户名称
     */
    @ApiModelProperty("用户名称")
    private String customerName;

    /**
     * 评论内容
     */
    @ApiModelProperty("评论内容")
    private String remark;

    /**
     * 评论时间
     */
    @ApiModelProperty("评论时间")
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createDate;

    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBackImg() {
        return backImg;
    }

    public void setBackImg(String backImg) {
        this.backImg = backImg;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
