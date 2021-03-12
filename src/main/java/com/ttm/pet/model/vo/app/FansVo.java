package com.ttm.pet.model.vo.app;

public class FansVo {
    private long id;
    private String customerId;
    private String customerName;
    private String portrait;
    private int isMutual;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    public int getIsMutual() {
        return isMutual;
    }

    public void setIsMutual(int isMutual) {
        this.isMutual = isMutual;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
