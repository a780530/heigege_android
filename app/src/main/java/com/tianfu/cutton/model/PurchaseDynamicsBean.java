package com.tianfu.cutton.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2017/10/16.
 */

public class PurchaseDynamicsBean implements Serializable {


    public boolean success;
    public Object msg;
    public Object code;
    public List<ValueBean> value;

    public static class ValueBean implements Serializable{

        public int id;
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
        public String updateUserId;
        public String updateUserType;
        public String micronGrade;
        public MicronAverageBean micronAverage;
        public BreakLoadAverageBean breakLoadAverage;
        public LengthAverageBean lengthAverage;
        public String purchaseStatusName;
        public String supplyId;
        public String supplyStatus;
        public String supplyTime;
        public String supplyStatusName;
        public boolean invalidStatus;
        public String startTime;
        public String endTime;
        public String sorts;
        public String address2;
        public List<String> keyword;
        public List<String> origin;
        public List<String> type;
        public List<String> colorGrade;
        public List<String> colorGrade2;
        public List<String> origin2;
        public List<String> type2;
        public static class MicronAverageBean implements Serializable{
            /**
             * min : 1.2
             * max : 2.3
             */

            public String min;
            public String max;
        }

        public static class BreakLoadAverageBean implements Serializable {
            /**
             * min : 1
             * max : 2
             */

            public String min;
            public String max;
        }

        public static class LengthAverageBean implements Serializable {
            /**
             * min : 3
             * max : 5
             */

            public String min;
            public String max;
        }
    }
}
