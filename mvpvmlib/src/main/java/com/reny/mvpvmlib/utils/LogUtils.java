package com.reny.mvpvmlib.utils;

import com.orhanobut.logger.Logger;


public class LogUtils {

    /**
     * 是否打印日志
     */
    public static final boolean isPrint = InitUtils.isPrint;


    /**
     * 错误信息
     *
     * @param TAG
     * @param msg
     */
    public final static void e(String TAG, String msg) {
        if (isPrint)
            Logger.e(TAG, msg);

    }

    public final static void e(String msg) {
        if (isPrint)
            Logger.e(msg, "");
    }

    /**
     * 警告信息
     *
     * @param TAG
     * @param msg
     */
    public final static void w(String TAG, String msg) {
        if (isPrint)
            Logger.w(TAG, msg);

    }

    /**
     * 调试信息
     *
     * @param TAG
     * @param msg
     */
    public final static void d(String TAG, String msg) {
        if (isPrint)
            Logger.d(TAG, msg);
    }

    /**
     * 提示信息
     *
     * @param TAG
     * @param msg
     */
    public final static void i(String TAG, String msg) {
        if (isPrint)
            Logger.i(TAG, msg);
    }

    public final static void d(String msg) {
        if (isPrint)
            Logger.d(msg);
    }

    public final static void json(Object obj){
        if(isPrint)
            Logger.json(InitUtils.gson.toJson(obj));
    }

    public final static void e(Throwable e){
        if (isPrint)
            Logger.d(e.getMessage());
    }
}




