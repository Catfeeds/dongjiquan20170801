package com.weiaibenpao.demo.chislim.bean;

/**
 * Created by lenovo on 2017/1/3.
 */

public class SportDisSpiceBean {
    public int id;
    public String fistCity;
    public String secondCity;
    public double fromLat;
    public double fromLong;
    public double toLat;
    public double toLong;
    public String image;
    public String title;
    public String con;
    public int hot;

    public SportDisSpiceBean() {
    }

    public SportDisSpiceBean(int id, String fistCity, String secondCity, double fromLat, double fromLong, double toLat, double toLong, String image, String title, String con, int hot) {
        this.id = id;
        this.fistCity = fistCity;
        this.secondCity = secondCity;
        this.fromLat = fromLat;
        this.fromLong = fromLong;
        this.toLat = toLat;
        this.toLong = toLong;
        this.image = image;
        this.title = title;
        this.con = con;
        this.hot = hot;
    }
}
