package com.weiaibenpao.demo.chislim.hurui.bean;

import java.util.List;

/**
 * Created by lenovo on 2017/4/13.
 */

public class RunMachineBean {


    /**
     * code : 0
     * msg : 成功
     * data : {"extra":{"totalSize":8,"size":4,"totalPage":2,"page":1},"treadmill":[{"treadmillTitle":"启迈斯T600跑步机","id":4,"treadmillImgUrl":"http://ofplk6att.bkt.clouddn.com/t6.png","tmallUrl":"https://detail.tmall.com/item.htm?id=542585989946","tmallID":"542585989946","treadmillIntro":"家用款多功能可折叠电动超静音正品特价健身器材"},{"treadmillTitle":"启迈斯R8跑步机","id":3,"treadmillImgUrl":"http://ofplk6att.bkt.clouddn.com/r8.jpg","tmallUrl":"https://detail.tmall.com/item.htm?id=45516252267","tmallID":"45516252267","treadmillIntro":"家用款 超静音多功能折叠健身器材智能电动跑步机"},{"treadmillTitle":"启迈斯boss5跑步机","id":2,"treadmillImgUrl":"http://ofplk6att.bkt.clouddn.com/boss5.jpg","tmallUrl":"https://detail.tmall.com/item.htm?id=542659265156","tmallID":"542659265156","treadmillIntro":"家用款多功能超静音电动折叠健身器材商用智能"},{"treadmillTitle":"启迈斯s500跑步机","id":1,"treadmillImgUrl":"http://ofplk6att.bkt.clouddn.com/s500.jpg","tmallUrl":"https://detail.tmall.com/item.htm?id=520895951003","tmallID":"520895951003","treadmillIntro":"家用款多功能超静音折叠健身器材电动智能跑步机"}]}
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
         * extra : {"totalSize":8,"size":4,"totalPage":2,"page":1}
         * treadmill : [{"treadmillTitle":"启迈斯T600跑步机","id":4,"treadmillImgUrl":"http://ofplk6att.bkt.clouddn.com/t6.png","tmallUrl":"https://detail.tmall.com/item.htm?id=542585989946","tmallID":"542585989946","treadmillIntro":"家用款多功能可折叠电动超静音正品特价健身器材"},{"treadmillTitle":"启迈斯R8跑步机","id":3,"treadmillImgUrl":"http://ofplk6att.bkt.clouddn.com/r8.jpg","tmallUrl":"https://detail.tmall.com/item.htm?id=45516252267","tmallID":"45516252267","treadmillIntro":"家用款 超静音多功能折叠健身器材智能电动跑步机"},{"treadmillTitle":"启迈斯boss5跑步机","id":2,"treadmillImgUrl":"http://ofplk6att.bkt.clouddn.com/boss5.jpg","tmallUrl":"https://detail.tmall.com/item.htm?id=542659265156","tmallID":"542659265156","treadmillIntro":"家用款多功能超静音电动折叠健身器材商用智能"},{"treadmillTitle":"启迈斯s500跑步机","id":1,"treadmillImgUrl":"http://ofplk6att.bkt.clouddn.com/s500.jpg","tmallUrl":"https://detail.tmall.com/item.htm?id=520895951003","tmallID":"520895951003","treadmillIntro":"家用款多功能超静音折叠健身器材电动智能跑步机"}]
         */

        private ExtraBean extra;
        private List<TreadmillBean> treadmill;

        public ExtraBean getExtra() {
            return extra;
        }

        public void setExtra(ExtraBean extra) {
            this.extra = extra;
        }

        public List<TreadmillBean> getTreadmill() {
            return treadmill;
        }

        public void setTreadmill(List<TreadmillBean> treadmill) {
            this.treadmill = treadmill;
        }

        public static class ExtraBean {
            /**
             * totalSize : 8
             * size : 4
             * totalPage : 2
             * page : 1
             */

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

        public static class TreadmillBean {
            /**
             * treadmillTitle : 启迈斯T600跑步机
             * id : 4
             * treadmillImgUrl : http://ofplk6att.bkt.clouddn.com/t6.png
             * tmallUrl : https://detail.tmall.com/item.htm?id=542585989946
             * tmallID : 542585989946
             * treadmillIntro : 家用款多功能可折叠电动超静音正品特价健身器材
             */

            private String treadmillTitle;
            private int id;
            private String treadmillImgUrl;
            private String tmallUrl;
            private String tmallID;
            private String treadmillIntro;

            public String getTreadmillTitle() {
                return treadmillTitle;
            }

            public void setTreadmillTitle(String treadmillTitle) {
                this.treadmillTitle = treadmillTitle;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTreadmillImgUrl() {
                return treadmillImgUrl;
            }

            public void setTreadmillImgUrl(String treadmillImgUrl) {
                this.treadmillImgUrl = treadmillImgUrl;
            }

            public String getTmallUrl() {
                return tmallUrl;
            }

            public void setTmallUrl(String tmallUrl) {
                this.tmallUrl = tmallUrl;
            }

            public String getTmallID() {
                return tmallID;
            }

            public void setTmallID(String tmallID) {
                this.tmallID = tmallID;
            }

            public String getTreadmillIntro() {
                return treadmillIntro;
            }

            public void setTreadmillIntro(String treadmillIntro) {
                this.treadmillIntro = treadmillIntro;
            }
        }
    }
}
