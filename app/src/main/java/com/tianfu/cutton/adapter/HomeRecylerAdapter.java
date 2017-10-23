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
import com.tianfu.cutton.model.PurchaseDynamicsBean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/20.
 */

public class HomeRecylerAdapter extends BaseQuickAdapter<PurchaseDynamicsBean.ValueBean, BaseViewHolder> {
    public HomeRecylerAdapter(@LayoutRes int layoutResId, @Nullable List<PurchaseDynamicsBean.ValueBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, PurchaseDynamicsBean.ValueBean item) {
        Button bt1 = helper.getView(R.id.bt1);
        Button bt2 = helper.getView(R.id.bt2);
        Button bt3 = helper.getView(R.id.bt3);
        if (item.keyword != null) {
            List<String> keyword = item.keyword;
            if (keyword.size() == 1) {
                helper.setText(R.id.bt1, keyword.get(0));
                bt1.setVisibility(View.VISIBLE);
                bt2.setVisibility(View.GONE);
                bt3.setVisibility(View.GONE);
            } else if (keyword.size() == 2) {
                helper.setText(R.id.bt1, keyword.get(0));
                helper.setText(R.id.bt2, keyword.get(1));
                bt2.setVisibility(View.VISIBLE);
                bt1.setVisibility(View.VISIBLE);
                bt3.setVisibility(View.GONE);
            } else if (keyword.size() >= 3) {
                bt2.setVisibility(View.VISIBLE);
                bt1.setVisibility(View.VISIBLE);
                bt3.setVisibility(View.VISIBLE);
                helper.setText(R.id.bt1, keyword.get(0));
                helper.setText(R.id.bt2, keyword.get(1));
                helper.setText(R.id.bt2, keyword.get(2));
            } else {
                bt1.setVisibility(View.GONE);
                bt2.setVisibility(View.GONE);
                bt3.setVisibility(View.GONE);
            }
        }
        TextView tv_purchase_commany = helper.getView(R.id.tv_purchase_commany);
        TextView tv_purchase_money = helper.getView(R.id.tv_purchase_money);
        if (item.minPrice == 0 && item.maxPrice == 0) {
            helper.setText(R.id.tv_purchase_money, "价格面议");
            tv_purchase_commany.setVisibility(View.GONE);
        } else if (item.minPrice == item.maxPrice) {
            tv_purchase_commany.setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_purchase_money, item.minPrice + "");
        } else {
            tv_purchase_commany.setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_purchase_money, item.minPrice + "-" + item.maxPrice);
        }
        if (item.breakLoadAverage == null) {
            helper.setText(R.id.tv_purchase_elasticity, "--");
        } else {
            helper.setText(R.id.tv_purchase_elasticity, item.breakLoadAverage.min + "-" + item.breakLoadAverage.max);
            if (item.breakLoadAverage.min.equals(item.breakLoadAverage.max)) {
                helper.setText(R.id.tv_purchase_elasticity, item.breakLoadAverage.min + "");
                if (item.breakLoadAverage.max.equals("31")) {
                    helper.setText(R.id.tv_purchase_elasticity, "31及以上");
                } else if (item.breakLoadAverage.max.equals("24")) {
                    helper.setText(R.id.tv_purchase_elasticity, "24及以下");
                }
            }
        }
        if (item.lengthAverage == null) {
            helper.setText(R.id.tv_purchase_length, "--");
        } else {
            helper.setText(R.id.tv_purchase_length, item.lengthAverage.min + "-" + item.lengthAverage.max);
            if (item.lengthAverage.min.equals(item.lengthAverage.max)) {
                helper.setText(R.id.tv_purchase_length, item.lengthAverage.min + "");
                if (item.lengthAverage.max.equals("32")) {
                    helper.setText(R.id.tv_purchase_length, "32及以上");
                } else if (item.lengthAverage.max.equals("25")) {
                    helper.setText(R.id.tv_purchase_length, "25及以下");
                }
            }
        }
        if (item.micronAverage == null) {
            helper.setText(R.id.tv_purchase_horse, "--");
        } else {
            helper.setText(R.id.tv_purchase_horse, item.micronAverage.min + "-" + item.micronAverage.max);
            if (item.micronAverage.min.equals(item.micronAverage.max)) {
                helper.setText(R.id.tv_purchase_horse, item.micronAverage.min + "");
                if (item.micronAverage.max.equals("5.0")) {
                    helper.setText(R.id.tv_purchase_horse, "5.0及以上");
                } else if (item.micronAverage.max.equals("3.4")) {
                    helper.setText(R.id.tv_purchase_horse, "3.4及以下");
                }
            }
        }
        if (!TextUtils.isEmpty(item.address2)) {
            helper.setText(R.id.tv_purchase_address, item.address2);
        } else {
            helper.setText(R.id.tv_purchase_address, "--");
        }
        if (!TextUtils.isEmpty(item.contacts)) {
            helper.setText(R.id.tv_supplyName, item.contacts);
        }else{
            helper.setText(R.id.tv_supplyName, "--");
        }
//        tv_supplyNum
        if (!TextUtils.isEmpty(item.supplyNum+"")) {
            helper.setText(R.id.tv_supplyNum, item.supplyNum+"人有供货意向");
        }else{
            helper.setText(R.id.tv_supplyNum, "--");
        }

    }
}
