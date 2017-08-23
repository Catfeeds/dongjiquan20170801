package com.weiaibenpao.demo.chislim.bean;

/**
 * Created by lenovo on 2016/8/15.
 */

public class UserBean {

    public int userId;
    public String userName;
    public String userPhoone;
    public String userBirth;
    public String userImage;
    public String userTntru;

    public String userSex;
    public int userProject;
    public String userEmail;

    public float userWeight;
    public int userHeight;
    public int userBmi;

    public int userMark;
    public String userHobby;

    public String userOpenid;

    @Override
    public String toString() {
        return "UserBean{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userPhoone='" + userPhoone + '\'' +
                ", userBirth='" + userBirth + '\'' +
                ", userImage='" + userImage + '\'' +
                ", userTntru='" + userTntru + '\'' +
                ", userSex='" + userSex + '\'' +
                ", userProject=" + userProject +
                ", userEmail='" + userEmail + '\'' +
                ", userWeight=" + userWeight +
                ", userHeight=" + userHeight +
                ", userBmi=" + userBmi +
                ", userMark=" + userMark +
                ", userHobby='" + userHobby + '\'' +
                ", userOpenid='" + userOpenid + '\'' +
                '}';
    }

    private static UserBean userBean;
    private UserBean() {
    }

    public static UserBean getUserBean() {
        if (null == userBean) {
            userBean = new UserBean();
        }
        return userBean;
    }

}
