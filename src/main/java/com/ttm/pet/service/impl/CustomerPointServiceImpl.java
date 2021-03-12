package com.ttm.pet.service.impl;

import com.ttm.pet.model.dto.CustomerPoint;
import com.ttm.pet.dao.CustomerPointMapper;
import com.ttm.pet.service.CustomerPointService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户点赞表 服务实现类
 * </p>
 *
 * @author cx
 * @since 2020-05-14
 */
@Service
public class CustomerPointServiceImpl extends ServiceImpl<CustomerPointMapper, CustomerPoint> implements CustomerPointService {

}
