package com.ttm.pet.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.ttm.pet.model.dto.Factory;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ttm.pet.model.vo.app.FactoryListVo;

import java.util.List;

/**
 * <p>
 * 抽奖上面厂家表 Mapper 接口
 * </p>
 *
 * @author cx
 * @since 2020-09-21
 */
public interface FactoryMapper extends BaseMapper<Factory> {

    //查询厂家列表
    public List<FactoryListVo> queryFactoryListVos(Page<FactoryListVo> page);
}
