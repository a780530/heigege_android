package com.tianfu.cutton.adapter;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.tianfu.cutton.R;
import com.tianfu.cutton.activity.base.BaseApplication;
import com.tianfu.cutton.model.StoreBean;
import com.tianfu.cutton.utils.StringJudgeUtils;

import java.util.List;

/**
 * Created by Administrator on 2017/6/20.
 */

public class StoreRecylerAdapter extends BaseQuickAdapter<StoreBean.ValueBean.RowsBean, BaseViewHolder> {

    public StoreRecylerAdapter(@LayoutRes int layoutResId, @Nullable List<StoreBean.ValueBean.RowsBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, StoreBean.ValueBean.RowsBean item) {
        helper.setText(R.id.tv_store_code, item.code + "(" + item.bagCount + "/" + item.batchCount + ")");
        Button button = helper.getView(R.id.bt_store_key);
        TextView tv_store_money = helper.getView(R.id.tv_store_money);
        ImageView imageView = helper.getView(R.id.ivIsCheap);
        if (TextUtils.isEmpty(item.isCheap)){
            imageView.setVisibility(View.GONE);
        }else{
            if (item.isCheap.equals("0")){
                imageView.setVisibility(View.GONE);
            }else{
                imageView.setVisibility(View.VISIBLE);
            }

        }
        if (item.batchType.equals("1")) {
            button.setVisibility(View.GONE);
        } else if (item.property != null && !"".equals(item.property)) {
            button.setVisibility(View.VISIBLE);
            helper.setText(R.id.bt_store_key, item.property);
        } else {
            button.setVisibility(View.GONE);
        }
        TextView textView = helper.getView(R.id.tv_store_commany);
        if (!TextUtils.isEmpty(item.stdweightPrice)) {
            helper.setText(R.id.tv_store_money, item.stdweightPrice);
            textView.setVisibility(View.VISIBLE);
        } else {
            textView.setVisibility(View.GONE);
            helper.setText(R.id.tv_store_money, "价格面议");
        }

        TextView tv_store_length = helper.getView(R.id.tv_store_length);
        if (item.lengthAverage != null && !"".equals(item.lengthAverage)) {
            helper.setText(R.id.tv_store_length, item.lengthAverage);
            if (Double.parseDouble(item.lengthAverage) >= 28) {
                tv_store_length.setTextColor(BaseApplication.getContextObject().getResources().getColor(R.color.red_translucent));
            } else {
                tv_store_length.setTextColor(BaseApplication.getContextObject().getResources().getColor(R.color.drop_color));
            }
        } else {
            helper.setText(R.id.tv_store_length, "--");
        }
        TextView tv_store_strong = helper.getView(R.id.tv_store_strong);
        if (item.breakLoadAverage != null && !"".equals(item.breakLoadAverage)) {
            helper.setText(R.id.tv_store_strong, item.breakLoadAverage);
            if (Double.parseDouble(item.breakLoadAverage) >= 28) {
                tv_store_strong.setTextColor(BaseApplication.getContextObject().getResources().getColor(R.color.red_translucent));
            } else {
                tv_store_strong.setTextColor(BaseApplication.getContextObject().getResources().getColor(R.color.drop_color));
            }
        } else {
            helper.setText(R.id.tv_store_strong, "--");
        }
        if (item.micronAverage != null && !"".equals(item.micronAverage)) {
            helper.setText(R.id.tv_store_horse, item.micronAverage);
        } else {
            helper.setText(R.id.tv_store_horse, "--");
        }
        if (item.type != null && !"".equals(item.type)) {
            helper.setText(R.id.tv_store_type, item.type);
        } else {
            helper.setText(R.id.tv_store_type, "--");
        }
        TextView view = helper.getView(R.id.tv_store_range);
        if (!TextUtils.isEmpty(item.isRule)){
            if (item.isRule.equals("0")){
                helper.setText(R.id.tv_store_range, "--");
                view.setTextColor(BaseApplication.getContextObject().getResources().getColor(R.color.drop_color));
            }else{
                StringJudgeUtils.judgeStringSts(item.sts,view);
            }
        }else{
            view.setTextColor(BaseApplication.getContextObject().getResources().getColor(R.color.drop_color));
            helper.setText(R.id.tv_store_range, "--");
        }
      /*  if (item.sts != null && !"".equals(item.sts)) {
            helper.setText(R.id.tv_store_range, item.sts + "km");
        } else {
            helper.setText(R.id.tv_store_range, "--");
        }*/
        if (item.updateTime != null && !"".equals(item.updateTime)) {
            String[] split = item.updateTime.split(" ");
            helper.setText(R.id.tv_store_date, split[0]);
        } else {
            helper.setText(R.id.tv_store_date, "--");
        }

        if (item.storage != null && !"".equals(item.storage)) {
            helper.setText(R.id.tv_store_fatory, item.storage);
        } else {
            helper.setText(R.id.tv_store_fatory, "--");
        }
    }
}
