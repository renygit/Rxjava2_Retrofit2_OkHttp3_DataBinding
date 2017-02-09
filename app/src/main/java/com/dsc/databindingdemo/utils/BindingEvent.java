package com.dsc.databindingdemo.utils;

import android.databinding.BindingAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dsc.databindingdemo.R;
import com.facebook.drawee.view.SimpleDraweeView;
import com.github.jdsjlzx.interfaces.OnLoadMoreListener;
import com.github.jdsjlzx.interfaces.OnRefreshListener;
import com.github.jdsjlzx.recyclerview.LRecyclerView;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.github.jdsjlzx.recyclerview.ProgressStyle;
import com.reny.mvpvmlib.BasePresenter;

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
        rv.setArrowImageView(R.drawable.ic_pulltorefresh_arrow);  //设置下拉刷新箭头
        rv.setHeaderViewColor(R.color.colorAccent, R.color.text_color ,android.R.color.white);
        rv.setFooterViewColor(R.color.colorAccent, R.color.text_color ,android.R.color.white);
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

}
