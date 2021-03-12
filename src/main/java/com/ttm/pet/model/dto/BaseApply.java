package com.ttm.pet.model.dto;

import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author cx
 * @since 2020-10-28
 */
@TableName("t_base_apply")
public class BaseApply extends Model<BaseApply> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String customerId;
    /**
     * 基地名称
     */
    private String baseName;
    /**
     * 姓名
     */
    private String name;
    /**
     * 手机
     */
    private String mobile;
    /**
     * 基地微信
     */
    private String baseWx;
    /**
     * 发起人名称
     */
    private String faqiName;
    /**
     * 接收人姓名
     */
    private String receiveName;
    /**
     * 地址
     */
    private String address;
    /**
     * 默认地址
     */
    private String defaultAddress;
    /**
     * 基地内容
     */
    private String content;
    /**
     * 图片
     */
    private String images;
    private String cardImage1;
    private String cardImage2;
    private String cardImage3;
    private Date createTime;
    private Date updateTime;
    @TableLogic
    private Integer deleted;


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

    public String getBaseName() {
        return baseName;
    }

    public void setBaseName(String baseName) {
        this.baseName = baseName;
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

    public String getBaseWx() {
        return baseWx;
    }

    public void setBaseWx(String baseWx) {
        this.baseWx = baseWx;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDefaultAddress() {
        return defaultAddress;
    }

    public void setDefaultAddress(String defaultAddress) {
        this.defaultAddress = defaultAddress;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public String getCardImage1() {
        return cardImage1;
    }

    public void setCardImage1(String cardImage1) {
        this.cardImage1 = cardImage1;
    }

    public String getCardImage2() {
        return cardImage2;
    }

    public void setCardImage2(String cardImage2) {
        this.cardImage2 = cardImage2;
    }

    public String getCardImage3() {
        return cardImage3;
    }

    public void setCardImage3(String cardImage3) {
        this.cardImage3 = cardImage3;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "BaseApply{" +
        ", id=" + id +
        ", customerId=" + customerId +
        ", baseName=" + baseName +
        ", name=" + name +
        ", mobile=" + mobile +
        ", baseWx=" + baseWx +
        ", faqiName=" + faqiName +
        ", receiveName=" + receiveName +
        ", address=" + address +
        ", defaultAddress=" + defaultAddress +
        ", content=" + content +
        ", images=" + images +
        ", cardImage1=" + cardImage1 +
        ", cardImage2=" + cardImage2 +
        ", cardImage3=" + cardImage3 +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", deleted=" + deleted +
        "}";
    }
}
