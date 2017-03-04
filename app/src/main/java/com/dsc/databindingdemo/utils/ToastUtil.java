package com.dsc.databindingdemo.utils;

import com.dsc.databindingdemo.MyApplication;
import com.github.johnpersano.supertoasts.library.Style;
import com.github.johnpersano.supertoasts.library.SuperToast;

/***
 * 解决部分手机上打印 Toast 需要权限的问题
 */
public class ToastUtil {

    private static Style style;

    private ToastUtil() {
    }

    private static void checkStyle() {
        if (null == style) {
            style = new Style();
            style.animations = Style.ANIMATIONS_POP;
            //给Toast加图标
            /*style.messageIconResource = R.mipmap.ic_toast;
            style.messageIconPosition = Style.ICONPOSITION_LEFT;*/
        }
    }


    public static void showShort(int resId) {
        checkStyle();
        SuperToast.create(MyApplication.getContext(), MyApplication.getContext().getString(resId), Style.DURATION_SHORT, style).show();
    }


    public static void showShort(String message) {
        checkStyle();
        SuperToast.create(MyApplication.getContext(), message, Style.DURATION_SHORT, style).show();
    }


    public static void showLong(int resId) {
        checkStyle();
        SuperToast.create(MyApplication.getContext(), MyApplication.getContext().getString(resId), Style.DURATION_LONG, style).show();
    }


    public static void showLong(String message) {
        checkStyle();
        SuperToast.create(MyApplication.getContext(), message, Style.DURATION_LONG, style).show();
    }
}
