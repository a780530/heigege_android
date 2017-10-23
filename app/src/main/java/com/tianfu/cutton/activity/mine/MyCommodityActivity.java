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
import com.tianfu.cutton.fragment.mine.MycommodityFragment;
import com.tianfu.cutton.utils.CallKefu;
import com.tianfu.cutton.utils.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyCommodityActivity extends BaseActivity {
    /*
    * 我的商品页
    * */
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.iv_message)
    ImageView ivMessage;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_commodity);
        ButterKnife.bind(this);
        tabLayout = (TabLayout) findViewById(R.id.tl_myCommodity);
        viewPager = (ViewPager) findViewById(R.id.vp_myCommodity);
        tvTitle.setText("我的商品");
        List<String> list = new ArrayList<>();
        list.add("全部");
        list.add("未上架");
        list.add("已上架");
        list.add("已出售");
        list.add("已消耗");
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(MycommodityFragment.newInstance(1));
        fragments.add(MycommodityFragment.newInstance(2));
        fragments.add(MycommodityFragment.newInstance(3));
        fragments.add(MycommodityFragment.newInstance(4));
        fragments.add(MycommodityFragment.newInstance(5));
        viewPager.setAdapter(new ContentPagerAdapter(getSupportFragmentManager(), fragments));
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).select();//设置选中第1个
        for (int i = 0; i < list.size(); i++) {
            tabLayout.getTabAt(i).setText(list.get(i));
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
                CallKefu.callOnLine(MyCommodityActivity.this, eMUserName, eMPassword);
                break;
        }
    }
}
