package com.weiaibenpao.demo.chislim.bean;

import java.util.List;

/**
 * Created by lenovo on 2016/12/5.
 */

public class CommentResult {


    /**
     * code : 0
     * msg : 成功
     * data : {"themelist":[{"comText":"qwer","userNickName":"小张","userIcon":"http://112.74.28.179:8080/Chislim/Image/20160817112589.PNG","commentTime":1480044411357,"humorId":1,"userId":2,"preUserNickName":"","parentId":0,"preComText":""},{"comText":"qwer","userNickName":"小张","userIcon":"http://112.74.28.179:8080/Chislim/Image/20160817112589.PNG","commentTime":1480044377668,"humorId":1,"userId":2,"preUserNickName":"小张","parentId":1,"preComText":"不好玩"},{"comText":"qwer","userNickName":"小张","userIcon":"http://112.74.28.179:8080/Chislim/Image/20160817112589.PNG","commentTime":1480044164189,"humorId":1,"userId":2,"preUserNickName":"","parentId":0,"preComText":""},{"comText":"不好玩","userNickName":"小张","userIcon":"http://112.74.28.179:8080/Chislim/Image/20160817112589.PNG","commentTime":1480044154142,"humorId":1,"userId":2,"preUserNickName":"","parentId":0,"preComText":""}],"extra":{"totalSize":4,"size":10,"totalPage":1,"page":0}}
     */

    private int code;
    private String msg;
    /**
     * themelist : [{"comText":"qwer","userNickName":"小张","userIcon":"http://112.74.28.179:8080/Chislim/Image/20160817112589.PNG","commentTime":1480044411357,"humorId":1,"userId":2,"preUserNickName":"","parentId":0,"preComText":""},{"comText":"qwer","userNickName":"小张","userIcon":"http://112.74.28.179:8080/Chislim/Image/20160817112589.PNG","commentTime":1480044377668,"humorId":1,"userId":2,"preUserNickName":"小张","parentId":1,"preComText":"不好玩"},{"comText":"qwer","userNickName":"小张","userIcon":"http://112.74.28.179:8080/Chislim/Image/20160817112589.PNG","commentTime":1480044164189,"humorId":1,"userId":2,"preUserNickName":"","parentId":0,"preComText":""},{"comText":"不好玩","userNickName":"小张","userIcon":"http://112.74.28.179:8080/Chislim/Image/20160817112589.PNG","commentTime":1480044154142,"humorId":1,"userId":2,"preUserNickName":"","parentId":0,"preComText":""}]
     * extra : {"totalSize":4,"size":10,"totalPage":1,"page":0}
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
         * totalSize : 4
         * size : 10
         * totalPage : 1
         * page : 0
         */

        private ExtraBean extra;
        /**
         * comText : qwer
         * userNickName : 小张
         * userIcon : http://112.74.28.179:8080/Chislim/Image/20160817112589.PNG
         * commentTime : 1480044411357
         * humorId : 1
         * userId : 2
         * preUserNickName :
         * parentId : 0
         * preComText :
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
            private String comText;
            private String userNickName;
            private String userIcon;
            private long commentTime;
            private int humorId;
            private int userId;
            private String preUserNickName;
            private int parentId;
            private String preComText;

            public String getComText() {
                return comText;
            }

            public void setComText(String comText) {
                this.comText = comText;
            }

            public String getUserNickName() {
                return userNickName;
            }

            public void setUserNickName(String userNickName) {
                this.userNickName = userNickName;
            }

            public String getUserIcon() {
                return userIcon;
            }

            public void setUserIcon(String userIcon) {
                this.userIcon = userIcon;
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

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public String getPreUserNickName() {
                return preUserNickName;
            }

            public void setPreUserNickName(String preUserNickName) {
                this.preUserNickName = preUserNickName;
            }

            public int getParentId() {
                return parentId;
            }

            public void setParentId(int parentId) {
                this.parentId = parentId;
            }

            public String getPreComText() {
                return preComText;
            }

            public void setPreComText(String preComText) {
                this.preComText = preComText;
            }
        }
    }
}
