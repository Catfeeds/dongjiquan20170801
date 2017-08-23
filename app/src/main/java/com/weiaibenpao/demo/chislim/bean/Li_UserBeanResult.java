package com.weiaibenpao.demo.chislim.bean;

/**
 * Created by lenovo on 2017/4/15.
 */

public class Li_UserBeanResult {


    /**
     * code : 0
     * msg : 成功
     * data : {"id":15,"userImage":"http://img3.duitang.com/uploads/item/201602/12/20160212214325_iFSaZ.thumb.224_0.jpeg","userName":"","userPass":"123456","userHeight":0,"userWeight":0,"BMI":0,"userPhone":"15527223470","userEmail":"","userSex":"","userBirth":"","userIntru":"","channelId":"","registerSource":"phone","openId":"","hobby":"","mark":0}
     */

    private int code;
    private String msg;
    /**
     * id : 15
     * userImage : http://img3.duitang.com/uploads/item/201602/12/20160212214325_iFSaZ.thumb.224_0.jpeg
     * userName :
     * userPass : 123456
     * userHeight : 0
     * userWeight : 0.0
     * BMI : 0
     * userPhone : 15527223470
     * userEmail :
     * userSex :
     * userBirth :
     * userIntru :
     * channelId :
     * registerSource : phone
     * openId :
     * hobby :
     * mark : 0
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
        private int id;
        private String userImage;
        private String userName;
        private String userPass;
        private int userHeigh;
        private double userWeight;
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
            return "DataBean{" +
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

        public double getUserWeight() {
            return userWeight;
        }

        public void setUserWeight(double userWeight) {
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
}
