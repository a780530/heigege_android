package com.tianfu.cutton.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tianfu.cutton.R;
import com.tianfu.cutton.activity.base.BaseApplication;
import com.tianfu.cutton.model.ListSupplyOrderBySelfBean;

import java.util.List;

/**
 * Created by Administrator on 2017/6/20.
 */

public class SupplyOrderAdapter extends BaseQuickAdapter<ListSupplyOrderBySelfBean.ValueBean.RowsBean, BaseViewHolder> {


    private LinearLayout ll_staus;
    private ImageView iv_staus;
    private boolean invalidStatus;
    private TextView supply_money;
    private TextView supply_money_danwei;
    private Button bt_suppliF;
    private Button bt_suppliS;
    private Button bt_suppliT;

    public SupplyOrderAdapter(@LayoutRes int layoutResId, @Nullable List<ListSupplyOrderBySelfBean.ValueBean.RowsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ListSupplyOrderBySelfBean.ValueBean.RowsBean item) {
        initView(helper);
        helper.setText(R.id.purchaseSn_supply, "采购单号：" + item.purchaseSn)
                .addOnClickListener(R.id.sure_supply)
                .setText(R.id.tv_supply_purchaseStatusName, item.supplyStatusName);
        TextView tv_supply_purchaseStatusName = helper.getView(R.id.tv_supply_purchaseStatusName);
        String staus = item.supplyStatus;
        System.out.println("supplyStatus" + staus);
        invalidStatus = item.invalidStatus;
        System.out.println("invalidStatus:" + invalidStatus);
        if (staus.equals("NotSupply")) {
            tv_supply_purchaseStatusName.setTextColor(BaseApplication.getContextObject().getResources().getColor(R.color.tab_tv_selected));
            ll_staus.setVisibility(View.VISIBLE);
        } else {
            tv_supply_purchaseStatusName.setTextColor(BaseApplication.getContextObject().getResources().getColor(R.color.drop_color));
            ll_staus.setVisibility(View.GONE);
        }
        if (invalidStatus == true) {
            iv_staus.setVisibility(View.VISIBLE);
        } else {
            iv_staus.setVisibility(View.GONE);
        }
        int min = item.minPrice;
        int max = item.maxPrice;
        if (min == 0 && max == 0) {
            supply_money_danwei.setVisibility(View.GONE);
            supply_money.setText("价格面议");
        } else {
            if (min == max) {
                helper.setText(R.id.supply_money, min + "");
            } else {
                supply_money_danwei.setVisibility(View.VISIBLE);
                helper.setText(R.id.supply_money, min + "-" + max);
            }
        }

        if (item.breakLoadAverage == null) {
            helper.setText(R.id.tv_supply_sto, "25.6-27.8");
        } else {
            helper.setText(R.id.tv_supply_sto, item.breakLoadAverage.min + "-" + item.breakLoadAverage.max);
            if (item.breakLoadAverage.min.equals(item.breakLoadAverage.max)) {
                helper.setText(R.id.tv_supply_sto, item.breakLoadAverage.min + "");
                if (item.breakLoadAverage.max.equals("24")) {
                    helper.setText(R.id.tv_supply_sto, "24及以下");
                } else if (item.breakLoadAverage.max.equals("31")) {
                    helper.setText(R.id.tv_supply_sto, "31及以上");
                }
            }
        }
        if (item.lengthAverage == null) {
            helper.setText(R.id.supply_long, "27-29");
        } else {
            helper.setText(R.id.supply_long, item.lengthAverage.min + "-" + item.lengthAverage.max);
            if (item.lengthAverage.min.equals(item.lengthAverage.max)) {
                helper.setText(R.id.supply_long, item.lengthAverage.min + "");
                if (item.lengthAverage.max.equals("25")) {
                    helper.setText(R.id.supply_long, "25及以下");
                } else if (item.lengthAverage.max.equals("32")) {
                    helper.setText(R.id.supply_long, "32及以上");
                }
            }
        }
        if (item.micronAverage == null) {
            helper.setText(R.id.supply_horse, "3.4-5");
        } else {
            helper.setText(R.id.supply_horse, item.micronAverage.min + "-" + item.micronAverage.max);
            if (item.micronAverage.min.equals(item.micronAverage.max)) {
                helper.setText(R.id.supply_horse, item.micronAverage.min + "");
                if (item.micronAverage.max.equals("3.4")) {
                    helper.setText(R.id.supply_horse, "3.4及以下");
                } else if (item.micronAverage.max.equals("5.0")) {
                    helper.setText(R.id.supply_horse, "5.0及以上");
                }
            }
        }

        if (item.colorGrade2 == null) {
            helper.setText(R.id.tv_suppl_cotton, "-");
        } else {
            helper.setText(R.id.tv_suppl_cotton, item.colorGrade2.get(0));
        }
        if (item.type2 == null) {
            helper.setText(R.id.tv_suppl_type, "-");
        } else {
            helper.setText(R.id.tv_suppl_type, item.type2.get(0));
        }
        if (item.origin2 == null) {
            helper.setText(R.id.tv_suppl_origin, "-");
        } else {
            helper.setText(R.id.tv_suppl_origin, item.origin2.get(0));
        }

        if (item.address == null && item.province == null) {
            helper.setText(R.id.tv_supply_address, "暂无信息");
        } else if (item.province == null) {
            helper.setText(R.id.tv_supply_address, item.address);
        } else {
            helper.setText(R.id.tv_supply_address, item.province + item.city + item.area + item.address);
        }
        List<String> keyword = item.keyword;
        if (keyword != null) {
            if (keyword.size() == 0) {
                bt_suppliF.setVisibility(View.GONE);
                bt_suppliS.setVisibility(View.GONE);
                bt_suppliT.setVisibility(View.GONE);
            } else if (keyword.size() == 1) {
                helper.setText(R.id.bt_suppliF, keyword.get(0));
                bt_suppliF.setVisibility(View.VISIBLE);
                bt_suppliS.setVisibility(View.GONE);
                bt_suppliT.setVisibility(View.GONE);
            } else if (keyword.size() == 2) {
                helper.setText(R.id.bt_suppliF, keyword.get(0));
                helper.setText(R.id.bt_suppliS, keyword.get(1));
                bt_suppliF.setVisibility(View.VISIBLE);
                bt_suppliS.setVisibility(View.VISIBLE);
                bt_suppliT.setVisibility(View.GONE);
            } else if (keyword.size() >= 3) {
                helper.setText(R.id.bt_suppliF, keyword.get(0));
                helper.setText(R.id.bt_suppliS, keyword.get(1));
                helper.setText(R.id.bt_suppliT, keyword.get(2));
                bt_suppliF.setVisibility(View.VISIBLE);
                bt_suppliS.setVisibility(View.VISIBLE);
                bt_suppliT.setVisibility(View.VISIBLE);
            }
        } else {
            bt_suppliF.setVisibility(View.GONE);
            bt_suppliS.setVisibility(View.GONE);
            bt_suppliT.setVisibility(View.GONE);
        }

    }

    private void initView(BaseViewHolder helper) {
        ll_staus = helper.getView(R.id.ll_staus);
        iv_staus = helper.getView(R.id.iv_staus);
        supply_money = helper.getView(R.id.supply_money);
        supply_money_danwei = helper.getView(R.id.supply_money_danwei);
        bt_suppliF = helper.getView(R.id.bt_suppliF);
        bt_suppliS = helper.getView(R.id.bt_suppliS);
        bt_suppliT = helper.getView(R.id.bt_suppliT);
    }
}
