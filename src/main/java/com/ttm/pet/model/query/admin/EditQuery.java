package com.ttm.pet.model.query.admin;

import io.swagger.annotations.ApiModelProperty;

public class EditQuery {
    @ApiModelProperty(value = "头像", required = false)
    private String portrait;
    @ApiModelProperty(value = "用户名", required = false)
    private String name;
    @ApiModelProperty(value = "简介", required = false)
    private String profile;
    @ApiModelProperty(value = "手机号", required = false)
    private String mobile;
    @ApiModelProperty(value = "微信", required = false)
    private String wxNumber;
    @ApiModelProperty(value = "性别", required = false)
    private String gender;
    @ApiModelProperty(value = "客户编号", required = false)
    private String uuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getWxNumber() {
        return wxNumber;
    }

    public void setWxNumber(String wxNumber) {
        this.wxNumber = wxNumber;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
