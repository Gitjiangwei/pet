package com.ttm.pet.model.vo.app;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("展位详细信息")
public class ExhibitionBoothDetailVo implements Serializable {

    @ApiModelProperty("展位id")
    private Long id;

    @ApiModelProperty("展会标题")
    private String exhibitionTitle;

    @ApiModelProperty("展位编号")
    private String boothCode;

    @ApiModelProperty("展位尺寸")
    private String boothSize;

    @ApiModelProperty("展位价格")
    private String boothPrice;

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
