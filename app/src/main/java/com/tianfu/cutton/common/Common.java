package com.tianfu.cutton.common;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.tianfu.cutton.activity.base.BaseApplication;
import com.tianfu.cutton.utils.SharedPreferencesUtil;

import java.util.List;

/**
 * Created by admin on 2017/7/4.
 */

public class Common {
    public static String deviceNo = SharedPreferencesUtil.getStringValue(BaseApplication.getContextObject(), "deviceNo");
    public static String versionNo = SharedPreferencesUtil.getStringValue(BaseApplication.getContextObject(), "versionNo");
    public static String from = "android";
    public static String sourceTypeEnum = "APP";
    public static String IM = "kefuchannelimid_580925";//正式
    public static String shareUrl = "http://www.xjce.org/";//线上
//    public static String shareUrl = "http://test.efuren.com/";//测试
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
}
