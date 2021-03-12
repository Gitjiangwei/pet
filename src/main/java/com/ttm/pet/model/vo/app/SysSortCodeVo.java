package com.ttm.pet.model.vo.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("统一编码传输实体")
public class SysSortCodeVo {
    @ApiModelProperty("类型编码")
    private String itemValue;

    @ApiModelProperty("类别名称")
    private String itemName;
}
