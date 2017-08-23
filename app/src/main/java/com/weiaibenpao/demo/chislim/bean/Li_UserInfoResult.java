package com.weiaibenpao.demo.chislim.bean;

import java.util.List;

/**
 * Created by lenovo on 2017/4/24.
 */

public class Li_UserInfoResult {

    /**
     * code : 0
     * msg : 成功
     * data : {"humorlist":[{"humorVideoUrl":"","praiselist":[{"id":11,"userNickName":"里鉴宝","userIcon":"http://img.hb.aicdn.com/13fc2bdcdde34426337adfc990cb04d7bc610023198bd-ohL8yQ_fw658","humorId":37,"userId":6},{"id":10,"userNickName":"里鉴宝","userIcon":"http://img.hb.aicdn.com/d83c2e3f81709bbd7306f9262e2adc9dd694636fb0ea-7VqAgF_fw658","humorId":37,"userId":5}],"commlist":[],"humorContent":"","comentNum":0,"isLike":"NO","humorImgUrl":"http://ofplk6att.bkt.clouddn.com//storage/emulated/0/DCIM/Screenshots/Screenshot_2016-06-06-23-12-26-431_com.supercell.boombeach.BD.jpg,http://ofplk6att.bkt.clouddn.com//storage/emulated/0/DCIM/Screenshots/Screenshot_2016-06-06-15-25-33-822_com.supercell.boombeach.BD.jpg,http://ofplk6att.bkt.clouddn.com//storage/emulated/0/DCIM/Screenshots/Screenshot_2016-07-22-09-38-39_com.miui.home.jpg,http://ofplk6att.bkt.clouddn.com//storage/emulated/0/DCIM/Screenshots/Screenshot_2016-07-26-10-24-36_com.miui.weather2.jpg","humorpalce":"浙江省杭州市江干区","id":37,"userId":15,"likeNum":0,"themeTitleStr":""}],"cares":0,"countSport":0,"fanscount":0,"extra":{"totalSize":1,"size":10,"totalPage":1,"page":0},"isFans":"请关注","user":{"id":15,"userImage":"http://img.hb.aicdn.com/66d1aab5d00cc91b7cf548f08cb674dde67d1da815d07-EIaziJ_fw658","userName":"里鉴宝","userPass":"******","userHeight":170,"userWeight":78,"BMI":0,"userPhone":"15527223470","userEmail":"jianbaopp@163.com","userSex":"男","userBirth":"1993.06.07","userIntru":"花开花落，无欲而安","channelId":"54465","registerSource":"phone","openId":"54465","hobby":"走马观花望穿世界","mark":14}}
     */

    private int code;
    private String msg;
    /**
     * humorlist : [{"humorVideoUrl":"","praiselist":[{"id":11,"userNickName":"里鉴宝","userIcon":"http://img.hb.aicdn.com/13fc2bdcdde34426337adfc990cb04d7bc610023198bd-ohL8yQ_fw658","humorId":37,"userId":6},{"id":10,"userNickName":"里鉴宝","userIcon":"http://img.hb.aicdn.com/d83c2e3f81709bbd7306f9262e2adc9dd694636fb0ea-7VqAgF_fw658","humorId":37,"userId":5}],"commlist":[],"humorContent":"","comentNum":0,"isLike":"NO","humorImgUrl":"http://ofplk6att.bkt.clouddn.com//storage/emulated/0/DCIM/Screenshots/Screenshot_2016-06-06-23-12-26-431_com.supercell.boombeach.BD.jpg,http://ofplk6att.bkt.clouddn.com//storage/emulated/0/DCIM/Screenshots/Screenshot_2016-06-06-15-25-33-822_com.supercell.boombeach.BD.jpg,http://ofplk6att.bkt.clouddn.com//storage/emulated/0/DCIM/Screenshots/Screenshot_2016-07-22-09-38-39_com.miui.home.jpg,http://ofplk6att.bkt.clouddn.com//storage/emulated/0/DCIM/Screenshots/Screenshot_2016-07-26-10-24-36_com.miui.weather2.jpg","humorpalce":"浙江省杭州市江干区","id":37,"userId":15,"likeNum":0,"themeTitleStr":""}]
     * cares : 0
     * countSport : 0
     * fanscount : 0
     * extra : {"totalSize":1,"size":10,"totalPage":1,"page":0}
     * isFans : 请关注
     * user : {"id":15,"userImage":"http://img.hb.aicdn.com/66d1aab5d00cc91b7cf548f08cb674dde67d1da815d07-EIaziJ_fw658","userName":"里鉴宝","userPass":"******","userHeight":170,"userWeight":78,"BMI":0,"userPhone":"15527223470","userEmail":"jianbaopp@163.com","userSex":"男","userBirth":"1993.06.07","userIntru":"花开花落，无欲而安","channelId":"54465","registerSource":"phone","openId":"54465","hobby":"走马观花望穿世界","mark":14}
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
        private int cares;
        private int countSport;
        private int fanscount;
        /**
         * totalSize : 1
         * size : 10
         * totalPage : 1
         * page : 0
         */

        private ExtraBean extra;
        private String isFans;
        private String name;
        private int gradeLevel;


        /**
         * id : 15
         * userImage : http://img.hb.aicdn.com/66d1aab5d00cc91b7cf548f08cb674dde67d1da815d07-EIaziJ_fw658
         * userName : 里鉴宝
         * userPass : ******
         * userHeight : 170
         * userWeight : 78
         * BMI : 0
         * userPhone : 15527223470
         * userEmail : jianbaopp@163.com
         * userSex : 男
         * userBirth : 1993.06.07
         * userIntru : 花开花落，无欲而安
         * channelId : 54465
         * registerSource : phone
         * openId : 54465
         * hobby : 走马观花望穿世界
         * mark : 14
         */

        private UserBean user;
        /**
         * humorVideoUrl :
         * praiselist : [{"id":11,"userNickName":"里鉴宝","userIcon":"http://img.hb.aicdn.com/13fc2bdcdde34426337adfc990cb04d7bc610023198bd-ohL8yQ_fw658","humorId":37,"userId":6},{"id":10,"userNickName":"里鉴宝","userIcon":"http://img.hb.aicdn.com/d83c2e3f81709bbd7306f9262e2adc9dd694636fb0ea-7VqAgF_fw658","humorId":37,"userId":5}]
         * commlist : []
         * humorContent :
         * comentNum : 0
         * isLike : NO
         * humorImgUrl : http://ofplk6att.bkt.clouddn.com//storage/emulated/0/DCIM/Screenshots/Screenshot_2016-06-06-23-12-26-431_com.supercell.boombeach.BD.jpg,http://ofplk6att.bkt.clouddn.com//storage/emulated/0/DCIM/Screenshots/Screenshot_2016-06-06-15-25-33-822_com.supercell.boombeach.BD.jpg,http://ofplk6att.bkt.clouddn.com//storage/emulated/0/DCIM/Screenshots/Screenshot_2016-07-22-09-38-39_com.miui.home.jpg,http://ofplk6att.bkt.clouddn.com//storage/emulated/0/DCIM/Screenshots/Screenshot_2016-07-26-10-24-36_com.miui.weather2.jpg
         * humorpalce : 浙江省杭州市江干区
         * id : 37
         * userId : 15
         * likeNum : 0
         * themeTitleStr :
         */

        private List<Moment> humorlist;

        public int getGradeLevel() {
            return gradeLevel;
        }

        public void setGradeLevel(int gradeLevel) {
            this.gradeLevel = gradeLevel;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCares() {
            return cares;
        }

        public void setCares(int cares) {
            this.cares = cares;
        }

        public int  getCountSport() {
            return countSport;
        }

        public void setCountSport(int countSport) {
            this.countSport = countSport;
        }

        public int getFanscount() {
            return fanscount;
        }

        public void setFanscount(int fanscount) {
            this.fanscount = fanscount;
        }

        public ExtraBean getExtra() {
            return extra;
        }

        public void setExtra(ExtraBean extra) {
            this.extra = extra;
        }

        public String getIsFans() {
            return isFans;
        }

        public void setIsFans(String isFans) {
            this.isFans = isFans;
        }

        public UserBean getUser() {
            return user;
        }

        public void setUser(UserBean user) {
            this.user = user;
        }

        public List<Moment> getHumorlist() {
            return humorlist;
        }

        public void setHumorlist(List<Moment> humorlist) {
            this.humorlist = humorlist;
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

        public static class UserBean {
            private int id;
            private String userImage;
            private String userName;
            private String userPass;
            private int userHeigh;
            private int userWeight;
            private int BMI;
            private String userPhone;
            private String userEmail;
            private String userSex;
            private String userBirth;
            private String userIntru;
            private String channelId;
            private String registerSource;
            private String openId;
            private String hobby;
            private int mark;

            @Override
            public String toString() {
                return "UserBean{" +
                        "id=" + id +
                        ", userImage='" + userImage + '\'' +
                        ", userName='" + userName + '\'' +
                        ", userPass='" + userPass + '\'' +
                        ", userHeigh=" + userHeigh +
                        ", userWeight=" + userWeight +
                        ", BMI=" + BMI +
                        ", userPhone='" + userPhone + '\'' +
                        ", userEmail='" + userEmail + '\'' +
                        ", userSex='" + userSex + '\'' +
                        ", userBirth='" + userBirth + '\'' +
                        ", userIntru='" + userIntru + '\'' +
                        ", channelId='" + channelId + '\'' +
                        ", registerSource='" + registerSource + '\'' +
                        ", openId='" + openId + '\'' +
                        ", hobby='" + hobby + '\'' +
                        ", mark=" + mark +
                        '}';
            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getUserImage() {
                return userImage;
            }

            public void setUserImage(String userImage) {
                this.userImage = userImage;
            }

            public String getUserName() {
                return userName;
            }

            public void setUserName(String userName) {
                this.userName = userName;
            }

            public String getUserPass() {
                return userPass;
            }

            public void setUserPass(String userPass) {
                this.userPass = userPass;
            }

            public int getUserHeigh() {
                return userHeigh;
            }

            public void setUserHeigh(int userHeigh) {
                this.userHeigh = userHeigh;
            }

            public int getUserWeight() {
                return userWeight;
            }

            public void setUserWeight(int userWeight) {
                this.userWeight = userWeight;
            }

            public int getBMI() {
                return BMI;
            }

            public void setBMI(int BMI) {
                this.BMI = BMI;
            }

            public String getUserPhone() {
                return userPhone;
            }

            public void setUserPhone(String userPhone) {
                this.userPhone = userPhone;
            }

            public String getUserEmail() {
                return userEmail;
            }

            public void setUserEmail(String userEmail) {
                this.userEmail = userEmail;
            }

            public String getUserSex() {
                return userSex;
            }

            public void setUserSex(String userSex) {
                this.userSex = userSex;
            }

            public String getUserBirth() {
                return userBirth;
            }

            public void setUserBirth(String userBirth) {
                this.userBirth = userBirth;
            }

            public String getUserIntru() {
                return userIntru;
            }

            public void setUserIntru(String userIntru) {
                this.userIntru = userIntru;
            }

            public String getChannelId() {
                return channelId;
            }

            public void setChannelId(String channelId) {
                this.channelId = channelId;
            }

            public String getRegisterSource() {
                return registerSource;
            }

            public void setRegisterSource(String registerSource) {
                this.registerSource = registerSource;
            }

            public String getOpenId() {
                return openId;
            }

            public void setOpenId(String openId) {
                this.openId = openId;
            }

            public String getHobby() {
                return hobby;
            }

            public void setHobby(String hobby) {
                this.hobby = hobby;
            }

            public int getMark() {
                return mark;
            }

            public void setMark(int mark) {
                this.mark = mark;
            }
        }

        public static class HumorlistBean {
            private String humorVideoUrl;
            private String humorContent;
            private int comentNum;
            private String isLike;
            private String humorImgUrl;
            private String humorpalce;
            private int id;
            private int userId;
            private int likeNum;
            private String themeTitleStr;
            /**
             * id : 11
             * userNickName : 里鉴宝
             * userIcon : http://img.hb.aicdn.com/13fc2bdcdde34426337adfc990cb04d7bc610023198bd-ohL8yQ_fw658
             * humorId : 37
             * userId : 6
             */

            private List<PraiselistBean> praiselist;
            private List<?> commlist;

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

            public String getHumorpalce() {
                return humorpalce;
            }

            public void setHumorpalce(String humorpalce) {
                this.humorpalce = humorpalce;
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

            public List<PraiselistBean> getPraiselist() {
                return praiselist;
            }

            public void setPraiselist(List<PraiselistBean> praiselist) {
                this.praiselist = praiselist;
            }

            public List<?> getCommlist() {
                return commlist;
            }

            public void setCommlist(List<?> commlist) {
                this.commlist = commlist;
            }

            public static class PraiselistBean {
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
        }
    }
}
