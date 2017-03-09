package com.dsc.databindingdemo.presenter;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;

import com.dsc.databindingdemo.R;
import com.dsc.databindingdemo.core.ServiceHelper;
import com.dsc.databindingdemo.model.HotMovieData;
import com.dsc.databindingdemo.model.event.RvScrollEvent;
import com.dsc.databindingdemo.presenter.vm.FBViewModel;
import com.dsc.databindingdemo.ui.MainActivity;
import com.dsc.databindingdemo.ui.WebActivity;
import com.dsc.databindingdemo.utils.ToastUtil;
import com.michaelflisar.rxbus2.RxBusBuilder;
import com.reny.mvpvmlib.BasePresenter;

import cn.bingoogolapple.androidcommon.adapter.BGABindingViewHolder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by reny on 2017/1/4.
 */

public class FBPresenter extends BasePresenter<FBViewModel> {

    @Override
    public void onCreatePresenter() {
        viewModel.innerAdapter.setItemEventHandler(this);
        loadData(true);

        bindEvent();//绑定监听事件
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
                        if(event.getType().equals(MainActivity.FBScrollType)) {
                            //viewModel.getLayoutManager().scrollToPosition(event.getPos());
                            ((LinearLayoutManager) (viewModel.getLayoutManager())).scrollToPositionWithOffset(event.getPos(), 0);
                        }
                    }
                }));
    }

    @Override
    public void loadData(final boolean isRefresh) {
        addDisposable(ServiceHelper.getDoubanAS().getHotMovie()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<HotMovieData>() {
                    @Override
                    public void onNext(HotMovieData value) {
                        //LogUtils.json(value);
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
    public void onClickItem(BGABindingViewHolder holder, HotMovieData.SubjectsBean model) {
        Intent intent = new Intent(context, WebActivity.class);
        intent.putExtra("url", model.getAlt());
        context.startActivity(intent);
    }


    public static String getDirectors(HotMovieData.SubjectsBean model){
        if(null == model)return null;
        String directors = "导演：";
        for (int i = 0; i < model.getDirectors().size(); i++) {
            directors += model.getDirectors().get(i).getName();
            if(i != model.getDirectors().size()-1)directors += "、";
        }
        return directors;
    }

    public static String getCasts(HotMovieData.SubjectsBean model){
        if(null == model)return null;
        String casts = "主演：";
        for (int i = 0; i < model.getCasts().size(); i++) {
            casts += model.getCasts().get(i).getName();
            if(i != model.getCasts().size()-1)casts += "/";
        }
        return casts;
    }

    public static String getGenres(HotMovieData.SubjectsBean model){
        if(null == model)return null;
        String genres = "类型：";
        for (int i = 0; i < model.getGenres().size(); i++) {
            genres += model.getGenres().get(i);
            if(i != model.getCasts().size()-1)genres += "/";
        }
        return genres;
    }

}