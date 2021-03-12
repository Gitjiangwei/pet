package com.ttm.pet.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.ttm.pet.model.dto.CustomerRelation;
import com.baomidou.mybatisplus.service.IService;
import com.ttm.pet.model.vo.app.AdoptListVo;
import com.ttm.pet.model.vo.app.FansVo;

/**
 * <p>
 * 用户关系表 服务类
 * </p>
 *
 * @author cx
 * @since 2020-04-23
 */
public interface CustomerRelationService extends IService<CustomerRelation> {

    //查询我的关注粉丝列表
    public Page<FansVo> findFansVo(Page<FansVo> page,String customerId, Integer type);

    //查询领养人列表
    public Page<AdoptListVo> findAdoptListVo(Page<AdoptListVo> page, String customerId);
}
