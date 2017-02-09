package com.reny.mvpvmlib;

import android.app.Activity;
import android.content.Context;

import com.reny.mvpvmlib.utils.LogUtils;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BasePresenter<VM extends BaseViewModel> implements Presenter {

    private CompositeDisposable mCompositeDisposable;
    protected VM viewModel;
    protected Context context;
    protected Activity activity;

    public void setViewModel(VM viewModel) {
        this.viewModel = viewModel;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public abstract void onCreatePresenter();

    @Override
    public void onCreate() {
        this.mCompositeDisposable = new CompositeDisposable();
        onCreatePresenter();
    }

    @Override
    public void onDestroy() {
        if(null != mCompositeDisposable) {
            this.mCompositeDisposable.clear();
            this.mCompositeDisposable = null;
        }
        this.viewModel = null;
    }

    @Override
    public void loadData(boolean isRefresh) {

    }

    @Override
    public void addDisposable(Disposable disposable) {
        if(null != mCompositeDisposable){
            mCompositeDisposable.add(disposable);
        }
    }

    @Override
    public void onFailure(Throwable e){
        if(null != e && null != e.getMessage())
            LogUtils.e(e.getMessage());
    }

}
