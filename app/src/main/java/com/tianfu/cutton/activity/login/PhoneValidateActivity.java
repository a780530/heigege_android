package com.tianfu.cutton.activity.login;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
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
import com.tianfu.cutton.model.LoginByCodeBean;
import com.tianfu.cutton.net.CallBack;
import com.tianfu.cutton.net.HttpManager;
import com.tianfu.cutton.utils.CharacterFormatUtil;
import com.tianfu.cutton.utils.CountDownTimerUtils;
import com.tianfu.cutton.utils.SharedPreferencesUtil;
import com.tianfu.cutton.utils.ToastUtil;
import com.tianfu.cutton.widget.UnderlineTextView;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class PhoneValidateActivity extends BaseActivity implements TextWatcher {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.et_Validatephone)
    EditText etValidatephone;
    @BindView(R.id.tv_getCode)
    TextView tvGetCode;
    @BindView(R.id.ll_validatePhone)
    AutoLinearLayout llValidatePhone;
    @BindView(R.id.tv_errorUser)
    TextView tvErrorUser;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.ll_validateSms)
    AutoLinearLayout llVlidateSms;
    @BindView(R.id.bt_login)
    Button btLogin;
    @BindView(R.id.tv_errorSms)
    TextView tvErrorSms;
    @BindView(R.id.call_Kefu)
    UnderlineTextView callKefu;
    /*
    * 禁止输入空格和回车
    * */
    private InputFilter filter = new InputFilter() {
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            if (source.equals(" ") || source.toString().contentEquals("\n")) return "";
            else return null;
        }
    };
    private String validatephone;
    private String sms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_validate);
        ButterKnife.bind(this);
        showBackground();//高亮输入框
        etCode.setFilters(new InputFilter[]{filter});

        etValidatephone.addTextChangedListener(this);
        etCode.addTextChangedListener(this);
    }

    private void showBackground() {

        etValidatephone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    llValidatePhone.setBackgroundResource(R.drawable.edittext_background_greenl);
                } else {
                    validatephone = etValidatephone.getText().toString().trim();
                    sms = etCode.getText().toString().trim();
                    if (TextUtils.isEmpty(validatephone)) {
                        tvErrorUser.setText("请输入手机号码");
                    } else if (!TextUtils.isEmpty(validatephone) &&!CharacterFormatUtil.isPhoneNumberValid(validatephone)) {
                        tvErrorUser.setText("请输入正确的手机号码");
                    } else {
                        tvErrorUser.setText("");
                    }
                    llValidatePhone.setBackgroundResource(R.drawable.edittext_background_normal);
                }
            }
        });
        etCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    llVlidateSms.setBackgroundResource(R.drawable.edittext_background_greenl);
                } else {
                    sms = etCode.getText().toString().trim();
                    if (TextUtils.isEmpty(sms)) {
                        tvErrorSms.setText("验证码不能为空");
                    } else {
                        tvErrorSms.setText("");
                    }
                    llVlidateSms.setBackgroundResource(R.drawable.edittext_background_normal);
                }
            }
        });
    }

    @OnClick({R.id.iv_back, R.id.tv_getCode, R.id.bt_login})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.tv_getCode://获取验证码
                if (TextUtils.isEmpty(validatephone)) {
                    tvErrorUser.setText("请输入手机号码");
                    break;
                } else if (!TextUtils.isEmpty(validatephone) && !CharacterFormatUtil.isPhoneNumberValid(validatephone)) {
                    tvErrorUser.setText("请输入正确的手机号码");
                    break;
                } else {
                    CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(tvGetCode, 60000, 1000);
                    mCountDownTimerUtils.start();
                    HashMap<String, String> params = new HashMap<>();
                    params.put("deviceNo", Common.deviceNo);
                    params.put("from", Common.from);
                    params.put("version", Common.versionNo);
                    params.put("mobile", validatephone);
                    params.put("serviceTypeEnum", "LOGIN");
                    params.put("sourceTypeEnum", Common.sourceTypeEnum);
                    HttpManager.getServerApi().getSms(params).enqueue(new CallBack<GetSms>() {
                        @Override
                        public void success(GetSms data) {
                            if (!data.isSuccess()) {
                                tvErrorSms.setText(data.getMsg());
                            }
                        }

                        @Override
                        public void failure(ErrorType type, int httpCode) {
                            ToastUtil.show(PhoneValidateActivity.this, "请检查您的网络");
                        }
                    });
                    break;
                }
            case R.id.bt_login:
                HashMap<String, String> paramsCode = new HashMap<>();
                paramsCode.put("deviceNo", Common.deviceNo);
                paramsCode.put("from", Common.from);
                paramsCode.put("version", Common.versionNo);
                paramsCode.put("mobile", validatephone);
                paramsCode.put("code", sms);
                HttpManager.getServerApi().loginByCode(paramsCode).enqueue(new CallBack<LoginByCodeBean>() {
                    @Override
                    public void success(LoginByCodeBean data) {
                        if (data.success) {
                            boolean isLogin = SharedPreferencesUtil.getBooleanValue(PhoneValidateActivity.this, "isLogin");
                            isLogin = true;
                            SharedPreferencesUtil.saveBooleanValue(PhoneValidateActivity.this, "isLogin", isLogin);
                            String mobile = data.value.mobile;
                            String havePwd = data.value.havePassword;
                            String userName = data.value.userName;
                            SharedPreferencesUtil.saveStringValue(BaseApplication.getContextObject(),"userLevel",data.value.userLevel);
                            SharedPreferencesUtil.saveStringValue(PhoneValidateActivity.this,"companyName",data.value.companyName);
                            SharedPreferencesUtil.saveStringValue(PhoneValidateActivity.this, "userName", userName);
                            SharedPreferencesUtil.saveStringValue(PhoneValidateActivity.this, "havePassword", havePwd);
                            SharedPreferencesUtil.saveStringValue(PhoneValidateActivity.this, "mobile", mobile);
                            SharedPreferencesUtil.saveStringValue(BaseApplication.getContextObject(), "eMUserName", data.value.eMUserName);
                            SharedPreferencesUtil.saveStringValue(BaseApplication.getContextObject(), "eMPassword", data.value.eMPassword);
                            SharedPreferencesUtil.saveStringValue(PhoneValidateActivity.this, "iconurl", data.value.headimgurl);
                            if (data.value.stateEnum.equals("FROZEN")) {
                                tvErrorSms.setText("手机账户被冻结");
                            } else {
                                Intent intent = new Intent(BaseApplication.getContextObject(), MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        } else {
                            tvErrorSms.setText(data.msg);
                        }
                    }

                    @Override
                    public void failure(ErrorType type, int httpCode) {
                        ToastUtil.show(PhoneValidateActivity.this, "请检查您的网络");
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
        sms = etCode.getText().toString().trim();
        validatephone = etValidatephone.getText().toString().trim();
        if (TextUtils.isEmpty(validatephone) || !CharacterFormatUtil.isPhoneNumberValid(validatephone) || TextUtils.isEmpty(sms)) {
            noClick();
        } else {
            okClick();
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    private void okClick() {
        btLogin.setBackgroundResource(R.drawable.login__button_background_green);
        btLogin.setEnabled(true);
    }

    private void noClick() {
        btLogin.setBackgroundResource(R.drawable.login__button_background_normal);
        btLogin.setEnabled(false);
    }

    @OnClick(R.id.call_Kefu)
    public void onViewClicked() {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
                + "0991-3671111"));//电话号码
        startActivity(intent);
    }
}
