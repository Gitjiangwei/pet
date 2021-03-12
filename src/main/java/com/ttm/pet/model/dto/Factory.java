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
 * 抽奖上面厂家表
 * </p>
 *
 * @author cx
 * @since 2020-09-21
 */
@TableName("t_factory")
public class Factory extends Model<Factory> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String customerId;
    /**
     * 厂家名称
     */
    private String name;
    /**
     * 赠品名称
     */
    private String giftName;
    /**
     * 赠品简称
     */
    private String giftSimpleName;
    /**
     * 赠品数量
     */
    private Integer giftNumber;
    /**
     * 赠品图片
     */
    private String giftImg;
    /**
     * 中奖标题
     */
    private String drawTitle;
    /**
     * 中奖内容
     */
    private String drawContent;
    /**
     * 中奖图片
     */
    private String drawImg;
    /**
     * 中奖链接
     */
    private String drawLink;
    private Date createTime;
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

    public String getGiftSimpleName() {
        return giftSimpleName;
    }

    public void setGiftSimpleName(String giftSimpleName) {
        this.giftSimpleName = giftSimpleName;
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

    public String getDrawTitle() {
        return drawTitle;
    }

    public void setDrawTitle(String drawTitle) {
        this.drawTitle = drawTitle;
    }

    public String getDrawContent() {
        return drawContent;
    }

    public void setDrawContent(String drawContent) {
        this.drawContent = drawContent;
    }

    public String getDrawImg() {
        return drawImg;
    }

    public void setDrawImg(String drawImg) {
        this.drawImg = drawImg;
    }

    public String getDrawLink() {
        return drawLink;
    }

    public void setDrawLink(String drawLink) {
        this.drawLink = drawLink;
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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Factory{" +
        ", id=" + id +
        ", customerId=" + customerId +
        ", name=" + name +
        ", giftName=" + giftName +
        ", giftSimpleName=" + giftSimpleName +
        ", giftNumber=" + giftNumber +
        ", giftImg=" + giftImg +
        ", drawTitle=" + drawTitle +
        ", drawContent=" + drawContent +
        ", drawImg=" + drawImg +
        ", drawLink=" + drawLink +
        ", createTime=" + createTime +
        ", deleted=" + deleted +
        "}";
    }
}
