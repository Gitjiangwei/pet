package com.ttm.pet.model.query.app;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

public class WxPayQuery {

    @ApiModelProperty(value = "用户id", required = true)
    private String customerId;
    @ApiModelProperty(value = "充值金额", required = true)
    private BigDecimal price;
    @ApiModelProperty(value = "类型  1-小程序 2-app", required = true)
    private Integer type;
    @ApiModelProperty(value = "type为1小程序时必传", required = false)
    private String openId;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
