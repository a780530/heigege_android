package com.tianfu.cutton.model;

import java.util.List;

/**
 * Created by admin on 2017/7/18.
 */

public class City {

    /**
     * name : 北京市
     * sub : [{"name":"北京市市辖区","sub":[{"name":"东城区"},{"name":"西城区"},{"name":"朝阳区"},{"name":"丰台区"},{"name":"石景山区"},{"name":"海淀区"},{"name":"门头沟区"},{"name":"房山区"},{"name":"通州区"},{"name":"顺义区"},{"name":"昌平区"},{"name":"大兴区"},{"name":"怀柔区"},{"name":"平谷区"},{"name":"密云区"},{"name":"延庆区"}]}]
     */

    public  String name;
    public List<SubBeanX> sub;

    public static class SubBeanX {
        /**
         * name : 北京市市辖区
         * sub : [{"name":"东城区"},{"name":"西城区"},{"name":"朝阳区"},{"name":"丰台区"},{"name":"石景山区"},{"name":"海淀区"},{"name":"门头沟区"},{"name":"房山区"},{"name":"通州区"},{"name":"顺义区"},{"name":"昌平区"},{"name":"大兴区"},{"name":"怀柔区"},{"name":"平谷区"},{"name":"密云区"},{"name":"延庆区"}]
         */

        public  String name;
        public List<SubBean> sub;

        public static class SubBean {
            /**
             * name : 东城区
             */

            public  String name;
        }
    }
}
