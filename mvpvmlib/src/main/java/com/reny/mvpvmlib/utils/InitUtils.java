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

import java.io.File;

/**
 * Created by reny on 2017/2/9.
 */

public class InitUtils {

    public static boolean isPrint;
    public static Gson gson;
    public static ClearableCookieJar cookieJar;
    public static File cacheDir;

    public static void init(Context context, boolean isPrintLog){
        isPrint = isPrintLog;
        gson = new Gson();
        cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));
        cacheDir = context.getCacheDir();
        Logger.init().logLevel(LogLevel.FULL);
        SkinManager.getInstance().init(context);
    }

}
