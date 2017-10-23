package com.tianfu.cutton.adapter;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tianfu.cutton.R;
import com.tianfu.cutton.model.ListSupplierByPurchaseIdBean;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/6/20.
 */

public class SupplierRecylerAdapter extends BaseQuickAdapter<ListSupplierByPurchaseIdBean.ValueBean.RowsBean, BaseViewHolder> {


    public SupplierRecylerAdapter(int layoutResId, List<ListSupplierByPurchaseIdBean.ValueBean.RowsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ListSupplierByPurchaseIdBean.ValueBean.RowsBean item) {
        AutoUtils.autoSize(helper.itemView);
        if (item.equals("") || item == null) {
            return;
        } else {
            helper.setText(R.id.tv_contacts, item.contacts)
                    .addOnClickListener(R.id.tv_callMobile)
                    .setText(R.id.tv_mobile, item.mobile);
            if (item.remark==null){
                helper.setText(R.id.tv_remark, "---");
            }else{
                helper.setText(R.id.tv_remark, item.remark);
            }
        }

        ;


    }
}
