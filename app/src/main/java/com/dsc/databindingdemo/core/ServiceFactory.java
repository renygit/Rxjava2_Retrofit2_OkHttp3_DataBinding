package com.dsc.databindingdemo.core;

import com.dsc.databindingdemo.api.APIConfig;
import com.dsc.databindingdemo.model.BaseModel;
import com.reny.mvpvmlib.http.BaseServiceFactory;
import com.reny.mvpvmlib.http.HttpBaseModel;
import com.reny.mvpvmlib.utils.LogUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by reny on 2017/2/9.
 */

public class ServiceFactory<S> extends BaseServiceFactory<S> {

    private static class SingletonHolder {
        private static final ServiceFactory INSTANCE = new ServiceFactory();
    }

    public static ServiceFactory getInstance() {
        return SingletonHolder.INSTANCE;
    }

    @Override
    public String getDefaultBaseUrl() {
        //默认BaseUrl
        return APIConfig.BASE_URL_DEFAULT;
    }

    @Override
    public Long getDefaultTimeOut() {
        //超时时间 返回null 将使用默认的10秒
        return null;
    }

    @Override
    public Response getResponse(Interceptor.Chain chain) throws IOException {
        //要添加统一请求头请参考注释代码
        /*Request originalRequest = chain.request();
        LogUtils.e(chain.request().url().url().toString());
        if(null == your token) return chain.proceed(originalRequest);
        Request authorised = originalRequest.newBuilder()
                .header("token", your token)
                .build();
        return chain.proceed(authorised);*/
        LogUtils.e(chain.request().url().url().toString());//打印要访问的地址 可注释
        return null;
    }

    @Override
    public Class<? extends HttpBaseModel> getBaseModelClass() {
        //统一错误处理的Model
        return BaseModel.class;
    }
}
