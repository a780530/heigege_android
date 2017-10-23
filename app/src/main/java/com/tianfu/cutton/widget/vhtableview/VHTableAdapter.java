package com.tianfu.cutton.widget.vhtableview;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tianfu.cutton.R;
import com.tianfu.cutton.activity.base.BaseApplication;

import java.util.ArrayList;

/**
 * Created by hei on 2016/7/20.
 * <p>
 * 都实现VHBaseAdapter接口
 */
public class VHTableAdapter implements VHBaseAdapter {
    private Context context;
    private ArrayList<String> titleData;
    private ArrayList<ArrayList<String>> dataList;

    public VHTableAdapter(Context context, ArrayList<String> titleData, ArrayList<ArrayList<String>> dataList) {
        this.context = context;
        this.titleData = titleData;
        this.dataList = dataList;
    }

    //表格内容的行数，不包括标题行
    @Override
    public int getContentRows() {
        return dataList.size();
    }

    private static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    //列数
    @Override
    public int getContentColumn() {
        return titleData.size();
    }

    //标题的view，这里从0开始，这里要注意，一定要有view返回去，不能为null，每一行
    // 各列的宽度就等于标题行的列的宽度，且边框的话，自己在这里和下文的表格单元格view里面设置
    @Override
    public View getTitleView(int columnPosition, ViewGroup parent) {
        int topAndBottom = dip2px(BaseApplication.getContextObject(), 5);
        int leftAndRight = dip2px(BaseApplication.getContextObject(), 10);
        TextView tv_item = new TextView(context);
        tv_item.setBackgroundResource(R.drawable.bg_shape_gray);
        if (0 == columnPosition) {
//            int left, int top, int right, int bottom
            tv_item.setPadding(leftAndRight, topAndBottom, leftAndRight, topAndBottom);
        } else {
            tv_item.setPadding(leftAndRight, topAndBottom, leftAndRight, topAndBottom);
        }
        tv_item.setText(titleData.get(columnPosition));
        tv_item.setGravity(Gravity.CENTER);
        tv_item.setTextSize(14);
//        tv_item.setTextColor(context.getResources().getColor(R.color.darkgreen));
        tv_item.setTextColor(context.getResources().getColor(R.color.grey_33));
        return tv_item;
    }

    //表格正文的view，行和列都从0开始，宽度的话在载入的时候，默认会是以标题行各列的宽度，高度的话自适应
    @Override
    public View getTableCellView(int contentRow, int contentColum, View view, ViewGroup parent) {
        if (null == view) {
            view = new TextView(context);
        }
        view.setBackgroundResource(R.drawable.bg_shape_gray);
        view.setPadding(0, 0, 0, 0);
        ((TextView) view).setText(dataList.get(contentRow).get(contentColum));
        ((TextView) view).setGravity(Gravity.CENTER);
        //为了更灵活一些，在VHTableView没收做设置边框，在这里通过背景实现，我这里的背景边框是顺手设的，要是想美观点的话，对应的边框做一下对应的设置就好
//        ((TextView)view).setTextColor(context.getResources().getColor(R.color.darkgreen));
        ((TextView) view).setTextColor(context.getResources().getColor(R.color.grey_33));
        if ( ((TextView) view).getText().toString().trim().equals("有")){
            ((TextView) view).setTextColor(context.getResources().getColor(R.color.homeTextLongColor));
        }
        return view;
    }


    @Override
    public Object getItem(int contentRow) {
        return dataList.get(contentRow);
    }


    //每一行被点击的时候的回调
    @Override
    public void OnClickContentRowItem(int row, View convertView) {
    }


}
