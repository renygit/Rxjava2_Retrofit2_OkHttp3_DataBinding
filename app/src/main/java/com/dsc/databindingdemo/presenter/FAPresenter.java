package com.dsc.databindingdemo.presenter;

import android.content.Intent;

import com.dsc.databindingdemo.core.ServiceHelper;
import com.dsc.databindingdemo.model.GankData;
import com.dsc.databindingdemo.presenter.vm.FAViewModel;
import com.dsc.databindingdemo.ui.WebActivity;
import com.reny.mvpvmlib.BasePresenter;
import com.reny.mvpvmlib.utils.LogUtils;
import com.reny.mvpvmlib.widget.EmptyStateView;

import cn.bingoogolapple.androidcommon.adapter.BGABindingViewHolder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by reny on 2017/1/4.
 */

public class FAPresenter extends BasePresenter<FAViewModel> {

    int page = 1;

    @Override
    public void onCreatePresenter() {
        viewModel.innerAdapter.setItemEventHandler(this);
        loadData(true);
    }

    @Override
    public void loadData(final boolean isRefresh) {
        if(isRefresh) page = 1;

        //LogUtils.e("loadata......");
        addDisposable(ServiceHelper.getGankAS().getGankData(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<GankData>() {
                    @Override
                    public void onNext(GankData value) {
                        //LogUtils.json(value);
                        page++;
                        viewModel.setData(isRefresh, value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        onFailure(e);
                    }

                    @Override
                    public void onComplete() {}
                })
        );
    }


    public void onClickItem(BGABindingViewHolder holder, GankData.ResultsBean model) {
        //LogUtils.e("onClickItem.."+model.getUrl());
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("url", model.getUrl());
        context.startActivity(intent);
    }

}
