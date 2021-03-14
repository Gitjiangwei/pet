package com.ttm.pet.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.google.common.collect.Lists;
import com.ttm.pet.dao.ExhibitionBoothMapper;
import com.ttm.pet.dao.ExhibitionBoothPayMapper;
import com.ttm.pet.dao.ExhibitionCollectionMapper;
import com.ttm.pet.dao.ExhibitionMapper;
import com.ttm.pet.enums.ReturnStatusEnum;
import com.ttm.pet.model.dto.DeleteExhibitionCollectionParam;
import com.ttm.pet.model.dto.ExhibitionBoothPay;
import com.ttm.pet.model.dto.ExhibitionCollection;
import com.ttm.pet.model.pojo.DataResult;
import com.ttm.pet.model.pojo.ListDataResult;
import com.ttm.pet.model.vo.app.ExhibitionBoothPayVo;
import com.ttm.pet.model.vo.app.ExhibitionBoothVo;
import com.ttm.pet.model.vo.app.ExhibitionCollectionVo;
import com.ttm.pet.model.vo.app.ExhibitionVo;
import com.ttm.pet.service.ExhibitionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ExhibitionServiceImpl implements ExhibitionService {
    @Autowired
    private ExhibitionMapper exhibitionMapper;

    @Autowired
    private ExhibitionBoothMapper exhibitionBoothMapper;

    @Autowired
    private ExhibitionCollectionMapper exhibitionCollectionMapper;

    @Autowired
    private ExhibitionBoothPayMapper exhibitionBoothPayMapper;

    @Override
    public ListDataResult listExhibition(Integer pageNo, Integer pageSize) {
        Page< ExhibitionVo > exhibitionQueryPage = new Page<>(pageNo, pageSize);
        exhibitionQueryPage.setRecords(exhibitionMapper.listExhibition());
        ListDataResult listDataResult = new ListDataResult();
        listDataResult.setCurrent(exhibitionQueryPage.getCurrent());
        listDataResult.setTotal(exhibitionQueryPage.getTotal());
        listDataResult.setData(exhibitionQueryPage.getRecords());
        return listDataResult;
    }

    @Override
    public DataResult listExhibitionBooth(Long exhibitionId) {
        List< ExhibitionBoothVo > exhibitionBoothVoList = exhibitionBoothMapper.listExhibitionBooth(exhibitionId);
        DataResult dataResult = new DataResult();
        dataResult.setData(exhibitionBoothVoList);
        return dataResult;
    }

    @Override
    public DataResult getExhibitionBoothDetail(Long exhibitionBoothId) {
        DataResult dataResult = new DataResult();
        dataResult.setData(exhibitionBoothMapper.getExhibitionBooth(exhibitionBoothId));
        return dataResult;
    }

    @Override
    public DataResult saveExhibitionCollection(ExhibitionCollection exhibitionCollection) {
        exhibitionCollectionMapper.saveExhibitionCollection(exhibitionCollection);
        DataResult dataResult = new DataResult();
        dataResult.setCode(ReturnStatusEnum.SUCCESS.getValue());
        dataResult.setMsg("收藏成功");
        return dataResult;
    }

    @Override
    public DataResult deleteExhibitionCollection(DeleteExhibitionCollectionParam deleteExhibitionCollectionParam) {
        List< String > collIdList = new ArrayList<>(Arrays.asList(deleteExhibitionCollectionParam.getCollIds()));
        exhibitionCollectionMapper.deleteExhibitionCollection(collIdList);
        DataResult dataResult = new DataResult();
        dataResult.setCode(ReturnStatusEnum.SUCCESS.getValue());
        dataResult.setMsg("取消收藏成功");
        return null;
    }

    @Override
    public ListDataResult listExhibitionCollection(String customerId, Integer pageNo, Integer pageSize) {
        Page< ExhibitionCollectionVo > exhibitionQueryPage = new Page<>(pageNo, pageSize);
        exhibitionQueryPage.setRecords(exhibitionCollectionMapper.listExhibitionCollection(customerId));
        ListDataResult listDataResult = new ListDataResult();
        listDataResult.setCurrent(exhibitionQueryPage.getCurrent());
        listDataResult.setTotal(exhibitionQueryPage.getTotal());
        listDataResult.setData(exhibitionQueryPage.getRecords());
        return listDataResult;
    }

    @Override
    public DataResult getExhibitionCollectDetail(Long collId) {
        DataResult dataResult = new DataResult();
        dataResult.setData(exhibitionCollectionMapper.getExhibitionCollectDetail(collId));
        return dataResult;
    }

    @Override
    public ListDataResult listExhibitionBoothPay(String customerId, Integer pageNo, Integer pageSize) {
        Page< ExhibitionBoothPayVo > exhibitionBoothPayVoPage = new Page<>(pageNo, pageSize);
        exhibitionBoothPayVoPage.setRecords(exhibitionBoothPayMapper.listExhibitionBoothPay(customerId));
        ListDataResult listDataResult = new ListDataResult();
        listDataResult.setCurrent(exhibitionBoothPayVoPage.getCurrent());
        listDataResult.setTotal(exhibitionBoothPayVoPage.getTotal());
        listDataResult.setData(exhibitionBoothPayVoPage.getRecords());
        return listDataResult;
    }

    @Override
    public DataResult getExhibitionPayDetail(Long payId) {
        DataResult dataResult = new DataResult();
        dataResult.setData(exhibitionBoothPayMapper.getExhibitionPayDetail(payId));
        return dataResult;
    }

}
