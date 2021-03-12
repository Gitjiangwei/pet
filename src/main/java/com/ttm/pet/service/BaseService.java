package com.ttm.pet.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.ttm.pet.model.dto.Base;
import com.baomidou.mybatisplus.service.IService;
import com.ttm.pet.model.vo.app.BaseDetailMoneyVo;
import com.ttm.pet.model.vo.app.BaseDetailVo;
import com.ttm.pet.model.vo.app.BaseListH5Vo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cx
 * @since 2020-08-04
 */
public interface BaseService extends IService<Base> {

    //查询基地详情
    public BaseDetailVo findBaseDetailVo(String customerId);

    //查询基地详情 (筹钱页面的详情)
    public BaseDetailMoneyVo findBaseDetailMoneyVo(String customerId);

    //h5页面查询基地列表
    public Page<BaseListH5Vo> findBaseListH5Vos(Page<BaseListH5Vo> page, String name);

    //h5页面查询基地列表 (筹钱页面)
    public Page<BaseListH5Vo> findBaseListH5ByMoneyVos(Page<BaseListH5Vo> page, String name);

}
