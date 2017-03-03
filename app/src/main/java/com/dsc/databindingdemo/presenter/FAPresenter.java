package com.dsc.databindingdemo.presenter;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.dsc.databindingdemo.R;
import com.dsc.databindingdemo.core.ServiceHelper;
import com.dsc.databindingdemo.model.GankData;
import com.dsc.databindingdemo.presenter.vm.FAViewModel;
import com.dsc.databindingdemo.ui.ImagesActivity;
import com.reny.mvpvmlib.BasePresenter;

import cn.bingoogolapple.androidcommon.adapter.BGABindingViewHolder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by reny on 2017/1/4.
 */

public class FAPresenter extends BasePresenter<FAViewModel> {

    private String category = "福利";
    private int count = 10;
    int page = 1;

    @Override
    public void onCreatePresenter() {
        viewModel.innerAdapter.setItemEventHandler(this);
        viewModel.layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        loadData(true);
    }

    @Override
    public void loadData(final boolean isRefresh) {
        if(isRefresh) page = 1;

        //LogUtils.e("loadata......"+type.toString());
        addDisposable(ServiceHelper.getGankAS().getGankIoData(category, count, page)
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
                        //System.out.println(e.getMessage());
                        onFailure(e);
                    }

                    @Override
                    public void onComplete() {}
                })
        );
    }


    public void onClickItem(BGABindingViewHolder holder, GankData.ResultsBean model) {
        //LogUtils.e("onClickItem.."+model.getUrl());
        View sdv = holder.getBinding().getRoot().findViewById(R.id.sdv);

        Intent intent = new Intent(context, ImagesActivity.class);
        //holder.getAdapterPosition();
        intent.putExtra("url", model.getUrl());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            context.startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(activity, sdv, "image").toBundle());
        }else {
            context.startActivity(intent);
        }
    }

}
