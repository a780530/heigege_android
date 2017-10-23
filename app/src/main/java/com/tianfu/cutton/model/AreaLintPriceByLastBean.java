package com.tianfu.cutton.model;

import java.util.List;

/**
 * Created by admin on 2017/10/16.
 */

public class AreaLintPriceByLastBean {

    public boolean success;
    public Object msg;
    public Object code;
    public List<ValueBean> value;

    public static class ValueBean {
        public String name;
        public String value;
    }
}
