package com.dsc.databindingdemo.api;

import com.dsc.databindingdemo.model.HotMovieData;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by reny on 2017/1/5.
 */

public interface DoubanApiService {

    //BaseServiceFactory->createService() 获取属性为BASE_URL的值
    String BASE_URL = "https://api.douban.com/v2/";

    /**
     * 豆瓣热映电影，每日更新
     */
    @GET("movie/in_theaters")
    Observable<HotMovieData> getHotMovie();

}
