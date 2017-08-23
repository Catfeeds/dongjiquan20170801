package com.weiaibenpao.demo.chislim.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lenovo on 2016/12/13.
 */

public class GetCollectionResult implements Serializable {

    /**
     * code : 0
     * msg : 成功
     * data : {"extra":{"totalSize":2,"size":10,"totalPage":1,"page":0},"collectionlist":[{"imgUrl":"http://ofplk6att.bkt.clouddn.com/hometown.png","collectionType":"话题","collectionTime":1481598576137,"id":19,"title":"#家#","userId":17,"objectId":6},{"imgUrl":"http://ofplk6att.bkt.clouddn.com/story_of_run.png","collectionType":"话题","collectionTime":1481594824985,"id":2,"title":"#背包旅行#","userId":17,"objectId":1}]}
     */

    private int code;
    private String msg;
    /**
     * extra : {"totalSize":2,"size":10,"totalPage":1,"page":0}
     * collectionlist : [{"imgUrl":"http://ofplk6att.bkt.clouddn.com/hometown.png","collectionType":"话题","collectionTime":1481598576137,"id":19,"title":"#家#","userId":17,"objectId":6},{"imgUrl":"http://ofplk6att.bkt.clouddn.com/story_of_run.png","collectionType":"话题","collectionTime":1481594824985,"id":2,"title":"#背包旅行#","userId":17,"objectId":1}]
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

    public DataBean getData()  {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean implements Serializable{
        /**
         * totalSize : 2
         * size : 10
         * totalPage : 1
         * page : 0
         */

        private ExtraBean extra;
        /**
         * imgUrl : http://ofplk6att.bkt.clouddn.com/hometown.png
         * collectionType : 话题
         * collectionTime : 1481598576137
         * id : 19
         * title : #家#
         * userId : 17
         * objectId : 6
         */

        private List<CollectionlistBean> collectionlist;

        public ExtraBean getExtra() {
            return extra;
        }

        public void setExtra(ExtraBean extra) {
            this.extra = extra;
        }

        public List<CollectionlistBean> getCollectionlist() {
            return collectionlist;
        }

        public void setCollectionlist(List<CollectionlistBean> collectionlist) {
            this.collectionlist = collectionlist;
        }

        public static class ExtraBean implements Serializable {
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

        public static class CollectionlistBean implements Serializable{
            private String imgUrl;
            private String collectionType;
            private long collectionTime;
            private int id;
            private String title;
            private int userId;
            private int objectId;

            public String getImgUrl() {
                return imgUrl;
            }

            public void setImgUrl(String imgUrl) {
                this.imgUrl = imgUrl;
            }

            public String getCollectionType() {
                return collectionType;
            }

            public void setCollectionType(String collectionType) {
                this.collectionType = collectionType;
            }

            public long getCollectionTime() {
                return collectionTime;
            }

            public void setCollectionTime(long collectionTime) {
                this.collectionTime = collectionTime;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public int getObjectId() {
                return objectId;
            }

            public void setObjectId(int objectId) {
                this.objectId = objectId;
            }
        }
    }
}
