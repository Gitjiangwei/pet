package com.ttm.pet.model.vo.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 展位收藏传输实体
 *
 * @author J
 * @date 2021/3/9
 */
@Data
@ApiModel("展位收藏列表传输实体")
public class ExhibitionCollectionVo implements Serializable {
    @ApiModelProperty("主键id")
    private Long id;

    @ApiModelProperty("展会标题")
    private String exhibitionTitle;

    @ApiModelProperty("展位编号")
    private String boothCode;

    @ApiModelProperty("展位尺寸")
    private String boothSize;

    @ApiModelProperty("展会地址")
    private String exhibitionAddress;

    @ApiModelProperty("展会开始时间")
    private Date exhibitionStartTime;

    @ApiModelProperty("展会结束时间")
    private Date exhibitionEndTime;

    @ApiModelProperty("封面图片")
    private String coverImage;
}
