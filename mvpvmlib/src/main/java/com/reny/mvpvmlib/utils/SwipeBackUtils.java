package com.reny.mvpvmlib.utils;

import android.app.Activity;

import com.jude.swipbackhelper.SwipeBackHelper;

public class SwipeBackUtils {

    public static void DisableSwipeActivity(Activity activity) {
        SwipeBackHelper.getCurrentPage(activity)
                .setSwipeBackEnable(false);
    }

    public static void EnableSwipeActivity(Activity activity, Float percent) {
        SwipeBackHelper.getCurrentPage(activity)
                .setSwipeBackEnable(true)
                .setSwipeEdgePercent(percent == null ? 1.0f : percent);
    }

}
