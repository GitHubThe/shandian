package com.developer.shan.utils;

/**
 * Created by HG on 2017/6/10.
 */

public class TimeUtils {
    //getTime方法返回的就是10位的时间戳
    public static String getTime(){
        long time=System.currentTimeMillis()/1000;//获取系统时间的10位的时间戳

        String  str=String.valueOf(time);

        return str;

    }
}
