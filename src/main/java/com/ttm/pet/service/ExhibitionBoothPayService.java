package com.ttm.pet.service;

import com.ttm.pet.model.dto.ExhibitionBoothPay;

/**
 * 展位支付业务接口
 *
 * @author J
 * @date 2021/3/10
 */
public interface ExhibitionBoothPayService {

    /**
     * 创建展位订单
     *
     * @param exhibitionBoothPay 新增参数
     * @param payType 支付类型 1 微信 2 支付宝
     * @param orderId 订单号
     * @return 新增成功标记
     * @author J
     * @date 2021/3/10
     */
    Boolean saveExhibitionBoothPay(ExhibitionBoothPay exhibitionBoothPay, String payType, String orderId);

    /**
     * 修改订单状态
     *
     * @param outTradeNo 订单号
     * @return 修改成功标记
     * @author J
     * @date 2021/3/10
     */
    Boolean updateExhibitionBoothPay(String outTradeNo);

    /**
     * 校验展位是否售出
     *
     * @param boothId 检索条件
     * @return true OR false
     * @author J
     * @date 2021/3/10
     */
    Boolean checkBoothStatus(Long boothId);
}
