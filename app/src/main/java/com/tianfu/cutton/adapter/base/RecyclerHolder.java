package com.tianfu.cutton.adapter.base;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by xiaohei on 2017/3/28.
 */

public class RecyclerHolder extends RecyclerView.ViewHolder{

    public View rootView;

    public RecyclerHolder(View itemView) {
        super(itemView);
        rootView = itemView;
    }
}
