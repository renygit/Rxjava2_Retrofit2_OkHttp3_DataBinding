package com.dsc.databindingdemo.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.dsc.databindingdemo.R;

/**
 * This class is from the v7 samples of the Android SDK. It's not by me!
 * 
 * https://gist.github.com/alexfu/0f464fc3742f134ccd1e
 * https://github.com/gabrielemariotti/RecyclerViewItemAnimators
 * 
 * See the license above for details.
 */
public class DividerItemDecoration extends RecyclerView.ItemDecoration {


	public static final int HORIZONTAL_LIST = LinearLayoutManager.HORIZONTAL;

	public static final int VERTICAL_LIST = LinearLayoutManager.VERTICAL;

	private Drawable mDivider;

	private int mOrientation;
	
	private int lineWidth = 8;


	//1px垂直高度  带颜色
	public static DividerItemDecoration get1pxDividerV(Context context){
		return new DividerItemDecoration(context, VERTICAL_LIST, R.drawable.divider, 1);
	}

	//默认垂直高度  透明
	public static DividerItemDecoration getDefaultDividerTransV(Context context){
		return new DividerItemDecoration(context, VERTICAL_LIST, R.drawable.divider_trans, dip2px(context, 8));
	}

	private DividerItemDecoration(Context context, int orientation, int drawableId, int lineWidth) {
		mDivider = context.getResources().getDrawable(drawableId);
		setOrientation(orientation);
		this.lineWidth = lineWidth;
	}

	private void setOrientation(int orientation) {
		if (orientation != HORIZONTAL_LIST && orientation != VERTICAL_LIST) {
			throw new IllegalArgumentException("invalid orientation");
		}
		mOrientation = orientation;
	}

	@Override
	public void onDraw(Canvas c, RecyclerView parent) {
		if (mOrientation == VERTICAL_LIST) {
			drawVertical(c, parent);
		} else {
			drawHorizontal(c, parent);
		}
	}

	public void drawVertical(Canvas c, RecyclerView parent) {
		final int left = parent.getPaddingLeft();
		final int right = parent.getWidth() - parent.getPaddingRight();

		final int childCount = parent.getChildCount();
		for (int i = 0; i < childCount; i++) {
			final View child = parent.getChildAt(i);
			//RecyclerView v = new RecyclerView(parent.getContext());
			final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
					.getLayoutParams();
			final int top = child.getBottom() + params.bottomMargin;
			final int bottom = top + lineWidth;
			mDivider.setBounds(left, top, right, bottom);
			mDivider.draw(c);
		}
	}

	public void drawHorizontal(Canvas c, RecyclerView parent) {
		final int top = parent.getPaddingTop();
		final int bottom = parent.getHeight() - parent.getPaddingBottom();

		final int childCount = parent.getChildCount();
		for (int i = 0; i < childCount; i++) {
			final View child = parent.getChildAt(i);
			final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child
					.getLayoutParams();
			final int left = child.getRight() + params.rightMargin;
			final int right = left + lineWidth;
			mDivider.setBounds(left, top, right, bottom);
			mDivider.draw(c);
		}
	}

	@Override
	public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
		if (mOrientation == VERTICAL_LIST) {
			outRect.set(0, 0, 0, lineWidth);
		} else {
			outRect.set(0, 0, lineWidth, 0);
		}
	}

	private static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}
}
