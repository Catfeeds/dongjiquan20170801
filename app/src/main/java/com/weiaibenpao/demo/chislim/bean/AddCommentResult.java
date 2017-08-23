package com.weiaibenpao.demo.chislim.bean;

/**
 * Created by lenovo on 2016/12/7.
 */

public class AddCommentResult {

    /**
     * code : 0
     * msg : 成功
     * data : {"id":24,"userId":2,"commentTime":1481083688268,"humorId":1,"parentId":0,"comText":"qwer"}
     */

    private int code;
    private String msg;
    /**
     * id : 24
     * userId : 2
     * commentTime : 1481083688268
     * humorId : 1
     * parentId : 0
     * comText : qwer
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
