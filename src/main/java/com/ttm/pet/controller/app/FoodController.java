package com.ttm.pet.controller.app;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ttm.pet.enums.ReturnStatusEnum;
import com.ttm.pet.model.dto.*;
import com.ttm.pet.model.pojo.DataResult;
import com.ttm.pet.model.pojo.ListDataResult;
import com.ttm.pet.model.query.app.SupportFoodQuery;
import com.ttm.pet.model.vo.app.*;
import com.ttm.pet.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@Api(value ="/app/food",tags = "app-food",description = "乐乐宠粮相关接口")
@RequestMapping("/app/food")
public class FoodController {
    private final static Logger logger = LoggerFactory.getLogger(FoodController.class);
    @Autowired
    private CustomerService customerService;
    @Autowired
    private CustomerDonateService customerDonateService;
    @Autowired
    private BaseService baseService;
    @Autowired
    private GoodsOrderService goodsOrderService;
    @Autowired
    private SystemImgService systemImgService;
    @Autowired
    private ConfigService configService;

    /**
     * 查询上面的公司列表
     * @return
     */
    @ApiOperation(httpMethod = "GET", value = " 查询上面的公司列表",nickname = "1")
    @RequestMapping(value = "/business" ,method = RequestMethod.GET)
    private ListDataResult getBusiness(@ApiParam(value = "页数", required = true) @RequestParam("page") Integer page,
                                       @ApiParam(value = "每页个数", required = true) @RequestParam("size") Integer size){
        ListDataResult result = new ListDataResult();
        try {
            Page<Customer> customerPage = new Page<>(page,size);
            EntityWrapper<Customer> businessEntityWrapper = new EntityWrapper<>();
            businessEntityWrapper.setSqlSelect("id","uuid customerId","portrait","name","sum_food sumFood","food");
            businessEntityWrapper.eq("is_business",1);
            businessEntityWrapper.orderBy("food",false);
            Page<Map<String,Object>> businessPage = customerService.selectMapsPage(customerPage,businessEntityWrapper);
            result.setCurrent(businessPage.getCurrent());
            result.setTotal(businessPage.getTotal());
            result.setData(businessPage.getRecords());
            return result;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("查询上面的公司列表失败", e.getMessage());
            result.setCode(ReturnStatusEnum.RFC_ERROR.getValue());
            result.setMsg("查询失败");
            return result;
        }
    }

    /**
     * 查询下面的基地列表
     * @return
     */
    @ApiOperation(httpMethod = "GET", value = "查询下面的基地列表",nickname = "2")
    @RequestMapping(value = "/bases" ,method = RequestMethod.GET)
    private ListDataResult getBases(@ApiParam(value = "页数", required = true) @RequestParam("page") Integer page,
                                    @ApiParam(value = "每页个数", required = true) @RequestParam("size") Integer size){
        ListDataResult result = new ListDataResult();
        try {
            Page<BaseListVo> baseListVoPage = new Page<>(page,size);
            baseListVoPage = customerService.findBaseListVos(baseListVoPage,null);
            result.setCurrent(baseListVoPage.getCurrent());
            result.setTotal(baseListVoPage.getTotal());
            result.setData(baseListVoPage.getRecords());
            return result;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("查询下面的基地列表失败", e.getMessage());
            result.setCode(ReturnStatusEnum.RFC_ERROR.getValue());
            result.setMsg("查询失败");
            return result;
        }
    }

    /**
     * 查询我的点赞粮数
     * @return
     */
    @ApiOperation(httpMethod = "GET", value = "查询当天我的点赞粮数",notes = "返回已点粮食数，注意每次判断剩余的数够不够点",nickname = "3")
    @RequestMapping(value = "/supportFood" ,method = RequestMethod.GET)
    private DataResult getSupportFood(@ApiParam(value = "用户id", required = true) @RequestParam("customerId") String customerId){
        DataResult result = new DataResult();
        try {
//            EntityWrapper<CustomerDonate> customerDonateEntityWrapper = new EntityWrapper<>();
//            customerDonateEntityWrapper.setSqlSelect("IFNULL(sum(food),0) supportFood");
//            customerDonateEntityWrapper.eq("customer_id",customerId);
//            customerDonateEntityWrapper.last("and TO_DAYS(create_time) = TO_DAYS(NOW())");
            Map<String,Object> map = customerDonateService.findSupportFood(customerId);
            Config sumConfig = configService.selectById(4);
            Config onceConfig = configService.selectById(5);
            map.put("sumPointFood",sumConfig.getValue().intValue());
            map.put("onceTimeFood",onceConfig.getValue().intValue());
            result.setData(map);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("查询当天我的点赞粮数失败", e.getMessage());
            result.setCode(ReturnStatusEnum.RFC_ERROR.getValue());
            result.setMsg("查询失败");
            return result;
        }
    }

    /**
     * 搜索基地
     * @return
     */
    @ApiOperation(httpMethod = "GET", value = "搜索基地",nickname = "4")
    @RequestMapping(value = "/basesByName" ,method = RequestMethod.GET)
    private ListDataResult getBasesByName(@ApiParam(value = "页数", required = true) @RequestParam("page") Integer page,
                                          @ApiParam(value = "每页个数", required = true) @RequestParam("size") Integer size,
                                          @ApiParam(value = "搜索的用户名关键词", required = true) @RequestParam("keyword") String keyword){
        ListDataResult result = new ListDataResult();
        try {
            Page<BaseListVo> baseListVoPage = new Page<>(page,size);
            baseListVoPage = customerService.findBaseListVos(baseListVoPage,keyword);
            result.setCurrent(baseListVoPage.getCurrent());
            result.setTotal(baseListVoPage.getTotal());
            result.setData(baseListVoPage.getRecords());
            return result;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("搜索基地失败", e.getMessage());
            result.setCode(ReturnStatusEnum.RFC_ERROR.getValue());
            result.setMsg("查询失败");
            return result;
        }
    }

    /**
     * 点赞领粮
     * @param supportFoodQuery
     * @return
     */
    @ApiOperation(httpMethod = "POST", value = "点赞领粮", nickname = "5")
    @RequestMapping(value = "/support" ,method = RequestMethod.POST)
    private DataResult addSupport(@RequestBody SupportFoodQuery supportFoodQuery){
        DataResult result = new DataResult();
        try {
            Config onceConfig = configService.selectById(5);
            int onceTime = onceConfig.getValue().intValue();
            Customer businessCustomer = customerService.selectOne(new EntityWrapper<Customer>().eq("uuid",supportFoodQuery.getBusinessCustomerId()));
            int haveFood = businessCustomer.getFood();
            if (haveFood < onceTime){
                return new DataResult(ReturnStatusEnum.SYS_ERROR.getValue(),"该公司宠粮已被领空",null);
            }
            CustomerDonate customerDonate = new CustomerDonate();
            BeanUtils.copyProperties(supportFoodQuery,customerDonate);
            customerDonate.setFood(onceTime);
            customerDonateService.insert(customerDonate);
            businessCustomer.setFood(haveFood - onceTime);
            customerService.updateById(businessCustomer);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("点赞领粮失败:{}", e);
            result.setCode(ReturnStatusEnum.SYS_ERROR.getValue());
            result.setMsg("点赞领粮失败");
            return result;
        }
    }

    /**
     * 查询基地详情
     * @return
     */
    @ApiOperation(httpMethod = "GET", value = "查询基地详情",nickname = "6")
    @RequestMapping(value = "/baseDetail" ,method = RequestMethod.GET)
    private DataResult getBaseDetail(@ApiParam(value = "基地id", required = true) @RequestParam(value = "id",required = true) Integer id){
        DataResult result = new DataResult();
        try {
            BaseDetailVo baseDetailVo = baseService.findBaseDetailVo(id.toString());
            result.setData(baseDetailVo);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("查询基地详情失败", e.getMessage());
            result.setCode(ReturnStatusEnum.RFC_ERROR.getValue());
            result.setMsg("查询失败");
            return result;
        }
    }

    /**
     * 查询支持者列表
     * @return
     */
    @ApiOperation(httpMethod = "GET", value = "查询支持者列表",nickname = "7")
    @RequestMapping(value = "/supporterList" ,method = RequestMethod.GET)
    private ListDataResult getSupporterList(@ApiParam(value = "页数", required = true) @RequestParam("page") Integer page,
                                            @ApiParam(value = "每页个数", required = true) @RequestParam("size") Integer size,
                                            @ApiParam(value = "基地id", required = true) @RequestParam(value = "id",required = true) Integer id,
                                            @ApiParam(value = "类型 1-筹粮 2-筹钱", required = false) @RequestParam(value = "type",required = false) Integer type){
        ListDataResult result = new ListDataResult();
        try {
            if (type == null || "".equals(type)){
                type = 1;
            }
            Page<SupportListVo> supportListVoPage = new Page<>(page,size);
            Base base = baseService.selectById(id);
            supportListVoPage = goodsOrderService.findSupportListVos(supportListVoPage,type,base.getCustomerId());
            result.setData(supportListVoPage.getRecords());
            result.setCurrent(supportListVoPage.getCurrent());
            result.setTotal(supportListVoPage.getTotal());
            return result;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("查询支持者列表失败", e.getMessage());
            result.setCode(ReturnStatusEnum.RFC_ERROR.getValue());
            result.setMsg("查询失败");
            return result;
        }
    }

    /**
     * 查询h5宠粮页面头部信息
     * 21.2 加入筹钱，根据type区分
     * @return
     */
    @ApiOperation(httpMethod = "GET", value = "查询h5宠粮页面头部信息（H5用）",nickname = "8")
    @RequestMapping(value = "/headInfo" ,method = RequestMethod.GET)
    private DataResult getHeadInfo(@ApiParam(value = "类型 1-筹粮 2-筹钱", required = false) @RequestParam(value = "type",required = false) Integer type){
        DataResult result = new DataResult();
        try {
            Map<String,Object> map = new HashedMap(2);
            SystemImg systemImg = systemImgService.selectById(1);
            map.put("images",systemImg.getValue());
            if (type == null || "".equals(type)){
                type = 1;
            }
            int baseNum = customerService.findBaseCount(type);
            map.put("baseNum",baseNum);
            result.setData(map);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("查询h5宠粮页面头部信息失败", e.getMessage());
            result.setCode(ReturnStatusEnum.RFC_ERROR.getValue());
            result.setMsg("查询失败");
            return result;
        }
    }

    /**
     * 查询h5宠粮页面下面基地列表
     * @return
     */
    @ApiOperation(httpMethod = "GET", value = "查询h5宠粮页面下面基地列表（H5用）",nickname = "9")
    @RequestMapping(value = "/baseList" ,method = RequestMethod.GET)
    private ListDataResult getBaseList(@ApiParam(value = "页数", required = true) @RequestParam("page") Integer page,
                                       @ApiParam(value = "每页个数", required = true) @RequestParam("size") Integer size){
        ListDataResult result = new ListDataResult();
        try {
            Page<BaseListH5Vo> baseListVoPage = new Page<>(page,size);
            baseListVoPage = baseService.findBaseListH5Vos(baseListVoPage,null);
            result.setCurrent(baseListVoPage.getCurrent());
            result.setTotal(baseListVoPage.getTotal());
            result.setData(baseListVoPage.getRecords());
            return result;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("查询h5宠粮页面下面基地列表失败", e.getMessage());
            result.setCode(ReturnStatusEnum.RFC_ERROR.getValue());
            result.setMsg("查询失败");
            return result;
        }
    }

    /**
     * 搜索基地
     * * 21.2 加入筹钱，根据type区分
     * @return
     */
    @ApiOperation(httpMethod = "GET", value = "搜索基地（H5用）",nickname = "10")
    @RequestMapping(value = "/baseListByName" ,method = RequestMethod.GET)
    private ListDataResult getBaseListByName(@ApiParam(value = "页数", required = true) @RequestParam("page") Integer page,
                                             @ApiParam(value = "每页个数", required = true) @RequestParam("size") Integer size,
                                             @ApiParam(value = "类型 1-筹粮 2-筹钱", required = false) @RequestParam(value = "type",required = false) Integer type,
                                             @ApiParam(value = "搜索的用户名关键词", required = false) @RequestParam(value = "keyword",required = false) String keyword){
        ListDataResult result = new ListDataResult();
        try {
            Page<BaseListH5Vo> baseListVoPage = new Page<>(page,size);
            if (type != null && type ==2) {
                baseListVoPage = baseService.findBaseListH5ByMoneyVos(baseListVoPage,keyword);
            }else {
                baseListVoPage = baseService.findBaseListH5Vos(baseListVoPage,keyword);
            }
            result.setCurrent(baseListVoPage.getCurrent());
            result.setTotal(baseListVoPage.getTotal());
            result.setData(baseListVoPage.getRecords());
            return result;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("搜索基地（H5用）失败", e.getMessage());
            result.setCode(ReturnStatusEnum.RFC_ERROR.getValue());
            result.setMsg("查询失败");
            return result;
        }
    }

    /**
     * 查询基地详情-筹钱基地的详情(H5用)
     * @return
     */
    @ApiOperation(httpMethod = "GET", value = "查询基地详情--筹钱基地的详情(H5用)",nickname = "11")
    @RequestMapping(value = "/baseDetailMoney" ,method = RequestMethod.GET)
    private DataResult getBaseDetailMoney(@ApiParam(value = "基地id", required = false) @RequestParam(value = "id",required = false) Integer id){
        DataResult result = new DataResult();
        try {
            BaseDetailMoneyVo baseDetailVo = baseService.findBaseDetailMoneyVo(id.toString());
            result.setData(baseDetailVo);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("查询基地详情H5用失败", e.getMessage());
            result.setCode(ReturnStatusEnum.RFC_ERROR.getValue());
            result.setMsg("查询失败");
            return result;
        }
    }

    /**
     * 查询h5筹钱页面下面基地列表
     * @return
     */
    @ApiOperation(httpMethod = "GET", value = "查询h5宠粮页面下面基地列表--筹钱页面用（H5用）",nickname = "12")
    @RequestMapping(value = "/baseListMoney" ,method = RequestMethod.GET)
    private ListDataResult getBaseListMoney(@ApiParam(value = "页数", required = true) @RequestParam("page") Integer page,
                                       @ApiParam(value = "每页个数", required = true) @RequestParam("size") Integer size){
        ListDataResult result = new ListDataResult();
        try {
            Page<BaseListH5Vo> baseListVoPage = new Page<>(page,size);
            baseListVoPage = baseService.findBaseListH5ByMoneyVos(baseListVoPage,null);
            result.setCurrent(baseListVoPage.getCurrent());
            result.setTotal(baseListVoPage.getTotal());
            result.setData(baseListVoPage.getRecords());
            return result;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("查询h5宠粮筹钱页面下面基地列表失败", e.getMessage());
            result.setCode(ReturnStatusEnum.RFC_ERROR.getValue());
            result.setMsg("查询失败");
            return result;
        }
    }
}
