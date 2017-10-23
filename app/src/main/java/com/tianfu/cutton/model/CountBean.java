package com.tianfu.cutton.model;

/**
 * Created by admin on 2017/7/26.
 */

public class CountBean {

    /**
     * success : true
     * msg : 查询成功
     * value : {"shareCount":"0","supCount":"0","purCount":"2","productCount":"0","favoritesCount":"0"}
     * code : null
     */

    public boolean success;
    public String msg;
    public ValueBean value;
    public Object code;

    public static class ValueBean {
        /**
         * shareCount : 0
         * supCount : 0
         * purCount : 2
         * productCount : 0
         * favoritesCount : 0
         */

        public String shareCount;
        public String supCount;
        public String purCount;
        public String productCount;
        public String favoritesCount;
        public String historyCount;
    }
}
