package com.tianfu.cutton.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import com.tianfu.cutton.R;

import java.text.DecimalFormat;

/**
 * Created by xiaohei on 2017/6/29.
 */
public class DoubleSeekBar extends View {
    private Scroller mScroller;
    private int cursorHeight;
    private int lineWidth;
    private int btnRadio;
    private int textSize;

    private int cursorColor;
    private int lineColor;
    private int selectedLineColor;
    private int textColor;
    private int leftBtnColor;
    private int rightBtnColor;

    private Paint linePaint;
    private Paint selectedLinePaint;
    private Paint cursorPaint;
    private Paint textPaint;
    private Paint leftBtnPaint;
    private Paint rightBtnPaint;

    private int touchLeftX;
    private int touchRightX;
    private int btnTop;

    private int textMarginBottom = 5;

    private int lowValue;
    private int highValue;
    private int averageValue;

    private int defaultLow;
    private int defaultHigh;

    private float multiple;

    private int leftPadding, rightPadding;

    private DoubleSeekBarValueChangeListener doubleSeekBarValueChangeListener;
    private boolean flag=false;

    public DoubleSeekBar(Context context) {
        this(context, null);
    }

    public DoubleSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DoubleSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DoubleSeekBar);
        cursorHeight = a.getDimensionPixelSize(R.styleable.DoubleSeekBar_cursor_height, 60);
        btnRadio = a.getDimensionPixelSize(R.styleable.DoubleSeekBar_btn_radio, 40);
        textSize = a.getDimensionPixelSize(R.styleable.DoubleSeekBar_text_size, 30);

        cursorColor = a.getColor(R.styleable.DoubleSeekBar_cursor_color, Color.BLACK);
        lineColor = a.getColor(R.styleable.DoubleSeekBar_line_color, Color.BLACK);
        selectedLineColor = a.getColor(R.styleable.DoubleSeekBar_selected_line_color, Color.BLUE);
        textColor = a.getColor(R.styleable.DoubleSeekBar_text_color, Color.BLACK);
        leftBtnColor = a.getColor(R.styleable.DoubleSeekBar_left_btn_color, Color.BLUE);
        rightBtnColor = a.getColor(R.styleable.DoubleSeekBar_right_btn_color, Color.BLUE);

        lowValue = a.getInteger(R.styleable.DoubleSeekBar_low_value, 0);
        highValue = a.getInteger(R.styleable.DoubleSeekBar_high_value, 9);
        if (lowValue >= highValue) {
            lowValue = 0;
            highValue = 9;
        }
        defaultLow = a.getInteger(R.styleable.DoubleSeekBar_default_low, lowValue);
        defaultHigh = a.getInteger(R.styleable.DoubleSeekBar_default_high, highValue);
        if (defaultLow < lowValue) {
            defaultLow = lowValue;
        }
        if (defaultHigh > highValue) {
            defaultHigh = highValue;
        }
        multiple = a.getFloat(R.styleable.DoubleSeekBar_multiple, 1.0f);

        a.recycle();
        init();
    }

    /*    private void init() {
            mScroller = new Scroller(getContext());
            linePaint = createPaint(lineColor);
            selectedLinePaint = createPaint(selectedLineColor);
            cursorPaint = createPaint(cursorColor);
            textPaint = createPaint(textColor, textSize);
            leftBtnPaint = createPaint(leftBtnColor);
    //        leftBtnPaint.setShadowLayer(5, 2, 2, Color.BLACK);
            leftBtnPaint.setShadowLayer(btnRadio+10, 10, 10, Color.BLACK);
            rightBtnPaint = createPaint(rightBtnColor);
            rightBtnPaint.setShadowLayer(btnRadio+10, 10, 10, Color.BLACK);
        }*/
    private void init() {
        mScroller = new Scroller(getContext());
        linePaint = createPaint(lineColor);
        selectedLinePaint = createPaint(selectedLineColor);
        cursorPaint = createPaint(cursorColor);
        textPaint = createPaint(textColor, textSize);
        leftBtnPaint = createPaint(leftBtnColor);
        this.setLayerType(View.LAYER_TYPE_SOFTWARE, leftBtnPaint);
        leftBtnPaint.setShadowLayer(2, 1, 1, getResources().getColor(R.color.black));
        rightBtnPaint = createPaint(rightBtnColor);
        rightBtnPaint.setShadowLayer(2, 1, 1, getResources().getColor(R.color.black));
    }

    public void setOnDoubleValueChangeListener(DoubleSeekBarValueChangeListener listener) {
        this.doubleSeekBarValueChangeListener = listener;
    }

    public void setDefaultLow(int defaultLow) {
        this.defaultLow = defaultLow;
    }

    public void setDefaultHigh(int defaultHigh) {
        this.defaultHigh = defaultHigh;
    }

    public void setLowValue(int lowValue) {
        this.lowValue = lowValue;
    }

    public void setHighValue(int highValue) {
        this.highValue = highValue;
    }

    public void setLowHighValue(int lowValue, int highValue, int defaultLow, int defaultHigh) {
        this.lowValue = lowValue;
        this.highValue = highValue;
        if (this.lowValue >= this.highValue) {
            this.lowValue = 0;
            this.highValue = 9;
        }
        this.defaultLow = defaultLow;
        this.defaultHigh = defaultHigh;
        if (this.defaultLow < this.lowValue) {
            this.defaultLow = lowValue;
        }
        if (this.defaultHigh > this.highValue) {
            this.defaultHigh = this.highValue;
        }
        requestLayout();
    }

    public void setValues(float lV, float rV) {
        int tLV;
        int tRV;
        if (multiple == 1.0f) {
            tLV = (int) lV;
            tRV = (int) rV;
        } else {
            tLV = (int) (lV / multiple);
            tRV = (int) (rV / multiple);
        }
        touchLeftX = getXPosition(tLV);
        touchRightX = getXPosition(tRV);
        invalidate();
    }

    public void setValues(int tLV, int tRV) {
        touchLeftX = getXPosition(tLV);
        touchRightX = getXPosition(tRV);
        invalidate();
    }

    private int getXPosition(int value) {
        int x = value - lowValue;
        int b = x * averageValue;
        return b + leftPadding;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        leftPadding = btnRadio + getPaddingLeft();
        rightPadding = btnRadio + getPaddingRight();
        lineWidth = widthSpecSize - leftPadding - rightPadding;
        int num = highValue - lowValue;
        averageValue = (lineWidth / num);
        btnRadio = Math.min(averageValue / 2, btnRadio);
        if (heightSpecMode == MeasureSpec.AT_MOST) {
            int tempHeight = Math.max(2 * btnRadio, cursorHeight) + (2 * (textSize + textMarginBottom));
            heightSpecSize = Math.min(tempHeight, heightSpecSize);
        }
        touchLeftX = averageValue * (defaultLow - lowValue) + leftPadding;
        touchRightX = averageValue * (defaultHigh - lowValue) + leftPadding;
        btnTop = heightSpecSize / 2;
        setMeasuredDimension(widthSpecSize, heightSpecSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawLine(canvas);
        drawCursor(canvas);
        drawSelectedLine(canvas);
        drawLeftBtn(canvas);
        drawRightBtn(canvas);
        drawTextView(canvas);
    }

    private void drawLine(Canvas canvas) {
        canvas.drawLine(leftPadding, getHeight() / 2, leftPadding + lineWidth, getHeight() / 2, linePaint);
    }

    private void drawCursor(Canvas canvas) {
        int num = highValue - lowValue;
        int sum = leftPadding;
        int top = (getHeight() - cursorHeight) / 2;
        for (int i = 0; i <= num; i++) {
            canvas.drawLine(sum, top, sum, top + cursorHeight, cursorPaint);
            sum += averageValue;
        }
    }

    private void drawLeftBtn(Canvas canvas) {
        canvas.drawCircle(touchLeftX, btnTop, btnRadio, leftBtnPaint);
    }

    private void drawRightBtn(Canvas canvas) {
        canvas.drawCircle(touchRightX, btnTop, btnRadio, rightBtnPaint);
    }

    private void drawSelectedLine(Canvas canvas) {
        canvas.drawLine(touchLeftX + btnRadio, getHeight() / 2, touchRightX - btnRadio, getHeight() / 2, selectedLinePaint);
    }

    private void drawTextView(Canvas canvas) {
        DecimalFormat decimalFormat = new DecimalFormat(".0");
        String num = multiple == 1.0f ? String.valueOf(getNumValue(touchLeftX)) : decimalFormat.format(getNumValue(touchLeftX) * multiple);
        canvas.drawText(String.valueOf(num), touchLeftX - (textPaint.measureText(String.valueOf(num)) / 2), textSize, textPaint);

        num = multiple == 1.0f ? String.valueOf(getNumValue(touchRightX)) : decimalFormat.format(getNumValue(touchRightX) * multiple);
        canvas.drawText(String.valueOf(num), touchRightX - (textPaint.measureText(String.valueOf(num)) / 2), textSize, textPaint);

    }

    private int getNumValue(int x) {
        int value = x - leftPadding;
        int num = value / averageValue;
        int temp = value - (num * averageValue);
        if (temp > averageValue / 2) {
            num++;
        }
        return num + lowValue;
    }

    private enum TOUCHTYPE {
        LEFT, RIGHT
    }

    private TOUCHTYPE touchType;
    private int touchUpX;

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                flag = true;
                if (mScroller.computeScrollOffset()) {
                    mScroller.forceFinished(true);
                }
                if (isTouchBtn(touchLeftX, event)) {
                    touchType = TOUCHTYPE.LEFT;
                } else if (isTouchBtn(touchRightX, event)) {
                    touchType = TOUCHTYPE.RIGHT;
                } else {
                    touchType = getNearBtn(event);
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_MOVE:

                int touchX = (int) event.getX();
                switch (touchType) {
                    case LEFT:
                        if (touchX > leftPadding && touchX < touchRightX - 2 * btnRadio) {
                            touchLeftX = touchX;
                            invalidate();
                        } else if (touchX >= touchRightX && touchLeftX != touchRightX) {
                            touchLeftX = touchRightX;
                            invalidate();
                        } else if (touchX <= leftPadding && touchLeftX != leftPadding) {
                            touchLeftX = leftPadding;
                            invalidate();
                        }
                        break;
                    case RIGHT:
                        if (touchX < lineWidth + leftPadding && touchX > touchLeftX + 2 * btnRadio) {
                            touchRightX = touchX;
                            invalidate();
                        } else if (touchX <= touchLeftX && touchRightX != touchLeftX) {
                            touchRightX = touchLeftX;
                            invalidate();
                        } else if (touchX >= lineWidth + leftPadding && touchRightX != lineWidth + leftPadding) {
                            touchRightX = lineWidth + leftPadding;
                            invalidate();
                        }
                        break;
                }
                break;
            case MotionEvent.ACTION_UP:
                flag = false;
                int touchUpNum = touchType == TOUCHTYPE.LEFT ? getNumValue(touchLeftX) : getNumValue(touchRightX);
                touchUpX = (touchUpNum - lowValue) * averageValue + leftPadding;
                switch (touchType) {
                    case LEFT:
                        smoothScrollRun(touchLeftX, touchUpX);
                        if (doubleSeekBarValueChangeListener != null) {
                            doubleSeekBarValueChangeListener.onDoubleValueChange(Math.round(touchUpNum * multiple * 10) / 10.0f, Math.round(getNumValue(touchRightX) * multiple * 10) / 10.0f);
                        }
                        break;
                    case RIGHT:
                        smoothScrollRun(touchRightX, touchUpX);
                        if (doubleSeekBarValueChangeListener != null) {
                            doubleSeekBarValueChangeListener.onDoubleValueChange(Math.round(getNumValue(touchLeftX) * multiple * 10) / 10.0f, Math.round(touchUpNum * multiple * 10) / 10.0f);
                        }
                        break;
                }
                break;
        }
        getParent().requestDisallowInterceptTouchEvent(flag);
        return true;
    }

    private int getExactlyCursorX(int x) {
        int num = getNumValue(x);
        return (num - lowValue) * averageValue + leftPadding;
    }

    private TOUCHTYPE getNearBtn(MotionEvent event) {
        int x = (int) event.getX();
        TOUCHTYPE type;
        if (x < touchLeftX) {
            int tempX = x < leftPadding ? leftPadding : x;
            touchLeftX = getExactlyCursorX(tempX);
            type = TOUCHTYPE.LEFT;
        } else if (x > touchRightX) {
            int tempX = x > lineWidth + leftPadding ? lineWidth + leftPadding : x;
            touchRightX = getExactlyCursorX(tempX);
            type = TOUCHTYPE.RIGHT;
        } else {
            int leftTemp = x - touchLeftX;
            int rightTemp = touchRightX - x;
            if (leftTemp > rightTemp) {
                touchRightX = getExactlyCursorX(x);
                type = TOUCHTYPE.RIGHT;
            } else {
                touchLeftX = getExactlyCursorX(x);
                type = TOUCHTYPE.LEFT;
            }
        }

        return type;
    }

    private void smoothScrollRun(int currentX, int destX) {
        mScroller.startScroll(currentX, 0, destX - currentX, 0, 300);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            if (touchType == null) {
                mScroller.forceFinished(true);
                return;
            }
            switch (touchType) {
                case LEFT:
                    touchLeftX = mScroller.getCurrX();
                    break;
                case RIGHT:
                    touchRightX = mScroller.getCurrX();
                    break;
            }
            postInvalidate();
        }
    }

    private boolean isTouchBtn(int x, MotionEvent event) {
        int left = x - btnRadio;
        int right = x + btnRadio;
        int top = btnTop - btnRadio;
        int bottom = btnTop + btnRadio;

        int touchX = (int) event.getX();
        int touchY = (int) event.getY();

        if (touchX > left && touchX < right && touchY > top && touchY < bottom) {
            return true;
        }
        return false;
    }

    private Paint createPaint() {
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setDither(true);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(3);
        return paint;
    }

    private Paint createPaint(int paintColor) {
        Paint paint = createPaint();
        paint.setColor(paintColor);
        return paint;
    }

    private Paint createPaint(int paintColor, int textSize) {
        Paint paint = createPaint(paintColor);
        paint.setTextSize(textSize);
        return paint;
    }

    public interface DoubleSeekBarValueChangeListener {
        void onDoubleValueChange(float lowValue, float highValue);
    }
}


/*
package com.xiaoyu.doubleseekbardemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;

import java.text.DecimalFormat;

*/
/**
 * Created by xiaoyu on 2017/6/29.
 *//*

public class DoubleSeekBar extends View {

    private Scroller mScroller;

    private int cursorHeight;
    private int lineWidth;
    private int btnRadio;
    private int textSize;

    private int cursorColor;
    private int lineColor;
    private int selectedLineColor;
    private int textColor;
    private int leftBtnColor;
    private int rightBtnColor;

    private Paint linePaint;
    private Paint selectedLinePaint;
    private Paint cursorPaint;
    private Paint textPaint;
    private Paint leftBtnPaint;
    private Paint rightBtnPaint;

    private int touchLeftX;
    private int touchRightX;
    private int btnTop;

    private int textMarginBottom = 10;

    private int lowValue;
    private int highValue;
    private int averageValue;

    private int defaultLow;
    private int defaultHigh;

    private float multiple;

    private DoubleSeekBarValueChangeListener doubleSeekBarValueChangeListener;

    public DoubleSeekBar(Context context) {
        this(context, null);
    }

    public DoubleSeekBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DoubleSeekBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DoubleSeekBar);
        cursorHeight = a.getDimensionPixelSize(R.styleable.DoubleSeekBar_cursor_height, 60);
        btnRadio = a.getDimensionPixelSize(R.styleable.DoubleSeekBar_btn_radio, 40);
        textSize = a.getDimensionPixelSize(R.styleable.DoubleSeekBar_text_size, 30);

        cursorColor = a.getColor(R.styleable.DoubleSeekBar_cursor_color, Color.BLACK);
        lineColor = a.getColor(R.styleable.DoubleSeekBar_line_color, Color.BLACK);
        selectedLineColor = a.getColor(R.styleable.DoubleSeekBar_selected_line_color, Color.BLUE);
        textColor = a.getColor(R.styleable.DoubleSeekBar_text_color, Color.BLACK);
        leftBtnColor = a.getColor(R.styleable.DoubleSeekBar_left_btn_color, Color.BLUE);
        rightBtnColor = a.getColor(R.styleable.DoubleSeekBar_right_btn_color, Color.BLUE);

        lowValue = a.getInteger(R.styleable.DoubleSeekBar_low_value, 0);
        highValue = a.getInteger(R.styleable.DoubleSeekBar_high_value, 9);
        if (lowValue >= highValue) {
            lowValue = 0;
            highValue = 9;
        }
        defaultLow = a.getInteger(R.styleable.DoubleSeekBar_default_low, lowValue);
        defaultHigh = a.getInteger(R.styleable.DoubleSeekBar_default_high, highValue);
        if (defaultLow < lowValue) {
            defaultLow = lowValue;
        }
        if (defaultHigh > highValue) {
            defaultHigh = highValue;
        }
        multiple = a.getFloat(R.styleable.DoubleSeekBar_multiple, 1.0f);

        a.recycle();
        init();
    }

    private void init() {
        mScroller = new Scroller(getContext());
        linePaint = createPaint(lineColor);
        selectedLinePaint = createPaint(selectedLineColor);
        cursorPaint = createPaint(cursorColor);
        textPaint = createPaint(textColor, textSize);
        leftBtnPaint = createPaint(leftBtnColor);
        leftBtnPaint.setShadowLayer(5, 2, 2, Color.BLACK);
        rightBtnPaint = createPaint(rightBtnColor);
        rightBtnPaint.setShadowLayer(5, 2, 2, Color.BLACK);
    }

    public void setOnDoubleValueChangeListener(DoubleSeekBarValueChangeListener listener) {
        this.doubleSeekBarValueChangeListener = listener;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        lineWidth = widthSpecSize - (btnRadio * 2);
        int num = highValue - lowValue;
        averageValue = (lineWidth / num);
        btnRadio = Math.min(averageValue / 2, btnRadio);
        if (heightSpecMode == MeasureSpec.AT_MOST) {
            int tempHeight = Math.max(2 * btnRadio, cursorHeight) + (2*(textSize + textMarginBottom));
            heightSpecSize = Math.min(tempHeight, heightSpecSize);
        }
        touchLeftX = averageValue * (defaultLow - lowValue) + btnRadio;
        touchRightX = averageValue * (defaultHigh - lowValue) + btnRadio;
        btnTop = heightSpecSize / 2;
        setMeasuredDimension(widthSpecSize, heightSpecSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawLine(canvas);
        drawCursor(canvas);
        drawSelectedLine(canvas);
        drawLeftBtn(canvas);
        drawRightBtn(canvas);
        drawTextView(canvas);
    }

    private void drawLine(Canvas canvas){
        canvas.drawLine(btnRadio, getHeight() / 2, btnRadio + lineWidth, getHeight() / 2, linePaint);
    }

    private void drawCursor(Canvas canvas) {
        int num = highValue - lowValue;
        int sum = btnRadio;
        int top = (getHeight() - cursorHeight)/2;
        for (int i = 0; i <= num; i++) {
            canvas.drawLine(sum, top, sum, top + cursorHeight, cursorPaint);
            sum += averageValue;
        }
    }

    private void drawLeftBtn(Canvas canvas) {
        canvas.drawCircle(touchLeftX, btnTop, btnRadio, leftBtnPaint);
    }

    private void drawRightBtn(Canvas canvas) {
        canvas.drawCircle(touchRightX, btnTop, btnRadio, rightBtnPaint);
    }

    private void drawSelectedLine(Canvas canvas) {
        canvas.drawLine(touchLeftX + btnRadio, getHeight() / 2, touchRightX - btnRadio, getHeight() / 2, selectedLinePaint);
    }

    private void drawTextView(Canvas canvas) {
        DecimalFormat decimalFormat=new DecimalFormat(".0");
        String num = multiple == 1.0f ? String.valueOf(getNumValue(touchLeftX)) : decimalFormat.format(getNumValue(touchLeftX)*multiple);
        canvas.drawText(String.valueOf(num), touchLeftX - (textPaint.measureText(String.valueOf(num)) / 2), textSize, textPaint);

        num = multiple == 1.0f ? String.valueOf(getNumValue(touchRightX)) : decimalFormat.format(getNumValue(touchRightX)*multiple);
        canvas.drawText(String.valueOf(num), touchRightX - (textPaint.measureText(String.valueOf(num)) / 2), textSize, textPaint);

    }

    private int getNumValue(int x) {
        int value = x - btnRadio;
        int num = value / averageValue;
        int temp = value - (num * averageValue);
        if (temp > averageValue / 2) {
            num++;
        }
        return num + lowValue;
    }

    private enum TOUCHTYPE{
        LEFT, RIGHT
    }

    private TOUCHTYPE touchType;
    private int touchUpX;
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mScroller.computeScrollOffset()) {
                    mScroller.forceFinished(true);
                }
                if (isTouchBtn(touchLeftX, event)) {
                    touchType = TOUCHTYPE.LEFT;
                }else if(isTouchBtn(touchRightX, event)){
                    touchType = TOUCHTYPE.RIGHT;
                }else {
                    touchType = getNearBtn(event);
                    invalidate();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                int touchX = (int)event.getX();
                switch (touchType) {
                    case LEFT:
                        if (touchX > btnRadio && touchX < touchRightX - 2*btnRadio) {
                            touchLeftX = touchX;
                            invalidate();
                        }else if(touchX >= touchRightX - 2*btnRadio && touchLeftX != touchRightX - 2*btnRadio){
                            touchLeftX = touchRightX - 2*btnRadio;
                            invalidate();
                        }else if (touchX <= btnRadio && touchLeftX != btnRadio) {
                            touchLeftX = btnRadio;
                            invalidate();
                        }
                        break;
                    case RIGHT:
                        if (touchX < lineWidth + btnRadio && touchX > touchLeftX + 2*btnRadio) {
                            touchRightX = touchX;
                            invalidate();
                        }else if (touchX <= touchLeftX + 2 * btnRadio && touchRightX != touchLeftX + 2 * btnRadio) {
                            touchRightX = touchLeftX + 2 * btnRadio;
                            invalidate();
                        }else if (touchX >= lineWidth + btnRadio && touchRightX != lineWidth + btnRadio) {
                            touchRightX = lineWidth + btnRadio;
                            invalidate();
                        }
                        break;
                }
                break;
            case MotionEvent.ACTION_UP:
                int touchUpNum = touchType == TOUCHTYPE.LEFT ? getNumValue(touchLeftX) : getNumValue(touchRightX);
                touchUpX = (touchUpNum - lowValue) * averageValue + btnRadio;
                switch (touchType) {
                    case LEFT:
                        smoothScrollRun(touchLeftX, touchUpX);
                        if (doubleSeekBarValueChangeListener != null) {
                            doubleSeekBarValueChangeListener.onDoubleValueChange(Math.round(touchUpNum*multiple*10)/10.0f, Math.round(getNumValue(touchRightX)*multiple*10)/10.0f);
                        }
                        break;
                    case RIGHT:
                        smoothScrollRun(touchRightX, touchUpX);
                        if (doubleSeekBarValueChangeListener != null) {
                            doubleSeekBarValueChangeListener.onDoubleValueChange(Math.round(getNumValue(touchLeftX)*multiple*10)/10.0f, Math.round(touchUpNum*multiple*10)/10.0f);
                        }
                        break;
                }
                break;
        }

        return true;
    }

    private int getExactlyCursorX(int x) {
        int num = getNumValue(x);
        return (num - lowValue) * averageValue + btnRadio;
    }

    private TOUCHTYPE getNearBtn(MotionEvent event) {
        int x = (int)event.getX();
        TOUCHTYPE type;
        if (x < touchLeftX) {
            int tempX = x < btnRadio ? btnRadio : x;
            touchLeftX = getExactlyCursorX(tempX);
            type = TOUCHTYPE.LEFT;
        }else if (x > touchRightX) {
            int tempX = x > lineWidth + btnRadio ? lineWidth + btnRadio : x;
            touchRightX = getExactlyCursorX(tempX);
            type = TOUCHTYPE.RIGHT;
        }else {
            int leftTemp = x - touchLeftX;
            int rightTemp = touchRightX - x;
            if (leftTemp > rightTemp) {
                touchRightX = getExactlyCursorX(x);
                type = TOUCHTYPE.RIGHT;
            }else{
                touchLeftX = getExactlyCursorX(x);
                type = TOUCHTYPE.LEFT;
            }
        }

        return type;
    }

    private void smoothScrollRun(int currentX, int destX) {
        mScroller.startScroll(currentX, 0, destX - currentX, 0, 300);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            if (touchType == null) {
                mScroller.forceFinished(true);
                return;
            }
            switch (touchType) {
                case LEFT:
                    touchLeftX = mScroller.getCurrX();
                    break;
                case RIGHT:
                    touchRightX = mScroller.getCurrX();
                    break;
            }
            postInvalidate();
        }
    }

    private boolean isTouchBtn(int x, MotionEvent event){
        int left = x - btnRadio;
        int right = x + btnRadio;
        int top = btnTop - btnRadio;
        int bottom = btnTop + btnRadio;

        int touchX = (int)event.getX();
        int touchY = (int)event.getY();

        if (touchX > left && touchX < right && touchY > top && touchY < bottom) {
            return true;
        }
        return false;
    }

    private Paint createPaint(){
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeCap(Paint.Cap.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setDither(true);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(3);
        return paint;
    }

    private Paint createPaint(int paintColor) {
        Paint paint = createPaint();
        paint.setColor(paintColor);
        return paint;
    }

    private Paint createPaint(int paintColor, int textSize) {
        Paint paint = createPaint(paintColor);
        paint.setTextSize(textSize);
        return paint;
    }

    public interface DoubleSeekBarValueChangeListener{
        void onDoubleValueChange(float lowValue, float highValue);
    }
}
*/
