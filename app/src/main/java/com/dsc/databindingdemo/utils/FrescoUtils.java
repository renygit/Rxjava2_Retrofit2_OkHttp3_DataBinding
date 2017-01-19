package com.dsc.databindingdemo.utils;

import android.net.Uri;
import android.text.TextUtils;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

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

    public static void displayWithResize(int width, int height, Uri uri, SimpleDraweeView simpleDraweeView){
        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(uri)
                .setResizeOptions(new ResizeOptions(width, height))
                .build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setOldController(simpleDraweeView.getController())
                .setImageRequest(request)
                .setAutoPlayAnimations(false)
                .build();
        simpleDraweeView.setController(controller);
    }

    public static void display(SimpleDraweeView imgView, String url){
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(parseUri(url))
                .setAutoPlayAnimations(true)
                .build();
        imgView.setController(controller);
    }
    public static void displayNoGif(SimpleDraweeView imgView, String url){
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(parseUri(url))
                .setAutoPlayAnimations(false)
                .build();
        imgView.setController(controller);
    }
    public static void display(SimpleDraweeView imgView, String url, ControllerListener listener){
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(parseUri(url))
                .setControllerListener(listener)
                .setAutoPlayAnimations(true)
                .setOldController(imgView.getController())
                .build();
        imgView.setController(controller);
    }

    public static void display(SimpleDraweeView imgView, String url, float aspectRatio){
        imgView.setAspectRatio(aspectRatio); // 设置宽高比
        display(imgView,url);
    }
}
