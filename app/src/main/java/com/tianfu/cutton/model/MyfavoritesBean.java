package com.tianfu.cutton.model;

import java.util.List;

/**
 * Created by admin on 2017/7/24.
 */

public class MyfavoritesBean {
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
            public String isProduct;
            public int goodsId;
            public String code;
            public String batchType;
            public String bagCount;
            public String batchCount;
            public String type;
            public String colorGrade;
            public String lengthAverage;
            public String breakLoadAverage;
            public String micronAverage;
            public String moisture;
            public String trash;
            public String stdweightPrice;
            public String factory;
            public String contact;
            public String mobile;
            public String storage;
            public int price;
            public String takeType;
            public String settlementMethod;
            public String checkStorage;
            public String releaseDate;
            public int customerId;
            public String state;
            public String property;
            public String grossweight;
            public String stdweight;
            public String createYear;
            public String distance;
            public String longitude;
            public String latitude;
            public String isCheap;
            public String isRule;
            //添加字段 标记选中状态
            public boolean isSelect;
            public String sts;
        }
    }
}
