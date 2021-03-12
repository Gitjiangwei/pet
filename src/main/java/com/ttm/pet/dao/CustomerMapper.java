package com.ttm.pet.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.ttm.pet.model.dto.Customer;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ttm.pet.model.vo.app.BaseListVo;
import com.ttm.pet.model.vo.app.CustomerLeleVo;
import com.ttm.pet.model.vo.app.CustomerSearchVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 客户表 Mapper 接口
 * </p>
 *
 * @author cx
 * @since 2020-03-27
 */
public interface CustomerMapper extends BaseMapper<Customer> {
    //首页搜索用户
    public List<CustomerSearchVo> queryCustomersBySearch(@Param("keyWord") String keyWord, @Param("customerId")String customerId);

    //查询乐乐页面上面25个头像
    public List<CustomerLeleVo> queryCustomerLeleVos();

    //查询宠粮基地列表
    public List<BaseListVo> queryBaseListVos(Page<BaseListVo>page,@Param("name")String name);

    //根据type查询基地数量
    public Integer queryBaseCount (Integer type);

    //查询模块页面滑动头像
    public List<CustomerLeleVo> queryModuleCustomerVos(@Param("moduleId")Integer moduleId);
}
