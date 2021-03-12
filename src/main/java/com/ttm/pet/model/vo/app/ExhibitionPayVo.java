package com.ttm.pet.model.vo.app;

import lombok.Data;

import java.io.Serializable;

/**
 * 支付回调修改展位收藏实体
 *
 * @author J
 * @date 2021/3/10
 */
@Data
public class ExhibitionPayVo implements Serializable {

    /**
     * 展位id
     */
    private Long boothId;

    /**
     * 收藏人UUID
     */
    private String customerId;
}
