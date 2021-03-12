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
 * 企业表
 * </p>
 *
 * @author cx
 * @since 2020-12-21
 */
@TableName("t_business")
public class Business extends Model<Business> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private Long customerId;
    /**
     * 公司名
     */
    private String name;
    /**
     * 公司简称
     */
    private String simpleName;
    /**
     * 法人
     */
    private String juridical;
    /**
     * 电话
     */
    private String phone;
    /**
     * 手机
     */
    private String mobile;
    /**
     * 地址
     */
    private String address;
    /**
     * 图片
     */
    private String images;
    /**
     * 板块id
     */
    private Integer moduleId;
    /**
     * 0：审核中  1:审核通过待支付 2：支付成功
     */
    private Integer status;

    @TableLogic
    private Integer deleted;
    private Date createTime;
    private Date updateTime;

    private Date vipEndTime;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSimpleName() {
        return simpleName;
    }

    public void setSimpleName(String simpleName) {
        this.simpleName = simpleName;
    }

    public String getJuridical() {
        return juridical;
    }

    public void setJuridical(String juridical) {
        this.juridical = juridical;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Business{" +
        ", id=" + id +
        ", customerId=" + customerId +
        ", name=" + name +
        ", simpleName=" + simpleName +
        ", juridical=" + juridical +
        ", phone=" + phone +
        ", mobile=" + mobile +
        ", address=" + address +
        ", images=" + images +
        ", moduleId=" + moduleId +
        ", status=" + status +
        ", deleted=" + deleted +
        ", createTime=" + createTime +
        ", updateTime=" + updateTime +
        "}";
    }

    public Date getVipEndTime() {
        return vipEndTime;
    }

    public void setVipEndTime(Date vipEndTime) {
        this.vipEndTime = vipEndTime;
    }
}
