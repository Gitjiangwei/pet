package com.ttm.pet.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.ttm.pet.model.dto.CustomerComment;
import com.baomidou.mybatisplus.service.IService;
import com.ttm.pet.model.vo.app.CustomerCommentVo;

/**
 * <p>
 * 用户评论表; InnoDB free: 11264 kB 服务类
 * </p>
 *
 * @author cx
 * @since 2020-04-08
 */
public interface CustomerCommentService extends IService<CustomerComment> {
    //查询作品评论
    public Page<CustomerCommentVo> findcustomerCommentVos(Page<CustomerCommentVo> page, Long worksId, String customerId);

}
