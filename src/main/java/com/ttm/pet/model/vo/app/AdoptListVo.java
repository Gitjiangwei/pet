package com.ttm.pet.model.vo.app;

public class AdoptListVo {
    private long id;
    private String customerId;
    private String customerName;
    private String portrait;
    private long lastWorkTime;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public long getLastWorkTime() {
        return lastWorkTime;
    }

    public void setLastWorkTime(long lastWorkTime) {
        this.lastWorkTime = lastWorkTime;
    }
}
