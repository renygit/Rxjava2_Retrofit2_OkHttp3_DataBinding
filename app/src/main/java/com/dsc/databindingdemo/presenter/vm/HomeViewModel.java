package com.dsc.databindingdemo.presenter.vm;

import com.dsc.databindingdemo.R;
import com.dsc.databindingdemo.core.BaseViewModel;
import com.dsc.databindingdemo.databinding.ItemMainBinding;
import com.dsc.databindingdemo.model.GankData;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGABindingRecyclerViewAdapter;

/**
 * Created by reny on 2017/1/4.
 */

public class HomeViewModel extends BaseViewModel {

    public BGABindingRecyclerViewAdapter<GankData.ResultsBean, ItemMainBinding> innerAdapter = new BGABindingRecyclerViewAdapter<>(R.layout.item_main);
    public LRecyclerViewAdapter adapter = new LRecyclerViewAdapter(innerAdapter);
    private List<GankData.ResultsBean> datas;

    public void setData(boolean isRefresh, GankData data) {
        if(null == datas)datas = new ArrayList<>();
        if(isRefresh)datas.clear();
        datas.addAll(data.getResults());
        if(isRefresh)innerAdapter.setData(datas);
        adapter.notifyDataSetChanged();
        refreshComplete();
    }

}
