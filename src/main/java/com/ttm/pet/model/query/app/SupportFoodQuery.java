package com.ttm.pet.model.query.app;

import io.swagger.annotations.ApiModelProperty;

public class SupportFoodQuery {
    @ApiModelProperty(value = "点赞者id（当前用户id）", required = true)
    private String customerId;
    @ApiModelProperty(value = "上面公司的id", required = true)
    private String businessCustomerId;
    @ApiModelProperty(value = "下面基地的id", required = true)
    private String baseCustomerId;
//    @ApiModelProperty(value = "领的粮食数量", required = true)
//    private Integer food;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getBusinessCustomerId() {
        return businessCustomerId;
    }

    public void setBusinessCustomerId(String businessCustomerId) {
        this.businessCustomerId = businessCustomerId;
    }

    public String getBaseCustomerId() {
        return baseCustomerId;
    }

    public void setBaseCustomerId(String baseCustomerId) {
        this.baseCustomerId = baseCustomerId;
    }

}
