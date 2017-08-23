package com.weiaibenpao.demo.chislim.hurui.bean;

import java.util.List;

/**
 * Created by lenovo on 2017/4/11.
 */

public class FlexibleBean {

    /**
     * code : 0
     * msg : 成功
     * data : {"extra":{"totalSize":3,"size":10,"totalPage":1,"page":0},"activitylist":[{"activityImgUrl":"http://ofplk6att.bkt.clouddn.com/img_activity1.png","imgCastingContentUrl":"http://m.51sai.com/deal/show/id/6391","activityTitle":"女神马拉松#在缠绵的春雨丝中，在飘扬的柳丝条中，专属于跑步女神的跑步party终于来了！","joinNumber":55,"isCollect":"No","id":3},{"activityImgUrl":"http://ofplk6att.bkt.clouddn.com/img_activity2.png","imgCastingContentUrl":"http://m.51sai.com/deal/show/id/6391","activityTitle":"跑步标题#以及描述","joinNumber":55,"isCollect":"No","id":2},{"activityImgUrl":"http://ofplk6att.bkt.clouddn.com/img_activity3.png","imgCastingContentUrl":"http://m.51sai.com/deal/show/id/6391","activityTitle":"女神马拉松#在缠绵的春雨丝中，在飘扬的柳丝条中，专属于跑步女神的跑步party终于来了！","joinNumber":25,"isCollect":"No","id":1}]}
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
         * extra : {"totalSize":3,"size":10,"totalPage":1,"page":0}
         * activitylist : [{"activityImgUrl":"http://ofplk6att.bkt.clouddn.com/img_activity1.png","imgCastingContentUrl":"http://m.51sai.com/deal/show/id/6391","activityTitle":"女神马拉松#在缠绵的春雨丝中，在飘扬的柳丝条中，专属于跑步女神的跑步party终于来了！","joinNumber":55,"isCollect":"No","id":3},{"activityImgUrl":"http://ofplk6att.bkt.clouddn.com/img_activity2.png","imgCastingContentUrl":"http://m.51sai.com/deal/show/id/6391","activityTitle":"跑步标题#以及描述","joinNumber":55,"isCollect":"No","id":2},{"activityImgUrl":"http://ofplk6att.bkt.clouddn.com/img_activity3.png","imgCastingContentUrl":"http://m.51sai.com/deal/show/id/6391","activityTitle":"女神马拉松#在缠绵的春雨丝中，在飘扬的柳丝条中，专属于跑步女神的跑步party终于来了！","joinNumber":25,"isCollect":"No","id":1}]
         */

        private ExtraBean extra;
        private List<ActivitylistBean> activitylist;

        public ExtraBean getExtra() {
            return extra;
        }

        public void setExtra(ExtraBean extra) {
            this.extra = extra;
        }

        public List<ActivitylistBean> getActivitylist() {
            return activitylist;
        }

        public void setActivitylist(List<ActivitylistBean> activitylist) {
            this.activitylist = activitylist;
        }

        public static class ExtraBean {
            /**
             * totalSize : 3
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

        public static class ActivitylistBean {
            /**
             * activityImgUrl : http://ofplk6att.bkt.clouddn.com/img_activity1.png
             * imgCastingContentUrl : http://m.51sai.com/deal/show/id/6391
             * activityTitle : 女神马拉松#在缠绵的春雨丝中，在飘扬的柳丝条中，专属于跑步女神的跑步party终于来了！
             * joinNumber : 55
             * isCollect : No
             * id : 3
             */

            private String activityImgUrl;
            private String imgCastingContentUrl;
            private String activityTitle;
            private int joinNumber;
            private String isCollect;
            private int id;

            public String getActivityImgUrl() {
                return activityImgUrl;
            }

            public void setActivityImgUrl(String activityImgUrl) {
                this.activityImgUrl = activityImgUrl;
            }

            public String getImgCastingContentUrl() {
                return imgCastingContentUrl;
            }

            public void setImgCastingContentUrl(String imgCastingContentUrl) {
                this.imgCastingContentUrl = imgCastingContentUrl;
            }

            public String getActivityTitle() {
                return activityTitle;
            }

            public void setActivityTitle(String activityTitle) {
                this.activityTitle = activityTitle;
            }

            public int getJoinNumber() {
                return joinNumber;
            }

            public void setJoinNumber(int joinNumber) {
                this.joinNumber = joinNumber;
            }

            public String getIsCollect() {
                return isCollect;
            }

            public void setIsCollect(String isCollect) {
                this.isCollect = isCollect;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }
        }
    }
}
