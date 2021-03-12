package com.ttm.pet.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.ttm.pet.dao.WorksMapper;
import com.ttm.pet.model.dto.CustomerReward;
import com.ttm.pet.dao.CustomerRewardMapper;
import com.ttm.pet.model.vo.app.RewardDetailVo;
import com.ttm.pet.model.vo.app.WorksDetailVo;
import com.ttm.pet.service.CustomerRewardService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 打赏表 服务实现类
 * </p>
 *
 * @author cx
 * @since 2020-08-19
 */
@Service
public class CustomerRewardServiceImpl extends ServiceImpl<CustomerRewardMapper, CustomerReward> implements CustomerRewardService {

    @Autowired
    private CustomerRewardMapper customerRewardMapper;

    @Override
    public Page<RewardDetailVo> findRewardDetailVos(Page<RewardDetailVo> page, String customerId,Long worksId) {
        List<RewardDetailVo> rewardDetailVos = customerRewardMapper.queryRewardDetailVos(page,customerId,worksId);
        page.setRecords(rewardDetailVos);
        return page;
    }
}
