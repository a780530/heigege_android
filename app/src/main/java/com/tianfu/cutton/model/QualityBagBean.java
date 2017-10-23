package com.tianfu.cutton.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2017/8/3.
 */

public class QualityBagBean {


    public boolean success;
    public Object msg;
    public ValueBean value;
    public Object code;

    public static class ValueBean {

        public int total;
        public int totalPage;
        public int currPage;
        public int pageSize;
        public List<RowsBean> rows =new ArrayList<>();

        public static class RowsBean {

            public long id;
            public int batchId;
            public String batchCode;
            public String barCode;
            public String lab;
            public String code;
            public String publishDate;
            public String createDate;
            public String checkDate;
            public String name;
            public String origin;
            public String factory;
            public String type;
            public String standard;
            public String checkOrg;
            public String colorGrade;
            public String lengthGrade;
            public String yg;
            public String micron;
            public String micronLevel;
            public String micronGrade;
            public String grade;
            public String hasForegionFiber;
            public String rd;
            public String length;
            public String plusB;
            public String uniformityIndex;
            public String breakLoad;
            public Object remark;
            public long createTime;
            public long updateTime;
            public int year;
            public Object version;
        }
    }
}
