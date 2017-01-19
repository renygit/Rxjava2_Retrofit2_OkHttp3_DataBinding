package com.dsc.databindingdemo.ui;

import android.os.Bundle;

import com.dsc.databindingdemo.R;
import com.dsc.databindingdemo.core.BaseFragment;
import com.dsc.databindingdemo.core.BaseViewModel;
import com.dsc.databindingdemo.core.EmptyPresenter;
import com.dsc.databindingdemo.databinding.FragmentBBinding;

/**
 * Created by reny on 2017/1/9.
 */

public class FragmentB extends BaseFragment<FragmentBBinding, BaseViewModel, EmptyPresenter>{

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        //binding.setPresenter(presenter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_b;
    }

    @Override
    protected Class<BaseViewModel> getViewModelClass() {
        return null;
    }

    @Override
    protected Class<EmptyPresenter> getPresenterClass() {
        return null;
    }
}
