package com.tianfu.cutton.activity.login;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
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
import com.tianfu.cutton.model.LoginBean;
import com.tianfu.cutton.net.CallBack;
import com.tianfu.cutton.net.HttpManager;
import com.tianfu.cutton.utils.CharacterFormatUtil;
import com.tianfu.cutton.utils.SharedPreferencesUtil;
import com.tianfu.cutton.utils.ToastUtil;
import com.tianfu.cutton.widget.UnderlineTextView;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareConfig;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.zhy.autolayout.AutoLinearLayout;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;



public class LoginActivity extends BaseActivity implements TextWatcher, View.OnFocusChangeListener {
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.ll_loginPhone)
    AutoLinearLayout llLoginPhone;
    @BindView(R.id.et_password)
    EditText etPassword;
    @BindView(R.id.ll_loginPsw)
    AutoLinearLayout llLoginPsw;
    @BindView(R.id.bt_login)
    Button btLogin;
    @BindView(R.id.bt_phoneLogin)
    Button btPhoneLogin;
    @BindView(R.id.tv_goRegister)
    UnderlineTextView tvGoRegister;
    @BindView(R.id.tv_forgetPsw)
    UnderlineTextView tvForgetPsw;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.tv_errorUser)
    TextView tvErrorUser;
    @BindView(R.id.tv_errorPwd)
    TextView tvErrorPwd;
    @BindView(R.id.loginWx)
    ImageView loginWx;
    @BindView(R.id.ll_login_Wx)
    AutoLinearLayout llLoginWx;
    @BindView(R.id.tv_Weixin)
    TextView tvWeixin;
    private String phonelogin;
    private String pswlogin;
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
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
//        showBackground();//高亮输入框

        etPhone.addTextChangedListener(this);
        etPhone.setOnFocusChangeListener(this);
        etPassword.addTextChangedListener(this);
        etPassword.setOnFocusChangeListener(this);
        etPassword.setFilters(new InputFilter[]{filter});
        boolean weixinAvilible = isWeixinAvilible(LoginActivity.this);
        if (weixinAvilible) {
            llLoginWx.setVisibility(View.VISIBLE);
            loginWx.setVisibility(View.VISIBLE);
            tvWeixin.setVisibility(View.VISIBLE);
        } else {
            llLoginWx.setVisibility(View.GONE);
            loginWx.setVisibility(View.GONE);
            tvWeixin.setVisibility(View.GONE);
        }
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        phonelogin = etPhone.getText().toString().trim();
        pswlogin = etPassword.getText().toString().trim();
        if (TextUtils.isEmpty(phonelogin) || !CharacterFormatUtil.isPhoneNumberValid(phonelogin) || TextUtils.isEmpty(pswlogin)) {
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

    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }

        return false;
    }

    @OnClick({R.id.bt_phoneLogin, R.id.tv_goRegister, R.id.tv_forgetPsw, R.id.bt_login, R.id.iv_back, R.id.loginWx})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.loginWx:
                loginWx.setEnabled(false);
                authorization(SHARE_MEDIA.WEIXIN);
                break;
            case R.id.bt_phoneLogin:
                startActivity(new Intent(this, PhoneValidateActivity.class));
                break;
            case R.id.tv_goRegister:
                startActivity(new Intent(this, RegisterActivity.class));
                break;
            case R.id.tv_forgetPsw:
                startActivity(new Intent(this, ForgetPwdActivity.class));
                break;
            case R.id.bt_login:
                HashMap<String, String> params = new HashMap<>();
                params.put("deviceNo", Common.deviceNo);
                params.put("from", Common.from);
                params.put("version", Common.versionNo);
                params.put("mobile", phonelogin);
                params.put("password", pswlogin);
                HttpManager.getServerApi().loginByPwd(params).enqueue(new CallBack<LoginBean>() {
                    @Override
                    public void success(LoginBean data) {
                        if (data.success) {
                            String havePwd = SharedPreferencesUtil.getStringValue(LoginActivity.this, "havePassword");
                            havePwd = "1";
                            SharedPreferencesUtil.saveStringValue(LoginActivity.this, "havePassword", havePwd);
                            Boolean isLogin = SharedPreferencesUtil.getBooleanValue(LoginActivity.this, "isLogin");
                            isLogin = true;
                            String mobile = data.value.mobile;
                            SharedPreferencesUtil.saveStringValue(BaseApplication.getContextObject(),"userLevel",data.value.userLevel);
                            SharedPreferencesUtil.saveStringValue(BaseApplication.getContextObject(), "eMUserName", data.value.eMUserName);
                            SharedPreferencesUtil.saveStringValue(BaseApplication.getContextObject(), "eMPassword", data.value.eMPassword);
                            SharedPreferencesUtil.saveStringValue(LoginActivity.this, "mobile", mobile);
                            SharedPreferencesUtil.saveStringValue(LoginActivity.this,"companyName",data.value.companyName);
                            SharedPreferencesUtil.saveStringValue(LoginActivity.this, "iconurl", data.value.headimgurl);
                            SharedPreferencesUtil.saveBooleanValue(LoginActivity.this, "isLogin", isLogin);
                            SharedPreferencesUtil.saveStringValue(LoginActivity.this, "userName", data.value.userName);
                            onBackPressed();
                            ToastUtil.show(BaseApplication.getContextObject(), "登录成功");
                        } else {
                            tvErrorPwd.setText(data.msg);
                        }
                    }

                    @Override
                    public void failure(ErrorType type, int httpCode) {
                        ToastUtil.show(BaseApplication.getContextObject(), "请检查您的网络");
                    }
                });
                break;
            case R.id.iv_back:
                onBackPressed();
        }
    }

    private void authorization(SHARE_MEDIA weixin) {
        UMShareConfig config = new UMShareConfig();
        config.isNeedAuthOnGetUserInfo(true);
        UMShareAPI.get(LoginActivity.this).setShareConfig(config);
        UMShareAPI.get(this).getPlatformInfo(this, weixin, new UMAuthListener() {
            @Override
            public void onStart(SHARE_MEDIA share_media) {
                System.out.println("onStart:" + "授权开始");
            }

            @Override
            public void onComplete(SHARE_MEDIA share_media, final int i, Map<String, String> map) {
                loginWx.setEnabled(true);
                final String uid = map.get("uid");
                System.out.println("uid:" + uid);
                String openid = map.get("openid");
                String unionid = map.get("unionid");
                String access_token = map.get("access_token");
                String expires_in = map.get("expires_in");
                String name = map.get("name");
                String gender = map.get("gender");
                final String iconurl = map.get("iconurl");
                Map map1 = new HashMap();
                map1.put("deviceNo", Common.deviceNo);
                System.out.println("Common.deviceNo:"+Common.deviceNo);
                System.out.println("deviceNo:" + Common.deviceNo);
                System.out.println("weixinuthorizeUid:------" + uid);
                map1.put("weixinuthorizeUid", uid);
                map1.put("headImgurl", iconurl);
                HttpManager.getServerApi().ifBindMobile(map1).enqueue(new CallBack<LoginBean>() {
                    @Override
                    public void success(LoginBean data) {
                        if (data.success) {
                            if (data.value != null && !"".equals(data.value)) {
//                                String havePwd = SharedPreferencesUtil.getStringValue(LoginActivity.this, "havePassword");
                                String havePassword = SharedPreferencesUtil.getStringValue(LoginActivity.this, "havePassword");
                                havePassword = data.value.havePassword;
                                SharedPreferencesUtil.saveStringValue(LoginActivity.this, "havePassword", havePassword);
                                Boolean isLogin = SharedPreferencesUtil.getBooleanValue(LoginActivity.this, "isLogin");
                                isLogin = true;
                                String mobile = data.value.mobile;
                                SharedPreferencesUtil.saveStringValue(BaseApplication.getContextObject(), "userLevel", data.value.userLevel);
                                SharedPreferencesUtil.saveStringValue(BaseApplication.getContextObject(), "iconurl", data.value.headimgurl);
                                SharedPreferencesUtil.saveStringValue(BaseApplication.getContextObject(), "eMUserName", data.value.eMUserName);
                                SharedPreferencesUtil.saveStringValue(BaseApplication.getContextObject(), "eMPassword", data.value.eMPassword);
                                SharedPreferencesUtil.saveStringValue(LoginActivity.this, "mobile", mobile);
                                SharedPreferencesUtil.saveBooleanValue(LoginActivity.this, "isLogin", isLogin);
                                SharedPreferencesUtil.saveStringValue(LoginActivity.this, "userName", data.value.userName);
                                onBackPressed();
                            } else {
                                Intent intent = new Intent(LoginActivity.this, BindMobileActivity.class);
                                intent.putExtra("uid", uid);
                                intent.putExtra("iconurl", iconurl);
                                startActivity(intent);
                            }
                        } else {
                            ToastUtil.show(BaseApplication.getContextObject(), data.msg);
                        }
                    }

                    @Override
                    public void failure(ErrorType type, int httpCode) {
                        ToastUtil.show(BaseApplication.getContextObject(), "请检查您的网络");
                    }
                });
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                loginWx.setEnabled(true);
                System.out.println("onStart:" + "授权失败");
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                loginWx.setEnabled(true);
                System.out.println("onStart:" + "授权取消");
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.et_phone:
                if (hasFocus) {
                    llLoginPhone.setBackgroundResource(R.drawable.edittext_background_greenl);
                } else {
                    llLoginPhone.setBackgroundResource(R.drawable.edittext_background_normal);
                    phonelogin = etPhone.getText().toString().trim();
                    if (TextUtils.isEmpty(phonelogin) || !CharacterFormatUtil.isPhoneNumberValid(phonelogin)) {
                        tvErrorUser.setText("请输入正确的手机号码");
                    } else {
                        tvErrorUser.setText("");
                    }
                }
                break;
            case R.id.et_password:
                if (hasFocus) {
                    llLoginPsw.setBackgroundResource(R.drawable.edittext_background_greenl);
                } else {
                    llLoginPsw.setBackgroundResource(R.drawable.edittext_background_normal);
                    pswlogin = etPassword.getText().toString().trim();
                    if (TextUtils.isEmpty(pswlogin) || !CharacterFormatUtil.isPassword(pswlogin)) {
                        tvErrorPwd.setText("请输入6-16位密码");
                    } else {
                        tvErrorPwd.setText("");
                    }
                }
                break;
        }

    }
}
