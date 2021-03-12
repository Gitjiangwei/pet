package com.ttm.pet.controller.admin.v1;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ttm.pet.enums.ReturnStatusEnum;
import com.ttm.pet.model.dto.City;
import com.ttm.pet.model.pojo.DataResult;
import com.ttm.pet.model.pojo.ListDataResult;
import com.ttm.pet.service.CityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api(value = "/admin/city",tags = "admin-city",description = "城市管理")
@RequestMapping("/admin/city")
public class CityController {
    private final static Logger logger = LoggerFactory.getLogger(CityController.class);

    @Autowired
    private CityService cityService;

    /**
     * 查询所有的市
     * @return
     */
    @ApiOperation(httpMethod = "GET",value = "查询所有的市")
    @RequestMapping(value="/cities", method=RequestMethod.GET)
    public ListDataResult queryCities(@ApiParam(value = "页数", required = true) @RequestParam("page") Integer page,
                                      @ApiParam(value = "每页个数", required = true) @RequestParam("size") Integer size,
                                      @ApiParam(value = "城市名", required = false) @RequestParam(value = "name",required = false) String name){
        ListDataResult result = new ListDataResult();
        try {
            Page<City> cityPage = new Page<>(page,size);
            EntityWrapper<City> cityEntityWrapper = new EntityWrapper<>();
            if (name != null && !"".equals(name)){
                cityEntityWrapper.like("name",name);
            }
            cityPage = cityService.selectPage(cityPage,cityEntityWrapper);
            result.setData(cityPage.getRecords());
            result.setTotal(cityPage.getTotal());
            result.setCurrent(cityPage.getCurrent());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("查询所有的市列表失败", e.getMessage());
            result.setCode(ReturnStatusEnum.RFC_ERROR.getValue());
            result.setMsg("查询失败");
            return result;
        }
    }

    /**
     * 新增城市
     * @param city
     * @return
     */
    @ApiOperation(httpMethod = "POST", notes = "只需要传name",value = "新增城市")
    @RequestMapping(value = "/city" ,method = RequestMethod.POST)
    private DataResult addCity(@RequestBody City city){
        DataResult result = new DataResult();
        try {
            cityService.insert(city);
            return result;
        }catch (Exception e){
            logger.error("新增城市失败:{}",e.getMessage());
            result.setCode(ReturnStatusEnum.RFC_ERROR.getValue());
            result.setMsg(ReturnStatusEnum.RFC_ERROR.getDesc());
            return result;
        }
    }

    /**
     * 删除某个城市
     * @param id
     * @return
     */
    @ApiOperation(httpMethod = "DELETE", value = "删除某个城市")
    @RequestMapping(value = "/city/{id}" ,method = RequestMethod.DELETE)
    private DataResult deleteCity(@PathVariable(value="id") Integer id){
        DataResult result = new DataResult();
        try {
            cityService.deleteById(id);
            return result;
        }catch (Exception e){
            logger.error("删除某个城市失败:{}",e.getMessage());
            result.setCode(ReturnStatusEnum.EXECUTESQL_ERROR.getValue());
            result.setMsg(ReturnStatusEnum.EXECUTESQL_ERROR.getDesc());
            return result;
        }
    }
}
