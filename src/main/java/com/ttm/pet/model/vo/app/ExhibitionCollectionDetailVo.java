package com.ttm.pet.model.vo.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 展位收藏详情传输实体
 *
 * @author J
 * @date 2021/3/9
 */
@Data
@ApiModel("展位收藏详情传输实体")
public class ExhibitionCollectionDetailVo implements Serializable {
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("展会标题")
    private String exhibitionTitle;

    @ApiModelProperty("展位编号")
    private String boothCode;

    @ApiModelProperty("展位尺寸")
    private String boothSize;

    @ApiModelProperty("展位单价")
    private String oldPrice;

    @ApiModelProperty("折扣后的价格")
    private String collectionPrice;

    @ApiModelProperty("展会地址")
    private String exhibitionAddress;

    @ApiModelProperty("展会开始时间")
    private Date exhibitionStartTime;

    @ApiModelProperty("展会结束时间")
    private Date exhibitionEndTime;

    @ApiModelProperty("联系方式1")
    private String customerTelephone1;

    @ApiModelProperty("联系方式2")
    private String customerTelephone2;

    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty("展位是否出售 1：已出售 2：未出售")
    private String boothStatus;

    @ApiModelProperty("公司性质编码")
    private String companyNature;

    @ApiModelProperty("产品类别编码")
    private String productCategory;

    @ApiModelProperty("产品类别为【其它】的内容")
    private String otherCategory;

    @ApiModelProperty("展位选择编码")
    private String boothSelection;
}
