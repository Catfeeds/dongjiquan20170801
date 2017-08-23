package com.weiaibenpao.demo.chislim.bean;

import java.util.List;

/**
 * Created by lenovo on 2016/8/15.
 */

public class UserResult {


    /**
     * error : 0
     * user : [{"userId":1,"userName":"李建宝","userImage":"http://112.74.28.179:8080/Weiaibenpao/Image/20160818197303.PNG","userPass":"555555","userWeight":37,"userHeight":26,"bmi":50,"project":15000,"userPhone":"13634146976","userEmail":"15566","userSex":"女","userBirth":"2016.8.7","userIntru":"李建宝李建宝11111","startTime":null,"stopNumber":0,"userDel":0,"channelId":"7987998798","qq":null,"wxin":null,"wbo":null,"openId":null,"hobby":"足球，拉拉队","userMark":444}]
     */

    private int error;
    /**
     * userId : 1
     * userName : 李建宝
     * userImage : http://112.74.28.179:8080/Weiaibenpao/Image/20160818197303.PNG
     * userPass : 555555
     * userWeight : 37.0
     * userHeight : 26
     * bmi : 50
     * project : 15000
     * userPhone : 13634146976
     * userEmail : 15566
     * userSex : 女
     * userBirth : 2016.8.7
     * userIntru : 李建宝李建宝11111
     * startTime : null
     * stopNumber : 0
     * userDel : 0
     * channelId : 7987998798
     * qq : null
     * wxin : null
     * wbo : null
     * openId : null
     * hobby : 足球，拉拉队
     * userMark : 444
     */

    private List<UserBean> user;

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }

    public List<UserBean> getUser() {
        return user;
    }

    public void setUser(List<UserBean> user) {
        this.user = user;
    }

    public static class UserBean {
        private int userId;
        private String userName;
        private String userImage;
        private String userPass;
        private double userWeight;
        private int userHeigh;
        private int bmi;
        private int project;
        private String userPhone;
        private String userEmail;
        private String userSex;
        private String userBirth;
        private String userIntru;
        private Object startTime;
        private int stopNumber;
        private int userDel;
        private String channelId;
        private Object qq;
        private Object wxin;
        private Object wbo;
        private Object openId;
        private String hobby;
        private int userMark;

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserImage() {
            return userImage;
        }

        public void setUserImage(String userImage) {
            this.userImage = userImage;
        }

        public String getUserPass() {
            return userPass;
        }

        public void setUserPass(String userPass) {
            this.userPass = userPass;
        }

        public double getUserWeight() {
            return userWeight;
        }

        public void setUserWeight(double userWeight) {
            this.userWeight = userWeight;
        }

        public int getUserHeigh() {
            return userHeigh;
        }

        public void setUserHeigh(int userHeigh) {
            this.userHeigh = userHeigh;
        }

        public int getBmi() {
            return bmi;
        }

        public void setBmi(int bmi) {
            this.bmi = bmi;
        }

        public int getProject() {
            return project;
        }

        public void setProject(int project) {
            this.project = project;
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

        public Object getStartTime() {
            return startTime;
        }

        public void setStartTime(Object startTime) {
            this.startTime = startTime;
        }

        public int getStopNumber() {
            return stopNumber;
        }

        public void setStopNumber(int stopNumber) {
            this.stopNumber = stopNumber;
        }

        public int getUserDel() {
            return userDel;
        }

        public void setUserDel(int userDel) {
            this.userDel = userDel;
        }

        public String getChannelId() {
            return channelId;
        }

        public void setChannelId(String channelId) {
            this.channelId = channelId;
        }

        public Object getQq() {
            return qq;
        }

        public void setQq(Object qq) {
            this.qq = qq;
        }

        public Object getWxin() {
            return wxin;
        }

        public void setWxin(Object wxin) {
            this.wxin = wxin;
        }

        public Object getWbo() {
            return wbo;
        }

        public void setWbo(Object wbo) {
            this.wbo = wbo;
        }

        public Object getOpenId() {
            return openId;
        }

        public void setOpenId(Object openId) {
            this.openId = openId;
        }

        public String getHobby() {
            return hobby;
        }

        public void setHobby(String hobby) {
            this.hobby = hobby;
        }

        public int getUserMark() {
            return userMark;
        }

        public void setUserMark(int userMark) {
            this.userMark = userMark;
        }
    }
}
