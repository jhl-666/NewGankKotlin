package com.ljhdemo.newgank.common.utils;


import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by ljh on 2017/12/29.
 */

public class DateUtils {
    public static String getDate(long currentTimeMillis) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日    HH:mm:ss     ");
        Date curDate = new Date(currentTimeMillis);//获取当前时间
        String date = formatter.format(curDate);
        return date;
    }
}
