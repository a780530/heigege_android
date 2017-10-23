package com.tianfu.cutton.utils;

import java.util.List;

/**
 * Created by admin on 2017/7/18.
 */

public class ListToListString {

    public static String getString(List<String> list) {
        String str = "[";
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
                str = str + "\"" + list.get(i) + "\"" + "]";
            } else {
                str = str + "\"" + list.get(i) + "\"" + ",";
            }
        }
        return str;
    }
}
