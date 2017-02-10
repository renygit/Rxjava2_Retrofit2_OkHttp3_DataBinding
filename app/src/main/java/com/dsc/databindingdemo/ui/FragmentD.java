package com.dsc.databindingdemo.ui;

import android.os.Bundle;

import com.dsc.databindingdemo.R;
import com.dsc.databindingdemo.databinding.FragmentBBinding;
import com.dsc.databindingdemo.databinding.FragmentDBinding;
import com.reny.mvpvmlib.BaseFragment;
import com.reny.mvpvmlib.BaseViewModel;
import com.reny.mvpvmlib.EmptyPresenter;

/**
 * Created by reny on 2017/1/9.
 */

public class FragmentD extends BaseFragment<FragmentDBinding, BaseViewModel, EmptyPresenter> {

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        //binding.setPresenter(presenter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_d;
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
