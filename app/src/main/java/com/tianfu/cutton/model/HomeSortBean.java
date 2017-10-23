package com.tianfu.cutton.model;

import java.util.List;

/**
 * Created by admin on 2017/8/10.
 */

public class HomeSortBean {

    /**
     * success : true
     * msg : null
     * value : {"product":["小小小笑笑笑小小小笑笑笑小小小笑笑笑","王林权","为人民服务","李威威","香草"],"purchase":["为人民服务","香草","15726694131","林","哈哈"],"active":["哈哈","王林权","为人民服务","李威威","林"],"supply":["王林权","香草","15726694131","哈哈","18948788872"]}
     * code : null
     */

    public boolean success;
    public Object msg;
    public ValueBean value;
    public Object code;

    public static class ValueBean {
        public List<String> product;
        public List<String> purchase;
        public List<String> active;
        public List<String> supply;
    }
}
