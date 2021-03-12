package com.ttm.pet.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.ttm.pet.model.dto.Customer;
import com.baomidou.mybatisplus.service.IService;
import com.ttm.pet.model.vo.app.BaseListVo;
import com.ttm.pet.model.vo.app.CustomerLeleVo;
import com.ttm.pet.model.vo.app.CustomerSearchVo;

import java.util.List;

/**
 * <p>
 * 客户表 服务类
 * </p>
 *
 * @author cx
 * @since 2020-03-27
 */
public interface CustomerService extends IService<Customer> {
    //首页搜索用户
    public Page<CustomerSearchVo> findCustomerSearchVos(Page<CustomerSearchVo> page, String keyWord, String customerId);

    //查询乐乐页面上面25个头像
    public List<CustomerLeleVo> findCustomerLeleVos();

    //查询宠粮基地列表
    public Page<BaseListVo> findBaseListVos(Page<BaseListVo>page, String name);

    //根据type查询基地数量
    public Integer findBaseCount (Integer type);

    //查询模块页面滑动头像
    public List<CustomerLeleVo> findModuleCustomerVos(Integer moduleId);
}
