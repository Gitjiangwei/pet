package com.ttm.pet.model.query.app;

import io.swagger.annotations.ApiModelProperty;

public class BusinessQuery {
    @ApiModelProperty(value = "用户uuid", required = true)
    private String customerId;

    @ApiModelProperty(value = "公司名", required = true)
    private String name;

    @ApiModelProperty(value = "公司简称", required = false)
    private String simpleName;

    @ApiModelProperty(value = "法人", required = true)
    private String juridical;

    @ApiModelProperty(value = "电话", required = true)
    private String phone;

    @ApiModelProperty(value = "手机", required = true)
    private String mobile;

    @ApiModelProperty(value = "地址", required = true)
    private String address;

    @ApiModelProperty(value = "地址", required = true)
    private String images;

    @ApiModelProperty(value = "板块id", required = true)
    private Integer moduleId;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSimpleName() {
        return simpleName;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }

    public String getJuridical() {
        return juridical;
    }

    public void setJuridical(String juridical) {
        this.juridical = juridical;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }
}
