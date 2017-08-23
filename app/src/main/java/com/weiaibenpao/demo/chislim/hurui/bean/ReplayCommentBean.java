package com.weiaibenpao.demo.chislim.hurui.bean;

/**
 * Created by lenovo on 2017/4/25.
 */

public class ReplayCommentBean {

    /**
     * code : 0
     * msg : 成功
     * data : {"id":10,"userId":15,"commentTime":1493106460574,"humorId":38,"parentId":2,"comText":"asdasd"}
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
         * id : 10
         * userId : 15
         * commentTime : 1493106460574
         * humorId : 38
         * parentId : 2
         * comText : asdasd
         */

        private int id;
        private int userId;
        private long commentTime;
        private int humorId;
        private int parentId;
        private String comText;

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

        public long getCommentTime() {
            return commentTime;
        }

        public void setCommentTime(long commentTime) {
            this.commentTime = commentTime;
        }

        public int getHumorId() {
            return humorId;
        }

        public void setHumorId(int humorId) {
            this.humorId = humorId;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        public String getComText() {
            return comText;
        }

        public void setComText(String comText) {
            this.comText = comText;
        }
    }
}

