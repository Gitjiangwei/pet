package com.ttm.pet.model.query.app;

import io.swagger.annotations.ApiModelProperty;

public class UuidQuery {
    @ApiModelProperty(value = "用户uuid", required = true)
    private String customerId;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
