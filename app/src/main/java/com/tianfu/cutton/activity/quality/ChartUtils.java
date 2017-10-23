package com.tianfu.cutton.activity.quality;

import com.tianfu.cutton.model.QualityBagBean;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by admin on 2017/8/5.
 */

public class ChartUtils {
    /**
     *
     * @param bagBean
     * @return Key: 长度值 Value 百分比
     */
    public static TreeMap<String,Float> getLenght(QualityBagBean bagBean) {
        TreeMap<String,Float> map = new TreeMap(new Comparator() {
            @Override
            public int compare(Object o1, Object o2) {
                return o1.toString().compareTo(o2.toString());
            }
        });
        float count = bagBean.value.rows.size();
//        bagBean.value.
        for (QualityBagBean.ValueBean.RowsBean bean : bagBean.value.rows) {
            try {
                String lenghtValue = bean.length.split("\\.")[0];
            } catch (Exception e) {
                continue;
            }
            String lenghtValue = bean.length.split("\\.")[0];
            if (!map.containsKey(lenghtValue)) {
                map.put(lenghtValue, (float) 1);
            } else {
                map.put(lenghtValue,map.get(lenghtValue).floatValue()+1);
            }
        }
        for (Map.Entry<String, Float> entry : map.entrySet()) {
            // 保留一位小数
            BigDecimal   b  =   new BigDecimal(entry.getValue()/ count *100);
            float   f1   =  b.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
            map.put(entry.getKey(), f1);
        }
        return map;
    }

    /**
     *
     * @param bagBean
     * @return Key: 轧工质量 Value 百分比
     */
    public static HashMap<String,Float> getYg(QualityBagBean bagBean) {
        HashMap<String,Float> map = new HashMap();
        float count = bagBean.value.rows.size();
        for (QualityBagBean.ValueBean.RowsBean bean : bagBean.value.rows) {

            String yg = bean.yg;
            if (!map.containsKey(yg)) {
                map.put(yg, (float) 1);
            } else {
                map.put(yg,map.get(yg).floatValue()+1);
            }
        }
        for (Map.Entry<String, Float> entry : map.entrySet()) {
            // 保留一位小数
            BigDecimal   b  =   new BigDecimal(entry.getValue()/ count *100);
            float   f1   =  b.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();
            map.put(entry.getKey(), f1);
            System.out.println("yg " + entry.getKey() + "  value  " + entry.getValue());
        }
        return map;
    }
}
