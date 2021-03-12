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
 * 打赏表
 * </p>
 *
 * @author cx
 * @since 2020-08-19
 */
@TableName("t_customer_reward")
public class CustomerReward extends Model<CustomerReward> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String customerId;
    /**
     * 作品id
     */
    private Long worksId;
    /**
     * 打赏宠币
     */
    private Integer coin;
    private Date createTime;
    @TableLogic
    private Integer deleted;
    private String toCustomerId;
    private Integer originalCoin;
    private Integer toOriginalCoin;


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

    public Long getWorksId() {
        return worksId;
    }

    public void setWorksId(Long worksId) {
        this.worksId = worksId;
    }

    public Integer getCoin() {
        return coin;
    }

    public void setCoin(Integer coin) {
        this.coin = coin;
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

    public Integer getOriginalCoin() {
        return originalCoin;
    }

    public void setOriginalCoin(Integer originalCoin) {
        this.originalCoin = originalCoin;
    }

    public String getToCustomerId() {
        return toCustomerId;
    }

    public void setToCustomerId(String toCustomerId) {
        this.toCustomerId = toCustomerId;
    }

    public Integer getToOriginalCoin() {
        return toOriginalCoin;
    }

    public void setToOriginalCoin(Integer toOriginalCoin) {
        this.toOriginalCoin = toOriginalCoin;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "CustomerReward{" +
        ", id=" + id +
        ", customerId=" + customerId +
        ", worksId=" + worksId +
        ", coin=" + coin +
        ", createTime=" + createTime +
        ", deleted=" + deleted +
        ", toCustomerId=" + toCustomerId +
        ", originalCoin=" + originalCoin +
        ", toOriginalCoin=" + toOriginalCoin +
        "}";
    }
}
