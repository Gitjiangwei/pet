package com.ttm.pet.model.query.app;

import io.swagger.annotations.ApiModelProperty;

public class ChatDeleteQuery {

    @ApiModelProperty(value = "当前用户id", required = true)
    private String customerId;

    @ApiModelProperty(value = "需要删除的对话框好友id", required = true)
    private String friendCustomerId;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getFriendCustomerId() {
        return friendCustomerId;
    }

    public void setFriendCustomerId(String friendCustomerId) {
        this.friendCustomerId = friendCustomerId;
    }
}
