package com.ttm.pet.dao;

import com.baomidou.mybatisplus.plugins.Page;
import com.ttm.pet.model.dto.Works;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.ttm.pet.model.vo.app.*;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 作品表; InnoDB free: 11264 kB Mapper 接口
 * </p>
 *
 * @author cx
 * @since 2020-04-07
 */
public interface WorksMapper extends BaseMapper<Works> {
    //发布作品
    public int inserts (Works works);
    //查看首页推荐作品
    public List<WorksDetailVo> queryRecommendWorks(Page<WorksDetailVo> page,@Param("recommendPoint") int recommendPoint,@Param("customerId") String customerId);
    //查询首页关注作品
    public List<WorksFansVo> queryFansWorkOuterVos(Page<WorksFansVo> page,@Param("customerId") String customerId);
    //查询首页同城作品
    public List<WorksCityIndexVo> queryCityWorkOuterVos(Page<WorksCityIndexVo> page,@Param("customerId") String customerId, @Param("cityId") Integer cityId, @Param("pX") BigDecimal pX, @Param("pY") BigDecimal pY);
    //查询首页最新作品
    public List<WorksCityIndexVo> queryWorksNewIndexVos(Page<WorksCityIndexVo> page, @Param("customerId") String customerId);
    //查询首页其他城市作品
    public List<WorksCityIndexVo> queryWorksOtherCityVos(Page<WorksCityIndexVo> page, @Param("customerId") String customerId, @Param("cityId") Integer cityId);
//    //查询助养领养作品
//    public List<WorksOuterCityVo> querySupportWorksOuterCityVos(Page<WorksOuterCityVo> page, @Param("cityId") Integer cityId, @Param("type") Integer type, @Param("name") String name);
    //小程序首页推荐作品
    public List<WorksOuterVo> queryModuleWorkVos(Page<WorksOuterVo> page,@Param("moduleId") int moduleId,@Param("cityId") int cityId);

    //小程序首页推荐作品
    public List<WorksOuterVo> queryMiniRecommendWorkVos(Page<WorksOuterVo> page,@Param("recommendPoint") int recommendPoint);
    //小程序首页关注作品
    public List<WorksOuterVo> queryMiniFansWorkOuterVos(Page<WorksOuterVo> page,@Param("customerId") String customerId);
    //小程序首页同城作品
    public List<WorksOuterCityVo> queryMiniWorksOuterCityVos(Page<WorksOuterCityVo> page, @Param("cityId") Integer cityId, @Param("pX") BigDecimal pX, @Param("pY") BigDecimal pY);
    //小程序查询首页最新作品
    public List<WorksOuterCityVo> queryMiniWorksNewIndexVos(Page<WorksOuterCityVo> page);
    //小程序查询首页其他城市作品
    public List<WorksOuterCityVo> queryMiniWorksOtherCityVos(Page<WorksOuterCityVo> page, @Param("cityId") Integer cityId);

    //小程序查询助养领养作品
    public List<WorksOuterCityVo> queryMiniSupportWorksOuterCityVos(Page<WorksOuterCityVo> page, @Param("cityId") Integer cityId, @Param("type") Integer type, @Param("name") String name);


    //查询作品详情
    public WorksDetailVo queryWorkDetailVo(@Param("worksId") Long worksId,@Param("customerId") String customerId);
    //查询用户作品
    public List<WorksPersonalVo> queryPersonalWorksVos(Page<WorksPersonalVo> page, @Param("customerId") String customerId);
    //查询用户喜欢的作品
    public List<WorksPersonalVo> queryLoverWorksVos(Page<WorksPersonalVo> page,@Param("customerId") String customerId);
    //查询打赏过的作品
    public List<WorksPersonalVo> queryRewardWorksVos(Page<WorksPersonalVo> page,@Param("customerId") String customerId);
    //查询领养人的作品
    public List<WorksPersonalVo> queryAdoptWorksVos(Page<WorksPersonalVo> page,@Param("customerId") String customerId);
    //查询用户喜欢的作品的个数
    public Integer queryPointWorksCount(@Param("customerId") String customerId);

    //查询参赛总人数和总赞数
    public Map<String,Integer> queryMatchCount();
    //查询自己排名
    public Map<String,Integer> queryRankByCustomerId(@Param("customerId") String customerId);
    //查询参赛作品根据排名
    public List<MatchWorksListVo> queryMatchWorksListVos(Page<MatchWorksListVo> page,@Param("customerId") String customerId);

    //搜索用户名查询出该用户的参赛作品
    public List<MatchWorksListVo> queryMatchWorksListByNameVos(Page<MatchWorksListVo> page,@Param("keyword") String keyword,@Param("customerId") String customerId);
}
