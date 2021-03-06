package com.dsc.databindingdemo.presenter;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.dsc.databindingdemo.R;
import com.dsc.databindingdemo.api.GankApiService;
import com.dsc.databindingdemo.core.ServiceHelper;
import com.dsc.databindingdemo.model.GankData;
import com.dsc.databindingdemo.model.custom.ImgsInfo;
import com.dsc.databindingdemo.model.event.RvScrollEvent;
import com.dsc.databindingdemo.presenter.vm.FAViewModel;
import com.dsc.databindingdemo.ui.ImagesActivity;
import com.dsc.databindingdemo.ui.MainActivity;
import com.dsc.databindingdemo.utils.ToastUtil;
import com.michaelflisar.rxbus2.RxBus;
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

public class FAPresenter extends BasePresenter<FAViewModel> {

    private String category = GankApiService.category_a;
    private int count;
    int page = 1;
    private ImgsInfo imgsInfo;

    @Override
    public void onCreatePresenter() {
        viewModel.innerAdapter.setItemEventHandler(this);
        count = context.getResources().getInteger(R.integer.load_count);//每页加载条数
        loadData(true);

        bindEvent();//绑定监听事件
    }

    private void bindEvent() {
        /***
         * 引用库地址 https://github.com/MFlisar/RxBus2
         * 发送事件 example
         * RxBus.get().send(new RvScrollEvent(0));
         * 此处不需要使用 RxDisposableManager 管理   自带的addDisposable已经管理
         * 这里监听了大图查看时滚动的位置，同时更新首页列表滚动的位置，剩余条数<=2条时 加载下一页
         */
        addDisposable(RxBusBuilder.create(RvScrollEvent.class)
                .subscribe(new Consumer<RvScrollEvent>() {
                    @Override
                    public void accept(RvScrollEvent event) {
                        //type过滤 只接受MainActivity、ImagesActivity发过来的消息
                        //ToastUtil.showShort(event.getType());
                        if(event.getType().equals(MainActivity.FAScrollType) || event.getType().equals(ImagesActivity.class.getSimpleName())) {
                            ((StaggeredGridLayoutManager) (viewModel.getLayoutManager())).scrollToPositionWithOffset(event.getPos(), 0);
                            viewModel.adapter.notifyDataSetChanged();//不调用会数据错乱

                            if (null != viewModel.imgsList && event.getPos() > viewModel.imgsList.size() - 4) {
                                loadData(false);
                            }
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

                        //如果是大图浏览时在加载数据 将数据用事件发送出去
                        if(!isRefresh) {
                            if (null == imgsInfo) imgsInfo = new ImgsInfo();
                            imgsInfo.setImgsList(viewModel.imgsList);
                            RxBus.get().send(imgsInfo);
                        }
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
        viewModel.updateImgsList();//更新viewModel.imgsList
        if(null == imgsInfo)imgsInfo = new ImgsInfo();
        imgsInfo.setImgsList(viewModel.imgsList);
        int curPos = holder.getAdapterPositionWrapper() - 1;
        imgsInfo.setCurPos(curPos < 0 ? 0 : curPos);
        imgsInfo.setListeningChanged(true);//设置开启监听

        View sdv = holder.getBinding().getRoot().findViewById(R.id.sdv);

        Intent intent = new Intent(context, ImagesActivity.class);
        intent.putExtra(ImgsInfo.KEY, imgsInfo);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            //转场动画似乎对 SimpleDraweeView 不起作用 因此没有写全transitionName
            context.startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(activity, sdv, "image").toBundle());
        }else {
            context.startActivity(intent);
        }
    }

}