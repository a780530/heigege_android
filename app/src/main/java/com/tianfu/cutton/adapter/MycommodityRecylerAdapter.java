package com.tianfu.cutton.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tianfu.cutton.R;
import com.tianfu.cutton.activity.base.BaseApplication;
import com.tianfu.cutton.model.MyStoreBean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/20.
 */

public class MycommodityRecylerAdapter extends BaseQuickAdapter<MyStoreBean.ValueBean.RowsBean, BaseViewHolder> {


    private Button commodiy_chushou;
    private Button commodiy_xiajia;
    private Button commodiy_xiaohao;
    private Button commodiy_key;
    private TextView commodiy_danwei;
    private TextView commodiy_length;
    private TextView commodiy_breakLoadAverage;

    public MycommodityRecylerAdapter(@LayoutRes int layoutResId, @Nullable List<MyStoreBean.ValueBean.RowsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder view, MyStoreBean.ValueBean.RowsBean item) {
        LinearLayout ll_MycommodityButton = view.getView(R.id.ll_MycommodityButton);
        view.addOnClickListener(R.id.commodiy_chushou)
                .addOnClickListener(R.id.commodiy_xiaohao).addOnClickListener(R.id.commodiy_xiajia);
        commodiy_chushou = view.getView(R.id.commodiy_chushou);
        commodiy_xiajia = view.getView(R.id.commodiy_xiajia);
        commodiy_xiaohao = view.getView(R.id.commodiy_xiaohao);
        commodiy_key = view.getView(R.id.commodiy_key);
        commodiy_length = view.getView(R.id.commodiy_length);
        commodiy_breakLoadAverage = view.getView(R.id.commodiy_breakLoadAverage);
        commodiy_danwei = view.getView(R.id.commodiy_danwei);
        if (item.state.equals("ON")) {
            ll_MycommodityButton.setVisibility(View.VISIBLE);
            view.setText(R.id.commodiy_xiajia,"下架");
        } else if (item.state.equals("OFF")) {
            ll_MycommodityButton.setVisibility(View.VISIBLE);
            view.setText(R.id.commodiy_xiajia,"上架");
        } else {
            ll_MycommodityButton.setVisibility(View.GONE);
        }
        TextView commodiy_state = view.getView(R.id.commodiy_state);
        if (item.state.equals("ON")) {
            commodiy_state.setTextColor(BaseApplication.getContextObject().getResources().getColor(R.color.drop_color));
            view.setText(R.id.commodiy_state, "已上架");
        } else if (item.state.equals("OFF")) {
            commodiy_state.setTextColor(BaseApplication.getContextObject().getResources().getColor(R.color.tab_tv_selected));
            view.setText(R.id.commodiy_state, "未上架");
        } else if (item.state.equals("SOLD_OUT")) {
            commodiy_state.setTextColor(BaseApplication.getContextObject().getResources().getColor(R.color.drop_color));
            view.setText(R.id.commodiy_state, "已出售");
        } else {
            commodiy_state.setTextColor(BaseApplication.getContextObject().getResources().getColor(R.color.drop_color));
            view.setText(R.id.commodiy_state, "已消耗");
        }
        view.setText(R.id.commodiy_Code, item.code + "(" + item.bagCount + "/" + item.batchCount + ")");
        if (item.property != null && !"".equals(item.property)) {
            commodiy_key.setVisibility(View.VISIBLE);
            view.setText(R.id.commodiy_key, item.property);
        } else {
            commodiy_key.setVisibility(View.GONE);
        }
        if (item.price != 0) {
            view.setText(R.id.commodiy_money, item.price + "");
            commodiy_danwei.setVisibility(View.VISIBLE);
        } else {
            view.setText(R.id.commodiy_money, "价格面议");
            commodiy_danwei.setVisibility(View.GONE);
        }
        if (item.lengthAverage != null && !"".equals(item.lengthAverage)) {
            view.setText(R.id.commodiy_length, item.lengthAverage);
            if (Double.parseDouble(item.lengthAverage) >= 28) {
                commodiy_length.setTextColor(BaseApplication.getContextObject().getResources().getColor(R.color.red_translucent));
            } else {
                commodiy_length.setTextColor(BaseApplication.getContextObject().getResources().getColor(R.color.drop_color));
            }
        } else {
            view.setText(R.id.commodiy_length, "-");
        }
        if (item.breakLoadAverage != null && !"".equals(item.breakLoadAverage)) {
            view.setText(R.id.commodiy_breakLoadAverage, item.breakLoadAverage);
            if (Double.parseDouble(item.breakLoadAverage) >= 28) {
                commodiy_breakLoadAverage.setTextColor(BaseApplication.getContextObject().getResources().getColor(R.color.red_translucent));
            } else {
                commodiy_breakLoadAverage.setTextColor(BaseApplication.getContextObject().getResources().getColor(R.color.drop_color));
            }
        } else {
            view.setText(R.id.commodiy_breakLoadAverage, "-");
        }
        if (item.micronAverage != null && !"".equals(item.micronAverage)) {
            view.setText(R.id.commodiy_micronAverage, item.micronAverage);
        } else {
            view.setText(R.id.commodiy_micronAverage, "-");
        }
        if (item.type != null && !"".equals(item.type)) {
            view.setText(R.id.commodiy_type, item.type);
        } else {
            view.setText(R.id.commodiy_type, "-");
        }
        if (item.factory != null && !"".equals(item.storage)) {
            view.setText(R.id.commodiy_factory, item.storage);
        } else {
            view.setText(R.id.commodiy_factory, "-");
        }
    }
}
