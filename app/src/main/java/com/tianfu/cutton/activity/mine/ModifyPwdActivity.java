package com.tianfu.cutton.activity.mine;

import android.content.Intent;
import android.net.Uri;
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

import com.tianfu.cutton.R;
import com.tianfu.cutton.activity.base.BaseActivity;
import com.tianfu.cutton.common.Common;
import com.tianfu.cutton.model.CodeValidate;
import com.tianfu.cutton.net.CallBack;
import com.tianfu.cutton.net.HttpManager;
import com.tianfu.cutton.utils.CharacterFormatUtil;
import com.tianfu.cutton.utils.SharedPreferencesUtil;
import com.tianfu.cutton.utils.ToastUtil;
import com.tianfu.cutton.widget.UnderlineTextView;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.tianfu.cutton.common.Common.versionNo;


public class ModifyPwdActivity extends BaseActivity implements TextWatcher {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.ll_oldPwd)
    AutoLinearLayout llOldPwd;
    @BindView(R.id.ll_newPwd)
    AutoLinearLayout llNewPwd;
    @BindView(R.id.et_pwdConfirm)
    EditText etPwdConfirm;
    @BindView(R.id.ll_pwdConfirm)
    AutoLinearLayout llPwdConfirm;
    @BindView(R.id.tv_pswConfirm)
    TextView tvPswConfirm;
    @BindView(R.id.bt_sure)
    Button btSure;
    @BindView(R.id.et_oldPwd)
    EditText etOldPwd;
    @BindView(R.id.et_newPwd)
    EditText etNewPwd;
    @BindView(R.id.call_Kefu)
    UnderlineTextView callKefu;
    @BindView(R.id.tv_pswOld)
    TextView tvPswOld;
    @BindView(R.id.tv_pswFirst)
    TextView tvPswFirst;
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
    private String newPwd;
    private String oldPwd;
    private String oldPwdConfirm;
    private String mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_pwd);
        ButterKnife.bind(this);
        tvTitle.setText("修改密码");

        showBackground();//高亮输入框

        etOldPwd.setFilters(new InputFilter[]{filter});
        etNewPwd.setFilters(new InputFilter[]{filter});
        etPwdConfirm.setFilters(new InputFilter[]{filter});

        etNewPwd.addTextChangedListener(this);
        etOldPwd.addTextChangedListener(this);
        etPwdConfirm.addTextChangedListener(this);

    }

    private void showBackground() {
        newPwd = etNewPwd.getText().toString().trim();
        oldPwd = etOldPwd.getText().toString().trim();
        oldPwdConfirm = etPwdConfirm.getText().toString().trim();
        etOldPwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    llOldPwd.setBackgroundResource(R.drawable.edittext_background_greenl);
                } else {
                    if (!CharacterFormatUtil.isPassword(oldPwd)){
                        tvPswOld.setText("请输入6-16位密码");
                    }else{
                        tvPswOld.setText("");
                    }
                    llOldPwd.setBackgroundResource(R.drawable.edittext_background_normal);
                }
            }
        });
        etNewPwd.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    llNewPwd.setBackgroundResource(R.drawable.edittext_background_greenl);
                } else {
                    if (!CharacterFormatUtil.isPassword(newPwd)){
                        tvPswFirst.setText("请输入6-16位密码");
                    }else{
                        tvPswFirst.setText("");
                    }
                    llNewPwd.setBackgroundResource(R.drawable.edittext_background_normal);
                }
            }
        });
        etPwdConfirm.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    llPwdConfirm.setBackgroundResource(R.drawable.edittext_background_greenl);
                } else {
                    if (!CharacterFormatUtil.isPassword(oldPwdConfirm)){
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
                if (newPwd.equals(oldPwd)) {
                    tvPswConfirm.setText("新密码不能和旧密码一致");
                    break;
                } else if (!newPwd.equals(oldPwdConfirm)) {
                    tvPswConfirm.setText("两次密码不一致");
                    break;
                } else {
                    mobile = SharedPreferencesUtil.getStringValue(ModifyPwdActivity.this, "mobile");
                    HashMap<String, String> params = new HashMap<>();
                    params.put("deviceNo", Common.deviceNo);
                    params.put("from", Common.from);
                    params.put("version", versionNo);
                    params.put("mobile", mobile);
                    params.put("password", newPwd);
                    params.put("oldPasswd", oldPwd);
                    HttpManager.getServerApi().modfidyPwd(params).enqueue(new CallBack<CodeValidate>() {
                        @Override
                        public void success(CodeValidate data) {
                            if (data.success) {
                                ToastUtil.show(ModifyPwdActivity.this, "密码修改成功");
                                onBackPressed();
                            } else {
                                tvPswConfirm.setText(data.msg);
                            }
                        }

                        @Override
                        public void failure(ErrorType type, int httpCode) {

                        }
                    });
                }
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        newPwd = etNewPwd.getText().toString().trim();
        oldPwd = etOldPwd.getText().toString().trim();
        oldPwdConfirm = etPwdConfirm.getText().toString().trim();
        if (CharacterFormatUtil.isPassword(newPwd)&& CharacterFormatUtil.isPassword(oldPwd)&& CharacterFormatUtil.isPassword(oldPwdConfirm)) {
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

    @OnClick(R.id.call_Kefu)
    public void onViewClicked() {
        Intent intent= new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
                + "0991-3671111"));//电话号码
        startActivity(intent);
    }
}
