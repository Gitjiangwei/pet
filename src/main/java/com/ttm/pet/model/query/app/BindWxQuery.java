package com.ttm.pet.model.query.app;

import io.swagger.annotations.ApiModelProperty;

public class BindWxQuery {
    @ApiModelProperty(value = "手机用户uuid", required = true)
    private String customerId;
    @ApiModelProperty(value = "微信UnionId", required = true)
    private String wxUnionId;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getWxUnionId() {
        return wxUnionId;
    }

    public void setWxUnionId(String wxUnionId) {
        this.wxUnionId = wxUnionId;
    }
}
