package com.tianfu.cutton.model;

import java.util.List;

/**
 * Created by admin on 2017/7/25.
 */

public class StoreKunMessage {

    /**
     * success : true
     * msg : 查询成功
     * value : [{"id":313,"createTime":"2017-08-23 14:24:33","updateTime":"2017-08-31 15:22:23","version":9,"batchType":"2","kunCode":"","code":"65534162018","bagCount":"186","batchCount":"1","storage":"江西","storageNameTemp":"江西","province":"南昌","storageId":"10738","settlementMethod":"1","price":"","state":"ON","customerId":5,"creatUserType":"customer","creatUserId":5,"mobile":"18679201490","contact":"中华人民共和国","companyName":null,"takeType":"2","remark":null,"distance":"452.35 ","baths":null,"productHost":null,"origin":"南疆","createYear":"2016","uniformityIndexAverage":"83.8","micronGrade":"C","type":"锯齿细绒棉","colorGrade":"无","lengthAverage":"28.6","breakLoadAverage":"28.4","micronAverage":"5.1","moisture":"5.2","trash":"1.0","factory":"阿克苏地区金翔棉业有限责任公司","position":"115.858197,28.682892","grossweight":"42.48","stdweight":"44.23","checkStorage":"新疆维吾尔自治区棉麻公司阿克苏棉麻站","releaseDate":"2017-08-23 14:24:33","property":"","rdAverage":"","plusBAverage":"","vehicleweight":"59.16","emptyweight":"16.68","tareweight":"247.38","netweight":"42.23","checkDate":"2016-10-19","factoryCode":"65534","factoryAddress":"新疆阿克苏地区阿克苏市207省道阿塔公路51公里处","yxxwCount":"0","fullFactoryAddress":"新疆维吾尔自治区阿克苏地区新疆阿克苏地区阿克苏市207省道阿塔公路51公里处"}]
     * code : null
     */

    public boolean success;
    public String msg;
    public Object code;
    public List<ValueBean> value;

    public static class ValueBean {
        /**
         * id : 313
         * createTime : 2017-08-23 14:24:33
         * updateTime : 2017-08-31 15:22:23
         * version : 9
         * batchType : 2
         * kunCode :
         * code : 65534162018
         * bagCount : 186
         * batchCount : 1
         * storage : 江西
         * storageNameTemp : 江西
         * province : 南昌
         * storageId : 10738
         * settlementMethod : 1
         * price :
         * state : ON
         * customerId : 5
         * creatUserType : customer
         * creatUserId : 5
         * mobile : 18679201490
         * contact : 中华人民共和国
         * companyName : null
         * takeType : 2
         * remark : null
         * distance : 452.35
         * baths : null
         * productHost : null
         * origin : 南疆
         * createYear : 2016
         * uniformityIndexAverage : 83.8
         * micronGrade : C
         * type : 锯齿细绒棉
         * colorGrade : 无
         * lengthAverage : 28.6
         * breakLoadAverage : 28.4
         * micronAverage : 5.1
         * moisture : 5.2
         * trash : 1.0
         * factory : 阿克苏地区金翔棉业有限责任公司
         * position : 115.858197,28.682892
         * grossweight : 42.48
         * stdweight : 44.23
         * checkStorage : 新疆维吾尔自治区棉麻公司阿克苏棉麻站
         * releaseDate : 2017-08-23 14:24:33
         * property :
         * rdAverage :
         * plusBAverage :
         * vehicleweight : 59.16
         * emptyweight : 16.68
         * tareweight : 247.38
         * netweight : 42.23
         * checkDate : 2016-10-19
         * factoryCode : 65534
         * factoryAddress : 新疆阿克苏地区阿克苏市207省道阿塔公路51公里处
         * yxxwCount : 0
         * fullFactoryAddress : 新疆维吾尔自治区阿克苏地区新疆阿克苏地区阿克苏市207省道阿塔公路51公里处
         */

        public int id;
        public String createTime;
        public String updateTime;
        public int version;
        public String batchType;
        public String kunCode;
        public String code;
        public String bagCount;
        public String batchCount;
        public String storage;
        public String storageNameTemp;
        public String province;
        public String storageId;
        public String settlementMethod;
        public String price;
        public String state;
        public int customerId;
        public String validDate;
        public String creatUserType;
        public int creatUserId;
        public String mobile;
        public String contact;
        public String companyName;
        public String takeType;
        public Object remark;
        public String distance;
        public Object baths;
        public Object productHost;
        public String origin;
        public String createYear;
        public String uniformityIndexAverage;
        public String micronGrade;
        public String type;
        public String colorGrade;
        public String lengthAverage;
        public String breakLoadAverage;
        public String micronAverage;
        public String moisture;
        public String trash;
        public String factory;
        public String position;
        public String grossweight;
        public String stdweight;
        public String checkStorage;
        public String releaseDate;
        public String property;
        public String rdAverage;
        public String isCheap;
        public String plusBAverage;
        public String vehicleweight;
        public String emptyweight;
        public String tareweight;
        public String netweight;
        public String checkDate;
        public String factoryCode;
        public String factoryAddress;
        public String yxxwCount;
        public String sts;
        public String premiumJson;
        public String fullFactoryAddress;
        public  String referencBeasePrice;
    }
}
