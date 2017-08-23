package com.weiaibenpao.demo.chislim.hurui.bean;

/**
 * Created by lenovo on 2017/4/22.
 */

public class PulishHumorBean {

    /**
     * code : 0
     * msg : 成功
     * data : {"id":18,"userId":1,"themeTitleStr":"huati","humorContent":"qweretr","humorImgUrl":"dizhi","humorVideoUrl":"shipin","likeNum":0,"humorpalce":"dinwe","comentNum":0}
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
         * id : 18
         * userId : 1
         * themeTitleStr : huati
         * humorContent : qweretr
         * humorImgUrl : dizhi
         * humorVideoUrl : shipin
         * likeNum : 0
         * humorpalce : dinwe
         * comentNum : 0
         */

        private int id;
        private int userId;
        private String themeTitleStr;
        private String humorContent;
        private String humorImgUrl;
        private String humorVideoUrl;
        private int likeNum;
        private String humorpalce;
        private int comentNum;

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

        public String getThemeTitleStr() {
            return themeTitleStr;
        }

        public void setThemeTitleStr(String themeTitleStr) {
            this.themeTitleStr = themeTitleStr;
        }

        public String getHumorContent() {
            return humorContent;
        }

        public void setHumorContent(String humorContent) {
            this.humorContent = humorContent;
        }

        public String getHumorImgUrl() {
            return humorImgUrl;
        }

        public void setHumorImgUrl(String humorImgUrl) {
            this.humorImgUrl = humorImgUrl;
        }

        public String getHumorVideoUrl() {
            return humorVideoUrl;
        }

        public void setHumorVideoUrl(String humorVideoUrl) {
            this.humorVideoUrl = humorVideoUrl;
        }

        public int getLikeNum() {
            return likeNum;
        }

        public void setLikeNum(int likeNum) {
            this.likeNum = likeNum;
        }

        public String getHumorpalce() {
            return humorpalce;
        }

        public void setHumorpalce(String humorpalce) {
            this.humorpalce = humorpalce;
        }

        public int getComentNum() {
            return comentNum;
        }

        public void setComentNum(int comentNum) {
            this.comentNum = comentNum;
        }
    }
}
