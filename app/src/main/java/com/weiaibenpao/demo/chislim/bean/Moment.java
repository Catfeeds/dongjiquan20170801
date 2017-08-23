package com.weiaibenpao.demo.chislim.bean;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Administrator on 2017/8/18.
 */

public class Moment implements Parcelable {
    /**
     * humorVideoUrl : http://ofplk6att.bkt.clouddn.com/(null)
     * praiselist : [{"id":5,"userNickName":"","userIcon":"","humorId":38,"userId":14},{"id":2,"userNickName":"","userIcon":"http://img3.duitang.com/uploads/item/201602/12/20160212214325_iFSaZ.thumb.224_0.jpeg","humorId":38,"userId":15}]
     * humorContent : 说些什么...
     * comentNum : 0
     * isLike : NO
     * humorImgUrl :
     * isFans : 请关注
     * humorpalce : 中国浙江省杭州市江干区白杨街道2号大街
     * userId : 5
     * likeNum : 0
     * themeTitleStr :
     * commlist : [{"comText":"这是内容","preUserId":11,"id":4,"userNickName":"","commentTime":20170416,"humorId":38,"userId":15,"preUserNickName":"","parentId":3},{"comText":"你好，这是回复内容","preUserId":15,"id":3,"userNickName":"","commentTime":20170416,"humorId":38,"userId":11,"preUserNickName":"","parentId":4},{"comText":"这是内容","preUserId":14,"id":2,"userNickName":"little hu","commentTime":20170416,"humorId":38,"userId":13,"preUserNickName":"","parentId":1},{"comText":"你好，这是回复内容","preUserId":0,"id":1,"userNickName":"","commentTime":20170416,"humorId":38,"userId":14,"preUserNickName":"","parentId":0}]
     * id : 38
     * userNickName :
     * userIcon :
     */

    private String humorVideoUrl;
    private String humorVideoImgUrl;
    private String humorContent;
    private int comentNum;
    private String isLike;
    private String humorImgUrl;
    private String humorImgUrlSmall;
    private String humorpalce;
    private int userId;
    private int likeNum;
    private int height;
    private int width;
    private String themeTitleStr;
    private int id;
    private String userNickName;
    private String userIcon;
    private long createTime;
    private String delete;
    private String isFans;
    private List<PraiselistBean> praiselist;
    private List<CommlistBean> commlist;

    public Moment(){

    }


    protected Moment(Parcel in) {
        humorVideoUrl = in.readString();
        humorVideoImgUrl = in.readString();
        humorContent = in.readString();
        comentNum = in.readInt();
        isLike = in.readString();
        humorImgUrl = in.readString();
        humorImgUrlSmall = in.readString();
        humorpalce = in.readString();
        userId = in.readInt();
        likeNum = in.readInt();
        height = in.readInt();
        width = in.readInt();
        themeTitleStr = in.readString();
        id = in.readInt();
        userNickName = in.readString();
        userIcon = in.readString();
        createTime = in.readLong();
        delete = in.readString();
        isFans = in.readString();
        praiselist = in.createTypedArrayList(PraiselistBean.CREATOR);
        commlist = in.createTypedArrayList(CommlistBean.CREATOR);
    }

    public static final Creator<Moment> CREATOR = new Creator<Moment>() {
        @Override
        public Moment createFromParcel(Parcel in) {
            return new Moment(in);
        }

        @Override
        public Moment[] newArray(int size) {
            return new Moment[size];
        }
    };

    public String getHumorVideoImgUrl() {
        return humorVideoImgUrl;
    }

    public void setHumorVideoImgUrl(String humorVideoImgUrl) {
        this.humorVideoImgUrl = humorVideoImgUrl;
    }

    public String getHumorImgUrlSmall() {
        return humorImgUrlSmall;
    }

    public void setHumorImgUrlSmall(String humorImgUrlSmall) {
        this.humorImgUrlSmall = humorImgUrlSmall;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(long createTime) {
        this.createTime = createTime;
    }

    public String getDelete() {
        return delete;
    }

    public void setDelete(String delete) {
        this.delete = delete;
    }

    public String getHumorVideoUrl() {
        return humorVideoUrl;
    }

    public void setHumorVideoUrl(String humorVideoUrl) {
        this.humorVideoUrl = humorVideoUrl;
    }

    public String getHumorContent() {
        return humorContent;
    }

    public void setHumorContent(String humorContent) {
        this.humorContent = humorContent;
    }

    public int getComentNum() {
        return comentNum;
    }

    public void setComentNum(int comentNum) {
        this.comentNum = comentNum;
    }

    public String getIsLike() {
        return isLike;
    }

    public void setIsLike(String isLike) {
        this.isLike = isLike;
    }

    public String getHumorImgUrl() {
        return humorImgUrl;
    }

    public void setHumorImgUrl(String humorImgUrl) {
        this.humorImgUrl = humorImgUrl;
    }

    public String getIsFans() {
        return isFans;
    }

    public void setIsFans(String isFans) {
        this.isFans = isFans;
    }

    public String getHumorpalce() {
        return humorpalce;
    }

    public void setHumorpalce(String humorpalce) {
        this.humorpalce = humorpalce;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(int likeNum) {
        this.likeNum = likeNum;
    }

    public String getThemeTitleStr() {
        return themeTitleStr;
    }

    public void setThemeTitleStr(String themeTitleStr) {
        this.themeTitleStr = themeTitleStr;
    }

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

    public List<PraiselistBean> getPraiselist() {
        return praiselist;
    }

    public void setPraiselist(List<PraiselistBean> praiselist) {
        this.praiselist = praiselist;
    }

    public List<CommlistBean> getCommlist() {
        return commlist;
    }

    public void setCommlist(List<CommlistBean> commlist) {
        this.commlist = commlist;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(humorVideoUrl);
        dest.writeString(humorVideoImgUrl);
        dest.writeString(humorContent);
        dest.writeInt(comentNum);
        dest.writeString(isLike);
        dest.writeString(humorImgUrl);
        dest.writeString(humorImgUrlSmall);
        dest.writeString(humorpalce);
        dest.writeInt(userId);
        dest.writeInt(likeNum);
        dest.writeInt(height);
        dest.writeInt(width);
        dest.writeString(themeTitleStr);
        dest.writeInt(id);
        dest.writeString(userNickName);
        dest.writeString(userIcon);
        dest.writeLong(createTime);
        dest.writeString(delete);
        dest.writeString(isFans);
        dest.writeTypedList(praiselist);
        dest.writeTypedList(commlist);
    }

    public static class PraiselistBean implements Parcelable {
        /**
         * id : 5
         * userNickName :
         * userIcon :
         * humorId : 38
         * userId : 14
         */

        private int id;
        private String userNickName;
        private String userIcon;
        private int humorId;
        private int userId;

        protected PraiselistBean(Parcel in) {
            id = in.readInt();
            userNickName = in.readString();
            userIcon = in.readString();
            humorId = in.readInt();
            userId = in.readInt();
        }

        public static final Creator<PraiselistBean> CREATOR = new Creator<PraiselistBean>() {
            @Override
            public PraiselistBean createFromParcel(Parcel in) {
                return new PraiselistBean(in);
            }

            @Override
            public PraiselistBean[] newArray(int size) {
                return new PraiselistBean[size];
            }
        };

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

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeInt(id);
            dest.writeString(userNickName);
            dest.writeString(userIcon);
            dest.writeInt(humorId);
            dest.writeInt(userId);
        }
    }

    public static class CommlistBean implements Parcelable {
        /**
         * comText : 这是内容
         * preUserId : 11
         * id : 4
         * userNickName :
         * commentTime : 20170416
         * humorId : 38
         * userId : 15
         * preUserNickName :
         * parentId : 3
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

        public CommlistBean(){

        }

        protected CommlistBean(Parcel in) {
            comText = in.readString();
            preUserId = in.readInt();
            id = in.readInt();
            userNickName = in.readString();
            commentTime = in.readLong();
            humorId = in.readInt();
            userId = in.readInt();
            preUserNickName = in.readString();
            parentId = in.readInt();
        }

        public static final Creator<CommlistBean> CREATOR = new Creator<CommlistBean>() {
            @Override
            public CommlistBean createFromParcel(Parcel in) {
                return new CommlistBean(in);
            }

            @Override
            public CommlistBean[] newArray(int size) {
                return new CommlistBean[size];
            }
        };

        public String getComText() {
            return comText;
        }

        public void setComText(String comText) {
            this.comText = comText;
        }

        public int getPreUserId() {
            return preUserId;
        }

        public void setPreUserId(int preUserId) {
            this.preUserId = preUserId;
        }

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

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getPreUserNickName() {
            return preUserNickName;
        }

        public void setPreUserNickName(String preUserNickName) {
            this.preUserNickName = preUserNickName;
        }

        public int getParentId() {
            return parentId;
        }

        public void setParentId(int parentId) {
            this.parentId = parentId;
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeString(comText);
            dest.writeInt(preUserId);
            dest.writeInt(id);
            dest.writeString(userNickName);
            dest.writeLong(commentTime);
            dest.writeInt(humorId);
            dest.writeInt(userId);
            dest.writeString(preUserNickName);
            dest.writeInt(parentId);
        }
    }

}
