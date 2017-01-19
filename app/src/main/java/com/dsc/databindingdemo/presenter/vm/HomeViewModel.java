package com.dsc.databindingdemo.presenter.vm;

import android.databinding.ObservableBoolean;

import com.dsc.databindingdemo.R;
import com.dsc.databindingdemo.core.BaseViewModel;
import com.dsc.databindingdemo.databinding.ItemMainBinding;
import com.dsc.databindingdemo.model.GankData;

import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGABindingRecyclerViewAdapter;

/**
 * Created by reny on 2017/1/4.
 */

public class HomeViewModel extends BaseViewModel {

    public BGABindingRecyclerViewAdapter<GankData.ResultsBean, ItemMainBinding> adapter = new BGABindingRecyclerViewAdapter<>(R.layout.item_main);

    public ObservableBoolean isRefreshing = new ObservableBoolean(true);

    public void finishFreshAndLoad() {
        isRefreshing.set(true);
        isRefreshing.set(false);
    }


    public void setData(boolean isRefresh,List<GankData.ResultsBean> data) {
        if(isRefresh)adapter.setData(data);
        else adapter.addMoreData(data);
        finishFreshAndLoad();
        //binding.springView.onFinishFreshAndLoad();
    }
}
