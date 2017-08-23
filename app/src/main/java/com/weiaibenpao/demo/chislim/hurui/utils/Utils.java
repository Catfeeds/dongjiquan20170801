package com.weiaibenpao.demo.chislim.hurui.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import com.weiaibenpao.demo.chislim.hurui.activity.HWebViewActivity;

/**
 * Created by lenovo on 2017/4/14.
 */

public class Utils {

    public static void gotoTmailShop(Context context , String id , String urlpath){
        Intent intent = new Intent(context , HWebViewActivity.class);
        intent.putExtra("urlpath" ,
                urlpath);
        context.startActivity(intent);
//        if (checkPackage(context, "com.tmall.wireless")) {
//            Intent intent = new Intent();
//            intent.setAction("android.intent.action.VIEW");
////            https://detail.tmall.com/item.htm?id=535823983028&spm=a21bo.7932663.item.2.0mvUrk&scm=1007.13596.65361.100200300000014
//            //spm=a1z10.1-b-s.1997427721.d4918089.4FdnMc
//            String url = "tmall://tmallclient/?{\"action\":”item:id="+id+"”} ";
//            Uri uri = Uri.parse(url);
//            intent.setData(uri);
//            context.startActivity(intent);
//        } else {
//            Toast.makeText(context, "没有天猫客户端", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(context , HWebViewActivity.class);
//            intent.putExtra("urlpath" ,
//                    urlpath);
//            context.startActivity(intent);
//            // WebViewActivity.open(MainActivity.this, "https://shop131259851.taobao.com/?spm=a230r.7195193.1997079397.8.Pp3ZMM");
//        }
    }

    public static boolean checkPackage(Context context , String packageName)
    {
        if (packageName == null || "".equals(packageName))
            return false;
        try{
            context.getPackageManager().getApplicationInfo(packageName, PackageManager.GET_UNINSTALLED_PACKAGES);
            return true;
        }catch (PackageManager.NameNotFoundException e){
            return false;
        }

    }

}
