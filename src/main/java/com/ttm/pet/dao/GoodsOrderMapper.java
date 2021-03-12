package com.ttm.pet.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.ttm.pet.model.dto.GoodsOrder;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ttm.pet.model.vo.app.SupportListVo;
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
public interface GoodsOrderMapper extends BaseMapper<GoodsOrder> {

    //查询支持人数列表
    public List<SupportListVo> querySupportListVos(Page<SupportListVo> page, @Param("type") Integer type, @Param("customerId") String customerId);

}
