package com.ttm.pet.dao;

import com.ttm.pet.model.dto.ExhibitionCollection;
import com.ttm.pet.model.vo.app.ExhibitionCollectionDetailVo;
import com.ttm.pet.model.vo.app.ExhibitionCollectionVo;
import com.ttm.pet.model.vo.app.ExhibitionPayVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 展会收藏持久层
 *
 * @author J
 * @date 2021/3/9
 */
public interface ExhibitionCollectionMapper {

    /**
     * 新增收藏展位信息
     *
     * @param exhibitionCollection 新增参数
     * @return 返回新增的行数
     * @author J
     * @date 2021/3/9
     */
    Integer saveExhibitionCollection(ExhibitionCollection exhibitionCollection);

    /**
     * 获取收藏的展位信息列表
     *
     * @param customerId 检索条件
     * @return 展位信息列表
     * @author J
     * @date 2021/3/9
     */
    List< ExhibitionCollectionVo > listExhibitionCollection(@Param("customerId") String customerId);
    
    /**
     * 获取收藏的展位信息详情
     *
     * @param collId 检索条件
     * @return 收藏的展位信息详情
     * @author J
     * @date 2021/3/9 
     */
    ExhibitionCollectionDetailVo getExhibitionCollectDetail(@Param("collId") Long collId);

    /**
     * 修改收藏的展位支付状态
     *
     * @param exhibitionPayVo 修改参数
     * @return 修改成功返回的行数
     * @author J
     * @date 2021/3/10
     */
    int updateBoothPayStatus(ExhibitionPayVo exhibitionPayVo);
}
