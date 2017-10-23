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
import com.tianfu.cutton.model.KunGetBatch;

import java.util.List;

/**
 * Created by admin on 2017/7/21.
 */

public class KunGetBatchAdapter extends BaseQuickAdapter<KunGetBatch.ValueBean.RowsBean, BaseViewHolder> {
    public KunGetBatchAdapter(@LayoutRes int layoutResId, @Nullable List<KunGetBatch.ValueBean.RowsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, KunGetBatch.ValueBean.RowsBean item) {
        helper.setText(R.id.tv_bitch, item.batchCode + "(" + item.totalBag + "/" + "1" + ")");
        Button bt_key = helper.getView(R.id.bt_key);
        bt_key.setVisibility(View.GONE);
        String property = item.property;
        if (property != null&&!"".equals(property)) {
            bt_key.setText(property);
            bt_key.setVisibility(View.VISIBLE);
        }else{
            bt_key.setVisibility(View.GONE);
        }
        TextView tv_quality_length = helper.getView(R.id.tv_quality_length);
        if (item.lengthAverage != null&&!"".equals(item.lengthAverage)) {
            helper.setText(R.id.tv_quality_length, item.lengthAverage);
            if (Double.parseDouble(item.lengthAverage)>=28){
                tv_quality_length.setTextColor(BaseApplication.getContextObject().getResources().getColor(R.color.red_translucent));
            }else{
                tv_quality_length.setTextColor(BaseApplication.getContextObject().getResources().getColor(R.color.drop_color));
            }
        } else {
            helper.setText(R.id.tv_quality_length, "--");
        }
        TextView tv_quality_break = helper.getView(R.id.tv_quality_break);
        if (item.breakLoadAverage != null&&!"".equals(item.breakLoadAverage)) {
            helper.setText(R.id.tv_quality_break, item.breakLoadAverage);
            if (Double.parseDouble(item.breakLoadAverage)>=28){
                tv_quality_break.setTextColor(BaseApplication.getContextObject().getResources().getColor(R.color.red_translucent));
            }else{
                tv_quality_break.setTextColor(BaseApplication.getContextObject().getResources().getColor(R.color.drop_color));
            }
        }else{
            helper.setText(R.id.tv_quality_break, "--");
        }
//        tv_quality_horse
        if (item.micronAverage != null&&!"".equals(item.micronAverage)) {
            helper.setText(R.id.tv_quality_horse,item.micronAverage);
        }else{
            helper.setText(R.id.tv_quality_horse,"--");
        }
//        tv_quality_storage
        if (item.checkStorage != null&&!"".equals(item.checkStorage)) {
            helper.setText(R.id.tv_quality_storage,item.checkStorage);
        }else{
            helper.setText(R.id.tv_quality_storage,"--");
        }
//        tv_quality_factory
        TextView tv_quality_type = helper.getView(R.id.tv_quality_type);
        if (item.type!=null&&!"".equals(item.type)){
            tv_quality_type.setText(item.type);
            tv_quality_type.setVisibility(View.VISIBLE);
        }else{
            tv_quality_type.setVisibility(View.GONE);
        }

        if (item.factory != null&&!"".equals(item.factory)) {
            helper.setText(R.id.tv_quality_factory,item.factory);
        }else{
            helper.setText(R.id.tv_quality_factory,"--");
        }
    }
}
