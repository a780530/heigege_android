package com.tianfu.cutton.model;

/**
 * Created by admin on 2017/7/5.
 */

public class LoginBean {

    /**
     * success : true
     * msg : 账号登录成功
     * value : {"id":27,"createTime":"2017-07-05 11:06:41","updateTime":"2017-07-05 11:38:33","version":1,"mobile":"18268931633","userName":"黑哥哥","companyName":null,"password":"","lastLogonTime":"2017-07-05 11:38:33","headimgurl":null,"sourceEnum":"APP","createUserId":null,"stateEnum":"NORMAL","remark":null,"forzenperationUserID":null,"detailAddress":null,"eMUserName":null,"eMPassword":null,"weixinuthorizeUid":null,"havePassword":"1","deviceCode":"863293039669475"}
     * code : null
     */

    public boolean success;
    public String msg;
    public ValueBean value;
    public Object code;

    public static class ValueBean {
        /**
         * id : 27
         * createTime : 2017-07-05 11:06:41
         * updateTime : 2017-07-05 11:38:33
         * version : 1
         * mobile : 18268931633
         * userName : 黑哥哥
         * companyName : null
         * password :
         * lastLogonTime : 2017-07-05 11:38:33
         * headimgurl : null
         * sourceEnum : APP
         * createUserId : null
         * stateEnum : NORMAL
         * remark : null
         * forzenperationUserID : null
         * detailAddress : null
         * eMUserName : null
         * eMPassword : null
         * weixinuthorizeUid : null
         * havePassword : 1
         * deviceCode : 863293039669475
         */

        public int id;
        public String createTime;
        public String updateTime;
        public int version;
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
        public String userLevel;
        public Object weixinuthorizeUid;
        public String havePassword;
        public String deviceCode;
    }
}
