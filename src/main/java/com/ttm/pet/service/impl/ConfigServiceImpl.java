package com.ttm.pet.service.impl;

import com.ttm.pet.model.dto.Config;
import com.ttm.pet.dao.ConfigMapper;
import com.ttm.pet.service.ConfigService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统配置 服务实现类
 * </p>
 *
 * @author cx
 * @since 2020-05-25
 */
@Service
public class ConfigServiceImpl extends ServiceImpl<ConfigMapper, Config> implements ConfigService {

}
