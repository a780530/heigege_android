package com.tianfu.cutton.model;

import java.util.List;

/**
 * Created by admin on 2017/10/16.
 */

public class SellerBean {

    public boolean success;
    public String msg;
    public Object code;
    public List<ValueBean> value;

    public static class ValueBean {

        public String companyName;
        public String resourceNum;
    }
}
