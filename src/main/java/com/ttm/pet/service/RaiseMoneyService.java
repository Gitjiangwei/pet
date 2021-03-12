package com.ttm.pet.service;

import com.ttm.pet.model.pojo.DataResult;

/**
 * 筹钱业务接口
 *
 * @author J
 * @date 2021/3/6
 */
public interface RaiseMoneyService {

    /**
     * 获取筹钱详情
     *
     * @param customerId 检索条件
     * @return 筹钱详情信息
     * @author J
     * @date 2021/3/6
     */
    DataResult getRaiseMoney(Long customerId);
}
