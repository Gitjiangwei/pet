package com.ttm.pet.model.vo.app;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 筹钱传输实体层
 *
 * @author J
 * @date 2021/3/6
 */
public class RaiseMoneyVo implements Serializable {
    private String customerId;

    private String shareTitle;

    private String shareImg;

    private String portrait;

    private String name;

    private String images;

    private String content;

    private String extra;

    private String goodsImg;

    private BigDecimal goodsPrice;

    private Integer target;

    private Integer subsidy;

    private String address;

    private String remark;

    private int supporterNum;

    private double bought;

    private String faqiName;

    private String receiveName;

    private Integer id;

    private Date finishStartMoneyTime;

    private Date finishMoneyTime;

    private String dayRemain;

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

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Integer getTarget() {
        return target;
    }

    public void setTarget(Integer target) {
        this.target = target;
    }

    public Integer getSubsidy() {
        return subsidy;
    }

    public void setSubsidy(Integer subsidy) {
        this.subsidy = subsidy;
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

    public double getBought() {
        return bought;
    }

    public void setBought(double bought) {
        this.bought = bought;
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

    public Date getFinishMoneyTime() {
        return finishMoneyTime;
    }

    public void setFinishMoneyTime(Date finishMoneyTime) {
        this.finishMoneyTime = finishMoneyTime;
    }

    public Date getFinishStartMoneyTime() {
        return finishStartMoneyTime;
    }

    public void setFinishStartMoneyTime(Date finishStartMoneyTime) {
        this.finishStartMoneyTime = finishStartMoneyTime;
    }

    public String getDayRemain() {
        return dayRemain;
    }

    public void setDayRemain(String dayRemain) {
        this.dayRemain = dayRemain;
    }
}
