package com.tianfu.cutton.widget;

import android.content.Context;
import android.support.v4.widget.NestedScrollView;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;

/**
 * Created by admin on 2017/8/13.
 */

public class MyScrollView extends NestedScrollView {
    private boolean canScroll;

    private GestureDetector mGestureDetector;
    OnTouchListener mGestureListener;

    public MyScrollView(Context context) {
        super(context);
    }
//    public MyScrollView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        mGestureDetector = new GestureDetector(new YScrollDetector());
//        canScroll = true;
//    }

    public MyScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private float mDownPosX = 0;
    private float mDownPosY = 0;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final float x = ev.getX();
        final float y = ev.getY();

        final int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                mDownPosX = x;
                mDownPosY = y;

                break;
            case MotionEvent.ACTION_MOVE:
                final float deltaX = Math.abs(x - mDownPosX);
                final float deltaY = Math.abs(y - mDownPosY);
                // 这里是够拦截的判断依据是左右滑动，读者可根据自己的逻辑进行是否拦截
                if (deltaX > deltaY) {
                    return false;
                }
        }

        return super.onInterceptTouchEvent(ev);
    }

//    class YScrollDetector extends GestureDetector.SimpleOnGestureListener {
//        @Override
//        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
//            if (canScroll)
//                if (Math.abs(distanceY) >= Math.abs(distanceX)) {
//                    canScroll = true;
//                } else {
//                    canScroll = false;
//                }
//            return canScroll;
//        }
//    }
}
