package com.tianfu.cutton.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tianfu.cutton.R;
import com.tianfu.cutton.activity.base.BaseApplication;
import com.tianfu.cutton.model.QualityBean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/20.
 */

public class QualityRecylerAdapter extends BaseQuickAdapter<QualityBean.ValueBean.RowsBean, BaseViewHolder> {

    public QualityRecylerAdapter(@LayoutRes int layoutResId, @Nullable List<QualityBean.ValueBean.RowsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, QualityBean.ValueBean.RowsBean item) {
        helper.setText(R.id.tv_bitch, item.code + "(" + item.bagCount + "/" + item.batchCount + ")");
        String property = item.property;
        Button button = helper.getView(R.id.bt_key);
        if (item.batchType.equals("1")) {
            button.setVisibility(View.GONE);
        } else if (property != null && !"".equals(property)) {
            button.setVisibility(View.VISIBLE);
            helper.setText(R.id.bt_key,property);
        }else{
            button.setVisibility(View.GONE);
        }
        TextView tv_quality_type = helper.getView(R.id.tv_quality_type);

        if (item.type != null&&!"".equals(item.type)) {
            helper.setText(R.id.tv_quality_type, item.type);
            tv_quality_type.setVisibility(View.VISIBLE);
        } else {
            tv_quality_type.setVisibility(View.GONE);
            helper.setText(R.id.tv_quality_type, "");
        }
        TextView tv_quality_length = helper.getView(R.id.tv_quality_length);
        if (item.lengthAverage == null || "".equals(item.lengthAverage)) {
            helper.setText(R.id.tv_quality_length, "--");
        } else {
            if (Double.parseDouble(item.lengthAverage) >= 28) {
                tv_quality_length.setTextColor(BaseApplication.getContextObject().getResources().getColor(R.color.red_translucent));
            } else {
                tv_quality_length.setTextColor(BaseApplication.getContextObject().getResources().getColor(R.color.drop_color));
            }
            helper.setText(R.id.tv_quality_length, item.lengthAverage);
        }
        TextView tv_quality_break = helper.getView(R.id.tv_quality_break);
        if (item.breakLoadAverage == null || "".equals(item.breakLoadAverage)) {
            helper.setText(R.id.tv_quality_break, "--");
        } else {
            if (Double.parseDouble(item.breakLoadAverage) >= 28) {
                tv_quality_break.setTextColor(BaseApplication.getContextObject().getResources().getColor(R.color.red_translucent));
            } else {
                tv_quality_break.setTextColor(BaseApplication.getContextObject().getResources().getColor(R.color.drop_color));
            }
            helper.setText(R.id.tv_quality_break, item.breakLoadAverage);
        }
        if (item.micronAverage == null || "".equals(item.micronAverage)) {
            helper.setText(R.id.tv_quality_horse, "--");
        } else {
            helper.setText(R.id.tv_quality_horse, item.micronAverage);
        }
//        tv_quality_storage
        if (item.checkStorage == null || "".equals(item.checkStorage)) {
            helper.setText(R.id.tv_quality_storage, "--");
        } else {
            helper.setText(R.id.tv_quality_storage, item.checkStorage);
        }
//        tv_quality_factory
        if (item.factory == null || "".equals(item.factory)) {
            helper.setText(R.id.tv_quality_factory, "--");
        } else {
            helper.setText(R.id.tv_quality_factory, item.factory);
        }
    }
}
