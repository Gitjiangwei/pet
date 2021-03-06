package com.ttm.pet.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import com.ttm.pet.service.WxService;
import com.ttm.pet.util.HttpUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.net.URLEncoder;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.Arrays;
import java.util.Map;

@Service
@PropertySource(value = {"classpath:wx.properties"})
public class WxServiceImpl implements WxService {
    private final static Logger logger = LoggerFactory.getLogger(WxServiceImpl.class);

    @Value("${wx.miniAppID}")
    private String appId;
    @Value("${wx.appSecret}")
    private String appSecret;
    @Value("${wx.jsCode2sessionUrl}")
    private String jsCode2sessionUrl;

    @Value("${wx.gzhAppId}")
    private String gzhAppId;
    @Value("${wx.gzhAppSecret}")
    private String gzhAppSecret;
    @Value("${pet.h5CallBackUrl}")
    private String h5CallBackUrl;

    @Override
    public JSONObject code2sessionKey(String jscCode) {
        try {
            StringBuffer url = new StringBuffer();
            url.append(jsCode2sessionUrl);
            url.append("appid=");
            url.append(appId);
            url.append("&secret=");
            url.append(appSecret);
            url.append("&js_code=");
            url.append(jscCode);
            url.append("&grant_type=authorization_code");
            System.out.println("url+"+url);
            String response = HttpUtils.httpsRequestToString(url.toString(), "GET", null);
            System.out.println(response);
            return JSON.parseObject(response);
        } catch (Exception e){
            e.printStackTrace();
            logger.error("??????code??????sessionKey?????????" + e.getMessage());
            return null;
        }
    }

    @Override
    public JSONObject decryptionUserInfo(String encryptedData, String sessionKey, String iv) {
        // ??????????????????
        byte[] dataByte = Base64.decode(encryptedData);
        // ????????????
        byte[] keyByte = Base64.decode(sessionKey);
        // ?????????
        byte[] ivByte = Base64.decode(iv);

        try {
            // ??????????????????16?????????????????????. ??????if ?????????????????????
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // ?????????
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// ?????????
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                return JSONObject.parseObject(result);

            }else {
                logger.error("???????????????" );
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("???????????????" + e.getMessage());
            return null;
        }
    }

    @Override
    public String getCodeUrl() {
        // ???????????????????????????????????????code
        String getCodeUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=" + gzhAppId + "&redirect_uri="
                + URLEncoder.encode(h5CallBackUrl) + "&response_type=code" + "&scope=snsapi_userinfo"
                + "&state=STATE#wechat_redirect";

        logger.info("??????code, getCodeUrl=" + getCodeUrl);
        return getCodeUrl;
    }

    @Override
    public JSONObject code2AccessToken(String code) {
        try {
            // ??????code??????????????????access_token
            String getTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=" + gzhAppId + "&secret="
                    + gzhAppSecret + "&code=" + code + "&grant_type=authorization_code";
            logger.info("??????token,getTokenUrl=" + getTokenUrl);
            System.out.println("??????????????????access_token_url+"+getTokenUrl);
            String response = HttpUtils.httpsRequestToString(getTokenUrl, "GET", null);
            System.out.println(response);
            return JSON.parseObject(response);
        } catch (Exception e){
            e.printStackTrace();
            logger.error("??????code????????????????????????????????????" + e.getMessage());
            return null;
        }
    }

    @Override
    public JSONObject verifyToken(String accessToken, String openId) {
        try {
            String verifyTokenUrl = "https://api.weixin.qq.com/sns/auth?access_token=" + accessToken + "&openid=" + openId;
            logger.info("??????token,verifyTokenUrl=" + verifyTokenUrl);
            String response = HttpUtils.httpsRequestToString(verifyTokenUrl, "GET", null);
            return JSON.parseObject(response);
        } catch (Exception e){
            e.printStackTrace();
            logger.error("??????????????????assessToken?????????????????????" + e.getMessage());
            return null;
        }
    }

    @Override
    public JSONObject refreshToken(String refreshToken) {
        try {
            String refreshTokenUrl = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=" + gzhAppId
                    + "&grant_type=refresh_token&refresh_token=" + refreshToken;
            logger.info("??????token,refreshTokenUrl=" + refreshTokenUrl);
            String response = HttpUtils.httpsRequestToString(refreshTokenUrl, "GET", null);
            return JSON.parseObject(response);
        } catch (Exception e){
            e.printStackTrace();
            logger.error("??????access_token?????????" + e.getMessage());
            return null;
        }
    }

    @Override
    public JSONObject getUserInfo(String accessToken, String openId) {
        try {
            String getUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=" + accessToken + "&openid=" + openId
                    + "&lang=zh_CN";
            logger.info("?????????????????????getUserInfoUrl=" + getUserInfoUrl.toString());
            String response = HttpUtils.httpsRequestToString(getUserInfoUrl, "GET", null);
            return JSON.parseObject(response);
        } catch (Exception e){
            e.printStackTrace();
            logger.error("????????????????????????????????????" + e.getMessage());
            return null;
        }
    }
}
