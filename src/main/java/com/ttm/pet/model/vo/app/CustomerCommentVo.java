package com.ttm.pet.model.vo.app;

public class CustomerCommentVo {

    public Integer id;

    public String customerId;

    public String portrait;

    public String customerName;

    public String remark;

    public Long createDate;

    public int pcnt;

    public int isPoint;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Long createDate) {
        this.createDate = createDate;
    }

    public int getPcnt() {
        return pcnt;
    }

    public void setPcnt(int pcnt) {
        this.pcnt = pcnt;
    }

    public int getIsPoint() {
        return isPoint;
    }

    public void setIsPoint(int isPoint) {
        this.isPoint = isPoint;
    }
}
