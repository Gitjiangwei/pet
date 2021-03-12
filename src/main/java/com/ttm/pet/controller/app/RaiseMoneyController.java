package com.ttm.pet.controller.app;

import com.ttm.pet.model.pojo.DataResult;
import com.ttm.pet.service.RaiseMoneyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 筹钱控制层
 *
 * @author J
 * @date 2021/3/6
 */
@RestController
@RequestMapping("/app/raiseMoney")
@Api(value = "/app/raiseMoney", tags = "app-raiseMoney", description = "乐乐筹钱相关接口")
public class RaiseMoneyController {

    @Autowired
    private RaiseMoneyService raiseMoneyService;

    /**
     * 获取筹钱信息
     *
     * @param id 检索条件
     * @return 筹钱数据
     * @author J
     * @date 2021/3/6
     */
    @ApiOperation(value = "筹钱详情", httpMethod = "GET")
    @GetMapping("/queryRaiseMoneyDetail")
    public DataResult queryRaiseMoneyDetail(
            @ApiParam(value = "基地id", required = true) @RequestParam(value = "id", required = true) Long id) {
        return raiseMoneyService.getRaiseMoney(id);
    }
}
