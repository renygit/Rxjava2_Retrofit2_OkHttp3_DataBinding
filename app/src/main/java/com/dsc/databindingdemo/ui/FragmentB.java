package com.dsc.databindingdemo.ui;

import android.os.Bundle;

import com.dsc.databindingdemo.R;
import com.dsc.databindingdemo.databinding.FragmentBBinding;
import com.dsc.databindingdemo.presenter.FBPresenter;
import com.dsc.databindingdemo.presenter.vm.FBViewModel;
import com.reny.mvpvmlib.BaseFragment;
import com.reny.mvpvmlib.BaseViewModel;
import com.reny.mvpvmlib.EmptyPresenter;

/**
 * Created by reny on 2017/1/9.
 */

public class FragmentB extends BaseFragment<FragmentBBinding, FBViewModel, FBPresenter> {

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        binding.setPresenter(presenter);
        binding.setViewModel(viewModel);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_b;
    }

    @Override
    protected Class<FBViewModel> getViewModelClass() {
        return FBViewModel.class;
    }

    @Override
    protected Class<FBPresenter> getPresenterClass() {
        return FBPresenter.class;
    }
}
