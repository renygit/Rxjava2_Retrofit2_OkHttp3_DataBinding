package com.dsc.databindingdemo.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.dsc.databindingdemo.R;
import com.dsc.databindingdemo.api.APIConfig;
import com.dsc.databindingdemo.core.MyBaseActivity;
import com.dsc.databindingdemo.databinding.ActivityMainBinding;
import com.dsc.databindingdemo.presenter.MainPresenter;
import com.dsc.databindingdemo.presenter.vm.MainViewModel;
import com.reny.mvpvmlib.BaseActivity;
import com.reny.mvpvmlib.utils.SwipeBackUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends MyBaseActivity<ActivityMainBinding, MainViewModel, MainPresenter> {

    @Override
    protected void init(Bundle savedInstanceState) {
        SwipeBackUtils.DisableSwipeActivity(this);

        binding.toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_about:
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(APIConfig.ABOUT_URL));
                        startActivity(intent);
                        break;
                }
                return true;
            }
        });

        List<Fragment> fragmentList = new ArrayList<>();
        fragmentList.add(new FragmentA());
        fragmentList.add(new FragmentB());
        fragmentList.add(new FragmentC());
        fragmentList.add(new FragmentD());
        binding.vp.setOffscreenPageLimit(fragmentList.size());
        //we need the savedInstanceState to retrieve the position
        binding.tabLayout.initialize(binding.vp, getSupportFragmentManager(), fragmentList, savedInstanceState);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected Class<MainPresenter> getPresenterClass() {
        return MainPresenter.class;
    }

    @Override
    protected Class<MainViewModel> getViewModelClass() {
        return MainViewModel.class;
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        binding.tabLayout.saveState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

}
