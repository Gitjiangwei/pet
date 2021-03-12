package com.ttm.pet.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.ttm.pet.model.dto.CustomerDraw;
import com.ttm.pet.dao.CustomerDrawMapper;
import com.ttm.pet.model.vo.app.DrawCodeListVo;
import com.ttm.pet.service.CustomerDrawService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cx
 * @since 2020-06-24
 */
@Service
public class CustomerDrawServiceImpl extends ServiceImpl<CustomerDrawMapper, CustomerDraw> implements CustomerDrawService {

    @Autowired
    private CustomerDrawMapper customerDrawMapper;

    @Override
    public Page<DrawCodeListVo> findDrawCodeListVos(Page<DrawCodeListVo> page, String customerId, Integer matchId) {
        List<DrawCodeListVo> drawCodeListVos = customerDrawMapper.queryDrawCodeListVos(page,customerId,matchId);
        page.setRecords(drawCodeListVos);
        return page;
    }
}
