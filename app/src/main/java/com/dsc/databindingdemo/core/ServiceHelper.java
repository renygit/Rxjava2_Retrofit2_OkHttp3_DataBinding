package com.dsc.databindingdemo.core;

import com.dsc.databindingdemo.api.GankApiService;

/**
 * Created by reny on 2017/2/9.
 */

public class ServiceHelper {

    public static GankApiService getGankAS(){
        return (GankApiService) ServiceFactory.getInstance().getService(GankApiService.class);
    }

}
