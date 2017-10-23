package com.tianfu.cutton.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.tmall.ultraviewpager.UltraViewPager;

/**
 * Created by admin on 2017/8/13.
 */

public class UltraViewPagerPro extends UltraViewPager {
    private        boolean flag = false;

    public UltraViewPagerPro(Context context) {
        super(context);
    }

    public UltraViewPagerPro(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UltraViewPagerPro(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                flag = false;
                break;
            case MotionEvent.ACTION_MOVE:
                flag = true;
                break;
        }
        getParent().requestDisallowInterceptTouchEvent(flag);
        return super.onTouchEvent(event);
    }
}
