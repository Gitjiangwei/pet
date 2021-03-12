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
 * @since 2020-08-03
 */
@TableName("t_customer_donate")
public class CustomerDonate extends Model<CustomerDonate> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String customerId;
    private String businessCustomerId;
    private String baseCustomerId;
    private Integer food;
    @TableLogic
    private Integer deleted;
    private Date createTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getBusinessCustomerId() {
        return businessCustomerId;
    }

    public void setBusinessCustomerId(String businessCustomerId) {
        this.businessCustomerId = businessCustomerId;
    }

    public String getBaseCustomerId() {
        return baseCustomerId;
    }

    public void setBaseCustomerId(String baseCustomerId) {
        this.baseCustomerId = baseCustomerId;
    }

    public Integer getFood() {
        return food;
    }

    public void setFood(Integer food) {
        this.food = food;
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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "CustomerDonate{" +
        ", id=" + id +
        ", customerId=" + customerId +
        ", businessCustomerId=" + businessCustomerId +
        ", baseCustomerId=" + baseCustomerId +
        ", food=" + food +
        ", deleted=" + deleted +
        ", createTime=" + createTime +
        "}";
    }
}
