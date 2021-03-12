package com.ttm.pet.model.query.app;

import io.swagger.annotations.ApiModelProperty;

public class SendChatQuery {
    @ApiModelProperty(value = "发送者id", required = true)
    private String fromCustomerId;

    @ApiModelProperty(value = "接受者id", required = true)
    private String toCustomerId;

    @ApiModelProperty(value = "发送内容", required = true)
    private String content;

    public String getFromCustomerId() {
        return fromCustomerId;
    }

    public void setFromCustomerId(String fromCustomerId) {
        this.fromCustomerId = fromCustomerId;
    }

    public String getToCustomerId() {
        return toCustomerId;
    }

    public void setToCustomerId(String toCustomerId) {
        this.toCustomerId = toCustomerId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
