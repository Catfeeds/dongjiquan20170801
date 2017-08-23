package com.weiaibenpao.demo.chislim.bean;

/**
 * Created by Administrator on 2017/8/14.
 */

public class FansBean {

    /**
     * code : 0
     * msg : 成功
     * data : {"cares":0,"countSport":0,"fanscount":0,"totalHumor":0}
     */

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
        /**
         * cares : 0
         * countSport : 0
         * fanscount : 0
         * totalHumor : 0
         */

        private int cares;
        private int countSport;
        private int fanscount;
        private int totalHumor;

        public int getCares() {
            return cares;
        }

        public void setCares(int cares) {
            this.cares = cares;
        }

        public int getCountSport() {
            return countSport;
        }

        public void setCountSport(int countSport) {
            this.countSport = countSport;
        }

        public int getFanscount() {
            return fanscount;
        }

        public void setFanscount(int fanscount) {
            this.fanscount = fanscount;
        }

        public int getTotalHumor() {
            return totalHumor;
        }

        public void setTotalHumor(int totalHumor) {
            this.totalHumor = totalHumor;
        }
    }
}
