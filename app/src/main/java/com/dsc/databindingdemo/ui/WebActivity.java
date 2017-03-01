package com.dsc.databindingdemo.ui;

import android.os.Bundle;
import android.text.TextUtils;

import com.dsc.databindingdemo.R;
import com.dsc.databindingdemo.databinding.ActivityWebBinding;
import com.reny.mvpvmlib.BaseActivity;
import com.reny.mvpvmlib.BaseViewModel;
import com.reny.mvpvmlib.EmptyPresenter;
import com.reny.mvpvmlib.utils.SwipeBackUtils;

public class WebActivity extends BaseActivity<ActivityWebBinding, BaseViewModel, EmptyPresenter> {

    @Override
    protected void init(Bundle savedInstanceState) {
        SwipeBackUtils.EnableSwipeActivity(this, 0.1f);//设置可滑动退出的范围
        if(null != getIntent()){
            String url = getIntent().getStringExtra("url");
            if(TextUtils.isEmpty(url))url = "https://www.baidu.com";

            binding.webView.loadUrl(url);
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web;
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
