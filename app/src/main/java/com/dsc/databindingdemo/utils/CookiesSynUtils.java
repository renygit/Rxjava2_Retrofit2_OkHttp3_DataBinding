package com.dsc.databindingdemo.utils;

import android.text.TextUtils;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;

import com.dsc.databindingdemo.MyApplication;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;

import java.util.List;

import okhttp3.Cookie;

/**
 * Created by reny on 2016/9/22.
 */

public class CookiesSynUtils {

    public static void synCookies(String url) {
        SharedPrefsCookiePersistor cookiePersistor = new SharedPrefsCookiePersistor(MyApplication.getContext());

        CookieSyncManager.createInstance(MyApplication.getContext());
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeAllCookie();

        List<Cookie> cookies=cookiePersistor.loadAll();

        StringBuffer sb = new StringBuffer();

        for ( Cookie cookie : cookies)
        {
            String cookieName = cookie.name();
            String cookieValue = cookie.value();
            if (!TextUtils.isEmpty(cookieName)
                    && !TextUtils.isEmpty(cookieValue)) {
                sb.append(cookieName + "=");
                sb.append(cookieValue + ";");
            }
        }

        String[] cookie = sb.toString().split(";");
        for (int i = 0; i < cookie.length; i++) {
            //LogUtils.d("cookie[i]:"+cookie[i]);
            cookieManager.setCookie(url, cookie[i]);// cookies是在HttpClient中获得的cookie
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.flush();
        } else {
            CookieSyncManager.getInstance().sync();
        }
    }

    public static void clearCookies(){
        CookieManager cookieManager = CookieManager.getInstance();
        cookieManager.setAcceptCookie(true);
        cookieManager.removeSessionCookie();// 移除
        cookieManager.removeAllCookie();
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.flush();
        } else {
            CookieSyncManager.getInstance().sync();
        }

        if(null != MyApplication.cookieJar) {
            MyApplication.cookieJar.clear();
            MyApplication.cookieJar.clearSession();
        }
    }
}
