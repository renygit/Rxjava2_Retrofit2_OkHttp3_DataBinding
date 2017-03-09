package com.dsc.databindingdemo.ui;

import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.dsc.databindingdemo.R;
import com.dsc.databindingdemo.databinding.FragmentABinding;
import com.dsc.databindingdemo.presenter.FAPresenter;
import com.dsc.databindingdemo.presenter.vm.FAViewModel;
import com.reny.mvpvmlib.BaseFragment;

/**
 * Created by reny on 2017/1/9.
 */

public class FragmentA extends BaseFragment<FragmentABinding, FAViewModel, FAPresenter> {

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
        //因为懒加载，无法在Presenter中初始化LayoutManager，所以binding后为RecyclerView设置LayoutManager
        viewModel.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
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
