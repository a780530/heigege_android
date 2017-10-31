package com.tianfu.cutton.activity.base;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.content.Intent;
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

/**
 * Created by admin on 2017/7/17.
 */

public class BaseFragment extends Fragment {
//    public static NetBroadcastReceiver.NetEvevt evevt;
    /**
     * 网络类型
     */
    private int netMobile;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        evevt = this;
        inspectNet();
    }


    /**
     * 初始化时判断有没有网络
     */

    public boolean inspectNet() {
        this.netMobile = NetUtils.getNetWorkState(getActivity());
        return isNetConnect();
    }


/*    @Override
    public void onNetChange(int netMobile) {
        this.netMobile = netMobile;
        isNetConnect();
    }*/

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
            barProgressDialog.show(getActivity(), true, null);
        }else{
            if (getContext()!=null){
                barProgressDialog = new LoadingDialog(getContext());
                barProgressDialog.show(getActivity(), true, null);
            }
        }

    }

    protected void dismissProgressBar() {
        if (barProgressDialog != null) {
            barProgressDialog.dismisss();
        }
        barProgressDialog = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        boolean isLogin = SharedPreferencesUtil.getBooleanValue(BaseApplication.getContextObject(), "isLogin");
        if (isLogin){
            islogin();
        }
    }

    private void islogin() {
            Map<String, String> map = new HashMap();
            map.put("deviceNo", Common.deviceNo);
            map.put("from", Common.from);
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
                            if (!TextUtils.isEmpty(data.value.pushOffTime)){
                                SharedPreferencesUtil.saveStringValue(BaseApplication.getContextObject(),"pushOffTime",data.value.pushOffTime);
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

    }

    private void showdialog() {
        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setMessage("该用户在另外一台设备登录，请重新登录或者修改密码")
                .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(BaseApplication.getContextObject(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        getActivity().finish();
                    }
                })
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(final DialogInterface dialog, int which) {
                        Intent intent = new Intent(BaseApplication.getContextObject(), MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("fragmentid","4");
                        startActivity(intent);
                        getActivity().finish();
                    }
                })
                .create();
        dialog.show();
    }
}
