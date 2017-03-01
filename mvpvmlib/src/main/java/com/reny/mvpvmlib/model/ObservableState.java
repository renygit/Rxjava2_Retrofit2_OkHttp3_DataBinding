package com.reny.mvpvmlib.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.reny.mvpvmlib.widget.EmptyStateView;

/**
 * Created by reny on 2017/3/1.
 */

public class ObservableState extends BaseObservable {

    //对EmptyState包装一次 实现双向绑定
    private EmptyStateView.EmptyState state;

    @Bindable
    public EmptyStateView.EmptyState getState() {
        return state;
    }

    public void setState(EmptyStateView.EmptyState state) {
        this.state = state;
        notifyChange();
    }
}
