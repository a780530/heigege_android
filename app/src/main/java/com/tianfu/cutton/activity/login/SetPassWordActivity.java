package com.tianfu.cutton.activity.login;

import android.content.Intent;
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
import com.tianfu.cutton.model.ForgetPwdBean;
import com.tianfu.cutton.model.SetPwdBean;
import com.tianfu.cutton.net.CallBack;
import com.tianfu.cutton.net.HttpManager;
import com.tianfu.cutton.utils.CharacterFormatUtil;
import com.tianfu.cutton.utils.SharedPreferencesUtil;
import com.tianfu.cutton.utils.ToastUtil;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.tianfu.cutton.utils.SharedPreferencesUtil.getStringValue;

public class SetPassWordActivity extends BaseActivity implements TextWatcher {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.ll_Pwd)
    AutoLinearLayout llPwd;
    @BindView(R.id.et_pwdConfirm)
    EditText etPwdConfirm;
    @BindView(R.id.ll_pwdConfirm)
    AutoLinearLayout llPwdConfirm;
    @BindView(R.id.bt_sure)
    Button btSure;
    @BindView(R.id.tv_pswConfirm)
    TextView tvPswConfirm;
    @BindView(R.id.tv_pswFirst)
    TextView tvPswFirst;
    private String pwdFirst;
    private String pwdConfirm;
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
    private String mobile;
    private String code;
    private String userName;
    private String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_pass_word);
        ButterKnife.bind(this);
        tvTitle.setText("设置密码");
        etPwd.addTextChangedListener(this);
        etPwdConfirm.addTextChangedListener(this);

        showBackground();//高亮输入框

        etPwd.addTextChangedListener(this);
        etPwdConfirm.addTextChangedListener(this);
        etPwd.setFilters(new InputFilter[]{filter});
        etPwdConfirm.setFilters(new InputFilter[]{filter});

        Bundle bundle = this.getIntent().getExtras();
        name = bundle.getString("name");
    }

    private void showBackground() {
        pwdFirst = etPwd.getText().toString().trim();
        pwdConfirm = etPwdConfirm.getText().toString().trim();
        etPwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    llPwd.setBackgroundResource(R.drawable.edittext_background_greenl);
                } else {
                    if (!CharacterFormatUtil.isPassword(pwdFirst)){
                        tvPswFirst.setText("请输入6-16位密码");
                    }else{
                        tvPswFirst.setText("");
                    }
                    llPwd.setBackgroundResource(R.drawable.edittext_background_normal);
                }
            }
        });
        etPwdConfirm.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    llPwdConfirm.setBackgroundResource(R.drawable.edittext_background_greenl);
                } else {
                    if (!CharacterFormatUtil.isPassword(pwdConfirm)){
                        tvPswConfirm.setText("请输入6-16位密码");
                    }else{
                        tvPswConfirm.setText("");
                    }
                    llPwdConfirm.setBackgroundResource(R.drawable.edittext_background_normal);
                }
            }
        });
    }

    @OnClick({R.id.iv_back, R.id.bt_sure})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                onBackPressed();
                break;
            case R.id.bt_sure:
                if (!pwdFirst.equals(pwdConfirm)) {
                    tvPswConfirm.setText("两次密码不一致");
                    break;
                } else {
                    if (name != null && name.equals("设置界面")) {
                        HashMap<String, String> paramsPwd = new HashMap<>();
                        String mobile = getStringValue(SetPassWordActivity.this, "mobile");
                        paramsPwd.put("password", pwdConfirm);
                        paramsPwd.put("deviceNo", Common.deviceNo);
                        paramsPwd.put("from", Common.from);
                        paramsPwd.put("version", Common.versionNo);
                        paramsPwd.put("mobile", mobile);
                        HttpManager.getServerApi().setPwd(paramsPwd).enqueue(new CallBack<SetPwdBean>() {
                            @Override
                            public void success(SetPwdBean data) {
                                if (data.success) {
                                    ToastUtil.show(SetPassWordActivity.this, "设置成功");
                                    String havePwd = SharedPreferencesUtil.getStringValue(SetPassWordActivity.this, "havePassword");
                                    havePwd = "1";
                                    SharedPreferencesUtil.saveStringValue(SetPassWordActivity.this, "havePassword", havePwd);
                                    onBackPressed();
                                    System.out.println("设置成功----------");
                                } else {
                                    tvPswConfirm.setText(data.msg);
                                }
                            }

                            @Override
                            public void failure(ErrorType type, int httpCode) {

                            }
                        });
                    } else if (name.equals("注册界面")) {
                        Intent intent = getIntent();
                        mobile = SharedPreferencesUtil.getStringValue(SetPassWordActivity.this, "mobile");
                        code = SharedPreferencesUtil.getStringValue(SetPassWordActivity.this, "code");
                        userName = SharedPreferencesUtil.getStringValue(SetPassWordActivity.this, "userName");
                        doRegisters();
                    }
                    break;
                }


        }
    }

    private void doRegisters() {
        HashMap<String, String> params = new HashMap<>();
        params.put("deviceNo", Common.deviceNo);
        params.put("from", Common.from);
        params.put("version", Common.versionNo);
        params.put("mobile", mobile);
        params.put("code", code);
        params.put("password", pwdConfirm);
        params.put("userName", userName);
        HttpManager.getServerApi().doRegister(params).enqueue(new CallBack<ForgetPwdBean>() {
            @Override
            public void success(ForgetPwdBean data) {
                if (data.success) {
                    String userName1 = SharedPreferencesUtil.getStringValue(SetPassWordActivity.this, "userName");
                    userName1 = userName;
                    SharedPreferencesUtil.saveStringValue(SetPassWordActivity.this, "userName", userName1);
                    Boolean isLogin = SharedPreferencesUtil.getBooleanValue(SetPassWordActivity.this, "isLogin");
                    isLogin = true;
                    SharedPreferencesUtil.saveBooleanValue(SetPassWordActivity.this, "isLogin", isLogin);
                    String havePwd = getStringValue(SetPassWordActivity.this, "havePassword");
                    havePwd = "1";
                    SharedPreferencesUtil.saveStringValue(SetPassWordActivity.this, "havePassword", havePwd);
                    ToastUtil.show(SetPassWordActivity.this, data.msg);
                    Intent intent = new Intent(BaseApplication.getContextObject(), MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                    ToastUtil.show(BaseApplication.getContextObject(),"注册成功");
                    intent.putExtra("fragmentid", "4");
                    intent.putExtra("Login", "Login");
                    startActivity(intent);
                } else {
                    tvPswConfirm.setText(data.msg);
                }
            }

            @Override
            public void failure(ErrorType type, int httpCode) {
                ToastUtil.show(SetPassWordActivity.this, "请检查您的网络");
            }
        });

    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        pwdFirst = etPwd.getText().toString().trim();
        pwdConfirm = etPwdConfirm.getText().toString().trim();
        if (!TextUtils.isEmpty(pwdFirst) && !TextUtils.isEmpty(pwdConfirm)) {
            btSure.setBackgroundResource(R.drawable.login__button_background_green);
            btSure.setEnabled(true);
        } else {
            btSure.setBackgroundResource(R.drawable.login__button_background_normal);
            btSure.setEnabled(false);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}
