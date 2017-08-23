package com.weiaibenpao.demo.chislim.util;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.weiaibenpao.demo.chislim.bean.LatLongRemberBean;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by lenovo on 2016/12/1.
 */

public class LatLong_impl implements latlong_Dao {

    private MySQLHelper mySQLHelper ;

    public LatLong_impl(){
    }

    public LatLong_impl(Context context){
        mySQLHelper = new MySQLHelper(context);
    }

    /**
     * 获取系统当前时间
     * @return
     */
    public String getTime( ){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        return formatter.format(curDate);
    }

    /**
     * 制作标记
     */
    public String getTab( ){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        return formatter.format(curDate);
    }

    /**
     * 制作标记
     */
    public int getPosition( ){
        SimpleDateFormat formatter = new SimpleDateFormat("MMdd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        return Integer.parseInt(formatter.format(curDate));
    }

    /**
     * 制作标记
     */
    public String getDate( ){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        return formatter.format(curDate);
    }


    /**
     *
     * @param latitude   经度
     * @param longitude   纬度
     * @param sporttime   运动时间
     * @param sportdis    运动距离
     * @param sportcor    消耗卡路里
     * @param sportstep    走动的步数
     * @param sportspeed   运动速度
     */
    @Override
    public void addLatLong(double latitude,double longitude,String sporttime,String sportdis,String sportcor,String sportstep,String sportspeed,String speedspace,String stepfrequency,String stepscope,String elevation) {
        SQLiteDatabase db = mySQLHelper.getWritableDatabase();
        db.execSQL("insert into jianbaoline(latitude,longitude,sporttime,sportdis,sportcor,sportstep,sportspeed,speedspace,stepfrequency,stepscope,elevation,tab) values(?,?,?,?,?,?,?,?,?,?,?,?)",
                new Object[]{latitude,longitude,sporttime,sportdis,sportcor,sportstep,sportspeed,speedspace,stepfrequency,stepscope,elevation,getTab()});
        db.close();
    }

    /**
     * 查询数据
     */
    @Override
    public ArrayList<LatLongRemberBean> getLatLong(String tab) {
        double latitude = 0;
        double longitude = 0;
        String sporttime;
        String sportdis;
        String sportcor;
        String sportstep;
        String sportspeed;
        String speedspace;
        String stepfrequency;
        String stepscope;
        String elevation;
        ArrayList arrayList = new ArrayList();
        LatLongRemberBean longRemberBean;

        SQLiteDatabase db = mySQLHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from jianbaoline where tab = ? order by id ASC",new String[]{tab});
        while (cursor.moveToNext()){

            latitude = cursor.getDouble(cursor.getColumnIndex("latitude"));
            longitude = cursor.getDouble(cursor.getColumnIndex("longitude"));
            sporttime = cursor.getString(cursor.getColumnIndex("sporttime"));
            sportdis = cursor.getString(cursor.getColumnIndex("sportdis"));
            sportcor = cursor.getString(cursor.getColumnIndex("sportcor"));
            sportstep = cursor.getString(cursor.getColumnIndex("sportstep"));
            sportspeed = cursor.getString(cursor.getColumnIndex("sportspeed"));

            speedspace = cursor.getString(cursor.getColumnIndex("speedspace"));
            stepfrequency = cursor.getString(cursor.getColumnIndex("stepfrequency"));
            stepscope = cursor.getString(cursor.getColumnIndex("stepscope"));
            elevation = cursor.getString(cursor.getColumnIndex("elevation"));
            longRemberBean = new LatLongRemberBean(latitude,longitude,sporttime,sportdis,sportcor,sportstep,sportspeed,speedspace,stepfrequency,stepscope,elevation);

            arrayList.add(longRemberBean);
        }
        cursor.close();
        db.close();
        return arrayList;
    }
}
