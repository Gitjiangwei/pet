package com.ttm.pet.model.query.admin;

import com.ttm.pet.model.query.PageBeanQuery;
import io.swagger.annotations.ApiModelProperty;

public class HappyQuery extends PageBeanQuery {
    @ApiModelProperty(value = "用户名", required = false)
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    @ApiModelProperty(value = "头像", required = false)
    private String portrait;
}
