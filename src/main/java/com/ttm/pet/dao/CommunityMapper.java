package com.ttm.pet.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.ttm.pet.model.dto.Community;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ttm.pet.model.vo.app.CommunityListVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cx
 * @since 2020-08-07
 */
public interface CommunityMapper extends BaseMapper<Community> {

    //查询社群信息列表
    public List<CommunityListVo> queryCommunityListVos(Page<CommunityListVo> page, @Param("cityId") Integer cityId);

}
