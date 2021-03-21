package com.ttm.pet.model.vo.app;

import java.math.BigDecimal;
import java.util.Date;

public class BaseDetailMoneyVo {
    private String customerId;
    private String shareTitle;
    private String shareImg;
    private String portrait;
    private String name;
    private String images;
    private String content;
    private String extra;
    private String address;
    private String remark;
    private int supporterNum;
    private String faqiName;
    private String receiveName;
    private Integer id;
    private String goodsImg;

    private BigDecimal targetMoney;
    private String finishMoneyTime;
    private Date finishMoneyTimes;
    private Integer moneyFinished;
    private BigDecimal sumMoney;
    private String ratio;

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getShareTitle() {
        return shareTitle;
    }

    public void setShareTitle(String shareTitle) {
        this.shareTitle = shareTitle;
    }

    public String getShareImg() {
        return shareImg;
    }

    public void setShareImg(String shareImg) {
        this.shareImg = shareImg;
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

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getExtra() {
        return extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getSupporterNum() {
        return supporterNum;
    }

    public void setSupporterNum(int supporterNum) {
        this.supporterNum = supporterNum;
    }

    public String getFaqiName() {
        return faqiName;
    }

    public void setFaqiName(String faqiName) {
        this.faqiName = faqiName;
    }

    public String getReceiveName() {
        return receiveName;
    }

    public void setReceiveName(String receiveName) {
        this.receiveName = receiveName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getTargetMoney() {
        return targetMoney;
    }

    public void setTargetMoney(BigDecimal targetMoney) {
        this.targetMoney = targetMoney;
    }

    public String getFinishMoneyTime() {
        return finishMoneyTime;
    }

    public void setFinishMoneyTime(String finishMoneyTime) {
        this.finishMoneyTime = finishMoneyTime;
    }

    public Integer getMoneyFinished() {
        return moneyFinished;
    }

    public void setMoneyFinished(Integer moneyFinished) {
        this.moneyFinished = moneyFinished;
    }

    public BigDecimal getSumMoney() {
        return sumMoney;
    }

    public void setSumMoney(BigDecimal sumMoney) {
        this.sumMoney = sumMoney;
    }

    public Date getFinishMoneyTimes() {
        return finishMoneyTimes;
    }

    public void setFinishMoneyTimes(Date finishMoneyTimes) {
        this.finishMoneyTimes = finishMoneyTimes;
    }

    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }
}
