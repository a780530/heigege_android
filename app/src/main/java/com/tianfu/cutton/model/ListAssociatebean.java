package com.tianfu.cutton.model;

import java.util.List;

/**
 * Created by admin on 2017/8/1.
 */

public class ListAssociatebean {

    /**
     * success : true
     * msg : null
     * value : [{"keyword":"山东","type":3,"value":"山东高棉集团有限公司"},{"keyword":"山东","type":3,"value":"山东金乡天元棉业有限公司"},{"keyword":"山东","type":3,"value":"山东武城天元棉业有限公司"},{"keyword":"山东","type":3,"value":"山东润丰种业有限公司"},{"keyword":"山东","type":3,"value":"山东鑫诚棉业有限公司"},{"keyword":"山东","type":3,"value":"山东巨野鲁棉天元棉业有限公司"},{"keyword":"山东","type":3,"value":"山东兴棉种业有限公司"},{"keyword":"山东","type":3,"value":"山东高棉棉业有限公司"},{"keyword":"山东","type":3,"value":"山东省惠民县联谊棉业有限责任公司"},{"keyword":"山东","type":3,"value":"山东永佳集团有限公司"},{"keyword":"山东","type":3,"value":"山东巨野华纬棉业有限公司"},{"keyword":"山东","type":3,"value":"山东高鑫种业有限公司"},{"keyword":"山东","type":3,"value":"山东省博兴县润来油棉有限公司"},{"keyword":"山东","type":3,"value":"山东垦利天元棉业有限公司"},{"keyword":"山东","type":3,"value":"山东圣源棉业有限公司"},{"keyword":"山东","type":3,"value":"山东省寿光市巨兴油棉有限公司"},{"keyword":"山东","type":3,"value":"山东省博兴县华茂棉业有限公司"},{"keyword":"山东","type":3,"value":"山东省博兴县永盛棉业有限公司"},{"keyword":"山东","type":3,"value":"山东智德纺织有限公司"},{"keyword":"山东","type":3,"value":"山东省博兴县农盛棉业有限公司"}]
     * code : null
     */

    public boolean success;
    public String msg;
    public String code;
    public List<ValueBean> value;

    public static class ValueBean {
        /**
         * keyword : 山东
         * type : 3
         * value : 山东高棉集团有限公司
         */

        public String keyword;
        public int type;
        public String value;
    }
}
