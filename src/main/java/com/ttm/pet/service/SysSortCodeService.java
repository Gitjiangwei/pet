package com.ttm.pet.service;

import com.ttm.pet.model.pojo.DataResult;

/**
 * 系统统一编码业务接口
 *
 * @author J
 * @date 2021/3/9
 */
public interface SysSortCodeService {

    /**
     * 获取统一编码列表
     *
     * @param codeSortId 检索条件
     * @return 编码列表
     * @author J
     * @date 2021/3/9
     */
    DataResult listSysSortCode(String codeSortId);
}
