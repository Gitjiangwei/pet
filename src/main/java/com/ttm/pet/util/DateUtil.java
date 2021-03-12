package com.ttm.pet.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {

    public static Date String2Date(String DateStr) {
        Date date = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = sdf.parse(DateStr);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String Date2String(Date date) {
        String newDate;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        newDate = sdf.format(date);
        return newDate;
    }

    /**
     * 字符串转秒
     * 
     * @param DateStr
     * @return
     */
    public static Long string2long(String DateStr) {
        Long timestamp = null;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            timestamp = sdf.parse(DateStr).getTime() / 1000;
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return timestamp;
    }

    /**
     * 秒数转时间
     * 
     * @param timestamp
     * @return
     */
    public static Date long2date(long timestamp) {
        Date date = new Date();
        long time = timestamp * 1000l;
        date.setTime(time);
        return date;
    }

    /**
     * 字符串转秒
     * 
     * @param date
     * @return
     */
    public static Long date2long(Date date) {
        Long timestamp = null;
        timestamp = date.getTime() / 1000;
        return timestamp;
    }

    /**
     * 计算两个时间相差的天数
     *
     * @param endDate 结束时间
     * @param nowDate 开始时间
     * @return 相差的天数
     * @author J
     * @date 2021/3/6
     */
    public static String getDatePoor(Date endDate, Date nowDate) {
        if (endDate == null){
            return "0";
        }
        long nd = 1000 * 24 * 60 * 60;
        // 获得两个时间的毫秒时间差异
        long diff = endDate.getTime() - nowDate.getTime();
        // 计算差多少天
        return String.valueOf(diff / nd);
    }
}
