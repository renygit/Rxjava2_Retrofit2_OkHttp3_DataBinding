package com.dsc.databindingdemo.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dsc.databindingdemo.R;

import cn.bingoogolapple.androidcommon.adapter.BGARecyclerViewAdapter;
import cn.bingoogolapple.androidcommon.adapter.BGAViewHolderHelper;

/**
 * Created by reny on 2017/3/6.
 */

public class SimpleSheetAdapter extends BGARecyclerViewAdapter<String> {

    public SimpleSheetAdapter(RecyclerView recyclerView) {
        super(recyclerView, R.layout.view_simple_sheet);
    }

    @Override
    protected void fillData(BGAViewHolderHelper helper, int position, String s) {
        helper.setText(R.id.tv_txt, s);
        helper.getView(R.id.widLine).setVisibility(position == getItemCount()-1 ? View.VISIBLE : View.GONE);
    }
}
