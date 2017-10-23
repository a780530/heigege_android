package com.tianfu.cutton.model;

import java.util.List;

/**
 * Created by admin on 2017/7/12.
 */

public class PurchaseorderbySelfDetailsBean {

    /**
     * success : true
     * msg : null
     * value : {"id":1,"createTime":"2017-06-30 14:11:01","updateTime":"2017-06-30 14:11:01","version":0,"customerId":1,"customerName":null,"purchaseSn":"2017063000001","contacts":"联系人","telephone":"18812345678","receiveDate":"2017-06-01","minPrice":2.3,"maxPrice":3.3,"deadline":"2017-06-02","batchCount":1,"province":"浙江省","city":"杭州市","area":"上城区","address":"详细地址xxxx","remark":"test说明","purchaseStatus":"Purchasing","supplyNum":0,"keyword":["双28","双29","超低价","兵团棉","无三丝","手摘棉","机采棉","3128B"],"origin":["南疆","北疆","东疆","地产棉","进口棉"],"type":["细绒棉","机采棉","皮辊细绒棉","长绒棉"],"colorGrade":["white1","white2","white3","white4","white5","lightSpotted1","lightSpotted2","lightSpotted3","yellowish1","yellowish2","yellowish3","yellow1","yellow2"],"micronGrade":"C1","micronAverage":{"min":1.2,"max":2.3},"breakLoadAverage":{"min":1,"max":2},"lengthAverage":{"min":3,"max":5},"purchaseStatusName":"采购中","colorGrade2":["白棉1级","白棉2级","白棉3级","白棉4级","白棉5级","淡点污棉1级","淡点污棉2级","淡点污棉3级","淡黄染棉1级","淡黄染棉2级","淡黄染棉3级","黄染棉1级","黄染棉2级"],"origin2":["南疆","北疆","东疆","地产棉","进口棉"],"type2":["细绒棉","机采棉","皮辊细绒棉","长绒棉"],"supplyId":null,"supplyStatus":null,"supplyStatusName":null,"invalidStatus":false,"startTime":null,"endTime":null,"sorts":null}
     */

    public boolean success;
    public Object msg;
    public ValueBean value;

    public static class ValueBean {
        /**
         * id : 1
         * createTime : 2017-06-30 14:11:01
         * updateTime : 2017-06-30 14:11:01
         * version : 0
         * customerId : 1
         * customerName : null
         * purchaseSn : 2017063000001
         * contacts : 联系人
         * telephone : 18812345678
         * receiveDate : 2017-06-01
         * minPrice : 2.3
         * maxPrice : 3.3
         * deadline : 2017-06-02
         * batchCount : 1
         * province : 浙江省
         * city : 杭州市
         * area : 上城区
         * address : 详细地址xxxx
         * remark : test说明
         * purchaseStatus : Purchasing
         * supplyNum : 0
         * keyword : ["双28","双29","超低价","兵团棉","无三丝","手摘棉","机采棉","3128B"]
         * origin : ["南疆","北疆","东疆","地产棉","进口棉"]
         * type : ["细绒棉","机采棉","皮辊细绒棉","长绒棉"]
         * colorGrade : ["white1","white2","white3","white4","white5","lightSpotted1","lightSpotted2","lightSpotted3","yellowish1","yellowish2","yellowish3","yellow1","yellow2"]
         * micronGrade : C1
         * micronAverage : {"min":1.2,"max":2.3}
         * breakLoadAverage : {"min":1,"max":2}
         * lengthAverage : {"min":3,"max":5}
         * purchaseStatusName : 采购中
         * colorGrade2 : ["白棉1级","白棉2级","白棉3级","白棉4级","白棉5级","淡点污棉1级","淡点污棉2级","淡点污棉3级","淡黄染棉1级","淡黄染棉2级","淡黄染棉3级","黄染棉1级","黄染棉2级"]
         * origin2 : ["南疆","北疆","东疆","地产棉","进口棉"]
         * type2 : ["细绒棉","机采棉","皮辊细绒棉","长绒棉"]
         * supplyId : null
         * supplyStatus : null
         * supplyStatusName : null
         * invalidStatus : false
         * startTime : null
         * endTime : null
         * sorts : null
         */

        public String id;
        public String createTime;
        public String updateTime;
        public int version;
        public int customerId;
        public Object customerName;
        public String purchaseSn;
        public String contacts;
        public String telephone;
        public String receiveDate;
        public int minPrice;
        public int maxPrice;
        public String deadline;
        public String batchCount;
        public String province;
        public String stateUpdateTime;
        public String city;
        public String area;
        public String address;
        public String address2;
        public String remark;
        public String purchaseStatus;
        public int supplyNum;
        public String micronGrade;
        public MicronAverageBean micronAverage;
        public BreakLoadAverageBean breakLoadAverage;
        public LengthAverageBean lengthAverage;
        public String purchaseStatusName;
        public Object supplyId;
        public Object supplyStatus;
        public Object supplyStatusName;
        public boolean invalidStatus;
        public Object startTime;
        public Object endTime;
        public Object sorts;
        public List<String> keyword;
        public List<String> origin;
        public List<String> type;
        public List<String> colorGrade;
        public List<String> colorGrade2;
        public List<String> origin2;
        public List<String> type2;

        public List<String> getKeyword() {
            return keyword;
        }

        public void setKeyword(List<String> keyword) {
            this.keyword = keyword;
        }

        public static class MicronAverageBean {
            /**
             * min : 1.2
             * max : 2.3
             */

            public double min;
            public double max;
        }

        public static class BreakLoadAverageBean {
            /**
             * min : 1
             * max : 2
             */

            public int min;
            public int max;
        }

        public static class LengthAverageBean {
            /**
             * min : 3
             * max : 5
             */

            public int min;
            public int max;
        }
    }

}
