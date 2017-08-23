package com.weiaibenpao.demo.chislim.bean;

/**
 * Created by lenovo on 2016/12/5.
 */

public class UploadResult {
    /**
     * code : 0
     * msg : 成功
     * data : 1
     */
    private int code;
    private String msg;
    private int data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}
