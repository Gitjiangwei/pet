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
 * 用户关系表
 * </p>
 *
 * @author cx
 * @since 2020-04-23
 */
@TableName("t_customer_relation")
public class CustomerRelation extends Model<CustomerRelation> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 关注人uuid
     */
    private String fromCustomerId;
    /**
     * 被关注人id
     */
    private String toCustomerId;
    /**
     * 关系类型;1-粉丝  2-黑名单
     */
    private Integer relationType;

    private String remark;

    private Integer deleted;
    private Date createDate;

    private Integer isAdopt;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFromCustomerId() {
        return fromCustomerId;
    }

    public void setFromCustomerId(String fromCustomerId) {
        this.fromCustomerId = fromCustomerId;
    }

    public String getToCustomerId() {
        return toCustomerId;
    }

    public void setToCustomerId(String toCustomerId) {
        this.toCustomerId = toCustomerId;
    }

    public Integer getRelationType() {
        return relationType;
    }

    public void setRelationType(Integer relationType) {
        this.relationType = relationType;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getIsAdopt() {
        return isAdopt;
    }

    public void setIsAdopt(Integer isAdopt) {
        this.isAdopt = isAdopt;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "CustomerRelation{" +
        ", id=" + id +
        ", fromCustomerId=" + fromCustomerId +
        ", toCustomerId=" + toCustomerId +
        ", relationType=" + relationType +
        ", remark=" + remark +
        ", isAdopt=" + isAdopt +
        ", deleted=" + deleted +
        ", createDate=" + createDate +
        "}";
    }
}
