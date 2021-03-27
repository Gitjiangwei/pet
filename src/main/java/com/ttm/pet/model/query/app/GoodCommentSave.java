package com.ttm.pet.model.query.app;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * 添加评论实体
 *
 * @author J
 * @date 2021/3/4
 */
@Data
@ApiModel("添加评论实体")
public class GoodCommentSave implements Serializable {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private String customerId;

    /**
     * 父级ID
     */
    @ApiModelProperty(value = "父级id", required = true, example = "最顶层为0，其余为主键id")
    @NotNull(message = "父级id不能为空")
    private Long parentId;

    /**
     * 基地id
     */
    @ApiModelProperty(value = "基地id", required = true)
    @NotNull(message = "基地id不能为空")
    private Long goodId;

    /**
     * 评论内容
     */
    @ApiModelProperty(value = "评论内容", required = true)
    @NotBlank(message = "评论内容不能为空")
    private String remark;
}
