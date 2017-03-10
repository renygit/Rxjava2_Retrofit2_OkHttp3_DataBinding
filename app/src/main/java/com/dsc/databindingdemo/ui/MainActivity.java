package com.dsc.databindingdemo.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.dsc.databindingdemo.R;
import com.dsc.databindingdemo.api.APIConfig;
import com.dsc.databindingdemo.core.MyBaseActivity;
import com.dsc.databindingdemo.databinding.ActivityMainBinding;
import com.dsc.databindingdemo.model.event.RvScrollEvent;
import com.dsc.databindingdemo.presenter.MainPresenter;
import com.dsc.databindingdemo.presenter.vm.MainViewModel;
import com.michaelflisar.rxbus2.RxBus;
import com.reny.mvpvmlib.utils.SwipeBackUtils;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends MyBaseActivity<ActivityMainBinding, MainViewModel, MainPresenter> {

    private String[] tabTitles;
    public static final String FAScrollType = FragmentA.class.getSimpleName();
    public static final String FBScrollType = FragmentB.class.getSimpleName();
    public static final String FCScrollType = FragmentC.class.getSimpleName();

    @Override
    protected void init(Bundle savedInstanceState) {
        SwipeBackUtils.DisableSwipeActivity(this);

        tabTitles = getResources().getStringArray(R.array.tabTitles);

        binding.toolbar.setTitle(tabTitles[0]);
        setSupportActionBar(binding.toolbar);
        binding.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_about:
                        Intent intent = new Intent(MainActivity.this, WebActivity.class);
                        intent.putExtra("url", APIConfig.ABOUT_URL);
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

        //演示“发送事件” （功能可以用FragmentA的实例调用内部方法实现滑动到顶部，eg: fragmentA.scrollToTop(); 在FragmentA中实现滚动方法即可）
        binding.toolbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //list回到顶部
                switch (binding.vp.getCurrentItem()){
                    case 0://点击toolbar时当前页面在第1页时
                        RxBus.get().send(new RvScrollEvent(FAScrollType, 0));
                        break;
                    case 1://点击toolbar时当前页面在第2页时
                        RxBus.get().send(new RvScrollEvent(FBScrollType, 0));
                        break;
                    case 2://点击toolbar时当前页面在第3页时
                        RxBus.get().send(new RvScrollEvent(FCScrollType, 0));
                        break;
                }
            }
        });

        binding.vp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}
            @Override
            public void onPageSelected(int position) {
                binding.toolbar.setTitle(tabTitles[position]);
            }
            @Override
            public void onPageScrollStateChanged(int state) {}
        });
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
