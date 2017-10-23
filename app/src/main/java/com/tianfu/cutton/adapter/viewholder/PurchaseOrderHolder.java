package com.tianfu.cutton.adapter.viewholder;

import android.view.View;

import com.tianfu.cutton.adapter.base.RecyclerHolder;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by admin on 2017/6/30.
 */

public class PurchaseOrderHolder extends RecyclerHolder {
    public PurchaseOrderHolder(View itemView) {
        super(itemView);
        AutoUtils.autoSize(itemView);
    }
}
