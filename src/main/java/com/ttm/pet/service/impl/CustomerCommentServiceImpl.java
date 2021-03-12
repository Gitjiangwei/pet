package com.ttm.pet.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.ttm.pet.model.dto.CustomerComment;
import com.ttm.pet.dao.CustomerCommentMapper;
import com.ttm.pet.model.vo.app.CustomerCommentVo;
import com.ttm.pet.service.CustomerCommentService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户评论表; InnoDB free: 11264 kB 服务实现类
 * </p>
 *
 * @author cx
 * @since 2020-04-08
 */
@Service
public class CustomerCommentServiceImpl extends ServiceImpl<CustomerCommentMapper, CustomerComment> implements CustomerCommentService {

    @Autowired
    private CustomerCommentMapper customerCommentMapper;
    @Override
    public Page<CustomerCommentVo> findcustomerCommentVos(Page<CustomerCommentVo> page, Long worksId, String customerId) {
        List<CustomerCommentVo> customerCommentVos = customerCommentMapper.queryCustomerCommentVos(page,worksId,customerId);
        page.setRecords(customerCommentVos);
        return page;
    }
}
