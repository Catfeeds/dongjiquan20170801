package com.weiaibenpao.demo.chislim.bean;

/**
 * Created by lenovo on 2016/8/13.
 */

public class TeachBean {
    public String teachAdderss;
    public String teachTitle;
    public String imageAddress;
    public String teachMore;

    public TeachBean(String teachAdderss, String teachMore, String imageAddress, String teachTitle) {
        this.teachAdderss = teachAdderss;
        this.teachMore = teachMore;
        this.imageAddress = imageAddress;
        this.teachTitle = teachTitle;
    }
}
