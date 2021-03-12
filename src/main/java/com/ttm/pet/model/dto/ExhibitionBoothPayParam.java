package com.ttm.pet.model.dto;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 展位创建订单参数实体
 *
 * @author J
 * @date 2021/3/10
 */
@Data
public class ExhibitionBoothPayParam implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 展位id
     */
    private Long boothId;

    /**
     * 支付人uuid
     */
    private String customerId;

    /**
     * 支付金额
     */
    private BigDecimal payPrice;

    /**
     * 支付类型 1：微信 2：支付宝
     */
    private String payType;

    /**
     * 订单号
     */
    private String outTradeNo;
}
