package com.ttm.pet.service.impl;

import com.ttm.pet.model.dto.CustomerDonate;
import com.ttm.pet.dao.CustomerDonateMapper;
import com.ttm.pet.service.CustomerDonateService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cx
 * @since 2020-08-03
 */
@Service
public class CustomerDonateServiceImpl extends ServiceImpl<CustomerDonateMapper, CustomerDonate> implements CustomerDonateService {
    @Autowired
    private CustomerDonateMapper customerDonateMapper;

    @Override
    public Map<String, Object> findSupportFood(String customerId) {
        return customerDonateMapper.querySupportFood(customerId);
    }
}
