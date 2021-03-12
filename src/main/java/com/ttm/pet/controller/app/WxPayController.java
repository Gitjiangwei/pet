package com.ttm.pet.controller.app;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ttm.pet.enums.ConstantsEnum;
import com.ttm.pet.enums.ReturnStatusEnum;
import com.ttm.pet.model.dto.*;
import com.ttm.pet.model.pojo.DataResult;
import com.ttm.pet.model.query.app.GoodsPayQuery;
import com.ttm.pet.model.query.app.VipPayQuery;
import com.ttm.pet.model.query.app.WxPayQuery;
import com.ttm.pet.service.*;
import com.ttm.pet.util.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.xml.crypto.Data;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@Api(value = "/app/pay", tags = "app-pay", description = "微信支付相关接口")
@RequestMapping("/app/pay")
@PropertySource(value = { "classpath:wx.properties" })
public class WxPayController {
    private final static Logger logger = LoggerFactory.getLogger(WxPayController.class);

    @Autowired
    private PayService payService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private BaseService baseService;

    @Autowired
    private GoodsOrderService goodsOrderService;

    @Autowired
    private ConfigService configService;

    @Autowired
    private VipOrderService vipOrderService;

    @Autowired
    private BusinessService businessService;

    @Autowired
    private ExhibitionBoothPayService exhibitionBoothPayService;

    @Value("${wx.miniAppID}")
    private String miniAppid;

    @Value("${wx.appAppID}")
    private String appAppID;

    @Value("${wx.gzhAppId}")
    private String gzhAppID;

    @Value("${wx.mch_id}")
    private String mch_id;

    @Value("${wx.pay_key}")
    private String key;

    @Value("${wx.pay_url}")
    private String url;

    private final static String notify_url = "https://www.zglele.com:8081/pet/app/pay/notify";

    private final static String notify_url1 = "https://www.zglele.com:8081/pet/app/pay/goodsNotify";

    private final static String notify_url_vip = "https://www.zglele.com:8081/pet/app/pay/vipNotify";

    private final static String NOTIFY_URL_BOOTH = "https://www.zglele.com:8081/pet/app/pay/wxBoothNotify";

    private final static String mini_trade_type = "JSAPI";

    private final static String app_trade_type = "APP";

    private final static String body = "宠艾-宠币充值";

    private final static String body1 = "宠艾-购买宠粮";

    private final static String bodyVip = "宠艾-蓝v会员";

    private final static String EXH_BOOTH = "宠艾-购买展位";

    @Value("${wx.pay_query_url}")
    private String payQueryUrl;

    @ApiOperation(httpMethod = "POST", value = "微信支付回调接口，小程序和客户端勿调", nickname = "2")
    @PostMapping("/wxBoothNotify")
    public String wxBoothNotify(HttpServletRequest request) {
        Map< String, String > returnData = new HashMap<>();
        try {
            InputStream inputStream = request.getInputStream();
            // BufferedReader是包装设计模式，性能更搞
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuffer stringBuffer = new StringBuffer();
            // 1、将微信回调信息转为字符串
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuffer.append(line);
            }
            bufferedReader.close();
            inputStream.close();
            logger.info("回调返回" + stringBuffer.toString());

            // 2、将xml格式字符串格式转为map集合
            Map< String, String > callbackMap = XmlUtil.doXMLParse(stringBuffer.toString());
            logger.info("xml转map" + callbackMap.toString());

            // 3、转为有序的map
            SortedMap< String, String > sortedMap = PayUtil.getSortedMap(callbackMap);

            // 4、判断签名是否正确
            if (PayUtil.isCorrectSign(sortedMap, key)) {
                // 5、判断回调信息是否成功
                if ("SUCCESS".equals(sortedMap.get("result_code"))) {
                    // 更新订单状态
                    exhibitionBoothPayService.updateExhibitionBoothPay(sortedMap.get("out_trade_no"));
                    // 下发成功信息
                    returnData.put("return_code", "SUCCESS");
                    returnData.put("return_msg", "OK");
                }
            }
            else {
                // 7、通知微信订单处理失败
                returnData.put("return_code", "FAIL");
                returnData.put("return_msg", "return_code不正确");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error("wxNotify: 下单失败" + e.getMessage());
            logger.error(e.getMessage(), e);
            returnData.put("return_code", "FAIL");
            returnData.put("return_msg", "return_code不正确");
        }
        return PayUtil.getRequestXml(returnData);
    }

    /**
     * 微信支付通知地址 会员通知
     * 
     * @return
     */
    @ApiOperation(httpMethod = "POST", value = "微信支付回调接口，小程序和客户端勿调", nickname = "2")
    @RequestMapping(value = "/notify", method = RequestMethod.POST)
    @ResponseBody
    public String wxNotify(HttpServletRequest request) {
        logger.info("notify......");
        Map< String, String > return_data = new HashMap<>();
        try {
            InputStream inputStream = request.getInputStream();

            // BufferedReader是包装设计模式，性能更搞
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuffer sb = new StringBuffer();
            // 1、将微信回调信息转为字符串
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            in.close();
            inputStream.close();
            logger.info("回调返回" + sb.toString());

            // 2、将xml格式字符串格式转为map集合
            Map< String, String > callbackMap = XmlUtil.doXMLParse(sb.toString());
            logger.info("xml转map" + callbackMap.toString());

            // 3、转为有序的map
            SortedMap< String, String > sortedMap = PayUtil.getSortedMap(callbackMap);

            // 4、判断签名是否正确
            if (PayUtil.isCorrectSign(sortedMap, key)) {
                // 5、判断回调信息是否成功
                if ("SUCCESS".equals(sortedMap.get("result_code"))) {
                    String outTradeNo = sortedMap.get("out_trade_no");
                    // 6、数据库查找订单,如果存在判断金额，根据订单号更新该订单
                    Pay pay = payService.selectOne(new EntityWrapper< Pay >().eq("out_trade_no", outTradeNo));
                    if (pay != null) {
                        // 对比金额
                        String total_fee = sortedMap.get("total_fee");
                        String price = String
                                .valueOf(pay.getPrice().multiply(new BigDecimal(100)).setScale(0, RoundingMode.DOWN));
                        // 修改订单状态为支付 并且会员更新时间
                        if (total_fee.equals(price)) {
                            pay.setStatus(ConstantsEnum.PAY_STATUS_SUCCESS.getCode());
                            Date successTime = DateUtil.String2Date(sortedMap.get("time_end"));
                            pay.setUpdateTime(successTime);
                            payService.updateById(pay);

                            Customer customer = customerService
                                    .selectOne(new EntityWrapper< Customer >().eq("uuid", pay.getCustomerId()));
                            Integer originalCoin = customer.getPetCoin();
                            Integer increaseCoin = pay.getIncreaseCoin();
                            customer.setPetCoin(originalCoin + increaseCoin);
                            customerService.updateById(customer);
                        }

                        // 下发成功信息
                        return_data.put("return_code", "SUCCESS");
                        return_data.put("return_msg", "OK");
                        return PayUtil.getRequestXml(return_data);
                    }
                }
            }
            // 7、通知微信订单处理失败
            return_data.put("return_code", "FAIL");
            return_data.put("return_msg", "return_code不正确");
            return PayUtil.getRequestXml(return_data);
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error("wxNotify: 下单失败" + e.getMessage());
            logger.error(e.getMessage(), e);
            return_data.put("return_code", "FAIL");
            return_data.put("return_msg", "return_code不正确");
            return PayUtil.getRequestXml(return_data);
        }
    }

    /**
     * 微信支付通知地址 宠粮通知
     * 
     * @return
     */
    @ApiOperation(httpMethod = "POST", value = "微信支付回调接口，小程序和客户端勿调", nickname = "2")
    @RequestMapping(value = "/goodsNotify", method = RequestMethod.POST)
    @ResponseBody
    public String goodsNotify(HttpServletRequest request) {
        logger.info("goodsNotify......");
        Map< String, String > return_data = new HashMap<>();
        try {
            InputStream inputStream = request.getInputStream();

            // BufferedReader是包装设计模式，性能更搞
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuffer sb = new StringBuffer();
            // 1、将微信回调信息转为字符串
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            in.close();
            inputStream.close();
            logger.info("回调返回" + sb.toString());

            // 2、将xml格式字符串格式转为map集合
            Map< String, String > callbackMap = XmlUtil.doXMLParse(sb.toString());
            logger.info("xml转map" + callbackMap.toString());

            // 3、转为有序的map
            SortedMap< String, String > sortedMap = PayUtil.getSortedMap(callbackMap);

            // 4、判断签名是否正确
            if (PayUtil.isCorrectSign(sortedMap, key)) {
                // 5、判断回调信息是否成功
                if ("SUCCESS".equals(sortedMap.get("result_code"))) {
                    String outTradeNo = sortedMap.get("out_trade_no");
                    // 6、数据库查找订单,如果存在判断金额，根据订单号更新该订单
                    GoodsOrder goodsOrder = goodsOrderService
                            .selectOne(new EntityWrapper< GoodsOrder >().eq("out_trade_no", outTradeNo));
                    if (goodsOrder != null) {
                        // 对比金额
                        String total_fee = sortedMap.get("total_fee");
                        String price = String.valueOf(
                                goodsOrder.getPrice().multiply(new BigDecimal(100)).setScale(0, RoundingMode.DOWN));
                        // 修改订单状态为支付 并且会员更新时间
                        if (total_fee.equals(price)) {
                            goodsOrder.setStatus(ConstantsEnum.PAY_STATUS_SUCCESS.getCode());
                            Date successTime = DateUtil.String2Date(sortedMap.get("time_end"));
                            goodsOrder.setUpdateTime(successTime);
                            goodsOrderService.updateById(goodsOrder);
                        }

                        // 下发成功信息
                        return_data.put("return_code", "SUCCESS");
                        return_data.put("return_msg", "OK");
                        return PayUtil.getRequestXml(return_data);
                    }
                }
            }
            // 7、通知微信订单处理失败
            return_data.put("return_code", "FAIL");
            return_data.put("return_msg", "return_code不正确");
            return PayUtil.getRequestXml(return_data);
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error("wxNotify: 下单失败" + e.getMessage());
            logger.error(e.getMessage(), e);
            return_data.put("return_code", "FAIL");
            return_data.put("return_msg", "return_code不正确");
            return PayUtil.getRequestXml(return_data);
        }
    }

    /**
     * 微信支付通知地址 蓝v通知
     * 
     * @return
     */
    @ApiOperation(httpMethod = "POST", value = "微信支付回调接口，小程序和客户端勿调", nickname = "2")
    @RequestMapping(value = "/vipNotify", method = RequestMethod.POST)
    @ResponseBody
    public String vipNotify(HttpServletRequest request) {
        logger.info("vipNotify......");
        Map< String, String > return_data = new HashMap<>();
        try {
            InputStream inputStream = request.getInputStream();

            // BufferedReader是包装设计模式，性能更搞
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
            StringBuffer sb = new StringBuffer();
            // 1、将微信回调信息转为字符串
            String line;
            while ((line = in.readLine()) != null) {
                sb.append(line);
            }
            in.close();
            inputStream.close();
            logger.info("回调返回" + sb.toString());

            // 2、将xml格式字符串格式转为map集合
            Map< String, String > callbackMap = XmlUtil.doXMLParse(sb.toString());
            logger.info("xml转map" + callbackMap.toString());

            // 3、转为有序的map
            SortedMap< String, String > sortedMap = PayUtil.getSortedMap(callbackMap);

            // 4、判断签名是否正确
            if (PayUtil.isCorrectSign(sortedMap, key)) {
                // 5、判断回调信息是否成功
                if ("SUCCESS".equals(sortedMap.get("result_code"))) {
                    String outTradeNo = sortedMap.get("out_trade_no");
                    // 6、数据库查找订单,如果存在判断金额，根据订单号更新该订单
                    VipOrder pay = vipOrderService
                            .selectOne(new EntityWrapper< VipOrder >().eq("out_trade_no", outTradeNo));
                    if (pay != null) {
                        // 对比金额
                        String total_fee = sortedMap.get("total_fee");
                        String price = String
                                .valueOf(pay.getPrice().multiply(new BigDecimal(100)).setScale(0, RoundingMode.DOWN));
                        // 修改订单状态为支付 并且会员更新时间
                        if (total_fee.equals(price)) {
                            pay.setStatus(ConstantsEnum.PAY_STATUS_SUCCESS.getCode());
                            Date successTime = DateUtil.String2Date(sortedMap.get("time_end"));
                            pay.setUpdateTime(successTime);
                            vipOrderService.updateById(pay);

                            Customer customer = customerService
                                    .selectOne(new EntityWrapper< Customer >().eq("uuid", pay.getCustomerId()));
                            customer.setVipEndTime(pay.getNowEndTime());
                            customerService.updateById(customer);
                        }

                        // 下发成功信息
                        return_data.put("return_code", "SUCCESS");
                        return_data.put("return_msg", "OK");
                        return PayUtil.getRequestXml(return_data);
                    }
                }
            }
            // 7、通知微信订单处理失败
            return_data.put("return_code", "FAIL");
            return_data.put("return_msg", "return_code不正确");
            return PayUtil.getRequestXml(return_data);
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error("vipNotify: 下单失败" + e.getMessage());
            logger.error(e.getMessage(), e);
            return_data.put("return_code", "FAIL");
            return_data.put("return_msg", "return_code不正确");
            return PayUtil.getRequestXml(return_data);
        }
    }

    @ApiOperation(httpMethod = "POST", value = "微信购买展位付款", nickname = "0")
    @PostMapping("/exhibitionBoothWxPay")
    public DataResult exhibitionBoothWxPay(HttpServletRequest request,
            @RequestBody @Valid ExhibitionBoothPay exhibitionBoothPay) {
        DataResult dataResult = new DataResult();
        try {
            // 校验当前展位是否售出
            boolean result = exhibitionBoothPayService.checkBoothStatus(exhibitionBoothPay.getBoothId());
            if (!result) {
                return new DataResult(ReturnStatusEnum.CLIENT_ERROR.getValue(), "当前展位已售出!", null);
            }
            String ip = PayUtil.getIpAddr(request);
            if ("0:0:0:0:0:0:0:1".equals(ip)) {
                ip = "127.0.0.1";
            }
            String nonceStr = UUIDHexGeneratorUtils.generate();
            // 单位为分并取证
            BigDecimal amount = exhibitionBoothPay.getPayPrice()
                    .multiply(new BigDecimal(100).setScale(0, RoundingMode.DOWN));
            String appId = appAppID;
            if (exhibitionBoothPay.getType() == 1) {
                appId = miniAppid;
            }
            // 把请求参数打包成数组
            SortedMap< String, String > paramMap = new TreeMap<>();
            paramMap.put("appid", appId);
            paramMap.put("mch_id", mch_id);
            paramMap.put("nonce_str", nonceStr);
            paramMap.put("body", EXH_BOOTH);
            // 生成订单号
            String outTradeNo = "coin" + new SimpleDateFormat("yyyyMMddhhmmss").format(new Date())
                    + PayUtil.createCode(5);
            paramMap.put("out_trade_no", outTradeNo);
            paramMap.put("total_fee", amount.toString());
            paramMap.put("spbill_create_ip", ip);
            paramMap.put("notify_url", NOTIFY_URL_BOOTH);
            if (exhibitionBoothPay.getType() == 1) {
                paramMap.put("trade_type", mini_trade_type);
                paramMap.put("openid", exhibitionBoothPay.getOpenId());
            }
            else {
                paramMap.put("trade_type", app_trade_type);
            }
            // MD5运算生成签名
            String sign = PayUtil.createSign(paramMap, key);
            paramMap.put("sign", sign);
            String paramXml = PayUtil.getRequestXml(paramMap);
            logger.info("统一下单前请求参数:" + paramXml);

            String res = HttpUtils.httpsRequestToString(url, "POST", paramXml);
            logger.info("请求微信预支付接口，返回 result：" + result);
            Map< String, Object > resurtMap = XmlUtil.doXMLParse(res);

            // 返回状态码
            String returnCode = resurtMap.get("return_code").toString();
            if ("SUCCESS".equals(returnCode)) {
                String resultCode = resurtMap.get("result_code").toString();
                if ("SUCCESS".equals(resultCode)) {
                    exhibitionBoothPayService.saveExhibitionBoothPay(exhibitionBoothPay, "1", outTradeNo);
                    // 再次签名返回
                    String prepay_id = resurtMap.get("prepay_id").toString();
                    String nonceStr1 = UUIDHexGeneratorUtils.generate();
                    Long timeStamp = DateUtil.date2long(new Date());
                    if (exhibitionBoothPay.getType() == 1) {
                        Map< String, Object > returnMap = new HashMap<>();
                        returnMap.put("nonceStr", nonceStr);
                        returnMap.put("signType", "MD5");
                        returnMap.put("timeStamp", timeStamp);
                        returnMap.put("package", "prepay_id=" + prepay_id);
                        String stringSignTemp = "appId=" + appId + "&nonceStr=" + nonceStr + "&package=prepay_id="
                                + prepay_id + "&signType=MD5&timeStamp=" + timeStamp;
                        String sign2 = PayUtil.sign(stringSignTemp, key, "utf-8").toUpperCase();
                        returnMap.put("paySign", sign2);
                        returnMap.put("out_trade_no", outTradeNo);
                        dataResult.setData(returnMap);
                    }
                    else {
                        SortedMap< String, String > paramMapNew = new TreeMap<>();
                        paramMapNew.put("appid", appId);
                        paramMapNew.put("noncestr", nonceStr);
                        paramMapNew.put("package", "Sign=WXPay");
                        paramMapNew.put("partnerid", mch_id);
                        paramMapNew.put("prepayid", prepay_id);
                        paramMapNew.put("timestamp", timeStamp.toString());
                        String sign2 = PayUtil.createSign(paramMapNew, key);
                        paramMapNew.put("sign", sign2);
                        paramMapNew.put("out_trade_no", outTradeNo);
                        dataResult.setData(paramMapNew);
                    }
                }
            }
            else {
                dataResult.setCode(ReturnStatusEnum.RFC_ERROR.getValue());
                dataResult.setMsg("微信下单返回错误");
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error("微信支付下单失败" + e.getMessage());
            dataResult.setCode(ReturnStatusEnum.RFC_ERROR.getValue());
            dataResult.setMsg("下单失败");
        }
        return dataResult;
    }

    /**
     * 微信支付宠币下单
     * 
     * @param wxPayQuery
     * @return
     */
    @ApiOperation(httpMethod = "POST", value = "微信充值宠币统一下单", nickname = "0")
    @RequestMapping(value = "/wxPay", method = RequestMethod.POST)
    public DataResult wxPay(HttpServletRequest request, @RequestBody WxPayQuery wxPayQuery) {
        DataResult result = new DataResult();
        try {
            // 生成订单号
            String out_trade_no = "coin" + new SimpleDateFormat("yyyyMMddhhmmss").format(new Date())
                    + PayUtil.createCode(5);

            String ip = PayUtil.getIpAddr(request);
            if ("0:0:0:0:0:0:0:1".equals(ip)) {
                ip = "127.0.0.1";
            }
            String nonce_str = UUIDHexGeneratorUtils.generate();

            BigDecimal amount = wxPayQuery.getPrice().multiply(new BigDecimal(100)).setScale(0, RoundingMode.DOWN);// 单位分并取整
            String total_fee = String.valueOf(amount);
            String appId = "";
            if (wxPayQuery.getType() == 1) {
                appId = miniAppid;
            }
            else {
                appId = appAppID;
            }
            // 把请求参数打包成数组
            SortedMap< String, String > paramMap = new TreeMap<>();
            paramMap.put("appid", appId);
            paramMap.put("mch_id", mch_id);
            paramMap.put("nonce_str", nonce_str);
            paramMap.put("body", body);
            paramMap.put("out_trade_no", out_trade_no);
            paramMap.put("total_fee", total_fee);
            paramMap.put("spbill_create_ip", ip);
            paramMap.put("notify_url", notify_url);
            if (wxPayQuery.getType() == 1) {
                paramMap.put("trade_type", mini_trade_type);
                paramMap.put("openid", wxPayQuery.getOpenId());
            }
            else {
                paramMap.put("trade_type", app_trade_type);
            }
            // 除去数组中的空值和签名参数
            // Map paramTempNew = PayUtil.paraFilter(paramMap);
            // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
            // String paramstr = PayUtil.createLinkString(paramTempNew);
            // MD5运算生成签名
            // String sign = PayUtil.sign(paramstr, key, "utf-8").toUpperCase();
            // MD5运算生成签名
            String sign = PayUtil.createSign(paramMap, key);
            paramMap.put("sign", sign);

            String paramXml = PayUtil.getRequestXml(paramMap);
            logger.info("统一下单前请求参数:" + paramXml);

            String res = HttpUtils.httpsRequestToString(url, "POST", paramXml);
            logger.info("请求微信预支付接口，返回 result：" + result);
            Map< String, Object > resurtMap = XmlUtil.doXMLParse(res);

            String return_code = resurtMap.get("return_code").toString();// 返回状态码
            if ("SUCCESS".equals(return_code)) {
                String result_code = resurtMap.get("result_code").toString();
                if ("SUCCESS".equals(result_code)) {
                    // 自己业务
                    // 先查出原充币
                    Customer customer = customerService
                            .selectOne(new EntityWrapper< Customer >().eq("uuid", wxPayQuery.getCustomerId()));
                    Integer originalCoin = customer.getPetCoin();
                    Pay pay = new Pay();
                    Date date = new Date();
                    pay.setCustomerId(wxPayQuery.getCustomerId());
                    pay.setPrice(wxPayQuery.getPrice());
                    pay.setOutTradeNo(out_trade_no);
                    pay.setType(1);// 微信1 支付宝2
                    pay.setOriginalCoin(originalCoin);

                    pay.setIncreaseCoin(Integer.parseInt(total_fee) / 100 * 10);
                    pay.setCreateTime(date);
                    pay.setUpdateTime(date);
                    payService.insert(pay);
                    // 再次签名返回
                    String prepay_id = resurtMap.get("prepay_id").toString();
                    String nonceStr = UUIDHexGeneratorUtils.generate();
                    Long timeStamp = DateUtil.date2long(date);
                    if (wxPayQuery.getType() == 1) {
                        Map< String, Object > returnMap = new HashMap<>();
                        returnMap.put("nonceStr", nonceStr);
                        returnMap.put("signType", "MD5");
                        returnMap.put("timeStamp", timeStamp);
                        returnMap.put("package", "prepay_id=" + prepay_id);
                        String stringSignTemp = "appId=" + appId + "&nonceStr=" + nonceStr + "&package=prepay_id="
                                + prepay_id + "&signType=MD5&timeStamp=" + timeStamp;
                        String sign2 = PayUtil.sign(stringSignTemp, key, "utf-8").toUpperCase();
                        returnMap.put("paySign", sign2);
                        returnMap.put("out_trade_no", out_trade_no);
                        result.setData(returnMap);
                    }
                    else {
                        SortedMap< String, String > paramMapNew = new TreeMap<>();
                        paramMapNew.put("appid", appId);
                        paramMapNew.put("noncestr", nonceStr);
                        paramMapNew.put("package", "Sign=WXPay");
                        paramMapNew.put("partnerid", mch_id);
                        paramMapNew.put("prepayid", prepay_id);
                        paramMapNew.put("timestamp", timeStamp.toString());
                        String sign2 = PayUtil.createSign(paramMapNew, key);
                        paramMapNew.put("sign", sign2);
                        paramMapNew.put("out_trade_no", out_trade_no);
                        result.setData(paramMapNew);
                    }
                }
                return result;
            }
            else {
                result.setCode(ReturnStatusEnum.RFC_ERROR.getValue());
                result.setMsg("微信下单返回错误");
                return result;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error("微信支付下单失败" + e.getMessage());
            result.setCode(ReturnStatusEnum.RFC_ERROR.getValue());
            result.setMsg("下单失败");
            return result;
        }

    }

    /**
     * 根据订单编号查询订单信息
     * 
     * @param out_trade_no
     * @return
     */
    @ApiOperation(httpMethod = "GET", value = "根据订单编号查询订单信息,0-未支付或者支付失败 1-支付中 2-支付成功", nickname = "1")
    @RequestMapping(value = "/orderState", method = RequestMethod.GET)
    public DataResult queryOrderState(
            @ApiParam(value = "订单编号", required = true) @RequestParam("out_trade_no") String out_trade_no,
            @ApiParam(value = "类型 1-小程序 2-app 3-公众号h5", required = true) @RequestParam("type") Integer type) {
        DataResult result = new DataResult();
        try {
            Integer code = null;
            String remark = null;
            String appId = "";
            if (type == 1) {
                appId = miniAppid;
            }
            else
                if (type == 2) {
                    appId = appAppID;
                }
                else {
                    appId = gzhAppID;
                }
            String nonce_str = UUIDHexGeneratorUtils.generate();
            SortedMap< String, String > paramMap = new TreeMap<>();
            paramMap.put("appid", appId);
            paramMap.put("mch_id", mch_id);
            paramMap.put("out_trade_no", out_trade_no);
            paramMap.put("nonce_str", nonce_str);
            String sign = PayUtil.createSign(paramMap, key);
            paramMap.put("sign", sign);

            String paramXml = PayUtil.getRequestXml(paramMap);
            logger.info("查询订单前请求参数:" + paramXml);

            String res = HttpUtils.httpsRequestToString(payQueryUrl, "POST", paramXml);
            logger.info("请求微信预支付接口，返回 result：" + result);
            Map< String, Object > resurtMap = XmlUtil.doXMLParse(res);
            String return_code = resurtMap.get("return_code").toString();

            if ("SUCCESS".equals(return_code)) {
                String result_code = resurtMap.get("result_code").toString();
                if ("SUCCESS".equals(result_code)) {
                    String trade_state = resurtMap.get("trade_state").toString();
                    logger.debug("trade_state:" + trade_state);
                    if ("SUCCESS".equalsIgnoreCase(trade_state)) {// SUCCESS—支付成功 对应商户状态码 2 其他都是1
                        code = 2;
                        remark = "支付成功";
                    }
                    else
                        if ("REFUND".equalsIgnoreCase(trade_state)) {// REFUND—转入退款
                            code = 0;
                            remark = "转入退款";
                        }
                        else
                            if ("NOTPAY".equalsIgnoreCase(trade_state)) {// NOTPAY—未支付
                                code = 0;
                                remark = "未支付";
                            }
                            else
                                if ("CLOSED".equalsIgnoreCase(trade_state)) {// CLOSED—已关闭
                                    code = 0;
                                    remark = "已关闭";
                                }
                                else
                                    if ("REVOKED".equalsIgnoreCase(trade_state)) {// REVOKED—已撤销（刷卡支付）
                                        code = 0;
                                        remark = "已撤销";
                                    }
                                    else
                                        if ("USERPAYING".equalsIgnoreCase(trade_state)) {// USERPAYING--用户支付中
                                            code = 1;
                                            remark = "用户支付中";
                                        }
                                        else
                                            if ("PAYERROR".equalsIgnoreCase(trade_state)) {// PAYERROR--支付失败(其他原因，如银行返回失败)
                                                code = 0;
                                                remark = "支付失败";
                                            }
                                            else {
                                                code = 0;
                                            }
                }
                else {
                    code = 0;
                }
            }
            else {
                code = 0;
            }
            Map< String, Object > map = new HashMap<>();
            if ("coin".equals(out_trade_no.substring(0, 4))) {
                Pay pay = payService.selectOne(new EntityWrapper< Pay >().eq("out_trade_no", out_trade_no));
                if (pay != null) {
                    map.put("code", code);
                    map.put("remark", remark);
                    int status = pay.getStatus();
                    if (status == code) {
                        result.setData(map);
                        return result;
                    }
                    if (status != code) {
                        pay.setStatus(code);
                        pay.setUpdateTime(new Date());
                        payService.updateById(pay);

                        if (code == 2) {
                            Customer customer = customerService
                                    .selectOne(new EntityWrapper< Customer >().eq("uuid", pay.getCustomerId()));
                            Integer originalCoin = customer.getPetCoin();
                            Integer increaseCoin = pay.getIncreaseCoin();
                            customer.setPetCoin(originalCoin + increaseCoin);
                            customerService.updateById(customer);
                        }

                        logger.error("提醒：单号-" + out_trade_no + "的更改状态为：" + code);
                        result.setData(map);
                        return result;
                    }
                }
            }
            else {
                GoodsOrder goodsOrder = goodsOrderService
                        .selectOne(new EntityWrapper< GoodsOrder >().eq("out_trade_no", out_trade_no));
                if (goodsOrder != null) {
                    map.put("code", code);
                    map.put("remark", remark);
                    int status = goodsOrder.getStatus();
                    if (status == code) {
                        result.setData(map);
                        return result;
                    }
                    if (status != code) {
                        goodsOrder.setStatus(code);
                        goodsOrder.setUpdateTime(new Date());
                        goodsOrderService.updateById(goodsOrder);

                        logger.error("提醒：单号-" + out_trade_no + "的更改状态为：" + code);
                        result.setData(map);
                        return result;
                    }
                }

            }
            map.put("code", code);
            map.put("remark", "异常信息");
            result.setData(map);
            return result;
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error("查询订单失败" + e.getMessage());
            result.setCode(ReturnStatusEnum.RFC_ERROR.getValue());
            result.setMsg("查询订单失败");
            return result;
        }
    }

    /**
     * 微信基地支付下单 20-2月 后加一个flag为筹钱
     * 
     * @param goodsPayQuery
     * @return
     */
    @ApiOperation(httpMethod = "POST", value = "微信基地购买商品统一下单", nickname = "3")
    @RequestMapping(value = "/goodsPay", method = RequestMethod.POST)
    public DataResult goodsPay(HttpServletRequest request, @RequestBody GoodsPayQuery goodsPayQuery) {
        DataResult result = new DataResult();
        try {
            Double goodsWeight = 0.0;
            // 生成订单号
            String out_trade_no = "";
            if (goodsPayQuery.getFlag() != null && goodsPayQuery.getFlag() == 2) {
                out_trade_no = "price" + new SimpleDateFormat("yyyyMMddhhmmss").format(new Date())
                        + PayUtil.createCode(5);
            }
            else {
                // 先判断金额数量是否正确
                Base base = baseService
                        .selectOne(new EntityWrapper< Base >().eq("customer_id", goodsPayQuery.getBaseCustomerId()));
                if (0 != goodsPayQuery.getPrice()
                        .compareTo(base.getGoodsPrice().multiply(new BigDecimal(goodsPayQuery.getCount())))) {
                    return new DataResult(ReturnStatusEnum.CLIENT_ERROR.getValue(), "金额数量错误，请核对!", null);
                }
                out_trade_no = "goods" + new SimpleDateFormat("yyyyMMddhhmmss").format(new Date())
                        + PayUtil.createCode(5);
                goodsWeight = base.getGoodsWeight();
            }

            String ip = PayUtil.getIpAddr(request);
            if ("0:0:0:0:0:0:0:1".equals(ip)) {
                ip = "127.0.0.1";
            }
            String nonce_str = UUIDHexGeneratorUtils.generate();

            BigDecimal amount = goodsPayQuery.getPrice().multiply(new BigDecimal(100)).setScale(0, RoundingMode.DOWN);// 单位分并取整
            String total_fee = String.valueOf(amount);
            String appId = "";
            if (goodsPayQuery.getType() != null && goodsPayQuery.getType() == 2) {
                appId = gzhAppID;
            }
            else {
                appId = miniAppid;
            }

            // 把请求参数打包成数组
            SortedMap< String, String > paramMap = new TreeMap<>();
            paramMap.put("appid", appId);
            paramMap.put("mch_id", mch_id);
            paramMap.put("nonce_str", nonce_str);
            paramMap.put("body", body1);
            paramMap.put("out_trade_no", out_trade_no);
            paramMap.put("total_fee", total_fee);
            paramMap.put("spbill_create_ip", ip);
            paramMap.put("notify_url", notify_url1);
            paramMap.put("trade_type", mini_trade_type);
            paramMap.put("openid", goodsPayQuery.getOpenId());

            String sign = PayUtil.createSign(paramMap, key);
            paramMap.put("sign", sign);

            String paramXml = PayUtil.getRequestXml(paramMap);
            logger.info("统一下单前请求参数:" + paramXml);

            String res = HttpUtils.httpsRequestToString(url, "POST", paramXml);
            logger.info("请求微信预支付接口，返回 result：" + res);
            Map< String, Object > resurtMap = XmlUtil.doXMLParse(res);

            String return_code = resurtMap.get("return_code").toString();// 返回状态码
            if ("SUCCESS".equals(return_code)) {
                String result_code = resurtMap.get("result_code").toString();
                if ("SUCCESS".equals(result_code)) {
                    Date date = new Date();
                    GoodsOrder goodsOrder = new GoodsOrder();
                    goodsOrder.setCustomerId(goodsPayQuery.getCustomerId());
                    goodsOrder.setBaseCustomerId(goodsPayQuery.getBaseCustomerId());
                    goodsOrder.setOutTradeNo(out_trade_no);
                    goodsOrder.setPrice(goodsPayQuery.getPrice());
                    if (goodsPayQuery.getFlag() != null && goodsPayQuery.getFlag() == 2) {
                        goodsOrder.setType(2);
                    }
                    else {
                        goodsOrder.setType(1);
                        goodsOrder.setWeight(goodsPayQuery.getCount() * goodsWeight);
                    }
                    goodsOrder.setCreateTime(date);
                    goodsOrder.setUpdateTime(date);
                    goodsOrderService.insert(goodsOrder);

                    // 再次签名返回
                    String prepay_id = resurtMap.get("prepay_id").toString();
                    String nonceStr = UUIDHexGeneratorUtils.generate();
                    Map< String, Object > returnMap = new HashMap<>();
                    resurtMap.put("nonceStr", nonceStr);
                    resurtMap.put("signType", "MD5");
                    Long timeStamp = DateUtil.date2long(date);
                    resurtMap.put("timeStamp", timeStamp);
                    resurtMap.put("package", "prepay_id=" + prepay_id);
                    String stringSignTemp = "appId=" + appId + "&nonceStr=" + nonceStr + "&package=prepay_id="
                            + prepay_id + "&signType=MD5&timeStamp=" + timeStamp;
                    String sign2 = PayUtil.sign(stringSignTemp, key, "utf-8").toUpperCase();
                    resurtMap.put("paySign", sign2);
                    resurtMap.put("out_trade_no", out_trade_no);
                    result.setData(resurtMap);
                }
            }
            else {
                result.setCode(ReturnStatusEnum.RFC_ERROR.getValue());
                result.setMsg("微信购买商品下单返回错误");
                return result;
            }
            return result;
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error("微信基地购买商品统一下单失败" + e.getMessage());
            result.setCode(ReturnStatusEnum.RFC_ERROR.getValue());
            result.setMsg("下单失败");
            return result;
        }
    }

    /**
     * 微信蓝v充值会员下单
     * 
     * @param vipPayQuery
     * @return
     */
    @ApiOperation(httpMethod = "POST", value = "微信蓝v充值会员下单", nickname = "4")
    @RequestMapping(value = "/vipPay", method = RequestMethod.POST)
    public DataResult vipPay(HttpServletRequest request, @RequestBody VipPayQuery vipPayQuery) {
        DataResult result = new DataResult();
        try {
            Integer years = vipPayQuery.getYears();
            BigDecimal price = vipPayQuery.getPrice();

            // 先判断金额数量是否正确
            Config vipYearMoney = configService.selectById(7);
            if (0 != price.compareTo(vipYearMoney.getValue().multiply(new BigDecimal(years)))) {
                return new DataResult(ReturnStatusEnum.CLIENT_ERROR.getValue(), "金额数量错误，请核对!", null);
            }
            // 生成订单号
            String out_trade_no = "vip" + new SimpleDateFormat("yyyyMMddhhmmss").format(new Date())
                    + PayUtil.createCode(5);

            String ip = PayUtil.getIpAddr(request);
            if ("0:0:0:0:0:0:0:1".equals(ip)) {
                ip = "127.0.0.1";
            }
            String nonce_str = UUIDHexGeneratorUtils.generate();

            BigDecimal amount = price.multiply(new BigDecimal(100)).setScale(0, RoundingMode.DOWN);// 单位分并取整
            String total_fee = String.valueOf(amount);
            String appId = appAppID;

            // 把请求参数打包成数组
            SortedMap< String, String > paramMap = new TreeMap<>();
            paramMap.put("appid", appId);
            paramMap.put("mch_id", mch_id);
            paramMap.put("nonce_str", nonce_str);
            paramMap.put("body", bodyVip);
            paramMap.put("out_trade_no", out_trade_no);
            paramMap.put("total_fee", total_fee);
            paramMap.put("spbill_create_ip", ip);
            paramMap.put("notify_url", notify_url_vip);
            paramMap.put("trade_type", app_trade_type);

            String sign = PayUtil.createSign(paramMap, key);
            paramMap.put("sign", sign);

            String paramXml = PayUtil.getRequestXml(paramMap);
            logger.info("统一下单前请求参数:" + paramXml);

            String res = HttpUtils.httpsRequestToString(url, "POST", paramXml);
            logger.info("请求微信预支付接口，返回 result：" + res);
            Map< String, Object > resurtMap = XmlUtil.doXMLParse(res);

            String return_code = resurtMap.get("return_code").toString();// 返回状态码
            if ("SUCCESS".equals(return_code)) {
                String result_code = resurtMap.get("result_code").toString();
                if ("SUCCESS".equals(result_code)) {
                    // 先查出原到期时间
                    Customer customer = customerService
                            .selectOne(new EntityWrapper< Customer >().eq("uuid", vipPayQuery.getCustomerId()));
                    Date originalEndTime = customer.getVipEndTime();
                    VipOrder vipOrder = new VipOrder();
                    Date date = new Date();
                    vipOrder.setCustomerId(vipPayQuery.getCustomerId());
                    vipOrder.setPrice(price);
                    vipOrder.setYears(years);
                    vipOrder.setOutTradeNo(out_trade_no);
                    vipOrder.setType(1);// 微信1 支付宝2
                    vipOrder.setOriginalEndTime(originalEndTime);
                    Date nowEndTime;
                    if (originalEndTime == null || originalEndTime.before(date)) {
                        nowEndTime = dateAdd(date, years);
                    }
                    else {
                        nowEndTime = dateAdd(originalEndTime, years);
                    }
                    vipOrder.setNowEndTime(nowEndTime);
                    vipOrder.setCreateTime(date);
                    vipOrder.setUpdateTime(date);
                    vipOrderService.insert(vipOrder);

                    customer.setVipEndTime(nowEndTime);
                    customerService.updateById(customer);

                    Business business = businessService.selectList(new EntityWrapper< Business >()
                            .eq("customer_id", vipPayQuery.getCustomerId()).orderBy("update_time", false)).get(0);
                    business.setStatus(2);
                    business.setUpdateTime(date);
                    business.setVipEndTime(nowEndTime);
                    businessService.updateById(business);

                    // 再次签名返回
                    String prepay_id = resurtMap.get("prepay_id").toString();
                    String nonceStr = UUIDHexGeneratorUtils.generate();
                    SortedMap< String, String > returnMap = new TreeMap<>();
                    returnMap.put("appid", appId);
                    returnMap.put("nonceStr", nonceStr);
                    returnMap.put("package", "Sign=WXPay");
                    returnMap.put("partnerid", mch_id);
                    returnMap.put("prepayid", prepay_id);
                    Long timeStamp = DateUtil.date2long(date);
                    returnMap.put("timeStamp", timeStamp.toString());
                    String sign2 = PayUtil.createSign(returnMap, key);
                    returnMap.put("sign", sign2);
                    returnMap.put("out_trade_no", out_trade_no);
                    result.setData(returnMap);
                }
            }
            else {
                result.setCode(ReturnStatusEnum.RFC_ERROR.getValue());
                result.setMsg("微信蓝v充值会员下单返回错误");
                return result;
            }
            return result;
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error("微信蓝v充值会员下单失败" + e.getMessage());
            result.setCode(ReturnStatusEnum.RFC_ERROR.getValue());
            result.setMsg("下单失败");
            return result;
        }
    }

    public static Date dateAdd(Date originalDate, int years) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(originalDate);
        cal.add(Calendar.YEAR, years);
        return cal.getTime();
    }
}
