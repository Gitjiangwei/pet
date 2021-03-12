package com.ttm.pet.model.dto;

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
 * @since 2020-06-22
 */
@TableName("t_chat")
public class Chat extends Model<Chat> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 真实发送者id
     */
    private String fromCustomerId;
    /**
     * 真实接受者id
     */
    private String toCustomerId;
    /**
     * 所属者id
     */
    private String customerId;
    /**
     * 关联用户id
     */
    private String friendCustomerId;
    /**
     * 内容
     */
    private String content;
    private Date createTime;
    /**
     * 消息状态 0：未读 1：已读 2：删除
     */
    private Integer status;


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

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getFriendCustomerId() {
        return friendCustomerId;
    }

    public void setFriendCustomerId(String friendCustomerId) {
        this.friendCustomerId = friendCustomerId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Chat{" +
        ", id=" + id +
        ", fromCustomerId=" + fromCustomerId +
        ", toCustomerId=" + toCustomerId +
        ", customerId=" + customerId +
        ", friendCustomerId=" + friendCustomerId +
        ", content=" + content +
        ", createTime=" + createTime +
        ", status=" + status +
        "}";
    }
}
