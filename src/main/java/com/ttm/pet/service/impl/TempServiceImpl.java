package com.ttm.pet.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ttm.pet.model.dto.Temp;
import com.ttm.pet.dao.TempMapper;
import com.ttm.pet.service.TempService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.ttm.pet.util.HttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * <p>
 * 用来存放accessToken (之后需用redis) 服务实现类
 * </p>
 *
 * @author cx
 * @since 2020-09-16
 */
@Service
@PropertySource(value = {"classpath:wx.properties"})
public class TempServiceImpl extends ServiceImpl<TempMapper, Temp> implements TempService {
    private final static Logger logger = LoggerFactory.getLogger(TempServiceImpl.class);

    @Value("${wx.miniAppID}")
    private String appId;
    @Value("${wx.appSecret}")
    private String appSecret;

    @Value("${wx.gzhAppId}")
    private String gzhAppID;
    @Value("${wx.gzhAppSecret}")
    private String gzhSecret;

    @Autowired
    private TempMapper tempMapper;

    @Override
    public String getAssessToken() {
        try {
            Temp temp = tempMapper.selectById(1);
            Date date = new Date();
            if (temp.getEffectiveTime().before(date)){
                String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+appId+"&secret="+appSecret;
                String response = HttpUtils.httpsRequestToString(url, "GET", null);
                System.out.println(response);
                JSONObject json = JSON.parseObject(response);
                if(json.get("access_token") != null){
                    temp.setValue(json.get("access_token").toString());
                    long time = date.getTime() + 6000000;
                    temp.setEffectiveTime(new Date(time));
                    tempMapper.updateById(temp);
                    return json.get("access_token").toString();
                }else {
                    logger.error("查询小程序最新的assess token失败：" + response);
                    return null;
                }
            }else {
                return temp.getValue();
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("查询小程序最新的assess token失败：" + e.getMessage());
            return null;
        }

    }

    @Override
    public String getGzhAssessToken() {
        try {
            Temp temp = tempMapper.selectById(2);
            Date date = new Date();
            if (temp.getEffectiveTime().before(date)){
                String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+gzhAppID+"&secret="+gzhSecret;
                String response= HttpUtils.httpsRequestToString(url, "GET", null);
                System.out.println(response);
                logger.info("公众号h5页面分享参数获取 accessToken response:"+response);

                JSONObject json = JSON.parseObject(response);
                if(json.get("access_token") != null){
                    temp.setValue(json.get("access_token").toString());
                    long time = date.getTime() + 6000000;
                    temp.setEffectiveTime(new Date(time));
                    tempMapper.updateById(temp);
                    return json.get("access_token").toString();
                }else {
                    logger.error("查询公众号最新的assess token失败：" + response);
                    return null;
                }
            }else {
                return temp.getValue();
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("查询公众号最新的assess token失败：" + e.getMessage());
            return null;
        }
    }

    @Override
    public String getGzhJsapiTicket(String accessToken) {
        try {
            Temp temp = tempMapper.selectById(3);
            Date date = new Date();
            if (temp.getEffectiveTime().before(date)){
                String ticketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+accessToken+"&type=jsapi";
                String ticketResponse= HttpUtils.httpsRequestToString(ticketUrl, "GET", null);
                logger.info("公众号h5页面分享参数 ticketResponse:"+ticketResponse);
                String ticket = JSON.parseObject(ticketResponse).getString("ticket");

                if(ticket != null){
                    temp.setValue(ticket);
                    long time = date.getTime() + 6000000;
                    temp.setEffectiveTime(new Date(time));
                    tempMapper.updateById(temp);
                    return ticket;
                }else {
                    logger.error("查询公众号最新的jsapi_ticket失败：" + ticketResponse);
                    return null;
                }
            }else {
                return temp.getValue();
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("查询公众号最新的jsapi_ticket失败：" + e.getMessage());
            return null;
        }
    }
}
