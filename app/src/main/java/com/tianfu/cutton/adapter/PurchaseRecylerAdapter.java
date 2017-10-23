package com.tianfu.cutton.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tianfu.cutton.R;
import com.tianfu.cutton.model.ListPurchaseOrder;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/6/20.
 */

public class PurchaseRecylerAdapter extends BaseQuickAdapter<ListPurchaseOrder.ValueBean.RowsBean, BaseViewHolder> {

    private Button bt_purchase1;
    private Button bt_purchase2;
    private Button bt_purchase3;
    private TextView tv_purchase_commany;
    private TextView tv_purchase_money;

    public PurchaseRecylerAdapter(@LayoutRes int layoutResId, @Nullable List<ListPurchaseOrder.ValueBean.RowsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ListPurchaseOrder.ValueBean.RowsBean item) {
        AutoUtils.autoSize(helper.itemView);
        initview(helper);
        int minPrice = item.minPrice;

        if (minPrice == 0&&item.maxPrice==0) {
            helper.setText(R.id.tv_purchase_money, "价格面议");
            tv_purchase_commany.setVisibility(View.GONE);
        } else if (minPrice==item.maxPrice){
            tv_purchase_commany.setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_purchase_money, minPrice+"");
        }else{
            tv_purchase_commany.setVisibility(View.VISIBLE);
            helper.setText(R.id.tv_purchase_money, minPrice+"-"+item.maxPrice);
        }

        ;
        if (item.breakLoadAverage == null) {
            helper.setText(R.id.tv_purchase_elasticity, "24-31");
        } else {
            helper.setText(R.id.tv_purchase_elasticity, item.breakLoadAverage.min + "-" + item.breakLoadAverage.max);
            if (item.breakLoadAverage.min.equals(item.breakLoadAverage.max)){
                helper.setText(R.id.tv_purchase_elasticity, item.breakLoadAverage.min + "");
                if (item.breakLoadAverage.max.equals("31")){
                    helper.setText(R.id.tv_purchase_elasticity,"31及以上");
                }else if (item.breakLoadAverage.max.equals("24")){
                    helper.setText(R.id.tv_purchase_elasticity,"24及以下");
                }
            }
        }
        if (item.lengthAverage == null) {
            helper.setText(R.id.tv_purchase_length, "25-32");
        } else {
            helper.setText(R.id.tv_purchase_length, item.lengthAverage.min + "-" + item.lengthAverage.max);
            if (item.lengthAverage.min.equals(item.lengthAverage.max)){
                helper.setText(R.id.tv_purchase_length, item.lengthAverage.min + "");
                if (item.lengthAverage.max.equals("32")){
                    helper.setText(R.id.tv_purchase_length,"32及以上");
                }else if (item.lengthAverage.max.equals("25")){
                    helper.setText(R.id.tv_purchase_length,"25及以下");
                }
            }
        }
        if (item.micronAverage == null) {
            helper.setText(R.id.tv_purchase_horse, "3.4-5.0");
        } else {
            helper.setText(R.id.tv_purchase_horse, item.micronAverage.min + "-" + item.micronAverage.max);
            if (item.micronAverage.min.equals(item.micronAverage.max)){
                helper.setText(R.id.tv_purchase_horse, item.micronAverage.min + "");
                if (item.micronAverage.max.equals("5.0")){
                    helper.setText(R.id.tv_purchase_horse,"5.0及以上");
                }else if (item.micronAverage.max.equals("3.4")){
                    helper.setText(R.id.tv_purchase_horse,"3.4及以下");
                }
            }
        }
        if (item.colorGrade2 == null) {
            helper.setText(R.id.tv_purchase_cotton, "--");
        } else {
            helper.setText(R.id.tv_purchase_cotton, item.colorGrade2.get(0));
        }
        if (item.type2 == null) {
            helper.setText(R.id.tv_purchase_type, "--");
        } else {
            helper.setText(R.id.tv_purchase_type, item.type2.get(0));
        }
        if (item.origin2 == null) {
            helper.setText(R.id.tv_purchase_origin, "--");
        } else {
            helper.setText(R.id.tv_purchase_origin, item.origin2.get(0));
        }
        if (item.address2.equals("")) {
            helper.setText(R.id.tv_purchase_address, "--");
        }  else {
            helper.setText(R.id.tv_purchase_address, item.address2);
        }

        List<String> keyword = item.keyword;
        if (keyword.size() == 0) {
            bt_purchase1.setVisibility(View.GONE);
            bt_purchase2.setVisibility(View.GONE);
            bt_purchase3.setVisibility(View.GONE);
        } else if (keyword.size() == 1) {
            helper.setText(R.id.bt_purchase1, keyword.get(0));
            bt_purchase1.setVisibility(View.VISIBLE);
            bt_purchase2.setVisibility(View.GONE);
            bt_purchase3.setVisibility(View.GONE);
        } else if (keyword.size() == 2) {
            helper.setText(R.id.bt_purchase1, keyword.get(0));
            helper.setText(R.id.bt_purchase2, keyword.get(1));
            bt_purchase1.setVisibility(View.VISIBLE);
            bt_purchase2.setVisibility(View.VISIBLE);
            bt_purchase3.setVisibility(View.GONE);
        } else if (keyword.size() >= 3) {
            helper.setText(R.id.bt_purchase1, keyword.get(0));
            helper.setText(R.id.bt_purchase2, keyword.get(1));
            helper.setText(R.id.bt_purchase3, keyword.get(2));
            bt_purchase1.setVisibility(View.VISIBLE);
            bt_purchase2.setVisibility(View.VISIBLE);
            bt_purchase3.setVisibility(View.VISIBLE);
        }
    }

    private void initview(BaseViewHolder helper) {
        bt_purchase1 = helper.getView(R.id.bt_purchase1);
        bt_purchase2 = helper.getView(R.id.bt_purchase2);
        bt_purchase3 = helper.getView(R.id.bt_purchase3);

        tv_purchase_commany = helper.getView(R.id.tv_purchase_commany);
        tv_purchase_money = helper.getView(R.id.tv_purchase_money);
    }
}
