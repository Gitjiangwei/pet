package com.ttm.pet.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.ttm.pet.model.dto.Community;
import com.ttm.pet.dao.CommunityMapper;
import com.ttm.pet.model.vo.app.CommunityListVo;
import com.ttm.pet.service.CommunityService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cx
 * @since 2020-08-07
 */
@Service
public class CommunityServiceImpl extends ServiceImpl<CommunityMapper, Community> implements CommunityService {

    @Autowired
    private CommunityMapper communityMapper;

    @Override
    public Page<CommunityListVo> findCommunityListVos(Page<CommunityListVo> page, Integer cityId) {
        List<CommunityListVo> communityListVos = communityMapper.queryCommunityListVos(page,cityId);
        page.setRecords(communityListVos);
        return page;
    }
}
