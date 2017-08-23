package com.weiaibenpao.demo.chislim.bean;

import java.io.Serializable;

/**
 * Created by lenovo on 2017/1/12.
 */

public class SpiceSportRemberBean implements Serializable{
    String fistCity;        //起始城市
    String secondCity;      //结束城市
    double fromLat;         //起点坐标
    double fromLong;        //起点坐标
    double toLat;           //终点坐标
    double toLong;          //终点坐标
    int position;            //标记
    int disNow;              //当前跑步距离

    public SpiceSportRemberBean(String fistCity, String secondCity, double fromLat, double fromLong, double toLat, double toLong, int position, int disNow) {
        this.fistCity = fistCity;
        this.secondCity = secondCity;
        this.fromLat = fromLat;
        this.fromLong = fromLong;
        this.toLat = toLat;
        this.toLong = toLong;
        this.position = position;
        this.disNow = disNow;
    }

    public SpiceSportRemberBean() {
    }

    public String getFistCity() {
        return fistCity;
    }

    public String getSecondCity() {
        return secondCity;
    }

    public double getFromLat() {
        return fromLat;
    }

    public double getFromLong() {
        return fromLong;
    }

    public double getToLat() {
        return toLat;
    }

    public double getToLong() {
        return toLong;
    }

    public int getPosition() {
        return position;
    }

    public void setFistCity(String fistCity) {
        this.fistCity = fistCity;
    }

    public void setSecondCity(String secondCity) {
        this.secondCity = secondCity;
    }

    public void setFromLat(double fromLat) {
        this.fromLat = fromLat;
    }

    public void setFromLong(double fromLong) {
        this.fromLong = fromLong;
    }

    public void setToLat(double toLat) {
        this.toLat = toLat;
    }

    public void setToLong(double toLong) {
        this.toLong = toLong;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setDisNow(int disNow) {
        this.disNow = disNow;
    }

    public int getDisNow() {
        return disNow;
    }
}
