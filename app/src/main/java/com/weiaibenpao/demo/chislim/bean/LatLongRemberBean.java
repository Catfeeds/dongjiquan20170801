package com.weiaibenpao.demo.chislim.bean;

/**
 * Created by lenovo on 2016/12/6.
 */

public class LatLongRemberBean {
    double latitude;
    double longitude;
    String sporttime;
    String sportdis;
    String sportcor;
    String sportstep;
    String sportspeed;
    String speedspace;           //配速
    String stepfrequency;       //步频
    String stepscope;           //步幅
    String elevation;

    public LatLongRemberBean() {
    }

    public LatLongRemberBean(double latitude, double longitude, String sporttime, String sportdis,
                             String sportcor, String sportstep, String sportspeed,String speedspace,String stepfrequency,String stepscope,String elevation) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.sporttime = sporttime;
        this.sportdis = sportdis;
        this.sportcor = sportcor;
        this.sportstep = sportstep;
        this.sportspeed = sportspeed;

        this.speedspace = speedspace;
        this.stepfrequency = stepfrequency;
        this.stepscope = stepscope;
        this.elevation = elevation;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getSpeedspace() {
        return speedspace;
    }

    public void setSpeedspace(String speedspace) {
        this.speedspace = speedspace;
    }

    public String getStepfrequency() {
        return stepfrequency;
    }

    public void setStepfrequency(String stepfrequency) {
        this.stepfrequency = stepfrequency;
    }

    public String getStepscope() {
        return stepscope;
    }

    public void setStepscope(String stepscope) {
        this.stepscope = stepscope;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getSporttime() {
        return sporttime;

    }

    public void setSporttime(String sporttime) {
        this.sporttime = sporttime;
    }

    public String getSportdis() {
        return sportdis;
    }

    public void setSportdis(String sportdis) {
        this.sportdis = sportdis;
    }

    public String getSportcor() {
        return sportcor;
    }

    public void setSportcor(String sportcor) {
        this.sportcor = sportcor;
    }

    public String getSportstep() {
        return sportstep;
    }

    public void setSportstep(String sportstep) {
        this.sportstep = sportstep;
    }

    public String getSportspeed() {
        return sportspeed;
    }

    public void setSportspeed(String sportspeed) {
        this.sportspeed = sportspeed;
   }

    public String getElevation() {
        return elevation;
    }

    public void setElevation(String elevation) {
        this.elevation = elevation;
    }
}
