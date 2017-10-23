package com.tianfu.cutton.model;

import java.util.List;

/**
 * Created by admin on 2017/7/13.
 */

public class ListSupplierByPurchaseIdBean {

    /**
     * success : true
     * msg : null
     * value : {"total":1,"totalPage":1,"currPage":1,"pageSize":20,"rows":[{"id":1,"createTime":"2017-06-28 21:57:33","updateTime":"2017-06-28 21:58:38","version":1,"customerId":1,"customerName":null,"purchaseId":1,"contacts":"test联系人","mobile":"18812345678","remark":"test备注","supplyStatus":"Supply","code":null,"supplyStatusName":"已供货"}]}
     */

    public boolean success;
    public Object msg;
    public ValueBean value;

    public static class ValueBean {
        /**
         * total : 1
         * totalPage : 1
         * currPage : 1
         * pageSize : 20
         * rows : [{"id":1,"createTime":"2017-06-28 21:57:33","updateTime":"2017-06-28 21:58:38","version":1,"customerId":1,"customerName":null,"purchaseId":1,"contacts":"test联系人","mobile":"18812345678","remark":"test备注","supplyStatus":"Supply","code":null,"supplyStatusName":"已供货"}]
         */

        public int total;
        public int totalPage;
        public int currPage;
        public int pageSize;
        public List<RowsBean> rows;

        public static class RowsBean {
            /**
             * id : 1
             * createTime : 2017-06-28 21:57:33
             * updateTime : 2017-06-28 21:58:38
             * version : 1
             * customerId : 1
             * customerName : null
             * purchaseId : 1
             * contacts : test联系人
             * mobile : 18812345678
             * remark : test备注
             * supplyStatus : Supply
             * code : null
             * supplyStatusName : 已供货
             */

            public int id;
            public String createTime;
            public String updateTime;
            public int version;
            public int customerId;
            public Object customerName;
            public int purchaseId;
            public String contacts;
            public String mobile;
            public String remark;
            public String supplyStatus;
            public Object code;
            public String supplyStatusName;
        }
    }
}
