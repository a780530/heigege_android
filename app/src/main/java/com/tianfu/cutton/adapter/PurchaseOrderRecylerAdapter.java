package com.tianfu.cutton.adapter;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tianfu.cutton.R;
import com.tianfu.cutton.activity.base.BaseApplication;
import com.tianfu.cutton.model.PurchaseOrderBySelfBean;
import com.zhy.autolayout.AutoLinearLayout;
import com.zhy.autolayout.utils.AutoUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/6/20.
 */

public class PurchaseOrderRecylerAdapter extends BaseQuickAdapter<PurchaseOrderBySelfBean.ValueBean.RowsBean, BaseViewHolder> {
    private AutoLinearLayout llButton;
    private TextView tvCompany;
    private TextView tvMoney;
    private Button btEdit;
    private Button btClose;
    private Button btFinish;
    private Button btFirst;
    private Button btSecond;
    private Button btThird;

    public PurchaseOrderRecylerAdapter(int layoutResId, List<PurchaseOrderBySelfBean.ValueBean.RowsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder viewHolder, PurchaseOrderBySelfBean.ValueBean.RowsBean item) {
        AutoUtils.autoSize(viewHolder.itemView);
        initView(viewHolder);
        viewHolder.addOnClickListener(R.id.bt_edit)
                .addOnClickListener(R.id.bt_close)
                .addOnClickListener(R.id.bt_finish);

        String staus = item.purchaseStatus;
        int moneyMin = item.minPrice;
        int moneyMax = item.maxPrice;
        TextView tv_staus = viewHolder.getView(R.id.tv_staus);
        if (staus.equals("Purchasing")) {
            tv_staus.setTextColor(BaseApplication.getContextObject().getResources().getColor(R.color.tab_tv_selected));
            llButton.setVisibility(View.VISIBLE);
            btClose.setVisibility(View.VISIBLE);
            btFinish.setVisibility(View.VISIBLE);
        } else if (staus.equals("Close") || staus.equals("Invalid")) {
            tv_staus.setTextColor(BaseApplication.getContextObject().getResources().getColor(R.color.drop_color));
            llButton.setVisibility(View.VISIBLE);
            btClose.setVisibility(View.GONE);
            btFinish.setVisibility(View.GONE);
        } else {
            tv_staus.setTextColor(BaseApplication.getContextObject().getResources().getColor(R.color.drop_color));
            llButton.setVisibility(View.GONE);
        }
        if (moneyMin == 0 && moneyMax == 0) {
            tvMoney.setText("价格面议");
            tvCompany.setVisibility(View.GONE);
        } else {
            tvCompany.setVisibility(View.VISIBLE);
            if (moneyMin == moneyMax) {
                viewHolder.setText(R.id.tv_money, moneyMin + "");
            } else {

                viewHolder.setText(R.id.tv_money, moneyMin + "-" + moneyMax);
            }

        }

        viewHolder.setText(R.id.tv_Numbers, item.purchaseSn)
                .setText(R.id.tv_staus, item.purchaseStatusName)
        ;
        if (item.breakLoadAverage == null) {
            viewHolder.setText(R.id.tv_elasticity, "25.6-27.8");
        } else {
            viewHolder.setText(R.id.tv_elasticity, item.breakLoadAverage.min + "-" + item.breakLoadAverage.max);
            if ((item.breakLoadAverage.max + "").equals(item.breakLoadAverage.min+"")) {
                viewHolder.setText(R.id.tv_elasticity, item.breakLoadAverage.min+"");
                if ((item.breakLoadAverage.max + "").equals("24")) {
                    viewHolder.setText(R.id.tv_elasticity, "24及以下");
                } else if ((item.breakLoadAverage.max + "").equals("31")) {
                    viewHolder.setText(R.id.tv_elasticity, "31及以上");
                }
            }
        }
        if (item.lengthAverage == null) {
            viewHolder.setText(R.id.tv_length, "27-29");
        } else {
            viewHolder.setText(R.id.tv_length, item.lengthAverage.min + "-" + item.lengthAverage.max);
            if ((item.lengthAverage.max + "").equals(item.lengthAverage.min+"")) {
                viewHolder.setText(R.id.tv_length, item.lengthAverage.min+"");
                if ((item.lengthAverage.max + "").equals("25")) {
                    viewHolder.setText(R.id.tv_length, "25及以下");
                } else if ((item.lengthAverage.max + "").equals("32")) {
                    viewHolder.setText(R.id.tv_length, "32及以上");
                }
            }

        }
        if (item.micronAverage == null) {
            viewHolder.setText(R.id.tv_horse, "3.4-5.0");
        } else {
            viewHolder.setText(R.id.tv_horse, item.micronAverage.min + "-" + item.micronAverage.max);
            if ((item.micronAverage.max + "").equals(item.micronAverage.min+"")) {
                viewHolder.setText(R.id.tv_horse, item.micronAverage.min+"");
                if ((item.micronAverage.max + "").equals("3.4")) {
                    viewHolder.setText(R.id.tv_horse, "3.4及以下");
                } else if ((item.micronAverage.max + "").equals("5.0")) {
                    viewHolder.setText(R.id.tv_horse, "5.0及以上");
                }
            }
        }
        if (item.colorGrade2 == null) {
            viewHolder.setText(R.id.tv_cotton, "--");
        } else {
            viewHolder.setText(R.id.tv_cotton, item.colorGrade2.get(0));
        }
        if (item.type2 == null) {
            viewHolder.setText(R.id.tv_type, "--");
        } else {
            viewHolder.setText(R.id.tv_type, item.type2.get(0));
        }
        if (item.origin2 == null) {
            viewHolder.setText(R.id.tv_origin, "--");
        } else {
            viewHolder.setText(R.id.tv_origin, item.origin2.get(0));
        }

        if (item.address != null && item.address.equals("") && item.province == null) {
            viewHolder.setText(R.id.tv_factory, "--");
        } else if (item.province == null) {
            viewHolder.setText(R.id.tv_factory, item.address);
        } else {
            viewHolder.setText(R.id.tv_factory, item.address2);
        }

        List<String> keyword = item.keyword;
        if (keyword != null) {
            if (keyword.size() == 0) {
                btFirst.setVisibility(View.GONE);
                btSecond.setVisibility(View.GONE);
                btThird.setVisibility(View.GONE);
            } else if (keyword.size() == 1) {
                viewHolder.setText(R.id.bt_First, keyword.get(0));
                btFirst.setVisibility(View.VISIBLE);
                btSecond.setVisibility(View.GONE);
                btThird.setVisibility(View.GONE);
            } else if (keyword.size() == 2) {
                viewHolder.setText(R.id.bt_First, keyword.get(0));
                viewHolder.setText(R.id.bt_Second, keyword.get(1));
                btFirst.setVisibility(View.VISIBLE);
                btSecond.setVisibility(View.VISIBLE);
                btThird.setVisibility(View.GONE);
            } else if (keyword.size() >= 3) {
                viewHolder.setText(R.id.bt_First, keyword.get(0));
                viewHolder.setText(R.id.bt_Second, keyword.get(1));
                viewHolder.setText(R.id.bt_Third, keyword.get(2));
                btFirst.setVisibility(View.VISIBLE);
                btSecond.setVisibility(View.VISIBLE);
                btThird.setVisibility(View.VISIBLE);
            }
        } else {
            btFirst.setVisibility(View.GONE);
            btSecond.setVisibility(View.GONE);
            btThird.setVisibility(View.GONE);
        }
    }

    private void initView(BaseViewHolder viewHolder) {
        llButton = viewHolder.getView(R.id.ll_button);
        tvCompany = viewHolder.getView(R.id.tv_Company);
        tvMoney = viewHolder.getView(R.id.tv_money);

        btEdit = viewHolder.getView(R.id.bt_edit);
        btClose = viewHolder.getView(R.id.bt_close);
        btFinish = viewHolder.getView(R.id.bt_finish);

        btFirst = viewHolder.getView(R.id.bt_First);
        btSecond = viewHolder.getView(R.id.bt_Second);
        btThird = viewHolder.getView(R.id.bt_Third);

    }
}
