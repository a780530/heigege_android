package com.tianfu.cutton.model;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by admin on 2017/7/18.
 */

public class SerializableMap implements Serializable{
    private Map<String,String> map;

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String, String> map) {
        this.map = map;
    }
}
