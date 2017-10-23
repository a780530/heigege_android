package com.tianfu.cutton.adapter.viewholder;

import android.view.View;

import com.tianfu.cutton.adapter.base.RecyclerHolder;
import com.zhy.autolayout.utils.AutoUtils;

/**
 * Created by Administrator on 2017/6/20.
 */

public class HomeRecylerHolder extends RecyclerHolder  {


    public HomeRecylerHolder(View itemView ) {
        super(itemView);
        AutoUtils.autoSize(itemView);
    }


}
