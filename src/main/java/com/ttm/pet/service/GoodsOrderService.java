package com.ttm.pet.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.ttm.pet.model.dto.GoodsOrder;
import com.baomidou.mybatisplus.service.IService;
import com.ttm.pet.model.vo.app.SupportListVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cx
 * @since 2020-08-04
 */
public interface GoodsOrderService extends IService<GoodsOrder> {

    //查询支持人数列表
    public Page<SupportListVo> findSupportListVos(Page<SupportListVo> page, Integer type, String customerId);

}
