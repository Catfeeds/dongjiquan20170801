package com.weiaibenpao.demo.chislim.hurui.utils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.util.Log;

/**
 * Created by lenovo on 2017/4/11.
 */

public class AppNetUtil {
    public static boolean isHaveNet(Context context){
        ConnectivityManager con=(ConnectivityManager)context.getSystemService(Activity.CONNECTIVITY_SERVICE);
        boolean wifi=con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        boolean internet=con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        if(wifi|internet){
            //执行相关操作
            Log.i("网络","网络已连接");
            return true ;
        }else{
            return false ;
        }
    };
}
