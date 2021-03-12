package com.ttm.pet.model.dto;

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
 * 系统配置
 * </p>
 *
 * @author cx
 * @since 2020-05-25
 */
@TableName("t_config")
public class Config extends Model<Config> {

    private static final long serialVersionUID = 1L;

    /**
     * 注释
     */
    @TableId(value = "id")
    private Integer id;
    /**
     * 名称
     */
    private String name;
    /**
     * 值
     */
    private BigDecimal value;
    /**
     * 更新时间
     */
    private Date createTime;
    /**
     * 备注
     */
    private String remark;
    /**
     * 有效性
     */
    @TableLogic
    private Integer deleted;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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
        return "Config{" +
        ", id=" + id +
        ", name=" + name +
        ", value=" + value +
        ", createTime=" + createTime +
        ", remark=" + remark +
        ", deleted=" + deleted +
        "}";
    }
}
