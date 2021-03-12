package com.ttm.pet.model.vo.app;


import com.ttm.pet.model.dto.Customer;

import java.util.Date;

public class CustomerVo{

    private String uuid;

    private String petNumber;

    private String name;

    private String mobile;

    private String showMobile;

    private Integer isShowMobile;

    private String gender;

    private Integer age;

    private String portrait;

    private Integer cityId;

    private String cityName;

    private String openId;

    private String wxUnionId;

    private String wxNumber;

    private Integer isShowWx;

    private String profile;

    private String backImg;

    private String remark;

    private Integer isPartnership;

    private Integer isBusiness;

    private Integer isBase;
    private String baseDescribe;

    private Integer isVerified;
    private String verifiedName;
    private Integer isAdopted;

    //关注数
    private int attentionCount;
    //粉丝数
    private int fansCount;
    //我的作品数
    private int WorksCount;
    //点赞数
    private int pointCount;
//    //打赏数
//    private int rewardCount;
    //领养人数量
    private int adoptCount;

    //获得打赏币数
    private int getCoin;
    //打赏币数
    private int rewardCoin;

    private String address;

    private String account;

    private Integer isWorksAdopt;
    private Integer isWorksSupport;

    private String identityToken;

    private String businessName;
    private Date vipStartTime;
    private Date vipEndTime;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getPetNumber() {
        return petNumber;
    }

    public void setPetNumber(String petNumber) {
        this.petNumber = petNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getShowMobile() {
        return showMobile;
    }

    public void setShowMobile(String showMobile) {
        this.showMobile = showMobile;
    }

    public Integer getIsShowMobile() {
        return isShowMobile;
    }

    public void setIsShowMobile(Integer isShowMobile) {
        this.isShowMobile = isShowMobile;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getWxUnionId() {
        return wxUnionId;
    }

    public void setWxUnionId(String wxUnionId) {
        this.wxUnionId = wxUnionId;
    }

    public String getWxNumber() {
        return wxNumber;
    }

    public void setWxNumber(String wxNumber) {
        this.wxNumber = wxNumber;
    }

    public Integer getIsShowWx() {
        return isShowWx;
    }

    public void setIsShowWx(Integer isShowWx) {
        this.isShowWx = isShowWx;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getBackImg() {
        return backImg;
    }

    public void setBackImg(String backImg) {
        this.backImg = backImg;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getIsPartnership() {
        return isPartnership;
    }

    public void setIsPartnership(Integer isPartnership) {
        this.isPartnership = isPartnership;
    }

    public Integer getIsBusiness() {
        return isBusiness;
    }

    public void setIsBusiness(Integer isBusiness) {
        this.isBusiness = isBusiness;
    }

    public Integer getIsBase() {
        return isBase;
    }

    public void setIsBase(Integer isBase) {
        this.isBase = isBase;
    }

    public String getBaseDescribe() {
        return baseDescribe;
    }

    public void setBaseDescribe(String baseDescribe) {
        this.baseDescribe = baseDescribe;
    }

    public Integer getIsVerified() {
        return isVerified;
    }

    public void setIsVerified(Integer isVerified) {
        this.isVerified = isVerified;
    }

    public String getVerifiedName() {
        return verifiedName;
    }

    public void setVerifiedName(String verifiedName) {
        this.verifiedName = verifiedName;
    }

    public Integer getIsAdopted() {
        return isAdopted;
    }

    public void setIsAdopted(Integer isAdopted) {
        this.isAdopted = isAdopted;
    }

    public int getAttentionCount() {
        return attentionCount;
    }

    public void setAttentionCount(int attentionCount) {
        this.attentionCount = attentionCount;
    }

    public int getFansCount() {
        return fansCount;
    }

    public void setFansCount(int fansCount) {
        this.fansCount = fansCount;
    }

    public int getWorksCount() {
        return WorksCount;
    }

    public void setWorksCount(int worksCount) {
        WorksCount = worksCount;
    }

    public int getPointCount() {
        return pointCount;
    }

    public void setPointCount(int pointCount) {
        this.pointCount = pointCount;
    }

    public int getAdoptCount() {
        return adoptCount;
    }

    public void setAdoptCount(int adoptCount) {
        this.adoptCount = adoptCount;
    }

    public int getGetCoin() {
        return getCoin;
    }

    public void setGetCoin(int getCoin) {
        this.getCoin = getCoin;
    }

    public int getRewardCoin() {
        return rewardCoin;
    }

    public void setRewardCoin(int rewardCoin) {
        this.rewardCoin = rewardCoin;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Integer getIsWorksAdopt() {
        return isWorksAdopt;
    }

    public void setIsWorksAdopt(Integer isWorksAdopt) {
        this.isWorksAdopt = isWorksAdopt;
    }

    public Integer getIsWorksSupport() {
        return isWorksSupport;
    }

    public void setIsWorksSupport(Integer isWorksSupport) {
        this.isWorksSupport = isWorksSupport;
    }

    public String getIdentityToken() {
        return identityToken;
    }

    public void setIdentityToken(String identityToken) {
        this.identityToken = identityToken;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public Date getVipStartTime() {
        return vipStartTime;
    }

    public void setVipStartTime(Date vipStartTime) {
        this.vipStartTime = vipStartTime;
    }

    public Date getVipEndTime() {
        return vipEndTime;
    }

    public void setVipEndTime(Date vipEndTime) {
        this.vipEndTime = vipEndTime;
    }
}
