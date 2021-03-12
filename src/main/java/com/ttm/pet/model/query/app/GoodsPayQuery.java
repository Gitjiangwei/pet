package com.ttm.pet.model.query.app;

import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;

public class GoodsPayQuery {

    @ApiModelProperty(value = "用户id", required = true)
    private String customerId;
    @ApiModelProperty(value = "基地用户id", required = true)
    private String baseCustomerId;
    @ApiModelProperty(value = "购买数量 筹粮必传", required = false)
    private int count;
    @ApiModelProperty(value = "需付金额", required = true)
    private BigDecimal price;
    @ApiModelProperty(value = "openId", required = true)
    private String openId;

    @ApiModelProperty(value = "1-小程序 2-公众号h5", required = false)
    private Integer type;

    @ApiModelProperty(value = "1-筹粮 2-筹钱", required = false)
    private Integer flag;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getBaseCustomerId() {
        return baseCustomerId;
    }

    public void setBaseCustomerId(String baseCustomerId) {
        this.baseCustomerId = baseCustomerId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }
}
