package com.tianfu.cutton;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tianfu.cutton.activity.base.BaseActivity;
import com.tianfu.cutton.activity.base.BaseApplication;
import com.tianfu.cutton.activity.login.LoginActivity;
import com.tianfu.cutton.common.Common;
import com.tianfu.cutton.fragment.adapter.BackHandlerHelper;
import com.tianfu.cutton.fragment.home.HomeFragment;
import com.tianfu.cutton.fragment.inquiry.PurchaseFragment;
import com.tianfu.cutton.fragment.mine.MineFragment;
import com.tianfu.cutton.fragment.quality.QualityFragment;
import com.tianfu.cutton.fragment.store.StoreFragment;
import com.tianfu.cutton.model.IsLogin;
import com.tianfu.cutton.net.CallBack;
import com.tianfu.cutton.net.HttpManager;
import com.tianfu.cutton.utils.PrefManager;
import com.tianfu.cutton.utils.SharedPreferencesUtil;
import com.tianfu.cutton.widget.NoScrollViewPager;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.PermissionListener;

import java.util.HashMap;
import java.util.List;

import static com.tianfu.cutton.activity.base.BaseApplication.getContextObject;

public class MainActivity extends BaseActivity {
    public TabLayout mTabLayout;
    private static String versionNo;
    //Tab 文字
    private final int[] TAB_TITLES = new int[]{R.string.home, R.string.store, R.string.quality, R.string.purchase, R.string.mine};
    //Tab 图片
    private final int[] TAB_IMGS = new int[]{R.drawable.tab_home_selector, R.drawable.tab_store_selector, R.drawable.tab_purchase_selector, R.drawable.tab_quality_selector, R.drawable.tab_mine_selector};
    //Fragment 数组
    private final Fragment[] TAB_FRAGMENTS = new Fragment[]{new HomeFragment(), new StoreFragment(), new PurchaseFragment(), new QualityFragment(), new MineFragment()};
    //Tab 数目
    private final int COUNT = TAB_TITLES.length;
    private MyViewPagerAdapter mAdapter;
    public NoScrollViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AndPermission.with(this)
                .permission(Manifest.permission.READ_PHONE_STATE, Manifest.permission.CAMERA)
                .callback(listener)
                .start();
    }

    private PermissionListener listener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantedPermissions) {
            // 权限申请成功回调。
            // 这里的requestCode就是申请时设置的requestCode。
            // 和onActivityResult()的requestCode一样，用来区分多个不同的请求。
            String deviceId = getDeviceId();
            System.out.println("deviceId:"+deviceId);
            SharedPreferencesUtil.saveStringValue(BaseApplication.getContextObject(),"deviceNo",deviceId);
            //这里存的设备号和版本号
            String versionNo = getVersionNo();
            SharedPreferencesUtil.saveStringValue(BaseApplication.getContextObject(),"versionNo",versionNo);
            isLogin();
            initViews();
            Intent intent = getIntent();
            String fragmentid = intent.getStringExtra("fragmentid");
            String Login = intent.getStringExtra("Login");
            if (fragmentid != null && fragmentid.equals("2")) {
                mViewPager.setCurrentItem(2);
            } else if ("1".equals(fragmentid)) {
                mViewPager.setCurrentItem(1);
            } else if ("4".equals(fragmentid)) {
                mViewPager.setCurrentItem(4);
                if (Login != null && !"".equals(Login)) {
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
            }
        }

        @Override
        public void onFailed(int requestCode, List<String> deniedPermissions) {
            if (AndPermission.hasAlwaysDeniedPermission(MainActivity.this, deniedPermissions)) {
                // 第一种：用AndPermission默认的提示语。
                AndPermission.defaultSettingDialog(MainActivity.this, 400).show();
            } else {
                finish();
            }
        }
    };

    private void isLogin() {
        HashMap<String, String> params = new HashMap<>();
        params.put("deviceNo", Common.deviceNo);
        params.put("from", Common.from);
        params.put("version", Common.versionNo);
        //100 ：没有登陆；200：异地登录 300:冻结
        HttpManager.getServerApi().isLogin(params).enqueue(new CallBack<IsLogin>() {
            @Override
            public void success(IsLogin data) {
                PrefManager prefManager = new PrefManager(MainActivity.this);
                boolean firstTimeLaunch = prefManager.isFirstTimeLaunch();
                if (data.success) {
                    if (firstTimeLaunch) {
                        SharedPreferencesUtil.saveBooleanValue(MainActivity.this, "isLogin", false);
                    } else {
                        boolean isLogin = data.success;
                        SharedPreferencesUtil.saveBooleanValue(MainActivity.this, "isLogin", isLogin);
                    }
                } else {
                    boolean isLogin = data.success;
                    SharedPreferencesUtil.saveBooleanValue(MainActivity.this, "isLogin", isLogin);
                }
                prefManager.setFirstTimeLaunch(false);
            }

            @Override
            public void failure(ErrorType type, int httpCode) {

            }
        });
    }

    /*
  * 获取设备id
  * */
    public static String getDeviceId() {
        TelephonyManager tm = (TelephonyManager) BaseApplication.getContextObject().getSystemService(Context.TELEPHONY_SERVICE);
        String deviceId = tm.getDeviceId();
        if (deviceId == null) {
            return "-";
        } else {
            return deviceId;
        }
    }

    public static String getVersionNo() {
        try {
            PackageManager manager = getContextObject().getPackageManager();
            PackageInfo info = manager.getPackageInfo(getContextObject().getPackageName(), 0);
            versionNo = info.versionName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return versionNo;
    }

    private void initViews() {
        mTabLayout = (TabLayout) findViewById(R.id.tablayout);
        setTabs(mTabLayout, this.getLayoutInflater(), TAB_TITLES, TAB_IMGS);

        mAdapter = new MyViewPagerAdapter(getSupportFragmentManager());
        mViewPager = (NoScrollViewPager) findViewById(R.id.viewpager);
        mViewPager.setNoScroll(true);
        mViewPager.setAdapter(mAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.setOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
    }

    /**
     * @description: 设置添加Tab
     */
    private void setTabs(TabLayout tabLayout, LayoutInflater inflater, int[] tabTitlees, int[] tabImgs) {
        for (int i = 0; i < tabImgs.length; i++) {
            TabLayout.Tab tab = tabLayout.newTab();
            View view = inflater.inflate(R.layout.tab_custom, null);
            tab.setCustomView(view);

            TextView tvTitle = (TextView) view.findViewById(R.id.tv_tab);
            tvTitle.setText(tabTitlees[i]);
            ImageView imgTab = (ImageView) view.findViewById(R.id.img_tab);
            imgTab.setImageResource(tabImgs[i]);
            tabLayout.addTab(tab);

        }
    }

    /**
     * @description: ViewPager 适配器
     */
    private class MyViewPagerAdapter extends FragmentPagerAdapter {
        public MyViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return TAB_FRAGMENTS[position];
        }

        @Override
        public int getCount() {
            return COUNT;
        }
    }

    @Override
    public void onBackPressed() {
        if (!BackHandlerHelper.handleBackPress(this)) {
            super.onBackPressed();
        }
    }


}