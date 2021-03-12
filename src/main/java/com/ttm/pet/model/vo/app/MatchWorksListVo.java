package com.ttm.pet.model.vo.app;

public class MatchWorksListVo {
    private Long id;
    private String customerId;
    private String customerName;
    private String portrait;
    private String firstImg;
    private Integer pcnt;
    private int isPoint;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public String getFirstImg() {
        return firstImg;
    }

    public void setFirstImg(String firstImg) {
        this.firstImg = firstImg;
    }

    public Integer getPcnt() {
        return pcnt;
    }

    public void setPcnt(Integer pcnt) {
        this.pcnt = pcnt;
    }

    public int getIsPoint() {
        return isPoint;
    }

    public void setIsPoint(int isPoint) {
        this.isPoint = isPoint;
    }
}
