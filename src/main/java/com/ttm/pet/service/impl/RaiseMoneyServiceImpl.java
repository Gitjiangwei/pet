package com.ttm.pet.service.impl;

import com.ttm.pet.dao.RaiseMoneyMapper;
import com.ttm.pet.model.pojo.DataResult;
import com.ttm.pet.model.vo.app.RaiseMoneyVo;
import com.ttm.pet.service.RaiseMoneyService;
import com.ttm.pet.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 筹钱业务实现层
 *
 * @author J
 * @date 2021/3/6
 */
@Service
public class RaiseMoneyServiceImpl implements RaiseMoneyService {
    /**
     * 筹钱持久层
     */
    @Autowired
    private RaiseMoneyMapper raiseMoneyMapper;

    @Override
    public DataResult getRaiseMoney(Long customerId) {
        RaiseMoneyVo raiseMoneyVo = raiseMoneyMapper.getRaiseMoney(customerId);
        //计算还剩几天
        raiseMoneyVo.setDayRemain(
                DateUtil.getDatePoor(raiseMoneyVo.getFinishMoneyTime(), new Date()));
        DataResult dataResult = new DataResult();
        dataResult.setData(raiseMoneyVo);
        return dataResult;
    }
}
