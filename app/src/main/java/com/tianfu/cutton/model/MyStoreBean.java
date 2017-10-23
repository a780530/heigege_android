package com.tianfu.cutton.model;

import java.util.List;

/**
 * Created by admin on 2017/7/27.
 */

public class MyStoreBean {
    public boolean success;
    public String msg;
    public ValueBean value;
    public Object code;
    public static class ValueBean {
        public int total;
        public int totalPage;
        public int currPage;
        public int pageSize;
        public List<RowsBean> rows;
        public static class RowsBean {
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
            public int price;
            public String state;
            public int customerId;
            public String creatUserType;
            public int creatUserId;
            public String mobile;
            public String contact;
            public String takeType;
            public Object remark;
            public Object distance;
            public Object baths;
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
            public String plusBAverage;
            public String vehicleweight;
            public String emptyweight;
            public String tareweight;
            public String netweight;
            public String checkDate;
            public String factoryCode;
            public String factoryAddress;
        }
    }
}
