package com.weiaibenpao.demo.chislim.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lenovo on 2016/12/4.
 */

public class ThemeResule implements Serializable{

    /**
     * code : 0
     * msg : 成功
     * data : {"themelist":[{"themeTitle":"#家乡#","isCollection":"NO","themeContent":"家乡，离开这个地方后，才知道这里是家，非常想念，想家，家想，谓之家乡。","id":6,"themeImgUrl":"http://ofplk6att.bkt.clouddn.com/hometown.png","themeHot":78787},{"themeTitle":"#一张照片,一段故事#","isCollection":"NO","themeContent":"你的手机里有没有那么一张照片，即使更换过许多手机，依然保存到现在，即使删除过很多照片，它依然存在于你的相册中？","id":5,"themeImgUrl":"http://ofplk6att.bkt.clouddn.com/photo_story.png","themeHot":25252},{"themeTitle":"#一本书,一句话#","isCollection":"NO","themeContent":"一本书，一段人生和历史的缩影，一段跨越时间地点的旅行， 一句话，揣测许久，方得之真谛","id":4,"themeImgUrl":"http://ofplk6att.bkt.clouddn.com/readbook.png","themeHot":78789},{"themeTitle":"#时尚#","isCollection":"NO","themeContent":"所谓时尚，是时与尚的结合体。所谓时，乃时间，时下，即在一个时间段内；尚，则有崇尚，高尚，高品位，领先。时尚其实在这个时代而言的，不仅仅是为了修饰，甚至演化成了一种追求真善美的意识。","id":3,"themeImgUrl":"http://ofplk6att.bkt.clouddn.com/feshion.png","themeHot":2564},{"themeTitle":"#跑步故事#","isCollection":"NO","themeContent":"村上村树在他的一书《我在跑步的时候都在想什么》中，完美诠释了他与跑步之间的故事，那么你的故事又是怎样的？","id":2,"themeImgUrl":"http://ofplk6att.bkt.clouddn.com/story_of_run.png","themeHot":52525},{"themeTitle":"#背包旅行#","isCollection":"NO","themeContent":"心灵和身体总要有一个在路上，你的呢？","id":1,"themeImgUrl":"http://ofplk6att.bkt.clouddn.com/travel_with_bag.png","themeHot":2555}],"extra":{"totalSize":6,"size":10,"totalPage":1,"page":0}}
     */

    private int code;
    private String msg;
    /**
     * themelist : [{"themeTitle":"#家乡#","isCollection":"NO","themeContent":"家乡，离开这个地方后，才知道这里是家，非常想念，想家，家想，谓之家乡。","id":6,"themeImgUrl":"http://ofplk6att.bkt.clouddn.com/hometown.png","themeHot":78787},{"themeTitle":"#一张照片,一段故事#","isCollection":"NO","themeContent":"你的手机里有没有那么一张照片，即使更换过许多手机，依然保存到现在，即使删除过很多照片，它依然存在于你的相册中？","id":5,"themeImgUrl":"http://ofplk6att.bkt.clouddn.com/photo_story.png","themeHot":25252},{"themeTitle":"#一本书,一句话#","isCollection":"NO","themeContent":"一本书，一段人生和历史的缩影，一段跨越时间地点的旅行， 一句话，揣测许久，方得之真谛","id":4,"themeImgUrl":"http://ofplk6att.bkt.clouddn.com/readbook.png","themeHot":78789},{"themeTitle":"#时尚#","isCollection":"NO","themeContent":"所谓时尚，是时与尚的结合体。所谓时，乃时间，时下，即在一个时间段内；尚，则有崇尚，高尚，高品位，领先。时尚其实在这个时代而言的，不仅仅是为了修饰，甚至演化成了一种追求真善美的意识。","id":3,"themeImgUrl":"http://ofplk6att.bkt.clouddn.com/feshion.png","themeHot":2564},{"themeTitle":"#跑步故事#","isCollection":"NO","themeContent":"村上村树在他的一书《我在跑步的时候都在想什么》中，完美诠释了他与跑步之间的故事，那么你的故事又是怎样的？","id":2,"themeImgUrl":"http://ofplk6att.bkt.clouddn.com/story_of_run.png","themeHot":52525},{"themeTitle":"#背包旅行#","isCollection":"NO","themeContent":"心灵和身体总要有一个在路上，你的呢？","id":1,"themeImgUrl":"http://ofplk6att.bkt.clouddn.com/travel_with_bag.png","themeHot":2555}]
     * extra : {"totalSize":6,"size":10,"totalPage":1,"page":0}
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
         * totalSize : 6
         * size : 10
         * totalPage : 1
         * page : 0
         */

        private ExtraBean extra;
        /**
         * themeTitle : #家乡#
         * isCollection : NO
         * themeContent : 家乡，离开这个地方后，才知道这里是家，非常想念，想家，家想，谓之家乡。
         * id : 6
         * themeImgUrl : http://ofplk6att.bkt.clouddn.com/hometown.png
         * themeHot : 78787
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

        public static class ThemelistBean implements Serializable,Parcelable{
            private String themeTitle;
            private String isCollection;
            private String themeContent;
            private int id;
            private String themeImgUrl;
            private int themeHot;

            protected ThemelistBean(Parcel in) {
                themeTitle = in.readString();
                isCollection = in.readString();
                themeContent = in.readString();
                id = in.readInt();
                themeImgUrl = in.readString();
                themeHot = in.readInt();
            }

            public static final Creator<ThemelistBean> CREATOR = new Creator<ThemelistBean>() {
                @Override
                public ThemelistBean createFromParcel(Parcel in) {
                    return new ThemelistBean(in);
                }

                @Override
                public ThemelistBean[] newArray(int size) {
                    return new ThemelistBean[size];
                }
            };

            public String getThemeTitle() {
                return themeTitle;
            }

            public void setThemeTitle(String themeTitle) {
                this.themeTitle = themeTitle;
            }

            public String getIsCollection() {
                return isCollection;
            }

            public void setIsCollection(String isCollection) {
                this.isCollection = isCollection;
            }

            public String getThemeContent() {
                return themeContent;
            }

            public void setThemeContent(String themeContent) {
                this.themeContent = themeContent;
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getThemeImgUrl() {
                return themeImgUrl;
            }

            public void setThemeImgUrl(String themeImgUrl) {
                this.themeImgUrl = themeImgUrl;
            }

            public int getThemeHot() {
                return themeHot;
            }

            public void setThemeHot(int themeHot) {
                this.themeHot = themeHot;
            }

            @Override
            public int describeContents() {
                return 0;
            }

            @Override
            public void writeToParcel(Parcel parcel, int i) {
                parcel.writeString(themeTitle);
                parcel.writeString(isCollection);
                parcel.writeString(themeContent);
                parcel.writeInt(id);
                parcel.writeString(themeImgUrl);
                parcel.writeInt(themeHot);
            }
        }
    }
}
