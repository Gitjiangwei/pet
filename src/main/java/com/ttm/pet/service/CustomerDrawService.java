package com.ttm.pet.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.ttm.pet.model.dto.CustomerDraw;
import com.baomidou.mybatisplus.service.IService;
import com.ttm.pet.model.vo.app.DrawCodeListVo;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cx
 * @since 2020-06-24
 */
public interface CustomerDrawService extends IService<CustomerDraw> {
    //查询我的抽奖码列表
    public Page<DrawCodeListVo> findDrawCodeListVos(Page<DrawCodeListVo> page, String customerId, Integer matchId);

}
