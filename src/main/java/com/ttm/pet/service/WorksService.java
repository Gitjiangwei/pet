package com.ttm.pet.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.ttm.pet.model.dto.Works;
import com.baomidou.mybatisplus.service.IService;
import com.ttm.pet.model.vo.app.*;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.Map;

/**
 * <p>
 * 作品表; InnoDB free: 11264 kB 服务类
 * </p>
 *
 * @author cx
 * @since 2020-04-07
 */
public interface WorksService extends IService<Works> {
    //发布作品
    public int inserts (Works works);
    //查询首页推荐作品
    public Page<WorksDetailVo> findRecommendWorks(Page<WorksDetailVo> page,int recommendPoint,String customerId);
    //查询首页关注作品
    public Page<WorksFansVo> findFansWorksOuterVos(Page<WorksFansVo> page, String customerId);
    //查询首页同城作品
    public Page<WorksCityIndexVo> findWorksCityIndexVos(Page<WorksCityIndexVo> page, String customerId, Integer cityId, BigDecimal pX, BigDecimal pY);
    //查询首页最新作品
    public Page<WorksCityIndexVo> findWorksNewIndexVos(Page<WorksCityIndexVo> page, String customerId);
    //查询首页其他城市作品
    public Page<WorksCityIndexVo> findWorksOtherCityVos(Page<WorksCityIndexVo> page, String customerId, Integer cityId);
//    //查询助养领养作品
//    public Page<WorksOuterCityVo> findSupportWorksOuterCityVos(Page<WorksOuterCityVo> page, Integer cityId, Integer type, String name);

    //查询模块作品列表
    public Page<WorksOuterVo> findModuleWorkVos(Page<WorksOuterVo> page, int moduleId, int cityId);

    //小程序首页推荐作品
    public Page<WorksOuterVo> findMiniRecommendWorkVos(Page<WorksOuterVo> page, int recommendPoint);
    //小程序首页关注作品
    public Page<WorksOuterVo> findMiniFansWorksOuterVos(Page<WorksOuterVo> page, String customerId);
    //小程序首页同城作品
    public Page<WorksOuterCityVo> findMiniWorksOuterCityVos(Page<WorksOuterCityVo> page, Integer cityId, BigDecimal pX, BigDecimal pY);
    //小程序首页同城作品
    public Page<WorksOuterCityVo> findMiniWorksOuterNewVos(Page<WorksOuterCityVo> page);
    //小程序查询首页其他城市作品
    public Page<WorksOuterCityVo> findMiniWorksOtherCityVos(Page<WorksOuterCityVo> page, Integer cityId);
    //小程序查询助养领养作品
    public Page<WorksOuterCityVo> findMiniSupportWorksOuterCityVos(Page<WorksOuterCityVo> page, @Param("cityId") Integer cityId, @Param("type") Integer type, @Param("name") String name);


    //查询作品详情
    public WorksDetailVo findWorkDetailVo(Long worksId,String customerId);
    //查询用户作品
    public Page<WorksPersonalVo> findPersonalWorksVos(Page<WorksPersonalVo> page, String customerId);
    //查询用户喜欢的作品
    public Page<WorksPersonalVo> findLoverWorksVos(Page<WorksPersonalVo> page, String customerId);
    //查询打赏过的作品
    public Page<WorksPersonalVo> findRewardWorksVos(Page<WorksPersonalVo> page, String customerId);
    //查询领养人的作品
    public Page<WorksPersonalVo> findAdoptWorksVos(Page<WorksPersonalVo> page, String customerId);
    //查询用户喜欢的作品的个数
    public Integer findPointWorksCount(String customerId);

    //查询参赛总人数和总赞数
    public Map<String,Integer> findMatchCount();
    //查询自己排名
    public Map<String,Integer> findRankByCustomerId(String customerId);
    //查询参赛作品根据排名
    public Page<MatchWorksListVo> findMatchWorksListVos(Page<MatchWorksListVo> page, String customerId);
    //搜索用户名查询出该用户的参赛作品
    public Page<MatchWorksListVo> findMatchWorksListByNameVos(Page<MatchWorksListVo> page,String keyword,String customerId);
}
