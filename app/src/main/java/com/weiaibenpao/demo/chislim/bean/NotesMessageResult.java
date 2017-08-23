package com.weiaibenpao.demo.chislim.bean;

import java.util.List;

/**
 * Created by lenovo on 2016/10/28.
 */

public class NotesMessageResult {

    /**
     * error : 0
     * travel_message : [{"tm_id":26,"tra_tm_id":0,"tn_id":2,"userID":5,"tm_text":"这个地方我曾经去过","tm_time":"2016.10.20","userName":"000000","tm_del":0},{"tm_id":25,"tra_tm_id":0,"tn_id":2,"userID":5,"tm_text":"这个地方我曾经去过","tm_time":"2016.10.20","userName":"000000","tm_del":0},{"tm_id":24,"tra_tm_id":0,"tn_id":2,"userID":5,"tm_text":"这个地方我曾经去过","tm_time":"2016.10.20","userName":"000000","tm_del":0},{"tm_id":23,"tra_tm_id":0,"tn_id":2,"userID":5,"tm_text":"到地方","tm_time":"2016.10.20","userName":"000000","tm_del":0},{"tm_id":22,"tra_tm_id":0,"tn_id":2,"userID":5,"tm_text":"到地方","tm_time":"2016.10.20","userName":"000000","tm_del":0},{"tm_id":21,"tra_tm_id":0,"tn_id":2,"userID":5,"tm_text":"到地方","tm_time":"2016.10.20","userName":"000000","tm_del":0},{"tm_id":20,"tra_tm_id":0,"tn_id":2,"userID":5,"tm_text":"到地方","tm_time":"2016.10.20","userName":"000000","tm_del":0},{"tm_id":19,"tra_tm_id":0,"tn_id":2,"userID":5,"tm_text":"到地方","tm_time":"2016.10.20","userName":"000000","tm_del":0},{"tm_id":18,"tra_tm_id":0,"tn_id":2,"userID":5,"tm_text":"到地方","tm_time":"2016.10.20","userName":"000000","tm_del":0},{"tm_id":17,"tra_tm_id":0,"tn_id":2,"userID":5,"tm_text":"到地方","tm_time":"2016.10.20","userName":"000000","tm_del":0},{"tm_id":16,"tra_tm_id":0,"tn_id":2,"userID":5,"tm_text":"到地方","tm_time":"2016.10.20","userName":"000000","tm_del":0},{"tm_id":15,"tra_tm_id":0,"tn_id":2,"userID":5,"tm_text":"到地方","tm_time":"2016.10.20","userName":"000000","tm_del":0},{"tm_id":14,"tra_tm_id":0,"tn_id":2,"userID":5,"tm_text":"到地方","tm_time":"2016.10.20","userName":"000000","tm_del":0},{"tm_id":13,"tra_tm_id":0,"tn_id":2,"userID":5,"tm_text":"到地方","tm_time":"2016.10.20","userName":"000000","tm_del":0},{"tm_id":12,"tra_tm_id":0,"tn_id":2,"userID":5,"tm_text":"到地方","tm_time":"2016.10.20","userName":"000000","tm_del":0},{"tm_id":11,"tra_tm_id":0,"tn_id":2,"userID":5,"tm_text":"这个地方我曾经去过","tm_time":"2016.10.20","userName":"000000","tm_del":0},{"tm_id":10,"tra_tm_id":0,"tn_id":2,"userID":5,"tm_text":"到地方","tm_time":"2016.10.20","userName":"000000","tm_del":0},{"tm_id":9,"tra_tm_id":0,"tn_id":2,"userID":5,"tm_text":"到地方","tm_time":"2016.10.20","userName":"000000","tm_del":0},{"tm_id":8,"tra_tm_id":0,"tn_id":2,"userID":5,"tm_text":"到地方","tm_time":"2016.10.20","userName":"000000","tm_del":0},{"tm_id":7,"tra_tm_id":0,"tn_id":2,"userID":5,"tm_text":"到地方","tm_time":"2016.10.20","userName":"000000","tm_del":0},{"tm_id":6,"tra_tm_id":0,"tn_id":2,"userID":5,"tm_text":"到地方","tm_time":"2016.10.20","userName":"000000","tm_del":0},{"tm_id":5,"tra_tm_id":0,"tn_id":2,"userID":5,"tm_text":"到地方","tm_time":"2016.10.20","userName":"000000","tm_del":0},{"tm_id":4,"tra_tm_id":0,"tn_id":2,"userID":5,"tm_text":"到地方","tm_time":"2016.10.20","userName":"000000","tm_del":0},{"tm_id":3,"tra_tm_id":0,"tn_id":2,"userID":5,"tm_text":"到地方","tm_time":"2016.10.20","userName":"000000","tm_del":0}]
     */

    private int error;
    /**
     * tm_id : 26
     * tra_tm_id : 0
     * tn_id : 2
     * userID : 5
     * tm_text : 这个地方我曾经去过
     * tm_time : 2016.10.20
     * userName : 000000
     * tm_del : 0
     */

    private List<TravelMessageBean> travel_message;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public List<TravelMessageBean> getTravel_message() {
        return travel_message;
    }

    public void setTravel_message(List<TravelMessageBean> travel_message) {
        this.travel_message = travel_message;
    }

    public static class TravelMessageBean {
        private int tm_id;
        private int tra_tm_id;
        private int tn_id;
        private int userID;
        private String tm_text;
        private String tm_time;
        private String userName;
        private int tm_del;

        public int getTm_id() {
            return tm_id;
        }

        public void setTm_id(int tm_id) {
            this.tm_id = tm_id;
        }

        public int getTra_tm_id() {
            return tra_tm_id;
        }

        public void setTra_tm_id(int tra_tm_id) {
            this.tra_tm_id = tra_tm_id;
        }

        public int getTn_id() {
            return tn_id;
        }

        public void setTn_id(int tn_id) {
            this.tn_id = tn_id;
        }

        public int getUserID() {
            return userID;
        }

        public void setUserID(int userID) {
            this.userID = userID;
        }

        public String getTm_text() {
            return tm_text;
        }

        public void setTm_text(String tm_text) {
            this.tm_text = tm_text;
        }

        public String getTm_time() {
            return tm_time;
        }

        public void setTm_time(String tm_time) {
            this.tm_time = tm_time;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public int getTm_del() {
            return tm_del;
        }

        public void setTm_del(int tm_del) {
            this.tm_del = tm_del;
        }
    }
}
