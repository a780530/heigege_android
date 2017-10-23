package com.tianfu.cutton.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tianfu.cutton.R;
import com.tianfu.cutton.model.HomeNewPriceBean;

import java.util.List;

/**
 * Created by admin on 2017/10/11.
 */

public class HomeNewPriceAdapter extends BaseQuickAdapter<HomeNewPriceBean, BaseViewHolder> {

    public HomeNewPriceAdapter(@LayoutRes int layoutResId, @Nullable List<HomeNewPriceBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeNewPriceBean item) {
        helper.setText(R.id.new_Location1, item.text1);
        helper.setText(R.id.new_Location2, item.text2);
        helper.setText(R.id.new_Location3, item.text3);
        helper.setText(R.id.new_Location4, item.text4);
        helper.setText(R.id.new_Location5, item.text5);
        helper.setText(R.id.new_Location6, item.text6);
        helper.setText(R.id.new_Location7, item.text7);
        helper.setText(R.id.new_Location8, item.text8);
        helper.setText(R.id.new_price1, item.text9);
        helper.setText(R.id.new_price2, item.text10);
        helper.setText(R.id.new_price3, item.text11);
        helper.setText(R.id.new_price4, item.text12);
        helper.setText(R.id.new_price5, item.text13);
        helper.setText(R.id.new_price6, item.text14);
        helper.setText(R.id.new_price7, item.text15);
        helper.setText(R.id.new_price8, item.text16);
    }
}
