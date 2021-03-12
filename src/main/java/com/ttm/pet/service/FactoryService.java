package com.ttm.pet.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.ttm.pet.model.dto.Factory;
import com.baomidou.mybatisplus.service.IService;
import com.ttm.pet.model.vo.app.FactoryListVo;

/**
 * <p>
 * 抽奖上面厂家表 服务类
 * </p>
 *
 * @author cx
 * @since 2020-09-21
 */
public interface FactoryService extends IService<Factory> {

    //查询厂家列表
    public Page<FactoryListVo> findFactoryListVos(Page<FactoryListVo> page);
}
