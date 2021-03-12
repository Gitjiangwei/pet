package com.ttm.pet.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.ttm.pet.model.dto.Community;
import com.baomidou.mybatisplus.service.IService;
import com.ttm.pet.model.vo.app.CommunityListVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cx
 * @since 2020-08-07
 */
public interface CommunityService extends IService<Community> {

    //查询社群信息列表
    public Page<CommunityListVo> findCommunityListVos(Page<CommunityListVo> page, Integer cityId);
}
