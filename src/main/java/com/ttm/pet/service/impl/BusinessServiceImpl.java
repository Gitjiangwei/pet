package com.ttm.pet.service.impl;

import com.ttm.pet.model.dto.Business;
import com.ttm.pet.dao.BusinessMapper;
import com.ttm.pet.service.BusinessService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 企业表 服务实现类
 * </p>
 *
 * @author cx
 * @since 2020-12-21
 */
@Service
public class BusinessServiceImpl extends ServiceImpl<BusinessMapper, Business> implements BusinessService {

}
