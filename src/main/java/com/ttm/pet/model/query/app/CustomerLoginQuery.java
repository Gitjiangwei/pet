package com.ttm.pet.model.query.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel(value="登陆用户")
public class CustomerLoginQuery {

    @ApiModelProperty(value = "手机号", required = false)
    private String phoneNum;

    @ApiModelProperty(value = "密码", required = false)
    private String password;

    @ApiModelProperty(value = "微信unionId", required = false)
    private String wxUnionId;
    @ApiModelProperty(value = "微信昵称", required = false)
    private String name;
    @ApiModelProperty(value = "性别", required = false)
    private String gender;
    @ApiModelProperty(value = "头像", required = false)
    private String portrait;
    @ApiModelProperty(value = "城市，有就给", required = false)
    private String cityName;
    @ApiModelProperty(value = "城市Id，有就给", required = false)
    private Integer cityId;

    @ApiModelProperty(value = "苹果登录", required = false)
    private String identityToken;

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getWxUnionId() {
        return wxUnionId;
    }

    public void setWxUnionId(String wxUnionId) {
        this.wxUnionId = wxUnionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getIdentityToken() {
        return identityToken;
    }

    public void setIdentityToken(String identityToken) {
        this.identityToken = identityToken;
    }
}
