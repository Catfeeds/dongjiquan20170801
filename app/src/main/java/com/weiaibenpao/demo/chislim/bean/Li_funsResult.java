package com.weiaibenpao.demo.chislim.bean;

import java.util.List;

/**
 * Created by lenovo on 2017/4/25.
 */

public class Li_funsResult {

    /**
     * code : 0
     * msg : 成功
     * data : {"extra":{"totalSize":1,"size":10,"totalPage":1,"page":0},"list":[{"beUserId":2,"id":3,"time":1492670552823,"userName":"用户名","userIcon":"http://img.hb.aicdn.com/c4dcb8d1bbd584bad02706e7810e2908c661fb7b27a93-gKgyYw_fw658","userId":1}]}
     */

    private int code;
    private String msg;
    /**
     * extra : {"totalSize":1,"size":10,"totalPage":1,"page":0}
     * list : [{"beUserId":2,"id":3,"time":1492670552823,"userName":"用户名","userIcon":"http://img.hb.aicdn.com/c4dcb8d1bbd584bad02706e7810e2908c661fb7b27a93-gKgyYw_fw658","userId":1}]
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
         * totalSize : 1
         * size : 10
         * totalPage : 1
         * page : 0
         */

        private ExtraBean extra;
        /**
         * beUserId : 2
         * id : 3
         * time : 1492670552823
         * userName : 用户名
         * userIcon : http://img.hb.aicdn.com/c4dcb8d1bbd584bad02706e7810e2908c661fb7b27a93-gKgyYw_fw658
         * userId : 1
         */

        private List<ListBean> list;

        public ExtraBean getExtra() {
            return extra;
        }

        public void setExtra(ExtraBean extra) {
            this.extra = extra;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
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

        public static class ListBean {
            private int beUserId;
            private int id;
            private long time;
            private String userName;
            private String userIcon;
            private int userId;

            public int getBeUserId() {
                return beUserId;
            }

            public void setBeUserId(int beUserId) {
                this.beUserId = beUserId;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public long getTime() {
                return time;
            }

            public void setTime(long time) {
                this.time = time;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getUserIcon() {
                return userIcon;
            }

            public void setUserIcon(String userIcon) {
                this.userIcon = userIcon;
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
