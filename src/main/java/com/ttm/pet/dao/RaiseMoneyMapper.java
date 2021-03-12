package com.ttm.pet.dao;

import com.ttm.pet.model.vo.app.RaiseMoneyVo;
import org.apache.ibatis.annotations.Param;

/**
 * 筹钱持久层
 *
 * @author J
 * @date 2021/3/6
 */
public interface RaiseMoneyMapper {

    /**
     * 筹钱详情
     *
     * @param customerId 检索条件
     * @return 筹粮信息
     * @author j
     * @date 2021/3/6
     */
    RaiseMoneyVo getRaiseMoney(@Param("customerId") Long customerId);
}
