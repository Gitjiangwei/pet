package com.ttm.pet.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.ttm.pet.model.dto.CustomerReward;
import com.baomidou.mybatisplus.service.IService;
import com.ttm.pet.model.vo.app.RewardDetailVo;

/**
 * <p>
 * 打赏表 服务类
 * </p>
 *
 * @author cx
 * @since 2020-08-19
 */
public interface CustomerRewardService extends IService<CustomerReward> {
    //查询我的被打赏明细
    public Page<RewardDetailVo> findRewardDetailVos(Page<RewardDetailVo> page, String customerId,Long worksId);
}
