package com.ttm.pet.model.query.app;

import io.swagger.annotations.ApiModelProperty;

public class BindPhoneQuery {
    @ApiModelProperty(value = "微信用户uuid", required = true)
    private String customerId;
    @ApiModelProperty(value = "手机号", required = true)
    private String phoneNum;
    @ApiModelProperty(value = "验证码", required = true)
    private int code;
    @ApiModelProperty(value = "密码", required = false)
    private String password;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
