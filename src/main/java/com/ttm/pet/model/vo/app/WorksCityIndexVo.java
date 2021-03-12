package com.ttm.pet.model.vo.app;

import java.math.BigDecimal;

public class WorksCityIndexVo {
    private String customerId;
    private String title;
    private String customerName;
    private String portrait;

    private Long id;
    private String firstImg;
    private String content;
    private String describe;
    private Long createTime;
    private Integer type;

    private Integer cityId;
    private String location;
    private BigDecimal pX;
    private BigDecimal pY;
    private Long distance;
    private Integer isPartnership;

    //后加
    private int isFans;
    private int isPoint;
    private int pcnt;
    private int replyCnt;
    private int sumCoin;

    private String urlDescribe;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstImg() {
        return firstImg;
    }

    public void setFirstImg(String firstImg) {
        this.firstImg = firstImg;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public BigDecimal getpX() {
        return pX;
    }

    public void setpX(BigDecimal pX) {
        this.pX = pX;
    }

    public BigDecimal getpY() {
        return pY;
    }

    public void setpY(BigDecimal pY) {
        this.pY = pY;
    }

    public Integer getIsPartnership() {
        return isPartnership;
    }

    public void setIsPartnership(Integer isPartnership) {
        this.isPartnership = isPartnership;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getDistance() {
        return distance;
    }

    public void setDistance(Long distance) {
        this.distance = distance;
    }

    public int getIsFans() {
        return isFans;
    }

    public void setIsFans(int isFans) {
        this.isFans = isFans;
    }

    public int getIsPoint() {
        return isPoint;
    }

    public void setIsPoint(int isPoint) {
        this.isPoint = isPoint;
    }

    public int getPcnt() {
        return pcnt;
    }

    public void setPcnt(int pcnt) {
        this.pcnt = pcnt;
    }

    public int getReplyCnt() {
        return replyCnt;
    }

    public void setReplyCnt(int replyCnt) {
        this.replyCnt = replyCnt;
    }

    public int getSumCoin() {
        return sumCoin;
    }

    public void setSumCoin(int sumCoin) {
        this.sumCoin = sumCoin;
    }

    public String getUrlDescribe() {
        return urlDescribe;
    }

    public void setUrlDescribe(String urlDescribe) {
        this.urlDescribe = urlDescribe;
    }
}
