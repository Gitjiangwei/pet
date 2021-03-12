package com.ttm.pet.controller.app;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ttm.pet.enums.ConstantsEnum;
import com.ttm.pet.enums.PathEnum;
import com.ttm.pet.enums.ReturnStatusEnum;
import com.ttm.pet.model.dto.*;
import com.ttm.pet.model.pojo.BaseResult;
import com.ttm.pet.model.pojo.DataResult;
import com.ttm.pet.service.*;
import com.ttm.pet.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 通用接口 前端控制器
 * </p>
 *
 * @author cx
 * @since 2020-03-27
 */
@RestController
@Api(value = "/app/system",tags = "app-system",description = "系统相关接口")
@RequestMapping("/app/system")
@PropertySource(value = {"classpath:wx.properties"})
public class SystemApiController {
    private final static Logger logger = LoggerFactory.getLogger(SystemApiController.class);

    @Autowired
    private CustomerService customerService;
    @Autowired
    private VerificationCodeService verificationCodeService;
    @Autowired
    private CityService cityService;
    @Autowired
    private TempService tempService;
    @Autowired
    private AgreementService agreementService;
    @Autowired
    private ContactService contactService;
    @Autowired
    private FeedbackService feedbackService;
    @Autowired
    private SystemImgService systemImgService;
    @Autowired
    private ConfigService configService;

    @Value("${wx.miniAppID}")
    private String miniAppid;
    @Value("${wx.appSecret}")
    private String appSecret;

    @Value("${wx.gzhAppId}")
    private String gzhAppID;
    @Value("${wx.gzhAppSecret}")
    private String gzhSecret;

    @ApiOperation( value = "图片上传", notes = "图片上传:name=file")
    @RequestMapping(value="upload", method={RequestMethod.POST } )
    public DataResult fileUploadWatermark(HttpServletRequest request, HttpServletResponse response){
        DataResult result = new DataResult();
        String val = FileUtils.uploadImg(request, "file",PathEnum.CUSTOMER_UPLOAD_PATH.getPath());
        result.setData(val);
        return result;
    }

    /**
     * 发送验证码
     * @param phoneNum codeType
     * @return
     */
    @ApiOperation( httpMethod = "GET",value = "发送验证码",notes = "codeType 1-注册验证码类型,2-重置密码类型，3-绑定账户")
    @RequestMapping(value="identifyingCode", method={RequestMethod.GET } )
    public BaseResult sendPhoneCodeApi(@ApiParam(value = "手机号码" ,required=true ) @RequestParam("phoneNum") String phoneNum,
                                       @ApiParam(value = "验证码类型" ,required=true ) @RequestParam("codeType") int codeType) {
        BaseResult result = new BaseResult();
        StringBuffer sb = new StringBuffer();
        for(int i = 0;i < 6;i++){
            int num = (int)(Math.random()*(10));
            sb.append(String.valueOf(num));
        }
        String code = sb.toString();
        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setCode(Integer.parseInt(code));
        verificationCode.setMobile(phoneNum);
        verificationCode.setType(codeType);
        try {
            if(ConstantsEnum.REGIEST_CODE.getCode() == codeType){
                Customer customer = customerService.selectOne(new EntityWrapper<Customer>().eq("mobile",phoneNum));
                if(null != customer){
                    return new BaseResult(ReturnStatusEnum.CLIENT_ERROR.getValue(),"用户已经存在");
                }else{
                    SendSmsResponse msgRes = SMSUtil.sendMessage(code,phoneNum);
                    System.out.println(msgRes.getCode());
                    System.out.println(msgRes.getMessage());
                    if("OK".equals(msgRes.getCode())){
                        verificationCodeService.insert(verificationCode);
                        return new BaseResult(ReturnStatusEnum.SUCCESS.getValue(),"验证码发送中！");
                    }else{
                        return new BaseResult(ReturnStatusEnum.SYS_ERROR.getValue(),"验证码发送失败！");
                    }
                }
            } else if(ConstantsEnum.RESET_CODE.getCode() == codeType || ConstantsEnum.BIND_CODE.getCode() == codeType){
                SendSmsResponse res = SMSUtil.sendMessage(code,phoneNum);
                System.out.println(res.getCode());
                if("OK".equals(res.getCode())){
                    verificationCodeService.insert(verificationCode);
                    return new BaseResult(ReturnStatusEnum.SUCCESS.getValue(),"验证码发送中！");
                }else{
                    return new BaseResult(ReturnStatusEnum.SYS_ERROR.getValue(),"验证码发送失败！");
                }
            } else{
                return new BaseResult(ReturnStatusEnum.SYS_ERROR.getValue(),"无效验证码类型！");
            }
        } catch (Exception e) {
            logger.error("error", e);
            e.printStackTrace();
            result.setCode(ReturnStatusEnum.SYS_ERROR.getValue());
            result.setMsg(ReturnStatusEnum.SYS_ERROR.getDesc());
            return result;
        }
    }

    /**
     * 查询所有的市
     * @return
     */
    @ApiOperation(httpMethod = "GET",value = "查询所有的市")
    @RequestMapping(value="/citys", method=RequestMethod.GET)
    public DataResult queryCitys(){
        try {
            DataResult dataResult = new DataResult();
            EntityWrapper<City> cityEntityWrapper = new EntityWrapper<>();
            cityEntityWrapper.setSqlSelect("id","city name","province_id provinceId");
            List<Map<String,Object>> citys = cityService.selectMaps(cityEntityWrapper);
            dataResult.setData(citys);
            return dataResult;

        } catch (Exception e) {
            logger.error("查询所有的市异常");
            return new DataResult(ReturnStatusEnum.SERVER_ERROR.getValue(),"查询失败！",null);
        }
    }


    /**
     * 获取小程序码
     * @return
     */
    @ApiOperation( httpMethod = "GET",value = "获取小程序码")
    @RequestMapping(value="getwxacodeunlimit", method={RequestMethod.GET } )
    public DataResult getwxacodeunlimit(String sceneStr, String page) {
        DataResult dataResult = new DataResult();
        RestTemplate rest = new RestTemplate();
        try {
//            String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+miniAppid+"&secret="+appSecret;
//            String response= HttpUtils.httpsRequestToString(accessTokenUrl, "GET", null);
//            String accessToken = JSON.parseObject(response).getString("access_token");
            String accessToken = tempService.getAssessToken();
            String url = "https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="+accessToken;
            Map<String,Object> param = new HashMap<>();
            param.put("scene", sceneStr);
            param.put("page", page);
            param.put("width", 430);
            param.put("auto_color", false);
            Map<String,Object> line_color = new HashMap<>();
            line_color.put("r", 0);
            line_color.put("g", 0);
            line_color.put("b", 0);
            param.put("line_color", line_color);
            logger.info("调用生成微信URL接口传参:" + param);
            MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
            HttpEntity requestEntity = new HttpEntity(param, headers);
            try {
                //说明是错误的
                ResponseEntity<Object> entity = rest.exchange(url, HttpMethod.POST, requestEntity,Object.class, new Object[0]);
                logger.info("调用小程序生成微信永久小程序码URL接口返回结果:" + entity.getBody());
                Object result = entity.getBody();
                dataResult.setCode(ReturnStatusEnum.CLIENT_ERROR.getValue());
                dataResult.setData("微信返回失败");
                dataResult.setData(result.toString());
            } catch (RestClientException e){
                ResponseEntity<byte[]> entity = rest.exchange(url, HttpMethod.POST, requestEntity,byte[].class, new Object[0]);
                logger.info("调用小程序生成微信永久小程序码URL接口返回结果:" + entity.getBody());
                byte[] result = entity.getBody();
                logger.info("小程序码base64：" + Base64.encodeBase64String(result));
                dataResult.setData(Base64.encodeBase64String(result));
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("调用小程序生成微信永久小程序码URL接口异常", e);
            return new DataResult(ReturnStatusEnum.SERVER_ERROR.getValue(),"获取失败！",null);
        }
        return dataResult;
    }


    /**
     * 微信内容安全-检查文本
     * @return
     */
    @ApiOperation( httpMethod = "GET",value = "微信内容安全-检查文本")
    @RequestMapping(value="msgSecCheck", method={RequestMethod.GET } )
    public String msgSecCheck(@ApiParam(value = "内容", required = true) @RequestParam(value = "id") String content) {
        String accessToken = tempService.getAssessToken();
        String url = "https://api.weixin.qq.com/wxa/msg_sec_check?access_token="+accessToken;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("content",content);
        String response = HttpUtils.httpsRequestToString(url, "POST", jsonObject.toString());
        return response;
    }

    /**
     * 微信内容安全-检查图片/音频
     * @return
     */
    @ApiOperation( httpMethod = "GET",value = "微信内容安全-检查图片/音频")
    @RequestMapping(value="mediaCheckAsync", method={RequestMethod.GET } )
    public String mediaCheckAsync(@ApiParam(value = "要检测的多媒体url", required = true) @RequestParam(value = "media_url") String media_url,
                                  @ApiParam(value = "1:音频;2:图片", required = true) @RequestParam(value = "media_type") Integer media_type) {
        String accessToken = tempService.getAssessToken();
        String url = " https://api.weixin.qq.com/wxa/media_check_async?access_token="+accessToken;
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("media_url",media_url);
        jsonObject.put("media_type",media_type);
        String response = HttpUtils.httpsRequestToString(url, "POST", jsonObject.toString());
        return response;
    }

    /**
     * 获取各种协议
     * @return
     */
    @ApiOperation( httpMethod = "GET",value = "获取各种协议")
    @RequestMapping(value="agreement", method={RequestMethod.GET } )
    public DataResult getAgreement() {
        DataResult dataResult = new DataResult();
        try {
            EntityWrapper<Agreement> agreementEntityWrapper = new EntityWrapper<>();
            agreementEntityWrapper.setSqlSelect("id","title","link","sort");
            agreementEntityWrapper.orderBy("sort",true);
            List<Map<String,Object>> map = agreementService.selectMaps(agreementEntityWrapper);
            dataResult.setData(map);
            return dataResult;
        } catch (Exception e) {
            logger.error("获取各种协议异常"+e.getMessage());
            return new DataResult(ReturnStatusEnum.SERVER_ERROR.getValue(),"查询失败！",null);
        }
    }

    /**
     * 获取协议详情
     * @return
     */
    @ApiOperation( httpMethod = "GET",value = "获取协议详情")
    @RequestMapping(value="agreementDetail", method={RequestMethod.GET } )
    public DataResult getAgreementDetail(@ApiParam(value = "协议id", required = true) @RequestParam(value = "id") Integer id) {
        DataResult dataResult = new DataResult();
        try {
            Agreement agreement = agreementService.selectById(id);
            dataResult.setData(agreement);
            return dataResult;
        } catch (Exception e) {
            logger.error("获取协议详情异常"+e.getMessage());
            return new DataResult(ReturnStatusEnum.SERVER_ERROR.getValue(),"查询失败！",null);
        }
    }

    /**
     * 获取联系我们各项
     * @return
     */
    @ApiOperation( httpMethod = "GET",value = "获取联系我们各项")
    @RequestMapping(value="contact", method={RequestMethod.GET } )
    public DataResult getContact() {
        DataResult dataResult = new DataResult();
        try {
            List<Contact> contacts = contactService.selectList(new EntityWrapper<Contact>());
            dataResult.setData(contacts);
            return dataResult;
        } catch (Exception e) {
            logger.error("获取联系我们各项异常"+e.getMessage());
            return new DataResult(ReturnStatusEnum.SERVER_ERROR.getValue(),"查询失败！",null);
        }
    }

    /**
     * 用户进行反馈与帮助
     * @param feedback
     * @return
     */
    @ApiOperation(httpMethod = "POST", value = "用户进行反馈与帮助",notes = "createTime 和 deleted 不用传")
    @RequestMapping(value = "/feedback",method = RequestMethod.POST)
    private DataResult setFeedback(@RequestBody Feedback feedback){
        DataResult result = new DataResult();
        try {
            feedbackService.insert(feedback);
            return result;
        }catch (Exception e){
            logger.error("反馈失败:" + e.getMessage());
            result.setCode(ReturnStatusEnum.SYS_ERROR.getValue());
            result.setMsg("反馈失败");
            return result;
        }
    }

    /**
     * 获取首页弹窗图
     * @return
     */
    @ApiOperation( httpMethod = "GET",value = "获取首页弹窗图")
    @RequestMapping(value="indexPop", method={RequestMethod.GET } )
    public DataResult getIndexPop() {
        DataResult dataResult = new DataResult();
        try {
            SystemImg systemImg = systemImgService.selectById(3);
            Map<String,String> map = new HashMap<>(1);
            map.put("images",systemImg.getValue());
            dataResult.setData(map);
            return dataResult;
        } catch (Exception e) {
            logger.error("获取首页弹窗图异常"+e.getMessage());
            return new DataResult(ReturnStatusEnum.SERVER_ERROR.getValue(),"查询失败！",null);
        }
    }

    /**
     * 获取app启动页和广告图
     * @return
     */
    @ApiOperation( httpMethod = "GET",value = "获取app启动页和广告图")
    @RequestMapping(value="startImg", method={RequestMethod.GET } )
    public DataResult getStartImg() {
        DataResult dataResult = new DataResult();
        try {
            SystemImg systemImg = systemImgService.selectById(4);
            SystemImg systemImg2 = systemImgService.selectById(5);
            Map<String,String> map = new HashMap<>(2);
            map.put("startImg",systemImg.getValue());
            map.put("advertisementImg",systemImg2.getValue());
            dataResult.setData(map);
            return dataResult;
        } catch (Exception e) {
            logger.error("获取app启动页和广告图异常"+e.getMessage());
            return new DataResult(ReturnStatusEnum.SERVER_ERROR.getValue(),"查询失败！",null);
        }
    }

    /**
     * 公众号h5页面分享参数
     * @return
     */
    @ApiOperation( httpMethod = "GET",value = "公众号h5页面分享参数")
    @RequestMapping(value="shareH5", method={RequestMethod.GET } )
    public DataResult getShareH5(@ApiParam(value = "生成签名所需url", required = true) @RequestParam(value = "url") String url) {
        DataResult dataResult = new DataResult();
        try {
            //第一步：拿到accessToken
//            String accessTokenUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+gzhAppID+"&secret="+gzhSecret;
//            String response= HttpUtils.httpsRequestToString(accessTokenUrl, "GET", null);
//            String accessToken = JSON.parseObject(response).getString("access_token");
//            logger.info("公众号h5页面分享参数 accessToken response:"+response);
            String accessToken = tempService.getGzhAssessToken();
            logger.info("公众号分享accessToken:"+accessToken);
            //第二步：根据token获取jsapi_ticket
//            String ticketUrl = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token="+accessToken+"&type=jsapi";
//            String ticketResponse= HttpUtils.httpsRequestToString(ticketUrl, "GET", null);
//            String ticket = JSON.parseObject(ticketResponse).getString("ticket");
//            logger.info("公众号h5页面分享参数 ticketResponse:"+ticketResponse);
            String ticket = tempService.getGzhJsapiTicket(accessToken);
            logger.info("公众号分享jsapi_ticket:"+ticket);
            //第三步：签名

            String nonce_str = UUIDHexGeneratorUtils.generate();
            Date date = new Date();
            Long timeStamp = DateUtil.date2long(date);
            String temp = "jsapi_ticket=" + ticket +
                    "&noncestr=" + nonce_str +
                    "&timestamp=" + timeStamp +
                    "&url=" + url;
            System.out.println(temp);
            logger.info("公众号h5页面分享参数 tempString:"+temp);
            String sign = DigestUtils.sha1Hex(temp);
            Map<String,Object> ret = new HashMap<>(5);
            ret.put("url", url);
            ret.put("appId", gzhAppID);
            ret.put("nonceStr", nonce_str);
            ret.put("timestamp", timeStamp);
            ret.put("signature", sign);
            dataResult.setData(ret);
            return dataResult;
        } catch (Exception e) {
            logger.error("获取公众号h5页面分享参数异常"+e.getMessage());
            return new DataResult(ReturnStatusEnum.SERVER_ERROR.getValue(),"获取失败！",null);
        }
    }

    /**
     * 获取app配置
     * @return
     */
    @ApiOperation( httpMethod = "GET",value = "获取app配置")
    @RequestMapping(value="config", method={RequestMethod.GET } )
    public DataResult getConfig() {
        DataResult dataResult = new DataResult();
        try {
            EntityWrapper<Config> configEntityWrapper = new EntityWrapper<>();
            configEntityWrapper.setSqlSelect("name"," FLOOR(value) value","remark");
            configEntityWrapper.gt("id",1000);
            List<Map<String,Object>> configs = configService.selectMaps(configEntityWrapper);
            dataResult.setData(configs);
            return dataResult;
        } catch (Exception e) {
            logger.error("获取app配置异常"+e.getMessage());
            return new DataResult(ReturnStatusEnum.SERVER_ERROR.getValue(),"查询失败！",null);
        }
    }
    /**
     * 获取小程序是否展示视频配置
     * @return
     */
    @ApiOperation( httpMethod = "GET",value = "获取小程序是否展示视频配置")
    @RequestMapping(value="miniConfig", method={RequestMethod.GET } )
    public DataResult getMiniConfig() {
        DataResult dataResult = new DataResult();
        try {
            EntityWrapper<Config> configEntityWrapper = new EntityWrapper<>();
            configEntityWrapper.setSqlSelect("name"," FLOOR(value) value","remark");
            configEntityWrapper.eq("id",6);
            List<Map<String,Object>> configs = configService.selectMaps(configEntityWrapper);
            dataResult.setData(configs);
            return dataResult;
        } catch (Exception e) {
            logger.error("获取小程序是否展示视频配置异常"+e.getMessage());
            return new DataResult(ReturnStatusEnum.SERVER_ERROR.getValue(),"查询失败！",null);
        }
    }
}
