package com.tianfu.cutton.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
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
import com.tianfu.cutton.model.ForgetPwdBean;
import com.tianfu.cutton.net.CallBack;
import com.tianfu.cutton.net.HttpManager;
import com.tianfu.cutton.utils.CharacterFormatUtil;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ResettingPwdActivity extends BaseActivity implements TextWatcher {

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
    @BindView(R.id.tv_pswConfirm)
    TextView tvPswConfirm;
    @BindView(R.id.bt_sure)
    Button btSure;
    @BindView(R.id.tv_pswError)
    TextView tvPswError;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resetting_pwd);
        ButterKnife.bind(this);
        tvTitle.setText("重置密码");
        etPwd.addTextChangedListener(this);
        etPwdConfirm.addTextChangedListener(this);
        showBackground();//高亮输入框

        etPwd.addTextChangedListener(this);
        etPwdConfirm.addTextChangedListener(this);

        etPwd.setFilters(new InputFilter[]{filter});
        etPwdConfirm.setFilters(new InputFilter[]{filter});
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
                        tvPswError.setText("请输入6-16位密码");
                    }else{
                        tvPswError.setText("");
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

    @OnClick({R.id.iv_back, R.id.tv_pswConfirm, R.id.bt_sure})
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
                    Intent intent = getIntent();
                    String forgetPhone = intent.getStringExtra("mobileForget");
                    String codeForget = intent.getStringExtra("codeForget");
                    HashMap<String, String> paramsCode = new HashMap<>();
                    paramsCode.put("mobile", forgetPhone);
                    paramsCode.put("code", codeForget);
                    paramsCode.put("password", pwdConfirm);
                    HttpManager.getServerApi().forgetPwd(paramsCode).enqueue(new CallBack<ForgetPwdBean>() {
                        @Override
                        public void success(ForgetPwdBean data) {
                            if (data.success) {
                                Intent intent = new Intent(BaseApplication.getContextObject(), MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("fragmentid", "4");
                                startActivity(intent);
                                finish();
                            } else {
                                tvPswConfirm.setText(data.msg);
                            }
                        }

                        @Override
                        public void failure(ErrorType type, int httpCode) {

                        }
                    });
                    finish();
                    break;
                }

        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        pwdFirst = etPwd.getText().toString().trim();
        pwdConfirm = etPwdConfirm.getText().toString().trim();
        if (CharacterFormatUtil.isPassword(pwdFirst)&&CharacterFormatUtil.isPassword(pwdConfirm)) {
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
