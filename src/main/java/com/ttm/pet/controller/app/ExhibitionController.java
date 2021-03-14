package com.ttm.pet.controller.app;

import com.ttm.pet.model.dto.DeleteExhibitionCollectionParam;
import com.ttm.pet.model.dto.ExhibitionCollection;
import com.ttm.pet.model.pojo.DataResult;
import com.ttm.pet.model.pojo.ListDataResult;
import com.ttm.pet.service.ExhibitionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Api(value = "/app/exhibition", tags = "app-exhibition", description = "展会信息")
@RestController
@RequestMapping("/app/exhibition")
public class ExhibitionController {

    @Autowired
    private ExhibitionService exhibitionService;

    @ApiOperation(httpMethod = "GET", value = "展会列表")
    @GetMapping("/queryExhibition")
    public ListDataResult queryExhibition(@ApiParam(value = "页数", required = true) @RequestParam("page") Integer page,
            @ApiParam(value = "每页个数", required = true) @RequestParam("size") Integer size) {
        return exhibitionService.listExhibition(page, size);
    }

    @ApiOperation(httpMethod = "GET", value = "展会展位信息")
    @GetMapping("/queryExhibitionBooth")
    public DataResult queryExhibitionBooth(
            @ApiParam(value = "展会id", required = true) @RequestParam("exhibitionId") Long exhibitionId) {
        return exhibitionService.listExhibitionBooth(exhibitionId);
    }

    @ApiOperation(httpMethod = "GET", value = "展位详情")
    @GetMapping("/queryExhibitionBoothDetail")
    public DataResult queryExhibitionBoothDetail(
            @ApiParam(value = "展位id", required = true) @RequestParam("exhibitionBoothId") Long exhibitionBoothId) {
        return exhibitionService.getExhibitionBoothDetail(exhibitionBoothId);
    }

    @ApiOperation(httpMethod = "POST", value = "展位收藏")
    @PostMapping("/saveExhibitionCollection")
    public DataResult saveExhibitionCollection(@RequestBody @Valid ExhibitionCollection exhibitionCollection) {
        return exhibitionService.saveExhibitionCollection(exhibitionCollection);
    }

    @ApiOperation(httpMethod = "POST", value = "取消收藏，可用于批量")
    @PostMapping("/clearExhibitionCollection")
    public DataResult clearExhibitionCollection(
            @RequestBody @Valid DeleteExhibitionCollectionParam deleteExhibitionCollectionParam) {
        return exhibitionService.deleteExhibitionCollection(deleteExhibitionCollectionParam);
    }

    @ApiOperation(httpMethod = "GET", value = "获取展位收藏列表")
    @GetMapping("/queryPageExhibitionCollection")
    public ListDataResult queryPageExhibitionCollection(
            @ApiParam(value = "收藏人UUID", required = true) @RequestParam("customerId") String customerId,
            @ApiParam(value = "页数", required = true) @RequestParam("page") Integer page,
            @ApiParam(value = "每页个数", required = true) @RequestParam("size") Integer size) {
        return exhibitionService.listExhibitionCollection(customerId, page, size);
    }

    @ApiOperation(httpMethod = "GET", value = "获取收藏的展位详情信息")
    @GetMapping("/queryExhibitionCollectDetail")
    public DataResult queryExhibitionCollectDetail(
            @ApiParam(value = "收藏主键id", required = true) @RequestParam("collId") Long collId) {
        return exhibitionService.getExhibitionCollectDetail(collId);
    }

    @ApiOperation(httpMethod = "GET", value = "获取我的展位订单列表")
    @GetMapping("/queryPageExhibitionBoothPay")
    public ListDataResult queryPageExhibitionBoothPay(
            @ApiParam(value = "收藏人UUID", required = true) @RequestParam("customerId") String customerId,
            @ApiParam(value = "页数", required = true) @RequestParam("page") Integer page,
            @ApiParam(value = "每页个数", required = true) @RequestParam("size") Integer size) {
        return exhibitionService.listExhibitionBoothPay(customerId, page, size);
    }

    @ApiOperation(httpMethod = "GET", value = "获取我的展位订单详情信息")
    @GetMapping("/queryExhibitionPayDetail")
    public DataResult queryExhibitionPayDetail(
            @ApiParam(value = "订单交易id", required = true) @RequestParam("payId") Long payId) {
        return exhibitionService.getExhibitionPayDetail(payId);
    }
}
