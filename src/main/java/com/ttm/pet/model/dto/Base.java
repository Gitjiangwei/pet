package com.ttm.pet.model.dto;

import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.enums.IdType;
import java.math.BigDecimal;
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
 * @since 2020-08-04
 */
@TableName("t_base")
public class Base extends Model<Base> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String customerId;
    /**
     * 上面头图
     */
    private String images;
    /**
     * 介绍内容
     */
    private String content;
    /**
     * 额外的一张图
     */
    private String extra;
    /**
     * 商品的图片
     */
    private String goodsImg;
    /**
     * 商品的价格
     */
    private BigDecimal goodsPrice;
    /**
     * 商品的重量
     */
    private Double goodsWeight;
    /**
     * 目标宠粮
     */
    private Integer target;
    /**
     * 补贴金额
     */
    private Integer subsidy;
    /**
     * 地址
     */
    private String address;
    /**
     * 备注
     */
    private String remark;
    @TableLogic
    private Integer deleted;
    private Date createTime;

    private Integer likeTarget;

    private String faqiName;

    private String receiveName;

    private String shareTitle;

    private String shareImg;

    private Integer buyFinished;
    private Integer giveFinished;

    private Integer type;
    private BigDecimal targetMoney;
    private Date finishMoneyTime;
    private Integer moneyFinished;

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

    public Double getGoodsWeight() {
        return goodsWeight;
    }

    public void setGoodsWeight(Double goodsWeight) {
        this.goodsWeight = goodsWeight;
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

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getLikeTarget() {
        return likeTarget;
    }

    public void setLikeTarget(Integer likeTarget) {
        this.likeTarget = likeTarget;
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

    public Integer getBuyFinished() {
        return buyFinished;
    }

    public void setBuyFinished(Integer buyFinished) {
        this.buyFinished = buyFinished;
    }

    public Integer getGiveFinished() {
        return giveFinished;
    }

    public void setGiveFinished(Integer giveFinished) {
        this.giveFinished = giveFinished;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public BigDecimal getTargetMoney() {
        return targetMoney;
    }

    public void setTargetMoney(BigDecimal targetMoney) {
        this.targetMoney = targetMoney;
    }

    public Date getFinishMoneyTime() {
        return finishMoneyTime;
    }

    public void setFinishMoneyTime(Date finishMoneyTime) {
        this.finishMoneyTime = finishMoneyTime;
    }

    public Integer getMoneyFinished() {
        return moneyFinished;
    }

    public void setMoneyFinished(Integer moneyFinished) {
        this.moneyFinished = moneyFinished;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Base{" +
        ", id=" + id +
        ", customerId=" + customerId +
        ", images=" + images +
        ", content=" + content +
        ", extra=" + extra +
        ", goodsImg=" + goodsImg +
        ", goodsPrice=" + goodsPrice +
        ", goodsWeight=" + goodsWeight +
        ", target=" + target +
        ", subsidy=" + subsidy +
        ", address=" + address +
        ", remark=" + remark +
        ", deleted=" + deleted +
        ", createTime=" + createTime +
        ", likeTarget=" + likeTarget +
        ", faqiName=" + faqiName +
        ", receiveName=" + receiveName +
        ", shareTitle=" + shareTitle +
        ", shareImg=" + shareImg +
        ", buyFinished=" + buyFinished +
        ", giveFinished=" + giveFinished +
        ", type=" + type +
        ", finishMoneyTime=" + finishMoneyTime +
        ", targetMoney=" + targetMoney +
        ", moneyFinished=" + moneyFinished +
        "}";
    }
}
