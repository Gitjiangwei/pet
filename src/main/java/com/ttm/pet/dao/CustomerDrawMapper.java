package com.ttm.pet.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.ttm.pet.model.dto.CustomerDraw;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ttm.pet.model.vo.app.DrawCodeListVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cx
 * @since 2020-06-24
 */
public interface CustomerDrawMapper extends BaseMapper<CustomerDraw> {

    //查询我的抽奖码列表
    public List<DrawCodeListVo> queryDrawCodeListVos(Page<DrawCodeListVo> page,@Param("customerId")String customerId,@Param("matchId")Integer matchId);
}
