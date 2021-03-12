package com.ttm.pet.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.ttm.pet.model.dto.GoodsOrder;
import com.ttm.pet.dao.GoodsOrderMapper;
import com.ttm.pet.model.vo.app.SupportListVo;
import com.ttm.pet.service.GoodsOrderService;
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
 * @since 2020-08-04
 */
@Service
public class GoodsOrderServiceImpl extends ServiceImpl<GoodsOrderMapper, GoodsOrder> implements GoodsOrderService {

    @Autowired
    private GoodsOrderMapper goodsOrderMapper;

    @Override
    public Page<SupportListVo> findSupportListVos(Page<SupportListVo> page, Integer type, String customerId) {
        List<SupportListVo> supportListVos = goodsOrderMapper.querySupportListVos(page,type,customerId);
        page.setRecords(supportListVos);
        return page;
    }
}
