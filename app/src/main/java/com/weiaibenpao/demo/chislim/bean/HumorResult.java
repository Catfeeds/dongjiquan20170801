package com.weiaibenpao.demo.chislim.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lenovo on 2016/12/4.
 */

public class HumorResult implements Serializable{


    /**
     * code : 0
     * msg : 成功
     * data : {"humorlist":[{"humorContent":"说说内容","comentNum":0,"isLike":"NO","humorImgUrl":"说说的图片地址","id":18,"userNickName":"小张","userIcon":"http://112.74.28.179:8080/Chislim/Image/20160817112589.PNG","userId":2,"humorDate":1481018164579,"likeNum":0,"themeTitleStr":"werrt"},{"humorContent":"说说内容","comentNum":0,"isLike":"NO","humorImgUrl":"说说的图片地址","id":17,"userNickName":"小张","userIcon":"http://112.74.28.179:8080/Chislim/Image/20160817112589.PNG","userId":2,"humorDate":1481018099464,"likeNum":0,"themeTitleStr":"wrew"},{"humorContent":"毕业了，失业了？","comentNum":11,"isLike":"NO","humorImgUrl":"http://ofplk6att.bkt.clouddn.com/biye.png","id":16,"userNickName":"小张","userIcon":"http://112.74.28.179:8080/Chislim/Image/20160817112589.PNG","userId":2,"humorDate":1481018099464,"likeNum":321,"themeTitleStr":"sdf"},{"humorContent":"一场永不分离的恋爱","comentNum":12,"isLike":"NO","humorImgUrl":"http://ofplk6att.bkt.clouddn.com/lianai.png","id":15,"userNickName":"小张","userIcon":"http://112.74.28.179:8080/Chislim/Image/20160817112589.PNG","userId":2,"humorDate":1481018099464,"likeNum":222,"themeTitleStr":"sdf"},{"humorContent":"美食节","comentNum":13,"isLike":"NO","humorImgUrl":"http://ofplk6att.bkt.clouddn.com/meishi.png","id":14,"userNickName":"小张","userIcon":"http://112.74.28.179:8080/Chislim/Image/20160817112589.PNG","userId":2,"humorDate":1481018099464,"likeNum":222,"themeTitleStr":"sdf"},{"humorContent":"你来，或者不来","comentNum":14,"isLike":"NO","humorImgUrl":"http://ofplk6att.bkt.clouddn.com/cangyang.png","id":13,"userNickName":"小张","userIcon":"http://112.74.28.179:8080/Chislim/Image/20160817112589.PNG","userId":2,"humorDate":1481018099464,"likeNum":433,"themeTitleStr":"dsf"},{"humorContent":"睡梦中的狮子","comentNum":15,"isLike":"NO","humorImgUrl":"http://ofplk6att.bkt.clouddn.com/shizi.png","id":12,"userNickName":"小张","userIcon":"http://112.74.28.179:8080/Chislim/Image/20160817112589.PNG","userId":2,"humorDate":1481018099464,"likeNum":987,"themeTitleStr":"dsf"},{"humorContent":"大明湖畔的夏雨荷","comentNum":16,"isLike":"NO","humorImgUrl":"http://ofplk6att.bkt.clouddn.com/xiayuhe.png","id":11,"userNickName":"小张","userIcon":"http://112.74.28.179:8080/Chislim/Image/20160817112589.PNG","userId":2,"humorDate":1481018099464,"likeNum":232,"themeTitleStr":"sdf"},{"humorContent":"路漫漫其修远兮","comentNum":1,"isLike":"NO","humorImgUrl":"http://ofplk6att.bkt.clouddn.com/quyuan.png","id":10,"userNickName":"小张","userIcon":"http://112.74.28.179:8080/Chislim/Image/20160817112589.PNG","userId":2,"humorDate":1481018099464,"likeNum":51,"themeTitleStr":"sdf"},{"humorContent":"樱花绽放，你在等着谁？","comentNum":7,"isLike":"NO","humorImgUrl":"http://ofplk6att.bkt.clouddn.com/yinghua.png","id":9,"userNickName":"小李","userIcon":"http://112.74.28.179:8080/Chislim/Image/20160817112589.PNG","userId":3,"humorDate":1481018099464,"likeNum":429,"themeTitleStr":"sdf"}],"extra":{"totalSize":18,"size":10,"totalPage":2,"page":0}}
     */

    private int code;
    private String msg;
    /**
     * humorlist : [{"humorContent":"说说内容","comentNum":0,"isLike":"NO","humorImgUrl":"说说的图片地址","id":18,"userNickName":"小张","userIcon":"http://112.74.28.179:8080/Chislim/Image/20160817112589.PNG","userId":2,"humorDate":1481018164579,"likeNum":0,"themeTitleStr":"werrt"},{"humorContent":"说说内容","comentNum":0,"isLike":"NO","humorImgUrl":"说说的图片地址","id":17,"userNickName":"小张","userIcon":"http://112.74.28.179:8080/Chislim/Image/20160817112589.PNG","userId":2,"humorDate":1481018099464,"likeNum":0,"themeTitleStr":"wrew"},{"humorContent":"毕业了，失业了？","comentNum":11,"isLike":"NO","humorImgUrl":"http://ofplk6att.bkt.clouddn.com/biye.png","id":16,"userNickName":"小张","userIcon":"http://112.74.28.179:8080/Chislim/Image/20160817112589.PNG","userId":2,"humorDate":1481018099464,"likeNum":321,"themeTitleStr":"sdf"},{"humorContent":"一场永不分离的恋爱","comentNum":12,"isLike":"NO","humorImgUrl":"http://ofplk6att.bkt.clouddn.com/lianai.png","id":15,"userNickName":"小张","userIcon":"http://112.74.28.179:8080/Chislim/Image/20160817112589.PNG","userId":2,"humorDate":1481018099464,"likeNum":222,"themeTitleStr":"sdf"},{"humorContent":"美食节","comentNum":13,"isLike":"NO","humorImgUrl":"http://ofplk6att.bkt.clouddn.com/meishi.png","id":14,"userNickName":"小张","userIcon":"http://112.74.28.179:8080/Chislim/Image/20160817112589.PNG","userId":2,"humorDate":1481018099464,"likeNum":222,"themeTitleStr":"sdf"},{"humorContent":"你来，或者不来","comentNum":14,"isLike":"NO","humorImgUrl":"http://ofplk6att.bkt.clouddn.com/cangyang.png","id":13,"userNickName":"小张","userIcon":"http://112.74.28.179:8080/Chislim/Image/20160817112589.PNG","userId":2,"humorDate":1481018099464,"likeNum":433,"themeTitleStr":"dsf"},{"humorContent":"睡梦中的狮子","comentNum":15,"isLike":"NO","humorImgUrl":"http://ofplk6att.bkt.clouddn.com/shizi.png","id":12,"userNickName":"小张","userIcon":"http://112.74.28.179:8080/Chislim/Image/20160817112589.PNG","userId":2,"humorDate":1481018099464,"likeNum":987,"themeTitleStr":"dsf"},{"humorContent":"大明湖畔的夏雨荷","comentNum":16,"isLike":"NO","humorImgUrl":"http://ofplk6att.bkt.clouddn.com/xiayuhe.png","id":11,"userNickName":"小张","userIcon":"http://112.74.28.179:8080/Chislim/Image/20160817112589.PNG","userId":2,"humorDate":1481018099464,"likeNum":232,"themeTitleStr":"sdf"},{"humorContent":"路漫漫其修远兮","comentNum":1,"isLike":"NO","humorImgUrl":"http://ofplk6att.bkt.clouddn.com/quyuan.png","id":10,"userNickName":"小张","userIcon":"http://112.74.28.179:8080/Chislim/Image/20160817112589.PNG","userId":2,"humorDate":1481018099464,"likeNum":51,"themeTitleStr":"sdf"},{"humorContent":"樱花绽放，你在等着谁？","comentNum":7,"isLike":"NO","humorImgUrl":"http://ofplk6att.bkt.clouddn.com/yinghua.png","id":9,"userNickName":"小李","userIcon":"http://112.74.28.179:8080/Chislim/Image/20160817112589.PNG","userId":3,"humorDate":1481018099464,"likeNum":429,"themeTitleStr":"sdf"}]
     * extra : {"totalSize":18,"size":10,"totalPage":2,"page":0}
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
         * totalSize : 18
         * size : 10
         * totalPage : 2
         * page : 0
         */

        private ExtraBean extra;
        /**
         * humorContent : 说说内容
         * comentNum : 0
         * isLike : NO
         * humorImgUrl : 说说的图片地址
         * id : 18
         * userNickName : 小张
         * userIcon : http://112.74.28.179:8080/Chislim/Image/20160817112589.PNG
         * userId : 2
         * humorDate : 1481018164579
         * likeNum : 0
         * themeTitleStr : werrt
         */

        private List<HumorlistBean> humorlist;

        public ExtraBean getExtra() {
            return extra;
        }

        public void setExtra(ExtraBean extra) {
            this.extra = extra;
        }

        public List<HumorlistBean> getHumorlist() {
            return humorlist;
        }

        public void setHumorlist(List<HumorlistBean> humorlist) {
            this.humorlist = humorlist;
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

        public static class HumorlistBean implements Serializable{
            private String humorContent;
            private int comentNum;
            private String isLike;
            private String humorImgUrl;
            private int id;
            private String userNickName;
            private String userIcon;
            private int userId;
            private long humorDate;
            private int likeNum;
            private String themeTitleStr;

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

            public int getUserId() {
                return userId;
            }

            public void setUserId(int userId) {
                this.userId = userId;
            }

            public long getHumorDate() {
                return humorDate;
            }

            public void setHumorDate(long humorDate) {
                this.humorDate = humorDate;
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
        }
    }
}
