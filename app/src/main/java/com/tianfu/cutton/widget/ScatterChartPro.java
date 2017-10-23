package com.tianfu.cutton.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.ScatterChart;

/**
 * Created by hei on 2017/9/17.
 */

public class ScatterChartPro extends ScatterChart {
    private float y = 0.0f;
    private Paint mPaint;
    private float mWidth;
    private float mHeight;

    public ScatterChartPro(Context context) {
        super(context);
    }

    public ScatterChartPro(Context context, AttributeSet attrs) {
        super(context, attrs);


    }

    public ScatterChartPro(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }


    public void drawHorizonForY(float y) {
        this.y = y;
    }

    public void cancelHorizon() {
        y = 0;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        try {
            super.onDraw(canvas);

            if (y != 0) {
                if (mPaint == null) {
                    mPaint = new Paint();
                    mPaint.setColor(Color.BLUE);
                    mPaint.setStrokeWidth(3);
                }
                canvas.drawLine(mWidth * 0.07f, mHeight , mWidth * 0.96f,mHeight, mPaint);
                canvas.drawLine(mWidth * 0.07f, 0 , mWidth * 0.96f,0, mPaint);
                canvas.drawLine(mWidth * 0.07f, fixY(y) * mHeight , mWidth * 0.96f, fixY(y )* mHeight, mPaint);
                System.out.println("---------------"+y);
            }
        } catch (Exception e) {

        }

    }

    private float fixY(float y) {
        return (y/1f)*0.83f+0.08f; // 0-1 >>> 0.1-0.85 改这个?
//        return (y/1f)*0.90f+0.05f; // 0-1 >>> 0.1-0.85  我刚才用这个数据 显示你看看
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 获取控件区域宽高
        if (mWidth == 0 || mHeight == 0) {
            final int minimumWidth = getSuggestedMinimumWidth();
            final int minimumHeight = getSuggestedMinimumHeight();
            mWidth = resolveMeasured(widthMeasureSpec, minimumWidth);
            mHeight = resolveMeasured(heightMeasureSpec, minimumHeight);
        }
    }

    private int resolveMeasured(int measureSpec, int desired) {
        int result = 0;
        int specSize = MeasureSpec.getSize(measureSpec);
        switch (MeasureSpec.getMode(measureSpec)) {
            case MeasureSpec.UNSPECIFIED:
                result = desired;
                break;
            case MeasureSpec.AT_MOST:
                result = Math.min(specSize, desired);
                break;
            case MeasureSpec.EXACTLY:
            default:
                result = specSize;
        }
        return result;
    }
}
