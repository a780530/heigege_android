package com.tianfu.cutton.model;

import java.util.List;

/**
 * Created by admin on 2017/8/3.
 */

public class SearchTestbean {

    /**
     * success : true
     * msg : null
     * value : {"total":1,"totalPage":1,"currPage":1,"pageSize":20,"rows":[{"id":"b271323","productId":null,"batchType":2,"code":"65423161007","bagCount":186,"batchCount":1,"type":"锯齿细绒棉","colorGrade":"白棉3级","lengthAverage":30,"uniformityIndexAverage":"84.1","breakLoadAverage":28,"micronAverage":4.7,"micronGrade":"B","moisture":9.6,"trash":2.5,"factory":"乌苏市新鑫棉业有限责任公司","factoryCode":"65423","factoryAddress":"","districtShortName":"塔城","createDate":"2017-07-13 18:00:42","createYear":"2016","property":"3130B","grossweight":"42.22","stdweight":"41.55","vehicleweight":"60.1","emptyweight":"17.88","tareweight":"252.96","netweight":"41.97","checkDate":"2016-10-29","rdAverage":"","plusBAverage":"","origin":"北疆","checkStorage":"新疆生产建设兵团奎屯储运有限公司","isProduct":0,"mobile":"","contact":"","storage":"","storageProvince":"","longitude":null,"latitude":null,"price":null,"settlementMethod":"","takeType":"","theLocation":"","distance":12851.08,"attribute":"","batchCode":65423161007,"updateTime":"","kunCode":"","fullFactoryAddress":null}]}
     * code : null
     */

    public boolean success;
    public Object msg;
    public ValueBean value;
    public Object code;

    public static class ValueBean {
        /**
         * total : 1
         * totalPage : 1
         * currPage : 1
         * pageSize : 20
         * rows : [{"id":"b271323","productId":null,"batchType":2,"code":"65423161007","bagCount":186,"batchCount":1,"type":"锯齿细绒棉","colorGrade":"白棉3级","lengthAverage":30,"uniformityIndexAverage":"84.1","breakLoadAverage":28,"micronAverage":4.7,"micronGrade":"B","moisture":9.6,"trash":2.5,"factory":"乌苏市新鑫棉业有限责任公司","factoryCode":"65423","factoryAddress":"","districtShortName":"塔城","createDate":"2017-07-13 18:00:42","createYear":"2016","property":"3130B","grossweight":"42.22","stdweight":"41.55","vehicleweight":"60.1","emptyweight":"17.88","tareweight":"252.96","netweight":"41.97","checkDate":"2016-10-29","rdAverage":"","plusBAverage":"","origin":"北疆","checkStorage":"新疆生产建设兵团奎屯储运有限公司","isProduct":0,"mobile":"","contact":"","storage":"","storageProvince":"","longitude":null,"latitude":null,"price":null,"settlementMethod":"","takeType":"","theLocation":"","distance":12851.08,"attribute":"","batchCode":65423161007,"updateTime":"","kunCode":"","fullFactoryAddress":null}]
         */

        public int total;
        public int totalPage;
        public int currPage;
        public int pageSize;
        public List<RowsBean> rows;

        public static class RowsBean {
            /**
             * id : b271323
             * productId : null
             * batchType : 2
             * code : 65423161007
             * bagCount : 186
             * batchCount : 1
             * type : 锯齿细绒棉
             * colorGrade : 白棉3级
             * lengthAverage : 30.0
             * uniformityIndexAverage : 84.1
             * breakLoadAverage : 28.0
             * micronAverage : 4.7
             * micronGrade : B
             * moisture : 9.6
             * trash : 2.5
             * factory : 乌苏市新鑫棉业有限责任公司
             * factoryCode : 65423
             * factoryAddress :
             * districtShortName : 塔城
             * createDate : 2017-07-13 18:00:42
             * createYear : 2016
             * property : 3130B
             * grossweight : 42.22
             * stdweight : 41.55
             * vehicleweight : 60.1
             * emptyweight : 17.88
             * tareweight : 252.96
             * netweight : 41.97
             * checkDate : 2016-10-29
             * rdAverage :
             * plusBAverage :
             * origin : 北疆
             * checkStorage : 新疆生产建设兵团奎屯储运有限公司
             * isProduct : 0
             * mobile :
             * contact :
             * storage :
             * storageProvince :
             * longitude : null
             * latitude : null
             * price : null
             * settlementMethod :
             * takeType :
             * theLocation :
             * distance : 12851.08
             * attribute :
             * batchCode : 65423161007
             * updateTime :
             * kunCode :
             * fullFactoryAddress : null
             */

            public String id;
            public String productId;
            public String batchType;
            public String code;
            public String bagCount;
            public String batchCount;
            public String type;
            public String colorGrade;
            public String lengthAverage;
            public String uniformityIndexAverage;
            public String breakLoadAverage;
            public String micronAverage;
            public String micronGrade;
            public String moisture;
            public String trash;
            public String sts;
            public String factory;
            public String factoryCode;
            public String factoryAddress;
            public String districtShortName;
            public String createDate;
            public String createYear;
            public String property;
            public String grossweight;
            public String stdweight;
            public String vehicleweight;
            public String emptyweight;
            public String tareweight;
            public String netweight;
            public String checkDate;
            public String rdAverage;
            public String plusBAverage;
            public String origin;
            public String checkStorage;
            public String isProduct;
            public String mobile;
            public String contact;
            public String storage;
            public String storageProvince;
            public String longitude;
            public String latitude;
            public int price;
            public String settlementMethod;
            public String takeType;
            public String theLocation;
            public String distance;
            public String attribute;
            public String batchCode;
            public String updateTime;
            public String kunCode;
            public String fullFactoryAddress;
            public String isRule;
            public String isCheap;
        }
    }
}
