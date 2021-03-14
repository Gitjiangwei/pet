package com.ttm.pet.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * 取消收藏参数实体
 *
 * @author J
 * @date 2021/3/14
 */
@Data
@ApiModel("取消收藏参数实体")
public class DeleteExhibitionCollectionParam implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "收藏主键id，多个以逗号分隔", required = true, example = "多个id以逗号分隔。1,2,3")
    @NotBlank(message = "收藏id不能为空")
    private String collIds;
}
