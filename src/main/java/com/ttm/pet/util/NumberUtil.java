package com.ttm.pet.util;

import ch.qos.logback.core.net.SyslogOutputStream;

import java.util.UUID;

public class NumberUtil {

    //uuid转成8位数字
    public static String uuidToNum(String uuid) {
        System.out.println(uuid);
        Integer orderId=uuid.hashCode();
        System.out.println(orderId);
        orderId = orderId < 0 ? -orderId : orderId;
        return orderId.toString().substring(0,9);
    }

}
