package com.ttm.pet.service.impl;

import com.ttm.pet.model.dto.Contact;
import com.ttm.pet.dao.ContactMapper;
import com.ttm.pet.service.ContactService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author cx
 * @since 2020-10-12
 */
@Service
public class ContactServiceImpl extends ServiceImpl<ContactMapper, Contact> implements ContactService {

}
