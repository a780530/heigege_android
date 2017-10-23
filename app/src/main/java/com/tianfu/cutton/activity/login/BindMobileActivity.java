package com.tianfu.cutton.activity.login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.tianfu.cutton.MainActivity;
import com.tianfu.cutton.R;
import com.tianfu.cutton.activity.base.BaseActivity;
import com.tianfu.cutton.activity.base.BaseApplication;
import com.tianfu.cutton.common.Common;
import com.tianfu.cutton.model.GetSms;
import com.tianfu.cutton.model.LoginBean;
import com.tianfu.cutton.net.CallBack;
import com.tianfu.cutton.net.HttpManager;
import com.tianfu.cutton.utils.CharacterFormatUtil;
import com.tianfu.cutton.utils.CountDownTimerUtils;
import com.tianfu.cutton.utils.SharedPreferencesUtil;
import com.tianfu.cutton.utils.ToastUtil;
import com.tianfu.cutton.widget.UnderlineTextView;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.tianfu.cutton.R.id.tv_title;
import static com.tianfu.cutton.activity.base.BaseApplication.getContextObject;

public class BindMobileActivity extends BaseActivity implements TextWatcher {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(tv_title)
    TextView tvTitle;
    @BindView(R.id.et_forgetPhone)
    EditText etForgetPhone;
    @BindView(R.id.tv_getCode)
    TextView tvGetCode;
    @BindView(R.id.ll_forgetPhone)
    AutoLinearLayout llForgetPhone;
    @BindView(R.id.tv_errorUser)
    TextView tvErrorUser;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.ll_forgetSms)
    AutoLinearLayout llForgetSms;
    @BindView(R.id.tv_errorSms)
    TextView tvErrorSms;
    @BindView(R.id.bt_next)
    Button btNext;
    @BindView(R.id.call_kefu)
    UnderlineTextView callKefu;
    private String forgetPhone;
    private String sms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_mobile);
        ButterKnife.bind(this);
        showBackground();//高亮输入框
        tvTitle.setText("绑定手机");
        etForgetPhone.addTextChangedListener(this);
        etCode.addTextChangedListener(this);
    }

    private void showBackground() {
        forgetPhone = etForgetPhone.getText().toString().trim();
        sms = etCode.getText().toString().trim();
        etForgetPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    llForgetPhone.setBackgroundResource(R.drawable.edittext_background_greenl);
                } else {
                    if (TextUtils.isEmpty(forgetPhone)) {
                        tvErrorUser.setText("请输入手机号码");
                    } else if (!TextUtils.isEmpty(forgetPhone) && !CharacterFormatUtil.isPhoneNumberValid(forgetPhone)) {
                        tvErrorUser.setText("请输入正确的手机号码");
                    } else {
                        tvErrorUser.setText("");
                    }
                    llForgetPhone.setBackgroundResource(R.drawable.edittext_background_normal);
                }
            }
        });
        etCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    llForgetSms.setBackgroundResource(R.drawable.edittext_background_greenl);
                } else {
                    if (TextUtils.isEmpty(sms)) {
                        tvErrorSms.setText("验证码不能为空");
                    } else {
                        tvErrorSms.setText("");
                    }
                    llForgetSms.setBackgroundResource(R.drawable.edittext_background_normal);
                }
            }
        });
    }

    @OnClick({R.id.iv_back, R.id.tv_getCode, R.id.bt_next})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_getCode:
                if (TextUtils.isEmpty(forgetPhone)) {
                    tvErrorUser.setText("请输入手机号码");
                    break;
                } else if (!TextUtils.isEmpty(forgetPhone) && !CharacterFormatUtil.isPhoneNumberValid(forgetPhone)) {
                    tvErrorUser.setText("请输入正确的手机号码");
                } else {
                    CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(tvGetCode, 60000, 1000);
                    mCountDownTimerUtils.start();
                    HashMap<String, String> params = new HashMap<>();
                    params.put("deviceNo", Common.deviceNo);
                    params.put("from", Common.from);
                    params.put("version", Common.versionNo);
                    params.put("mobile", forgetPhone);
                    params.put("serviceTypeEnum", "LOGIN");
                    params.put("sourceTypeEnum", Common.sourceTypeEnum);
                    HttpManager.getServerApi().getSms(params).enqueue(new CallBack<GetSms>() {
                        @Override
                        public void success(GetSms data) {
                            if (!data.isSuccess()) {
                                tvErrorUser.setText(data.getMsg());
                            }
                        }

                        @Override
                        public void failure(ErrorType type, int httpCode) {
                            ToastUtil.show(BindMobileActivity.this, "请检查您的网络");
                        }
                    });
                }

                break;
            case R.id.bt_next:
                String uid = getIntent().getStringExtra("uid");
                String iconurl = getIntent().getStringExtra("iconurl");
                Map<String, String> stringMap = new HashMap<>();
                stringMap.put("deviceNo", Common.deviceNo);
                stringMap.put("from", Common.from);
                stringMap.put("version", Common.versionNo);
                stringMap.put("weixinuthorizeUid", uid);
                stringMap.put("mobile", forgetPhone);
                stringMap.put("code", sms);
                stringMap.put("headImgurl",iconurl);
                HttpManager.getServerApi().bindMobile(stringMap).enqueue(new CallBack<LoginBean>() {
                    @Override
                    public void success(LoginBean data) {
                        if (data.success) {
                            ToastUtil.show(getContextObject(), "绑定成功");
                            Intent intent = new Intent(BindMobileActivity.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                            String havePassword = data.value.havePassword;
                            SharedPreferencesUtil.saveStringValue(BindMobileActivity.this, "havePassword", havePassword);
//                            Boolean isLogin = SharedPreferencesUtil.getBooleanValue(BindMobileActivity.this, "isLogin");
                           boolean isLogin = true;
                            String mobile = data.value.mobile;
                            SharedPreferencesUtil.saveStringValue(BaseApplication.getContextObject(),"userLevel",data.value.userLevel);
                            SharedPreferencesUtil.saveStringValue(getContextObject(), "eMUserName", data.value.eMUserName);
                            SharedPreferencesUtil.saveStringValue(getContextObject(), "eMPassword", data.value.eMPassword);
                            SharedPreferencesUtil.saveStringValue(BindMobileActivity.this, "mobile", mobile);
                            SharedPreferencesUtil.saveStringValue(BindMobileActivity.this,"companyName",data.value.companyName);
                            SharedPreferencesUtil.saveBooleanValue(BindMobileActivity.this, "isLogin", isLogin);
                            SharedPreferencesUtil.saveStringValue(BindMobileActivity.this,"iconurl",data.value.headimgurl);
                            SharedPreferencesUtil.saveStringValue(BindMobileActivity.this, "userName", data.value.userName);
                            onBackPressed();
                        }else{
                            ToastUtil.show(getContextObject(), data.msg);
                            onBackPressed();
                        }
                    }

                    @Override
                    public void failure(ErrorType type, int httpCode) {

                    }
                });
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        forgetPhone = etForgetPhone.getText().toString().trim();
        sms = etCode.getText().toString().trim();
        if (TextUtils.isEmpty(forgetPhone) || !CharacterFormatUtil.isPhoneNumberValid(forgetPhone) || TextUtils.isEmpty(sms)) {
            noClick();
        } else {
            okClick();
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private void okClick() {
        btNext.setBackgroundResource(R.drawable.login__button_background_green);
        btNext.setEnabled(true);
    }

    private void noClick() {
        btNext.setBackgroundResource(R.drawable.login__button_background_normal);
        btNext.setEnabled(false);
    }

    @OnClick(R.id.call_kefu)
    public void onViewClicked() {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
                + "0991-3671111"));//电话号码
        startActivity(intent);
    }
}
