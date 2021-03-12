package com.ttm.pet.service.impl;

import com.ttm.pet.model.dto.Feedback;
import com.ttm.pet.dao.FeedbackMapper;
import com.ttm.pet.service.FeedbackService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 意见反馈表 服务实现类
 * </p>
 *
 * @author cx
 * @since 2020-10-12
 */
@Service
public class FeedbackServiceImpl extends ServiceImpl<FeedbackMapper, Feedback> implements FeedbackService {

}
