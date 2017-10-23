package com.tianfu.cutton.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by admin on 2017/7/21.
 */

public class QualityBean implements Serializable {
    public boolean success;
    public String msg;
    public ValueBean value;
    public String code;
    public static class ValueBean implements Serializable{
        public String total;
        public String totalPage;
        public String currPage;
        public String pageSize;
        public List<RowsBean> rows;
        public static class RowsBean implements Serializable{
            public String id;
            public String releaseDate;
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
            public String price;
            public String settlementMethod;
            public String takeType;
            public String theLocation;
            public String distance;
            public String attribute;
            public String batchCode;
            public String updateTime;
            //添加字段 标记选中状态
            public boolean isSelect;
        }
    }
}
