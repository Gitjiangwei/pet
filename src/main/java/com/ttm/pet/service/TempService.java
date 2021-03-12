package com.ttm.pet.service;

import com.ttm.pet.model.dto.Temp;
import com.baomidou.mybatisplus.service.IService;

/**
 * <p>
 * 用来存放accessToken (之后需用redis) 服务类
 * </p>
 *
 * @author cx
 * @since 2020-09-16
 */
public interface TempService extends IService<Temp> {

    //查询小程序最新的assess token
    public String getAssessToken();

    //查询公众号最新的assess token
    public String getGzhAssessToken();

    //查询公众号最新的jsapi_ticket
    public String getGzhJsapiTicket(String accessToken);
}
