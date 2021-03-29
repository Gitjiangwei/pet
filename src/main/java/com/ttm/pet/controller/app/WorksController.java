package com.ttm.pet.controller.app;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ttm.pet.enums.ConstantsEnum;
import com.ttm.pet.enums.ReturnStatusEnum;
import com.ttm.pet.model.dto.*;
import com.ttm.pet.model.pojo.DataResult;
import com.ttm.pet.model.pojo.ListDataResult;
import com.ttm.pet.model.query.admin.WorksQuery;
import com.ttm.pet.model.vo.app.*;
import com.ttm.pet.service.*;
import com.ttm.pet.util.BadWordUtil2;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * <p>
 * 作品表; InnoDB free: 11264 kB 前端控制器
 * </p>
 *
 * @author cx
 * @since 2020-04-07
 */
@RestController
@Api(value ="/app/works",tags = "app-works",description = "作品相关")
@RequestMapping("/app/works")
public class WorksController {
    private final static Logger logger = LoggerFactory.getLogger(WorksController.class);

    @Autowired
    private WorksService worksService;
    @Autowired
    private CustomerCommentService customerCommentService;
    @Autowired
    private ConfigService configService;
    @Autowired
    private CustomerPointService customerPointService;
    @Autowired
    private MatchPointService matchPointService;
    @Autowired
    private SystemImgService systemImgService;
    @Autowired
    private CityService cityService;
    @Autowired
    private HistoryMatchService historyMatchService;
    @Autowired
    private CustomerDrawService customerDrawService;
    @Autowired
    private CustomerService customerService;

    /**
     * 作品发布
     * @param worksQuery
     * @return
     */
    @ApiOperation(httpMethod = "POST", value = "作品发布", nickname = "1")
    @RequestMapping(value = "/works" ,method = RequestMethod.POST)
    private DataResult getUsers(@RequestBody WorksQuery worksQuery){
        DataResult result = new DataResult();
        try {
            Works works = new Works();
            Customer customer = customerService.selectOne(new EntityWrapper<Customer>().eq("uuid",worksQuery.getCustomerId()));
            if (customer.getModuleId() != null){
                works.setModuleId(customer.getModuleId());
            }
            String content = worksQuery.getTitle()+worksQuery.getDescribe();
            Set<String> set = BadWordUtil2.getBadWord(content, 2);
            if(set.size() > 0){
                return new DataResult(ReturnStatusEnum.ILLEGAL_PARAM_ERROR.getValue(),"您发布的内容包含敏感词，请检查后发布!",null);
            }

            if (worksQuery.getIsMatch() == 1){
                //查询这个用户有没有其他参赛作品
                int count = worksService.selectCount(new EntityWrapper<Works>().eq("customer_id",worksQuery.getCustomerId()).eq("is_match",1).eq("deleted",0));
                if (count >= 1){
                    result.setCode(ReturnStatusEnum.SYS_ERROR.getValue());
                    result.setMsg("您已经有参赛作品了!如需发布参赛作品，请删除原参赛作品");
                    return result;
                }
            }
            BeanUtils.copyProperties(worksQuery,works);
            worksService.insert(works);
            return result;
        } catch (Exception e) {
            logger.error("作品发布失败:{}", e);
            result.setCode(ReturnStatusEnum.SYS_ERROR.getValue());
            result.setMsg("作品发布失败");
            return result;
        }
    }

    /**
     * 删除发布
     * @return
     */
    @ApiOperation(httpMethod = "DELETE", value = "删除作品",notes = "作品id  只有自己的作品才可以看到删除选项",nickname = "2")
    @RequestMapping(value = "/works/{id}" ,method = RequestMethod.DELETE)
    private DataResult deleteWorks(@PathVariable(value = "id") Long id){
        DataResult result = new DataResult();
        try {
            //先查询这个作品是不是比赛作品，如果是比赛判断目前有没有比赛，删除这个人 该场比赛的抽奖
            Works works = worksService.selectById(id);
            if (works != null){
                if (works.getIsMatch() == 1){
                    //查询现在有没有比赛
                    HistoryMatch historyMatch = historyMatchService.selectOne(new EntityWrapper<HistoryMatch>().eq("finished",0).gt("draw_end_time",new Date()));
                    if (historyMatch != null){
                        Integer matchId = historyMatch.getId();
                        customerDrawService.delete(new EntityWrapper<CustomerDraw>().eq("customer_id",works.getCustomerId()).eq("match_id",matchId));
                    }
                }
                works.setId(id);
                works.setDeleted(ConstantsEnum.DELETED.getCode());
                worksService.updateById(works);
                result.setMsg("删除成功！");
            }else {
                result.setCode(ReturnStatusEnum.ILLEGAL_PARAM_ERROR.getValue());
                result.setMsg("没有该作品");
            }
            return result;
        }catch (Exception e){
            logger.error("删除作品id:" + id+",失败："+e.getMessage());
            result.setCode(ReturnStatusEnum.SYS_ERROR.getValue());
            result.setMsg("删除作品失败");
            return result;
        }
    }

    /**
     * 首页推荐作品
     * 推荐作品确定为 1后台设为推荐 或者 2点赞量达到设定值
     * @return WorksDetailVo
     */
    @ApiOperation(httpMethod = "GET", tags = "app-works", value = "首页推荐作品",nickname = "3")
    @RequestMapping(value = "/recommendedWorks" ,method = RequestMethod.GET)
    private ListDataResult getRecommendedWorks(@ApiParam(value = "页数", required = true) @RequestParam("page") Integer page,
                                               @ApiParam(value = "每页个数", required = true) @RequestParam("size") Integer size,
                                               @ApiParam(value = "用户id(登陆后要传)", required = false) @RequestParam(value = "customerId",required = false) String customerId){
        ListDataResult result = new ListDataResult();
        try {
            Config config = configService.selectOne(new EntityWrapper<Config>().eq("name","recommend_point"));
            int recommendPoint = config.getValue().intValue();
            Page<WorksDetailVo> worksDetailVoPage = new Page<>(page,size);
            worksDetailVoPage = worksService.findRecommendWorks(worksDetailVoPage,recommendPoint,customerId);
            result.setCurrent(worksDetailVoPage.getCurrent());
            result.setTotal(worksDetailVoPage.getTotal());
            result.setData(worksDetailVoPage.getRecords());
        } catch (Exception e) {
            logger.error("推荐作品查询失败:{}", e);
            result.setCode(ReturnStatusEnum.SYS_ERROR.getValue());
            result.setMsg("推荐作品查询失败");
        }
        return result;
    }

    /**
     * 首页关注作品
     * @return
     */
    @ApiOperation(httpMethod = "GET", tags = "app-works", value = "首页关注作品",nickname = "4")
    @RequestMapping(value = "/fansWorks" ,method = RequestMethod.GET)
    private ListDataResult getFansWorks(@ApiParam(value = "页数", required = true) @RequestParam("page") Integer page,
                                        @ApiParam(value = "每页个数", required = true) @RequestParam("size") Integer size,
                                        @ApiParam(value = "用户id", required = true) @RequestParam(value = "customerId") String customerId){
        ListDataResult result = new ListDataResult();
        try {
            Page<WorksFansVo> worksOuterVoPage = new Page<>(page,size);
            worksOuterVoPage = worksService.findFansWorksOuterVos(worksOuterVoPage,customerId);
            result.setCurrent(worksOuterVoPage.getCurrent());
            result.setTotal(worksOuterVoPage.getTotal());
            result.setData(worksOuterVoPage.getRecords());
        } catch (Exception e) {
            logger.error("关注作品查询失败:{}", e);
            result.setCode(ReturnStatusEnum.SYS_ERROR.getValue());
            result.setMsg("关注作品查询失败");
        }
        return result;
    }
    /**
     * 首页同城作品
     * 同城作品确定为 1同一个城市 或者 2合作关系发布的作品
     * @return
     */
    @ApiOperation(httpMethod = "GET", tags = "app-works", value = "首页同城作品",notes = "需求是查出同一城市 或者 合作关系发布的作品（不同城也可以查出来） ----isPartnership为1是合作关系用户，展示location就行，不用展示距离",nickname = "5")
    @RequestMapping(value = "/cityWorks" ,method = RequestMethod.GET)
    private ListDataResult getCityWorks(@ApiParam(value = "页数", required = true) @RequestParam("page") Integer page,
                                        @ApiParam(value = "每页个数", required = true) @RequestParam("size") Integer size,
                                        @ApiParam(value = "城市id", required = true) @RequestParam(value = "cityId") Integer cityId,
                                        @ApiParam(value = "纬度", required = true) @RequestParam(value = "pX") BigDecimal pX,
                                        @ApiParam(value = "经度", required = true) @RequestParam(value = "pY") BigDecimal pY,
                                        @ApiParam(value = "用户id(登陆后要传)", required = false) @RequestParam(value = "customerId",required = false) String customerId){
        ListDataResult result = new ListDataResult();
        try {
            Page<WorksCityIndexVo> worksCityIndexVoPage = new Page<>(page,size);
            worksCityIndexVoPage = worksService.findWorksCityIndexVos(worksCityIndexVoPage,customerId,cityId,pX,pY);
            result.setCurrent(worksCityIndexVoPage.getCurrent());
            result.setTotal(worksCityIndexVoPage.getTotal());
            result.setData(worksCityIndexVoPage.getRecords());
        } catch (Exception e) {
            logger.error("首页同城作品查询失败:{}", e);
            result.setCode(ReturnStatusEnum.SYS_ERROR.getValue());
            result.setMsg("同城作品查询失败");
        }
        return result;
    }

    /**
     * 首页最新作品
     * 推荐或同城刷完之后展示最新作品
     * @return
     */
    @ApiOperation(httpMethod = "GET", tags = "app-works", value = "首页最新作品",notes = "时间排序最新作品",nickname = "5")
    @RequestMapping(value = "/newWorks" ,method = RequestMethod.GET)
    private ListDataResult getnewWorks(@ApiParam(value = "页数", required = true) @RequestParam("page") Integer page,
                                        @ApiParam(value = "每页个数", required = true) @RequestParam("size") Integer size,
                                        @ApiParam(value = "用户id(登陆后要传)", required = false) @RequestParam(value = "customerId",required = false) String customerId){
        ListDataResult result = new ListDataResult();
        try {
            Page<WorksCityIndexVo> worksCityIndexVoPage = new Page<>(page,size);
            worksCityIndexVoPage = worksService.findWorksNewIndexVos(worksCityIndexVoPage,customerId);
            result.setCurrent(worksCityIndexVoPage.getCurrent());
            result.setTotal(worksCityIndexVoPage.getTotal());
            result.setData(worksCityIndexVoPage.getRecords());
        } catch (Exception e) {
            logger.error("首页最新作品查询失败:{}", e);
            result.setCode(ReturnStatusEnum.SYS_ERROR.getValue());
            result.setMsg("最新作品查询失败");
        }
        return result;
    }

    /**
     * 首页其他城市作品
     * 推荐或同城刷完之后展示最新作品
     * @return
     */
    @ApiOperation(httpMethod = "GET", tags = "app-works", value = "首页其他城市作品",notes = "除所传id外其他城市作品",nickname = "5")
    @RequestMapping(value = "/otherCityWorks" ,method = RequestMethod.GET)
    private ListDataResult getOtherCityWorks(@ApiParam(value = "页数", required = true) @RequestParam("page") Integer page,
                                             @ApiParam(value = "每页个数", required = true) @RequestParam("size") Integer size,
                                             @ApiParam(value = "城市id", required = true) @RequestParam("cityId") Integer cityId,
                                             @ApiParam(value = "用户id(登陆后要传)", required = false) @RequestParam(value = "customerId",required = false) String customerId){
        ListDataResult result = new ListDataResult();
        try {
            Page<WorksCityIndexVo> worksCityIndexVoPage = new Page<>(page,size);
            worksCityIndexVoPage = worksService.findWorksOtherCityVos(worksCityIndexVoPage,customerId,cityId);
            result.setCurrent(worksCityIndexVoPage.getCurrent());
            result.setTotal(worksCityIndexVoPage.getTotal());
            result.setData(worksCityIndexVoPage.getRecords());
        } catch (Exception e) {
            logger.error("首页其他城市作品失败:{}", e);
            result.setCode(ReturnStatusEnum.SYS_ERROR.getValue());
            result.setMsg("其他城市作品失败");
        }
        return result;
    }


    /**
     * 小程序首页关注/推荐/同城作品
     * @return
     */
    @ApiOperation(httpMethod = "GET", tags = "app-works", value = "小程序首页关注/推荐/同城/其他城市作品",notes = "关注:type=1  推荐:type=2  同城:type=3 最新：type=4 其他城市:type=5 ",nickname = "6")
    @RequestMapping(value = "/indexWorks" ,method = RequestMethod.GET)
    private ListDataResult getIndexWorks(@ApiParam(value = "页数", required = true) @RequestParam("page") Integer page,
                                         @ApiParam(value = "每页个数", required = true) @RequestParam("size") Integer size,
                                         @ApiParam(value = "类型--关注：1 推荐：2 同城：3 其他城市：5", required = true) @RequestParam("type") Integer type,
                                         @ApiParam(value = "用户id(关注必传)", required = false) @RequestParam(value = "customerId",required = false) String customerId,
                                         @ApiParam(value = "城市id (同城时必传)", required = false) @RequestParam(value = "cityId",required = false) Integer cityId,
                                         @ApiParam(value = "纬度 (同城时必传)", required = false) @RequestParam(value = "pX",required = false) BigDecimal pX,
                                         @ApiParam(value = "经度 (同城时必传)", required = false) @RequestParam(value = "pY",required = false) BigDecimal pY){
        ListDataResult result = new ListDataResult();
        try {

            if(type == 1){
                Page<WorksOuterVo> worksOuterVoPage = new Page<>(page,size);
                if(customerId == null || customerId.equals("")){
                    result.setMsg("用户id必传");
                    return result;
                }
                worksOuterVoPage = worksService.findMiniFansWorksOuterVos(worksOuterVoPage,customerId);
                result.setCurrent(worksOuterVoPage.getCurrent());
                result.setTotal(worksOuterVoPage.getTotal());
                result.setData(worksOuterVoPage.getRecords());
            }else if (type == 2){
                Page<WorksOuterVo> worksOuterVoPage = new Page<>(page,size);
                Config config = configService.selectOne(new EntityWrapper<Config>().eq("name","recommend_point"));
                int recommendPoint = config.getValue().intValue();
                worksOuterVoPage = worksService.findMiniRecommendWorkVos(worksOuterVoPage,recommendPoint);
                result.setCurrent(worksOuterVoPage.getCurrent());
                result.setTotal(worksOuterVoPage.getTotal());
                result.setData(worksOuterVoPage.getRecords());
            }else if (type == 3){
                Page<WorksOuterCityVo> worksOuterCityVoPage = new Page<>(page,size);
                worksOuterCityVoPage = worksService.findMiniWorksOuterCityVos(worksOuterCityVoPage,cityId,pX,pY);
                result.setCurrent(worksOuterCityVoPage.getCurrent());
                result.setTotal(worksOuterCityVoPage.getTotal());
                result.setData(worksOuterCityVoPage.getRecords());
            }else if (type == 4){
                Page<WorksOuterCityVo> worksOuterCityVoPage = new Page<>(page,size);
                worksOuterCityVoPage = worksService.findMiniWorksOuterNewVos(worksOuterCityVoPage);
                result.setCurrent(worksOuterCityVoPage.getCurrent());
                result.setTotal(worksOuterCityVoPage.getTotal());
                result.setData(worksOuterCityVoPage.getRecords());
            }else if (type == 5){
                Page<WorksOuterCityVo> worksOuterCityVoPage = new Page<>(page,size);
                worksOuterCityVoPage = worksService.findMiniWorksOtherCityVos(worksOuterCityVoPage,cityId);
                result.setCurrent(worksOuterCityVoPage.getCurrent());
                result.setTotal(worksOuterCityVoPage.getTotal());
                result.setData(worksOuterCityVoPage.getRecords());
            }else {
                result.setMsg("参数类型错误");
                return result;
            }
            return result;
        } catch (Exception e) {
            logger.error("首页同城作品查询失败:{}", e);
            result.setCode(ReturnStatusEnum.SYS_ERROR.getValue());
            result.setMsg("同城作品查询失败");
            return result;
        }
    }
    /**
     * 小程序 领养 助养作品
     * @return
     */
    @ApiOperation(httpMethod = "GET", tags = "app-works", value = "小程序 领养/助养作品",notes = "2-领养  3-助养  查全国的话不传cityId",nickname = "7")
    @RequestMapping(value = "/adoptWorks" ,method = RequestMethod.GET)
    private ListDataResult getAdoptWorks(@ApiParam(value = "页数", required = true) @RequestParam("page") Integer page,
                                         @ApiParam(value = "每页个数", required = true) @RequestParam("size") Integer size,
                                         @ApiParam(value = "type 2-领养  3-助养", required = true) @RequestParam("type") Integer type,
                                         @ApiParam(value = "城市id,全国不传", required = false) @RequestParam(value = "cityId",required = false) Integer cityId,
                                         @ApiParam(value = "救助站名称", required = false) @RequestParam(value = "name",required = false) String name){
        ListDataResult result = new ListDataResult();
        try {
            Page<WorksOuterCityVo> worksCityIndexVoPage = new Page<>(page,size);
            worksCityIndexVoPage = worksService.findMiniSupportWorksOuterCityVos(worksCityIndexVoPage,cityId,type,name);
            result.setCurrent(worksCityIndexVoPage.getCurrent());
            result.setTotal(worksCityIndexVoPage.getTotal());
            result.setData(worksCityIndexVoPage.getRecords());
        } catch (Exception e) {
            logger.error("首页领养/助养作品失败:{}", e);
            result.setCode(ReturnStatusEnum.SYS_ERROR.getValue());
            result.setMsg("最新领养/助养作品查询失败");
        }
        return result;
    }

    /**
     * 小程序 领养轮播图查询
     * @return
     */
    @ApiOperation(httpMethod = "GET", tags = "app-works", value = "小程序 领养轮播图查询",notes = "全国：0  城市：cityId",nickname = "7")
    @RequestMapping(value = "/adoptedRotation" ,method = RequestMethod.GET)
    private DataResult getAdoptedRotation(@ApiParam(value = "城市id,全国不传", required = true) @RequestParam("cityId") Integer cityId){
        DataResult result = new DataResult();
        try {
            String images = "";
            if (cityId == 0){
                images = systemImgService.selectById(6).getValue();
            }else {
                images = cityService.selectById(cityId).getAdoptedRotation();
            }
            Map<String,String> map = new HashMap<>(1);
            map.put("images",images);
            result.setData(map);
        } catch (Exception e) {
            logger.error("小程序 领养轮播图查询失败:{}", e);
            result.setCode(ReturnStatusEnum.SYS_ERROR.getValue());
            result.setMsg("领养轮播图查询失败");
        }
        return result;
    }

    /**
     * 小程序 助养轮播图查询
     * @return
     */
    @ApiOperation(httpMethod = "GET", tags = "app-works", value = "小程序 助养轮播图查询",notes = "全国：0  城市：cityId",nickname = "7")
    @RequestMapping(value = "/supportRotation" ,method = RequestMethod.GET)
    private DataResult getSupportRotation(@ApiParam(value = "城市id,全国不传", required = true) @RequestParam("cityId") Integer cityId){
        DataResult result = new DataResult();
        try {
            String images = "";
            if (cityId == 0){
                images = systemImgService.selectById(7).getValue();
            }else {
                images = cityService.selectById(cityId).getSupportRotation();
            }
            Map<String,String> map = new HashMap<>(1);
            map.put("images",images);
            result.setData(map);
        } catch (Exception e) {
            logger.error("小程序 助养轮播图查询失败:{}", e);
            result.setCode(ReturnStatusEnum.SYS_ERROR.getValue());
            result.setMsg("助养轮播图查询失败");
        }
        return result;
    }

    /**
     * 查询作品详情
     * @return
     */
    @ApiOperation(httpMethod = "GET", tags = "app-works", value = "查询作品详情",nickname = "8")
    @RequestMapping(value = "/workDetails" ,method = RequestMethod.GET)
    private DataResult getWorkDetails(@ApiParam(value = "作品id", required = true) @RequestParam(value = "id") Long id,
                                      @ApiParam(value = "用户id(登陆后要传)", required = false) @RequestParam(value = "customerId",required = false) String customerId){
        DataResult result = new ListDataResult();
        try {
            WorksDetailVo worksDetailVo = worksService.findWorkDetailVo(id,customerId);
            if (customerId != null && !"".equals(customerId)){
                if (worksDetailVo.getIsMatch() == 0){
                    int count = customerPointService.selectCount(new EntityWrapper<CustomerPoint>().eq("type",1).eq("type_id",worksDetailVo.getId()).eq("customer_id",customerId).eq("deleted",0));
                    if (count > 0){
                        worksDetailVo.setIsPoint(1);
                    }
                }else if(worksDetailVo.getIsMatch() == 1){
                    int count = matchPointService.selectCount(new EntityWrapper<MatchPoint>().eq("customer_id",customerId).eq("work_id",worksDetailVo.getId()).eq("deleted",0).last("and to_days(create_time) = to_days(now())"));
                    if (count > 0){
                        worksDetailVo.setIsPoint(1);
                    }
                }
            }
            result.setData(worksDetailVo);
        } catch (Exception e) {
            logger.error("查看作品详情失败:{}", e);
            result.setCode(ReturnStatusEnum.SYS_ERROR.getValue());
            result.setMsg("查看作品详情失败");
        }
        return result;
    }

    /**
     * 查看评论列表
     * @return
     */
    @ApiOperation(httpMethod = "GET", value = "查看评论列表", nickname = "8")
    @RequestMapping(value = "/comments" ,method = RequestMethod.GET)
    private ListDataResult getComments(@ApiParam(value = "作品id", required = true) @RequestParam(value = "id") Long id,
                                       @ApiParam(value = "页数", required = true) @RequestParam("page") Integer page,
                                       @ApiParam(value = "每页个数", required = true) @RequestParam("size") Integer size,
                                       @ApiParam(value = "用户id(登陆后要传)", required = false) @RequestParam(value = "customerId",required = false) String customerId){
        ListDataResult result = new ListDataResult();
        try {
            Page<CustomerCommentVo> commentVoPage = new Page<>(page,size);
            commentVoPage = customerCommentService.findcustomerCommentVos(commentVoPage,id,customerId);
            result.setCurrent(commentVoPage.getCurrent());
            result.setTotal(commentVoPage.getTotal());
            result.setData(commentVoPage.getRecords());
        } catch (Exception e) {
            logger.error("查看评论失败:{}", e);
            result.setCode(ReturnStatusEnum.SYS_ERROR.getValue());
            result.setMsg("查看评论失败");
        }
        return result;
    }

    /**
     * 查看我的/用户作品  / 喜欢的作品 /打赏/领养人作品
     * @return ListDataResult
     */
    @ApiOperation(httpMethod = "GET", value = "查看用户本人作品 /喜欢 /打赏/领养的作品", notes = "type:1-本人作品 2-喜欢的作品 3-打赏过的作品 4-领养人作品",nickname = "9")
    @RequestMapping(value = "/customerWorks" ,method = RequestMethod.GET)
    private ListDataResult getCustomerWorks(@ApiParam(value = "页数", required = true) @RequestParam("page") Integer page,
                                            @ApiParam(value = "每页个数", required = true) @RequestParam("size") Integer size,
                                            @ApiParam(value = "用户id", required = true) @RequestParam(value = "customerId") String customerId,
                                            @ApiParam(value = "查询类型 1-本人作品 2-喜欢的作品 3-打赏过的作品 4-领养人作品", required = true) @RequestParam(value = "type") Integer type){
        ListDataResult result = new ListDataResult();
        try {
            Page<WorksPersonalVo> worksPersonalVoPage = new Page<>(page,size);
            if (type ==1){
                worksPersonalVoPage = worksService.findPersonalWorksVos(worksPersonalVoPage,customerId);
            } else if (type == 2){
                worksPersonalVoPage = worksService.findLoverWorksVos(worksPersonalVoPage,customerId);
            } else if (type == 3){
                worksPersonalVoPage = worksService.findRewardWorksVos(worksPersonalVoPage,customerId);
            }else if (type == 4){
                worksPersonalVoPage = worksService.findAdoptWorksVos(worksPersonalVoPage,customerId);
            }
            result.setCurrent(worksPersonalVoPage.getCurrent());
            result.setTotal(worksPersonalVoPage.getTotal());
            result.setData(worksPersonalVoPage.getRecords());
        } catch (Exception e) {
            logger.error("查看用户本人作品 /喜欢的作品失败:{}", e);
            result.setCode(ReturnStatusEnum.SYS_ERROR.getValue());
            result.setMsg("查询失败");
        }
        return result;
    }

    /**
     * 查询作品链接
     * @return ListDataResult
     */
    @ApiOperation(httpMethod = "GET", value = "查询作品链接",nickname = "9")
    @RequestMapping(value = "/worksUrl" ,method = RequestMethod.GET)
    private DataResult getWorksUrl(@ApiParam(value = "作品id", required = true) @RequestParam(value = "id") Long id){
        DataResult result = new DataResult();
        try {
            Works works = worksService.selectById(id);
            Map<String,String> map = new HashMap<>(1);
            if (works != null){
                map.put("url",works.getUrl());
            }
            Integer count = works.getUrlCount() + 1;
            works.setUrlCount(count);
            worksService.updateById(works);
            result.setData(map);
        } catch (Exception e) {
            logger.error("查询作品链接失败:{}", e);
            result.setCode(ReturnStatusEnum.SYS_ERROR.getValue());
            result.setMsg("查询失败");
        }
        return result;
    }

}

