package com.ttm.pet.model.vo.app;

public class ChatListVo {
    private String customerId;
    private String friendCustomerId;
    private String content;
    private String friendCustomerName;
    private String portrait;
    private Long createTime;
    private Integer status;

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

    public String getFriendCustomerId() {
        return friendCustomerId;
    }

    public void setFriendCustomerId(String friendCustomerId) {
        this.friendCustomerId = friendCustomerId;
    }

    public String getFriendCustomerName() {
        return friendCustomerName;
    }

    public void setFriendCustomerName(String friendCustomerName) {
        this.friendCustomerName = friendCustomerName;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
