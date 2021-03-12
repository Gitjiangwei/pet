package com.ttm.pet.model.dto;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author cx
 * @since 2020-11-15
 */
@TableName("t_province")
public class Province extends Model<Province> {

    private static final long serialVersionUID = 1L;

    /**
     * 省份行政区号
     */
    private Integer id;
    /**
     * 省份名称
     */
    private String province;
    @TableLogic
    private Integer delete;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public Integer getDelete() {
        return delete;
    }

    public void setDelete(Integer delete) {
        this.delete = delete;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "Province{" +
        ", id=" + id +
        ", province=" + province +
        ", delete=" + delete +
        "}";
    }
}
