package com.dsc.databindingdemo.ui;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dsc.databindingdemo.BuildConfig;
import com.dsc.databindingdemo.R;
import com.dsc.databindingdemo.core.MyBaseActivity;
import com.dsc.databindingdemo.databinding.ActivityWebBinding;
import com.reny.mvpvmlib.BaseViewModel;
import com.reny.mvpvmlib.EmptyPresenter;
import com.reny.mvpvmlib.utils.SwipeBackUtils;

public class WebActivity extends MyBaseActivity<ActivityWebBinding, BaseViewModel, EmptyPresenter> {

    @Override
    protected void init(Bundle savedInstanceState) {
        SwipeBackUtils.EnableSwipeActivity(this, 0.1f);//设置可滑动退出的范围

        initWebViewSettings();

        if(null != getIntent()){
            String url = getIntent().getStringExtra("url");
            if(TextUtils.isEmpty(url))url = "about:blank";

            binding.webView.loadUrl(url);
        }

        binding.webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        });

        binding.webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                binding.toolbar.setTitle(title);
            }
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                binding.pb.setProgress(newProgress);
                if (newProgress != 100) {
                    binding.pb.setVisibility(View.VISIBLE);
                } else {
                    binding.pb.setVisibility(View.GONE);
                }
            }
        });
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//给左上角图标的左边加上一个返回的图标
        binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(binding.webView.canGoBack()){
                    binding.webView.goBack();
                }else {
                    finish();
                }
            }
        });
    }

    private void initWebViewSettings() {
        binding.webView.setVerticalScrollBarEnabled(false);
        WebSettings webSetting = binding.webView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        //webSetting.setSupportZoom(true);
        // webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(true);
        webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            WebView.setWebContentsDebuggingEnabled(BuildConfig.DEBUG);
        }
        //Android 5.0中使用webView 加载网页不显示图片问题
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            webSetting.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && binding.webView.canGoBack()) {
            binding.webView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
