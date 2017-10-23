package com.tianfu.cutton.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tianfu.cutton.R;
import com.tianfu.cutton.activity.base.BaseActivity;
import com.tianfu.cutton.activity.base.BaseApplication;
import com.tianfu.cutton.common.Common;
import com.tianfu.cutton.model.BagDetailsMessageBean;
import com.tianfu.cutton.net.CallBack;
import com.tianfu.cutton.net.HttpManager;
import com.tianfu.cutton.utils.SharedPreferencesUtil;
import com.tianfu.cutton.utils.ToastUtil;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScanLoginActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.sureLogin)
    Button sureLogin;
    @BindView(R.id.dismissLogin)
    TextView dismissLogin;
    private String mobile;
    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_login);
        ButterKnife.bind(this);
        tvTitle.setText("扫码登录");
        Intent intent = getIntent();
         uid = intent.getStringExtra("uid");
    }

    @OnClick({R.id.iv_back, R.id.sureLogin, R.id.dismissLogin})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                noLogin();
                onBackPressed();
                break;
            case R.id.sureLogin:
                goLogin();
                break;
            case R.id.dismissLogin:
                noLogin();
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mobile = SharedPreferencesUtil.getStringValue(BaseApplication.getContextObject(), "mobile");
    }

    private void goLogin() {
        Map<String, String> map = new HashMap<>();
        map.put("mobile", mobile);
        map.put("deviceNo", Common.deviceNo);
        map.put("uid", uid);
        HttpManager.getServerApi().scanLogin(map).enqueue(new CallBack<BagDetailsMessageBean>() {
            @Override
            public void success(BagDetailsMessageBean data) {
                onBackPressed();
            }

            @Override
            public void failure(ErrorType type, int httpCode) {
                ToastUtil.show(BaseApplication.getContextObject(), "请检查您的网络");
            }
        });
    }

    private void noLogin() {
        Map<String, String> map = new HashMap<>();
        map.put("uid",uid);
        HttpManager.getServerApi().cancleLogin(map).enqueue(new CallBack<BagDetailsMessageBean>() {
            @Override
            public void success(BagDetailsMessageBean data) {
                onBackPressed();
            }

            @Override
            public void failure(ErrorType type, int httpCode) {

            }
        });
    }
}
