package com.weiaibenpao.demo.chislim.bean;

/**
 * Created by lenovo on 2016/10/31.
 */

public class MenuBean {
    public String menuImage;
    public String menuText;

    public MenuBean() {
    }

    public MenuBean(String menuImage, String menuText) {
        this.menuImage = menuImage;
        this.menuText = menuText;
    }

    public String getMenuImage() {
        return menuImage;
    }

    public void setMenuImage(String menuImage) {
        this.menuImage = menuImage;
    }

    public String getMenuText() {
        return menuText;
    }

    public void setMenuText(String menuText) {
        this.menuText = menuText;
    }
}
