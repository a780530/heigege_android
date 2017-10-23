package com.tianfu.cutton.model;

/**
 * Created by admin on 2017/8/15.
 */

public class SetPwdBean {

    /**
     * success : true
     * msg : 密码设置成功
     * value : {"id":36,"createTime":"2017-08-15 13:31:26","updateTime":"2017-08-15 13:40:36","version":2,"mobile":"18268931633","userName":"18268931633","companyName":null,"password":"***","lastLogonTime":"2017-08-15 13:31:26","headimgurl":null,"sourceEnum":"APP","createUserId":null,"stateEnum":"NORMAL","remark":null,"forzenperationUserID":null,"detailAddress":null,"eMUserName":"xjm201708151404","eMPassword":"123456","weixinuthorizeUid":"***","havePassword":"1","deviceCode":"864913037898235","loginTimes":1}
     * code : null
     */

    public boolean success;
    public String msg;
    public ValueBean value;
    public Object code;

    public static class ValueBean {
        /**
         * id : 36
         * createTime : 2017-08-15 13:31:26
         * updateTime : 2017-08-15 13:40:36
         * version : 2
         * mobile : 18268931633
         * userName : 18268931633
         * companyName : null
         * password : ***
         * lastLogonTime : 2017-08-15 13:31:26
         * headimgurl : null
         * sourceEnum : APP
         * createUserId : null
         * stateEnum : NORMAL
         * remark : null
         * forzenperationUserID : null
         * detailAddress : null
         * eMUserName : xjm201708151404
         * eMPassword : 123456
         * weixinuthorizeUid : ***
         * havePassword : 1
         * deviceCode : 864913037898235
         * loginTimes : 1
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
        public Object headimgurl;
        public String sourceEnum;
        public Object createUserId;
        public String stateEnum;
        public Object remark;
        public Object forzenperationUserID;
        public Object detailAddress;
        public String eMUserName;
        public String eMPassword;
        public String weixinuthorizeUid;
        public String havePassword;
        public String deviceCode;
        public int loginTimes;
    }
}
