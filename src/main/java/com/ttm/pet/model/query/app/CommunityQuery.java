package com.ttm.pet.model.query.app;

import io.swagger.annotations.ApiModelProperty;

public class CommunityQuery {
    @ApiModelProperty(value = "用户id", required = true)
    private String customerId;
    @ApiModelProperty(value = "内容", required = true)
    private String content;
    @ApiModelProperty(value = "图片地址，多图分号分隔", required = true)
    private String images;
    @ApiModelProperty(value = "城市id", required = true)
    private Integer cityId;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }
}
