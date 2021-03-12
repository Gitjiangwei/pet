package com.ttm.pet.dao;

import com.ttm.pet.model.vo.app.ExhibitionBoothDetailVo;
import com.ttm.pet.model.vo.app.ExhibitionBoothVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExhibitionBoothMapper {

    List<ExhibitionBoothVo> listExhibitionBooth(@Param("exhibitionId") Long exhibitionId);

    ExhibitionBoothDetailVo getExhibitionBooth(@Param("exhibitionBoothId") Long exhibitionBoothId);

    /**
     * 修改展位是否出售
     *
     * @param boothId 修改参数
     * @return 修改成功返回的行数
     * @author J
     * @date 2021/3/10
     */
    Integer updateBoothStatus(@Param("boothId") Long boothId);

    /**
     * 获取展位是否出售
     *
     * @param boothId 检索条件
     * @return 展位是否出售 1：已出售 2：未出售
     * @author J
     * @date 2021/3/10
     */
    String getBoothStatus(@Param("boothId") Long boothId);
}
