package com.weiaibenpao.demo.chislim.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2017/8/22.
 */

public class Comment implements Parcelable{

    /**
     * id : 114
     * userId : 15
     * commentTime : 1502526970157
     * humorId : 33
     * parentId : 17
     * comText : 不知道你说的啥
     *
     */

    private String comText;
    private int preUserId;
    private int id;
    private String userNickName;
    private long commentTime;
    private int humorId;
    private int userId;
    private String preUserNickName;
    private int parentId;

    public Comment() {
    }

    protected Comment(Parcel in) {
        id = in.readInt();
        userId = in.readInt();
        commentTime = in.readLong();
        humorId = in.readInt();
        parentId = in.readInt();
        comText = in.readString();
        userNickName = in.readString();
        preUserNickName = in.readString();
    }

    public static final Creator<Comment> CREATOR = new Creator<Comment>() {
        @Override
        public Comment createFromParcel(Parcel in) {
            return new Comment(in);
        }

        @Override
        public Comment[] newArray(int size) {
            return new Comment[size];
        }
    };

    @Override
    public String toString() {
        return "DataBean{" +
                "id=" + id +
                ", userId=" + userId +
                ", commentTime=" + commentTime +
                ", humorId=" + humorId +
                ", parentId=" + parentId +
                ", comText='" + comText + '\'' +
                ", userNickName='" + userNickName + '\'' +
                ", preUserNickName='" + preUserNickName + '\'' +
                '}';
    }


    public int getPreUserId() {
        return preUserId;
    }

    public void setPreUserId(int preUserId) {
        this.preUserId = preUserId;
    }

    public String getUserNickName() {
        return userNickName;
    }

    public void setUserNickName(String userNickName) {
        this.userNickName = userNickName;
    }

    public String getPreUserNickName() {
        return preUserNickName;
    }

    public void setPreUserNickName(String preUserNickName) {
        this.preUserNickName = preUserNickName;
    }

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

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getComText() {
        return comText;
    }

    public void setComText(String comText) {
        this.comText = comText;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(userId);
        dest.writeLong(commentTime);
        dest.writeInt(humorId);
        dest.writeInt(parentId);
        dest.writeString(comText);
        dest.writeString(userNickName);
        dest.writeString(preUserNickName);
    }
}
