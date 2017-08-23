package com.weiaibenpao.demo.chislim.util;

/**
 * Created by lenovo on 2016/12/1.
 */

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class MySQLHelper extends SQLiteOpenHelper {
    private static final String DBNAME = "jianbaopp" ;
    private static final int VERSION = 1;

    //创建轨迹坐标表
    private static final String CREATE_HIS_TABLE = "create table jianbaoline\n" +
            "(\n" +
            "   id                   integer primary key autoincrement,\n" +
            "   latitude             float(10),\n" +
            "   longitude            float(10),\n" +
            "   sporttime            char(10),\n" +                       //运动时间
            "   sportdis             char(10),\n" +                       //运动距离
            "   sportcor             char(10),\n" +                       //消耗卡路里
            "   sportstep            char(10),\n" +                       //运动步数
            "   sportspeed           char(10),\n" +                       //速度
            "   speedspace           char(10),\n" +                       //配速
            "   stepfrequency        char(10),\n" +                       //步频
            "   stepscope            char(10),\n" +                       //步幅
            "   elevation            char(10),\n" +                       //海拔
            "   tab                  char(10));";

    public MySQLHelper(Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_HIS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
