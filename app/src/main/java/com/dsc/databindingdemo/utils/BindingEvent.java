package com.dsc.databindingdemo.utils;

import android.databinding.BindingAdapter;
import android.databinding.ObservableBoolean;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.dsc.databindingdemo.core.BasePresenter;
import com.dsc.databindingdemo.core.BaseViewModel;
import com.facebook.drawee.view.SimpleDraweeView;
import com.liaoinstan.springview.container.DefaultFooter;
import com.liaoinstan.springview.container.DefaultHeader;
import com.liaoinstan.springview.widget.SpringView;

/**
 * Created by reny on 2017/1/5.
 */

public class BindingEvent {

    @BindingAdapter("frescoImgUrl")
    public static void setFrescoImgUrl(final SimpleDraweeView sdv, String url){
        FrescoUtils.display(sdv, url);
    }

    @BindingAdapter("adapter")
    public static void setAdapter(final RecyclerView rv, RecyclerView.Adapter adapter){
        rv.setAdapter(adapter);
    }

    @BindingAdapter("layoutManager")
    public static void setLayoutManager(final RecyclerView rv, RecyclerView.LayoutManager layoutManager){
        if(null == layoutManager)layoutManager = new LinearLayoutManager(rv.getContext());
        rv.setLayoutManager(layoutManager);
    }

    @BindingAdapter("headerfooter")
    public static void setDefaultHeaderFooter(final SpringView sv, BaseViewModel vm){
        sv.setHeader(new DefaultHeader(sv.getContext()));
        sv.setFooter(new DefaultFooter(sv.getContext()));
    }

    @BindingAdapter("OnFreshListener")
    public static void OnFreshListener(final SpringView sv, final BasePresenter presenter){
        sv.setListener(new SpringView.OnFreshListener() {
            @Override
            public void onRefresh() {
                presenter.loadData(true);
            }
            @Override
            public void onLoadmore() {
                presenter.loadData(false);
            }
        });
    }

    @BindingAdapter("isRefreshing")
    public static void onFinishFreshAndLoad(final SpringView sv, boolean isRefreshing){
        if(!isRefreshing)sv.onFinishFreshAndLoad();
    }

}
