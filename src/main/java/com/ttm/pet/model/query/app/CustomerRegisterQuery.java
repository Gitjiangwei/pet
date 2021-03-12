package com.ttm.pet.model.query.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="注册用户",description="用户对象user")
public class CustomerRegisterQuery {
    @ApiModelProperty(value = "手机号码", required = true)
    private String phoneNum;

    @ApiModelProperty(value = "验证码", required = true)
    private int code;

    @ApiModelProperty(value = "密码", required = true)
    private String password;

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
