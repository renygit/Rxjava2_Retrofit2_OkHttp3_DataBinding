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

    public boolean firstLoadDataSuc = false;
    //下拉刷新控制
    public ObservableBoolean isRefreshing = new ObservableBoolean(true);
    //加载更多时出错
    public ObservableBoolean isError = new ObservableBoolean(false);
    //加载更多时没有更多数据
    public ObservableBoolean noMore = new ObservableBoolean(false);
    //第一次加载中，加载出错，加载失败等状态
    public ObservableState state = new ObservableState();
    //决定显示RecyclerView 还是EmptyStateView（用于显示第一次加载数据时的UI）
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
        isError.set(false);
        boolean noData = (null == datas);
        if (datas instanceof List) {
            noData = (((List) datas).size() == 0);
        }
        if (!firstLoadDataSuc) {
            if (noData) setState(EmptyStateView.EmptyState.nodata);
            else {
                setState(EmptyStateView.EmptyState.hide);
                firstLoadDataSuc = true;//第一次加载数据成功
            }
        }else {
            noMore.set(noData);
        }
        refreshComplete();
    }

    public void setLayoutManager(RecyclerView.LayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    public RecyclerView.LayoutManager getLayoutManager() {
        return layoutManager;
    }

}
