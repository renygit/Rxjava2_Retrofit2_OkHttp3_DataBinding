package com.dsc.databindingdemo.ui;

import android.os.Bundle;
import android.widget.CompoundButton;

import com.dsc.databindingdemo.R;
import com.dsc.databindingdemo.databinding.FragmentCBinding;
import com.dsc.databindingdemo.presenter.FCPresenter;
import com.dsc.databindingdemo.presenter.vm.FCViewModel;
import com.reny.mvpvmlib.BaseFragment;

/**
 * Created by reny on 2017/1/9.
 */

public class FragmentC extends BaseFragment<FragmentCBinding, FCViewModel, FCPresenter> {

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        binding.setPresenter(presenter);
        binding.setViewModel(viewModel);

        binding.switchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                presenter.switchBtnCheckChange(isChecked);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_c;
    }

    @Override
    protected Class<FCViewModel> getViewModelClass() {
        return FCViewModel.class;
    }

    @Override
    protected Class<FCPresenter> getPresenterClass() {
        return FCPresenter.class;
    }
}
