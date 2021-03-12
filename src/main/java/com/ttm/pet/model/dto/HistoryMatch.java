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
 * 历史比赛表

设计稿5-5, 所有的比赛信息为图片  
 * </p>
 *
 * @author cx
 * @since 2020-06-12
 */
@TableName("t_history_match")
public class HistoryMatch extends Model<HistoryMatch> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String title;
    /**
     * 每次比赛信息为一张图，图片地址
     */
    private String image;
    private String showImg;
    private String rule;
    private Date startTime;
    private Date endTime;
    private Date drawEndTime;
    private Date createTime;
    private Integer deleted;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
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

    public String getShowImg() {
        return showImg;
    }

    public void setShowImg(String showImg) {
        this.showImg = showImg;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    public Date getDrawEndTime() {
        return drawEndTime;
    }

    public void setDrawEndTime(Date drawEndTime) {
        this.drawEndTime = drawEndTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "HistoryMatch{" +
        ", id=" + id +
        ", title=" + title +
        ", image=" + image +
        ", showImg=" + showImg +
        ", rule=" + rule +
        ", startTime=" + startTime +
        ", endTime=" + endTime +
        ", drawEndTime=" + drawEndTime +
        ", createTime=" + createTime +
        ", deleted=" + deleted +
        "}";
    }
}
