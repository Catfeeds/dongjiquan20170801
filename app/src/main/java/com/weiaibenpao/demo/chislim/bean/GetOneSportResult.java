package com.weiaibenpao.demo.chislim.bean;

import java.util.List;

/**
 * Created by lenovo on 2016/8/19.
 */

public class GetOneSportResult {


    /**
     * error : 0
     * everyDaySport : [{"sportID":8,"userID":1,"dayTime":"2016.08.21","distance":12,"calories":3575,"sportTime":2,"successes":"asdala","project":10}]
     */

    private int error;
    /**
     * sportID : 8
     * userID : 1
     * dayTime : 2016.08.21
     * distance : 12
     * calories : 3575
     * sportTime : 2
     * successes : asdala
     * project : 10
     */

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

    public static class EveryDaySportBean {
        private int sportID;
        private int userID;
        private String dayTime;
        private int distance;
        private int calories;
        private int sportTime;
        private String successes;
        private int project;

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

        public String getSuccesses() {
            return successes;
        }

        public void setSuccesses(String successes) {
            this.successes = successes;
        }

        public int getProject() {
            return project;
        }

        public void setProject(int project) {
            this.project = project;
        }
    }
}
