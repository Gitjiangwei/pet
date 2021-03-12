package com.ttm.pet.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.ttm.pet.model.dto.Works;
import com.ttm.pet.dao.WorksMapper;
import com.ttm.pet.model.vo.app.*;
import com.ttm.pet.service.WorksService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 作品表; InnoDB free: 11264 kB 服务实现类
 * </p>
 *
 * @author cx
 * @since 2020-04-07
 */
@Service
public class WorksServiceImpl extends ServiceImpl<WorksMapper, Works> implements WorksService {

    @Autowired
    private WorksMapper worksMapper;
    @Override
    public int inserts(Works works) {
        return worksMapper.inserts(works);
    }

    @Override
    public Page<WorksDetailVo> findRecommendWorks(Page<WorksDetailVo> page, int recommendPoint, String customerId) {
        List<WorksDetailVo> worksDetailVos = worksMapper.queryRecommendWorks(page,recommendPoint,customerId);
        page.setRecords(worksDetailVos);
        return page;
    }

    @Override
    public Page<WorksFansVo> findFansWorksOuterVos(Page<WorksFansVo> page, String customerId) {
        List<WorksFansVo> worksOuterVos = worksMapper.queryFansWorkOuterVos(page,customerId);
        page.setRecords(worksOuterVos);
        return page;
    }

    @Override
    public Page<WorksCityIndexVo> findWorksCityIndexVos(Page<WorksCityIndexVo> page, String customerId, Integer cityId, BigDecimal pX, BigDecimal pY) {
        List<WorksCityIndexVo> worksCityIndexVos = worksMapper.queryCityWorkOuterVos(page,customerId,cityId,pX,pY);
        page.setRecords(worksCityIndexVos);
        return page;
    }

    @Override
    public Page<WorksCityIndexVo> findWorksNewIndexVos(Page<WorksCityIndexVo> page, String customerId) {
        List<WorksCityIndexVo> worksCityIndexVos = worksMapper.queryWorksNewIndexVos(page,customerId);
        page.setRecords(worksCityIndexVos);
        return page;
    }

    @Override
    public Page<WorksCityIndexVo> findWorksOtherCityVos(Page<WorksCityIndexVo> page, String customerId, Integer cityId) {
        List<WorksCityIndexVo> worksOtherCityIndexVos = worksMapper.queryWorksOtherCityVos(page,customerId,cityId);
        page.setRecords(worksOtherCityIndexVos);
        return page;
    }

    @Override
    public Page<WorksOuterVo> findModuleWorkVos(Page<WorksOuterVo> page, int moduleId, int cityId) {
        List<WorksOuterVo> moduleWorksVos = worksMapper.queryModuleWorkVos(page,moduleId,cityId);
        page.setRecords(moduleWorksVos);
        return page;
    }

    @Override
    public Page<WorksOuterVo> findMiniRecommendWorkVos(Page<WorksOuterVo> page, int recommendPoint) {
        List<WorksOuterVo> worksOuterVos = worksMapper.queryMiniRecommendWorkVos(page,recommendPoint);
        page.setRecords(worksOuterVos);
        return page;
    }

    @Override
    public Page<WorksOuterVo> findMiniFansWorksOuterVos(Page<WorksOuterVo> page, String customerId) {
        List<WorksOuterVo> worksOuterVos = worksMapper.queryMiniFansWorkOuterVos(page,customerId);
        page.setRecords(worksOuterVos);
        return page;
    }

    @Override
    public Page<WorksOuterCityVo> findMiniWorksOuterCityVos(Page<WorksOuterCityVo> page, Integer cityId, BigDecimal pX, BigDecimal pY) {
        List<WorksOuterCityVo> worksCityIndexVos = worksMapper.queryMiniWorksOuterCityVos(page,cityId,pX,pY);
        page.setRecords(worksCityIndexVos);
        return page;
    }

    @Override
    public Page<WorksOuterCityVo> findMiniWorksOuterNewVos(Page<WorksOuterCityVo> page) {
        List<WorksOuterCityVo> worksCityIndexVos = worksMapper.queryMiniWorksNewIndexVos(page);
        page.setRecords(worksCityIndexVos);
        return page;
    }

    @Override
    public Page<WorksOuterCityVo> findMiniWorksOtherCityVos(Page<WorksOuterCityVo> page, Integer cityId) {
        List<WorksOuterCityVo> worksCityIndexVos = worksMapper.queryMiniWorksOtherCityVos(page,cityId);
        page.setRecords(worksCityIndexVos);
        return page;
    }

    @Override
    public Page<WorksOuterCityVo> findMiniSupportWorksOuterCityVos(Page<WorksOuterCityVo> page, Integer cityId, Integer type, String name) {
        List<WorksOuterCityVo> worksCityIndexVos = worksMapper.queryMiniSupportWorksOuterCityVos(page,cityId,type,name);
        page.setRecords(worksCityIndexVos);
        return page;
    }

    @Override
    public WorksDetailVo findWorkDetailVo(Long worksId, String customerId) {
        return worksMapper.queryWorkDetailVo(worksId,customerId);
    }

    @Override
    public Page<WorksPersonalVo> findPersonalWorksVos(Page<WorksPersonalVo> page, String customerId) {
        List<WorksPersonalVo> worksPersonalVos = worksMapper.queryPersonalWorksVos(page,customerId);
        for (WorksPersonalVo worksPersonalVo : worksPersonalVos){
            System.out.println(worksPersonalVo.getId());
            System.out.println(worksPersonalVo.getFirstImg());

        }
        page.setRecords(worksPersonalVos);
        return page;
    }

    @Override
    public Page<WorksPersonalVo> findLoverWorksVos(Page<WorksPersonalVo> page, String customerId) {
        List<WorksPersonalVo> worksPersonalVos = worksMapper.queryLoverWorksVos(page,customerId);
        page.setRecords(worksPersonalVos);
        return page;
    }

    @Override
    public Page<WorksPersonalVo> findRewardWorksVos(Page<WorksPersonalVo> page, String customerId) {
        List<WorksPersonalVo> worksPersonalVos = worksMapper.queryRewardWorksVos(page,customerId);
        page.setRecords(worksPersonalVos);
        return page;
    }

    @Override
    public Page<WorksPersonalVo> findAdoptWorksVos(Page<WorksPersonalVo> page, String customerId) {
        List<WorksPersonalVo> worksPersonalVos = worksMapper.queryAdoptWorksVos(page,customerId);
        page.setRecords(worksPersonalVos);
        return page;
    }

    @Override
    public Integer findPointWorksCount(String customerId) {
        return worksMapper.queryPointWorksCount(customerId);
    }

    @Override
    public Map<String, Integer> findMatchCount() {
        return worksMapper.queryMatchCount();
    }

    @Override
    public Map<String, Integer> findRankByCustomerId(String customerId) {
        return worksMapper.queryRankByCustomerId(customerId);
    }

    @Override
    public Page<MatchWorksListVo> findMatchWorksListVos(Page<MatchWorksListVo> page, String customerId) {
        List<MatchWorksListVo> matchWorksListVos = worksMapper.queryMatchWorksListVos(page,customerId);
        page.setRecords(matchWorksListVos);
        return page;
    }

    @Override
    public Page<MatchWorksListVo> findMatchWorksListByNameVos(Page<MatchWorksListVo> page, String keyword, String customerId) {
        List<MatchWorksListVo> matchWorksListVos = worksMapper.queryMatchWorksListByNameVos(page,keyword,customerId);
        page.setRecords(matchWorksListVos);
        return page;
    }
}
