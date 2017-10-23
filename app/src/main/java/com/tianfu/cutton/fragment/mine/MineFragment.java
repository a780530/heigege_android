package com.tianfu.cutton.fragment.mine;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hyphenate.chat.ChatClient;
import com.hyphenate.helpdesk.model.ContentFactory;
import com.hyphenate.helpdesk.model.VisitorTrack;
import com.tianfu.cutton.R;
import com.tianfu.cutton.activity.base.BaseApplication;
import com.tianfu.cutton.activity.base.BaseFragment;
import com.tianfu.cutton.activity.login.LoginActivity;
import com.tianfu.cutton.activity.mine.MyCollectionActivity;
import com.tianfu.cutton.activity.mine.MyCommodityActivity;
import com.tianfu.cutton.activity.mine.MyListHistoryActivity;
import com.tianfu.cutton.activity.mine.MyshareActivity;
import com.tianfu.cutton.activity.mine.PurchaseOrderActivity;
import com.tianfu.cutton.activity.mine.SettingActivity;
import com.tianfu.cutton.activity.mine.StsCountActivity;
import com.tianfu.cutton.activity.mine.SupplyOrderActivity;
import com.tianfu.cutton.common.Common;
import com.tianfu.cutton.model.CountBean;
import com.tianfu.cutton.net.CallBack;
import com.tianfu.cutton.net.HttpManager;
import com.tianfu.cutton.utils.CallKefu;
import com.tianfu.cutton.utils.GlideCircleTransform;
import com.tianfu.cutton.utils.SharedPreferencesUtil;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class MineFragment extends BaseFragment implements View.OnClickListener {
    private View mRootView;
    private LinearLayout llLogin;
    private LinearLayout myCommodity;
    private LinearLayout llSetting;
    private LinearLayout llMySupplyOrder;
    private LinearLayout llMyPurchaseOrder;
    private TextView tvLogin;
    private LinearLayout llMycollection;
    private Boolean isLogin;
    private TextView tv_favoritesCount;
    private TextView tv_productCount;
    private TextView tv_purCount;
    private TextView tv_shareCount;
    private TextView tv_supCount;
    private String eMUserName;
    private String eMPassword;
    private TextView tv_his_count;
    private String mobile;
    private ImageView ic_mine_head;
    private String iconurl;
    private ImageView im_message;
    private LinearLayout llStsCount;

    public MineFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mRootView != null) {
            return mRootView;
        } else {
            mRootView = inflater.inflate(R.layout.fragment_mine, container, false);
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null) {
                parent.removeView(mRootView);
            }
            initView();
            return mRootView;
        }
    }
    protected boolean isVisible = false;//页面是否可见
    protected boolean isPrepared = false;//是否布局已创建
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
        }
    }
    @Override
    public void onResume() {
        super.onResume();
        isPrepared = true;
        onVisible();
    }
    private void onVisible() {
        if (!isVisible || !isPrepared) {
            return;
        }
        String userLevel = SharedPreferencesUtil.getStringValue(BaseApplication.getContextObject(), "userLevel");
    /*    if (isLogin){
            if ("VIP".equals(userLevel)){
                llStsCount.setVisibility(View.VISIBLE);
            }else{
                llStsCount.setVisibility(View.GONE);
            }
        }else{
            llStsCount.setVisibility(View.GONE);
        }*/
    }


    @Override
    public void onStart() {
        super.onStart();
        if (ChatClient.getInstance().isLoggedInBefore()) {
            CallKefu.addMessageListener(im_message);
        }
        iconurl = SharedPreferencesUtil.getStringValue(BaseApplication.getContextObject(), "iconurl");
        eMUserName = SharedPreferencesUtil.getStringValue(BaseApplication.getContextObject(), "eMUserName");
        eMPassword = SharedPreferencesUtil.getStringValue(BaseApplication.getContextObject(), "eMPassword");
        isLogin = SharedPreferencesUtil.getBooleanValue(getActivity(), "isLogin");
        String userName = SharedPreferencesUtil.getStringValue(getActivity(), "userName");
        mobile = SharedPreferencesUtil.getStringValue(getActivity(), "mobile");
        initData();
        if (isLogin && userName.length() >= 1) {
            tvLogin.setText(userName);
        } else if (isLogin && mobile.length() > 1) {
            tvLogin.setText(mobile);
        } else {
            tvLogin.setText("登录/注册");
        }
        if (isLogin && iconurl != null && !"".equals(iconurl)) {
            Glide.with(BaseApplication.getContextObject())
                    .load(iconurl)
                    .transform(new GlideCircleTransform(BaseApplication.getContextObject()))
                    .into(ic_mine_head);

        } else {
            ic_mine_head.setImageResource(R.mipmap.ic_mine_head);
        }
    }

    private void initView() {
        ic_mine_head = (ImageView) mRootView.findViewById(R.id.ic_mine_head);
        tv_his_count = (TextView) mRootView.findViewById(R.id.tv_his_count);
        im_message = (ImageView) mRootView.findViewById(R.id.im_message);
        im_message.setOnClickListener(this);
        tv_favoritesCount = (TextView) mRootView.findViewById(R.id.tv_favoritesCount);
        tv_productCount = (TextView) mRootView.findViewById(R.id.tv_productCount);
        tv_purCount = (TextView) mRootView.findViewById(R.id.tv_purCount);
        tv_shareCount = (TextView) mRootView.findViewById(R.id.tv_shareCount);
        tv_supCount = (TextView) mRootView.findViewById(R.id.tv_supCount);
        LinearLayout ll_listHis = (LinearLayout) mRootView.findViewById(R.id.ll_listHis);
        ll_listHis.setOnClickListener(this);
        LinearLayout ll_myshare = (LinearLayout) mRootView.findViewById(R.id.ll_myshare);
        ll_myshare.setOnClickListener(this);
        llLogin = (LinearLayout) mRootView.findViewById(R.id.ll_login);
        myCommodity = (LinearLayout) mRootView.findViewById(R.id.ll_myCommodity);
        llLogin.setOnClickListener(this);
        myCommodity.setOnClickListener(this);
        llSetting = (LinearLayout) mRootView.findViewById(R.id.ll_setting);
        llSetting.setOnClickListener(this);
        llMySupplyOrder = (LinearLayout) mRootView.findViewById(R.id.ll_mySupplyOrder);
        llMySupplyOrder.setOnClickListener(this);
        llMyPurchaseOrder = (LinearLayout) mRootView.findViewById(R.id.ll_myPurchaseOrder);
        llMyPurchaseOrder.setOnClickListener(this);
        tvLogin = (TextView) mRootView.findViewById(R.id.tv_login);
        llMycollection = (LinearLayout) mRootView.findViewById(R.id.ll_mycollection);
        llMycollection.setOnClickListener(this);
        llStsCount = (LinearLayout) mRootView.findViewById(R.id.ll_stsCount);
        llStsCount.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.im_message:
                im_message.setImageResource(R.mipmap.ic_mine_message);
                if (!isLogin) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    return;
                }
                CallKefu.callOnLine(getActivity(), eMUserName, eMPassword);
                break;
            case R.id.ll_stsCount:
                if (!isLogin) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    return;
                }
                startActivity(new Intent(getActivity(), StsCountActivity.class));
                break;
            case R.id.ll_listHis:
                if (!isLogin) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    return;
                }
                startActivity(new Intent(getActivity(), MyListHistoryActivity.class));
                break;
            case R.id.ll_myshare:
                if (!isLogin) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    return;
                }
                startActivity(new Intent(getActivity(), MyshareActivity.class));
                break;
            case R.id.ll_login:
                if (isLogin != null && isLogin) {
                    break;
                } else {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    break;
                }
            case R.id.ll_myCommodity:
                if (!isLogin) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    return;
                }
                startActivity(new Intent(getActivity(), MyCommodityActivity.class));
                break;
            case R.id.ll_setting:
                startActivity(new Intent(getActivity(), SettingActivity.class));
                break;
            case R.id.ll_mySupplyOrder:
                if (!isLogin) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    return;
                }
                startActivity(new Intent(getActivity(), SupplyOrderActivity.class));
                break;
            case R.id.ll_myPurchaseOrder:
                if (!isLogin) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    return;
                }
                startActivity(new Intent(getActivity(), PurchaseOrderActivity.class));
                break;
            case R.id.ll_mycollection:
                if (!isLogin) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    return;
                }
                startActivity(new Intent(getActivity(), MyCollectionActivity.class));
                break;
        }
    }

    private VisitorTrack createVisitorTrack() {
        VisitorTrack track = ContentFactory.createVisitorTrack(null);
        track.title("我正在看")
                .price("4110￥")
                .desc("6528321642313（186/1）1427A \n巴楚县广大棉业有限责任公司")
                .imageUrl("https://ss0.bdstatic.com/5aV1bjqh_Q23odCf/static/superman/img/logo/bd_logo1_31bdc765.png")
                .itemUrl("http://www.baidu.com");
        return track;
    }


    private void initData() {
        if (isLogin) {
            Map<String, String> map = new HashMap<>();
            map.put("deviceNo", Common.deviceNo);
            map.put("mobile", mobile);
            HttpManager.getServerApi().getCount(map).enqueue(new CallBack<CountBean>() {
                @Override
                public void success(CountBean data) {
                    if (data.success) {

                        tv_supCount.setText("(" + data.value.supCount + ")");
                        tv_purCount.setText("(" + data.value.purCount + ")");
                        tv_productCount.setText("(" + data.value.productCount + ")");
                        if (data.value.shareCount.equals("0")) {
                            tv_shareCount.setText("");
                        } else {
                            tv_shareCount.setText(data.value.shareCount + "条");
                        }
                        if (data.value.historyCount.equals("0")) {
                            tv_his_count.setText("");
                        } else {
                            tv_his_count.setText(data.value.historyCount + "条");
                        }

                        if (data.value.favoritesCount.equals("0")) {
                            tv_favoritesCount.setText("");
                        } else {
                            tv_favoritesCount.setText(data.value.favoritesCount + "条");
                        }

                    }
                }

                @Override
                public void failure(ErrorType type, int httpCode) {

                }
            });
        } else {
            tv_favoritesCount.setText("");
            tv_supCount.setText("(" + 0 + ")");
            tv_purCount.setText("(" + 0 + ")");
            tv_shareCount.setText("");
            tv_productCount.setText("(" + 0 + ")");
            tv_his_count.setText("");
        }
    }
}
