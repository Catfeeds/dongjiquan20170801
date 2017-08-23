package com.weiaibenpao.demo.chislim.util;

import android.content.Context;
import android.util.Log;

import com.weiaibenpao.demo.chislim.bean.Li_UserBeanResult;
import com.weiaibenpao.demo.chislim.bean.UserBean;

/**
 * Created by lenovo on 2017/4/19.
 */

public class WriteReadSharedPrefs {
    public static final String PREFS_NAME = "MyUserInfo";
    public static final String TOTAL_DISTANCE ="total_distance";
    public WriteReadSharedPrefs() {
    }

    /**
     * 将用户信息写入文件
     */
    public static void writeUser(Context context,Li_UserBeanResult.DataBean user){
        /**
         * 保存数据
         */
        //获得SharedPreferences.Editor对象，使SharedPreferences对象变为可编辑状态（生成编辑器）
        //SharedPreferences.Editor edit = settings.edit();
        //清除文件内容
        // edit.clear();

        //将数据设置给SharedPreferences.Editor对象
        /**
         * 在登录界面保存数据到文件中，在注册完成后的信息完善界面中也有信息保存
         * 在mainActivity中有数据的获取，若添加保存数据，这三者要同步，不然会出现异常
         */

        SharedPrefsUtil.putValue(context,PREFS_NAME,"content",true);
        SharedPrefsUtil.putValue(context,PREFS_NAME,"isFirst",1);
        SharedPrefsUtil.putValue(context,PREFS_NAME,"userPhone",user.getUserPhone());
        SharedPrefsUtil.putValue(context,PREFS_NAME,"userName",user.getUserName());
        SharedPrefsUtil.putValue(context,PREFS_NAME,"userId",user.getId());
        SharedPrefsUtil.putValue(context,PREFS_NAME,"userBirth",user.getUserBirth());
        SharedPrefsUtil.putValue(context,PREFS_NAME,"userImage",user.getUserImage());
        SharedPrefsUtil.putValue(context,PREFS_NAME,"userIntru",user.getUserIntru());
        SharedPrefsUtil.putValue(context,PREFS_NAME,"userSex",user.getUserSex());
        SharedPrefsUtil.putValue(context,PREFS_NAME,"userEmail",user.getUserEmail());
        SharedPrefsUtil.putValue(context,PREFS_NAME,"userWeight",(float) user.getUserWeight());
        SharedPrefsUtil.putValue(context,PREFS_NAME,"userHeight",user.getUserHeigh());
        SharedPrefsUtil.putValue(context,PREFS_NAME,"userHobby",user.getHobby());
        SharedPrefsUtil.putValue(context,PREFS_NAME,"userMark",user.getMark());
        SharedPrefsUtil.putValue(context,PREFS_NAME,"userBmi",user.getBMI());
        Log.e("wlx233", "writeUser: "+user.getUserName() );

    }

    /**
     * 将用户信息读入app
     */
    public static void readUser(Context context,UserBean userBean){
        /**
         * 保存数据
         */
        //获得SharedPreferences.Editor对象，使SharedPreferences对象变为可编辑状态（生成编辑器）
        //SharedPreferences.Editor edit = settings.edit();
        //清除文件内容
        // edit.clear();

        //将数据设置给SharedPreferences.Editor对象
        /**
         * 在登录界面保存数据到文件中，在注册完成后的信息完善界面中也有信息保存
         * 在mainActivity中有数据的获取，若添加保存数据，这三者要同步，不然会出现异常
         */

        SharedPrefsUtil.getValue(context,PREFS_NAME,"isFirst",1);
        userBean.userPhoone = SharedPrefsUtil.getValue(context,PREFS_NAME,"userPhone","");
        userBean.userName = SharedPrefsUtil.getValue(context,PREFS_NAME,"userName","");
        userBean.userId = SharedPrefsUtil.getValue(context,PREFS_NAME,"userId",0);
        userBean.userBirth = SharedPrefsUtil.getValue(context,PREFS_NAME,"userBirth","");
        userBean.userImage = SharedPrefsUtil.getValue(context,PREFS_NAME,"userImage","");
        userBean.userTntru = SharedPrefsUtil.getValue(context,PREFS_NAME,"userIntru","");
        userBean.userSex = SharedPrefsUtil.getValue(context,PREFS_NAME,"userSex","");
        userBean.userEmail = SharedPrefsUtil.getValue(context,PREFS_NAME,"userEmail","");
        userBean.userWeight = SharedPrefsUtil.getValue(context,PREFS_NAME,"userWeight",(float) 0);
        userBean.userHobby = SharedPrefsUtil.getValue(context,PREFS_NAME,"userHobby","");
        userBean.userMark = SharedPrefsUtil.getValue(context,PREFS_NAME,"userMark",0);
        userBean.userBmi = SharedPrefsUtil.getValue(context,PREFS_NAME,"userBmi",0);
        userBean.userHeight = SharedPrefsUtil.getValue(context,PREFS_NAME,"userHeight",0);
    }
}
