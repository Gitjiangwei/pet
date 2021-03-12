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
 * @since 2021-03-03
 */
@TableName("t_vip_order")
public class VipOrder extends Model<VipOrder> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String customerId;
    private BigDecimal price;
    /**
     * 原到期时间
     */
    private Date originalEndTime;
    /**
     * 现在到期时间
     */
    private Date nowEndTime;
    /**
     * 充值年数
     */
    private Integer years;
    private String outTradeNo;
    /**
     * 充值类型 1-微信 2-支付宝
     */
    private Integer type;
    private Date createTime;
    private Date updateTime;
    /**
     * 1-申请中  2-已支付
     */
    private Integer status;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Date getOriginalEndTime() {
        return originalEndTime;
    }

    public void setOriginalEndTime(Date originalEndTime) {
        this.originalEndTime = originalEndTime;
    }

    public Date getNowEndTime() {
        return nowEndTime;
    }

    public void setNowEndTime(Date nowEndTime) {
        this.nowEndTime = nowEndTime;
    }

    public Integer getYears() {
        return years;
    }

    public void setYears(Integer years) {
        this.years = years;
    }

    public String getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(String outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
        return "VipOrder{" +
        ", id=" + id +
        ", customerId=" + customerId +
        ", price=" + price +
        ", originalEndTime=" + originalEndTime +
        ", nowEndTime=" + nowEndTime +
        ", years=" + years +
        ", outTradeNo=" + outTradeNo +
        ", type=" + type +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", status=" + status +
        ", deleted=" + deleted +
        "}";
    }
}
