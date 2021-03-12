package com.ttm.pet.dao;

import com.ttm.pet.model.dto.ExhibitionBoothPayParam;
import com.ttm.pet.model.vo.app.ExhibitionBoothPayVo;
import com.ttm.pet.model.vo.app.ExhibitionPayDetailVo;
import com.ttm.pet.model.vo.app.ExhibitionPayVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 展位支付持久层接口
 *
 * @author J
 * @date 2021/3/10
 */
public interface ExhibitionBoothPayMapper {

    /**
     * 创建订单
     *
     * @param exhibitionBoothPayParam 新增参数
     * @return 新增成功返回的行数
     * @author J
     * @date 2021/3/10
     */
    Integer saveExhibitionBoothPay(ExhibitionBoothPayParam exhibitionBoothPayParam);

    /**
     * 修改订单状态
     *
     * @param outTradeNo 修改参数
     * @return 修改成功后返回的行数
     * @author J
     * @date 2021/3/10
     */
    Integer updateExhibitionBoothPay(@Param("outTradeNo") String outTradeNo);

    /**
     * 获取展位id
     *
     * @param outTradeNo 检索条件
     * @return 展位id
     * @author J
     * @date 2021/3/10
     */
    ExhibitionPayVo getBoothId(@Param("outTradeNo") String outTradeNo);

    /**
     * 我的订单列表
     *
     * @param customerId 检索条件
     * @return 我的订单列表
     * @author J
     * @date 2021/3/10
     */
    List< ExhibitionBoothPayVo > listExhibitionBoothPay(@Param("customerId") String customerId);

    /**
     * 获取我的展位订单详情
     *
     * @param payId
     * @return
     * @author 姜伟
     * @date 2021/3/10
     */
    ExhibitionPayDetailVo getExhibitionPayDetail(@Param("payId") Long payId);
}
