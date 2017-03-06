package com.dsc.databindingdemo.presenter;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.dsc.databindingdemo.R;
import com.dsc.databindingdemo.core.ServiceHelper;
import com.dsc.databindingdemo.model.GankData;
import com.dsc.databindingdemo.model.custom.ImgsInfo;
import com.dsc.databindingdemo.presenter.vm.FAViewModel;
import com.dsc.databindingdemo.ui.ImagesActivity;
import com.dsc.databindingdemo.utils.ToastUtil;
import com.reny.mvpvmlib.BasePresenter;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGABindingViewHolder;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by reny on 2017/1/4.
 */

public class FAPresenter extends BasePresenter<FAViewModel> {

    private String category = "福利";
    private int count;
    int page = 1;
    private List<String> imgsList;
    private ImgsInfo imgsInfo;

    @Override
    public void onCreatePresenter() {
        viewModel.innerAdapter.setItemEventHandler(this);
        viewModel.layoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        count = context.getResources().getInteger(R.integer.load_count);
        loadData(true);
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
        if(null == imgsList)imgsList = new ArrayList<>();
        else imgsList.clear();

        List<GankData.ResultsBean> datas = viewModel.innerAdapter.getData();
        for (GankData.ResultsBean bean:datas) {
            imgsList.add(bean.getUrl());
        }
        if(null == imgsInfo)imgsInfo = new ImgsInfo();
        imgsInfo.setImgsList(imgsList);
        int curPos = holder.getAdapterPositionWrapper() - 1;
        imgsInfo.setCurPos(curPos < 0 ? 0 : curPos);

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
