/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// based on FastScroller.java for ListView in android code base (sv) 

package com.dsc.databindingdemo.widget.webview;


import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;

import com.dsc.databindingdemo.R;

/**
 * Helper class for AbsListView to draw and control the Fast Scroll thumb
 */
class WebViewFastScroller {

    // Minimum number of pages to justify showing a fast scroll thumb
    private static final int MIN_PAGES = 2;
    // Scroll thumb not showing
    private static final int STATE_NONE = 0;
    // Not implemented yet - fade-in transition
    // private static final int STATE_ENTER = 1;
    // Scroll thumb visible and moving along with the scrollbar
    private static final int STATE_VISIBLE = 2;
    // Scroll thumb being dragged by user
    private static final int STATE_DRAGGING = 3;
    // Scroll thumb fading out due to inactivity timeout
    private static final int STATE_EXIT = 4;

    private Drawable mThumbDrawable;
    //private Drawable mOverlayDrawable;

    private int mThumbH;
    private int mThumbW;
    private int mThumbY;

    private RectF mOverlayPos;
    private int mOverlaySize = 104;

    private WebView mView;
    private boolean mScrollCompleted;
    private int mVisibleItem;
    private Paint mPaint;
    // private int mViewOffset;
    private int mItemCount = -1;
    private boolean mLongList;

    // private Object[] mSections;
    // private String mSectionText;
    // private boolean mDrawOverlay;
    private ScrollFade mScrollFade;

    private int mState;

    private Handler mHandler = new Handler();

    // private SectionIndexer mSectionIndexer;

    private boolean mChangedBounds;
    private int mMinPagesThreshold;

    public WebViewFastScroller(Context context, WebView view) {
        mView = view;
        setFastscrollState(PreferenceManager.getDefaultSharedPreferences(context).getBoolean(
                "show_scroll_tab", true), MIN_PAGES);
        init(context);
    }

    /**
     * Enable or disable the fast-scroll state.
     * 
     * @param enabled
     *            The new fast-scroll state.
     */
    public void setFastscrollState(boolean enabled, int threshold) {
        if (enabled) {
            mMinPagesThreshold = threshold;
        } else {
            // The user has disabled fast scroll, so make the threshold effectively infinite.
            mMinPagesThreshold = Integer.MAX_VALUE;
        }
        mItemCount = -1;
    }
    
    public void setState(int state) {
        switch (state) {
        case STATE_NONE:
            mHandler.removeCallbacks(mScrollFade);
            mView.invalidate();
            break;
        case STATE_VISIBLE:
            if (mState != STATE_VISIBLE) { // Optimization
                resetThumbPos();
            }
            // Fall through
        case STATE_DRAGGING:
            mHandler.removeCallbacks(mScrollFade);
            break;
        case STATE_EXIT:
            int viewWidth = mView.getWidth();
            mView.invalidate(viewWidth - mThumbW, mThumbY + mView.getScrollY(), viewWidth, mThumbY + mThumbH
                    + mView.getScrollY());
            break;
        }
        mState = state;
    }

    public int getState() {
        return mState;
    }

    private void resetThumbPos() {
        final int viewWidth = mView.getWidth();
        // Bounds are always top right. Y coordinate get's translated during draw
        mThumbDrawable.setBounds(viewWidth - mThumbW, 0, viewWidth, mThumbH);
        mThumbDrawable.setAlpha(ScrollFade.ALPHA_MAX);
    }

    private void useThumbDrawable(Drawable drawable, Context context) {
        mThumbDrawable = drawable;

        mThumbH = drawable.getIntrinsicHeight();
        mThumbW = (int) (mThumbH * 0.75);

        mChangedBounds = true;
    }

    private void init(Context context) {
        // Get both the scrollbar states drawables
        final Resources res = context.getResources();
        useThumbDrawable(res.getDrawable(R.mipmap.fast_scroller), context);

        //mOverlayDrawable = res.getDrawable(R.mipmap.menu_submenu_background);

        mScrollCompleted = true;

        mOverlayPos = new RectF();
        mScrollFade = new ScrollFade();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(mOverlaySize / 2);
        TypedArray ta = context.getTheme().obtainStyledAttributes(new int[] { android.R.attr.textColorPrimary });
        ColorStateList textColor = ta.getColorStateList(ta.getIndex(0));
        int textColorNormal = textColor.getDefaultColor();
        mPaint.setColor(textColorNormal);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);

        mState = STATE_NONE;
    }

    void stop() {
        setState(STATE_NONE);
    }

    boolean isVisible() {
        return !(mState == STATE_NONE);
    }

    public void draw(Canvas canvas) {

        if (mState == STATE_NONE || mMinPagesThreshold >= Integer.MAX_VALUE) {
            // No need to draw anything
            return;
        }

        mThumbY = Math.round(((mView.getHeight() - mThumbH) * mView.getScrollY())
                / (mView.getContentHeight() * mView.getScale() - mView.getHeight()));

        final int y = mThumbY + mView.getScrollY();
        final int x = mView.getScrollX();
        final int viewWidth = mView.getWidth();
        final ScrollFade scrollFade = mScrollFade;

        int alpha = -1;
        if (mState == STATE_EXIT) {
            alpha = scrollFade.getAlpha();
            if (alpha < ScrollFade.ALPHA_MAX / 2) {
                mThumbDrawable.setAlpha(alpha * 2);
            }
            int left = viewWidth - (mThumbW * alpha) / ScrollFade.ALPHA_MAX;
            mThumbDrawable.setBounds(left, 0, viewWidth, mThumbH);
            mChangedBounds = true;
        }

        canvas.translate(x, y);
        mThumbDrawable.draw(canvas);
        canvas.translate(-x, -y);

        // TODO - it may be useful to draw the verse number in the overlay

        // If user is dragging the scroll bar, draw the alphabet overlay
        // if (mState == STATE_DRAGGING && mDrawOverlay) {
        // mOverlayDrawable.draw(canvas);
        // final Paint paint = mPaint;
        // float descent = paint.descent();
        // final RectF rectF = mOverlayPos;
        // canvas.drawText(mSectionText, (int) (rectF.left + rectF.right) / 2, (int) (rectF.bottom + rectF.top) / 2
        // + mOverlaySize / 4 - descent, paint);
        // } else
        if (mState == STATE_EXIT) {
            if (alpha == 0) { // Done with exit
                setState(STATE_NONE);
            } else {
                mView.invalidate(viewWidth - mThumbW, y, viewWidth, y + mThumbH);
            }
        }
    }

    void onSizeChanged(int w, int h, int oldw, int oldh) {
        if (mThumbDrawable != null) {
            mThumbDrawable.setBounds(w - mThumbW, 0, w, mThumbH);
        }
        final RectF pos = mOverlayPos;
        pos.left = (w - mOverlaySize) / 2;
        pos.right = pos.left + mOverlaySize;
        pos.top = h / 10; // 10% from top
        pos.bottom = pos.top + mOverlaySize;
        /*if (mOverlayDrawable != null) {
            mOverlayDrawable.setBounds((int) pos.left, (int) pos.top, (int) pos.right, (int) pos.bottom);
        }*/
    }

    void onScroll(View view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        // Are there enough pages to require fast scroll? Recompute only if total count changes
        if (mItemCount != totalItemCount && visibleItemCount > 0) {
            mItemCount = totalItemCount;
            mLongList = mItemCount / visibleItemCount >= mMinPagesThreshold;
        }
        if (!mLongList) {
            if (mState != STATE_NONE) {
                setState(STATE_NONE);
            }
            return;
        }
        if (totalItemCount - visibleItemCount > 0 && mState != STATE_DRAGGING) {
            mThumbY = ((mView.getHeight() - mThumbH) * firstVisibleItem) / (totalItemCount - visibleItemCount);
            if (mChangedBounds) {
                resetThumbPos();
                mChangedBounds = false;
            }
        }
        mScrollCompleted = true;
        if (firstVisibleItem == mVisibleItem) {
            // return;
        }
        mVisibleItem = firstVisibleItem;
        if (mState != STATE_DRAGGING) {
            setState(STATE_VISIBLE);
            mHandler.postDelayed(mScrollFade, 1500);
        }
    }

    private void scrollTo(float position) {
        mScrollCompleted = false;
        float scrollableHeight = mView.getContentHeight() * mView.getScale() - mView.getHeight();
        float scrollY = position * scrollableHeight;

        mView.scrollTo(mView.getScrollX(), Math.round(scrollY));
    }

    private void cancelFling() {
        // Cancel the list fling
        MotionEvent cancelFling = MotionEvent.obtain(0, 0, MotionEvent.ACTION_CANCEL, 0, 0, 0);
        mView.onTouchEvent(cancelFling);
        cancelFling.recycle();
    }

    boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mState > STATE_NONE && ev.getAction() == MotionEvent.ACTION_DOWN) {
            if ((ev.getX() > (mView.getWidth() - mThumbW)) && (ev.getY() >= mThumbY)
                    && (ev.getY() <= (mThumbY + mThumbH))) {
                setState(STATE_DRAGGING);
                return true;
            }
        }
        return false;
    }

    boolean onTouchEvent(MotionEvent me) {
        if (mState == STATE_NONE) {
            return false;
        }
        if (me.getAction() == MotionEvent.ACTION_DOWN) {
            if ((me.getX() > (mView.getWidth() - mThumbW)) && (me.getY() >= mThumbY)
                    && (me.getY() <= (mThumbY + mThumbH))) {

                setState(STATE_DRAGGING);
                cancelFling();
                return true;
            }
        } else if (me.getAction() == MotionEvent.ACTION_UP) {
            if (mState == STATE_DRAGGING) {
                setState(STATE_VISIBLE);
                final Handler handler = mHandler;
                handler.removeCallbacks(mScrollFade);
                handler.postDelayed(mScrollFade, 1000);
                return true;
            }
        } else if (me.getAction() == MotionEvent.ACTION_MOVE) {
            if (mState == STATE_DRAGGING) {
                final int viewHeight = mView.getHeight();
                // Jitter
                int newThumbY = (int) me.getY() - mThumbH + 10;
                if (newThumbY < 0) {
                    newThumbY = 0;
                } else if (newThumbY + mThumbH > viewHeight) {
                    newThumbY = viewHeight - mThumbH;
                }
                if (Math.abs(mThumbY - newThumbY) < 2) {
                    return true;
                }
                mThumbY = newThumbY;
                // If the previous scrollTo is still pending
                if (mScrollCompleted) {
                    scrollTo((float) mThumbY / (viewHeight - mThumbH));
                }
                return true;
            }
        }
        return false;
    }

    public class ScrollFade implements Runnable {

        long mStartTime;
        long mFadeDuration;
        static final int ALPHA_MAX = 208;
        static final long FADE_DURATION = 200;

        void startFade() {
            mFadeDuration = FADE_DURATION;
            mStartTime = SystemClock.uptimeMillis();
            setState(STATE_EXIT);
        }

        int getAlpha() {
            if (getState() != STATE_EXIT) {
                return ALPHA_MAX;
            }
            int alpha;
            long now = SystemClock.uptimeMillis();
            if (now > mStartTime + mFadeDuration) {
                alpha = 0;
            } else {
                alpha = (int) (ALPHA_MAX - ((now - mStartTime) * ALPHA_MAX) / mFadeDuration);
            }
            return alpha;
        }

        public void run() {
            if (getState() != STATE_EXIT) {
                startFade();
                return;
            }

            if (getAlpha() > 0) {
                mView.invalidate();
            } else {
                setState(STATE_NONE);
            }
        }
    }
}