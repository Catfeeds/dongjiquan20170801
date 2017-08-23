package com.weiaibenpao.demo.chislim.bean;

import java.io.Serializable;

/**
 * Created by zhangxing on 2017/3/9.
 */

public class ResultBean implements Serializable {


    /**
     * code : 100000
     * text : 不理你呢
     */

    private int code;
    private String text;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
