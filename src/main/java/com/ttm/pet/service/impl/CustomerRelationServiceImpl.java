package com.ttm.pet.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.ttm.pet.model.dto.CustomerRelation;
import com.ttm.pet.dao.CustomerRelationMapper;
import com.ttm.pet.model.vo.app.AdoptListVo;
import com.ttm.pet.model.vo.app.FansVo;
import com.ttm.pet.service.CustomerRelationService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 用户关系表 服务实现类
 * </p>
 *
 * @author cx
 * @since 2020-04-23
 */
@Service
public class CustomerRelationServiceImpl extends ServiceImpl<CustomerRelationMapper, CustomerRelation> implements CustomerRelationService {

    @Autowired
    private CustomerRelationMapper customerRelationMapper;

    @Override
    public Page<FansVo> findFansVo(Page<FansVo> page,String customerId, Integer type){
        if (type == 1){
            List<FansVo> fansVos = customerRelationMapper.queryAttentionList(page,customerId);
            page.setRecords(fansVos);
            return page;
        }else if (type == 2){
            List<FansVo> fansVos = customerRelationMapper.queryFansList(page,customerId);
            page.setRecords(fansVos);
            return page;
        }else {
            return null;
        }
    }

    @Override
    public Page<AdoptListVo> findAdoptListVo(Page<AdoptListVo> page, String customerId) {
        List<AdoptListVo> adoptListVos = customerRelationMapper.queryAdoptListVo(page,customerId);
        page.setRecords(adoptListVos);
        return page;
    }
}
