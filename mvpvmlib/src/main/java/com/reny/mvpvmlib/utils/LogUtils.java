package com.reny.mvpvmlib.utils;

import android.util.Log;

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
        if (isPrint){
            try {
                Logger.e(TAG, msg);
            }catch (Exception e){
                Log.e("LogUtils.e", msg);
            }
        }
    }

    public final static void e(String msg) {
        if (isPrint) {
            try {
                Logger.e(msg, "");
            }catch (Exception e){
                Log.e("LogUtils.e", msg);
            }
        }
    }

    /**
     * 警告信息
     *
     * @param TAG
     * @param msg
     */
    public final static void w(String TAG, String msg) {
        if (isPrint){
            try {
                Logger.w(TAG, msg);
            }catch (Exception e){
                Log.w("LogUtils.w", msg);
            }
        }
    }

    /**
     * 调试信息
     *
     * @param TAG
     * @param msg
     */
    public final static void d(String TAG, String msg) {
        if (isPrint){
            try {
                Logger.d(TAG, msg);
            }catch (Exception e){
                Log.d("LogUtils.d", msg);
            }
        }
    }

    /**
     * 提示信息
     *
     * @param TAG
     * @param msg
     */
    public final static void i(String TAG, String msg) {
        if (isPrint){
            try {
                Logger.i(TAG, msg);
            }catch (Exception e){
                Log.i("LogUtils.i", msg);
            }
        }
    }

    public final static void d(String msg) {
        if (isPrint){
            try {
                Logger.d(msg);
            }catch (Exception e){
                Log.d("LogUtils.d", msg);
            }
        }
    }

    public final static void json(Object obj){
        if(isPrint){
            try {
                Logger.json(InitUtils.gson.toJson(obj));
            }catch (Exception e){
                Log.e("LogUtils.json", obj.toString());
            }
        }
    }

    public final static void e(Throwable e){
        if (isPrint)
            e(e.getMessage());
    }
}




