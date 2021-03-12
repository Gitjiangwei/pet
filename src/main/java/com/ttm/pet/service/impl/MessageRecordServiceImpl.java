package com.ttm.pet.service.impl;

import com.ttm.pet.model.dto.MessageRecord;
import com.ttm.pet.dao.MessageRecordMapper;
import com.ttm.pet.service.MessageRecordService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 消息业务冗余表 服务实现类
 * </p>
 *
 * @author cx
 * @since 2020-06-22
 */
@Service
public class MessageRecordServiceImpl extends ServiceImpl<MessageRecordMapper, MessageRecord> implements MessageRecordService {

}
