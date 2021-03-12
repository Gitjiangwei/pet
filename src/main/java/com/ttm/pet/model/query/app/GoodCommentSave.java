package com.ttm.pet.model.query.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 添加评论实体
 *
 * @author J
 * @date 2021/3/4
 */
@ApiModel("添加评论实体")
public class GoodCommentSave implements Serializable {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private String customerId;

    /**
     * 基地id
     */
    @ApiModelProperty(value = "基地id", required = true)
    @NotNull(message = "基地id不能为空")
    private Long goodId;

    /**
     * 评论内容
     */
    @ApiModelProperty(value = "评论内容", required = true)
    @NotBlank(message = "评论内容不能为空")
    private String remark;

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getGoodId() {
        return goodId;
    }

    public void setGoodId(Long goodId) {
        this.goodId = goodId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
