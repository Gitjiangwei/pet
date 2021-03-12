package com.ttm.pet.model.dto;

import com.baomidou.mybatisplus.annotations.TableField;
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
 * 作品表; InnoDB free: 11264 kB
 * </p>
 *
 * @author cx
 * @since 2020-04-07
 */
@TableName("t_works")
public class Works extends Model<Works> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 用户uuid
     */
    private String customerId;
    /**
     * 首图地址
     */
    private String firstImg;
    /**
     * 作品标题
     */
    private String title;
    /**
     * 作品内容（视频或者图片链接）
     */
    private String content;
    /**
     * 作品内容文字描述
     */
    private String describe;
    /**
     * 纬度
     */
    private BigDecimal pX;
    /**
     * 经度
     */
    private BigDecimal pY;
    /**
     * 是否推荐
     */
    private Integer isRecommend;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 是否参加比赛，1-参加
     */
    private Integer isMatch;
    /**
     * 是否删除
     */
//    @TableLogic
    private Integer deleted;

    private String location;

    private Integer type;

    private Integer cityId;

    private Integer pointNum;

    private Integer isTop;

    private String urlDescribe;
    private String url;
    private Integer urlCount;

    private Integer moduleId;

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

    public String getFirstImg() {
        return firstImg;
    }

    public void setFirstImg(String firstImg) {
        this.firstImg = firstImg;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public BigDecimal getpX() {
        return pX;
    }

    public void setpX(BigDecimal pX) {
        this.pX = pX;
    }

    public BigDecimal getpY() {
        return pY;
    }

    public void setpY(BigDecimal pY) {
        this.pY = pY;
    }

    public Integer getIsRecommend() {
        return isRecommend;
    }

    public void setIsRecommend(Integer isRecommend) {
        this.isRecommend = isRecommend;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getIsMatch() {
        return isMatch;
    }

    public void setIsMatch(Integer isMatch) {
        this.isMatch = isMatch;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getCityId() {
        return cityId;
    }

    public void setCityId(Integer cityId) {
        this.cityId = cityId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPointNum() {
        return pointNum;
    }

    public void setPointNum(Integer pointNum) {
        this.pointNum = pointNum;
    }

    public Integer getIsTop() {
        return isTop;
    }

    public void setIsTop(Integer isTop) {
        this.isTop = isTop;
    }

    public String getUrlDescribe() {
        return urlDescribe;
    }

    public void setUrlDescribe(String urlDescribe) {
        this.urlDescribe = urlDescribe;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getUrlCount() {
        return urlCount;
    }

    public void setUrlCount(Integer urlCount) {
        this.urlCount = urlCount;
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Works{" +
        ", id=" + id +
        ", customerId=" + customerId +
        ", title=" + title +
        ", firstImg=" + firstImg +
        ", content=" + content +
        ", describe=" + describe +
        ", pX=" + pX +
        ", pY=" + pY +
        ", isRecommend=" + isRecommend +
        ", createTime=" + createTime +
        ", isMatch=" + isMatch +
        ", deleted=" + deleted +
        ", location=" + location +
        ", type=" + type +
        ", cityId=" + cityId +
        ", pointNum=" + pointNum +
        ", isTop=" + isTop +
        ", urlDescribe=" + urlDescribe +
        ", url=" + url +
        ", urlCount=" + urlCount +
        ", moduleId=" + moduleId +
        "}";
    }
}
