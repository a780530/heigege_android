package com.tianfu.cutton.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2017/7/11.
 */

public class PurchaseOrderBySelfBean implements Serializable {
    public boolean success;
    public Object msg;
    public ValueBean value;

    public static class ValueBean implements Serializable {

        public int total;
        public int totalPage;
        public int currPage;
        public int pageSize;
        public List<RowsBean> rows;

        public static class RowsBean implements Serializable {

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
            public List<String> createYear;

            public static class MicronAverageBean implements Serializable {
                /**
                 * min : 1.2
                 * max : 2.3
                 */

                public double min;
                public double max;
            }

            public static class BreakLoadAverageBean implements Serializable {
                /**
                 * min : 1
                 * max : 2
                 */

                public int min;
                public int max;
            }

            public static class LengthAverageBean implements Serializable {
                /**
                 * min : 3
                 * max : 5
                 */

                public int min;
                public int max;
            }
        }
    }
}
