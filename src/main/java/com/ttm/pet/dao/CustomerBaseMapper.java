package com.ttm.pet.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ttm.pet.model.dto.Base;
import com.ttm.pet.model.vo.app.BaseDetailMoneyVo;
import com.ttm.pet.model.vo.app.BaseDetailVo;
import com.ttm.pet.model.vo.app.BaseListH5Vo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cx
 * @since 2020-08-04
 */
public interface CustomerBaseMapper extends BaseMapper<Base> {

    //查询基地详情
    public BaseDetailVo queryBaseDetailVo(@Param("customerId") String customerId);

    //查询基地详情 (筹钱页面的详情)
    public BaseDetailMoneyVo queryBaseDetailMoneyVo(@Param("customerId") String customerId);

    //h5页面查询基地列表
    public List<BaseListH5Vo> queryBaseListH5Vos(Page<BaseListH5Vo> page,@Param("name") String name);

    //h5页面查询基地列表 (筹钱页面的搜索)
    public List<BaseListH5Vo> queryBaseListH5ByMoneyVos(Page<BaseListH5Vo> page,@Param("name") String name);
}
