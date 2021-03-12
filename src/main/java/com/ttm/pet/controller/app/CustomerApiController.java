package com.ttm.pet.controller.app;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.ttm.pet.enums.ConstantsEnum;
import com.ttm.pet.enums.PathEnum;
import com.ttm.pet.enums.ReturnStatusEnum;
import com.ttm.pet.model.dto.*;
import com.ttm.pet.model.pojo.DataResult;
import com.ttm.pet.model.pojo.ListDataResult;
import com.ttm.pet.model.query.app.*;
import com.ttm.pet.model.vo.app.*;
import com.ttm.pet.service.*;
import com.ttm.pet.util.NumberUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.*;

/**
 * <p>
 * 客户表 前端控制器
 * </p>
 *
 * @author cx
 * @since 2020-03-27
 */
@RestController
@Api(value ="/app/customer",tags = "app-customer",description = "用户中心")
@RequestMapping("/app/customer")
public class CustomerApiController {
    private final static Logger logger = LoggerFactory.getLogger(CustomerApiController.class);

    @Autowired
    private CustomerService customerService;

    @Autowired
    private VerificationCodeService verificationCodeService;

    @Autowired
    private CustomerRelationService customerRelationService;

    @Autowired
    private WxService wxService;

    @Autowired
    private MessageRecordService messageRecordService;

    @Autowired
    private ConfigService configService;

    @Autowired
    private WorksService worksService;

    @Autowired
    private CustomerPointService customerPointService;

    @Autowired
    private CustomerRewardService customerRewardService;

    @Autowired
    private CashOutService cashOutService;

    @Autowired
    private SystemImgService systemImgService;

    /**
     * 注册用户
     * @param customerRegisterQuery
     * @return
     */
    @ApiOperation(httpMethod = "POST",value = "注册用户",nickname = "0")
    @RequestMapping(value="/newCustomer", method=RequestMethod.POST)
    public DataResult registerUserApi(@RequestBody CustomerRegisterQuery customerRegisterQuery){
        Customer customerOld = customerService.selectOne(new EntityWrapper<Customer>().eq("mobile",customerRegisterQuery.getPhoneNum()));
        if(null != customerOld){
            return new DataResult(ReturnStatusEnum.SYS_ERROR.getValue(),"该号码已经注册！",null);
        }
        DataResult result = verificationCodeService.findVerificationCodeVos(customerRegisterQuery.getPhoneNum(),customerRegisterQuery.getCode(),ConstantsEnum.REGIEST_CODE.getCode());
        if(null != result){
            return result;
        }
        try {
            //拿背景图
            SystemImg systemImg = systemImgService.selectById(2);

            Customer customer = new Customer();
            customer.setPortrait(PathEnum.DEFAULT_HEAD.getPath());
            String uuid = UUID.randomUUID().toString().toLowerCase();
            customer.setUuid(uuid);
            customer.setPetNumber(NumberUtil.uuidToNum(uuid));
            String phoneNum = customerRegisterQuery.getPhoneNum();
            customer.setName("用户_"+ phoneNum.substring(phoneNum.length()-4,phoneNum.length()));
            customer.setMobile(phoneNum);
            customer.setShowMobile(phoneNum);
            customer.setPassword(DigestUtils.md5Hex(customerRegisterQuery.getPassword()));
            customer.setBackImg(systemImg.getValue());
            customerService.insert(customer);
            MessageRecord messageRecord = new MessageRecord();
            messageRecord.setLastBrowseTime(new Date());
            messageRecord.setCustomerId(uuid);
            messageRecordService.insert(messageRecord);
            return new DataResult(ReturnStatusEnum.SUCCESS.getValue(),"注册成功",customer);
        } catch (Exception e) {
            logger.error("注册失败",e);
            return new DataResult(ReturnStatusEnum.SYS_ERROR.getValue(),"注册失败！",null);
        }
    }

    /**
     * 用户登陆
     * @param customerLoginQuery
     * @return
     */
    @ApiOperation(httpMethod = "POST", value = "登陆",nickname = "1")
    @RequestMapping(value="/customer", method=RequestMethod.POST)
    public DataResult loginUser(@RequestBody CustomerLoginQuery customerLoginQuery){
        try {
            CustomerVo customerVo = new CustomerVo();
            Customer customer = new Customer();
            if(!StringUtils.isBlank(customerLoginQuery.getPhoneNum()) && !StringUtils.isBlank(customerLoginQuery.getPassword())){
                String password = DigestUtils.md5Hex(customerLoginQuery.getPassword());
                customer = customerService.selectOne(new EntityWrapper<Customer>().eq("mobile",customerLoginQuery.getPhoneNum()).eq("password",password));
                if(null == customer){
                    return new DataResult(ReturnStatusEnum.SYS_ERROR.getValue(),"登陆失败,请核对手机号或密码！",null);
                }
            } else if (!StringUtils.isBlank(customerLoginQuery.getWxUnionId())){
                customer = customerService.selectOne(new EntityWrapper<Customer>().eq("wx_union_id",customerLoginQuery.getWxUnionId()));
                if(null == customer){
                    customer = new Customer();
                    customer.setWxUnionId(customerLoginQuery.getWxUnionId());
                    //拿背景图
                    SystemImg systemImg = systemImgService.selectById(2);
                    customer.setBackImg(systemImg.getValue());

                    String uuid = UUID.randomUUID().toString().toLowerCase();
                    customer.setUuid(uuid);

                    customer.setPetNumber(NumberUtil.uuidToNum(uuid));
                    if(customerLoginQuery.getPortrait() != null && !"".equals(customerLoginQuery.getPortrait())){
                        customer.setPortrait(customerLoginQuery.getPortrait());
                    }
                    if("true".equals(customerLoginQuery.getGender()) || "1".equals(customerLoginQuery.getGender())){
                        customer.setGender("1");
                    }else{
                        customer.setGender("0");
                    }
                    if(!StringUtils.isBlank(customerLoginQuery.getName())){
                        customer.setName(customerLoginQuery.getName());
                    }else {
                        String wxUnionId = customerLoginQuery.getWxUnionId();
                        customer.setName("用户_" + wxUnionId.substring(wxUnionId.length()-4,wxUnionId.length()));
                    }
                    if(!StringUtils.isBlank(customerLoginQuery.getCityName()) && customerLoginQuery.getCityId() != null){
                        customer.setCityId(customerLoginQuery.getCityId());
                        customer.setCityName(customerLoginQuery.getCityName());
                    }

                    customerService.insert(customer);
                    BeanUtils.copyProperties(customer,customerVo);
                    MessageRecord messageRecord = new MessageRecord();
                    messageRecord.setLastBrowseTime(new Date());
                    messageRecord.setCustomerId(uuid);
                    messageRecordService.insert(messageRecord);
                    return new DataResult(100,"登陆成功！",customerVo);
                }
            }else if (!StringUtils.isBlank(customerLoginQuery.getIdentityToken())){
                customer = customerService.selectOne(new EntityWrapper<Customer>().eq("identity_token",customerLoginQuery.getIdentityToken()));
                if(null == customer){
                    customer = new Customer();
                    customer.setIdentityToken(customerLoginQuery.getIdentityToken());
                    //拿背景图
                    SystemImg systemImg = systemImgService.selectById(2);
                    customer.setBackImg(systemImg.getValue());

                    String uuid = UUID.randomUUID().toString().toLowerCase();
                    customer.setUuid(uuid);

                    customer.setPetNumber(NumberUtil.uuidToNum(uuid));
                    customer.setGender("2");

                    if(!StringUtils.isBlank(customerLoginQuery.getName())){
                        customer.setName(customerLoginQuery.getName());
                    }else {
                        String identityToken = customerLoginQuery.getIdentityToken();
                        customer.setName("用户_" + identityToken.substring(identityToken.length()-4,identityToken.length()));
                    }
                    if(!StringUtils.isBlank(customerLoginQuery.getCityName()) && customerLoginQuery.getCityId() != null){
                        customer.setCityId(customerLoginQuery.getCityId());
                        customer.setCityName(customerLoginQuery.getCityName());
                    }

                    customerService.insert(customer);
                    BeanUtils.copyProperties(customer,customerVo);
                    MessageRecord messageRecord = new MessageRecord();
                    messageRecord.setLastBrowseTime(new Date());
                    messageRecord.setCustomerId(uuid);
                    messageRecordService.insert(messageRecord);
                    return new DataResult(100,"登陆成功！",customerVo);
                }
            }
            BeanUtils.copyProperties(customer,customerVo);
            //查询粉丝数和关注数
            int attentionCount = customerRelationService.selectCount(new EntityWrapper<CustomerRelation>()
                    .eq("from_customer_id",customer.getUuid())
                    .eq("relation_type",1)
                    .eq("deleted",0));
            int fansCount = customerRelationService.selectCount(new EntityWrapper<CustomerRelation>()
                    .eq("to_customer_id",customer.getUuid())
                    .eq("relation_type",1)
                    .eq("deleted",0));
            customerVo.setAttentionCount(attentionCount);
            customerVo.setFansCount(fansCount);
            //我的作品数
            int worksCount = worksService.selectCount(new EntityWrapper<Works>().eq("customer_id",customer.getUuid()).andNew().eq("deleted",0).or().eq("deleted",2));
            customerVo.setWorksCount(worksCount);
            //点赞数
//            int pointCount = customerPointService.selectCount(new EntityWrapper<CustomerPoint>().eq("customer_id",customer.getUuid()).eq("type",1).eq("deleted",0));
            int pointCount = worksService.findPointWorksCount(customer.getUuid());
            customerVo.setPointCount(pointCount);
            //我打赏的金币数
            Map<String,Object> rewardCoinMap = customerRewardService.selectMap(new EntityWrapper<CustomerReward>().setSqlSelect("ifnull(sum(coin),0) coin").eq("customer_id",customer.getUuid()));
            customerVo.setRewardCoin(Integer.parseInt(rewardCoinMap.get("coin").toString()));
            //我获得的金币数
            Map<String,Object> getCoinMap = customerRewardService.selectMap(new EntityWrapper<CustomerReward>().setSqlSelect("ifnull(sum(coin),0) coin").eq("to_customer_id",customer.getUuid()));
            customerVo.setGetCoin(Integer.parseInt(getCoinMap.get("coin").toString()));

            //领养人数
            if (customer.getIsAdopted() == 1){
                int adoptedCount = customerRelationService.selectCount(new EntityWrapper<CustomerRelation>().eq("from_customer_id",customer.getUuid()).eq("deleted",0).eq("is_adopt",1));
                customerVo.setAdoptCount(adoptedCount);
            }
            return new DataResult(100,"登陆成功！",customerVo);
        } catch (Exception e) {
            logger.error("登陆失败", e);
            return new DataResult(101,"登陆失败",null);
        }
    }

     /**
     * 使用登录凭证 js_code 获取 session_key 和 openid  unionId。
     * @param preLoginQuery
     * @return
     */
    @ApiOperation(httpMethod = "POST", value = "小程序第一次授权使用js_code等等登陆",nickname = "2")
    @RequestMapping(value = "/miniCustomerInfo" ,method = RequestMethod.POST)
    private DataResult addMiniCustomerInfo(@RequestBody PreLoginQuery preLoginQuery){
        DataResult result = new DataResult();
        try {
            JSONObject sessionKeyJson = wxService.code2sessionKey(preLoginQuery.getJsCode());
            String openId = sessionKeyJson.getString("openid");// 用户唯一标识
            String session_key = sessionKeyJson.getString("session_key");// 密钥

            System.out.println("openId"+openId);
            System.out.println("session_key"+session_key);
            logger.info("openId:"+openId+";sessionKey:"+session_key);


            JSONObject json = wxService.decryptionUserInfo(preLoginQuery.getEncryptedData(), session_key, preLoginQuery.getIv());
            System.out.println(json);
            String unionId = json.get("unionId").toString();
            System.out.println("unionId"+unionId);

            Customer customer = customerService.selectOne(new EntityWrapper<Customer>().eq("wx_union_id",unionId));
            if(customer != null){
                if ( customer.getOpenId() == null ){
                    customer.setOpenId(openId);
                    customerService.updateById(customer);
                }
                result.setData(customer);
                return result;
            }else {
                Customer customerNew = new Customer();
                //拿背景图
                SystemImg systemImg = systemImgService.selectById(2);
                customerNew.setBackImg(systemImg.getValue());
                customerNew.setWxUnionId(unionId);
                String uuid = UUID.randomUUID().toString().toLowerCase();
                customerNew.setUuid(uuid);
                customerNew.setOpenId(openId);
                customerNew.setPetNumber(NumberUtil.uuidToNum(uuid));
                customerNew.setName(json.get("nickName").toString());
                customerNew.setGender(json.get("gender").toString());
                customerNew.setPortrait(json.get("avatarUrl").toString());
                customerService.insert(customerNew);
                result.setData(customerNew);
                MessageRecord messageRecord = new MessageRecord();
                messageRecord.setLastBrowseTime(new Date());
                messageRecord.setCustomerId(uuid);
                messageRecordService.insert(messageRecord);
                return result;
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("小程序登录失败："+e.getMessage());
            result.setCode(ReturnStatusEnum.RFC_ERROR.getValue());
            result.setMsg("登陆失败");
            return result;
        }

    }

//    /**
//     * 公众号网页授权登陆回调地址
//     * @return
//     */
//    @ApiOperation(httpMethod = "GET", value = "公众号网页授权登陆地址",nickname = "3")
//    @RequestMapping(value = "/h5Login",method = RequestMethod.GET)
//    private DataResult h5Login(){
//        DataResult result = new DataResult();
//        try {
//            result.setData(wxService.getCodeUrl());
//            return result;
//        } catch (Exception e){
//            e.printStackTrace();
//            logger.error("公众号网页授权登陆地址失败："+e.getMessage());
//            result.setCode(ReturnStatusEnum.RFC_ERROR.getValue());
//            result.setMsg("获取失败");
//            return result;
//        }
//    }

    /**
     * 公众号网页授权登陆回调地址
     * @return
     */
    @ApiOperation(httpMethod = "GET", value = "公众号网页授权登陆",nickname = "3")
    @RequestMapping(value = "/h5Login",method = RequestMethod.GET)
    private DataResult h5LoginCallBack(@ApiParam(value = "第一步用户点击授权拿到的code" ,required=true ) @RequestParam("code") String code){
        DataResult result = new DataResult();
        try {
//            String code = request.getParameter("code");

            // 第二步：通过code换取网页授权access_token
            JSONObject getTokenJson = wxService.code2AccessToken(code);
            logger.info("获取token,getTokenJson=" + getTokenJson.toJSONString());
            String openid = getTokenJson.getString("openid");
            String access_token = getTokenJson.getString("access_token");
            String refresh_token = getTokenJson.getString("refresh_token");
            if (access_token == null){
                return new DataResult(ReturnStatusEnum.CLIENT_ERROR.getValue(),"code换取网页授权access_token失败",null);
            }

            // 第五步：验证access_token是否失效
            JSONObject validTokenJson = wxService.verifyToken(access_token,openid);
            logger.info("验证token,validTokenJson=" + validTokenJson.toJSONString());
            if (!"0".equals(validTokenJson.getString("errcode"))) {
                // 第三步：刷新access_token（如果需要）
                JSONObject refreshTokenJson = wxService.refreshToken(refresh_token);
                logger.info("刷新token,refreshTokenJson=" + refreshTokenJson.toJSONString());
                access_token = refreshTokenJson.getString("access_token");
            }

            // 第四步：拉取用户信息(需scope为 snsapi_userinfo)
            JSONObject userInfoJson = wxService.getUserInfo(access_token,openid);
            logger.info("获取用户信息，getUserInfoJson=" + userInfoJson.toString());

            String unionId = userInfoJson.get("unionid").toString();
            //查询用户之前注册了吗
            Customer customer = customerService.selectOne(new EntityWrapper<Customer>().eq("wx_union_id",unionId));
            if(customer != null){
                if ( customer.gethOpenId() == null || customer.gethOpenId().equals("")){
                    customer.sethOpenId(openid);
                    customerService.updateById(customer);
                }
                result.setData(customer);
                return result;
            }else {
                Customer customerNew = new Customer();
                //拿背景图
                SystemImg systemImg = systemImgService.selectById(2);
                customerNew.setBackImg(systemImg.getValue());
                customerNew.setWxUnionId(unionId);
                String uuid = UUID.randomUUID().toString().toLowerCase();
                customerNew.setUuid(uuid);
                customerNew.sethOpenId(openid);
                customerNew.setPetNumber(NumberUtil.uuidToNum(uuid));
                customerNew.setName(userInfoJson.get("nickname").toString());
                customerNew.setPortrait(userInfoJson.get("headimgurl").toString());
                customerNew.setGender(userInfoJson.get("sex").toString());
                customerService.insert(customerNew);
                result.setData(customerNew);
                MessageRecord messageRecord = new MessageRecord();
                messageRecord.setLastBrowseTime(new Date());
                messageRecord.setCustomerId(uuid);
                messageRecordService.insert(messageRecord);
                return result;
            }
        }catch (Exception e){
            e.printStackTrace();
            logger.error("公众号网页授权登陆回调失败："+e.getMessage());
            result.setCode(ReturnStatusEnum.RFC_ERROR.getValue());
            result.setMsg("公众号网页授权登陆失败");
            return result;
        }
    }



    /**
     * 微信用户绑定手机号
     * @param bindPhoneQuery
     * @return
     */
    @ApiOperation(httpMethod = "POST", value = "微信用户绑定手机号",nickname = "4")
    @RequestMapping(value = "/bindPhone" ,method = RequestMethod.POST)
    private DataResult bandPhone(@RequestBody BindPhoneQuery bindPhoneQuery){
        try {
            DataResult codeResult = verificationCodeService.findVerificationCodeVos(bindPhoneQuery.getPhoneNum(),bindPhoneQuery.getCode(),ConstantsEnum.BIND_CODE.getCode());
            if(null != codeResult){
                return codeResult;
            }

            Customer oldCustomer = customerService.selectOne(new EntityWrapper<Customer>().eq("mobile",bindPhoneQuery.getPhoneNum()));
            if(null != oldCustomer) {
                if (null != oldCustomer.getWxUnionId()) {
                    return new DataResult(101, "该手机号已被其他账号绑定！请直接登录~", null);
                }
                customerService.deleteById(oldCustomer.getId());
            }
            Customer customer = new Customer();
            customer.setMobile(bindPhoneQuery.getPhoneNum());
            if (bindPhoneQuery.getPassword() != null && !"".equals(bindPhoneQuery.getPassword())){
                customer.setPassword(DigestUtils.md5Hex(bindPhoneQuery.getPassword()));
            }
            customerService.update(customer,new EntityWrapper<Customer>().eq("uuid",bindPhoneQuery.getCustomerId()));
            return new DataResult(200,"绑定成功！",null);

        }catch (Exception e){
            e.printStackTrace();
            logger.error("微信用户绑定手机号失败："+e.getMessage());
            DataResult result = new DataResult();
            result.setCode(ReturnStatusEnum.RFC_ERROR.getValue());
            result.setMsg("绑定失败");
            return result;
        }

    }
    /**
     * 手机号用户绑定微信
     * @param bindWxQuery
     * @return
     */
    @ApiOperation(httpMethod = "POST", value = "手机号用户绑定微信",nickname = "4")
    @RequestMapping(value = "/bindWx" ,method = RequestMethod.POST)
    private DataResult bindWx(@RequestBody BindWxQuery bindWxQuery){
        try {
            Customer oldCustomer = customerService.selectOne(new EntityWrapper<Customer>().eq("wx_union_id",bindWxQuery.getWxUnionId()));
            if(null != oldCustomer) {
                if (null != oldCustomer.getMobile()) {
                    return new DataResult(101, "该微信已被其他账号绑定！请直接登录~", null);
                }
                customerService.deleteById(oldCustomer.getId());
            }
            Customer customer = new Customer();
            customer.setWxUnionId(bindWxQuery.getWxUnionId());
            customerService.update(customer,new EntityWrapper<Customer>().eq("uuid",bindWxQuery.getCustomerId()));
            return new DataResult(200,"绑定成功！",null);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("手机号用户绑定微信失败："+e.getMessage());
            DataResult result = new DataResult();
            result.setCode(ReturnStatusEnum.RFC_ERROR.getValue());
            result.setMsg("绑定失败");
            return result;
        }

    }

    /**
     * 注销用户
     * @param uuidQuery
     * @return
     */
    @ApiOperation(httpMethod = "POST", value = "注销用户",nickname = "4")
    @RequestMapping(value = "/logout" ,method = RequestMethod.POST)
    private DataResult logout(@RequestBody UuidQuery uuidQuery){
        try {
            customerService.delete(new EntityWrapper<Customer>().eq("uuid",uuidQuery.getCustomerId()));
            return new DataResult(200,"注销成功！",null);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("注销用户失败："+e.getMessage());
            DataResult result = new DataResult();
            result.setCode(ReturnStatusEnum.RFC_ERROR.getValue());
            result.setMsg("注销失败");
            return result;
        }
    }

    /**
     * 根据用户uuid查询用户
     * @param uuid
     * @return
     */
    @ApiOperation(httpMethod = "GET",value = "根据uuid查询自己个人信息",notes = "uuid示例：ce47b06d-91d5-433b-8d80-b32d173c3fbf",nickname = "4")
    @RequestMapping(value="/customer", method=RequestMethod.GET)
    public DataResult queryCustomerById(@ApiParam(value = "用户id" ,required=true ) @RequestParam("uuid") String uuid){
        try {

            EntityWrapper<Customer> customerEntityWrapper = new EntityWrapper<>();
            customerEntityWrapper.eq("uuid",uuid);
            Customer customer = customerService.selectOne(customerEntityWrapper);
            if(null == customer){
                return new DataResult(ReturnStatusEnum.CLIENT_ERROR.getValue(),"获取用户失败！",null);
            }else {
                CustomerVo customerVo = new CustomerVo();
                BeanUtils.copyProperties(customer,customerVo);
                //查询粉丝数和关注数
                int attentionCount = customerRelationService.selectCount(new EntityWrapper<CustomerRelation>()
                        .eq("from_customer_id",customer.getUuid())
                        .eq("relation_type",1)
                        .eq("deleted",0));
                int fansCount = customerRelationService.selectCount(new EntityWrapper<CustomerRelation>()
                        .eq("to_customer_id",customer.getUuid())
                        .eq("relation_type",1)
                        .eq("deleted",0));
                customerVo.setAttentionCount(attentionCount);
                customerVo.setFansCount(fansCount);
                //我的作品数
                int worksCount = worksService.selectCount(new EntityWrapper<Works>().eq("customer_id",customer.getUuid()).andNew().eq("deleted",0).or().eq("deleted",2));
                customerVo.setWorksCount(worksCount);
                //点赞数
//                int pointCount = customerPointService.selectCount(new EntityWrapper<CustomerPoint>().eq("customer_id",customer.getUuid()).eq("type",1).eq("deleted",0));
                int pointCount = worksService.findPointWorksCount(customer.getUuid());
                customerVo.setPointCount(pointCount);
                //我的打赏数
//                int rewardCount = customerRewardService.selectCount(new EntityWrapper<CustomerReward>().eq("customer_id",customer.getUuid()));
//                customerVo.setRewardCount(rewardCount);
//                Map<String,Object> map = customerRewardService.selectMap(new EntityWrapper<CustomerReward>().setSqlSelect("ifnull(sum(coin),0) coin").eq("customer_id",customer.getUuid()));
//                customerVo.setRewardCount(Integer.parseInt(map.get("coin").toString()));

                //我打赏的金币数
                Map<String,Object> rewardCoinMap = customerRewardService.selectMap(new EntityWrapper<CustomerReward>().setSqlSelect("ifnull(sum(coin),0) coin").eq("customer_id",customer.getUuid()));
                customerVo.setRewardCoin(Integer.parseInt(rewardCoinMap.get("coin").toString()));
                //我获得的金币数
                Map<String,Object> getCoinMap = customerRewardService.selectMap(new EntityWrapper<CustomerReward>().setSqlSelect("ifnull(sum(coin),0) coin").eq("to_customer_id",customer.getUuid()));
                customerVo.setGetCoin(Integer.parseInt(getCoinMap.get("coin").toString()));

                //领养人数
                if (customer.getIsAdopted() == 1){
                    int adoptedCount = customerRelationService.selectCount(new EntityWrapper<CustomerRelation>().eq("from_customer_id",customer.getUuid()).eq("deleted",0).eq("is_adopt",1));
                    customerVo.setAdoptCount(adoptedCount);
                }
                return new DataResult(ReturnStatusEnum.SUCCESS.getValue(),"获取用户成功！",customerVo);
            }
        } catch (Exception e) {
            logger.error("获取用户异常，用户id{}，{}",uuid,e);
            return new DataResult(ReturnStatusEnum.SERVER_ERROR.getValue(),"获取用户异常！",null);
        }
    }

    /**
     * 重置密码
     * @param customerRegisterQuery
     * @return
     */
    @ApiOperation(httpMethod = "PUT",value = "重置密码",nickname = "5")
    @RequestMapping(value="/password", method=RequestMethod.PUT)
    public DataResult resetPasswordApi(@RequestBody CustomerRegisterQuery customerRegisterQuery){
        try {
            Customer customer = customerService.selectOne(new EntityWrapper<Customer>().eq("mobile",customerRegisterQuery.getPhoneNum()));
            if(null == customer){
                return new DataResult(ReturnStatusEnum.SYS_ERROR.getValue(),"没有此用户，请检查输入的手机号码！",null);
            }

            DataResult result = verificationCodeService.findVerificationCodeVos(customerRegisterQuery.getPhoneNum(),customerRegisterQuery.getCode(),ConstantsEnum.RESET_CODE.getCode());
            if(null != result){
                return result;
            }

            String password = DigestUtils.md5Hex(customerRegisterQuery.getPassword());
            customer.setPassword(password);
            customerService.updateById(customer);
            return new DataResult(ReturnStatusEnum.SUCCESS.getValue(),"修改成功",customer);
        } catch (Exception e) {
            logger.error("修改密码失败", e.getMessage());
            return new DataResult(ReturnStatusEnum.SYS_ERROR.getValue(),"修改失败",null);
        }
    }

    /**
     * 修改用户资料
     * @param customer
     * @return
     */
    @ApiOperation(httpMethod = "PUT",value = "修改用户资料",notes = "id不用管，uuid必传,其他修改什么传什么就可以",nickname = "5")
    @RequestMapping(value="/customer", method=RequestMethod.PUT)
    public DataResult updateUserApi(@RequestBody Customer customer){
        try {
            if(null == customer.getUuid() && customer.getUuid().equals("")){
                return new DataResult(ReturnStatusEnum.SYS_ERROR.getValue(),"uuid必传",null);
            }
            Customer customerOld = customerService.selectOne(new EntityWrapper<Customer>().eq("uuid",customer.getUuid()));
            if(null == customerOld){
                return new DataResult(ReturnStatusEnum.SYS_ERROR.getValue(),"没有此用户！",null);
            }
            customer.setId(customerOld.getId());
            customerService.updateById(customer);
            customer = customerService.selectById(customerOld.getId());
            return new DataResult(ReturnStatusEnum.SUCCESS.getValue(),"修改成功",customer);
        }catch (Exception e) {
            logger.error("修改用户资料失败", e);
            return new DataResult(ReturnStatusEnum.SYS_ERROR.getValue(),"修改失败",null);
        }
    }

    /**
     * 首页搜索用户
     * @return
     */
    @ApiOperation(httpMethod = "GET",value = "首页搜索用户",nickname = "6")
    @RequestMapping(value="/searchCustomers", method=RequestMethod.GET)
    public ListDataResult queryCustomers(@ApiParam(value = "页数", required = true) @RequestParam("page") Integer page,
                                         @ApiParam(value = "每页个数", required = true) @RequestParam("size") Integer size,
                                         @ApiParam(value = "搜索关键词" ,required=true) @RequestParam("keyWord") String keyWord,
                                         @ApiParam(value = "用户id(登陆后要传)", required = false) @RequestParam(value = "customerId",required = false) String customerId){
        ListDataResult result = new ListDataResult();
        try {
            Page<CustomerSearchVo> customerSearchVoPage = new Page<>(page,size);
            customerSearchVoPage = customerService.findCustomerSearchVos(customerSearchVoPage,keyWord,customerId);
            result.setCurrent(customerSearchVoPage.getCurrent());
            result.setTotal(customerSearchVoPage.getTotal());
            result.setData(customerSearchVoPage.getRecords());
        } catch (Exception e) {
            logger.error("首页搜索用户失败，key：{}，e:{}",keyWord, e);
            result.setCode(ReturnStatusEnum.SYS_ERROR.getValue());
            result.setMsg("搜索失败");
        }
        return result;
    }

    /**
     * 加关注
     * @return
     */
    @ApiOperation(httpMethod = "POST", value = "加关注/取消关注(关注和取消一个接口)",notes = "只需传2个参数  1：fromCustomerId-关注人uuid 2：toCustomerId-被关注人uuid ",nickname = "7")
    @RequestMapping(value = "/relation" ,method = RequestMethod.POST)
    private DataResult addRelation(@RequestBody CustomerRelation customerRelation){
        DataResult result = new DataResult();
        try {

            EntityWrapper<CustomerRelation> customerRelationEntityWrapper = new EntityWrapper<>();
            customerRelationEntityWrapper.eq("from_customer_id",customerRelation.getFromCustomerId());
            customerRelationEntityWrapper.eq("to_customer_id",customerRelation.getToCustomerId());
            customerRelationEntityWrapper.eq("relation_type",ConstantsEnum.CUSTOMER_RELATION_FANS.getCode());

            CustomerRelation customerRelationOld = customerRelationService.selectOne(customerRelationEntityWrapper);

            //判断之前有没有这条记录，如果有deleted改为相反的，否则新增一条
            if(null != customerRelationOld){
                if(customerRelationOld.getDeleted() == ConstantsEnum.DELETED.getCode()){
                    customerRelation.setDeleted(ConstantsEnum.NOTDELETED.getCode());
                    customerRelationService.update(customerRelation,customerRelationEntityWrapper);
                    result.setMsg("关注成功!");
                }
                if(customerRelationOld.getDeleted() == ConstantsEnum.NOTDELETED.getCode()){
                    customerRelation.setDeleted(ConstantsEnum.DELETED.getCode());
                    customerRelationService.update(customerRelation,customerRelationEntityWrapper);
                    result.setMsg("取消成功!");
                }
            }else{
                customerRelationService.insert(customerRelation);
                result.setMsg("关注成功!");
            }

            return result;
        }catch (Exception e){
            logger.error("加关注失败", e.getMessage());
            result.setCode(ReturnStatusEnum.RFC_ERROR.getValue());
            result.setMsg(ReturnStatusEnum.RFC_ERROR.getDesc());
            return result;
        }
    }
    /**
     * 设为领养人
     * @return
     */
    @ApiOperation(httpMethod = "POST", value = "设为领养人/取消",notes = "只需传2个参数  1：fromCustomerId-关注人uuid 2：toCustomerId-被关注人uuid ",nickname = "7")
    @RequestMapping(value = "/adopt" ,method = RequestMethod.POST)
    private DataResult addAdopt(@RequestBody CustomerRelation customerRelation){
        DataResult result = new DataResult();
        try {

            EntityWrapper<CustomerRelation> customerRelationEntityWrapper = new EntityWrapper<>();
            customerRelationEntityWrapper.eq("from_customer_id",customerRelation.getFromCustomerId());
            customerRelationEntityWrapper.eq("to_customer_id",customerRelation.getToCustomerId());
            customerRelationEntityWrapper.eq("relation_type",ConstantsEnum.CUSTOMER_RELATION_FANS.getCode());

            CustomerRelation customerRelationOld = customerRelationService.selectOne(customerRelationEntityWrapper);

            if(null != customerRelationOld){
                if(customerRelationOld.getIsAdopt() == ConstantsEnum.NOTDELETED.getCode()){
                    customerRelation.setDeleted(ConstantsEnum.NOTDELETED.getCode());
                    customerRelation.setIsAdopt(1);
                    customerRelationService.update(customerRelation,customerRelationEntityWrapper);
                    result.setMsg("设为领养成功!");
                }
                if(customerRelationOld.getIsAdopt() == ConstantsEnum.DELETED.getCode()){
//                    customerRelation.setDeleted(ConstantsEnum.DELETED.getCode());
                    customerRelation.setIsAdopt(0);
                    customerRelationService.update(customerRelation,customerRelationEntityWrapper);
                    result.setMsg("取消成功!");
                }
            }else{
                customerRelation.setIsAdopt(1);
                customerRelationService.insert(customerRelation);
                result.setMsg("设置成功!");
            }

            return result;
        }catch (Exception e){
            logger.error("设为领养人失败", e.getMessage());
            result.setCode(ReturnStatusEnum.RFC_ERROR.getValue());
            result.setMsg(ReturnStatusEnum.RFC_ERROR.getDesc());
            return result;
        }
    }
    /**
     * 给关注的人加备注
     * @return
     */
    @ApiOperation(httpMethod = "POST", value = "给关注的人加备注",notes = "只需传3个参数  1：fromCustomerId-关注人uuid 2：toCustomerId-被关注人uuid 3 remark-备注",nickname = "7")
    @RequestMapping(value = "/relationRemark" ,method = RequestMethod.POST)
    private DataResult addRemark(@RequestBody CustomerRelation customerRelation){
        DataResult result = new DataResult();
        try {

            EntityWrapper<CustomerRelation> customerRelationEntityWrapper = new EntityWrapper<>();
            customerRelationEntityWrapper.eq("from_customer_id",customerRelation.getFromCustomerId());
            customerRelationEntityWrapper.eq("to_customer_id",customerRelation.getToCustomerId());

            CustomerRelation customerRelationOld = customerRelationService.selectOne(customerRelationEntityWrapper);


            if(null != customerRelationOld){
                customerRelationOld.setRemark(customerRelation.getRemark());
                customerRelationService.updateById(customerRelationOld);
            }else{
                result.setCode(ReturnStatusEnum.RFC_ERROR.getValue());
                result.setMsg("你还未关注对方!");
                return result;
            }

            return result;
        }catch (Exception e){
            logger.error("给关注的人加备注", e.getMessage());
            result.setCode(ReturnStatusEnum.RFC_ERROR.getValue());
            result.setMsg(ReturnStatusEnum.RFC_ERROR.getDesc());
            return result;
        }
    }

    /**
     * 查看他人首页信息
     * @return
     */
    @ApiOperation(httpMethod = "GET", value = "查看他人首页信息",notes = "isShowMobile/isShowWx 是否展示手机号和微信号；只有为1时返回 mobile/wxNumber 字段;  isFans:0没关注他  1关注他了 ",nickname = "8")
    @RequestMapping(value = "/otherInfo" ,method = RequestMethod.GET)
    private DataResult getOtherInfo(@ApiParam(value = "自己的用户id(登陆后需传)", required = false) @RequestParam(value = "customerId",required = false) String customerId,
                                    @ApiParam(value = "他人的用户id", required = true) @RequestParam("OtherCustomerId") String OtherCustomerId){
        DataResult result = new DataResult();
        try {
            Customer customer = customerService.selectOne(new EntityWrapper<Customer>().eq("uuid",OtherCustomerId));
            if (customer == null){
                return new DataResult(ReturnStatusEnum.SYS_ERROR.getValue(),"查无此人",null);
            }
            Map<String,Object> map = new HashedMap();
            int attentionCount = customerRelationService.selectCount(new EntityWrapper<CustomerRelation>()
                    .eq("from_customer_id",OtherCustomerId)
                    .eq("relation_type",1)
                    .eq("deleted",0));
            int fansCount = customerRelationService.selectCount(new EntityWrapper<CustomerRelation>()
                    .eq("to_customer_id",OtherCustomerId)
                    .eq("relation_type",1)
                    .eq("deleted",0));
            map.put("attentionCount",attentionCount);
            map.put("fansCount",fansCount);
            map.put("customerId",customer.getUuid());
            map.put("customerName",customer.getName());
            map.put("portrait",customer.getPortrait());
            map.put("profile",customer.getProfile());
            map.put("backImg",customer.getBackImg());
            map.put("cityId",customer.getCityId());
            map.put("cityName",customer.getCityName());
            map.put("petNumber",customer.getPetNumber());
            map.put("isVerified",customer.getIsVerified());
            map.put("verifiedName",customer.getVerifiedName());
            map.put("isShowMobile",customer.getIsShowMobile());
            map.put("isShowWx",customer.getIsShowWx());
            map.put("businessName",customer.getBusinessName());
            map.put("vipEndTime",customer.getVipEndTime());
            if(customer.getIsShowMobile() == 1){
                map.put("mobile",customer.getMobile());
            }
            if(customer.getIsShowWx() == 1){
                map.put("wxNumber",customer.getWxNumber());
            }
            if(customerId != null && !"".equals(customerId)){

                CustomerRelation customerRelation = customerRelationService.selectOne(new EntityWrapper<CustomerRelation>()
                        .eq("from_customer_id",customerId)
                        .eq("to_customer_id",OtherCustomerId)
                        .eq("relation_type",1)
                        .eq("deleted",0));
                if (customerRelation != null){
                    map.put("isFans",1);
                    map.put("remark",customerRelation.getRemark());
                    map.put("isAdopt",customerRelation.getIsAdopt());
                }else {
                    map.put("isFans",0);
                }
            }else {
                map.put("isFans",0);
            }
            //我的作品数
            int worksCount = worksService.selectCount(new EntityWrapper<Works>().eq("customer_id",OtherCustomerId).andNew().eq("deleted",0).or().eq("deleted",2));
            map.put("worksCount",worksCount);
//            //点赞数
//            int pointCount = customerPointService.selectCount(new EntityWrapper<CustomerPoint>().eq("customer_id",OtherCustomerId).eq("type",1).eq("deleted",0));
//            map.put("pointCount",pointCount);
            //我的打赏数
//            int rewardCount = customerRewardService.selectCount(new EntityWrapper<CustomerReward>().eq("customer_id",OtherCustomerId));
//            map.put("rewardCount",rewardCount);
//            Map<String,Object> rewardMap = customerRewardService.selectMap(new EntityWrapper<CustomerReward>().setSqlSelect("ifnull(sum(coin),0) coin").eq("customer_id",customer.getUuid()));
//            map.put("rewardCount",rewardMap.get("coin"));

            //我获得的金币数
            Map<String,Object> getCoinMap = customerRewardService.selectMap(new EntityWrapper<CustomerReward>().setSqlSelect("ifnull(sum(coin),0) coin").eq("to_customer_id",customer.getUuid()));
            map.put("getCoin",Integer.parseInt(getCoinMap.get("coin").toString()));

            result.setData(map);
            return result;
        }catch (Exception e){
            logger.error("查询他人首页失败", e.getMessage());
            result.setCode(ReturnStatusEnum.RFC_ERROR.getValue());
            result.setMsg(ReturnStatusEnum.RFC_ERROR.getDesc());
            return result;
        }
    }

    /**
     * 乐乐页面上面用户头像列表
     * @return
     */
    @ApiOperation(httpMethod = "GET", value = "查询乐乐页面上面用户头像列表",nickname = "9")
    @RequestMapping(value = "/leleCustomers" ,method = RequestMethod.GET)
    private DataResult getLeleCustomers(){
        DataResult result = new DataResult();
        try {
            List<CustomerLeleVo> customerLeleVos = customerService.findCustomerLeleVos();
            result.setData(customerLeleVos);
            return result;
        }catch (Exception e){
            logger.error("查询乐乐页面上面用户头像列表", e.getMessage());
            result.setCode(ReturnStatusEnum.RFC_ERROR.getValue());
            result.setMsg(ReturnStatusEnum.RFC_ERROR.getDesc());
            return result;
        }
    }

    /**
     * 查询我的钱包剩余宠币和人民币
     * @return
     */
    @ApiOperation(httpMethod = "GET", value = "查询我的钱包剩余宠币和人民币和账户",nickname = "10")
    @RequestMapping(value = "/wallet" ,method = RequestMethod.GET)
    private DataResult getWallet(@ApiParam(value = "用户id", required = true) @RequestParam("customerId") String customerId){
        DataResult result = new DataResult();
        try {
            Customer customer = customerService.selectOne(new EntityWrapper<Customer>().eq("uuid",customerId));
            Integer petCoin = customer.getPetCoin();

            double petCoinMoney = petCoin/(10*1.0);
//            Config config = configService.selectById(2);
//            double tax = config.getValue().intValue()/(100*1.0);
            BigDecimal tax = customer.getRate().divide(new BigDecimal("100"));
            BigDecimal taxMoney = tax.multiply(new BigDecimal(petCoinMoney));

            Map<String,Object> map = new HashedMap(2);
            map.put("petCoin",petCoin);
            map.put("money",taxMoney.setScale( 0, BigDecimal.ROUND_DOWN ).longValue());
            map.put("account",customer.getAccount());
            result.setData(map);
            return result;
        }catch (Exception e){
            logger.error("查询我的钱包剩余宠币和人民币失败", e.getMessage());
            result.setCode(ReturnStatusEnum.RFC_ERROR.getValue());
            result.setMsg(ReturnStatusEnum.RFC_ERROR.getDesc());
            return result;
        }
    }

    /**
     * 查询我的被打赏明细
     * @return
     */
    @ApiOperation(httpMethod = "GET", value = "查询我的被打赏明细",nickname = "10")
    @RequestMapping(value = "/rewardDetail" ,method = RequestMethod.GET)
    private ListDataResult getRewardDetail(@ApiParam(value = "用户id", required = true) @RequestParam("customerId") String customerId,
                                           @ApiParam(value = "作品id", required = false) @RequestParam(value = "worksId",required = false) Long worksId,
                                       @ApiParam(value = "页数", required = true) @RequestParam("page") Integer page,
                                       @ApiParam(value = "每页个数", required = true) @RequestParam("size") Integer size){
        ListDataResult result = new ListDataResult();
        try {
            Page<RewardDetailVo> rewardDetailVoPage = new Page<>(page,size);
            rewardDetailVoPage = customerRewardService.findRewardDetailVos(rewardDetailVoPage,customerId,worksId);
            result.setCurrent(rewardDetailVoPage.getCurrent());
            result.setTotal(rewardDetailVoPage.getTotal());
            result.setData(rewardDetailVoPage.getRecords());
            return result;
        }catch (Exception e){
            logger.error("查询我的被打赏明细失败", e.getMessage());
            result.setCode(ReturnStatusEnum.RFC_ERROR.getValue());
            result.setMsg(ReturnStatusEnum.RFC_ERROR.getDesc());
            return result;
        }
    }

    /**
     * 查询我的被打赏总额
     * @return
     */
    @ApiOperation(httpMethod = "GET", value = "查询我的被打赏总额",nickname = "10")
    @RequestMapping(value = "/sumReward" ,method = RequestMethod.GET)
    private DataResult getSumReward(@ApiParam(value = "用户id", required = true) @RequestParam("customerId") String customerId,
                                        @ApiParam(value = "作品id", required = false) @RequestParam(value = "worksId",required = false) Long worksId){
        DataResult result = new DataResult();
        try {
            EntityWrapper<CustomerReward> customerRewardEntityWrapper = new EntityWrapper<>();
            customerRewardEntityWrapper.setSqlSelect("ifnull(sum(coin),0) coin");
            customerRewardEntityWrapper.eq("to_customer_id",customerId);
            if (worksId != null){
                customerRewardEntityWrapper.eq("works_id",worksId);
            }
            Map<String,Object> getCoinMap = customerRewardService.selectMap(customerRewardEntityWrapper);
            Map<String,Object> map = new HashMap<>(1);
            map.put("coin",Integer.parseInt(getCoinMap.get("coin").toString()));
            result.setData(map);
            return result;
        }catch (Exception e){
            logger.error("查询我的被打赏总额失败", e.getMessage());
            result.setCode(ReturnStatusEnum.RFC_ERROR.getValue());
            result.setMsg(ReturnStatusEnum.RFC_ERROR.getDesc());
            return result;
        }
    }

    /**
     * 绑定支付宝账户
     * @return
     */
    @ApiOperation(httpMethod = "POST", value = "绑定支付宝账户",nickname = "11")
    @RequestMapping(value = "/account" ,method = RequestMethod.POST)
    private DataResult bindAccount(@RequestBody BindAccountQuery bindAccountQuery){
        DataResult result = new DataResult();
        try {
            Customer customer = new Customer();
            customer.setAccount(bindAccountQuery.getAccount());
            customer.setRealName(bindAccountQuery.getRealName());
            customerService.update(customer,new EntityWrapper<Customer>().eq("uuid",bindAccountQuery.getCustomerId()));
            return result;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("绑定支付宝账户失败", e.getMessage());
            result.setCode(ReturnStatusEnum.RFC_ERROR.getValue());
            result.setMsg(ReturnStatusEnum.RFC_ERROR.getDesc());
            return result;
        }
    }

    /**
     * 解除绑定支付宝账户
     * @return
     */
    @ApiOperation(httpMethod = "PUT", value = "解除绑定支付宝账户",nickname = "12")
    @RequestMapping(value = "/removeAccount" ,method = RequestMethod.PUT)
    private DataResult removeAccount(@RequestBody UuidQuery uuidQuery){
        DataResult result = new DataResult();
        try {
            Customer customer = new Customer();
            customer.setAccount("");
            customer.setRealName("");
            customerService.update(customer,new EntityWrapper<Customer>().eq("uuid",uuidQuery.getCustomerId()));
            return result;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("解除绑定支付宝账户失败", e.getMessage());
            result.setCode(ReturnStatusEnum.RFC_ERROR.getValue());
            result.setMsg(ReturnStatusEnum.RFC_ERROR.getDesc());
            return result;
        }
    }

    /**
     * 宠币提现
     * @return
     */
    @ApiOperation(httpMethod = "POST", value = "宠币提现",nickname = "13")
    @RequestMapping(value = "/cashOut" ,method = RequestMethod.POST)
    private DataResult cashOut(@RequestBody CashOutQuery cashOutQuery){
        DataResult result = new DataResult();
        try {
            Customer customer = customerService.selectOne(new EntityWrapper<Customer>().eq("uuid",cashOutQuery.getCustomerId()));
            CashOut cashOut = new CashOut();
            if (cashOutQuery.getType() != null && cashOutQuery.getType() ==1){
                cashOut.setAccount(customer.getOpenId());
            }else {
                //核对支付宝是不是绑定的
                String realAccount = customer.getAccount();
                if(!cashOutQuery.getAccount().equals(realAccount)){
                    result.setCode(ReturnStatusEnum.CLIENT_ERROR.getValue());
                    result.setMsg("您提现的账户与您绑定的账户不符，请核对！");
                    return result;
                }
                cashOut.setAccount(realAccount);
                cashOut.setType(2);
            }
            //核对提现的金额和宠币是否对应
            int petCoin = customer.getPetCoin();
            double petCoinMoney = petCoin/(10*1.0);
//            Config config = configService.selectById(2);
//            double tax = config.getValue().intValue()/(100*1.0);
            BigDecimal tax = customer.getRate().divide(new BigDecimal("100"));
            BigDecimal taxMoney = tax.multiply(new BigDecimal(petCoinMoney));
            if (taxMoney.setScale( 0, BigDecimal.ROUND_DOWN ).longValue() < Math.floor(cashOutQuery.getMoney())){
                result.setCode(ReturnStatusEnum.CLIENT_ERROR.getValue());
                result.setMsg("您提现的金额与您的宠币金额不符合，请核对！");
                return result;
            }

            cashOut.setOriginalCoin(petCoin);
            cashOut.setOutCoin(petCoin);
            cashOut.setCustomerId(cashOutQuery.getCustomerId());
            cashOut.setPrice(taxMoney.setScale( 0, BigDecimal.ROUND_DOWN ).longValue());
            cashOut.setRate(customer.getRate());
            cashOutService.insert(cashOut);

            Customer customerNew = new Customer();
            customerNew.setPetCoin(0);
            customerNew.setId(customer.getId());
            customerService.updateById(customerNew);
            result.setMsg("您的提现申请已提交，请等待审核");
            return result;
        }catch (Exception e){
            e.printStackTrace();
            logger.error("宠币提现失败", e.getMessage());
            result.setCode(ReturnStatusEnum.RFC_ERROR.getValue());
            result.setMsg(ReturnStatusEnum.RFC_ERROR.getDesc());
            return result;
        }
    }

    /**
     * 查询我的关注/粉丝列表
     * @return
     */
    @ApiOperation(httpMethod = "GET", value = "查询我的关注/粉丝列表",nickname = "14")
    @RequestMapping(value = "/fansList" ,method = RequestMethod.GET)
    private ListDataResult getFansList(@ApiParam(value = "页数", required = true) @RequestParam("page") Integer page,
                                   @ApiParam(value = "每页个数", required = true) @RequestParam("size") Integer size,
                                   @ApiParam(value = "用户id", required = true) @RequestParam("customerId") String customerId,
                                   @ApiParam(value = "类型 1-关注列表 2-粉丝列表", required = true) @RequestParam("type") Integer type){
        ListDataResult result = new ListDataResult();
        try {
            Page<FansVo> fansVoPage = new Page<>(page,size);
            fansVoPage = customerRelationService.findFansVo(fansVoPage,customerId,type);
            result.setData(fansVoPage.getRecords());
            result.setCurrent(fansVoPage.getCurrent());
            result.setTotal(fansVoPage.getTotal());
            return result;
        }catch (Exception e){
            result.setCode(ReturnStatusEnum.RFC_ERROR.getValue());
            result.setMsg(ReturnStatusEnum.RFC_ERROR.getDesc());
            return result;
        }
    }

    /**
     * 查询我的领养人列表
     * @return
     */
    @ApiOperation(httpMethod = "GET", value = "查询我的领养人列表",nickname = "15")
    @RequestMapping(value = "/adoptList" ,method = RequestMethod.GET)
    private ListDataResult getAdoptList(@ApiParam(value = "页数", required = true) @RequestParam("page") Integer page,
                                       @ApiParam(value = "每页个数", required = true) @RequestParam("size") Integer size,
                                       @ApiParam(value = "用户id", required = true) @RequestParam("customerId") String customerId){
        ListDataResult result = new ListDataResult();
        try {
            Page<AdoptListVo> adoptListVoPage = new Page<>(page,size);
            adoptListVoPage = customerRelationService.findAdoptListVo(adoptListVoPage,customerId);
            result.setData(adoptListVoPage.getRecords());
            result.setCurrent(adoptListVoPage.getCurrent());
            result.setTotal(adoptListVoPage.getTotal());
            return result;
        }catch (Exception e){
            result.setCode(ReturnStatusEnum.RFC_ERROR.getValue());
            result.setMsg(ReturnStatusEnum.RFC_ERROR.getDesc());
            return result;
        }
    }
}

