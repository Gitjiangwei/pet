package com.ttm.pet.model.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 展位创建订单参数实体
 *
 * @author J
 * @date 2021/3/10
 */
@Data
@ApiModel("展位创建订单参数实体")
public class ExhibitionBoothPay implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "展位id", required = true, example = "1")
    @NotNull(message = "展位id不能为空")
    private Long boothId;

    @ApiModelProperty(value = "下单人uuid", required = true, example = "下单人UUID")
    @NotBlank(message = "下单人UUID不能为空")
    private String customerId;

    @ApiModelProperty(value = "待支付金额", required = true, example = "10000")
    @NotBlank(message = "待支付金额不能为空")
    private BigDecimal payPrice;

    @ApiModelProperty(value = "类型  1-小程序 2-app", required = true)
    private Integer type;
    @ApiModelProperty(value = "type为1小程序时必传", required = false)
    private String openId;
}
