package com.weiaibenpao.demo.chislim.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lenovo on 2017/8/19.
 */

public class Li_Praise_Result implements Serializable {

    /**
     * code : 0
     * msg : 成功
     * data : {"praiselist":[{"id":232,"userNickName":"sunshine ☀ 种子","userIcon":"http://ofplk6att.bkt.clouddn.com/boy.png","humorId":130,"userId":46},{"id":231,"userNickName":"vivo测试","userIcon":"http://ofplk6att.bkt.clouddn.com/boy.png","humorId":130,"userId":45},{"id":230,"userNickName":"じ☆ve天狠ぷ藍","userIcon":"http://ofplk6att.bkt.clouddn.com/boy.png","humorId":130,"userId":44}],"extra":{"totalSize":3,"size":20,"totalPage":1,"page":0}}
     */

    private int code;
    private String msg;
    /**
     * praiselist : [{"id":232,"userNickName":"sunshine ☀ 种子","userIcon":"http://ofplk6att.bkt.clouddn.com/boy.png","humorId":130,"userId":46},{"id":231,"userNickName":"vivo测试","userIcon":"http://ofplk6att.bkt.clouddn.com/boy.png","humorId":130,"userId":45},{"id":230,"userNickName":"じ☆ve天狠ぷ藍","userIcon":"http://ofplk6att.bkt.clouddn.com/boy.png","humorId":130,"userId":44}]
     * extra : {"totalSize":3,"size":20,"totalPage":1,"page":0}
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

    public static class DataBean implements Serializable{
        /**
         * totalSize : 3
         * size : 20
         * totalPage : 1
         * page : 0
         */

        private ExtraBean extra;
        /**
         * id : 232
         * userNickName : sunshine ☀ 种子
         * userIcon : http://ofplk6att.bkt.clouddn.com/boy.png
         * humorId : 130
         * userId : 46
         */

        private List<PraiselistBean> praiselist;

        public ExtraBean getExtra() {
            return extra;
        }

        public void setExtra(ExtraBean extra) {
            this.extra = extra;
        }

        public List<PraiselistBean> getPraiselist() {
            return praiselist;
        }

        public void setPraiselist(List<PraiselistBean> praiselist) {
            this.praiselist = praiselist;
        }

        public static class ExtraBean implements Serializable{
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

        public static class PraiselistBean implements Serializable{
            private int id;
            private String userNickName;
            private String userIcon;
            private int humorId;
            private int userId;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
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
        }
    }
}
