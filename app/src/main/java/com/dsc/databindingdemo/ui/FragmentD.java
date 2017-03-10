package com.dsc.databindingdemo.ui;

import android.os.Bundle;
import android.widget.CompoundButton;

import com.dsc.databindingdemo.R;
import com.dsc.databindingdemo.databinding.FragmentDBinding;
import com.dsc.databindingdemo.presenter.FDPresenter;
import com.dsc.databindingdemo.presenter.vm.FDViewModel;
import com.reny.mvpvmlib.BaseFragment;

/**
 * Created by reny on 2017/1/9.
 */

public class FragmentD extends BaseFragment<FragmentDBinding, FDViewModel, FDPresenter> {

    /***
     * 和Activity中的init()有所区别
     * 这里只做Fragment尚未出现时的操作
     * 可以延迟初始化的工作放在onCreateViewLazy方法中，实现懒加载
     * binding必须写在Init中
     * @param savedInstanceState
     */
    @Override
    protected void init(Bundle savedInstanceState) {
        binding.setPresenter(presenter);
        binding.setViewModel(viewModel);
    }

    @Override
    protected void onCreateViewLazy(Bundle savedInstanceState) {
        //未保存switch状态
        binding.switchBtn.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                presenter.switchBtnCheckChange(isChecked);
            }
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_d;
    }

    @Override
    protected Class<FDViewModel> getViewModelClass() {
        return FDViewModel.class;
    }

    @Override
    protected Class<FDPresenter> getPresenterClass() {
        return FDPresenter.class;
    }
}
