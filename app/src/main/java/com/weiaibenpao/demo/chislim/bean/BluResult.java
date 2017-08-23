package com.weiaibenpao.demo.chislim.bean;

import java.io.Serializable;

/**
 * Created by lenovo on 2016/11/24.
 * "code":0,"slopeFlag":true,"state":100,”maxSpeed”:15,”maxSlope”:10,"nowBean":{"calories":1000,"distance":10.8,"heart":77,"slope":3,"speed":5.5,"time":"10.22.45"}}
 * {“EE,c0,sf0,st100,mp10,ms5,cor200,dis10,h8,s3,p5,t10.22,55,FF”}
 */

public class BluResult implements Serializable {
    public String code;
    public String slopeFlag;
    public String state;
    public String maxSpeed;
    public String maxSlope;
    public String minSpeed;
    public String calories;
    public String distance;
    public String heart;
    public String slope;
    public String speed;
    public String time;
}
