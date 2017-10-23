package com.tianfu.cutton.model;

/**
 * Created by admin on 2017/7/18.
 */

public class MicronAverage {

    public String min;
    public String max;

    public MicronAverage(String min, String max) {
        this.min = min;
        this.max = max;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    @Override
    public String toString() {
        return "MicronAverage{" +
                "min='" + min + '\'' +
                ", max='" + max + '\'' +
                '}';
    }
}
