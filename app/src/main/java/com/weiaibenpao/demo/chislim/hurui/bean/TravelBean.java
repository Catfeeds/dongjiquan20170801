package com.weiaibenpao.demo.chislim.hurui.bean;

import java.util.List;

/**
 * Created by lenovo on 2017/4/14.
 */

public class TravelBean {

    /**
     * code : 0
     * msg : 成功
     * data : {"travelist":[{"travelHot":51,"travelType":"156","travelContentUrl":"http://112.74.28.179:8080/Chislim/travelresource/zhaji.htm","isCollect":"No","travelImgUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1492682055&di=2c902b6df28bb23fab4c85d76637b8fd&imgtype=jpg&er=1&src=http%3A%2F%2Ftupian.enterdesk.com%2F2013%2Flxy%2F10%2F10%2F5%2F9.jpg","id":5,"travelTitle":"地上的星"},{"travelHot":231,"travelType":"leixn","travelContentUrl":"http://112.74.28.179:8080/Chislim/travelresource/zhaji.htm","isCollect":"No","travelImgUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1492682055&di=2c902b6df28bb23fab4c85d76637b8fd&imgtype=jpg&er=1&src=http%3A%2F%2Ftupian.enterdesk.com%2F2013%2Flxy%2F10%2F10%2F5%2F9.jpg","id":4,"travelTitle":"地上的星"},{"travelHot":231,"travelType":"leixn","travelContentUrl":"http://112.74.28.179:8080/Chislim/travelresource/zhaji.htm","isCollect":"No","travelImgUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1492682055&di=2c902b6df28bb23fab4c85d76637b8fd&imgtype=jpg&er=1&src=http%3A%2F%2Ftupian.enterdesk.com%2F2013%2Flxy%2F10%2F10%2F5%2F9.jpg","id":3,"travelTitle":"地上的星"},{"travelHot":555,"travelType":"leixn","travelContentUrl":"http://112.74.28.179:8080/Chislim/travelresource/fengjie.html","isCollect":"No","travelImgUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1492682055&di=2c902b6df28bb23fab4c85d76637b8fd&imgtype=jpg&er=1&src=http%3A%2F%2Ftupian.enterdesk.com%2F2013%2Flxy%2F10%2F10%2F5%2F9.jpg","id":2,"travelTitle":"仙女湖"},{"travelHot":155,"travelType":"类型","travelContentUrl":"http://112.74.28.179:8080/Chislim/travelresource/zhaji.html","isCollect":"No","travelImgUrl":"http://img3.imgtn.bdimg.com/it/u=4029368265,956204632&fm=23&gp=0.jpg","id":1,"travelTitle":"的湖泊,仿佛是跌落在地上的星星"}],"extra":{"totalSize":5,"size":10,"totalPage":1,"page":0}}
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
         * travelist : [{"travelHot":51,"travelType":"156","travelContentUrl":"http://112.74.28.179:8080/Chislim/travelresource/zhaji.htm","isCollect":"No","travelImgUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1492682055&di=2c902b6df28bb23fab4c85d76637b8fd&imgtype=jpg&er=1&src=http%3A%2F%2Ftupian.enterdesk.com%2F2013%2Flxy%2F10%2F10%2F5%2F9.jpg","id":5,"travelTitle":"地上的星"},{"travelHot":231,"travelType":"leixn","travelContentUrl":"http://112.74.28.179:8080/Chislim/travelresource/zhaji.htm","isCollect":"No","travelImgUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1492682055&di=2c902b6df28bb23fab4c85d76637b8fd&imgtype=jpg&er=1&src=http%3A%2F%2Ftupian.enterdesk.com%2F2013%2Flxy%2F10%2F10%2F5%2F9.jpg","id":4,"travelTitle":"地上的星"},{"travelHot":231,"travelType":"leixn","travelContentUrl":"http://112.74.28.179:8080/Chislim/travelresource/zhaji.htm","isCollect":"No","travelImgUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1492682055&di=2c902b6df28bb23fab4c85d76637b8fd&imgtype=jpg&er=1&src=http%3A%2F%2Ftupian.enterdesk.com%2F2013%2Flxy%2F10%2F10%2F5%2F9.jpg","id":3,"travelTitle":"地上的星"},{"travelHot":555,"travelType":"leixn","travelContentUrl":"http://112.74.28.179:8080/Chislim/travelresource/fengjie.html","isCollect":"No","travelImgUrl":"https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1492682055&di=2c902b6df28bb23fab4c85d76637b8fd&imgtype=jpg&er=1&src=http%3A%2F%2Ftupian.enterdesk.com%2F2013%2Flxy%2F10%2F10%2F5%2F9.jpg","id":2,"travelTitle":"仙女湖"},{"travelHot":155,"travelType":"类型","travelContentUrl":"http://112.74.28.179:8080/Chislim/travelresource/zhaji.html","isCollect":"No","travelImgUrl":"http://img3.imgtn.bdimg.com/it/u=4029368265,956204632&fm=23&gp=0.jpg","id":1,"travelTitle":"的湖泊,仿佛是跌落在地上的星星"}]
         * extra : {"totalSize":5,"size":10,"totalPage":1,"page":0}
         */

        private ExtraBean extra;
        private List<TravelistBean> travelist;

        public ExtraBean getExtra() {
            return extra;
        }

        public void setExtra(ExtraBean extra) {
            this.extra = extra;
        }

        public List<TravelistBean> getTravelist() {
            return travelist;
        }

        public void setTravelist(List<TravelistBean> travelist) {
            this.travelist = travelist;
        }

        public static class ExtraBean {
            /**
             * totalSize : 5
             * size : 10
             * totalPage : 1
             * page : 0
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

        public static class TravelistBean {
            /**
             * travelHot : 51
             * travelType : 156
             * travelContentUrl : http://112.74.28.179:8080/Chislim/travelresource/zhaji.htm
             * isCollect : No
             * travelImgUrl : https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1492682055&di=2c902b6df28bb23fab4c85d76637b8fd&imgtype=jpg&er=1&src=http%3A%2F%2Ftupian.enterdesk.com%2F2013%2Flxy%2F10%2F10%2F5%2F9.jpg
             * id : 5
             * travelTitle : 地上的星
             */

            private int travelHot;
            private String travelType;
            private String travelContentUrl;
            private String isCollect;
            private String travelImgUrl;
            private int id;
            private String travelTitle;

            public int getTravelHot() {
                return travelHot;
            }

            public void setTravelHot(int travelHot) {
                this.travelHot = travelHot;
            }

            public String getTravelType() {
                return travelType;
            }

            public void setTravelType(String travelType) {
                this.travelType = travelType;
            }

            public String getTravelContentUrl() {
                return travelContentUrl;
            }

            public void setTravelContentUrl(String travelContentUrl) {
                this.travelContentUrl = travelContentUrl;
            }

            public String getIsCollect() {
                return isCollect;
            }

            public void setIsCollect(String isCollect) {
                this.isCollect = isCollect;
            }

            public String getTravelImgUrl() {
                return travelImgUrl;
            }

            public void setTravelImgUrl(String travelImgUrl) {
                this.travelImgUrl = travelImgUrl;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTravelTitle() {
                return travelTitle;
            }

            public void setTravelTitle(String travelTitle) {
                this.travelTitle = travelTitle;
            }
        }
    }
}
