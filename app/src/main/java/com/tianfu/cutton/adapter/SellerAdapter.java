package com.tianfu.cutton.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tianfu.cutton.R;
import com.tianfu.cutton.model.SellerBean;

import java.util.List;

/**
 * Created by admin on 2017/10/16.
 */

public class SellerAdapter extends BaseQuickAdapter<SellerBean.ValueBean, BaseViewHolder> {

    public SellerAdapter(@LayoutRes int layoutResId, @Nullable List<SellerBean.ValueBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SellerBean.ValueBean item) {
        if (TextUtils.isEmpty(item.companyName)) {
            helper.setText(R.id.companyNameHome, "");
        } else {
            helper.setText(R.id.companyNameHome, item.companyName);
        }
        if (TextUtils.isEmpty(item.resourceNum)) {
            helper.setText(R.id.resourceNumHome, "");
        } else {
            helper.setText(R.id.resourceNumHome, item.resourceNum
                    + "批");
        }
    }
}
