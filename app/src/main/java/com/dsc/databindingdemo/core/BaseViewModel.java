package com.dsc.databindingdemo.core;

import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.support.v7.widget.RecyclerView;

/**
 * Created by reny on 2017/1/4.
 */

public class BaseViewModel extends BaseObservable {

    public ObservableBoolean isLoading = new ObservableBoolean(true);
    public ObservableBoolean isEmpty = new ObservableBoolean(false);
    public ObservableBoolean isError = new ObservableBoolean(false);

    public RecyclerView.LayoutManager layoutManager = null;

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
