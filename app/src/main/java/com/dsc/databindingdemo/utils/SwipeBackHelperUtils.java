package com.dsc.databindingdemo.utils;

import android.app.Activity;

import com.jude.swipbackhelper.SwipeBackHelper;

public class SwipeBackHelperUtils {

    public static void DisableSwipeActivity(Activity activity) {
        SwipeBackHelper.getCurrentPage(activity)
                .setSwipeBackEnable(false);
    }

    public static void EnableSwipeActivity(Activity activity) {
        SwipeBackHelper.getCurrentPage(activity)
                .setSwipeBackEnable(true)
                .setSwipeEdgePercent(1.0f);
    }

    //带有Tab的滑动页 减少滑动退出的有效滑动退出范围
    public static void EnableSwipeTabActivity(Activity activity) {
        SwipeBackHelper.getCurrentPage(activity)
                .setSwipeBackEnable(true)
                .setSwipeEdgePercent(0.1f);
    }

}
