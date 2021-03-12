package com.ttm.pet.controller.app;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.domain.AlipayTradeAppPayModel;
import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.AlipayTradeAppPayRequest;
import com.alipay.api.response.AlipayTradeAppPayResponse;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.ttm.pet.enums.ConstantsEnum;
import com.ttm.pet.enums.ReturnStatusEnum;
import com.ttm.pet.model.dto.Business;
import com.ttm.pet.model.dto.Config;
import com.ttm.pet.model.dto.Customer;
import com.ttm.pet.model.dto.ExhibitionBoothPay;
import com.ttm.pet.model.dto.Pay;
import com.ttm.pet.model.dto.VipOrder;
import com.ttm.pet.model.pojo.DataResult;
import com.ttm.pet.model.query.app.VipPayQuery;
import com.ttm.pet.model.query.app.WxPayQuery;
import com.ttm.pet.service.BusinessService;
import com.ttm.pet.service.ConfigService;
import com.ttm.pet.service.CustomerService;
import com.ttm.pet.service.ExhibitionBoothPayService;
import com.ttm.pet.service.PayService;
import com.ttm.pet.service.VipOrderService;
import com.ttm.pet.util.PayUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@Api(value = "/app/aliPay", tags = "app-aliPay", description = "支付宝支付相关接口")
@RequestMapping("/app/aliPay")
@PropertySource(value = { "classpath:alipay.properties" })
public class AliPayController {
    private final static Logger logger = LoggerFactory.getLogger(AliPayController.class);

    @Autowired
    private CustomerService customerService;

    @Autowired
    private PayService payService;

    @Autowired
    private ConfigService configService;

    @Autowired
    private VipOrderService vipOrderService;

    @Autowired
    private BusinessService businessService;

    @Autowired
    private ExhibitionBoothPayService exhibitionBoothPayService;

    @Value("${app_id}")
    private String appId;

    @Value("${merchant_private_key}")
    private String privateKey;

    @Value("${alipay_public_key}")
    private String publicKey;

    @Value("${gatewayUrl}")
    private String gatewayUrl;

    @Value("${charset}")
    private String charset;

    @Value("${sign_type}")
    private String signType;

    @Value("${notify_url}")
    private String notifyUrl;

    @Value("${notify_vip_url}")
    private String notifyVipUrl;

    @Value("${exhibition_booth_url}")
    private String exhibitionBoothUrl;

    private final static String body = "宠艾-宠币充值";

    private final static String bodyVip = "宠艾-蓝v会员";

    private final static String EXH_BOOTH = "宠艾-购买展位";

    @ApiOperation(httpMethod = "POST", value = "展位支付", nickname = "0")
    @PostMapping(value = "/exhibitionBoothAliPay")
    public DataResult exhibitionBoothAliPay(@RequestBody @Valid ExhibitionBoothPay exhibitionBoothPay) {
        DataResult dataResult = new DataResult();
        try {
            // 校验当前展位是否售出
            boolean result = exhibitionBoothPayService.checkBoothStatus(exhibitionBoothPay.getBoothId());
            if (!result) {
                return new DataResult(ReturnStatusEnum.CLIENT_ERROR.getValue(), "当前展位已售出!", null);
            }
            AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl, appId, privateKey, "json", charset,
                    publicKey, signType);
            // 实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
            AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
            // SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
            AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
            model.setBody(EXH_BOOTH);
            model.setSubject("展位");
            String out_trade_no = "zw" + new SimpleDateFormat("yyyyMMddhhmmss").format(new Date())
                    + PayUtil.createCode(5);
            model.setOutTradeNo(out_trade_no);
            model.setTimeoutExpress("30m");
            model.setTotalAmount(exhibitionBoothPay.getPayPrice().toString());

            request.setBizModel(model);
            request.setNotifyUrl(exhibitionBoothUrl);
            // 这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            String aliPayResult = response.getBody();
            if (StringUtils.isNotBlank(aliPayResult)) {
                exhibitionBoothPayService.saveExhibitionBoothPay(exhibitionBoothPay, "2", out_trade_no);
            }
            dataResult.setData(aliPayResult);
        }
        catch (Exception e) {
            logger.error("支付宝下单失败" + e.getMessage());
            dataResult.setCode(ReturnStatusEnum.RFC_ERROR.getValue());
            dataResult.setMsg("下单失败");
        }
        return dataResult;
    }

    @ApiOperation(httpMethod = "POST", value = "支付宝充值充币", nickname = "0")
    @RequestMapping(value = "/pay", method = RequestMethod.POST)
    public DataResult aliPay(@RequestBody WxPayQuery wxPayQuery) {
        DataResult result = new DataResult();
        try {
            // 生成订单号
            String out_trade_no = "coin" + new SimpleDateFormat("yyyyMMddhhmmss").format(new Date())
                    + PayUtil.createCode(5);

            AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl, appId, privateKey, "json", charset,
                    publicKey, signType);
            // 实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
            AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
            // SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
            AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
            model.setBody(body);// 商品描述
            model.setSubject("宠币");// 关键字
            model.setOutTradeNo(out_trade_no); // 唯一订单不能重复
            model.setTimeoutExpress("30m");// 最晚付款时间
            model.setTotalAmount(wxPayQuery.getPrice().toString());// 商品价格
            // model.setProductCode("QUICK_MSECURITY_PAY");//产品销售码
            // 建议保存唯一订单号 方便于回调查看是否支付成功

            request.setBizModel(model);
            request.setNotifyUrl(notifyUrl);
            // 这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            String aliPayResult = response.getBody();
            // result = new String(result.getBytes("ISO-8859-1"), "utf-8");
            // 就是orderString 可以直接给客户端请求，无需再做处理。
            if (!aliPayResult.equals("")) {
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
                pay.setType(2);// 微信1 支付宝2
                pay.setOriginalCoin(originalCoin);

                pay.setIncreaseCoin(wxPayQuery.getPrice().setScale(0, BigDecimal.ROUND_DOWN).intValue() * 10);
                pay.setCreateTime(date);
                pay.setUpdateTime(date);
                payService.insert(pay);
            }
            result.setData(aliPayResult);
            return result;
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error("支付宝下单失败" + e.getMessage());
            result.setCode(ReturnStatusEnum.RFC_ERROR.getValue());
            result.setMsg("下单失败");
            return result;
        }
    }

    @ApiOperation(httpMethod = "POST", value = "支付宝蓝v会员充币", nickname = "0")
    @RequestMapping(value = "/vipPay", method = RequestMethod.POST)
    public DataResult aliPayVip(@RequestBody VipPayQuery vipPayQuery) {
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

            AlipayClient alipayClient = new DefaultAlipayClient(gatewayUrl, appId, privateKey, "json", charset,
                    publicKey, signType);
            // 实例化具体API对应的request类,类名称和接口名称对应,当前调用接口名称：alipay.trade.app.pay
            AlipayTradeAppPayRequest request = new AlipayTradeAppPayRequest();
            // SDK已经封装掉了公共参数，这里只需要传入业务参数。以下方法为sdk的model入参方式(model和biz_content同时存在的情况下取biz_content)。
            AlipayTradeAppPayModel model = new AlipayTradeAppPayModel();
            model.setBody(bodyVip);// 商品描述
            model.setSubject("蓝v");// 关键字
            model.setOutTradeNo(out_trade_no); // 唯一订单不能重复
            model.setTimeoutExpress("30m");// 最晚付款时间
            model.setTotalAmount(price.toString());// 商品价格
            // model.setProductCode("QUICK_MSECURITY_PAY");//产品销售码
            // 建议保存唯一订单号 方便于回调查看是否支付成功

            request.setBizModel(model);
            request.setNotifyUrl(notifyVipUrl);
            // 这里和普通的接口调用不同，使用的是sdkExecute
            AlipayTradeAppPayResponse response = alipayClient.sdkExecute(request);
            String aliPayResult = response.getBody();
            // result = new String(result.getBytes("ISO-8859-1"), "utf-8");
            // 就是orderString 可以直接给客户端请求，无需再做处理。
            if (!aliPayResult.equals("")) {
                // 自己业务
                // 先查出原到期时间
                Customer customer = customerService
                        .selectOne(new EntityWrapper< Customer >().eq("uuid", vipPayQuery.getCustomerId()));
                Date originalEndTime = customer.getVipEndTime();
                VipOrder vipOrder = new VipOrder();
                Date date = new Date();
                vipOrder.setCustomerId(vipOrder.getCustomerId());
                vipOrder.setPrice(price);
                vipOrder.setYears(years);
                vipOrder.setOutTradeNo(out_trade_no);
                vipOrder.setType(2);// 微信1 支付宝2
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

            }
            result.setData(aliPayResult);
            return result;
        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error("支付宝蓝v下单失败" + e.getMessage());
            result.setCode(ReturnStatusEnum.RFC_ERROR.getValue());
            result.setMsg("下单失败");
            return result;
        }
    }

    @PostMapping(value = "/notifyBooth")
    public String notifyBooth(HttpServletRequest request) {
        try {
            // 获取支付宝POST过来反馈信息
            Map< String, String > params = new HashMap< String, String >();
            Map requestParams = request.getParameterMap();
            for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
                }
                // 乱码解决，这段代码在出现乱码时使用。
                // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
                params.put(name, valueStr);
            }
            String paramsJson = JSON.toJSONString(params);
            logger.info("支付宝回调参数，{}", paramsJson);
            boolean flag = AlipaySignature.rsaCheckV1(params, publicKey, charset, signType);
            if (flag) {
                logger.info("支付宝回调签名认证成功");
                // 商户订单号
                String outTradeNo = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");

                String tradeStatus = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
                // 判断是否支付成功
                if ("TRADE_SUCCESS".equals(tradeStatus)) {
                    exhibitionBoothPayService.updateExhibitionBoothPay(outTradeNo);
                }
            }
            return "SUCCESS";
        }
        catch (Exception e) {
            logger.error("支付宝回调失败" + e.getMessage());
            return "FAIL";
        }
    }

    @RequestMapping(value = "/notify", method = RequestMethod.POST)
    @ResponseBody
    public String notify(HttpServletRequest request) {
        try {
            // 获取支付宝POST过来反馈信息
            Map< String, String > params = new HashMap< String, String >();
            Map requestParams = request.getParameterMap();
            Map< Object, Object > resultMap = new HashMap<>();
            for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
                }
                // 乱码解决，这段代码在出现乱码时使用。
                // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
                params.put(name, valueStr);
            }
            String paramsJson = JSON.toJSONString(params);
            logger.info("支付宝回调参数，{}", paramsJson);
            // 切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
            // boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String
            // sign_type)

            boolean flag = AlipaySignature.rsaCheckV1(params, publicKey, charset, signType);
            if (flag) {
                logger.info("支付宝回调签名认证成功");
                // 商户订单号
                String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
                // 付款金额
                String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");

                String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
                // 判断是否支付成功
                if ("TRADE_SUCCESS".equals(trade_status)) {
                    logger.info("支付宝支付成功");
                    Pay pay = payService.selectOne(new EntityWrapper< Pay >().eq("out_trade_no", out_trade_no));
                    logger.info("支付宝返回付款金额：" + total_amount);
                    String price = String.valueOf(pay.getPrice());
                    logger.info("数据库付款金额：" + price);
                    // 对比金额
                    // String price = String.valueOf(pay.getPrice().multiply(new BigDecimal(100)).setScale(0,
                    // RoundingMode.DOWN));
                    // //修改订单状态为支付 并且会员更新时间
                    // if (total_fee.equals(price)) {
                    pay.setStatus(ConstantsEnum.PAY_STATUS_SUCCESS.getCode());
                    pay.setUpdateTime(new Date());
                    payService.updateById(pay);

                    Customer customer = customerService
                            .selectOne(new EntityWrapper< Customer >().eq("uuid", pay.getCustomerId()));
                    Integer originalCoin = customer.getPetCoin();
                    Integer increaseCoin = pay.getIncreaseCoin();
                    customer.setPetCoin(originalCoin + increaseCoin);
                    customerService.updateById(customer);
                    return "SUCCESS";
                    // }
                }

                return "FAIL";
            }
            else {
                String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
                return "FAIL";
            }

        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error("支付宝回调失败" + e.getMessage());
            return "FAIL";
        }
    }

    @RequestMapping(value = "/notifyVip", method = RequestMethod.POST)
    @ResponseBody
    public String notifyVip(HttpServletRequest request) {
        try {
            // 获取支付宝POST过来反馈信息
            Map< String, String > params = new HashMap<>();
            Map requestParams = request.getParameterMap();
            Map< Object, Object > resultMap = new HashMap<>();
            for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext();) {
                String name = (String) iter.next();
                String[] values = (String[]) requestParams.get(name);
                String valueStr = "";
                for (int i = 0; i < values.length; i++) {
                    valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
                }
                // 乱码解决，这段代码在出现乱码时使用。
                // valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
                params.put(name, valueStr);
            }
            String paramsJson = JSON.toJSONString(params);
            logger.info("支付宝回调参数，{}", paramsJson);
            // 切记alipaypublickey是支付宝的公钥，请去open.alipay.com对应应用下查看。
            // boolean AlipaySignature.rsaCheckV1(Map<String, String> params, String publicKey, String charset, String
            // sign_type)

            boolean flag = AlipaySignature.rsaCheckV1(params, publicKey, charset, signType);
            if (flag) {
                logger.info("支付宝回调签名认证成功");
                // 商户订单号
                String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
                // 付款金额
                String total_amount = new String(request.getParameter("total_amount").getBytes("ISO-8859-1"), "UTF-8");

                String trade_status = new String(request.getParameter("trade_status").getBytes("ISO-8859-1"), "UTF-8");
                // 判断是否支付成功
                if ("TRADE_SUCCESS".equals(trade_status)) {
                    logger.info("支付宝支付成功");
                    VipOrder pay = vipOrderService
                            .selectOne(new EntityWrapper< VipOrder >().eq("out_trade_no", out_trade_no));
                    logger.info("支付宝返回付款金额：" + total_amount);
                    String price = String.valueOf(pay.getPrice());
                    logger.info("数据库付款金额：" + price);
                    // 对比金额

                    // 修改订单状态为支付 并且会员更新时间
                    if (total_amount.equals(price)) {
                        pay.setStatus(ConstantsEnum.PAY_STATUS_SUCCESS.getCode());
                        pay.setUpdateTime(new Date());
                        vipOrderService.updateById(pay);

                        Customer customer = customerService
                                .selectOne(new EntityWrapper< Customer >().eq("uuid", pay.getCustomerId()));
                        customer.setVipEndTime(pay.getNowEndTime());
                        customerService.updateById(customer);
                        return "SUCCESS";
                    }
                }

                return "FAIL";
            }
            else {
                String out_trade_no = new String(request.getParameter("out_trade_no").getBytes("ISO-8859-1"), "UTF-8");
                return "FAIL";
            }

        }
        catch (Exception e) {
            e.printStackTrace();
            logger.error("支付宝回调失败" + e.getMessage());
            return "FAIL";
        }
    }

    public static Date dateAdd(Date originalDate, int years) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(originalDate);
        cal.add(Calendar.YEAR, years);
        return cal.getTime();
    }
}
