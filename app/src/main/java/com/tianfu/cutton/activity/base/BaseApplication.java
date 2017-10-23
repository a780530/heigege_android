package com.tianfu.cutton.activity.base;

import android.app.Application;
import android.content.Context;

import com.hyphenate.chat.ChatClient;
import com.hyphenate.helpdesk.easeui.UIProvider;
import com.tianfu.cutton.net.HttpManager;
import com.umeng.socialize.Config;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

/**
 * Created by admin on 2017/7/4.
 */

public class BaseApplication extends Application {

    public static Context context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
        HttpManager.init();
        UMShareAPI.get(this);
        Config.DEBUG = true;
        //客服
        ChatClient.Options options = new ChatClient.Options();
        options.setAppkey("1157170731178970#kefuchannelapp45134");
        options.setTenantId("45134");
        // Kefu SDK 初始化
        if (!ChatClient.getInstance().init(this, options)){
            return;
        }
        // Kefu EaseUI的初始化
        UIProvider.getInstance().init(this);
        //后面可以设置其他属性
//        registToWX();
    }

    public static Context getContextObject() {
        return context;
    }

    {

        PlatformConfig.setWeixin("wxca966f1d2309e553", "8a725765fc5387e1196237fe3d805555");
    }
}
