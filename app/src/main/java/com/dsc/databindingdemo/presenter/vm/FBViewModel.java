package com.dsc.databindingdemo.presenter.vm;

import android.databinding.ObservableBoolean;

import com.dsc.databindingdemo.R;
import com.dsc.databindingdemo.databinding.ItemFragmentBBinding;
import com.dsc.databindingdemo.model.HotMovieData;
import com.github.jdsjlzx.recyclerview.LRecyclerViewAdapter;
import com.reny.mvpvmlib.BaseViewModel;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.androidcommon.adapter.BGABindingRecyclerViewAdapter;

/**
 * Created by reny on 2017/1/4.
 */

public class FBViewModel extends BaseViewModel {

    public ObservableBoolean loadMoreEnable = new ObservableBoolean(false);
    public BGABindingRecyclerViewAdapter<HotMovieData.SubjectsBean, ItemFragmentBBinding> innerAdapter = new BGABindingRecyclerViewAdapter<>(R.layout.item_fragment_b);
    public LRecyclerViewAdapter adapter = new LRecyclerViewAdapter(innerAdapter);
    private List<HotMovieData.SubjectsBean> datas;

    public void setData(boolean isRefresh, HotMovieData data) {
        setDataState(data.getSubjects());
        if (null == datas) datas = new ArrayList<>();
        if (isRefresh) datas.clear();
        datas.addAll(data.getSubjects());
        if (isRefresh) innerAdapter.setData(datas);
        adapter.notifyDataSetChanged();
    }

}
