package com.weiaibenpao.demo.chislim.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by lenovo on 2016/12/9.
 */

public class SendHumorResult {

    /**
     * code : 0
     * msg : 成功
     * data : {"id":19,"userId":17,"themeTitleStr":"话题名称,逗号拼接","humorContent":"说说内容","humorImgUrl":"说说的图片地址","likeNum":0,"comentNum":0,"humorDate":1481252956137}
     */

    private int code;
    private String msg;
    /**
     * id : 19
     * userId : 17
     * themeTitleStr : 话题名称,逗号拼接
     * humorContent : 说说内容
     * humorImgUrl : 说说的图片地址
     * likeNum : 0
     * comentNum : 0
     * humorDate : 1481252956137
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

    public static class DataBean implements Parcelable{
        private int id;
        private int userId;
        private String themeTitleStr;
        private String humorContent;
        private String humorImgUrl;
        private int likeNum;
        private int comentNum;
        private long humorDate;

        protected DataBean(Parcel in) {
            id = in.readInt();
            userId = in.readInt();
            themeTitleStr = in.readString();
            humorContent = in.readString();
            humorImgUrl = in.readString();
            likeNum = in.readInt();
            comentNum = in.readInt();
            humorDate = in.readLong();
        }

        public static final Creator<DataBean> CREATOR = new Creator<DataBean>() {
            @Override
            public DataBean createFromParcel(Parcel in) {
                return new DataBean(in);
            }

            @Override
            public DataBean[] newArray(int size) {
                return new DataBean[size];
            }
        };

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getThemeTitleStr() {
            return themeTitleStr;
        }

        public void setThemeTitleStr(String themeTitleStr) {
            this.themeTitleStr = themeTitleStr;
        }

        public String getHumorContent() {
            return humorContent;
        }

        public void setHumorContent(String humorContent) {
            this.humorContent = humorContent;
        }

        public String getHumorImgUrl() {
            return humorImgUrl;
        }

        public void setHumorImgUrl(String humorImgUrl) {
            this.humorImgUrl = humorImgUrl;
        }

        public int getLikeNum() {
            return likeNum;
        }

        public void setLikeNum(int likeNum) {
            this.likeNum = likeNum;
        }

        public int getComentNum() {
            return comentNum;
        }

        public void setComentNum(int comentNum) {
            this.comentNum = comentNum;
        }

        public long getHumorDate() {
            return humorDate;
        }

        public void setHumorDate(long humorDate) {
            this.humorDate = humorDate;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(id);
            parcel.writeInt(userId);
            parcel.writeString(themeTitleStr);
            parcel.writeString(humorContent);
            parcel.writeString(humorImgUrl);
            parcel.writeInt(likeNum);
            parcel.writeInt(comentNum);
            parcel.writeLong(humorDate);
        }
    }
}
