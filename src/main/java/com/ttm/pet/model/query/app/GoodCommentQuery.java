package com.ttm.pet.model.query.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 订单评论参数实体
 *
 * @author 姜伟
 * @date 2021/3/4
 */
@Data
@ApiModel("获取订单评论参数实体")
public class GoodCommentQuery implements Serializable {
    /**
     * 基地id
     */
    @ApiModelProperty(value = "基地id", required = true)
    @NotNull(message = "基地id不能为空")
    private Long goodId;

    /**
     * 评论父级id
     */
    @ApiModelProperty(value = "评论父级id",example = "默认为0，有父级则传id")
    private Long parentId;

    /**
     * 每页显示的条数
     */
    @ApiModelProperty(value = "每页显示的条数", required = true, example = "10")
    @NotNull(message = "每页显示的条数不能为空")
    private Integer pageSize;

    /**
     * 当前页
     */
    @ApiModelProperty(value = "当前页", required = true, example = "1")
    @NotNull(message = "当前页不能为空")
    private Integer pageNo;
}
