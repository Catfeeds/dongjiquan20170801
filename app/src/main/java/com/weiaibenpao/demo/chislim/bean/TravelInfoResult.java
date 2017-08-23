package com.weiaibenpao.demo.chislim.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lenovo on 2016/10/26.
 */

public class TravelInfoResult implements Serializable{

    /**
     * error : 0
     * travel_info : [{"ti_id":13,"t_id":7,"ti_name":"景点","ti_image":"tra13","ti_text":"文本","ti_del":0},{"ti_id":6,"t_id":7,"ti_name":"景点","ti_image":"tra6,tra3,tra2,tra6,tra7,tra22","ti_text":"文本","ti_del":0}]
     */

    private int error;
    /**
     * ti_id : 13
     * t_id : 7
     * ti_name : 景点
     * ti_image : tra13
     * ti_text : 文本
     * ti_del : 0
     */

    private List<TravelInfoBean> travel_info;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public List<TravelInfoBean> getTravel_info() {
        return travel_info;
    }

    public void setTravel_info(List<TravelInfoBean> travel_info) {
        this.travel_info = travel_info;
    }

    public static class TravelInfoBean implements Serializable{
        private int ti_id;
        private int t_id;
        private String ti_name;
        private String ti_image;
        private String ti_text;
        private int ti_del;

        public int getTi_id() {
            return ti_id;
        }

        public void setTi_id(int ti_id) {
            this.ti_id = ti_id;
        }

        public int getT_id() {
            return t_id;
        }

        public void setT_id(int t_id) {
            this.t_id = t_id;
        }

        public String getTi_name() {
            return ti_name;
        }

        public void setTi_name(String ti_name) {
            this.ti_name = ti_name;
        }

        public String getTi_image() {
            return ti_image;
        }

        public void setTi_image(String ti_image) {
            this.ti_image = ti_image;
        }

        public String getTi_text() {
            return ti_text;
        }

        public void setTi_text(String ti_text) {
            this.ti_text = ti_text;
        }

        public int getTi_del() {
            return ti_del;
        }

        public void setTi_del(int ti_del) {
            this.ti_del = ti_del;
        }
    }
}
