package com.weiaibenpao.demo.chislim.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/16.
 */

public class MyMedal {

    /**
     * code : 0
     * msg : 成功
     * data : {"badgeList":[{"badgeName":"3km勋章","id":1,"badgeNumber":0,"badgeIconUrl":"http://ofplk6att.bkt.clouddn.com/im_1.png"},{"badgeName":"5km勋章","id":2,"badgeNumber":0,"badgeIconUrl":"http://ofplk6att.bkt.clouddn.com/im_2.png"},{"badgeName":"10km勋章","id":3,"badgeNumber":0,"badgeIconUrl":"http://ofplk6att.bkt.clouddn.com/im_3.png"},{"badgeName":"半马勋章","id":4,"badgeNumber":0,"badgeIconUrl":"http://ofplk6att.bkt.clouddn.com/im_4.png"},{"badgeName":"全马勋章","id":5,"badgeNumber":0,"badgeIconUrl":"http://ofplk6att.bkt.clouddn.com/im_5.png"},{"badgeName":"50km勋章","id":6,"badgeNumber":0,"badgeIconUrl":"http://ofplk6att.bkt.clouddn.com/im_6.png"}],"totalbage":0}
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
         * badgeList : [{"badgeName":"3km勋章","id":1,"badgeNumber":0,"badgeIconUrl":"http://ofplk6att.bkt.clouddn.com/im_1.png"},{"badgeName":"5km勋章","id":2,"badgeNumber":0,"badgeIconUrl":"http://ofplk6att.bkt.clouddn.com/im_2.png"},{"badgeName":"10km勋章","id":3,"badgeNumber":0,"badgeIconUrl":"http://ofplk6att.bkt.clouddn.com/im_3.png"},{"badgeName":"半马勋章","id":4,"badgeNumber":0,"badgeIconUrl":"http://ofplk6att.bkt.clouddn.com/im_4.png"},{"badgeName":"全马勋章","id":5,"badgeNumber":0,"badgeIconUrl":"http://ofplk6att.bkt.clouddn.com/im_5.png"},{"badgeName":"50km勋章","id":6,"badgeNumber":0,"badgeIconUrl":"http://ofplk6att.bkt.clouddn.com/im_6.png"}]
         * totalbage : 0
         */

        private int totalbage;
        private List<BadgeListBean> badgeList;

        public int getTotalbage() {
            return totalbage;
        }

        public void setTotalbage(int totalbage) {
            this.totalbage = totalbage;
        }

        public List<BadgeListBean> getBadgeList() {
            return badgeList;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "totalbage=" + totalbage +
                    ", badgeList=" + badgeList +
                    '}';
        }

        public void setBadgeList(List<BadgeListBean> badgeList) {
            this.badgeList = badgeList;
        }

        public static class BadgeListBean {
            /**
             * badgeName : 3km勋章
             * id : 1
             * badgeNumber : 0
             * badgeIconUrl : http://ofplk6att.bkt.clouddn.com/im_1.png
             * badgewhiteIconUrl
             */

            private String badgeName;
            private int id;
            private int badgeNumber;
            private String badgeIconUrl;
            private String badgewhiteIconUrl;

            public BadgeListBean(String badgewhiteIconUrl) {
                this.badgewhiteIconUrl = badgewhiteIconUrl;
            }

            public BadgeListBean() {
            }

            @Override
            public String toString() {
                return "BadgeListBean{" +
                        "id=" + id +
                        '}';
            }

            public String getBadgewhiteIconUrl() {
                return badgewhiteIconUrl;
            }

            public void setBadgewhiteIconUrl(String badgewhiteIconUrl) {
                this.badgewhiteIconUrl = badgewhiteIconUrl;
            }

            public String getBadgeName() {
                return badgeName;
            }

            public void setBadgeName(String badgeName) {
                this.badgeName = badgeName;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getBadgeNumber() {
                return badgeNumber;
            }

            public void setBadgeNumber(int badgeNumber) {
                this.badgeNumber = badgeNumber;
            }

            public String getBadgeIconUrl() {
                return badgeIconUrl;
            }

            public void setBadgeIconUrl(String badgeIconUrl) {
                this.badgeIconUrl = badgeIconUrl;
            }
        }
    }
}
