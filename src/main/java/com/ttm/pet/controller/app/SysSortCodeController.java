package com.ttm.pet.controller.app;

import com.ttm.pet.model.pojo.DataResult;
import com.ttm.pet.service.SysSortCodeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 系统统一编码控制层
 *
 * @author J
 * @date 2021/3/9
 */
@Api(value = "/app/sysCode", tags = "app-sysCode", description = "系统统一编码")
@RestController
@RequestMapping("/app/sysCode")
public class SysSortCodeController {

    @Autowired
    private SysSortCodeService sysSortCodeService;

    @ApiOperation(httpMethod = "GET", value = "获取统一编码列表")
    @GetMapping("/querySysSortCode")
    public DataResult querySysSortCode(
            @ApiParam(value = "分类编码", required = true) @RequestParam("codeSortId") String codeSortId) {
        return sysSortCodeService.listSysSortCode(codeSortId);
    }
}
