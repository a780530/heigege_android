package com.tianfu.cutton.model;

import java.util.List;

/**
 * Created by admin on 2017/8/1.
 */

public class SearchBean {


    /**
     * success : true
     * msg : null
     * value : {"total":1,"totalPage":1,"currPage":1,"pageSize":20,"rows":[{"id":"k11","productId":null,"batchType":1,"code":"30000212106","bagCount":880,"batchCount":10,"type":"","colorGrade":"淡黄染棉2级","lengthAverage":28.5,"uniformityIndexAverage":"0","breakLoadAverage":0,"micronAverage":null,"micronGrade":"A","moisture":0,"trash":0,"factory":"","factoryCode":"","factoryAddress":"天津市西青区杨柳青镇新胜利路增1号","districtShortName":"进口","createDate":"2017-07-14 14:26:47","createYear":"2012","property":"2328A","grossweight":"","stdweight":"196.91","vehicleweight":"","emptyweight":"","tareweight":"","netweight":"","checkDate":"","rdAverage":"","plusBAverage":"","origin":"美国","checkStorage":"","isProduct":0,"mobile":"13752347228","contact":"季兰凤","storage":"","storageProvince":"天津","longitude":null,"latitude":null,"price":null,"settlementMethod":"","takeType":"","theLocation":"","distance":12851.08,"attribute":"","batchCode":30000212106,"updateTime":"","kunCode":"","fullFactoryAddress":null}]}
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
         * rows : [{"id":"k11","productId":null,"batchType":1,"code":"30000212106","bagCount":880,"batchCount":10,"type":"","colorGrade":"淡黄染棉2级","lengthAverage":28.5,"uniformityIndexAverage":"0","breakLoadAverage":0,"micronAverage":null,"micronGrade":"A","moisture":0,"trash":0,"factory":"","factoryCode":"","factoryAddress":"天津市西青区杨柳青镇新胜利路增1号","districtShortName":"进口","createDate":"2017-07-14 14:26:47","createYear":"2012","property":"2328A","grossweight":"","stdweight":"196.91","vehicleweight":"","emptyweight":"","tareweight":"","netweight":"","checkDate":"","rdAverage":"","plusBAverage":"","origin":"美国","checkStorage":"","isProduct":0,"mobile":"13752347228","contact":"季兰凤","storage":"","storageProvince":"天津","longitude":null,"latitude":null,"price":null,"settlementMethod":"","takeType":"","theLocation":"","distance":12851.08,"attribute":"","batchCode":30000212106,"updateTime":"","kunCode":"","fullFactoryAddress":null}]
         */

        public int total;
        public int totalPage;
        public int currPage;
        public int pageSize;
        public List<RowsBean> rows;

        public static class RowsBean {
            /**
             * id : k11
             * productId : null
             * batchType : 1
             * code : 30000212106
             * bagCount : 880
             * batchCount : 10
             * type :
             * colorGrade : 淡黄染棉2级
             * lengthAverage : 28.5
             * uniformityIndexAverage : 0
             * breakLoadAverage : 0
             * micronAverage : null
             * micronGrade : A
             * moisture : 0
             * trash : 0
             * factory :
             * factoryCode :
             * factoryAddress : 天津市西青区杨柳青镇新胜利路增1号
             * districtShortName : 进口
             * createDate : 2017-07-14 14:26:47
             * createYear : 2012
             * property : 2328A
             * grossweight :
             * stdweight : 196.91
             * vehicleweight :
             * emptyweight :
             * tareweight :
             * netweight :
             * checkDate :
             * rdAverage :
             * plusBAverage :
             * origin : 美国
             * checkStorage :
             * isProduct : 0
             * mobile : 13752347228
             * contact : 季兰凤
             * storage :
             * storageProvince : 天津
             * longitude : null
             * latitude : null
             * price : null
             * settlementMethod :
             * takeType :
             * theLocation :
             * distance : 12851.08
             * attribute :
             * batchCode : 30000212106
             * updateTime :
             * kunCode :
             * fullFactoryAddress : null
             */

            public String id;
            public Object productId;
            public int batchType;
            public String code;
            public int bagCount;
            public int batchCount;
            public String type;
            public String colorGrade;
            public String lengthAverage;
            public String uniformityIndexAverage;
            public String breakLoadAverage;
            public String micronAverage;
            public String micronGrade;
            public int moisture;
            public int trash;
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
            public int isProduct;
            public String mobile;
            public String contact;
            public String storage;
            public String storageProvince;
            public Object longitude;
            public Object latitude;
            public int price;
            public String settlementMethod;
            public String takeType;
            public String theLocation;
            public String distance;
            public String attribute;
            public long batchCode;
            public String updateTime;
            public String kunCode;
            public Object fullFactoryAddress;
        }
    }
}
