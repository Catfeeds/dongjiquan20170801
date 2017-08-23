package com.weiaibenpao.demo.chislim.bean;

/**
 * Created by lenovo on 2017/5/15.
 */

public class AddInterestSportResult {

    /**
     * code : 0
     * msg : 成功
     * data : {"id":5,"userId":3,"cityName":"北京","finishPercent":"50","allDis":"45s500","nowDis":"213","sportTime":"13123"}
     */

    private int code;
    private String msg;
    /**
     * id : 5
     * userId : 3
     * cityName : 北京
     * finishPercent : 50
     * allDis : 45s500
     * nowDis : 213
     * sportTime : 13123
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
        private int id;
        private int userId;
        private String cityName;
        private String finishPercent;
        private String allDis;
        private String nowDis;
        private String sportTime;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getFinishPercent() {
            return finishPercent;
        }

        public void setFinishPercent(String finishPercent) {
            this.finishPercent = finishPercent;
        }

        public String getAllDis() {
            return allDis;
        }

        public void setAllDis(String allDis) {
            this.allDis = allDis;
        }

        public String getNowDis() {
            return nowDis;
        }

        public void setNowDis(String nowDis) {
            this.nowDis = nowDis;
        }

        public String getSportTime() {
            return sportTime;
        }

        public void setSportTime(String sportTime) {
            this.sportTime = sportTime;
        }
    }
}
