package com.ttm.pet.model.vo.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 展位订单列表传输实体
 *
 * @author J
 * @date 2021/3/10
 */
@Data
@ApiModel("展位订单列表传输实体")
public class ExhibitionBoothPayVo implements Serializable {

    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("展会标题")
    private String exhibitionTitle;

    @ApiModelProperty("展位编号")
    private String boothCode;

    @ApiModelProperty("展位尺寸")
    private String boothSize;

    @ApiModelProperty("展位单价")
    private String payPrice;

    @ApiModelProperty("交易状态 1：待支付 2：已支付")
    private String payStatus;

    @ApiModelProperty("封面图片")
    private String coverImage;
}
