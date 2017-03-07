package com.reny.mvpvmlib;

import android.app.Activity;
import android.content.Context;

import com.reny.mvpvmlib.utils.InitUtils;
import com.reny.mvpvmlib.utils.LogUtils;
import com.reny.mvpvmlib.widget.EmptyStateView;
import com.tbruyelle.rxpermissions2.RxPermissions;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BasePresenter<VM extends BaseViewModel> implements Presenter {

    private CompositeDisposable mCompositeDisposable;
    protected VM viewModel;
    protected Context context;
    protected Activity activity;
    private RxPermissions rxPermissions;

    public void setViewModel(VM viewModel) {
        this.viewModel = viewModel;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public RxPermissions getRxPermissions(){
        if(null == activity)return null;
        if(null == rxPermissions)rxPermissions = new RxPermissions(activity);
        rxPermissions.setLogging(InitUtils.isPrint);
        return rxPermissions;
    }

    public abstract void onCreatePresenter();

    @Override
    public void onCreate() {
        this.mCompositeDisposable = new CompositeDisposable();
        if(null != viewModel) viewModel.setState(EmptyStateView.EmptyState.loading);
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
        if(null != viewModel) {
            if (!viewModel.firstLoadDataSuc) viewModel.setState(EmptyStateView.EmptyState.error);
            else {
                viewModel.isError.set(false);
                viewModel.isError.set(true);
            }
            viewModel.refreshComplete();
        }

        if(null != e && null != e.getMessage())
            LogUtils.e(e.getMessage());
    }

}
