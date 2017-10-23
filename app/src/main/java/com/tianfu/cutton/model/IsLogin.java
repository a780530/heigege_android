package com.tianfu.cutton.model;

/**
 * Created by admin on 2017/7/10.
 */

public class IsLogin {

    /**
     * success : true
     * msg : 用户正常登陆了
     * value : {"id":null,"createTime":null,"updateTime":null,"version":null,"mobile":null,"userName":"黑哥哥","companyName":null,"password":null,"lastLogonTime":null,"headimgurl":null,"sourceEnum":null,"createUserId":null,"stateEnum":null,"remark":null,"forzenperationUserID":null,"detailAddress":null,"eMUserName":null,"eMPassword":null,"weixinuthorizeUid":null,"havePassword":null,"deviceCode":null,"loginTimes":0}
     * code : null
     */

    public boolean success;
    public String msg;
    public ValueBean value;
    public String code;

    public static class ValueBean {
        /**
         * id : null
         * createTime : null
         * updateTime : null
         * version : null
         * mobile : null
         * userName : 黑哥哥
         * companyName : null
         * password : null
         * lastLogonTime : null
         * headimgurl : null
         * sourceEnum : null
         * createUserId : null
         * stateEnum : null
         * remark : null
         * forzenperationUserID : null
         * detailAddress : null
         * eMUserName : null
         * eMPassword : null
         * weixinuthorizeUid : null
         * havePassword : null
         * deviceCode : null
         * loginTimes : 0
         */

        public Object id;
        public Object createTime;
        public Object updateTime;
        public Object version;
        public Object mobile;
        public String userName;
        public String companyName;
        public Object password;
        public Object lastLogonTime;
        public Object headimgurl;
        public Object sourceEnum;
        public Object createUserId;
        public Object stateEnum;
        public Object remark;
        public Object forzenperationUserID;
        public Object detailAddress;
        public Object eMUserName;
        public Object eMPassword;
        public Object weixinuthorizeUid;
        public Object havePassword;
        public Object deviceCode;
        public int loginTimes;
        public String userLevel;
    }
}
