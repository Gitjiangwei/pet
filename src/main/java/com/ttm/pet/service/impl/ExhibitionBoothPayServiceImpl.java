package com.ttm.pet.service.impl;

import com.ttm.pet.dao.ExhibitionBoothMapper;
import com.ttm.pet.dao.ExhibitionBoothPayMapper;
import com.ttm.pet.dao.ExhibitionCollectionMapper;
import com.ttm.pet.model.dto.ExhibitionBoothPay;
import com.ttm.pet.model.dto.ExhibitionBoothPayParam;
import com.ttm.pet.model.pojo.DataResult;
import com.ttm.pet.model.vo.app.ExhibitionPayVo;
import com.ttm.pet.service.ExhibitionBoothPayService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 展位支付业务实现层
 *
 * @author J
 * @date 2021/3/10
 */
@Service
public class ExhibitionBoothPayServiceImpl implements ExhibitionBoothPayService {

    private final static String BOOTH_SELL = "1";

    @Autowired
    private ExhibitionBoothPayMapper exhibitionBoothPayMapper;

    @Autowired
    private ExhibitionCollectionMapper exhibitionCollectionMapper;

    @Autowired
    private ExhibitionBoothMapper exhibitionBoothMapper;

    @Override
    public Boolean saveExhibitionBoothPay(ExhibitionBoothPay exhibitionBoothPay, String payType, String orderId) {
        ExhibitionBoothPayParam exhibitionBoothPayParam = new ExhibitionBoothPayParam();
        BeanUtils.copyProperties(exhibitionBoothPay, exhibitionBoothPayParam);
        exhibitionBoothPayParam.setPayType(payType);
        exhibitionBoothPayParam.setOutTradeNo(orderId);
        exhibitionBoothPayMapper.saveExhibitionBoothPay(exhibitionBoothPayParam);
        return true;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateExhibitionBoothPay(String outTradeNo) {
        // 修改收藏的信息
        ExhibitionPayVo exhibitionPayVo = exhibitionBoothPayMapper.getBoothId(outTradeNo);
        exhibitionBoothPayMapper.updateExhibitionBoothPay(outTradeNo);

        exhibitionCollectionMapper.updateBoothPayStatus(exhibitionPayVo);
        // 将展位标记为已出售
        exhibitionBoothMapper.updateBoothStatus(exhibitionPayVo.getBoothId());
        return true;
    }

    /**
     * 校验展位是否出售
     *
     * @param boothId 检索条件
     * @return true OR false
     * @author J
     * @date 2021/3/10
     */
    @Override
    public Boolean checkBoothStatus(Long boothId) {
        String boothStatus = exhibitionBoothMapper.getBoothStatus(boothId);
        if (BOOTH_SELL.equals(boothStatus)) {
            return false;
        }
        else {
            return true;
        }
    }
}
