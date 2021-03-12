package com.ttm.pet.controller.app;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ttm.pet.enums.ReturnStatusEnum;
import com.ttm.pet.model.dto.CustomerDraw;
import com.ttm.pet.model.dto.HistoryMatch;
import com.ttm.pet.model.dto.Works;
import com.ttm.pet.model.pojo.DataResult;
import com.ttm.pet.model.pojo.ListDataResult;
import com.ttm.pet.model.vo.app.DrawCodeListVo;
import com.ttm.pet.model.vo.app.FactoryListVo;
import com.ttm.pet.model.vo.app.MatchWorksListVo;
import com.ttm.pet.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 乐乐页面
 * </p>
 *
 * @author cx
 * @since 2020-05-26
 */
@RestController
@Api(value ="/app/match",tags = "app-match",description = "乐乐比赛相关接口")
@RequestMapping("/app/match")
public class MatchApiController {
    private final static Logger logger = LoggerFactory.getLogger(MatchApiController.class);
    @Autowired
    private HistoryMatchService historyMatchService;
    @Autowired
    private WorksService worksService;

    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerDrawService customerDrawService;

    @Autowired
    private FactoryService factoryService;

    /**
     * 查询最新比赛信息
     * @return
     */
    @ApiOperation(httpMethod = "GET", value = "查询上面最新比赛信息",nickname = "1")
    @RequestMapping(value = "/matchInfo" ,method = RequestMethod.GET)
    private DataResult getMatchInfo(){
        DataResult result = new DataResult();
        try {
            EntityWrapper<HistoryMatch> historyMatchEntityWrapper = new EntityWrapper<>();
            historyMatchEntityWrapper.setSqlSelect("id","title","image","unix_timestamp(start_time) startTime","unix_timestamp(end_time) endTime","unix_timestamp(draw_end_time) drawEndTime");
            historyMatchEntityWrapper.eq("deleted",0);
            historyMatchEntityWrapper.orderBy("create_time",false).last("limit 1");
            Map<String,Object> HistoryMatch = historyMatchService.selectMap(historyMatchEntityWrapper);
            result.setData(HistoryMatch);
            return result;
        }catch (Exception e){
            logger.error("查询上面最新比赛信息失败", e.getMessage());
            result.setCode(ReturnStatusEnum.RFC_ERROR.getValue());
            result.setMsg("查询失败");
            return result;
        }
    }
    /**
     * 查询比赛人数，总赞数和排名
     * @return
     */
    @ApiOperation(httpMethod = "GET", value = "查询比赛人数、总赞数、个人排名",nickname = "2")
    @RequestMapping(value = "/middleInfo" ,method = RequestMethod.GET)
    private DataResult getMiddleInfo(@ApiParam(value = "用户id(登陆后要传)", required = false) @RequestParam(value = "customerId",required = false) String customerId){
        DataResult result = new DataResult();
        try {
            Map<String,Integer> map = worksService.findMatchCount();
            if(customerId != null || "".equals(customerId)){
                Map<String,Integer> rowNumMap = worksService.findRankByCustomerId(customerId);
                if (rowNumMap == null || rowNumMap.size() == 0){
                    map.put("rowNum",0);
                }else {
                    map.put("rowNum",rowNumMap.get("rownum"));
                }
            }else {
                map.put("rowNum",0);
            }
            result.setData(map);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("查询比赛人数、总赞数、个人排名", e.getMessage());
            result.setCode(ReturnStatusEnum.RFC_ERROR.getValue());
            result.setMsg("查询失败");
            return result;
        }
    }

    /**
     * 查询比赛排名作品列表
     * @return
     */
    @ApiOperation(httpMethod = "GET", value = "查询比赛排名作品列表",nickname = "3")
    @RequestMapping(value = "/worksList" ,method = RequestMethod.GET)
    private ListDataResult getWorksList(@ApiParam(value = "页数", required = true) @RequestParam("page") Integer page,
                                         @ApiParam(value = "每页个数", required = true) @RequestParam("size") Integer size,
                                         @ApiParam(value = "用户id(登陆后要传)", required = false) @RequestParam(value = "customerId",required = false) String customerId) {
        ListDataResult result = new ListDataResult();
        try {
            Page<MatchWorksListVo> worksListVoPage = new Page<>(page,size);
            worksListVoPage = worksService.findMatchWorksListVos(worksListVoPage,customerId);
            result.setCurrent(worksListVoPage.getCurrent());
            result.setTotal(worksListVoPage.getTotal());
            result.setData(worksListVoPage.getRecords());
            return result;
        }catch (Exception e){
            e.printStackTrace();
            result.setCode(ReturnStatusEnum.RFC_ERROR.getValue());
            result.setMsg(ReturnStatusEnum.RFC_ERROR.getDesc());
            return result;
        }
    }

    /**
     * 查询比赛规则
     * @return
     */
    @ApiOperation(httpMethod = "GET", value = "查询比赛规则",nickname = "4")
    @RequestMapping(value = "/matchRule" ,method = RequestMethod.GET)
    private DataResult getMatchRule(@ApiParam(value = "比赛id", required = true) @RequestParam(value = "id") Integer id) {
        DataResult result = new DataResult();
        try {
            HistoryMatch historyMatch = historyMatchService.selectById(id);
            Map<String,String> map = new HashMap<>(1);
            map.put("rule",historyMatch.getRule());
            result.setData(map);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            result.setCode(ReturnStatusEnum.RFC_ERROR.getValue());
            result.setMsg(ReturnStatusEnum.RFC_ERROR.getDesc());
            return result;
        }
    }

    /**
     * 查询历史比赛列表
     * @return
     */
    @ApiOperation(httpMethod = "GET", value = "查询历史比赛列表",nickname = "5")
    @RequestMapping(value = "/historyMatch" ,method = RequestMethod.GET)
    private DataResult getHistoryMatch(@ApiParam(value = "页数", required = true) @RequestParam("page") Integer page,
                                       @ApiParam(value = "每页个数", required = true) @RequestParam("size") Integer size) {
        DataResult result = new DataResult();
        try {
            Page<HistoryMatch> historyMatchPage = new Page<>(page,size);
            EntityWrapper<HistoryMatch> historyMatchEntityWrapper = new EntityWrapper<>();
            historyMatchEntityWrapper.setSqlSelect("id","show_img");
            historyMatchEntityWrapper.orderBy("create_time",false);
            Page<Map<String,Object>> history = historyMatchService.selectMapsPage(historyMatchPage,historyMatchEntityWrapper);
            result.setData(history);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            result.setCode(ReturnStatusEnum.RFC_ERROR.getValue());
            result.setMsg(ReturnStatusEnum.RFC_ERROR.getDesc());
            return result;
        }
    }

    /**
     * 搜索用户名查询出该用户的参赛作品
     * @return
     */
    @ApiOperation(httpMethod = "GET", value = "搜索用户名查询出该用户的参赛作品",nickname = "6")
    @RequestMapping(value = "/worksByName" ,method = RequestMethod.GET)
    private ListDataResult getWorksByName(@ApiParam(value = "页数", required = true) @RequestParam("page") Integer page,
                                          @ApiParam(value = "每页个数", required = true) @RequestParam("size") Integer size,
                                          @ApiParam(value = "搜索的用户名关键词", required = true) @RequestParam("keyword") String keyword,
                                          @ApiParam(value = "用户id(登陆后要传)", required = false) @RequestParam(value = "customerId",required = false) String customerId) {
        ListDataResult result = new ListDataResult();
        try {
            Page<MatchWorksListVo> worksListVoPage = new Page<>(page,size);
            worksListVoPage = worksService.findMatchWorksListByNameVos(worksListVoPage,keyword,customerId);
            result.setCurrent(worksListVoPage.getCurrent());
            result.setTotal(worksListVoPage.getTotal());
            result.setData(worksListVoPage.getRecords());
            return result;
        }catch (Exception e){
            e.printStackTrace();
            result.setCode(ReturnStatusEnum.RFC_ERROR.getValue());
            result.setMsg(ReturnStatusEnum.RFC_ERROR.getDesc());
            return result;
        }
    }


    /**
     * 查询抽奖厂家列表
     * @return
     */
    @ApiOperation(httpMethod = "GET", value = "查询抽奖厂家列表",nickname = "7")
    @RequestMapping(value = "/factories" ,method = RequestMethod.GET)
    private ListDataResult getFactories(@ApiParam(value = "页数", required = true) @RequestParam("page") Integer page,
                                        @ApiParam(value = "每页个数", required = true) @RequestParam("size") Integer size) {
        ListDataResult result = new ListDataResult();
        try {
            Page<FactoryListVo> factoryListVoPage = new Page<>(page,size);
            factoryListVoPage = factoryService.findFactoryListVos(factoryListVoPage);
            result.setCurrent(factoryListVoPage.getCurrent());
            result.setTotal(factoryListVoPage.getTotal());
            result.setData(factoryListVoPage.getRecords());
            return result;
        }catch (Exception e){
            e.printStackTrace();
            result.setCode(ReturnStatusEnum.RFC_ERROR.getValue());
            result.setMsg(ReturnStatusEnum.RFC_ERROR.getDesc());
            logger.error("查询抽奖厂家列表失败："+e.getMessage());
            return result;
        }
    }

    /**
     * 查询我的抽奖次数
     * @return
     */
    @ApiOperation(httpMethod = "GET", value = "查询我的抽奖次数",nickname = "8")
    @RequestMapping(value = "/drawCount" ,method = RequestMethod.GET)
    private DataResult getDrawCount(@ApiParam(value = "用户id", required = true) @RequestParam("customerId") String customerId) {
        DataResult result = new DataResult();
        try {

            //先查询期数
            EntityWrapper<HistoryMatch> historyMatchEntityWrapper = new EntityWrapper<>();
            HistoryMatch historyMatch = historyMatchService.selectList(historyMatchEntityWrapper.orderBy("create_time",false)).get(0);
            Map<String,Object> map = new HashedMap(1);
            //查询总抽奖数
            Works works = worksService.selectOne(new EntityWrapper<Works>().eq("customer_id",customerId).eq("is_match",1));
            if (works == null){
                map.put("ownerDrawCount",0);
            }else {
                //已经抽奖次数
                int count = customerDrawService.selectCount(new EntityWrapper<CustomerDraw>().eq("customer_id",customerId).eq("match_id",historyMatch.getId()));
                int newDrawCount = works.getPointNum() - count;
                if (works.getPointNum() - count < 0){
                    newDrawCount = 0;
                }
                map.put("ownerDrawCount",newDrawCount);
            }
            result.setData(map);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            result.setCode(ReturnStatusEnum.RFC_ERROR.getValue());
            result.setMsg(ReturnStatusEnum.RFC_ERROR.getDesc());
            logger.error("查询我的抽奖次数失败："+e.getMessage());
            return result;
        }
    }

    /**
     * 兑换中奖码
     * @return
     */
    @ApiOperation(httpMethod = "GET", value = "兑换中奖码",nickname = "9")
    @RequestMapping(value = "/drawCode" ,method = RequestMethod.GET)
    private DataResult getDrawCode(@ApiParam(value = "用户id", required = true) @RequestParam("customerId") String customerId,
                                   @ApiParam(value = "兑换的厂家id", required = true) @RequestParam("factoryId") Integer factoryId,
                                   @ApiParam(value = "比赛id", required = true) @RequestParam("matchId") Integer matchId) {
        DataResult result = new DataResult();
        try {
            int code = (int)((Math.random()*9+1)*100000);
            CustomerDraw customerDraw = new CustomerDraw();
            customerDraw.setCustomerId(customerId);
            customerDraw.setFactoryId(factoryId);
            customerDraw.setDrawCode(code);
            customerDraw.setMatchId(matchId);
            customerDrawService.insert(customerDraw);

            Map<String,Object> map = new HashedMap(1);
            map.put("code",code);
            result.setData(map);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            result.setCode(ReturnStatusEnum.RFC_ERROR.getValue());
            result.setMsg(ReturnStatusEnum.RFC_ERROR.getDesc());
            logger.error("兑换中奖码失败："+e.getMessage());
            return result;
        }
    }

    /**
     * 查询我的抽奖码
     * @return
     */
    @ApiOperation(httpMethod = "GET", value = "查询我的抽奖码",nickname = "10")
    @RequestMapping(value = "/drawCodeList" ,method = RequestMethod.GET)
    private ListDataResult getDrawCodeList(@ApiParam(value = "页数", required = true) @RequestParam("page") Integer page,
                                           @ApiParam(value = "每页个数", required = true) @RequestParam("size") Integer size,
                                           @ApiParam(value = "用户id", required = true) @RequestParam("customerId") String customerId,
                                           @ApiParam(value = "比赛id 本期传本期的比赛id 上期传0", required = true) @RequestParam("matchId") Integer matchId) {
        ListDataResult result = new ListDataResult();
        try {
            if(matchId == 0){
                EntityWrapper<HistoryMatch> historyMatchEntityWrapper = new EntityWrapper<>();
                List<HistoryMatch> historyMatchs = historyMatchService.selectList(historyMatchEntityWrapper.orderBy("create_time",false));
                if (historyMatchs.size() > 1){
                        matchId = historyMatchs.get(1).getId();
                }else {
                    result.setData(null);
                    result.setMsg("没有上一期");
                    return result;
                }
            }
            Page<DrawCodeListVo> drawCodeListVoPage = new Page<>(page,size);
            drawCodeListVoPage = customerDrawService.findDrawCodeListVos(drawCodeListVoPage,customerId,matchId);
            result.setCurrent(drawCodeListVoPage.getCurrent());
            result.setTotal(drawCodeListVoPage.getTotal());
            result.setData(drawCodeListVoPage.getRecords());
            return result;
        }catch (Exception e){
            e.printStackTrace();
            result.setCode(ReturnStatusEnum.RFC_ERROR.getValue());
            result.setMsg(ReturnStatusEnum.RFC_ERROR.getDesc());
            logger.error("查询我的抽奖码失败："+e.getMessage());
            return result;
        }
    }

}
