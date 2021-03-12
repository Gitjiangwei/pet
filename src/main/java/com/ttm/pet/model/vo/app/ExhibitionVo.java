package com.ttm.pet.model.vo.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@ApiModel("展会列表")
public class ExhibitionVo implements Serializable {

    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("展会标题")
    private String exhibitionTitle;

    @ApiModelProperty("封面标题")
    private String coverTitle;

    @ApiModelProperty("封面图片")
    private String coverImage;

    @ApiModelProperty("展出开始时间")
    private Date exhibitionStartTime;

    @ApiModelProperty("展出结束时间")
    private Date exhibitionEndTime;

    @ApiModelProperty("报名截止时间")
    private Date registrationDeadline;
}
