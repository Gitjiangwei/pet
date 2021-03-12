package com.ttm.pet.model.vo.app;

public class CustomerSearchVo {
    private String customerId;
    private String customerName;
    private String portrait;
    private int isFans;
    private int isAdopt;

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

    public int getIsFans() {
        return isFans;
    }

    public void setIsFans(int isFans) {
        this.isFans = isFans;
    }

    public int getIsAdopt() {
        return isAdopt;
    }

    public void setIsAdopt(int isAdopt) {
        this.isAdopt = isAdopt;
    }
}
