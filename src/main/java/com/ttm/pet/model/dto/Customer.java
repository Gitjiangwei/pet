package com.ttm.pet.model.dto;

import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.enums.IdType;

import java.math.BigDecimal;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 客户表
 * </p>
 *
 * @author cx
 * @since 2020-03-27
 */
@TableName("t_customer")
public class Customer extends Model<Customer> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 客户编号
     */
    private String uuid;
    /**
     * 宠艾号
     */
    private String petNumber;
    /**
     * 客户名称
     */
    private String name;
    /**
     * 手机号码
     */
    private String mobile;
    /**
     * 展示用手机号码
     */
    private String showMobile;
    /**
     * 是否展示手机号码
     */
    private Integer isShowMobile;
    /**
     * 密码
     */
    private String password;
    /**
     * 性别
     */
    private String gender;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 头像
     */
    private String portrait;
    /**
     * 城市id
     */
    private Integer cityId;

    /**
     * 城市名字
     */
    private String cityName;
    private String openId;

    /**
     * 微信编号
     */
    private String wxUnionId;
    /**
     * 微信号码
     */
    private String wxNumber;
    /**
     * 是否展示微信号码
     */
    private Integer isShowWx;
    /**
     * 简介
     */
    private String profile;
    /**
     * 背景图
     */
    private String backImg;
    /**
     * 备注
     */
    private String remark;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 是否为合作关系
     */
    private Integer isPartnership;
    /**
     * 是否有效
     */
    @TableLogic
    private Integer deleted;

    private Integer petCoin;


    private Integer isBusiness;
    private Integer sumFood;
    private Integer food;
    private Integer isBase;
    private String baseDescribe;

    private Integer isVerified;
    private String verifiedName;
    private Integer isAdopted;

    private String account;
    private String realName;
    private Integer isMini;
    private String address;

    private BigDecimal rate;
    private String hOpenId;

    //后台人员自己添加的标识
    private Integer sendFood;

    private Integer isWorksAdopt;
    private Integer isWorksSupport;

    private String identityToken;

    private String businessName;
    private Date vipStartTime;
    private Date vipEndTime;

    private Integer moduleId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getWxUnionId() {
        return wxUnionId;
    }

    public void setWxUnionId(String wxUnionId) {
        this.wxUnionId = wxUnionId;
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

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public Integer getIsShowMobile() {
        return isShowMobile;
    }

    public void setIsShowMobile(Integer isShowMobile) {
        this.isShowMobile = isShowMobile;
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

    public Integer getIsPartnership() {
        return isPartnership;
    }

    public void setIsPartnership(Integer isPartnership) {
        this.isPartnership = isPartnership;
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

    public Integer getPetCoin() {
        return petCoin;
    }

    public void setPetCoin(Integer petCoin) {
        this.petCoin = petCoin;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getIsBusiness() {
        return isBusiness;
    }

    public void setIsBusiness(Integer isBusiness) {
        this.isBusiness = isBusiness;
    }

    public Integer getSumFood() {
        return sumFood;
    }

    public void setSumFood(Integer sumFood) {
        this.sumFood = sumFood;
    }

    public Integer getFood() {
        return food;
    }

    public void setFood(Integer food) {
        this.food = food;
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

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public Integer getIsMini() {
        return isMini;
    }

    public void setIsMini(Integer isMini) {
        this.isMini = isMini;
    }

    public Integer getSendFood() {
        return sendFood;
    }

    public void setSendFood(Integer sendFood) {
        this.sendFood = sendFood;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public String gethOpenId() {
        return hOpenId;
    }

    public void sethOpenId(String hOpenId) {
        this.hOpenId = hOpenId;
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

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Customer{" +
        ", id=" + id +
        ", uuid=" + uuid +
        ", petNumber=" + petNumber +
        ", name=" + name +
        ", mobile=" + mobile +
        ", showMobile=" + showMobile +
        ", isShowMobile=" + isShowMobile +
        ", password=" + password +
        ", gender=" + gender +
        ", age=" + age +
        ", portrait=" + portrait +
        ", cityId=" + cityId +
        ", cityName=" + cityName +
        ", wxUnionId=" + wxUnionId +
        ", wxNumber=" + wxNumber +
        ", isShowWx=" + isShowWx +
        ", profile=" + profile +
        ", backImg=" + backImg +
        ", remark=" + remark +
        ", createTime=" + createTime +
        ", isPartnership=" + isPartnership +
        ", deleted=" + deleted +
        ", petCoin=" + petCoin +
        ", openId=" + openId +
        ", isBusiness=" + isBusiness +
        ", sumFood=" + sumFood +
        ", food=" + food +
        ", isBase=" + isBase +
        ", baseDescribe=" + baseDescribe +
        ", isVerified=" + isVerified +
        ", verifiedName=" + verifiedName +
        ", isAdopted=" + isAdopted +
        ", account=" + account +
        ", realName=" + realName +
        ", isMini=" + isMini +
        ", address=" + address +
        ", sendFood=" + sendFood +
        ", rate=" + rate +
        ", hOpenId=" + hOpenId +
        ", isWorksAdopt=" + isWorksAdopt +
        ", isWorksSupport=" + isWorksSupport +
        ", identityToken=" + identityToken +
        ", businessName=" + businessName +
        ", vipStartTime=" + vipStartTime +
        ", vipEndTime=" + vipEndTime +
        ", moduleId=" + moduleId +
        "}";
    }
}
