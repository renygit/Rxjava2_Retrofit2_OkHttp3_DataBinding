package com.reny.mvpvmlib.http;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.reny.mvpvmlib.http.converter.GsonConverterFactory;
import com.reny.mvpvmlib.utils.InitUtils;
import com.reny.mvpvmlib.utils.LogUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * Created by reny on 2017/1/5.
 */

public abstract class BaseServiceFactory<S> {

    private static long DEFAULT_TIMEOUT = 10;//超时时间 10秒
    private Map<Class<S>, S> serviceMap;

    public S getService(Class<S> serviceClass){
        if(null == serviceMap){
            serviceMap = new HashMap<>();
        }
        if(null != serviceMap.get(serviceClass)){
            //LogUtils.e("从Map取:"+serviceClass.getName());
            return serviceMap.get(serviceClass);
        }
        S service = createService(serviceClass);
        serviceMap.put(serviceClass, service);
        //LogUtils.e("新service:"+serviceClass.getName());
        return service;
    }

    public S createService(Class<S> serviceClass) {
        String baseUrl = getDefaultBaseUrl();
        try {
            Field field1 = serviceClass.getField("BASE_URL");
            baseUrl = (String) field1.get(serviceClass);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            LogUtils.e("Your Service NoSuchFieldException: BASE_URL");
        } catch (IllegalAccessException e) {
            e.getMessage();
            e.printStackTrace();
            LogUtils.e("IllegalAccessException");
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create(InitUtils.gson, getBaseModelClass()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(serviceClass);
    }

    private OkHttpClient getOkHttpClient() {
        //定制OkHttp
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        //设置超时时间
        DEFAULT_TIMEOUT = (getDefaultTimeOut() == null ? DEFAULT_TIMEOUT : getDefaultTimeOut());
        clientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        clientBuilder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        clientBuilder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        clientBuilder.retryOnConnectionFailure(true);

        clientBuilder.cookieJar(InitUtils.cookieJar);

        File cacheFile = new File(InitUtils.cacheDir, "HttpCache"); // 指定缓存路径
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); // 指定缓存大小100Mb
        clientBuilder.cache(cache);

        /*HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        clientBuilder.addInterceptor(interceptor);*/

        //为所有访问添加默认header 如token等
        Interceptor mTokenInterceptor = new Interceptor() {
            @Override public Response intercept(Chain chain) throws IOException {
                return null == getResponse(chain) ? chain.proceed(chain.request()) : getResponse(chain);
            }
        };
        clientBuilder.addNetworkInterceptor(mTokenInterceptor);
        return clientBuilder.build();
    }

    @Nullable
    public abstract String getDefaultBaseUrl();
    @Nullable
    public abstract Long getDefaultTimeOut();
    @Nullable
    public abstract Response getResponse(Interceptor.Chain chain) throws IOException;

    @NonNull
    public abstract Class<? extends HttpBaseModel> getBaseModelClass();

}
