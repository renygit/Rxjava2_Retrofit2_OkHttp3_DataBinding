package com.reny.mvpvmlib;

import android.databinding.BaseObservable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.v7.widget.RecyclerView;

import com.reny.mvpvmlib.model.ObservableState;
import com.reny.mvpvmlib.utils.LogUtils;
import com.reny.mvpvmlib.widget.EmptyStateView;

import java.util.List;

/**
 * Created by reny on 2017/1/4.
 */

public class BaseViewModel extends BaseObservable {

    public ObservableBoolean isRefreshing = new ObservableBoolean(true);
    public ObservableState state = new ObservableState();
    public ObservableBoolean showState = new ObservableBoolean(true);

    public RecyclerView.LayoutManager layoutManager = null;

    public void refreshComplete() {
        isRefreshing.set(true);
        isRefreshing.set(false);
    }

    public void setState(EmptyStateView.EmptyState state) {
        this.state.setState(state);
        showState.set(!(state == EmptyStateView.EmptyState.hide));
    }

    //判断数据是否为空
    public void setDataState(Object datas) {
        boolean noData = (null == datas);
        if(datas instanceof List){
            noData = (((List) datas).size() == 0);
        }
        if (noData) setState(EmptyStateView.EmptyState.nodata);
        else setState(EmptyStateView.EmptyState.hide);
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return layoutManager;
    }

}
