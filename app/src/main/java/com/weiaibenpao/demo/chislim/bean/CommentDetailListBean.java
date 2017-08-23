package com.weiaibenpao.demo.chislim.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/21.
 */

public class CommentDetailListBean {

    /**
     * code : 0
     * msg : 成功
     * data : {"extra":{"totalSize":6,"size":20,"totalPage":1,"page":0},"praiseList":[{"id":134,"userNickName":"里鉴宝","userIcon":"http://img.hb.aicdn.com/22b6dd9c4377c4ea700e5e0c6a35d05bbb72de304fafb-Xp8dGC_fw658","humorId":48,"userId":9},{"id":17,"userNickName":"小荣荣","userIcon":"http://img.hb.aicdn.com/d83c2e3f81709bbd7306f9262e2adc9dd694636fb0ea-7VqAgF_fw658","humorId":48,"userId":5}],"commentList":[{"comText":"asdasd","preUserId":0,"id":9,"userNickName":"访客","commentTime":20170416,"humorId":48,"userId":1,"preUserNickName":"","parentId":0}]}
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
         * extra : {"totalSize":6,"size":20,"totalPage":1,"page":0}
         * praiseList : [{"id":134,"userNickName":"里鉴宝","userIcon":"http://img.hb.aicdn.com/22b6dd9c4377c4ea700e5e0c6a35d05bbb72de304fafb-Xp8dGC_fw658","humorId":48,"userId":9},{"id":17,"userNickName":"小荣荣","userIcon":"http://img.hb.aicdn.com/d83c2e3f81709bbd7306f9262e2adc9dd694636fb0ea-7VqAgF_fw658","humorId":48,"userId":5}]
         * commentList : [{"comText":"asdasd","preUserId":0,"id":9,"userNickName":"访客","commentTime":20170416,"humorId":48,"userId":1,"preUserNickName":"","parentId":0}]
         */

        private ExtraBean extra;
        private List<PraiseListBean> priaselist;
        private List<Comment> contentlist;
        private String isLike;
        private int likeNum ;

        public String getIsLike() {
            return isLike;
        }

        public void setIsLike(String isLike) {
            this.isLike = isLike;
        }

        public int getLikeNum() {
            return likeNum;
        }

        public void setLikeNum(int likeNum) {
            this.likeNum = likeNum;
        }

        public ExtraBean getExtra() {
            return extra;
        }

        public void setExtra(ExtraBean extra) {
            this.extra = extra;
        }

        public List<PraiseListBean> getPraiseList() {
            return priaselist;
        }

        public void setPraiselist(List<PraiseListBean> praiseList) {
            this.priaselist = praiseList;
        }

        public List<Comment> getCommentList() {
            return contentlist;
        }

        public void setCommentlist(List<Comment> commentList) {
            this.contentlist = commentList;
        }

        public static class ExtraBean {
            /**
             * totalSize : 6
             * size : 20
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

        public static class PraiseListBean {
            /**
             * id : 134
             * userNickName : 里鉴宝
             * userIcon : http://img.hb.aicdn.com/22b6dd9c4377c4ea700e5e0c6a35d05bbb72de304fafb-Xp8dGC_fw658
             * humorId : 48
             * userId : 9
             */

            private int id;
            private String userNickName;
            private String userIcon;
            private int humorId;
            private int userId;

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
        }

//        public static class CommentBean {
//            /**
//             * comText : asdasd
//             * preUserId : 0
//             * id : 9
//             * userNickName : 访客
//             * commentTime : 20170416
//             * humorId : 48
//             * userId : 1
//             * preUserNickName :
//             * parentId : 0
//             */
//
//            private String comText;
//            private int preUserId;
//            private int id;
//            private String userNickName;
//            private int commentTime;
//            private int humorId;
//            private int userId;
//            private String preUserNickName;
//            private int parentId;
//
//            public String getComText() {
//                return comText;
//            }
//
//            public void setComText(String comText) {
//                this.comText = comText;
//            }
//
//            public int getPreUserId() {
//                return preUserId;
//            }
//
//            public void setPreUserId(int preUserId) {
//                this.preUserId = preUserId;
//            }
//
//            public int getId() {
//                return id;
//            }
//
//            public void setId(int id) {
//                this.id = id;
//            }
//
//            public String getUserNickName() {
//                return userNickName;
//            }
//
//            public void setUserNickName(String userNickName) {
//                this.userNickName = userNickName;
//            }
//
//            public int getCommentTime() {
//                return commentTime;
//            }
//
//            public void setCommentTime(int commentTime) {
//                this.commentTime = commentTime;
//            }
//
//            public int getHumorId() {
//                return humorId;
//            }
//
//            public void setHumorId(int humorId) {
//                this.humorId = humorId;
//            }
//
//            public int getUserId() {
//                return userId;
//            }
//
//            public void setUserId(int userId) {
//                this.userId = userId;
//            }
//
//            public String getPreUserNickName() {
//                return preUserNickName;
//            }
//
//            public void setPreUserNickName(String preUserNickName) {
//                this.preUserNickName = preUserNickName;
//            }
//
//            public int getParentId() {
//                return parentId;
//            }
//
//            public void setParentId(int parentId) {
//                this.parentId = parentId;
//            }
//        }
    }
}
