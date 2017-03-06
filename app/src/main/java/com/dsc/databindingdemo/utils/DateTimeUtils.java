package com.dsc.databindingdemo.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by reny on 2016/7/27.
 */

public class DateTimeUtils {

    private static Locale locale = Locale.CHINA;

    //"yyyy-MM-dd HH:mm:ss"
    public static String getCurDateStr(SimpleDateFormat formater) {
        return formater.format(new Date(System.currentTimeMillis()));
    }

    public static SimpleDateFormat getFormatYMDHMStimeStamp(){
        return new SimpleDateFormat("yyyyMMddHHmmss", locale);
    }
}
