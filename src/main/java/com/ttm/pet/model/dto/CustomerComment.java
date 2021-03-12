package com.ttm.pet.model.dto;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 用户评论表; InnoDB free: 11264 kB
 * </p>
 *
 * @author cx
 * @since 2020-04-08
 */
@TableName("t_customer_comment")
public class CustomerComment extends Model<CustomerComment> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 用户uuid
     */
    private String customerId;
    /**
     * 作品id
     */
    private Long worksId;
    /**
     * 评论内容
     */
    private String remark;
    private Date createTime;
    /**
     * 是否删除 0没有 1删除
     */
    @TableLogic
    private Integer deleted;


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

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "CustomerComment{" +
        ", id=" + id +
        ", customerId=" + customerId +
        ", worksId=" + worksId +
        ", remark=" + remark +
        ", createTime=" + createTime +
        ", deleted=" + deleted +
        "}";
    }
}
