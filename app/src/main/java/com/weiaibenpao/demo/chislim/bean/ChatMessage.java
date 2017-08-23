package com.weiaibenpao.demo.chislim.bean;

import java.util.Date;

/**
 * Created by zhangxing on 2017/3/9.
 */

public class ChatMessage {
    private String imgStr;
    private String name;
    private String msg;
    private Type type;
    private Date date;

    public ChatMessage(String msg, Type type, Date date) {
        this.msg = msg;
        this.type = type;
        this.date = date;
    }

    public ChatMessage(String name, String msg, Type type, Date date, String imgStr) {
        this.name = name;
        this.msg = msg;
        this.type = type;
        this.date = date;
        this.imgStr = imgStr;
    }

    public ChatMessage(){

    }

    public enum Type {
        INCOMING , OUTCOMING
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getImgStr() {
        return imgStr;
    }

    public void setImgStr(String imgStr) {
        this.imgStr = imgStr;
    }
}
