package com.tianfu.cutton.activity.quality;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.tianfu.cutton.R;
import com.tianfu.cutton.fragment.adapter.InquiryDetailsBundleAdpater;
import com.tianfu.cutton.fragment.quality.QualityBatachCountFragment;
import com.tianfu.cutton.fragment.quality.QualityBatchDetalisMessageFragment;
import com.tianfu.cutton.fragment.store.BagMessageFragment;
import com.zhy.autolayout.AutoLinearLayout;

import java.lang.reflect.Field;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BagDetailsActivity extends AppCompatActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tlMain)
    TabLayout tlMain;
    @BindView(R.id.iv_batch_share)
    ImageView ivBatchShare;
    @BindView(R.id.vpMain)
    ViewPager vpMain;
    @BindView(R.id.llgohome)
    AutoLinearLayout llgohome;
    @BindView(R.id.ll_call)
    AutoLinearLayout llCall;
    private InquiryDetailsBundleAdpater adapter;
    private ArrayList<String> titleList = new ArrayList<String>() {{
        add("信息");
        add("统计");
        add("包信息");
    }};
    private ArrayList<Fragment> fragmentList = new ArrayList<Fragment>() {{
        add(new QualityBatchDetalisMessageFragment());
        add(new QualityBatachCountFragment());
        add(new BagMessageFragment());
    }};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bag_details);
        ButterKnife.bind(this);
        adapter = new InquiryDetailsBundleAdpater(getSupportFragmentManager(), titleList, fragmentList);
        vpMain.setAdapter(adapter);
        tlMain.setupWithViewPager(vpMain);
        tlMain.post(new Runnable() {
            @Override
            public void run() {
                setIndicator(tlMain, 10, 10);
            }
        });
    }

    @OnClick({R.id.iv_back, R.id.iv_batch_share, R.id.llgohome, R.id.ll_call})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.iv_batch_share:

                break;
            case R.id.llgohome:

                break;
            case R.id.ll_call:

                break;
        }
    }
    public void setIndicator(TabLayout tabs, int leftDip, int rightDip) {
        Class<?> tabLayout = tabs.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayout.getDeclaredField("mTabStrip");
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        tabStrip.setAccessible(true);
        LinearLayout llTab = null;
        try {
            llTab = (LinearLayout) tabStrip.get(tabs);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        int left = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, leftDip, Resources.getSystem().getDisplayMetrics());
        int right = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, rightDip, Resources.getSystem().getDisplayMetrics());

        for (int i = 0; i < llTab.getChildCount(); i++) {
            View child = llTab.getChildAt(i);
            child.setPadding(0, 0, 0, 0);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
            params.leftMargin = left;
            params.rightMargin = right;
            child.setLayoutParams(params);
            child.invalidate();
        }

    }
}
