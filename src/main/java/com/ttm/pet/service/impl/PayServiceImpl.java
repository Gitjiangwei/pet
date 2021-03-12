package com.ttm.pet.service.impl;

import com.ttm.pet.model.dto.Pay;
import com.ttm.pet.dao.PayMapper;
import com.ttm.pet.service.PayService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cx
 * @since 2020-07-08
 */
@Service
public class PayServiceImpl extends ServiceImpl<PayMapper, Pay> implements PayService {

}
