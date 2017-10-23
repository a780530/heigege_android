package com.tianfu.cutton.widget;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Build;
import android.support.v7.content.res.AppCompatResources;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Scroller;

import com.tianfu.cutton.R;
import com.tianfu.cutton.activity.base.BaseApplication;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by xiaohei on 2017/7/15.
 */
public class MultiCheckGroupView extends ViewGroup {

    private static final String TAG = "MultiCheckGroupView";

    private Scroller mScroller;

    private int verticalSpace = dip2px(BaseApplication.getContextObject(), 6);//button 横着的间隙
    private int horizontalSpace =dip2px(BaseApplication.getContextObject(), 7);//button竖着的间隙

    private int rowNum = 3;//有几排button

    private int buttonWidth;
    private int buttonHeight = dip2px(BaseApplication.getContextObject(), 36); //button的高度

    private int currentHeight;

    private Map<String, String> values = new TreeMap<>();
    private List<State> selectedList = new ArrayList<>();

    private SharedPreferences sp;

    public MultiCheckGroupView(Context context) {
        this(context, null);
    }

    public MultiCheckGroupView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultiCheckGroupView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        prepare();
    }


    private void prepare() {
        sp = getContext().getSharedPreferences(TAG, Context.MODE_PRIVATE);
        mScroller = new Scroller(getContext());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        if (heightSpecMode == MeasureSpec.AT_MOST || heightSpecMode == MeasureSpec.UNSPECIFIED) {
            heightSpecSize = getHeightSize();
        }

        buttonWidth = getButtonWidth(widthSpecSize);
        setMeasuredDimension(widthSpecSize, heightSpecSize);
    }

    private int getButtonWidth(int widthSpecSize) {
        int contentWidth = widthSpecSize - getPaddingLeft() - getPaddingRight();
        int verticalSpaceNum = rowNum - 1;
        int truelyWidth = contentWidth - verticalSpaceNum * horizontalSpace;
        return truelyWidth / rowNum;
    }

    private int getHeightSize() {
        int heightSpecSize = 0;
        int lineNum = getButtonLineNum();
        if (lineNum != 0) {
            heightSpecSize = lineNum * buttonHeight + ((lineNum - 1) * verticalSpace) + getPaddingBottom() + getPaddingTop();
        }

        return heightSpecSize;
    }

    private int getButtonLineNum() {
        int lineNum = 0;

        if (values != null && values.size() > 0) {
            lineNum = values.size() / rowNum;
            lineNum = values.size() % rowNum == 0 ? lineNum : lineNum + 1;
        }

        return lineNum;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        removeAllViews();
        Set<Map.Entry<String, String>> entries = values.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            Button box = createCheckBox(getContext(), entry, getChildCount());
            layoutMyChild(box, getChildCount() - 1);
        }
    }

    private int getFontHeight()
    {
        Paint paint = new Paint();
        Paint.FontMetrics fm = paint.getFontMetrics();
        return (int) Math.ceil(fm.descent - fm.top) + 2;
    }
    private int getTextPaddingTop() {
        int textPaddingTop;
        textPaddingTop = (buttonHeight - dip2px(BaseApplication.getContextObject(), getFontHeight())) / 2;
        return textPaddingTop;
    }

    private Button createCheckBox(Context context, Map.Entry<String, String> entry, final int index) {
        Button box = new Button(context);
        box.setTag(entry.getKey());
        box.setText(entry.getValue());
        box.setTextSize(14);
        box.setPadding(0, getTextPaddingTop(), 0, 0);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            box.setStateListAnimator(null);
        }
        box.setTextColor(AppCompatResources.getColorStateList(BaseApplication.getContextObject(),R.drawable.set_color));
        box.setBackgroundResource(R.drawable.item_button);
        box.setSelected(selectedList.get(index).seleted);

        box.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isSelected = v.isSelected();
                //selectedList.get(index).seleted = !isSelected;
                v.setSelected(!isSelected);
            }
        });
        LayoutParams l = new LayoutParams(buttonWidth, buttonHeight);
        addView(box, l);
        return box;
    }

    public void commit(){
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            Button child = (Button)getChildAt(i);
            selectedList.get(i).seleted = child.isSelected();
        }
    }

    public void commitFixed(){
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            Button child = (Button)getChildAt(i);
            selectedList.get(i).seleted = child.isSelected();
            sp.edit().putBoolean((String)child.getTag(), child.isSelected()).apply();
        }
    }

    private void layoutMyChild(View child, int position) {
        int cLeft = getPaddingLeft();
        int cTop = getPaddingTop();
        int horizontalNum = position % rowNum;
        int verticalNum = position / rowNum;
        cLeft = (buttonWidth + horizontalSpace) * horizontalNum + cLeft;
        cTop = (buttonHeight + verticalSpace) * verticalNum + cTop;
        if (verticalNum == 0 && cTop + buttonHeight > getHeight()) {
            return;
        }
        child.layout(cLeft, cTop, cLeft + buttonWidth, cTop + buttonHeight);
    }

    public void addValues(Map<String, String> values) {
        this.values = values;
        selectedList.clear();
        Set<String> set = values.keySet();
        for (String key : set) {
            selectedList.add(new State(sp.getBoolean(key, false)));
        }

        requestLayout();
    }

    private boolean isShow = true;
    public boolean isShowMyLayout() {
        return isShow;
    }

    public void hideMyLayout(){
        isShow = false;
        if (mScroller.computeScrollOffset()) {
            mScroller.forceFinished(true);
            smoothScrollRun(getHeight(), 0);
        }else{
            currentHeight = getHeight();
            smoothScrollRun(currentHeight, 0);
        }

    }

    public void showMyLayout(){
        isShow = true;
        if (mScroller.computeScrollOffset()) {
            mScroller.forceFinished(true);
            scrollSetHeight(1);
            smoothScrollRun(getHeight(), currentHeight);
        }else{
            smoothScrollRun(0, currentHeight);
        }
    }

    private void smoothScrollRun(int currentX, int destX) {
        mScroller.startScroll(currentX, 0, destX - currentX, 0, 300);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollSetHeight(mScroller.getCurrX());
            postInvalidate();
        }
    }

    private void scrollSetHeight(int height){
        LayoutParams lp = getLayoutParams();
        lp.height = height;
        setLayoutParams(lp);
    }

    public List<String> getSelectedList(){
        int count = getChildCount();
        List<String> l = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);
            if (view.isSelected()) {
                l.add((String) view.getTag());
            }
        }
        return l;
    }

    public void clearSelected() {
        for (State b : selectedList) {
            b.seleted = false;
        }
        requestLayout();
    }

    public void clearFixed(){
        sp.edit().clear().apply();
    }

    private static class State{

        public State(boolean s){
            this.seleted = s;
        }

        boolean seleted = false;
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    private static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}


/*
package com.xiaoyu.doubleseekbardemo;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.Scroller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

*/
/**
 * Created by xiaoyu on 2017/7/15.
 *//*

public class MultiCheckGroupView extends ViewGroup {

    private static final String TAG = "MultiCheckGroupView";

    private Scroller mScroller;

    private int verticalSpace = 10;
    private int horizontalSpace = 10;

    private int rowNum = 3;

    private int buttonWidth;
    private int buttonHeight = 120;

    private int currentHeight;

    private SharedPreferences sp;

    private Map<String, String> values = new TreeMap<>();

    public MultiCheckGroupView(Context context) {
        this(context, null);
    }

    public MultiCheckGroupView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultiCheckGroupView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        prepare();
    }


    private void prepare() {
        mScroller = new Scroller(getContext());
        sp = getContext().getSharedPreferences(TAG, Context.MODE_PRIVATE);
        setWillNotDraw(false);
    }

    private int myExactlyHeight;
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);

        if (heightSpecMode == MeasureSpec.AT_MOST || heightSpecMode == MeasureSpec.UNSPECIFIED) {
            if (!mScroller.computeScrollOffset()) {
                if (isShow) {
                    myExactlyHeight = getHeightSize();
                }else{
                    myExactlyHeight = 0;
                }
            }
        }else {
            myExactlyHeight = heightSpecSize;
        }

        buttonWidth = getButtonWidth(widthSpecSize);
        setMeasuredDimension(widthSpecSize, myExactlyHeight);
    }

    private int getButtonWidth(int widthSpecSize) {
        int contentWidth = widthSpecSize - getPaddingLeft() - getPaddingRight();
        int verticalSpaceNum = rowNum - 1;
        int truelyWidth = contentWidth - verticalSpaceNum * horizontalSpace;
        return truelyWidth / rowNum;
    }

    private int getHeightSize() {
        int heightSpecSize = 0;
        int lineNum = getButtonLineNum();
        if (lineNum != 0) {
            heightSpecSize = lineNum * buttonHeight + ((lineNum - 1) * verticalSpace) + getPaddingBottom() + getPaddingTop();
        }

        return heightSpecSize;
    }

    private int getButtonLineNum() {
        int lineNum = 0;

        if (values != null && values.size() > 0) {
            lineNum = values.size() / rowNum;
            lineNum = values.size() % rowNum == 0 ? lineNum : lineNum + 1;
        }

        return lineNum;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        removeAllViews();
        Set<Map.Entry<String, String>> entries = values.entrySet();
        for (Map.Entry<String, String> entry : entries) {
            Button box = createCheckBox(getContext(), entry);
            layoutMyChild(box, getChildCount() - 1);
        }
    }

    private Button createCheckBox(Context context, Map.Entry<String, String> entry) {
        Button box = new Button(context);
        box.setTag(entry.getKey());
        box.setText(entry.getValue());
        box.setPadding(0, 30, 0, 0);

        box.setSelected(sp.getBoolean(entry.getKey(), false));
        box.setBackgroundResource(R.drawable.item_button);

        box.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isSelected = v.isSelected();
                v.setSelected(!isSelected);
                sp.edit().putBoolean((String) v.getTag(), !isSelected).apply();
            }
        });
        LayoutParams l = new LayoutParams(buttonWidth, buttonHeight);
        addView(box, l);
        return box;
    }

    private void layoutMyChild(View child, int position) {
        int cLeft = getPaddingLeft();
        int cTop = getPaddingTop();
        int horizontalNum = position % rowNum;
        int verticalNum = position / rowNum;
        cLeft = (buttonWidth + horizontalSpace) * horizontalNum + cLeft;
        cTop = (buttonHeight + verticalSpace) * verticalNum + cTop;
        if (verticalNum == 0 && cTop + buttonHeight > getHeight()) {
            return;
        }
        child.layout(cLeft, cTop, cLeft + buttonWidth, cTop + buttonHeight);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {

        super.dispatchDraw(canvas);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child instanceof Button) {
                String id = (String) child.getTag();
                child.setSelected(sp.getBoolean(id, false));
            }
        }
    }

    public void addValues(Map<String, String> values) {
        this.values = values;
        requestLayout();
    }

    private boolean isShow = true;
    public boolean isShowMyLayout() {
        return isShow;
    }

    public void hideMyLayout() {
        isShow = false;
        if (mScroller.computeScrollOffset()) {
            mScroller.forceFinished(true);
            smoothScrollRun(getHeight(), 0);
        } else {
            currentHeight = getHeight();
            smoothScrollRun(currentHeight, 0);
        }

    }

    public void showMyLayout(){
        isShow = true;
        if (mScroller.computeScrollOffset()) {
            mScroller.forceFinished(true);
            scrollSetHeight(1);
            smoothScrollRun(getHeight(), currentHeight);
        }else {
            smoothScrollRun(0, currentHeight);
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        List<String> list = getSelectedList();
        for (String l : list) {
            sp.edit().putBoolean(l, false).apply();
        }
    }

    private void smoothScrollRun(int currentX, int destX) {
        mScroller.startScroll(currentX, 0, destX - currentX, 0, 300);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if (mScroller.computeScrollOffset()) {
            scrollSetHeight(mScroller.getCurrX());
            postInvalidate();
        }
    }

    private void scrollSetHeight(int height) {
        ScrollView.LayoutParams lp = (ScrollView.LayoutParams) getLayoutParams();
        myExactlyHeight = height;
        lp.height = myExactlyHeight;
        setLayoutParams(lp);
    }

    public List<String> getSelectedList(){
        int count = getChildCount();
        List<String> l = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);
            if (view.isSelected()) {
                l.add((String) view.getTag());
            }
        }
        return l;
    }
}*/
