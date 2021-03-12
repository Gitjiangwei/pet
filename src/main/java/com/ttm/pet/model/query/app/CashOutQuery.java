package com.ttm.pet.model.query.app;

import io.swagger.annotations.ApiModelProperty;

public class CashOutQuery {
    @ApiModelProperty(value = "用户uuid", required = true)
    private String customerId;
    @ApiModelProperty(value = "账户（提现到支付宝需要填支付宝账号，微信提现不要这个字段）", required = false)
    private String account;
    @ApiModelProperty(value = "提现金额", required = true)
    private double money;
    @ApiModelProperty(value = "类型（1-微信 2-支付宝）", required = false)
    private Integer type;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public double getMoney() {
        return money;
    }

    public void setMoney(double money) {
        this.money = money;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
