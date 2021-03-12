package com.ttm.pet.model.query.app;

import io.swagger.annotations.ApiModelProperty;

public class MessageDeleteQuery {
    @ApiModelProperty(value = "用户id", required = true)
    private String customerId;
    @ApiModelProperty(value = "消息id", required = true)
    private Integer id;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
