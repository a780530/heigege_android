package com.tianfu.cutton.model;

import java.util.List;

/**
 * Created by admin on 2017/7/21.
 */

public class KunGetBatch {



    public boolean success;
    public String msg;
    public ValueBean value;
    public String code;

    public static class ValueBean {

        public String total;
        public String totalPage;
        public String currPage;
        public String pageSize;
        public List<RowsBean> rows;
        public static class RowsBean {
            public String id;
            public String type;
            public String property;
            public String createTime;
            public String updateTime;
            public String version;
            public String kunImId;
            public String kunCode;
            public String batchCode;
            public String totalBag;
            public String moisture;
            public String trash;
            public String stdweight;
            public String grossweight;
            public String lengthGrade;
            public String lengthAverage;
            public String length25;
            public String length26;
            public String length27;
            public String length28;
            public String length29;
            public String length30;
            public String length31;
            public String length32;
            public String colorGrade;
            public String white1;
            public String white2;
            public String white3;
            public String white4;
            public String white5;
            public String lightSpotted1;
            public String lightSpotted2;
            public String lightSpotted3;
            public String yellowish1;
            public String yellowish2;
            public String yellowish3;
            public String yellow1;
            public String yellow2;
            public String micronGrade;
            public String micronAverage;
            public String micronGradeA1Rate;
            public String micronGradeB1Rate;
            public String micronGradeB2Rate;
            public String micronGradeC1Rate;
            public String micronGradeC2Rate;
            public String breakLoadAverage;
            public String breakLoadMax;
            public String breakLoadMin;
            public String uniformityIndexAverage;
            public String uniformityIndexMax;
            public String uniformityIndexMin;
            public String rdAverage;
            public String plusBAverage;
            public String factory;
            public String checkStorage;
            public String year;
            public String origin;
        }
    }
}
