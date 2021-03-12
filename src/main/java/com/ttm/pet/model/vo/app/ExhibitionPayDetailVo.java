package com.ttm.pet.model.vo.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 我的展位订单详情实体
 *
 * @author J
 * @date 2021/3/10
 */
@Data
@ApiModel("我的展位订单详情传输实体")
public class ExhibitionPayDetailVo implements Serializable {

    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("展位订单编码")
    private String outTradeNo;

    @ApiModelProperty("展会标题")
    private String exhibitionTitle;

    @ApiModelProperty("展位编号")
    private String boothCode;

    @ApiModelProperty("展位尺寸")
    private String boothSize;

    @ApiModelProperty("展位原始价格")
    private String oldPrice;

    @ApiModelProperty("展位付款单价")
    private String payPrice;

    @ApiModelProperty("展位是否出售 1：已出售 2：未出售")
    private String boothStatus;

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
}
