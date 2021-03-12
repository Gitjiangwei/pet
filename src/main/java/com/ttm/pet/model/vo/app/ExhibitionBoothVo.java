package com.ttm.pet.model.vo.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@Data
@ApiModel("展位信息")
public class ExhibitionBoothVo implements Serializable {

    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("展位区域")
    private String boothRegion;

    @ApiModelProperty("展位编号")
    private String boothCode;

    @ApiModelProperty("展位尺寸")
    private String boothSize;

    @ApiModelProperty("展位价格")
    private String boothPrice;

    @ApiModelProperty("展位是否出售")
    private String boothStatus;
}
