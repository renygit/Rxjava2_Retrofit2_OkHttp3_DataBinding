package com.dsc.databindingdemo.presenter;

import android.content.Intent;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.dsc.databindingdemo.R;
import com.dsc.databindingdemo.api.GankApiService;
import com.dsc.databindingdemo.core.ServiceHelper;
import com.dsc.databindingdemo.model.GankData;
import com.dsc.databindingdemo.model.event.RvScrollEvent;
import com.dsc.databindingdemo.presenter.vm.FCViewModel;
import com.dsc.databindingdemo.ui.MainActivity;
import com.dsc.databindingdemo.ui.WebActivity;
import com.dsc.databindingdemo.utils.ToastUtil;
import com.michaelflisar.rxbus2.RxBusBuilder;
import com.reny.mvpvmlib.BasePresenter;
import com.reny.mvpvmlib.http.converter.ResultErrorException;

import cn.bingoogolapple.androidcommon.adapter.BGABindingViewHolder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by reny on 2017/1/4.
 */

public class FCPresenter extends BasePresenter<FCViewModel> {

    private String category = GankApiService.category_b;
    private int count;
    int page = 1;

    @Override
    public void onCreatePresenter() {
        viewModel.innerAdapter.setItemEventHandler(this);
        count = context.getResources().getInteger(R.integer.load_count);//每页加载条数
        loadData(true);

        bindEvent();
    }

    private void bindEvent() {
        /***
         * 引用库地址 https://github.com/MFlisar/RxBus2
         * 发送事件 example
         * RxBus.get().send(new RvScrollEvent(0));
         * 此处不需要使用 RxDisposableManager 管理   自带的addDisposable已经管理
         * 这里监听了点击ToolBar列表回到顶部
         */
        addDisposable(RxBusBuilder.create(RvScrollEvent.class)
                .subscribe(new Consumer<RvScrollEvent>() {
                    @Override
                    public void accept(RvScrollEvent event) {
                        //type过滤 只接受MainActivity发过来的消息
                        //ToastUtil.showShort(event.getType());
                        if(event.getType().equals(MainActivity.FCScrollType)) {
                            //viewModel.getLayoutManager().scrollToPosition(event.getPos());
                            ((StaggeredGridLayoutManager) (viewModel.getLayoutManager())).scrollToPositionWithOffset(event.getPos(), 0);
                        }
                    }
                }));
    }

    @Override
    public void loadData(final boolean isRefresh) {
        if(isRefresh) page = 1;

        addDisposable(ServiceHelper.getGankAS().getGankIoData(category, count, page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<GankData>() {
                    @Override
                    public void onNext(GankData value) {
                        if(value.isError())throw new ResultErrorException(-1, "数据错误");
                        page++;
                        viewModel.setData(isRefresh, value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if (isRefresh && viewModel.firstLoadDataSuc){
                            ToastUtil.showShort(R.string.refresh_error);
                        }
                        onFailure(e);
                    }

                    @Override
                    public void onComplete() {}
                })
        );
    }

    //列表Item点击 与xml绑定
    public void onClickItem(BGABindingViewHolder holder, GankData.ResultsBean model) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("url", model.getUrl());
        context.startActivity(intent);
    }

}
