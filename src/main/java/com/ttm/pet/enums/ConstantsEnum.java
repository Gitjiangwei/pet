package com.ttm.pet.enums;

public enum ConstantsEnum {
    REGIEST_CODE(1),//注册验证码类型
    RESET_CODE(2),//重置密码类型
    BIND_CODE(3),//绑定类型
    DELETED(1),   //已删除
    NOTDELETED(0), //未删除/默认状态/未审核
    PASS(2),//审核通过
    FAILED(3),//审核不通过
    NO_READ(0),//消息未读
    READ(1),//消息已读
    MESSAGE_SYSTEM(1),//系统消息
    MESSAGE_DRAW(2),//中奖消息
    MESSAGE_DELETED(2),//消息删除
    CUSTOMER_RELATION_FANS(1),//关注
    CUSTOMER_RELATION_BLACKLIST(2),//黑名单
    PAY_STATUS_SUCCESS(2);//会员支付成功


    private int code;

    private ConstantsEnum(int code){
        this.code = code;
    }

    public int getCode() {
        return code;
    }

}
