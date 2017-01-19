package com.dsc.databindingdemo.api;

import com.dsc.databindingdemo.model.GankData;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by reny on 2017/1/5.
 */

public interface BaiduApiService {

    //ServiceFactory->createService() 获取属性为BASE_URL的值
    String BASE_URL = "http://gank.io/api/data/";

    @GET("all/20/{page}")
    Observable<GankData> getGankData(@Path("page") int page);

}
