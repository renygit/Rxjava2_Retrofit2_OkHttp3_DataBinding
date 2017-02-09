package com.reny.mvpvmlib;

import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.support.v7.widget.RecyclerView;

/**
 * Created by reny on 2017/1/4.
 * 尚未完善界面的一些状态 Loading、Empty、Error 等
 */

public class BaseViewModel extends BaseObservable {

    public ObservableBoolean isRefreshing = new ObservableBoolean(true);
    public ObservableBoolean isLoading = new ObservableBoolean(true);
    public ObservableBoolean isEmpty = new ObservableBoolean(false);
    public ObservableBoolean isError = new ObservableBoolean(false);

    public RecyclerView.LayoutManager layoutManager = null;

    public void refreshComplete() {
        isRefreshing.set(true);
        isRefreshing.set(false);
    }

    public void setState(boolean isLoading, boolean isEmpty, boolean isError) {
        this.isLoading.set(isLoading);
        this.isEmpty.set(isEmpty);
        this.isError.set(isError);
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    public ObservableBoolean getIsLoading() {
        return isLoading;
    }

    public ObservableBoolean getIsEmpty() {
        return isEmpty;
    }

    public ObservableBoolean getIsError() {
        return isError;
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return layoutManager;
    }

}
