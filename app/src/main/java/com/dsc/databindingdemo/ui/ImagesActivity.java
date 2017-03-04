package com.dsc.databindingdemo.ui;

import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

import com.dsc.databindingdemo.R;
import com.dsc.databindingdemo.databinding.ActivityImagesBinding;
import com.dsc.databindingdemo.model.custom.ImgsInfo;
import com.dsc.databindingdemo.widget.ImageLoadingDrawable;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.BaseControllerListener;
import com.facebook.drawee.drawable.ScalingUtils;
import com.facebook.imagepipeline.image.ImageInfo;
import com.reny.mvpvmlib.BaseActivity;
import com.reny.mvpvmlib.BaseViewModel;
import com.reny.mvpvmlib.EmptyPresenter;
import com.reny.mvpvmlib.utils.SwipeBackUtils;

import java.util.List;

import me.relex.photodraweeview.OnViewTapListener;
import me.relex.photodraweeview.PhotoDraweeView;

public class ImagesActivity extends BaseActivity<ActivityImagesBinding, BaseViewModel, EmptyPresenter> {

    @Override
    protected void init(Bundle savedInstanceState) {
        SwipeBackUtils.DisableSwipeActivity(this);
        binding.flRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        if(null != getIntent()){
            ImgsInfo imgsInfo = (ImgsInfo) getIntent().getSerializableExtra(ImgsInfo.KEY);
            binding.vpImg.setPageMargin((int) (getResources().getDisplayMetrics().density * 15));
            binding.vpImg.setAdapter(new DraweePagerAdapter(imgsInfo.getImgsList()));
            binding.vpImg.setCurrentItem(imgsInfo.getCurPos());
            binding.vpImg.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {
                    //TODO
                    if(position > binding.vpImg.getAdapter().getCount()-4){

                    }
                }

                @Override
                public void onPageScrollStateChanged(int state) {
                }
            });
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_images;
    }

    @Override
    protected Class<EmptyPresenter> getPresenterClass() {
        return null;
    }

    @Override
    protected Class<BaseViewModel> getViewModelClass() {
        return null;
    }



    public class DraweePagerAdapter extends PagerAdapter {

        private List<String> imgsUrl;

        public DraweePagerAdapter(List<String> imgsUrl){
            this.imgsUrl = imgsUrl;
        }

        @Override
        public int getCount() {
            return imgsUrl.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public Object instantiateItem(ViewGroup viewGroup, int position) {
            final PhotoDraweeView photoDraweeView = new PhotoDraweeView(viewGroup.getContext());
            photoDraweeView.getHierarchy().setProgressBarImage(new ImageLoadingDrawable());
            photoDraweeView.getHierarchy().setFailureImage(getResources().getDrawable(R.color.img_error_color), ScalingUtils.ScaleType.FIT_CENTER);
            PipelineDraweeControllerBuilder controller = Fresco.newDraweeControllerBuilder();
            controller.setAutoPlayAnimations(true);
            controller.setUri(Uri.parse(imgsUrl.get(position)));
            controller.setOldController(photoDraweeView.getController());
            controller.setControllerListener(new BaseControllerListener<ImageInfo>() {
                @Override
                public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                    super.onFinalImageSet(id, imageInfo, animatable);
                    if (imageInfo == null) {
                        return;
                    }
                    photoDraweeView.update(imageInfo.getWidth(), imageInfo.getHeight());
                }
            });
            photoDraweeView.setController(controller.build());
            photoDraweeView.setOnViewTapListener(new OnViewTapListener() {
                @Override
                public void onViewTap(View view, float x, float y) {
                    finish();
                }
            });

            try {
                viewGroup.addView(photoDraweeView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return photoDraweeView;
        }
    }


}
