package com.tianfu.cutton.activity.mine;

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
import com.tianfu.cutton.fragment.adapter.ContentPagerAdapter;
import com.tianfu.cutton.fragment.mine.SupplyOrderFragment;
import com.tianfu.cutton.utils.CallKefu;
import com.tianfu.cutton.utils.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/*
* 我的供货单
* */
public class SupplyOrderActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.iv_message)
    ImageView ivMessage;
    @BindView(R.id.tl_mySupply)
    TabLayout tlMySupply;
    @BindView(R.id.vp_mySupply)
    ViewPager vpMySupply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_supply_order);
        ButterKnife.bind(this);
        List<String> list = new ArrayList<>();
        list.add("全部");
        list.add("未供货");
        list.add("已供货");
        tvTitle.setText("我的供货单");
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(SupplyOrderFragment.newInstance(1));
        fragments.add(SupplyOrderFragment.newInstance(2));
        fragments.add(SupplyOrderFragment.newInstance(3));
        vpMySupply.setAdapter(new ContentPagerAdapter(getSupportFragmentManager(), fragments));
        tlMySupply.setupWithViewPager(vpMySupply);

        tlMySupply.getTabAt(0).select();//设置选中第1个

        for (int i = 0; i < list.size(); i++) {
            tlMySupply.getTabAt(i).setText(list.get(i));
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
                CallKefu.callOnLine(SupplyOrderActivity.this, eMUserName, eMPassword);
                break;
        }
    }
}
