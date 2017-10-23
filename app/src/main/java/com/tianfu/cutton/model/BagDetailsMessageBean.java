package com.tianfu.cutton.model;

import java.io.Serializable;

/**
 * Created by admin on 2017/8/7.
 */

public class BagDetailsMessageBean implements Serializable {

    /**
     * success : true
     * msg : null
     * value : {"id":2016000000016346384,"batchId":306171,"batchCode":"65140161077","barCode":"32651401611141221550000121114104","lab":"阿克苏实验室","code":"114104","publishDate":"2016-11-20","createDate":"2016-11-14","checkDate":"2016-11-20","name":"细绒棉","origin":"新疆","factory":"沙雅国泰棉花有限公司","type":"锯齿细绒棉","standard":"GB1103.1-2012","checkOrg":"阿克苏地区纤维检验所","colorGrade":"白棉3级","lengthGrade":"28毫米","yg":"P2","micron":"4.6","micronLevel":"B2","micronGrade":"B","grade":"/","hasForegionFiber":"0","rd":"81.3","length":"28.2","plusB":"7.4","uniformityIndex":"82.5","breakLoad":"26.4","remark":null,"createTime":1500464330000,"updateTime":1500464330000,"year":2016,"version":null}
     * code : null
     */

    public boolean success;
    public String msg;
    public ValueBean value;
    public Object code;

    public static class ValueBean implements Serializable{
        /**
         * id : 2016000000016346384
         * batchId : 306171
         * batchCode : 65140161077
         * barCode : 32651401611141221550000121114104
         * lab : 阿克苏实验室
         * code : 114104
         * publishDate : 2016-11-20
         * createDate : 2016-11-14
         * checkDate : 2016-11-20
         * name : 细绒棉
         * origin : 新疆
         * factory : 沙雅国泰棉花有限公司
         * type : 锯齿细绒棉
         * standard : GB1103.1-2012
         * checkOrg : 阿克苏地区纤维检验所
         * colorGrade : 白棉3级
         * lengthGrade : 28毫米
         * yg : P2
         * micron : 4.6
         * micronLevel : B2
         * micronGrade : B
         * grade : /
         * hasForegionFiber : 0
         * rd : 81.3
         * length : 28.2
         * plusB : 7.4
         * uniformityIndex : 82.5
         * breakLoad : 26.4
         * remark : null
         * createTime : 1500464330000
         * updateTime : 1500464330000
         * year : 2016
         * version : null
         */

        public String id;
        public String batchId;
        public String batchCode;
        public String barCode;
        public String lab;
        public String code;
        public String publishDate;
        public String createDate;
        public String checkDate;
        public String name;
        public String origin;
        public String factory;
        public String type;
        public String standard;
        public String checkOrg;
        public String colorGrade;
        public String lengthGrade;
        public String yg;
        public String micron;
        public String micronLevel;
        public String micronGrade;
        public String grade;
        public String hasForegionFiber;
        public String rd;
        public String length;
        public String plusB;
        public String uniformityIndex;
        public String breakLoad;
        public String remark;
        public String createTime;
        public String updateTime;
        public String year;
        public String version;
    }
}
