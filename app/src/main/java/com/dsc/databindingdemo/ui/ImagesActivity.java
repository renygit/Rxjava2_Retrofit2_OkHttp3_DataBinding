package com.dsc.databindingdemo.ui;

import android.net.Uri;
import android.os.Bundle;

import com.dsc.databindingdemo.R;
import com.dsc.databindingdemo.databinding.ActivityImagesBinding;
import com.reny.mvpvmlib.BaseActivity;
import com.reny.mvpvmlib.BaseViewModel;
import com.reny.mvpvmlib.EmptyPresenter;
import com.reny.mvpvmlib.utils.SwipeBackUtils;

public class ImagesActivity extends BaseActivity<ActivityImagesBinding, BaseViewModel, EmptyPresenter> {

    @Override
    protected void init(Bundle savedInstanceState) {
        SwipeBackUtils.DisableSwipeActivity(this);
        if(null != getIntent()){
            String url = getIntent().getStringExtra("url");
            binding.pdv.setPhotoUri(Uri.parse(url));
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
}
