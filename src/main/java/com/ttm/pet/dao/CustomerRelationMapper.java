package com.ttm.pet.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.ttm.pet.model.dto.CustomerRelation;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ttm.pet.model.vo.app.AdoptListVo;
import com.ttm.pet.model.vo.app.FansVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户关系表 Mapper 接口
 * </p>
 *
 * @author cx
 * @since 2020-04-23
 */
public interface CustomerRelationMapper extends BaseMapper<CustomerRelation> {

    //查询我的关注列表
    public List<FansVo> queryAttentionList(Page<FansVo> page,@Param("customerId") String customerId);

    //查询我的粉丝列表
    public List<FansVo> queryFansList(Page<FansVo> page,@Param("customerId") String customerId);

    //查询领养人列表
    public List<AdoptListVo> queryAdoptListVo(Page<AdoptListVo> page,@Param("customerId") String customerId);
}
