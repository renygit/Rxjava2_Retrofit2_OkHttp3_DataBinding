package com.dsc.databindingdemo.presenter;

import com.dsc.databindingdemo.presenter.vm.FCViewModel;
import com.reny.mvpvmlib.BasePresenter;
import com.zhy.changeskin.SkinManager;

/**
 * Created by reny on 2017/1/4.
 */

public class FCPresenter extends BasePresenter<FCViewModel> {

    @Override
    public void onCreatePresenter() {
    }

    public void switchBtnCheckChange(boolean isChecked){
        viewModel.isDarkTheme = isChecked;

        SkinManager.getInstance().changeSkin(isChecked ? "night":"");
    }

}
