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
 * @since 2020-07-08
 */
@TableName("t_pay")
public class Pay extends Model<Pay> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String customerId;
    private BigDecimal price;
    private Integer originalCoin;
    private Integer increaseCoin;

    /**
     * 订单号
     */
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

    public Integer getIncreaseCoin() {
        return increaseCoin;
    }

    public void setIncreaseCoin(Integer increaseCoin) {
        this.increaseCoin = increaseCoin;
    }

    public Integer getOriginalCoin() {
        return originalCoin;
    }

    public void setOriginalCoin(Integer originalCoin) {
        this.originalCoin = originalCoin;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Pay{" +
        ", id=" + id +
        ", customerId=" + customerId +
        ", price=" + price +
        ", outTradeNo=" + outTradeNo +
        ", type=" + type +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        ", status=" + status +
        ", deleted=" + deleted +
        ", originalCoin=" + originalCoin +
        ", increaseCoin=" + increaseCoin +
        "}";
    }
}
