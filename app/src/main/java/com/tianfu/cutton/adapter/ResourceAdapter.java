package com.tianfu.cutton.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tianfu.cutton.R;
import com.tianfu.cutton.activity.base.BaseApplication;
import com.tianfu.cutton.model.ResourcesBean;

import java.util.List;

/**
 * Created by admin on 2017/10/15.
 */

public class ResourceAdapter extends BaseQuickAdapter<ResourcesBean.ValueBean, BaseViewHolder> {
    public ResourceAdapter(@LayoutRes int layoutResId, @Nullable List<ResourcesBean.ValueBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ResourcesBean.ValueBean item) {
//        AutoUtils.autoSize(helper.itemView);
        helper.setText(R.id.re_batch, item.code + "(" + item.bagCount + "/" + item.batchCount + ")");
        Button re_key = helper.getView(R.id.re_key);
//        property
        if (TextUtils.isEmpty(item.property)) {
            re_key.setVisibility(View.GONE);
        } else {
            helper.setText(R.id.re_key, item.property);
            re_key.setVisibility(View.VISIBLE);
        }
        if (!TextUtils.isEmpty(item.price + "")) {
            helper.setText(R.id.re_price, item.price);
        }else {
            helper.setText(R.id.re_price,"--");
        }
        TextView re_length = helper.getView(R.id.re_length);
        if (!TextUtils.isEmpty(item.lengthAverage)){
            helper.setText(R.id.re_length,item.lengthAverage);
            if (Double.parseDouble(item.lengthAverage) >= 28) {
                re_length.setTextColor(BaseApplication.getContextObject().getResources().getColor(R.color.red_translucent));
            } else {
                re_length.setTextColor(BaseApplication.getContextObject().getResources().getColor(R.color.drop_color));
            }
        }else{
            helper.setText(R.id.re_length,"--");
        }
        TextView re_break = helper.getView(R.id.re_break);
        if (!TextUtils.isEmpty(item.breakLoadAverage)){
            helper.setText(R.id.re_break,item.breakLoadAverage);
            if (Double.parseDouble(item.breakLoadAverage) >= 28) {
                re_break.setTextColor(BaseApplication.getContextObject().getResources().getColor(R.color.red_translucent));
            } else {
                re_break.setTextColor(BaseApplication.getContextObject().getResources().getColor(R.color.drop_color));
            }
        }else{
            helper.setText(R.id.breakLoadAverage,"--");
        }
        if (!TextUtils.isEmpty(item.micronAverage)){
            helper.setText(R.id.re_micr,item.micronAverage);
        }else{
            helper.setText(R.id.re_micr,"--");
        }
        if (!TextUtils.isEmpty(item.storage)){
            helper.setText(R.id.re_storg,item.storage);
        }else{
            helper.setText(R.id.re_storg,"--");
        }
    }
}
