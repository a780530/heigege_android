package com.tianfu.cutton.model;

/**
 * Created by admin on 2017/7/11.
 */

public class LoginByCodeBean {

    /**
     * msg : 查询成功
     * success : true
     * value : {"id":"33","mobile":"15514559090","userName":"李某","companyName":"杭州公司","stateEnum":"NORMAL","havePassword":"2"}
     */

    public String msg;
    public boolean success;
    public ValueBean value;

    public static class ValueBean {
        /**
         * id : 33
         * mobile : 15514559090
         * userName : 李某
         * companyName : 杭州公司
         * stateEnum : NORMAL
         * havePassword : 2
         */

        public String id;
        public String createTime;
        public String updateTime;
        public String version;
        public String mobile;
        public String userName;
        public String companyName;
        public String password;
        public String lastLogonTime;
        public String headimgurl;
        public String sourceEnum;
        public Object createUserId;
        public String stateEnum;
        public Object remark;
        public Object forzenperationUserID;
        public Object detailAddress;
        public String eMUserName;
        public String eMPassword;
        public Object weixinuthorizeUid;
        public String userLevel;
        public String havePassword;
        public String deviceCode;
    }
}
