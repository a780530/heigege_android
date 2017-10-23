package com.tianfu.cutton.common;

import com.tianfu.cutton.activity.base.BaseApplication;
import com.tianfu.cutton.utils.SharedPreferencesUtil;

/**
 * Created by admin on 2017/8/21.
 */

public class ComTem {
    private static ComTem comTem;
    public String deviceId;

    public ComTem() {
        deviceId = SharedPreferencesUtil.getStringValue(BaseApplication.getContextObject(), "deviceNo");
    }
    public static ComTem getInstance() {
        if (comTem==null) {
            comTem = new ComTem();
        }
        return comTem;
    }
}
