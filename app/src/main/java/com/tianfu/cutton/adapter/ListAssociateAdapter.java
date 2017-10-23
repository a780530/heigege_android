package com.tianfu.cutton.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tianfu.cutton.R;
import com.tianfu.cutton.model.ListAssociatebean;

import java.util.List;

/**
 * Created by admin on 2017/8/1.
 */

public class ListAssociateAdapter extends BaseQuickAdapter<ListAssociatebean.ValueBean, BaseViewHolder> {

    public ListAssociateAdapter(@LayoutRes int layoutResId, @Nullable List<ListAssociatebean.ValueBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ListAssociatebean.ValueBean item) {
        if (item != null) {
            helper.setText(R.id.list_text,item.value);
        }
    }
}
