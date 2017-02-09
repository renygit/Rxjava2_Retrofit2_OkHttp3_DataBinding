package com.reny.mvpvmlib.utils;

import android.content.Context;

import com.franmontiel.persistentcookiejar.ClearableCookieJar;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.google.gson.Gson;
import com.orhanobut.logger.LogLevel;
import com.orhanobut.logger.Logger;
import com.zhy.changeskin.SkinManager;

/**
 * Created by reny on 2017/2/9.
 */

public class InitUtils {

    public static Context mContext;
    public static Gson gson;
    public static ClearableCookieJar cookieJar;
    public static boolean isPrint;

    public static void init(Context context, boolean isPrintLog){
        mContext = context;
        isPrint = isPrintLog;
        gson = new Gson();
        cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));
        Logger.init().logLevel(LogLevel.FULL);
        SkinManager.getInstance().init(context);
    }

}
