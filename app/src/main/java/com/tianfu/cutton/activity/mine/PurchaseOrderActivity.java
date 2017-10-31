package com.tianfu.cutton.activity.mine;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tianfu.cutton.R;
import com.tianfu.cutton.activity.base.BaseActivity;
import com.tianfu.cutton.activity.base.BaseApplication;
import com.tianfu.cutton.activity.purchase.ReleasePurchaseActivity;
import com.tianfu.cutton.fragment.adapter.ContentPagerAdapter;
import com.tianfu.cutton.fragment.mine.PurchaseOrderFragment;
import com.tianfu.cutton.utils.CallKefu;
import com.tianfu.cutton.utils.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class PurchaseOrderActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_message)
    ImageView ivMessage;
    @BindView(R.id.tl_myPurchase)
    TabLayout tlMyPurchase;
    @BindView(R.id.vp_myPuechase)
    ViewPager vpMyPuechase;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.releasePurase)
    TextView releasePurase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase_order);
        ButterKnife.bind(this);
        List<String> list = new ArrayList<>();
        list.add("全部");
        list.add("采购中");
        list.add("已完成");
        list.add("已失效");
        list.add("已关闭");
        tvTitle.setText("我的采购单");
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(PurchaseOrderFragment.newInstance(1));
        fragments.add(PurchaseOrderFragment.newInstance(2));
        fragments.add(PurchaseOrderFragment.newInstance(3));
        fragments.add(PurchaseOrderFragment.newInstance(4));
        fragments.add(PurchaseOrderFragment.newInstance(5));
        vpMyPuechase.setAdapter(new ContentPagerAdapter(getSupportFragmentManager(), fragments));
        tlMyPurchase.setupWithViewPager(vpMyPuechase);

        tlMyPurchase.getTabAt(0).select();//设置选中第1个

        for (int i = 0; i < list.size(); i++) {
            tlMyPurchase.getTabAt(i).setText(list.get(i));
        }
    }

    @OnClick({R.id.iv_back, R.id.iv_message})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.iv_message:
                String eMUserName = SharedPreferencesUtil.getStringValue(BaseApplication.getContextObject(), "eMUserName");
                String eMPassword = SharedPreferencesUtil.getStringValue(BaseApplication.getContextObject(), "eMPassword");
                CallKefu.callOnLine(PurchaseOrderActivity.this, eMUserName, eMPassword);
                break;
        }
    }

    @OnClick(R.id.releasePurase)
    public void onViewClicked() {
        startActivity(new Intent(this, ReleasePurchaseActivity.class));
    }
}
