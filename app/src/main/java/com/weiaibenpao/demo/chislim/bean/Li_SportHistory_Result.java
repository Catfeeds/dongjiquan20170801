package com.weiaibenpao.demo.chislim.bean;

import java.util.List;

/**
 * Created by lenovo on 2017/6/7.
 */

public class Li_SportHistory_Result {

    /**
     * code : 0
     * msg : 成功
     * data : {"themelist":[{"bloodPressure":"789","average":"13","sportDate":1496800200475,"sportImgUrl":"13pppp","sportType":2,"totalTime":"666","totalStep":"3223","totalShin":"45","calories":"445","stepFrequency":"12","heartRate":"78","id":17,"totalDistance":3,"matchNumber":"131","altitudeHigh":"456"},{"bloodPressure":"789","average":"13","sportDate":1496800199100,"sportImgUrl":"13pppp","sportType":2,"totalTime":"666","totalStep":"3223","totalShin":"45","calories":"445","stepFrequency":"12","heartRate":"78","id":16,"totalDistance":3,"matchNumber":"131","altitudeHigh":"456"},{"bloodPressure":"789","average":"13","sportDate":1496800196412,"sportImgUrl":"13pppp","sportType":2,"totalTime":"666","totalStep":"3223","totalShin":"45","calories":"445","stepFrequency":"12","heartRate":"78","id":15,"totalDistance":3,"matchNumber":"131","altitudeHigh":"456"}],"extra":{"totalSize":3,"size":10,"totalPage":1,"page":0}}
     */

    private int code;
    private String msg;
    /**
     * themelist : [{"bloodPressure":"789","average":"13","sportDate":1496800200475,"sportImgUrl":"13pppp","sportType":2,"totalTime":"666","totalStep":"3223","totalShin":"45","calories":"445","stepFrequency":"12","heartRate":"78","id":17,"totalDistance":3,"matchNumber":"131","altitudeHigh":"456"},{"bloodPressure":"789","average":"13","sportDate":1496800199100,"sportImgUrl":"13pppp","sportType":2,"totalTime":"666","totalStep":"3223","totalShin":"45","calories":"445","stepFrequency":"12","heartRate":"78","id":16,"totalDistance":3,"matchNumber":"131","altitudeHigh":"456"},{"bloodPressure":"789","average":"13","sportDate":1496800196412,"sportImgUrl":"13pppp","sportType":2,"totalTime":"666","totalStep":"3223","totalShin":"45","calories":"445","stepFrequency":"12","heartRate":"78","id":15,"totalDistance":3,"matchNumber":"131","altitudeHigh":"456"}]
     * extra : {"totalSize":3,"size":10,"totalPage":1,"page":0}
     */

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
        /**
         * totalSize : 3
         * size : 10
         * totalPage : 1
         * page : 0
         */

        private ExtraBean extra;
        /**
         * bloodPressure : 789
         * average : 13
         * sportDate : 1496800200475
         * sportImgUrl : 13pppp
         * sportType : 2
         * totalTime : 666
         * totalStep : 3223
         * totalShin : 45
         * calories : 445
         * stepFrequency : 12
         * heartRate : 78
         * id : 17
         * totalDistance : 3.0
         * matchNumber : 131
         * altitudeHigh : 456
         */

        private List<ThemelistBean> themelist;

        public ExtraBean getExtra() {
            return extra;
        }

        public void setExtra(ExtraBean extra) {
            this.extra = extra;
        }

        public List<ThemelistBean> getThemelist() {
            return themelist;
        }

        public void setThemelist(List<ThemelistBean> themelist) {
            this.themelist = themelist;
        }

        public static class ExtraBean {
            private int totalSize;
            private int size;
            private int totalPage;
            private int page;

            public int getTotalSize() {
                return totalSize;
            }

            public void setTotalSize(int totalSize) {
                this.totalSize = totalSize;
            }

            public int getSize() {
                return size;
            }

            public void setSize(int size) {
                this.size = size;
            }

            public int getTotalPage() {
                return totalPage;
            }

            public void setTotalPage(int totalPage) {
                this.totalPage = totalPage;
            }

            public int getPage() {
                return page;
            }

            public void setPage(int page) {
                this.page = page;
            }
        }

        public static class ThemelistBean {
            private String bloodPressure;
            private String average;
            private long sportDate;
            private String sportImgUrl;
            private int sportType;
            private String totalTime;
            private String totalStep;
            private String totalShin;
            private String calories;
            private String stepFrequency;
            private String heartRate;
            private int id;
            private double totalDistance;
            private String matchNumber;
            private String altitudeHigh;

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

            public String getTotalTime() {
                return totalTime;
            }

            public void setTotalTime(String totalTime) {
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
