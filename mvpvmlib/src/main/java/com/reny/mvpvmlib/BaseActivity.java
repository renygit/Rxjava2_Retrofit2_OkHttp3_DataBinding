package com.reny.mvpvmlib;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.jude.swipbackhelper.SwipeBackHelper;
import com.reny.mvpvmlib.utils.SwipeBackUtils;
import com.zhy.changeskin.SkinManager;

/**
 * Created by reny on 2017/1/4.
 */

public abstract class BaseActivity<DB extends ViewDataBinding, VM extends BaseViewModel, P extends BasePresenter<VM>> extends AppCompatActivity{

    protected DB binding;
    protected P presenter;
    protected VM viewModel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        preOnCreate();
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, getLayoutId());

        SwipeBackHelper.onCreate(this);
        SwipeBackUtils.EnableSwipeActivity(this, null);

        if(null == presenter){
            try {
                if(null == getPresenterClass()) presenter = null;
                else presenter = getPresenterClass().newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        if(null == viewModel){
            try {
                if(null == getViewModelClass()) viewModel = null;
                else viewModel = getViewModelClass().newInstance();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        if(null != presenter && null != viewModel){
            presenter.setViewModel(viewModel);
        }

        if (null != presenter) {
            //确保init前 presenter 拿到了context activity
            presenter.setContext(this);
            presenter.setActivity(this);
        }

        preInit(savedInstanceState);
        this.init(savedInstanceState);

        if (null != presenter) {
            //确保init调用后再执行  一般是网络等耗时操作
            presenter.onCreate();
        }

        SkinManager.getInstance().register(this);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        SwipeBackHelper.onCreate(this);
    }

    @Override
    protected void onDestroy() {
        SwipeBackHelper.onDestroy(this);
        if (null != presenter) presenter.onDestroy();
        viewModel = null;
        presenter = null;
        SkinManager.getInstance().unregister(this);
        super.onDestroy();
    }

    protected void preOnCreate(){}
    protected void preInit(Bundle savedInstanceState){}

    protected abstract void init(Bundle savedInstanceState);

    protected abstract int getLayoutId();

    protected abstract Class<P> getPresenterClass();

    protected abstract Class<VM> getViewModelClass();

}
