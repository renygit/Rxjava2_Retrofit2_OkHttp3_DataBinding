package com.reny.mvpvmlib.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.reny.mvpvmlib.R;
import com.wang.avi.AVLoadingIndicatorView;

/**
 * Created by reny on 2016/7/13.
 */

public class EmptyStateView extends RelativeLayout {

    Context context;
    RelativeLayout rl_root_empty;
    LinearLayout ll_loading;
    LinearLayout ll_error;
    LinearLayout ll_nodata;
    AVLoadingIndicatorView aiv;

    public enum EmptyState {
        loading, error, nodata, hide
    }

    public EmptyStateView(Context context) {
        super(context);
        init(context, null);
    }
    
    public EmptyStateView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public void init(Context context, AttributeSet attrs) {
        this.context = context;
        View rootView = inflate(context, R.layout.view_empty_state, this);
        rootView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));

        rl_root_empty = (RelativeLayout) rootView.findViewById(R.id.rl_root_empty);
        ll_loading = (LinearLayout) rootView.findViewById(R.id.ll_loading);
        ll_error = (LinearLayout) rootView.findViewById(R.id.ll_error);
        ll_nodata = (LinearLayout) rootView.findViewById(R.id.ll_nodata);
        aiv = (AVLoadingIndicatorView) rootView.findViewById(R.id.aiv);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AVLAttr, 0, 0);
        String indicatorName = a.getString(R.styleable.AVLAttr_indicatorName);
        int indicatorColor = a.getColor(R.styleable.AVLAttr_indicatorColor, Color.BLUE);
        a.recycle();

        aiv.setIndicator(indicatorName);
        aiv.setIndicatorColor(indicatorColor);
    }

    public void setState(EmptyState state) {
        rl_root_empty.setVisibility(state == EmptyState.hide ? View.GONE : View.VISIBLE);
        if (state == EmptyState.loading) {
            ll_loading.setVisibility(View.VISIBLE);
            ll_error.setVisibility(View.GONE);
            ll_nodata.setVisibility(View.GONE);
        } else if (state == EmptyState.error) {
            ll_loading.setVisibility(View.GONE);
            ll_error.setVisibility(View.VISIBLE);
            ll_nodata.setVisibility(View.GONE);
        } else if (state == EmptyState.nodata) {
            ll_loading.setVisibility(View.GONE);
            ll_error.setVisibility(View.GONE);
            ll_nodata.setVisibility(View.VISIBLE);
        }
    }

    public void onRetry(OnClickListener listener) {
        ll_error.setOnClickListener(listener);
    }

}
