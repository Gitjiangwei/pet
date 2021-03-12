package com.ttm.pet.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.ttm.pet.model.dto.Customer;
import com.ttm.pet.dao.CustomerMapper;
import com.ttm.pet.model.vo.app.BaseListVo;
import com.ttm.pet.model.vo.app.CustomerLeleVo;
import com.ttm.pet.model.vo.app.CustomerSearchVo;
import com.ttm.pet.service.CustomerService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 客户表 服务实现类
 * </p>
 *
 * @author cx
 * @since 2020-03-27
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements CustomerService {
    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public Page<CustomerSearchVo> findCustomerSearchVos(Page<CustomerSearchVo> page, String keyWord, String customerId) {
        List<CustomerSearchVo> customerSearchVos = customerMapper.queryCustomersBySearch(keyWord, customerId);
        page.setRecords(customerSearchVos);
        return page;
    }

    @Override
    public List<CustomerLeleVo> findCustomerLeleVos() {
        return customerMapper.queryCustomerLeleVos();
    }

    @Override
    public Page<BaseListVo> findBaseListVos(Page<BaseListVo> page, String name) {
        List<BaseListVo> baseListVos = customerMapper.queryBaseListVos(page, name);
        page.setRecords(baseListVos);
        return page;
    }

    @Override
    public Integer findBaseCount(Integer type) {
        return customerMapper.queryBaseCount(type);
    }

    @Override
    public List<CustomerLeleVo> findModuleCustomerVos(Integer moduleId) {
        return customerMapper.queryModuleCustomerVos(moduleId);
    }
}
