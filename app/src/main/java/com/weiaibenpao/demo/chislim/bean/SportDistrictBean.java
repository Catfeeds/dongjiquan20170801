package com.weiaibenpao.demo.chislim.bean;

/**
 * Created by lenovo on 2017/1/20.
 */

public class SportDistrictBean {
    public int id;
    public String name;
    public String allDis;
    public String intro;
    public int hot;
    public String image;

    public SportDistrictBean(int id, String name, String allDis, String intro, int hot, String image) {
        this.id = id;
        this.name = name;
        this.allDis = allDis;
        this.intro = intro;
        this.hot = hot;
        this.image = image;
    }

    public SportDistrictBean() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAllDis() {
        return allDis;
    }

    public void setAllDis(String allDis) {
        this.allDis = allDis;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public int getHot() {
        return hot;
    }

    public void setHot(int hot) {
        this.hot = hot;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
