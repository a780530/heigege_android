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

import com.tianfu.cutton.R;
import com.tianfu.cutton.activity.base.BaseActivity;
import com.tianfu.cutton.activity.base.BaseApplication;
import com.tianfu.cutton.common.Common;
import com.tianfu.cutton.model.CodeValidate;
import com.tianfu.cutton.model.GetSms;
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

public class RegisterActivity extends BaseActivity implements TextWatcher {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_regPhone)
    EditText etRegPhone;
    @BindView(R.id.tv_getCode)
    TextView tvGetCode;
    @BindView(R.id.ll_regPhone)
    AutoLinearLayout llRegPhone;
    @BindView(R.id.et_code)
    EditText etCode;
    @BindView(R.id.ll_regCode)
    AutoLinearLayout llRegCode;
    @BindView(R.id.bt_next)
    Button btNext;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_errorUser)
    TextView tvErrorUser;
    @BindView(R.id.et_name)
    EditText etName;
    @BindView(R.id.tv_errorSms)
    TextView tvErrorSms;
    @BindView(R.id.ll_name)
    AutoLinearLayout llName;
    @BindView(R.id.call_kefu)
    UnderlineTextView callKefu;
    @BindView(R.id.tv_errorSmsCode)
    TextView tvErrorSmsCode;
    private String phoneRes;
    private String smsCode;
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
    private String realName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        ButterKnife.bind(this);
        tvTitle.setText("快速注册");
        etName.addTextChangedListener(this);
        etRegPhone.addTextChangedListener(this);
        etCode.addTextChangedListener(this);
        showBackground();//高亮输入框

        etRegPhone.addTextChangedListener(this);
        etCode.addTextChangedListener(this);

        etName.setFilters(new InputFilter[]{filter});

    }

    private void okClick() {
        btNext.setBackgroundResource(R.drawable.login__button_background_green);
        btNext.setEnabled(true);
    }

    private void noClick() {
        btNext.setBackgroundResource(R.drawable.login__button_background_normal);
        btNext.setEnabled(false);
    }

    private void showBackground() {
        etRegPhone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    llRegPhone.setBackgroundResource(R.drawable.edittext_background_greenl);
                } else {
                    phoneRes = etRegPhone.getText().toString().trim();
                    if (TextUtils.isEmpty(phoneRes)) {
                        tvErrorUser.setText("请输入手机号码");
                    }else if (!TextUtils.isEmpty(phoneRes) && !CharacterFormatUtil.isPhoneNumberValid(phoneRes)){
                        tvErrorUser.setText("请输入正确的手机号码");
                    }else {
                        tvErrorUser.setText("");
                    }
                    llRegPhone.setBackgroundResource(R.drawable.edittext_background_normal);
                }
            }
        });
        etCode.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    llRegCode.setBackgroundResource(R.drawable.edittext_background_greenl);
                } else {
                    smsCode = etCode.getText().toString().trim();
                    if (TextUtils.isEmpty(smsCode)) {
                        tvErrorSmsCode.setText("验证码不能为空");
                    } else {
                        tvErrorSmsCode.setText("");
                    }
                    llRegCode.setBackgroundResource(R.drawable.edittext_background_normal);
                }
            }
        });
        etName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    llName.setBackgroundResource(R.drawable.edittext_background_greenl);
                } else {
                    realName = etName.getText().toString().trim();
                    if (TextUtils.isEmpty(realName)) {
                        tvErrorSms.setText("用户名不能为空");
                    }
                    llName.setBackgroundResource(R.drawable.edittext_background_normal);
                }
            }
        });
    }

    @OnClick({R.id.tv_getCode, R.id.bt_next, R.id.iv_back})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_getCode:
                if (TextUtils.isEmpty(phoneRes)) {
                    tvErrorUser.setText("请输入手机号码");
                    break;
                }else if (!TextUtils.isEmpty(phoneRes)&&!CharacterFormatUtil.isPhoneNumberValid(phoneRes)) {
                    tvErrorUser.setText("请输入正确的手机号码");
                    break;
                } else {
                    CountDownTimerUtils mCountDownTimerUtils = new CountDownTimerUtils(tvGetCode, 60000, 1000);
                    mCountDownTimerUtils.start();
                    HashMap<String, String> params = new HashMap<>();
                    params.put("deviceNo", Common.deviceNo);
                    params.put("from", Common.from);
                    params.put("version", Common.versionNo);
                    params.put("mobile", phoneRes);
                    params.put("serviceTypeEnum", "REGISTER");
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
                            ToastUtil.show(RegisterActivity.this, "请检查您的网络");
                        }
                    });
                }
                break;
            case R.id.bt_next:
                //校验验证码并下一步
                HashMap<String, String> paramsCode = new HashMap<>();
                paramsCode.put("mobile", phoneRes);
                paramsCode.put("code", smsCode);
                paramsCode.put("serviceTypeEnum", "REGISTER");
                paramsCode.put("sourceTypeEnum", Common.sourceTypeEnum);
                HttpManager.getServerApi().smsValidate(paramsCode).enqueue(new CallBack<CodeValidate>() {
                    @Override
                    public void success(CodeValidate data) {
                        if (data.success) {
                            Intent intent = new Intent(RegisterActivity.this, SetPassWordActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("name", "注册界面");
                            intent.putExtras(bundle);
                            SharedPreferencesUtil.saveStringValue(RegisterActivity.this, "mobile", phoneRes);
                            SharedPreferencesUtil.saveStringValue(RegisterActivity.this, "code", smsCode);
                            SharedPreferencesUtil.saveStringValue(RegisterActivity.this, "userName", realName);
                            startActivity(intent);
                        } else {
                            tvErrorSms.setText(data.msg);
                        }
                    }

                    @Override
                    public void failure(ErrorType type, int httpCode) {
                        ToastUtil.show(BaseApplication.getContextObject(), "请检查你的网络");
                    }
                });


                break;
            case R.id.iv_back:
                onBackPressed();
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        phoneRes = etRegPhone.getText().toString().trim();
        smsCode = etCode.getText().toString().trim();
        realName = etName.getText().toString().trim();
        if (TextUtils.isEmpty(phoneRes) || !CharacterFormatUtil.isPhoneNumberValid(phoneRes) || TextUtils.isEmpty(smsCode) || TextUtils.isEmpty(realName)) {
            noClick();
        } else {
            okClick();
        }
    }

    @Override
    public void afterTextChanged(Editable s) {


    }


    @OnClick(R.id.call_kefu)
    public void onViewClicked() {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
                + "0991-3671111"));//电话号码
        startActivity(intent);
    }
}
