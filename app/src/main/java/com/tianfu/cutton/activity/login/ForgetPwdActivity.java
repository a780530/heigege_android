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

import com.tianfu.cutton.R;
import com.tianfu.cutton.activity.base.BaseActivity;
import com.tianfu.cutton.common.Common;
import com.tianfu.cutton.model.CodeValidate;
import com.tianfu.cutton.model.GetSms;
import com.tianfu.cutton.net.CallBack;
import com.tianfu.cutton.net.HttpManager;
import com.tianfu.cutton.utils.CharacterFormatUtil;
import com.tianfu.cutton.utils.CountDownTimerUtils;
import com.tianfu.cutton.utils.ToastUtil;
import com.tianfu.cutton.widget.UnderlineTextView;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ForgetPwdActivity extends BaseActivity implements TextWatcher {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
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
        setContentView(R.layout.activity_forget_pwd);
        ButterKnife.bind(this);
        showBackground();//高亮输入框
        tvTitle.setText("忘记密码");

        etForgetPhone.addTextChangedListener(this);
        etCode.addTextChangedListener(this);

    }

    private void showBackground() {
        etForgetPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    llForgetPhone.setBackgroundResource(R.drawable.edittext_background_greenl);
                } else {
                    forgetPhone = etForgetPhone.getText().toString().trim();
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
                    sms = etCode.getText().toString().trim();
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
                    break;
                } else {
                    CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(tvGetCode, 60000, 1000);
                    mCountDownTimerUtils.start();
                    HashMap<String, String> params = new HashMap<>();
                    params.put("deviceNo", Common.deviceNo);
                    params.put("from", Common.from);
                    params.put("version", Common.versionNo);
                    params.put("mobile", forgetPhone);
                    params.put("serviceTypeEnum", "FORGET");
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
                            ToastUtil.show(ForgetPwdActivity.this, "请检查您的网络");
                        }
                    });
                }
                break;
            case R.id.bt_next:
                HashMap<String, String> paramsCode = new HashMap<>();
                paramsCode.put("mobile", forgetPhone);
                paramsCode.put("code", sms);
                paramsCode.put("serviceTypeEnum", "FORGET");
                paramsCode.put("sourceTypeEnum", Common.sourceTypeEnum);
                HttpManager.getServerApi().smsValidate(paramsCode).enqueue(new CallBack<CodeValidate>() {
                    @Override
                    public void success(CodeValidate data) {
                        if (data.success) {
                            Intent intent = new Intent();
                            intent.setClass(ForgetPwdActivity.this, ResettingPwdActivity.class);
                            intent.putExtra("mobileForget", forgetPhone);
                            intent.putExtra("codeForget", sms);
                            startActivity(intent);
                        } else {
                            tvErrorSms.setText(data.msg);
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
