package com.ttm.pet.model.query.app;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

public class VipPayQuery {
    @ApiModelProperty(value = "用户id", required = true)
    private String customerId;
    @ApiModelProperty(value = "购买年数", required = true)
    private int years;
    @ApiModelProperty(value = "需付金额", required = true)
    private BigDecimal price;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public int getYears() {
        return years;
    }

    public void setYears(int years) {
        this.years = years;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

}
