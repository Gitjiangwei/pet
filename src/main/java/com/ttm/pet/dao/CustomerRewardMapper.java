package com.ttm.pet.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.ttm.pet.model.dto.CustomerReward;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ttm.pet.model.vo.app.RewardDetailVo;
import com.ttm.pet.model.vo.app.WorksDetailVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 打赏表 Mapper 接口
 * </p>
 *
 * @author cx
 * @since 2020-08-19
 */
public interface CustomerRewardMapper extends BaseMapper<CustomerReward> {
    //查询我的被打赏明细
    public List<RewardDetailVo> queryRewardDetailVos(Page<RewardDetailVo> page, @Param("customerId") String customerId, @Param("worksId") Long worksId);

}
