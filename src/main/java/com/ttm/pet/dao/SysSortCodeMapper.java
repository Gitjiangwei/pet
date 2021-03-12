package com.ttm.pet.dao;

import com.ttm.pet.model.vo.app.SysSortCodeVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 统一编码持久层
 *
 * @author J
 * @date 2021/3/9
 */
public interface SysSortCodeMapper {
    /**
     * 获取系统统一编码列表
     *
     * @param codeSortId 检索条件
     * @return 编码列表
     * @author J
     * @date 2021/3/9
     */
    List< SysSortCodeVo > listSysSortCode(@Param("codeSortId") String codeSortId);
}
