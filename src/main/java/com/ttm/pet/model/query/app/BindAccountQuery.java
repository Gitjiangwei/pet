package com.ttm.pet.model.query.app;

import io.swagger.annotations.ApiModelProperty;

public class BindAccountQuery {
    @ApiModelProperty(value = "用户uuid", required = true)
    private String customerId;
    @ApiModelProperty(value = "账户（支付宝）", required = true)
    private String account;
    @ApiModelProperty(value = "真实姓名", required = true)
    private String realName;

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

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }
}
