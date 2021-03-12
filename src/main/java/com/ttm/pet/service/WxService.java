package com.ttm.pet.service;

import com.alibaba.fastjson.JSONObject;

import java.util.Map;

public interface WxService {
    //小程序获取sessionKey
    public JSONObject code2sessionKey(String jscCode);

    //小程序得到信息
    public JSONObject decryptionUserInfo(String encryptedData, String sessionKey, String iv);

    //公众号网页授权登陆url  第一步
    public String getCodeUrl();

    //公众号网页授权换取assessToken  第二步
    public JSONObject code2AccessToken(String code);

    //公众号验证assessToken是否失效 第五步
    public JSONObject verifyToken(String accessToken, String openId);

    //公众号刷新access_token（如果需要） 第三步
    public JSONObject refreshToken(String refreshToken);

    //公众号拉取用户信息  第四步
    public JSONObject getUserInfo(String accessToken, String openId);
}
