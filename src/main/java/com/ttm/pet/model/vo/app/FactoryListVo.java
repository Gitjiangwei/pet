package com.ttm.pet.model.vo.app;

public class FactoryListVo {

    private Integer id;
    private String customerId;
    private String portrait;

    private String name;

    private String giftName;

    private String giftSimpleName;

    private Integer giftNumber;

    private String giftImg;

    private int sumDrawCount;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGiftName() {
        return giftName;
    }

    public void setGiftName(String giftName) {
        this.giftName = giftName;
    }

    public Integer getGiftNumber() {
        return giftNumber;
    }

    public void setGiftNumber(Integer giftNumber) {
        this.giftNumber = giftNumber;
    }

    public String getGiftImg() {
        return giftImg;
    }

    public void setGiftImg(String giftImg) {
        this.giftImg = giftImg;
    }

    public int getSumDrawCount() {
        return sumDrawCount;
    }

    public void setSumDrawCount(int sumDrawCount) {
        this.sumDrawCount = sumDrawCount;
    }

    public String getGiftSimpleName() {
        return giftSimpleName;
    }

    public void setGiftSimpleName(String giftSimpleName) {
        this.giftSimpleName = giftSimpleName;
    }
}
