package com.dsc.databindingdemo.utils;

import android.databinding.BindingAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dsc.databindingdemo.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnNetWorkErrorListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.reny.mvpvmlib.BasePresenter;
import com.reny.mvpvmlib.model.ObservableState;
import com.reny.mvpvmlib.widget.EmptyStateView;

/**
 * Created by reny on 2017/1/5.
 */

public class BindingEvent {

    @BindingAdapter("frescoImgUrl")
    public static void setFrescoImgUrl(final SimpleDraweeView sdv, String url){
        FrescoUtils.display(sdv, url);
    }

    @BindingAdapter("adapter")
    public static void setAdapter(final LRecyclerView rv, LRecyclerViewAdapter adapter){
        rv.setAdapter(adapter);
    }

    @BindingAdapter("layoutManager")
    public static void setLayoutManager(final LRecyclerView rv, RecyclerView.LayoutManager layoutManager){
        if(null == layoutManager)layoutManager = new LinearLayoutManager(rv.getContext());
        rv.setLayoutManager(layoutManager);
    }

    @BindingAdapter("OnFreshListener")
    public static void OnFreshListener(final LRecyclerView rv, final BasePresenter presenter){
        rv.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader); //设置下拉刷新Progress的样式
        rv.setArrowImageView(R.mipmap.ic_refresh_arrow);  //设置下拉刷新箭头
        rv.setHeaderViewColor(R.color.refresh_tip_color, R.color.refresh_tip_color ,R.color.bg_color);
        rv.setFooterViewColor(R.color.refresh_tip_color, R.color.refresh_tip_color ,R.color.bg_color);
        //设置底部加载文字提示
        rv.setFooterViewHint("加载中","我是底线","网络不给力啊，点击重试");
        rv.setLoadingMoreProgressStyle(ProgressStyle.BallSpinFadeLoader);

        rv.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadData(true);
            }
        });
        rv.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                presenter.loadData(false);
            }
        });

    }

    @BindingAdapter("isRefreshing")
    public static void onFinishFreshAndLoad(final LRecyclerView rv, boolean isRefreshing){
        if(!isRefreshing)rv.refreshComplete();
    }

    @BindingAdapter({"isError", "presenter"})
    public static void setLoadError(LRecyclerView rv, boolean isError, final BasePresenter presenter) {
        if(isError) {
            rv.setOnNetWorkErrorListener(new OnNetWorkErrorListener() {
                @Override
                public void reload() {
                    presenter.loadData(false);
                }
            });
        }
    }

    @BindingAdapter("noMore")
    public static void setNoMore(final LRecyclerView rv, boolean noMore){
        rv.setNoMore(noMore);
    }

    @BindingAdapter("loadMoreEnabled")
    public static void setLoadMoreEnabled(final LRecyclerView rv, boolean enable){
        rv.setLoadMoreEnabled(enable);
    }


    @BindingAdapter("state")
    public static void setLoadState(final EmptyStateView esv, ObservableState state){
        esv.setState(state.getState());
    }

    @BindingAdapter("retry")
    public static void setRetry(final EmptyStateView esv, final BasePresenter presenter){
        esv.onRetry(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                esv.setState(EmptyStateView.EmptyState.loading);
                presenter.loadData(true);
            }
        });
    }

}
