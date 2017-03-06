package com.dsc.databindingdemo.utils;

import android.graphics.Bitmap;
import android.net.Uri;
import android.text.TextUtils;

import com.dsc.databindingdemo.MyApplication;
import com.facebook.binaryresource.BinaryResource;
import com.facebook.binaryresource.FileBinaryResource;
import com.facebook.cache.common.CacheKey;
import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.references.CloseableReference;
import com.facebook.datasource.DataSource;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.cache.DefaultCacheKeyFactory;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.facebook.imagepipeline.core.ImagePipelineFactory;
import com.facebook.imagepipeline.datasource.BaseBitmapDataSubscriber;
import com.facebook.imagepipeline.image.CloseableImage;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;
import com.reny.mvpvmlib.utils.LogUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

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


    /***
     * 保存图片
     * @param picUrl        图片Url
     * @param imgCacheDir   图片准备保存的路径
     * @param imgName       图片要保存的名称
     */
    public static void savePicture(String picUrl, String imgCacheDir, String imgName) {
        File picDir = new File(imgCacheDir);
        if (!picDir.exists()) {
            picDir.mkdir();
        }
        CacheKey cacheKey = DefaultCacheKeyFactory.getInstance().getEncodedCacheKey(ImageRequest.fromUri(Uri.parse(picUrl)), null);
        File cacheFile = getCachedImageOnDisk(cacheKey);
        if (cacheFile == null) {
            downLoadImage(Uri.parse(picUrl), imgCacheDir, imgName);
        } else {
            copyTo(cacheFile, picDir, imgName);
        }
    }

    /***
     * 获取Fresco的缓存文件
     * @param cacheKey
     */
    private static File getCachedImageOnDisk(CacheKey cacheKey) {
        File localFile = null;
        if (cacheKey != null) {
            if (ImagePipelineFactory.getInstance().getMainFileCache().hasKey(cacheKey)) {
                BinaryResource resource = ImagePipelineFactory.getInstance().getMainFileCache().getResource(cacheKey);
                localFile = ((FileBinaryResource) resource).getFile();
            } else if (ImagePipelineFactory.getInstance().getSmallImageFileCache().hasKey(cacheKey)) {
                BinaryResource resource = ImagePipelineFactory.getInstance().getSmallImageFileCache().getResource(cacheKey);
                localFile = ((FileBinaryResource) resource).getFile();
            }
        }
        return localFile;
    }


    private static void downLoadImage(Uri uri, final String imgCacheDir, final String filename) {
        ImageRequest imageRequest = ImageRequestBuilder
                .newBuilderWithSource(uri)
                .setProgressiveRenderingEnabled(true)
                .build();
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        DataSource<CloseableReference<CloseableImage>>
                dataSource = imagePipeline.fetchDecodedImage(imageRequest, MyApplication.getContext());
        dataSource.subscribe(new BaseBitmapDataSubscriber() {
            @Override
            public void onNewResultImpl(Bitmap bitmap) {
                if (bitmap == null) {
                    ToastUtil.showShort("没有可用的存储设备");
                    return;
                }
                File appDir = new File(imgCacheDir);
                if (!appDir.exists()) {
                    appDir.mkdir();
                }
                String fileName = filename + ".jpg";
                File file = new File(appDir, fileName);
                try {
                    FileOutputStream fos = new FileOutputStream(file);
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
                    fos.flush();
                    fos.close();
                    refreshDirFile(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailureImpl(DataSource dataSource) {
            }
        }, CallerThreadExecutor.getInstance());
    }

    /**
     * 复制文件
     * @param src 源文件
     * @return
     */
    private static boolean copyTo(File src, File dir, String filename) {
        FileInputStream fi = null;
        FileOutputStream fo = null;
        FileChannel in = null;
        FileChannel out = null;
        try {
            fi = new FileInputStream(src);
            in = fi.getChannel();//得到对应的文件通道
            File dst;
            dst = new File(dir, filename + ".jpg");
            fo = new FileOutputStream(dst);
            out = fo.getChannel();//得到对应的文件通道
            in.transferTo(0, in.size(), out);//连接两个通道，并且从in通道读取，然后写入out通道
            refreshDirFile(dst);
            return true;
        } catch (IOException e) {
            return false;
        } finally {
            try {
                if (fi != null) fi.close();
                if (in != null) in.close();
                if (fo != null) fo.close();
                if (out != null) out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void refreshDirFile(File file) {
        ToastUtil.showLong("图片已保存到" + file.getAbsolutePath());
        FileUtils.notifyFileSystemChanged(file.getAbsolutePath());
    }

}
