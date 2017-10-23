package com.tianfu.cutton.model;

import java.util.List;

/**
 * Created by admin on 2017/10/15.
 */

public class NewPriceBean {

    public boolean success;
    public Object msg;
    public Object code;
    public List<ValueBean> value;

    public static class ValueBean {
        public int total;
        public int totalPage;//总页数
        public int currPage;//当前页
        public int pageSize;
        public List<String> date;
        public List<List<String>> value;
        public String max;
        public String min;
    }

}
