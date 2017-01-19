package com.dsc.databindingdemo.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dsc.databindingdemo.R;
import com.dsc.databindingdemo.core.BaseActivity;
import com.dsc.databindingdemo.databinding.ActivityMainBinding;
import com.dsc.databindingdemo.presenter.MainPresenter;
import com.dsc.databindingdemo.presenter.vm.MainViewModel;
import com.dsc.databindingdemo.utils.SwipeBackHelperUtils;
import com.shizhefei.view.indicator.IndicatorViewPager;
import com.shizhefei.view.indicator.transition.OnTransitionTextListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity<ActivityMainBinding, MainViewModel, MainPresenter>{

    private IndicatorViewPager indicatorViewPager;

    @Override
    protected void init(Bundle savedInstanceState) {
        SwipeBackHelperUtils.DisableSwipeActivity(this);

        binding.tabIndicator.setOnTransitionListener(new OnTransitionTextListener().setColor(Color.RED, Color.GRAY));
        indicatorViewPager = new IndicatorViewPager(binding.tabIndicator, binding.vp);
        indicatorViewPager.setAdapter(new MyAdapter(getSupportFragmentManager()));
        // 禁止viewpager的滑动事件
        binding.vp.setCanScroll(false);
        // 设置viewpager保留界面不重新加载的页面数量
        binding.vp.setOffscreenPageLimit(3);
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


    private class MyAdapter extends IndicatorViewPager.IndicatorFragmentPagerAdapter {
        private String[] tabNames = {"主页", "消息", "我"};
        private int[] tabIcons = {R.drawable.maintab_1_selector, R.drawable.maintab_2_selector, R.drawable.maintab_3_selector};
        private LayoutInflater inflater;
        private List<Fragment> fragmentList;

        public MyAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
            inflater = LayoutInflater.from(getApplicationContext());

            fragmentList = new ArrayList<>();
            fragmentList.add(new FragmentA());
            fragmentList.add(new FragmentB());
            fragmentList.add(new FragmentC());
        }

        @Override
        public int getCount() {
            return tabNames.length;
        }

        @Override
        public View getViewForTab(int position, View convertView, ViewGroup container) {
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.tab_main, container, false);
            }
            TextView textView = (TextView) convertView;
            textView.setText(tabNames[position]);
            textView.setCompoundDrawablesWithIntrinsicBounds(0, tabIcons[position], 0, 0);
            return textView;
        }

        @Override
        public Fragment getFragmentForPage(int position) {
            return fragmentList.get(position);
        }
    }
}
