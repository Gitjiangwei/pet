package com.ttm.pet.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.ttm.pet.dao.CustomerBaseMapper;
import com.ttm.pet.model.dto.Base;
import com.ttm.pet.model.vo.app.BaseDetailMoneyVo;
import com.ttm.pet.model.vo.app.BaseDetailVo;
import com.ttm.pet.model.vo.app.BaseListH5Vo;
import com.ttm.pet.service.BaseService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ttm.pet.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author cx
 * @since 2020-08-04
 */
@Service
public class BaseServiceImpl extends ServiceImpl< CustomerBaseMapper, Base > implements BaseService {
    @Autowired
    private CustomerBaseMapper customerBaseMapper;

    @Override
    public BaseDetailVo findBaseDetailVo(String customerId) {
        return customerBaseMapper.queryBaseDetailVo(customerId);
    }

    @Override
    public BaseDetailMoneyVo findBaseDetailMoneyVo(String customerId) {
        BaseDetailMoneyVo baseDetailMoneyVo = customerBaseMapper.queryBaseDetailMoneyVo(customerId);
        baseDetailMoneyVo.setFinishMoneyTime(DateUtil.getDatePoor(baseDetailMoneyVo.getFinishMoneyTimes(), new Date()));
        //已筹款
        BigDecimal sumMoney = new BigDecimal(String.valueOf(baseDetailMoneyVo.getSumMoney()));
        //目标款
        BigDecimal targetMoney  = new BigDecimal(String.valueOf(baseDetailMoneyVo.getTargetMoney()));
        //百分比
        BigDecimal ratio = sumMoney.divide(targetMoney,10,BigDecimal.ROUND_UP);
        baseDetailMoneyVo.setRatio(ratio.toString());
        return baseDetailMoneyVo;
    }

    @Override
    public Page< BaseListH5Vo > findBaseListH5Vos(Page< BaseListH5Vo > page, String name) {
        List< BaseListH5Vo > baseListH5Vos = customerBaseMapper.queryBaseListH5Vos(page, name);
        page.setRecords(baseListH5Vos);
        return page;
    }

    @Override
    public Page< BaseListH5Vo > findBaseListH5ByMoneyVos(Page< BaseListH5Vo > page, String name) {
        List< BaseListH5Vo > baseListH5Vos = customerBaseMapper.queryBaseListH5ByMoneyVos(page, name);
        page.setRecords(baseListH5Vos);
        return page;
    }
}
