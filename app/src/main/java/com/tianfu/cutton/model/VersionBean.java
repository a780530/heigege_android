package com.tianfu.cutton.model;

/**
 * Created by admin on 2017/8/15.
 */

public class VersionBean {

    /**
     * success : true
     * msg : 查询成功
     * value : {"id":2,"createTime":null,"updateTime":null,"version":null,"appName":null,"appCode":null,"appType":"android","appVersion":"1.0.0","forceUpdate":0,"updateList":null,"downloadUrl":"http://yf.xjmcotton.com/product/download/xjm.apk","createUser":null}
     * code : null
     */

    public boolean success;
    public String msg;
    public ValueBean value;
    public Object code;

    public static class ValueBean {
        /**
         * id : 2
         * createTime : null
         * updateTime : null
         * version : null
         * appName : null
         * appCode : null
         * appType : android
         * appVersion : 1.0.0
         * forceUpdate : 0
         * updateList : null
         * downloadUrl : http://yf.xjmcotton.com/product/download/xjm.apk
         * createUser : null
         */

        public int id;
        public String createTime;
        public String updateTime;
        public String version;
        public String appName;
        public String appCode;
        public String appType;
        public String appVersion;//版本号
        public int forceUpdate;//是否强制更新 1强制更新,0不强制更新
        public String updateList;
        public String downloadUrl;
        public String createUser;
    }
}
