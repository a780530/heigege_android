package com.tianfu.cutton.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2017/7/14.
 */

public class ListSupplyOrderBySelfBean implements Serializable {

    /**
     * success : true
     * msg : null
     * value : {"total":1,"totalPage":1,"currPage":1,"pageSize":20,"rows":[{"id":188,"createTime":"2017-07-14 14:44:40","updateTime":"2017-07-14 17:25:00","version":5,"customerId":70,"customerName":"嘿嘿","purchaseSn":"2017071400019","contacts":"嘿嘿","telephone":"18268931633","receiveDate":null,"minPrice":0,"maxPrice":0,"deadline":null,"batchCount":null,"province":"河北省","city":"石家庄市","area":"长安区","address":"哈哈哈","remark":"","purchaseStatus":"Purchasing","supplyNum":1,"stateUpdateTime":"2017-07-14 17:25:00","keyword":[],"origin":[],"type":[],"colorGrade":[],"micronGrade":null,"micronAverage":{"min":3.4,"max":5},"breakLoadAverage":{"min":24,"max":33},"lengthAverage":{"min":26,"max":32},"purchaseStatusName":"采购中","colorGrade2":null,"origin2":null,"type2":null,"supplyId":"57","supplyStatus":"Supply","supplyTime":"2017-07-14 17:50:47","supplyStatusName":"已供货","invalidStatus":false,"startTime":null,"endTime":null,"sorts":null,"address2":"河北省石家庄市长安区哈哈哈"}]}
     * code : null
     */

    public boolean success;
    public Object msg;
    public ValueBean value;
    public Object code;

    public static class ValueBean implements Serializable{
        /**
         * total : 1
         * totalPage : 1
         * currPage : 1
         * pageSize : 20
         * rows : [{"id":188,"createTime":"2017-07-14 14:44:40","updateTime":"2017-07-14 17:25:00","version":5,"customerId":70,"customerName":"嘿嘿","purchaseSn":"2017071400019","contacts":"嘿嘿","telephone":"18268931633","receiveDate":null,"minPrice":0,"maxPrice":0,"deadline":null,"batchCount":null,"province":"河北省","city":"石家庄市","area":"长安区","address":"哈哈哈","remark":"","purchaseStatus":"Purchasing","supplyNum":1,"stateUpdateTime":"2017-07-14 17:25:00","keyword":[],"origin":[],"type":[],"colorGrade":[],"micronGrade":null,"micronAverage":{"min":3.4,"max":5},"breakLoadAverage":{"min":24,"max":33},"lengthAverage":{"min":26,"max":32},"purchaseStatusName":"采购中","colorGrade2":null,"origin2":null,"type2":null,"supplyId":"57","supplyStatus":"Supply","supplyTime":"2017-07-14 17:50:47","supplyStatusName":"已供货","invalidStatus":false,"startTime":null,"endTime":null,"sorts":null,"address2":"河北省石家庄市长安区哈哈哈"}]
         */

        public int total;
        public int totalPage;
        public int currPage;
        public int pageSize;
        public List<RowsBean> rows;

        public static class RowsBean implements Serializable {
            /**
             * id : 188
             * createTime : 2017-07-14 14:44:40
             * updateTime : 2017-07-14 17:25:00
             * version : 5
             * customerId : 70
             * customerName : 嘿嘿
             * purchaseSn : 2017071400019
             * contacts : 嘿嘿
             * telephone : 18268931633
             * receiveDate : null
             * minPrice : 0
             * maxPrice : 0
             * deadline : null
             * batchCount : null
             * province : 河北省
             * city : 石家庄市
             * area : 长安区
             * address : 哈哈哈
             * remark :
             * purchaseStatus : Purchasing
             * supplyNum : 1
             * stateUpdateTime : 2017-07-14 17:25:00
             * keyword : []
             * origin : []
             * type : []
             * colorGrade : []
             * micronGrade : null
             * micronAverage : {"min":3.4,"max":5}
             * breakLoadAverage : {"min":24,"max":33}
             * lengthAverage : {"min":26,"max":32}
             * purchaseStatusName : 采购中
             * colorGrade2 : null
             * origin2 : null
             * type2 : null
             * supplyId : 57
             * supplyStatus : Supply
             * supplyTime : 2017-07-14 17:50:47
             * supplyStatusName : 已供货
             * invalidStatus : false
             * startTime : null
             * endTime : null
             * sorts : null
             * address2 : 河北省石家庄市长安区哈哈哈
             */

            public String id;
            public String createTime;
            public String updateTime;
            public int version;
            public int customerId;
            public String customerName;
            public String purchaseSn;
            public String contacts;
            public String telephone;
            public String receiveDate;
            public int minPrice;
            public int maxPrice;
            public String deadline;
            public String batchCount;
            public String province;
            public String city;
            public String area;
            public String address;
            public String remark;
            public String purchaseStatus;
            public int supplyNum;
            public String stateUpdateTime;
            public Object micronGrade;
            public MicronAverageBean micronAverage;
            public BreakLoadAverageBean breakLoadAverage;
            public LengthAverageBean lengthAverage;
            public String purchaseStatusName;
            public List<String> colorGrade2;
            public List<String> origin2;
            public List<String> type2;
            public String supplyId;
            public String supplyStatus;
            public String supplyTime;
            public String supplyStatusName;
            public boolean invalidStatus;
            public Object startTime;
            public Object endTime;
            public Object sorts;
            public String address2;
            public List<String> keyword;
            public List<String> origin;
            public List<String> type;
            public List<String> colorGrade;

            public static class MicronAverageBean implements Serializable{
                /**
                 * min : 3.4
                 * max : 5
                 */

                public String min;
                public String max;
            }

            public static class BreakLoadAverageBean implements Serializable {
                /**
                 * min : 24
                 * max : 33
                 */

                public String min;
                public String max;
            }

            public static class LengthAverageBean implements Serializable{
                /**
                 * min : 26
                 * max : 32
                 */

                public String min;
                public String max;
            }
        }
    }
}
