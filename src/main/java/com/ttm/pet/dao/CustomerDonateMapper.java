package com.ttm.pet.dao;

import com.ttm.pet.model.dto.CustomerDonate;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author cx
 * @since 2020-08-03
 */
public interface CustomerDonateMapper extends BaseMapper<CustomerDonate> {

    //查询当天点赞粮食数
    public Map<String,Object> querySupportFood(@Param("customerId")String customerId);

}
