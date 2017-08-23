package com.weiaibenpao.demo.chislim.bean;

/**
 * Created by lenovo on 2016/11/10.
 */

public class ApkVersionBean {

    /**
     * apkid : 1
     * apkName : chislim
     * apkUrl : http://112.74.28.179:8080/Chislim/apk/app-release.apk
     * apkVersion : 1.0.001
     * apkText : 你好，apk更新\n请安装
     * apkTab : 1
     */

    private int apkid;
    private String apkName;
    private String apkUrl;
    private String apkVersion;
    private String apkText;
    private int apkTab;

    public int getApkid() {
        return apkid;
    }

    public void setApkid(int apkid) {
        this.apkid = apkid;
    }

    public String getApkName() {
        return apkName;
    }

    public void setApkName(String apkName) {
        this.apkName = apkName;
    }

    public String getApkUrl() {
        return apkUrl;
    }

    public void setApkUrl(String apkUrl) {
        this.apkUrl = apkUrl;
    }

    public String getApkVersion() {
        return apkVersion;
    }

    public void setApkVersion(String apkVersion) {
        this.apkVersion = apkVersion;
    }

    public String getApkText() {
        return apkText;
    }

    public void setApkText(String apkText) {
        this.apkText = apkText;
    }

    public int getApkTab() {
        return apkTab;
    }

    public void setApkTab(int apkTab) {
        this.apkTab = apkTab;
    }
}
