package com.ttm.pet.controller.app;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ttm.pet.enums.ReturnStatusEnum;
import com.ttm.pet.model.dto.*;
import com.ttm.pet.model.pojo.DataResult;
import com.ttm.pet.model.pojo.ListDataResult;
import com.ttm.pet.model.query.app.BusinessQuery;
import com.ttm.pet.model.vo.app.CustomerLeleVo;
import com.ttm.pet.model.vo.app.WorksOuterVo;
import com.ttm.pet.service.BusinessService;
import com.ttm.pet.service.CustomerService;
import com.ttm.pet.service.ModuleService;
import com.ttm.pet.service.WorksService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 模块相关
 * </p>
 *
 * @author cx
 * @since 2020-12-21
 */
@RestController
@Api(value = "/app/module", tags = "app-module", description = "模块相关接口")
@RequestMapping("/app/module")
public class ModuleController {
    private final static Logger logger = LoggerFactory.getLogger(ModuleController.class);

    @Autowired
    private ModuleService moduleService;

    @Autowired
    private BusinessService businessService;

    @Autowired
    private WorksService worksService;

    @Autowired
    private CustomerService customerService;

    /**
     * 查询有效的模块
     * 
     * @return
     */
    @ApiOperation(httpMethod = "GET", value = "查询有效的模块", nickname = "1")
    @RequestMapping(value = "/modules", method = RequestMethod.GET)
    private DataResult getModules() {
        DataResult result = new DataResult();
        try {
            List< Module > modules = moduleService.selectList(new EntityWrapper< Module >().eq("isValid", 1));
            result.setData(modules);
            return result;
        }
        catch (Exception e) {
            e.printStackTrace();
            result.setCode(ReturnStatusEnum.RFC_ERROR.getValue());
            result.setMsg(ReturnStatusEnum.RFC_ERROR.getDesc());
            logger.error("查询有效的模块失败：" + e.getMessage());
            return result;
        }
    }

    /**
     * 申请企业认证
     * 
     * @param businessQuery
     * @return
     */
    @ApiOperation(httpMethod = "POST", value = "申请企业认证", nickname = "2")
    @RequestMapping(value = "/business", method = RequestMethod.POST)
    private DataResult addBusiness(@RequestBody BusinessQuery businessQuery) {
        DataResult result = new DataResult();
        try {
            Business business = new Business();

            BeanUtils.copyProperties(businessQuery, business);
            businessService.insert(business);
            return result;
        }
        catch (Exception e) {
            logger.error("申请企业认证失败:{}", e);
            result.setCode(ReturnStatusEnum.SYS_ERROR.getValue());
            result.setMsg("申请失败");
            return result;
        }
    }

    /**
     * 模块下作品列表
     * 
     * @return
     */
    @ApiOperation(httpMethod = "GET", value = "模块下作品列表", notes = "精选时 moduleId 传0; 全国cityId传0", nickname = "3")
    @RequestMapping(value = "/moduleWorks", method = RequestMethod.GET)
    private ListDataResult getModuleWorks(@ApiParam(value = "页数", required = true) @RequestParam("page") Integer page,
            @ApiParam(value = "每页个数", required = true) @RequestParam("size") Integer size,
            @ApiParam(value = "模块id", required = true) @RequestParam("moduleId") Integer moduleId,
            @ApiParam(value = "城市id", required = true) @RequestParam("cityId") Integer cityId) {
        ListDataResult result = new ListDataResult();
        try {
            Page< WorksOuterVo > worksOuterVoPage = new Page<>(page, size);
            worksOuterVoPage = worksService.findModuleWorkVos(worksOuterVoPage, moduleId, cityId);
            result.setCurrent(worksOuterVoPage.getCurrent());
            result.setTotal(worksOuterVoPage.getTotal());
            result.setData(worksOuterVoPage.getRecords());
        }
        catch (Exception e) {
            logger.error("模块下作品列表失败:{}", e);
            result.setCode(ReturnStatusEnum.SYS_ERROR.getValue());
            result.setMsg("模块下作品列表失败");
        }
        return result;
    }

    /**
     * 模块下头像列表
     * 
     * @return
     */
    @ApiOperation(httpMethod = "GET", value = "模块下滑动头像列表", nickname = "4")
    @RequestMapping(value = "/customers", method = RequestMethod.GET)
    private DataResult getCustomers(
            @ApiParam(value = "模块id", required = true) @RequestParam("moduleId") Integer moduleId) {
        DataResult result = new DataResult();
        try {
            List< CustomerLeleVo > customerModuleVos = customerService.findModuleCustomerVos(moduleId);
            result.setData(customerModuleVos);
        }
        catch (Exception e) {
            logger.error("模块下滑动头像列表失败:{}", e);
            result.setCode(ReturnStatusEnum.SYS_ERROR.getValue());
            result.setMsg("模块下滑动头像列表失败");
        }
        return result;
    }

    /**
     * 查询企业状态
     * 
     * @param customerId
     * @return
     */
    @ApiOperation(httpMethod = "GET", value = "查询企业状态", notes = "-1:未提交 0-提交未审核 1-审核成功未支付 2-支付成功 ;状态为支付成功时 vipEndTime有值", nickname = "3")
    @RequestMapping(value = "/businessStatus", method = RequestMethod.GET)
    private DataResult businessStatus(
            @ApiParam(value = "用户id", required = true) @RequestParam("customerId") String customerId) {
        DataResult result = new DataResult();
        try {
            Map< String, Object > map = new HashMap<>(2);
            List< Business > business = businessService.selectList(
                    new EntityWrapper< Business >().eq("customer_id", customerId).orderBy("update_time", false));
            if (business.size() == 0) {
                map.put("status", -1);
            }
            else {
                map.put("status", business.get(0).getStatus());
                map.put("vipEndTime", business.get(0).getVipEndTime());
            }
            result.setData(map);
            return result;
        }
        catch (Exception e) {
            logger.error("查询企业状态失败:{}", e);
            result.setCode(ReturnStatusEnum.SYS_ERROR.getValue());
            result.setMsg("查询失败");
            return result;
        }
    }

}
