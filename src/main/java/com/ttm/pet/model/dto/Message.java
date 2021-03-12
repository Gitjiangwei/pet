package com.ttm.pet.model.dto;

import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 系统消息表
 * </p>
 *
 * @author cx
 * @since 2020-06-22
 */
@TableName("t_message")
public class Message extends Model<Message> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 头像
     */
    private String portrait;
    private String title;
    private String content;
    private String toCustomerId;
    @TableLogic
    private Integer deleted;
    private Date createTime;
    /**
     * 1-系统消息 2-中奖消息
     */
    private Integer type;
    private String drawImg;
    private String drawUrl;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getToCustomerId() {
        return toCustomerId;
    }

    public void setToCustomerId(String toCustomerId) {
        this.toCustomerId = toCustomerId;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDrawImg() {
        return drawImg;
    }

    public void setDrawImg(String drawImg) {
        this.drawImg = drawImg;
    }

    public String getDrawUrl() {
        return drawUrl;
    }

    public void setDrawUrl(String drawUrl) {
        this.drawUrl = drawUrl;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Message{" +
        ", id=" + id +
        ", portrait=" + portrait +
        ", title=" + title +
        ", content=" + content +
        ", toCustomerId=" + toCustomerId +
        ", deleted=" + deleted +
        ", createTime=" + createTime +
        ", type=" + type +
        ", drawImg=" + drawImg +
        ", drawUrl=" + drawUrl +
        "}";
    }
}
