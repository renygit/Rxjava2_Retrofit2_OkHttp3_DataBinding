package com.dsc.databindingdemo.ui;

import android.os.Bundle;
import android.text.TextUtils;

import com.dsc.databindingdemo.R;
import com.dsc.databindingdemo.core.BaseActivity;
import com.dsc.databindingdemo.core.BaseViewModel;
import com.dsc.databindingdemo.core.EmptyPresenter;
import com.dsc.databindingdemo.databinding.ActivityWebBinding;

public class WebActivity extends BaseActivity<ActivityWebBinding, BaseViewModel, EmptyPresenter> {

    @Override
    protected void init(Bundle savedInstanceState) {
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
