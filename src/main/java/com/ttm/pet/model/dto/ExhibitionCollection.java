package com.ttm.pet.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 展位收藏
 *
 * @author J
 * @date 2021/3/9
 */
@Data
@ApiModel("展位收藏参数实体")
public class ExhibitionCollection implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
    private Long id;

    @ApiModelProperty(value = "收藏人uuid", required = true, example = "收藏人uuid")
    @NotBlank(message = "收藏人uuid不能为空")
    private String customerId;

    @ApiModelProperty(value = "展位id", required = true, example = "展位id")
    @NotNull(message = "展位id不能为空")
    private Long bootId;

    @ApiModelProperty(value = "公司性质编码 分类编码：10010", required = true, example = "公司性质编码 分类编码：10010")
    @NotBlank(message = "公司性质编码不能为空")
    private String companyNature;

    @ApiModelProperty(value = "展位类别编码 分类编码：10011", required = true, example = "展位类别编码 分类编码：10011")
    @NotBlank(message = "展位类别编码不能为空")
    private String productCategory;

    @ApiModelProperty(value = "展位类别为【其它】时填写", example = "XX类别  展位类别为【其它】时填写")
    private String otherCategory;

    @ApiModelProperty(value = "展位选择编码 分类编码：10012", required = true, example = "01  展位选择编码 分类编码：10012")
    @NotBlank(message = "展位选择编码不能为空")
    private String boothSelection;

    @ApiModelProperty(value = "展位价格", required = true, example = "100000  展位价格")
    @NotBlank(message = "展位价格不能为空")
    private String oldPrice;

    @ApiModelProperty(value = "备注")
    private String remark;

}
