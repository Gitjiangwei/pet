package com.ttm.pet.service.impl;

import com.ttm.pet.dao.SysSortCodeMapper;
import com.ttm.pet.model.pojo.DataResult;
import com.ttm.pet.service.SysSortCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 系统统一编码业务实现层
 * 
 * @author J
 * @date 2021/3/9
 */
@Service
public class SysSortCodeServiceImpl implements SysSortCodeService {

    @Autowired
    private SysSortCodeMapper sysSortCodeMapper;

    @Override
    public DataResult listSysSortCode(String codeSortId) {
        DataResult dataResult = new DataResult();
        dataResult.setData(sysSortCodeMapper.listSysSortCode(codeSortId));
        return dataResult;
    }
}
