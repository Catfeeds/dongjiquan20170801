package com.weiaibenpao.demo.chislim.hurui.bean;

import com.weiaibenpao.demo.chislim.bean.Comment;

/**
 * Created by Administrator on 2017/8/21.
 */

public class CommentBean {

    /**
     * code : 0
     * msg : 成功
     * data : {"id":114,"userId":15,"commentTime":1502526970157,"humorId":33,"parentId":17,"comText":"不知道你说的啥"}
     */

    private int code;
    private String msg;
    private Comment data;

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

    public Comment getData() {
        return data;
    }

    public void setData(Comment data) {
        this.data = data;
    }

//    public static class DataBean {
//        /**
//         * id : 114
//         * userId : 15
//         * commentTime : 1502526970157
//         * humorId : 33
//         * parentId : 17
//         * comText : 不知道你说的啥
//         *
//         */
//
//        private int id;
//        private int userId;
//        private long commentTime;
//        private int humorId;
//        private int parentId;
//        private String comText;
//
//        private   String userNickName;
//        private   String preUserNickName;
//
//        @Override
//        public String toString() {
//            return "DataBean{" +
//                    "id=" + id +
//                    ", userId=" + userId +
//                    ", commentTime=" + commentTime +
//                    ", humorId=" + humorId +
//                    ", parentId=" + parentId +
//                    ", comText='" + comText + '\'' +
//                    ", userNickName='" + userNickName + '\'' +
//                    ", preUserNickName='" + preUserNickName + '\'' +
//                    '}';
//        }
//
//        public String getUserNickName() {
//            return userNickName;
//        }
//
//        public void setUserNickName(String userNickName) {
//            this.userNickName = userNickName;
//        }
//
//        public String getPreUserNickName() {
//            return preUserNickName;
//        }
//
//        public void setPreUserNickName(String preUserNickName) {
//            this.preUserNickName = preUserNickName;
//        }
//
//        public int getId() {
//            return id;
//        }
//
//        public void setId(int id) {
//            this.id = id;
//        }
//
//        public int getUserId() {
//            return userId;
//        }
//
//        public void setUserId(int userId) {
//            this.userId = userId;
//        }
//
//        public long getCommentTime() {
//            return commentTime;
//        }
//
//        public void setCommentTime(long commentTime) {
//            this.commentTime = commentTime;
//        }
//
//        public int getHumorId() {
//            return humorId;
//        }
//
//        public void setHumorId(int humorId) {
//            this.humorId = humorId;
//        }
//
//        public int getParentId() {
//            return parentId;
//        }
//
//        public void setParentId(int parentId) {
//            this.parentId = parentId;
//        }
//
//        public String getComText() {
//            return comText;
//        }
//
//        public void setComText(String comText) {
//            this.comText = comText;
//        }
//    }
}
