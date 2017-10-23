package com.tianfu.cutton.activity.mine;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tianfu.cutton.R;
import com.tianfu.cutton.activity.base.BaseActivity;
import com.tianfu.cutton.activity.base.BaseApplication;
import com.tianfu.cutton.activity.login.SetPassWordActivity;
import com.tianfu.cutton.common.Common;
import com.tianfu.cutton.model.CodeValidate;
import com.tianfu.cutton.net.CallBack;
import com.tianfu.cutton.net.HttpManager;
import com.tianfu.cutton.utils.SharedPreferencesUtil;
import com.tianfu.cutton.utils.ToastUtil;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.tianfu.cutton.R.id.ll_modifyPwd;
import static com.tianfu.cutton.common.Common.versionNo;


public class SettingActivity extends BaseActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(ll_modifyPwd)
    AutoLinearLayout llModifyPwd;
    @BindView(R.id.ll_aboutTianFu)
    AutoLinearLayout llAboutTianFu;
    @BindView(R.id.logout)
    AutoLinearLayout logout;
    @BindView(R.id.ll_set)
    AutoLinearLayout llSet;
    @BindView(R.id.tv_setPwd)
    TextView tvSetPwd;
    private String havePwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        ButterKnife.bind(this);
        tvTitle.setText("设置");
        havePwd = SharedPreferencesUtil.getStringValue(SettingActivity.this, "havePassword");
        if (havePwd.equals("1")) {
            tvSetPwd.setText("修改密码");
        } else {
            tvSetPwd.setText("设置密码");
        }
    }

    @OnClick({R.id.iv_back, ll_modifyPwd, R.id.ll_aboutTianFu, R.id.logout})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case ll_modifyPwd:
                if (havePwd.equals("1")) {
                    startActivity(new Intent(this, ModifyPwdActivity.class));
                } else {
                    Intent intent = new Intent(SettingActivity.this, SetPassWordActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("name", "设置界面");
                    intent.putExtras(bundle);
                    startActivity(intent);
                }

                break;
            case R.id.ll_aboutTianFu:
                startActivity(new Intent(BaseApplication.getContextObject(), AboutWeActivity.class));
                break;
            case R.id.logout:
                HashMap<String, String> params = new HashMap<>();
                params.put("deviceNo", Common.deviceNo);
                params.put("from", Common.from);
                params.put("version", versionNo);
                HttpManager.getServerApi().loginOut(params).enqueue(new CallBack<CodeValidate>() {
                    @Override
                    public void success(CodeValidate data) {
                        if (data.success) {
                            SharedPreferences dataBase = getSharedPreferences("SharedPreferences", Activity.MODE_PRIVATE);
                            dataBase.edit().clear().commit();
                            Boolean isLogin = SharedPreferencesUtil.getBooleanValue(SettingActivity.this, "isLogin");
                            isLogin = false;
                            SharedPreferencesUtil.saveBooleanValue(SettingActivity.this, "isLogin", isLogin);
                            System.out.println("isLogin" + isLogin);
                            onBackPressed();
                        }

                    }

                    @Override
                    public void failure(ErrorType type, int httpCode) {
                        ToastUtil.show(BaseApplication.getContextObject(), "请检查您的网络");
                    }
                });
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (SharedPreferencesUtil.getBooleanValue(this, "isLogin") != null && SharedPreferencesUtil.getBooleanValue(this, "isLogin")) {
            llModifyPwd.setVisibility(View.VISIBLE);
            logout.setVisibility(View.VISIBLE);
            return;
        } else {
            llModifyPwd.setVisibility(View.GONE);
            logout.setVisibility(View.GONE);
            return;
        }
    }
}
