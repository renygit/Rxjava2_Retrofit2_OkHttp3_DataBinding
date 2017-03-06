package com.dsc.databindingdemo.core;

import android.databinding.ViewDataBinding;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.reny.mvpvmlib.BaseActivity;
import com.reny.mvpvmlib.BasePresenter;
import com.reny.mvpvmlib.BaseViewModel;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import qiu.niorgai.StatusBarCompat;

/**
 * Created by reny on 2017/3/6.
 */

public abstract class MyBaseActivity<DB extends ViewDataBinding, VM extends BaseViewModel, P extends BasePresenter<VM>> extends BaseActivity<DB,VM,P> {

    //init之前调用的方法 做项目的特殊初始化工作
    @Override
    protected void preInit(Bundle savedInstanceState) {
        if (isTranslucentStatusBar()) {
            StatusBarCompat.translucentStatusBar(this);

            setStatusBarColor(Color.TRANSPARENT);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                StatusBarCompat.translucentStatusBar(this, true);
            }
        }
    }

    protected void setStatusBarColor(int color){
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            //android 4.4 的处理
            StatusBarCompat.setStatusBarColor(this, color, 112);
        }else {
            StatusBarCompat.setStatusBarColor(this, color);
        }
    }

    //设置状态栏图标、文字颜色
    protected void setDarkStatusIcon(boolean bDark) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            View decorView = getWindow().getDecorView();
            if (decorView != null) {
                int vis = decorView.getSystemUiVisibility();
                if (bDark) {
                    vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                } else {
                    vis &= ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                }
                decorView.setSystemUiVisibility(vis);
            }
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }

        Class<? extends Window> clazz = getWindow().getClass();
        try {
            int darkModeFlag = 0;
            Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
            Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
            darkModeFlag = field.getInt(layoutParams);
            Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
            extraFlagField.invoke(getWindow(), bDark ? darkModeFlag : 0, darkModeFlag);
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (getWindow() != null) {
            try {
                WindowManager.LayoutParams lp = getWindow().getAttributes();
                Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
                Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
                darkFlag.setAccessible(true);
                meizuFlags.setAccessible(true);
                int bit = darkFlag.getInt(null);
                int value = meizuFlags.getInt(lp);
                if (bDark) {
                    value |= bit;
                } else {
                    value &= ~bit;
                }
                meizuFlags.setInt(lp, value);
                getWindow().setAttributes(lp);
            } catch (Exception e) {
            }
        }
    }

    //是否设置状态栏为透明
    protected boolean isTranslucentStatusBar() {
        return true;
    }

}
