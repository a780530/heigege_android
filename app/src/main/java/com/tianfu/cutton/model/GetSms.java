package com.tianfu.cutton.model;

/**
 * Created by admin on 2017/7/4.
 */

public class GetSms {

    /**
     * success : true
     * msg : 发送成功086217测试完成后将此提示修改
     * value : null
     * code : null
     */

    private boolean success;
    private String msg;
    private Object value;
    private Object code;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Object getCode() {
        return code;
    }

    public void setCode(Object code) {
        this.code = code;
    }
}
