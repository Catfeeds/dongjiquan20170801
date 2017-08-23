package com.weiaibenpao.demo.chislim.bean;

import java.util.List;

/**
 * Created by lenovo on 2017/6/7.
 */

public class SportHistoryResultBean {

    private int code;
    private String msg;
    private DataBean data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        private List<MonthRecord> sports;
        private double totalAllDistance ;

        public double getTotalAllDistance() {
            return totalAllDistance;
        }

        public void setTotalAllDistance(double totalAllDistance) {
            this.totalAllDistance = totalAllDistance;
        }

        public List<MonthRecord> getSports() {
            return sports;
        }

        public void setSports(List<MonthRecord> sports) {
            this.sports = sports;
        }

        public static class MonthRecord {

            private List<SportRecord> sportHistorhList;
            private double totalDistance;
            private String month;

            public String getMonth() {
                return month;
            }

            public void setMonth(String month) {
                this.month = month;
            }

            public double getTotalDistance() {
                return totalDistance;
            }

            public void setTotalDistance(double totalDistance) {
                this.totalDistance = totalDistance;
            }

            public List<SportRecord> getSportHistoryList() {
                return sportHistorhList;
            }

            public void setSportHistorhList(List<SportRecord> sportHistorhList) {
                this.sportHistorhList = sportHistorhList;
            }
        }


        public static class SportRecord {
            private String average;
            private String altitudeHigh;
            private String bloodPressure;
            private String calories;
            private String heartRate;
            private int id;
            private String matchNumber;
            private String stepFrequency;
            private long sportDate;
            private String sportImgUrl;
            private int sportType;
            private long totalTime;
            private String totalStep;
            private String totalShin;
            private double totalDistance;


            public String getBloodPressure() {
                return bloodPressure;
            }

            public void setBloodPressure(String bloodPressure) {
                this.bloodPressure = bloodPressure;
            }

            public String getAverage() {
                return average;
            }

            public void setAverage(String average) {
                this.average = average;
            }

            public long getSportDate() {
                return sportDate;
            }

            public void setSportDate(long sportDate) {
                this.sportDate = sportDate;
            }

            public String getSportImgUrl() {
                return sportImgUrl;
            }

            public void setSportImgUrl(String sportImgUrl) {
                this.sportImgUrl = sportImgUrl;
            }

            public int getSportType() {
                return sportType;
            }

            public void setSportType(int sportType) {
                this.sportType = sportType;
            }

            public long getTotalTime() {
                return totalTime;
            }

            public void setTotalTime(long totalTime) {
                this.totalTime = totalTime;
            }

            public String getTotalStep() {
                return totalStep;
            }

            public void setTotalStep(String totalStep) {
                this.totalStep = totalStep;
            }

            public String getTotalShin() {
                return totalShin;
            }

            public void setTotalShin(String totalShin) {
                this.totalShin = totalShin;
            }

            public String getCalories() {
                return calories;
            }

            public void setCalories(String calories) {
                this.calories = calories;
            }

            public String getStepFrequency() {
                return stepFrequency;
            }

            public void setStepFrequency(String stepFrequency) {
                this.stepFrequency = stepFrequency;
            }

            public String getHeartRate() {
                return heartRate;
            }

            public void setHeartRate(String heartRate) {
                this.heartRate = heartRate;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public double getTotalDistance() {
                return totalDistance;
            }

            public void setTotalDistance(double totalDistance) {
                this.totalDistance = totalDistance;
            }

            public String getMatchNumber() {
                return matchNumber;
            }

            public void setMatchNumber(String matchNumber) {
                this.matchNumber = matchNumber;
            }

            public String getAltitudeHigh() {
                return altitudeHigh;
            }

            public void setAltitudeHigh(String altitudeHigh) {
                this.altitudeHigh = altitudeHigh;
            }
        }
    }
}
