package com.dsc.databindingdemo.presenter;

import android.content.Intent;

import com.dsc.databindingdemo.api.BaiduApiService;
import com.dsc.databindingdemo.core.BasePresenter;
import com.dsc.databindingdemo.core.http.ServiceFactory;
import com.dsc.databindingdemo.model.GankData;
import com.dsc.databindingdemo.presenter.vm.HomeViewModel;
import com.dsc.databindingdemo.ui.WebActivity;

import cn.bingoogolapple.androidcommon.adapter.BGABindingViewHolder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by reny on 2017/1/4.
 */

public class HomePresenter extends BasePresenter<HomeViewModel> {

    int page = 1;

    @Override
    public void onCreatePresenter() {
        viewModel.adapter.setItemEventHandler(this);
        loadData(true);
    }

    @Override
    public void loadData(final boolean isRefresh) {
        page = isRefresh ? 1 : ++page;

        BaiduApiService service = (BaiduApiService) ServiceFactory.getInstance().getService(BaiduApiService.class);

        addDisposable(service.getGankData(page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<GankData>() {
                    @Override
                    public void onNext(GankData value) {
                        //LogUtils.json(value);
                        viewModel.setData(isRefresh, value.getResults());
                    }

                    @Override
                    public void onError(Throwable e) {
                        onFailure(e);
                        viewModel.finishFreshAndLoad();
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
