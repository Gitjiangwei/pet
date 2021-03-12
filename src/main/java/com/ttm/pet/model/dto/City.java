package com.ttm.pet.model.dto;

import com.baomidou.mybatisplus.annotations.TableLogic;
import com.baomidou.mybatisplus.enums.IdType;
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
 * @since 2020-05-09
 */
@TableName("t_city")
public class City extends Model<City> {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 城市名
     */
    private String city;
    private Integer provinceId;
    private String adoptedRotation;
    private String supportRotation;
    @TableLogic
    private Integer deleted;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Integer getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Integer provinceId) {
        this.provinceId = provinceId;
    }

    public Integer getDeleted() {
        return deleted;
    }

    public void setDeleted(Integer deleted) {
        this.deleted = deleted;
    }

    public String getAdoptedRotation() {
        return adoptedRotation;
    }

    public void setAdoptedRotation(String adoptedRotation) {
        this.adoptedRotation = adoptedRotation;
    }

    public String getSupportRotation() {
        return supportRotation;
    }

    public void setSupportRotation(String supportRotation) {
        this.supportRotation = supportRotation;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    @Override
    public String toString() {
        return "City{" +
        ", id=" + id +
        ", city=" + city +
        ", adoptedRotation=" + adoptedRotation +
        ", supportRotation=" + supportRotation +
        ", provinceId=" + provinceId +
        ", deleted=" + deleted +
        "}";
    }
}
