package com.dsc.databindingdemo.ui;

import android.os.Bundle;

import com.dsc.databindingdemo.R;
import com.dsc.databindingdemo.databinding.FragmentABinding;
import com.dsc.databindingdemo.presenter.FAPresenter;
import com.dsc.databindingdemo.presenter.vm.FAViewModel;
import com.reny.mvpvmlib.BaseFragment;

/**
 * Created by reny on 2017/1/9.
 */

public class FragmentA extends BaseFragment<FragmentABinding, FAViewModel, FAPresenter> {

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        binding.setPresenter(presenter);
        binding.setViewModel(viewModel);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_a;
    }

    @Override
    protected Class<FAViewModel> getViewModelClass() {
        return FAViewModel.class;
    }

    @Override
    protected Class<FAPresenter> getPresenterClass() {
        return FAPresenter.class;
    }

}
