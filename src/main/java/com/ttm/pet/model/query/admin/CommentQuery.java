package com.ttm.pet.model.query.admin;

import io.swagger.annotations.ApiModelProperty;

public class CommentQuery {
    @ApiModelProperty(value = "用户uuid", required = true)
    private String customerId;
    @ApiModelProperty(value = "作品id", required = true)
    private Long worksId;
    @ApiModelProperty(value = "评论内容", required = true)
    private String remark;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Long getWorksId() {
        return worksId;
    }

    public void setWorksId(Long worksId) {
        this.worksId = worksId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

}
