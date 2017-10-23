package com.tianfu.cutton.activity.base;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.tianfu.cutton.MainActivity;
import com.tianfu.cutton.common.Common;
import com.tianfu.cutton.model.IsLogin;
import com.tianfu.cutton.net.CallBack;
import com.tianfu.cutton.net.HttpManager;
import com.tianfu.cutton.utils.NetUtils;
import com.tianfu.cutton.utils.SharedPreferencesUtil;
import com.tianfu.cutton.widget.LoadingDialog;

import java.util.HashMap;
import java.util.Map;

import static com.umeng.socialize.utils.ContextUtil.getContext;

/**
 * Created by Administrator on 2017/6/20.
 */

public class BaseActivity extends AppCompatActivity {
    private static final int REQUEST_CODE = 0;

    /**
     * 获取点击事件
     */

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View view = getCurrentFocus();
            if (isHideInput(view, ev)) {
                HideSoftInput(view.getWindowToken());
                view.clearFocus();
            }
        }
        try {
            return super.dispatchTouchEvent(ev);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 判定是否需要隐藏
     */
    private boolean isHideInput(View v, MotionEvent ev) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left + v.getWidth();
            if (ev.getX() > left && ev.getX() < right && ev.getY() > top && ev.getY() < bottom) {
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 隐藏软键盘
     */
    private void HideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager manager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            manager.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @TargetApi(23)
    public void checkPermission() {
        if (!Settings.System.canWrite(this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, REQUEST_CODE);
        }
    }

//    public static NetBroadcastReceiver.NetEvevt evevt;
    /**
     * 网络类型
     */
    private int netMobile;
    private boolean isShow = true;

    @Override
    protected void onStart() {
        super.onStart();
        boolean isLogin = SharedPreferencesUtil.getBooleanValue(BaseApplication.getContextObject(), "isLogin");
        if (isLogin){
            islogin();
        }
    }

    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        inspectNet();
    }

    private void islogin() {
        Map<String, String> map = new HashMap();
        map.put("deviceNo", Common.deviceNo);
        map.put("from", Common.from);
        System.out.println("nihao tutututututu");
        map.put("version", Common.versionNo);
        HttpManager.getServerApi().isLogin(map).enqueue(new CallBack<IsLogin>() {
            @Override
            public void success(IsLogin data) {
                if (data != null) {
                    if (data.success) {
                        if (!TextUtils.isEmpty(data.value.userLevel)) {
                            SharedPreferencesUtil.saveStringValue(BaseApplication.getContextObject(),"userLevel",data.value.userLevel);
                        }else{
                            SharedPreferencesUtil.saveStringValue(BaseApplication.getContextObject(),"userLevel","");
                        }
                        if (!TextUtils.isEmpty(data.value.companyName)) {
                            SharedPreferencesUtil.saveStringValue(BaseApplication.getContextObject(),"companyName",data.value.companyName);
                        }
                        if (!TextUtils.isEmpty(data.value.userName)) {
                            SharedPreferencesUtil.saveStringValue(BaseApplication.getContextObject(),"userName",data.value.userName);
                        }
                        SharedPreferencesUtil.saveBooleanValue(BaseApplication.getContextObject(), "isLogin", true);
                    } else {
                        SharedPreferencesUtil.saveBooleanValue(BaseApplication.getContextObject(), "isLogin", false);
                        if ("200".equals(data.code)) {
                            showdialog();
                        } else if ("300".equals(data.code)) {
                            showdialog1();
                        }
                    }
                } else {

                }
            }

            @Override
            public void failure(ErrorType type, int httpCode) {

            }
        });
    }

    private void showdialog1() {
        AlertDialog dialog = new AlertDialog.Builder(BaseActivity.this)
                .setMessage("账号已经被冻结，请联系客服")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(BaseApplication.getContextObject(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                })
                .setPositiveButton("联系客服", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"
                                + "0991-3671111"));//电话号码
                        startActivity(intent);
                    }
                })
                .create();
        dialog.show();
    }

    private void showdialog() {
        AlertDialog dialog = new AlertDialog.Builder(BaseActivity.this)
                .setMessage("该用户在另外一台设备登录，请重新登录或者修改密码")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(BaseApplication.getContextObject(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        Intent intent = new Intent(BaseApplication.getContextObject(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("fragmentid","4");
                        startActivity(intent);
                        finish();
                    }
                })
                .create();
        dialog.show();
    }


    /**
     * 初始化时判断有没有网络
     */

    public boolean inspectNet() {
        this.netMobile = NetUtils.getNetWorkState(BaseActivity.this);
        return isNetConnect();
    }

/*    *//**
     * 网络变化之后的类型
     *//*
    @Override
    public void onNetChange(int netMobile) {
        this.netMobile = netMobile;
        isNetConnect();

    }*/

    /**
     * 判断有无网络 。
     *
     * @return true 有网, false 没有网络.
     */
    public boolean isNetConnect() {
        if (netMobile == NetUtils.NETWORK_WIFI) {
            return true;
        } else if (netMobile == NetUtils.NETWORK_MOBILE) {
            return true;
        } else if (netMobile == NetUtils.NETWORK_NONE) {
            return false;

        }
        return false;
    }

    private LoadingDialog barProgressDialog;

    protected void showProgressBar(int resId) {
        showProgressBar(getString(resId));
    }

    protected void showProgressBar(String loadingTxt) {
        showProgressBar(loadingTxt, true);
    }

    protected void showProgressBar(String loadingTxt, boolean isCancelable) {
        dismissProgressBar();
        if (barProgressDialog != null) {
            barProgressDialog.show(this, true, null);
        } else {
            if (getContext() != null) {
                barProgressDialog = new LoadingDialog(getContext());
                barProgressDialog.show(this, true, null);
            }
        }

    }

    protected void dismissProgressBar() {
        if (barProgressDialog != null) {
            barProgressDialog.dismisss();
        }
        barProgressDialog = null;
    }


}
