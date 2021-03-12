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
 * 提现表
 * </p>
 *
 * @author cx
 * @since 2020-07-08
 */
@TableName("t_cash_out")
public class CashOut extends Model<CashOut> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String customerId;
    /**
     * 提现金额
     */
    private double price;
    /**
     * 原来宠币
     */
    private Integer originalCoin;
    /**
     * 提现宠币
     */
    private Integer outCoin;
    /**
     * 提现账户
     */
    private String account;
    private Date createTime;
    private Integer status;
    @TableLogic
    private Integer deleted;

    private BigDecimal rate;

    private Integer type;

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

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Integer getOriginalCoin() {
        return originalCoin;
    }

    public void setOriginalCoin(Integer originalCoin) {
        this.originalCoin = originalCoin;
    }

    public Integer getOutCoin() {
        return outCoin;
    }

    public void setOutCoin(Integer outCoin) {
        this.outCoin = outCoin;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getRate() {
        return rate;
    }

    public void setRate(BigDecimal rate) {
        this.rate = rate;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "CashOut{" +
        ", id=" + id +
        ", customerId=" + customerId +
        ", price=" + price +
        ", originalCoin=" + originalCoin +
        ", outCoin=" + outCoin +
        ", account=" + account +
        ", createTime=" + createTime +
        ", status=" + status +
        ", deleted=" + deleted +
        ", rate=" + rate +
        ", type=" + type +
        "}";
    }
}
