package com.dsc.databindingdemo.presenter;

import com.dsc.databindingdemo.core.ServiceHelper;
import com.dsc.databindingdemo.model.GankData;
import com.dsc.databindingdemo.presenter.vm.FCViewModel;
import com.reny.mvpvmlib.BasePresenter;
import com.reny.mvpvmlib.utils.LogUtils;
import com.zhy.changeskin.SkinManager;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by reny on 2017/1/4.
 */

public class FCPresenter extends BasePresenter<FCViewModel> {

    private String category = "福利";
    private int count = 20;
    private int page = 1;

    @Override
    public void onCreatePresenter() {
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
                        LogUtils.json(value);
                        page++;
                        //viewModel.setData(isRefresh, value);
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

    public void switchBtnCheckChange(boolean isChecked){
        viewModel.isDarkTheme = isChecked;

        SkinManager.getInstance().changeSkin(isChecked ? "night":"");
    }

}
