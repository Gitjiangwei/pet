package com.ttm.pet.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.ttm.pet.model.dto.CustomerComment;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ttm.pet.model.vo.app.CustomerCommentVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 用户评论表; InnoDB free: 11264 kB Mapper 接口
 * </p>
 *
 * @author cx
 * @since 2020-04-08
 */
public interface CustomerCommentMapper extends BaseMapper<CustomerComment> {
    //查询作品评论
    public List<CustomerCommentVo> queryCustomerCommentVos(Page<CustomerCommentVo> page,@Param("worksId") Long worksId,@Param("customerId")String customerId);

}
