package com.weiaibenpao.demo.chislim.bean;

import java.io.Serializable;

/**
 * Created by zhangxing on 2017/1/21.
 */

public class SportCustomsBean implements Serializable {
    private String currentCity;
    private String currentDistance;//当前距离
    private String allDistance;//总距离
    private String currentTime;//当前时间

    public SportCustomsBean(String currentCity, String currentDistance, String allDstance, String currentTime) {
        this.currentCity = currentCity;
        this.currentDistance = currentDistance;
        this.allDistance = allDistance;
        this.currentTime = currentTime;
    }

    public String getCurrentDistance() {
        return currentDistance;
    }

    public void setCurrentDistance(String currentDistance) {
        this.currentDistance = currentDistance;
    }

    public String getAllDistance() {
        return allDistance;
    }

    public void setAllDistance(String allDistance) {
        this.allDistance = allDistance;
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }

    public String getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(String currentCity) {
        this.currentCity = currentCity;
    }
}
