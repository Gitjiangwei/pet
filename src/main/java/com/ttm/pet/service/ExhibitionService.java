package com.ttm.pet.service;

import com.ttm.pet.model.dto.DeleteExhibitionCollectionParam;
import com.ttm.pet.model.dto.ExhibitionCollection;
import com.ttm.pet.model.pojo.DataResult;
import com.ttm.pet.model.pojo.ListDataResult;

public interface ExhibitionService {

    ListDataResult listExhibition(Integer pageNo, Integer pageSize);

    DataResult listExhibitionBooth(Long exhibitionId);

    DataResult getExhibitionBoothDetail(Long exhibitionBoothId);

    /**
     * 新增展位收藏信息
     *
     * @param exhibitionCollection 新增参数
     * @return 新增成功标记
     * @author j
     * @date 2021/3/9
     */
    DataResult saveExhibitionCollection(ExhibitionCollection exhibitionCollection);

    /**
     * 删除收藏信息
     *
     * @param deleteExhibitionCollectionParam 删除参数
     * @return 删除成功标记
     * @author J
     * @date 2021/3/15
     */
    DataResult deleteExhibitionCollection(DeleteExhibitionCollectionParam deleteExhibitionCollectionParam);

    /**
     * 获取收藏的展位信息列表
     *
     * @param customerId 检索条件
     * @param pageNo 当前页
     * @param pageSize 每页显示条数
     * @return 展位列表信息
     * @author J
     * @date 2021/3/9
     */
    ListDataResult listExhibitionCollection(String customerId, Integer pageNo, Integer pageSize);

    /**
     * 获取收藏的展位信息详情
     *
     * @param collId 检索条件
     * @return 获取收藏的展位信息详情
     * @author J
     * @date 2021/3/9
     */
    DataResult getExhibitionCollectDetail(Long collId);

    /**
     * 获取我的展位订单列表
     *
     * @param customerId 下单人UUID
     * @param pageNo 当前页
     * @param pageSize 每页显示的条数
     * @return 我的订单展位信息
     * @author J
     * @date 2021/3/10
     */
    ListDataResult listExhibitionBoothPay(String customerId, Integer pageNo, Integer pageSize);

    /**
     * 获取我的展位订单详情信息
     *
     * @param payId 检索条件
     * @return 我的展位订单详情数据
     * @author J
     * @date 2021/3/10
     */
    DataResult getExhibitionPayDetail(Long payId);
}
