package com.dsc.databindingdemo.utils;

import android.net.Uri;
import android.text.TextUtils;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.reny.mvpvmlib.utils.LogUtils;

/**
 * Created by reny on 2016/6/17.
 */
public class FrescoUtils {

    private static Uri parseUri(String url){
        Uri uri = null;
        if(!TextUtils.isEmpty(url)){
            try {
                uri = Uri.parse(url);
            }catch (Exception e){
                LogUtils.e("FrescoUtils","Uri parse error:"+e.getMessage());
            }
        }
        return uri;
    }

    public static void display(SimpleDraweeView imgView, String url){
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(parseUri(url))
                .setAutoPlayAnimations(true)
                .build();
        imgView.setController(controller);
    }
}
