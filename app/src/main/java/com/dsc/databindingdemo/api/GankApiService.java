package com.dsc.databindingdemo.api;

import com.dsc.databindingdemo.model.GankData;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by reny on 2017/1/5.
 */

public interface GankApiService {

    //BaseServiceFactory->createService() 获取属性为BASE_URL的值
    String BASE_URL = "https://gank.io/api/";

    String category_a = "福利";
    String category_b = "休息视频";

    /**
     * 分类数据: http://gank.io/api/data/数据类型/请求个数/第几页
     * 数据类型： 福利 | Android | iOS | 休息视频 | 拓展资源 | 前端 | all
     * 请求个数： 数字，大于0
     * 第几页：数字，大于0
     * eg: http://gank.io/api/data/Android/10/1
     */
    @GET("data/{category}/{count}/{page}")
    Observable<GankData> getGankIoData(@Path("category") String category, @Path("count") int count, @Path("page") int page);

}
