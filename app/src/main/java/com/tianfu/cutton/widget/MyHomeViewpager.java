package com.tianfu.cutton.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * Created by admin on 2017/10/31.
 */

public class MyHomeViewpager extends ViewPager {
    private boolean flag=false;
    public MyHomeViewpager(Context context) {
        super(context);
    }
    public MyHomeViewpager(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                flag = true;
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_CANCEL:
                flag = false;
                break;
        }
        getParent().requestDisallowInterceptTouchEvent(flag);
        return true;
    }
}
