package com.ttm.pet.service;

import com.ttm.pet.model.dto.CustomerDonate;
import com.baomidou.mybatisplus.service.IService;

import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author cx
 * @since 2020-08-03
 */
public interface CustomerDonateService extends IService<CustomerDonate> {

    //查询当天点赞粮食数
    public Map<String,Object> findSupportFood(String customerId);

}
