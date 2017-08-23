package com.weiaibenpao.demo.chislim.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lenovo on 2016/10/29.
 */

public class TravelNotesUserItemResult implements Serializable{

    /**
     * error : 0
     * travel_notes_item : [{"tn_item_id":9,"tn_id":1,"tn_item_name":"杭州一日游","tn_item_image":"travelnotes85.jpg,travelnotes86.jpg,travelnotes87.travelnotes88.travelnotes89.travelnotes90.travelnotes91.travelnotes92.travelnotes93.travelnotes94","tn_item_text":"真他么人多","tn_item_time":"2016.10.19","tn_item_del":0,"tn_item_keywords":"杭州，西湖"},{"tn_item_id":8,"tn_id":1,"tn_item_name":"杭州一日游","tn_item_image":"travelnotes75.jpg,travelnotes76.jpg,travelnotes77.travelnotes78.travelnotes79.travelnotes80.travelnotes81.travelnotes82.travelnotes83.travelnotes84","tn_item_text":"真他么人多","tn_item_time":"2016.10.19","tn_item_del":0,"tn_item_keywords":"杭州，西湖"},{"tn_item_id":7,"tn_id":1,"tn_item_name":"杭州一日游","tn_item_image":"travelnotes65.jpg,travelnotes66.jpg,travelnotes67.travelnotes68.travelnotes69.travelnotes70.travelnotes71.travelnotes72.travelnotes73.travelnotes74","tn_item_text":"真他么人多","tn_item_time":"2016.10.19","tn_item_del":0,"tn_item_keywords":"杭州，西湖"},{"tn_item_id":6,"tn_id":1,"tn_item_name":"杭州一日游","tn_item_image":"travelnotes55.jpg,travelnotes56.jpg,travelnotes57.travelnotes58.travelnotes59.travelnotes60.travelnotes61.travelnotes62.travelnotes63.travelnotes64","tn_item_text":"真他么人多","tn_item_time":"2016.10.19","tn_item_del":0,"tn_item_keywords":"杭州，西湖"},{"tn_item_id":5,"tn_id":1,"tn_item_name":"杭州一日游","tn_item_image":"travelnotes45.jpg,travelnotes46.jpg,travelnotes47.travelnotes48.travelnotes49.travelnotes50.travelnotes51.travelnotes52.travelnotes53.travelnotes54","tn_item_text":"真他么人多","tn_item_time":"2016.10.19","tn_item_del":0,"tn_item_keywords":"杭州，西湖"},{"tn_item_id":4,"tn_id":1,"tn_item_name":"杭州一日游","tn_item_image":"travelnotes35.jpg,travelnotes36.jpg,travelnotes37.travelnotes38.travelnotes39.travelnotes40.travelnotes41.travelnotes42.travelnotes43.travelnotes44","tn_item_text":"真他么人多","tn_item_time":"2016.10.19","tn_item_del":0,"tn_item_keywords":"杭州，西湖"},{"tn_item_id":3,"tn_id":1,"tn_item_name":"杭州一日游","tn_item_image":"travelnotes25.jpg,travelnotes26.jpg,travelnotes27.travelnotes28.travelnotes29.travelnotes30.travelnotes31.travelnotes32.travelnotes33.travelnotes34","tn_item_text":"真他么人多","tn_item_time":"2016.10.19","tn_item_del":0,"tn_item_keywords":"杭州，西湖"},{"tn_item_id":2,"tn_id":1,"tn_item_name":"杭州一日游","tn_item_image":"travelnotes14.jpg,travelnotes15.jpg,travelnotes16.jpg,travelnotes17.travelnotes18.travelnotes19.travelnotes20.travelnotes21.travelnotes22.travelnotes23.travelnotes24","tn_item_text":"真他么人多","tn_item_time":"2016.10.19","tn_item_del":0,"tn_item_keywords":"杭州，西湖"},{"tn_item_id":1,"tn_id":1,"tn_item_name":"杭州一日游","tn_item_image":"travelnotes1.jpg,travelnotes2.jpg,travelnotes3.jpg,travelnotes4.jpg,travelnotes5.jpg,travelnotes6.jpg,travelnotes7.jpg,travelnotes8.jpg,travelnotes9.jpg,travelnotes10.jpg,travelnotes11.jpg,travelnotes12.jpg,travelnotes13.jpg","tn_item_text":"真他么人多","tn_item_time":"2016.10.19","tn_item_del":0,"tn_item_keywords":"杭州，西湖"}]
     */

    private int error;
    /**
     * tn_item_id : 9
     * tn_id : 1
     * tn_item_name : 杭州一日游
     * tn_item_image : travelnotes85.jpg,travelnotes86.jpg,travelnotes87.travelnotes88.travelnotes89.travelnotes90.travelnotes91.travelnotes92.travelnotes93.travelnotes94
     * tn_item_text : 真他么人多
     * tn_item_time : 2016.10.19
     * tn_item_del : 0
     * tn_item_keywords : 杭州，西湖
     */

    private List<TravelNotesItemBean> travel_notes_item;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public List<TravelNotesItemBean> getTravel_notes_item() {
        return travel_notes_item;
    }

    public void setTravel_notes_item(List<TravelNotesItemBean> travel_notes_item) {
        this.travel_notes_item = travel_notes_item;
    }

    public static class TravelNotesItemBean {
        private int tn_item_id;
        private int tn_id;
        private String tn_item_name;
        private String tn_item_image;
        private String tn_item_text;
        private String tn_item_time;
        private int tn_item_del;
        private String tn_item_keywords;

        public int getTn_item_id() {
            return tn_item_id;
        }

        public void setTn_item_id(int tn_item_id) {
            this.tn_item_id = tn_item_id;
        }

        public int getTn_id() {
            return tn_id;
        }

        public void setTn_id(int tn_id) {
            this.tn_id = tn_id;
        }

        public String getTn_item_name() {
            return tn_item_name;
        }

        public void setTn_item_name(String tn_item_name) {
            this.tn_item_name = tn_item_name;
        }

        public String getTn_item_image() {
            return tn_item_image;
        }

        public void setTn_item_image(String tn_item_image) {
            this.tn_item_image = tn_item_image;
        }

        public String getTn_item_text() {
            return tn_item_text;
        }

        public void setTn_item_text(String tn_item_text) {
            this.tn_item_text = tn_item_text;
        }

        public String getTn_item_time() {
            return tn_item_time;
        }

        public void setTn_item_time(String tn_item_time) {
            this.tn_item_time = tn_item_time;
        }

        public int getTn_item_del() {
            return tn_item_del;
        }

        public void setTn_item_del(int tn_item_del) {
            this.tn_item_del = tn_item_del;
        }

        public String getTn_item_keywords() {
            return tn_item_keywords;
        }

        public void setTn_item_keywords(String tn_item_keywords) {
            this.tn_item_keywords = tn_item_keywords;
        }
    }
}
