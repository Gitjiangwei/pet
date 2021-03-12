package com.ttm.pet.model.dto;

import com.baomidou.mybatisplus.enums.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 消息业务冗余表
 * </p>
 *
 * @author cx
 * @since 2020-06-22
 */
@TableName("t_message_record")
public class MessageRecord extends Model<MessageRecord> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    private String customerId;
    /**
     * 最后浏览时间
     */
    private Date lastBrowseTime;
    /**
     * 删除的消息id
     */
    private String deleteMessageId;
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

    public Date getLastBrowseTime() {
        return lastBrowseTime;
    }

    public void setLastBrowseTime(Date lastBrowseTime) {
        this.lastBrowseTime = lastBrowseTime;
    }

    public String getDeleteMessageId() {
        return deleteMessageId;
    }

    public void setDeleteMessageId(String deleteMessageId) {
        this.deleteMessageId = deleteMessageId;
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
        return "MessageRecord{" +
        ", id=" + id +
        ", customerId=" + customerId +
        ", lastBrowseTime=" + lastBrowseTime +
        ", deleteMessageId=" + deleteMessageId +
        ", createTime=" + createTime +
        "}";
    }
}
