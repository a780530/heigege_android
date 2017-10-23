package com.tianfu.cutton.utils;

import android.text.TextUtils;
import android.widget.TextView;

import com.tianfu.cutton.R;
import com.tianfu.cutton.activity.base.BaseApplication;

/**
 * Created by admin on 2017/8/24.
 */

public class StringJudgeUtils {
    // TODO: 2017/8/24 操作吨
    public static void judgeStringT(String str, TextView textView) {
        if (TextUtils.isEmpty(str)) {
            textView.setText("--");
            return;
        }
        try {
            if (Float.parseFloat(str) != 0) {
                textView.setText(str + "t");
            } else {
                textView.setText("--");
            }
        } catch (Exception e) {
            textView.setText(str);
        }

    }
    // TODO: 2017/8/24 操作千克
    public static void judgeStringKg(String str, TextView textView) {
        if (TextUtils.isEmpty(str)) {
            textView.setText("--");
            return;
        }
        try {//差不多了？
            if (Float.parseFloat(str) != 0) {
                textView.setText(str + "kg");
            } else {
                textView.setText("--");
            }
        } catch (Exception e) {
            textView.setText(str);
        }
    }
    // TODO: 2017/8/24 百分比
    public static void judgeStringP(String str, TextView textView) {
        if (TextUtils.isEmpty(str)) {
            textView.setText("--");
            return;
        }
        try {//差不多了？
            if (Float.parseFloat(str) != 0) {
                textView.setText(str + "%");
            } else {
                textView.setText("--");
            }
        } catch (Exception e) {
            textView.setText(str);
        }
    }
    // TODO: 2017/8/24 操作数字字符串
    public static void judgeStringNumber(String str, TextView textView) {
        if (TextUtils.isEmpty(str)) {
            textView.setText("--");
            return;
        }
        try {//差不多了？
            if (Float.parseFloat(str) != 0) {
                textView.setText(str);
            } else {
                textView.setText("--");
            }
        } catch (Exception e) {
            textView.setText(str);
        }
    }
    public static void judgeStringSts(String str, TextView textView){
        if (!TextUtils.isEmpty(str)) {
            if (str.startsWith("-")) {
                textView.setText(str);
                textView.setTextColor(BaseApplication.getContextObject().getResources().getColor(R.color.sts_text_color_green));
            }else if (str.equals("0")){
                textView.setText(str);
                textView.setTextColor(BaseApplication.getContextObject().getResources().getColor(R.color.drop_color));
            }else{
                textView.setText("+"+str);
                textView.setTextColor(BaseApplication.getContextObject().getResources().getColor(R.color.tab_tv_selected));
            }
        }else{
            textView.setText("--");
        }
    }
}
