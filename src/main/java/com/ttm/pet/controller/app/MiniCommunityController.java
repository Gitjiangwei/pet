package com.ttm.pet.controller.app;

import com.baomidou.mybatisplus.plugins.Page;
import com.ttm.pet.enums.ReturnStatusEnum;
import com.ttm.pet.model.dto.Community;
import com.ttm.pet.model.pojo.DataResult;
import com.ttm.pet.model.pojo.ListDataResult;
import com.ttm.pet.model.query.app.CommunityQuery;
import com.ttm.pet.model.query.app.IdQuery;
import com.ttm.pet.model.vo.app.CommunityListVo;
import com.ttm.pet.service.CommunityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 乐乐页面社群
 * </p>
 *
 * @author cx
 * @since 2020-05-26
 */
@RestController
@Api(value ="/app/community",tags = "app-community",description = "乐乐社群相关接口")
@RequestMapping("/app/community")
public class MiniCommunityController {
    private final static Logger logger = LoggerFactory.getLogger(MiniCommunityController.class);
    @Autowired
    private CommunityService communityService;

    /**
     * 发布社群信息
     * @param communityQuery
     * @return
     */
    @ApiOperation(httpMethod = "POST", value = "发布社群信息", nickname = "1")
    @RequestMapping(value = "/information" ,method = RequestMethod.POST)
    private DataResult addInformation(@RequestBody CommunityQuery communityQuery){
        DataResult result = new DataResult();
        try {
            Community community = new Community();

            BeanUtils.copyProperties(communityQuery,community);
            communityService.insert(community);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("发布社群信息失败:{}", e);
            result.setCode(ReturnStatusEnum.SYS_ERROR.getValue());
            result.setMsg("发布失败");
            return result;
        }
    }

    /**
     * 删除社群信息
     * @return
     */
    @ApiOperation(httpMethod = "DELETE", value = "删除自己的社群信息",notes = "作品id  只有自己的作品才可以看到删除选项",nickname = "2")
    @RequestMapping(value = "/information/{id}" ,method = RequestMethod.DELETE)
    private DataResult deleteInformation(@PathVariable(value = "id") Long id){
        DataResult result = new DataResult();
        try {
            communityService.deleteById(id);
            result.setMsg("删除成功！");
        }catch (Exception e){
            e.printStackTrace();
            logger.error("删除社群信息id:" + id+",失败："+e.getMessage());
            result.setCode(ReturnStatusEnum.SYS_ERROR.getValue());
            result.setMsg("删除社群信息失败");
            return result;
        }
        return result;
    }

    /**
     * 转发社群信息到全国
     * @return
     */
    @ApiOperation(httpMethod = "PUT", value = "转发社群信息到全国",notes = "作品id  只有自己的作品才可以看到转发选项",nickname = "3")
    @RequestMapping(value = "/country" ,method = RequestMethod.PUT)
    private DataResult updateInformation(@RequestBody IdQuery idQuery){
        DataResult result = new DataResult();
        try {
            Community community = new Community();
            community.setId(idQuery.getId());
            community.setIsCountry(1);
            communityService.updateById(community);
            result.setMsg("转发成功！");
        }catch (Exception e){
            e.printStackTrace();
            logger.error("转发社群信息到全国 信息id:" + idQuery.getId()+",失败："+e.getMessage());
            result.setCode(ReturnStatusEnum.SYS_ERROR.getValue());
            result.setMsg("转发失败");
            return result;
        }
        return result;
    }

    /**
     * 查询社群列表
     * @return
     */
    @ApiOperation(httpMethod = "GET", value = "查询社群列表",nickname = "4")
    @RequestMapping(value = "/information" ,method = RequestMethod.GET)
    private ListDataResult getInformation(@ApiParam(value = "页数", required = true) @RequestParam("page") Integer page,
                                          @ApiParam(value = "每页个数", required = true) @RequestParam("size") Integer size,
                                          @ApiParam(value = "城市id 全国的传0", required = true) @RequestParam("cityId") Integer cityId){
        ListDataResult result = new ListDataResult();
        try {
            Page<CommunityListVo> communityListVoPage = new Page<>(page,size);
            communityListVoPage = communityService.findCommunityListVos(communityListVoPage,cityId);
            result.setCurrent(communityListVoPage.getCurrent());
            result.setTotal(communityListVoPage.getTotal());
            result.setData(communityListVoPage.getRecords());
            return result;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("查询社群列表失败", e.getMessage());
            result.setCode(ReturnStatusEnum.RFC_ERROR.getValue());
            result.setMsg("查询失败");
            return result;
        }
    }
}
