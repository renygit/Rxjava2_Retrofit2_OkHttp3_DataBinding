package com.dsc.databindingdemo.core.http;

import com.dsc.databindingdemo.MyApplication;
import com.dsc.databindingdemo.api.APIConfig;
import com.dsc.databindingdemo.core.http.converter.GsonConverterFactory;
import com.dsc.databindingdemo.utils.LogUtils;
import com.franmontiel.persistentcookiejar.PersistentCookieJar;
import com.franmontiel.persistentcookiejar.cache.SetCookieCache;
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;

/**
 * Created by reny on 2017/1/5.
 */

public class ServiceFactory<S> {

    private final static long DEFAULT_TIMEOUT = 10;//超时时间 10秒
    private Map<Class<S>, S> serviceMap;

    private static class SingletonHolder {
        private static final ServiceFactory INSTANCE = new ServiceFactory();
    }

    public static ServiceFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public S getService(Class<S> serviceClass){
        if(null == serviceMap){
            serviceMap = new HashMap<>();
        }
        if(null != serviceMap.get(serviceClass)){
            LogUtils.e("从Map取");
            return serviceMap.get(serviceClass);
        }
        S service = createService(serviceClass);
        serviceMap.put(serviceClass, service);
        LogUtils.e("新service");
        return service;
    }

    public S createService(Class<S> serviceClass) {
        String baseUrl = APIConfig.BASE_URL_DEFAULT;
        try {
            Field field1 = serviceClass.getField("BASE_URL");
            baseUrl = (String) field1.get(serviceClass);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.getMessage();
            e.printStackTrace();
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create(MyApplication.gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        return retrofit.create(serviceClass);
    }

    private OkHttpClient getOkHttpClient() {
        //定制OkHttp
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        //设置超时时间
        clientBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        clientBuilder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        clientBuilder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        clientBuilder.retryOnConnectionFailure(true);

        if(null == MyApplication.cookieJar)
            MyApplication.cookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(MyApplication.getContext()));
        clientBuilder.cookieJar(MyApplication.cookieJar);

        File cacheFile = new File(MyApplication.getContext().getCacheDir(), "HttpCache"); // 指定缓存路径
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); // 指定缓存大小100Mb
        clientBuilder.cache(cache);

        /*HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        clientBuilder.addInterceptor(interceptor);*/

        //为所有访问添加默认header 如token等
        Interceptor mTokenInterceptor = new Interceptor() {
            @Override public Response intercept(Chain chain) throws IOException {
                Request originalRequest = chain.request();
                /*if (Your.sToken == null || alreadyHasAuthorizationHeader(originalRequest)) {
                    return chain.proceed(originalRequest);
                }*/
                Request authorised = originalRequest.newBuilder()
                        //.header("token", Your.sToken)
                        .header("apikey", APIConfig.APIKEY)
                        .build();
                return chain.proceed(authorised);
            }
        };
        clientBuilder.addNetworkInterceptor(mTokenInterceptor);
        return clientBuilder.build();
    }
}
