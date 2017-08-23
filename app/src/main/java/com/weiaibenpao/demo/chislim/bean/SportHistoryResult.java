package com.weiaibenpao.demo.chislim.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lenovo on 2016/11/26.
 */

public class SportHistoryResult implements Serializable{


    /**
     * error : 0
     * everyDaySport : [{"sportID":25,"userID":17,"dayTime":"2016.02.01","distance":1110,"calories":15974,"sportTime":10,"sportImage":"jsaknakdnkand","sportSpeed":"asnda","sportStep":0}]
     */

    private int error;
    private List<EveryDaySportBean> everyDaySport;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public List<EveryDaySportBean> getEveryDaySport() {
        return everyDaySport;
    }

    public void setEveryDaySport(List<EveryDaySportBean> everyDaySport) {
        this.everyDaySport = everyDaySport;
    }

    public static class EveryDaySportBean implements Serializable{
        /**
         * sportID : 25
         * userID : 17
         * dayTime : 2016.02.01
         * distance : 1110
         * calories : 15974
         * sportTime : 10
         * sportImage : jsaknakdnkand
         * sportSpeed : asnda
         * sportStep : 0
         */

        private int sportID;
        private int userID;
        private String dayTime;
        private int distance;
        private int calories;
        private int sportTime;
        private String sportImage;
        private String sportSpeed;
        private int sportStep;

        public int getSportID() {
            return sportID;
        }

        public void setSportID(int sportID) {
            this.sportID = sportID;
        }

        public int getUserID() {
            return userID;
        }

        public void setUserID(int userID) {
            this.userID = userID;
        }

        public String getDayTime() {
            return dayTime;
        }

        public void setDayTime(String dayTime) {
            this.dayTime = dayTime;
        }

        public int getDistance() {
            return distance;
        }

        public void setDistance(int distance) {
            this.distance = distance;
        }

        public int getCalories() {
            return calories;
        }

        public void setCalories(int calories) {
            this.calories = calories;
        }

        public int getSportTime() {
            return sportTime;
        }

        public void setSportTime(int sportTime) {
            this.sportTime = sportTime;
        }

        public String getSportImage() {
            return sportImage;
        }

        public void setSportImage(String sportImage) {
            this.sportImage = sportImage;
        }

        public String getSportSpeed() {
            return sportSpeed;
        }

        public void setSportSpeed(String sportSpeed) {
            this.sportSpeed = sportSpeed;
        }

        public int getSportStep() {
            return sportStep;
        }

        public void setSportStep(int sportStep) {
            this.sportStep = sportStep;
        }
    }
}
