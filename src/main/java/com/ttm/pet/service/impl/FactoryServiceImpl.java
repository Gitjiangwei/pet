package com.ttm.pet.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.ttm.pet.model.dto.Factory;
import com.ttm.pet.dao.FactoryMapper;
import com.ttm.pet.model.vo.app.FactoryListVo;
import com.ttm.pet.service.FactoryService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 抽奖上面厂家表 服务实现类
 * </p>
 *
 * @author cx
 * @since 2020-09-21
 */
@Service
public class FactoryServiceImpl extends ServiceImpl<FactoryMapper, Factory> implements FactoryService {

    @Autowired
    private FactoryMapper factoryMapper;

    @Override
    public Page<FactoryListVo> findFactoryListVos(Page<FactoryListVo> page) {
        List<FactoryListVo> factories = factoryMapper.queryFactoryListVos(page);
        page.setRecords(factories);
        return page;
    }
}
