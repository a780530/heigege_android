package com.tianfu.cutton.adapter.viewholder;

import android.view.View;
import android.widget.Button;

import com.tianfu.cutton.R;
import com.tianfu.cutton.adapter.base.RecyclerHolder;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by admin on 2017/6/30.
 */

public class PurchaseOrderdetailsHolder extends RecyclerHolder {

    private final Button button;

    public PurchaseOrderdetailsHolder(View itemView) {
        super(itemView);
        AutoUtils.autoSize(itemView);
        button = (Button) itemView.findViewById(R.id.bt_guanjianci);
    }
}
