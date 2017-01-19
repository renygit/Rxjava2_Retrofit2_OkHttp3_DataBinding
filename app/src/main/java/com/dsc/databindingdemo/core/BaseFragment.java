package com.dsc.databindingdemo.core;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;

/**
 * Created by reny on 2017/1/6.
 */

public abstract class BaseFragment<DB extends ViewDataBinding, VM extends BaseViewModel, P extends BasePresenter<VM>> extends Fragment{

    private boolean isInit = false;
    private boolean isStart = false;
    private Bundle savedInstanceState;
    public static final String INTENT_BOOLEAN_LAZYLOAD = "intent_boolean_lazyLoad";
    private boolean isLazyLoad = true;
    private View mRoot;

    private int isVisibleToUserState = VISIBLE_STATE_NOTSET;
    //未设置值
    private static final int VISIBLE_STATE_NOTSET = -1;
    //可见
    private static final int VISIBLE_STATE_VISIABLE = 1;
    //不可见
    private static final int VISIBLE_STATE_GONE = 0;

    protected DB binding;
    protected P presenter;
    protected VM viewModel;

    public BaseFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.savedInstanceState = savedInstanceState;
        binding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        mRoot = binding.getRoot();

        Bundle bundle = getArguments();
        if (bundle != null) {
            isLazyLoad = bundle.getBoolean(INTENT_BOOLEAN_LAZYLOAD, isLazyLoad);
        }
        
        if(null == presenter){
            try {
                if(null == getPresenterClass()) presenter = null;
                else presenter = getPresenterClass().newInstance();
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        if(null == viewModel){
            try {
                if(null == getViewModelClass()) viewModel = null;
                else viewModel = getViewModelClass().newInstance();
            } catch (java.lang.InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        if(null != presenter && null != viewModel){
            presenter.setViewModel(viewModel);
        }

        if (null != presenter) {
            presenter.setContext(getContext());
            presenter.setActivity(getActivity());
        }
        //为什么不直接getUserVisibleHint();而是通过自己存isVisibleToUserState变量判断
        //因为v4的25的版本 已经调用 setUserVisibleHint(true)，结果到这里getUserVisibleHint是false
        // （ps:看了FragmentManager源码Fragment被重新创建有直接赋值isVisibleToUser不知道是不是那里和之前v4有改动的地方）
        //所以我默认VISIBLE_STATE_NOTSET，之前没有调用setUserVisibleHint方法，就用系统的getUserVisibleHint，否则就用setUserVisibleHint后保存的值
        //总之就是调用了setUserVisibleHint 就使用setUserVisibleHint的值
        boolean isVisibleToUser;
        if (isVisibleToUserState == VISIBLE_STATE_NOTSET) {
            isVisibleToUser = getUserVisibleHint();
        } else {
            isVisibleToUser = isVisibleToUserState == VISIBLE_STATE_VISIABLE;
        }

        if (isLazyLoad) {
            if (isVisibleToUser) {
                isInit = true;
                onCreateViewLazy(savedInstanceState);
                lazyLoad();
            }
        } else {
            isInit = true;
            onCreateViewLazy(savedInstanceState);
            lazyLoad();
        }

        if(null == mRoot)
            return super.onCreateView(inflater, container, savedInstanceState);
        return mRoot;
    }


    private void lazyLoad(){
        if (null != presenter) {
            presenter.onCreate();//onCreate属于懒加载
        }
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isVisibleToUserState = isVisibleToUser ? VISIBLE_STATE_VISIABLE : VISIBLE_STATE_GONE;
        if (isVisibleToUser && !isInit && mRoot != null) {
            isInit = true;
            onCreateViewLazy(savedInstanceState);
            onResumeLazy();
            lazyLoad();
        }
        if (isInit && mRoot != null) {
            if (isVisibleToUser) {
                isStart = true;
                onStartLazy();
            } else {
                isStart = false;
                onStopLazy();
            }
        }
    }


    @Deprecated
    @Override
    public final void onStart() {
        super.onStart();
        if (isInit && !isStart && getUserVisibleHint()) {
            isStart = true;
            onStartLazy();
        }
    }

    @Deprecated
    @Override
    public final void onStop() {
        super.onStop();
        if (isInit && isStart && getUserVisibleHint()) {
            isStart = false;
            onStopLazy();
        }
    }

    @Override
    @Deprecated
    public final void onResume() {
        super.onResume();
        if (isInit) {
            onResumeLazy();
        }
    }

    @Override
    @Deprecated
    public final void onPause() {
        super.onPause();
        if (isInit) {
            onPauseLazy();
        }
    }

    @Override
    @Deprecated
    public final void onDestroyView() {
        super.onDestroyView();
        mRoot = null;
        if (null != presenter) presenter.onDestroy();
        viewModel = null;
        presenter = null;
        if (isInit) {
            onDestroyViewLazy();
        }
        isInit = false;
    }


    // http://stackoverflow.com/questions/15207305/getting-the-error-java-lang-illegalstateexception-activity-has-been-destroyed
    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }


    protected void onResumeLazy(){}

    protected void onStartLazy(){}

    protected void onStopLazy(){}

    protected void onPauseLazy(){}

    protected void onDestroyViewLazy(){}
    
    

    protected abstract void onCreateViewLazy(Bundle savedInstanceState);

    protected abstract int getLayoutId();

    protected abstract Class<VM> getViewModelClass();

    protected abstract Class<P> getPresenterClass();

}