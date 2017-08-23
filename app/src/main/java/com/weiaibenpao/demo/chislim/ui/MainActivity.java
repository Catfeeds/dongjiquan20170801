package com.weiaibenpao.demo.chislim.ui;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.weiaibenpao.demo.chislim.Interface.OnGetPro;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.bean.UserBean;
import com.weiaibenpao.demo.chislim.hurui.activity.HBaseActivity;
import com.weiaibenpao.demo.chislim.update.UpdateManager;
import com.weiaibenpao.demo.chislim.util.Default;
import com.weiaibenpao.demo.chislim.util.StackManager;
import com.weiaibenpao.demo.chislim.util.WriteReadSharedPrefs;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Locale;

import static com.weiaibenpao.demo.chislim.util.Default.READ_EXTERNAL_STORAGE_CODE;

public class MainActivity extends HBaseActivity implements View.OnClickListener,OnGetPro {
    //推送会用到
    public static boolean isForeground = false;
    //推送会用到

    StackManager manager = new StackManager();
    Context context;

    public static final String PREFS_NAME = "UserInfo";
    //登录用户
    UserBean user;

    MyBroadcast myBroadcast;  //广播对象
    boolean flag = false;

    android.support.v7.app.AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        CheckPerMission();
        StatusBarUtil.setColor(this , getResources().getColor(R.color.ku_bg ) , 0);
        user = UserBean.getUserBean();
        initView();


        //判断网络是否连接
        ConnectivityManager con=(ConnectivityManager)getSystemService(Activity.CONNECTIVITY_SERVICE);
        boolean wifi=con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        boolean internet=con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        if(wifi|internet){
            //执行相关操作
            Log.i("网络","网络已连接");
        }else{
            Toast.makeText(getApplicationContext(),
                    "亲，网络连了么？", Toast.LENGTH_LONG).show();
        }

        new UpdateManager(MainActivity.this).checkUpdate();     //监测更新

        manager.pushActivity(MainActivity.this);

        //跑步监听广播
        registerSportReceiver();
//        Log.e("MainActivity","sHA1: "+sHA1(this));
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        /*if(SharedPrefsUtil.getValue(context,PREFS_NAME,"content",false)){
        //if(!settings.getBoolean("content", false)){
            finish();
        }*/
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    FragmentTabHost tabHost;
    private void initView(){
        //读取用户信息到单例中
        WriteReadSharedPrefs.readUser(context,user);

        tabHost= (FragmentTabHost) findViewById(android.R.id.tabhost);
        tabHost.setup(this,getSupportFragmentManager(),R.id.content);
        initTabs();
    }

    private void initTabs(){
        MainTab[] tabs = MainTab.values();
        final int size = tabs.length;

        for (int i=0;i<size ;i++){
            MainTab mainTab=tabs[i];
            TabHost.TabSpec tab=tabHost.newTabSpec(getString(mainTab.getResName()));
            View indication= LayoutInflater.from(context).inflate(R.layout.tab_indication,null);
            TextView textView= (TextView) indication.findViewById(R.id.tab_title);
            textView.setText(mainTab.getResName());
         //   textView.setTextColor(getResources().getColor(R.color.blue_bg));
            Drawable drawable= ContextCompat.getDrawable(this,mainTab.getResIcon());
            textView.setCompoundDrawablesWithIntrinsicBounds(null, drawable,null,null);
            tab.setIndicator(indication);
            tabHost.addTab(tab,mainTab.getClz(),null);
            tabHost.getTabWidget().setDividerDrawable(null);//去掉竖直分割线
        }
    }

    @Override
    public void onClick(View view) {

    }


    @Override
    public void getPro(int pro) {

    }



    /**
     * 分享回调
     */
    @Override
    public void shareSport(String text,String title,String url,String media) {
        /*目前支持文本、图片（本地及URL）、音频及视频URL的分享,要分享图片的URL*/
        UMImage image = new UMImage(context, media);
        final SHARE_MEDIA[] displaylist = new SHARE_MEDIA[]
                {
                        SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.SINA,
                        SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE
                };
        new ShareAction(this).setDisplayList( displaylist )
                .withText( text )
                .withTitle(title)
                .withTargetUrl(url)
                .withMedia(image)
                .setListenerList(umShareListener)
                .open();
    }

    /**
     *  分享回调
     */
    private UMShareListener umShareListener = new UMShareListener(){
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.i("分享","-------------成功-----------");
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            //Toast.makeText(context,platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Log.i("分享","-------------取消-----------");
        }
    };

    /**
     * 分享面板添加按钮的回调
     */
    private ShareBoardlistener shareBoardlistener = new  ShareBoardlistener() {

        @Override
        public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
            if (share_media==null){
                if (snsPlatform.mKeyword.equals("11")){
                    Log.i("分享面板添加按钮的回调","-------------add button success-----------");
                }
            }
            else {
                new ShareAction(MainActivity.this).setPlatform(share_media).setCallback(umShareListener)
                        .withText("多平台分享")
                        .share();
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get( context ).onActivityResult( requestCode, resultCode, data);
    }




    @Override
    protected void onPause() {
        isForeground = false;
        super.onPause();
    }



    private void setCostomMsg(String msg){
       /* if (null != msgText) {
            msgText.setText(msg);
            msgText.setVisibility(android.view.View.VISIBLE);
        }*/
        Log.i("版本",android.view.View.VISIBLE+"");
    }


    public void registerSportReceiver(){
        //配置广播，接收当前是否有跑步正在进行中
        myBroadcast = new MyBroadcast();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.lijianbao.mapLocationLatLong");
        MainActivity.this.registerReceiver(myBroadcast, filter);
    }

    /**
     * 内部类实现进度条和界面动态显示
     */
    public class MyBroadcast extends BroadcastReceiver {

        public MyBroadcast() {

        }

        /**
         * @param context
         * @param intent  intent1.putExtra("state",num);
         */
        @Override
        public void onReceive(Context context, Intent intent) {

            String cor = intent.getStringExtra("contextActivity");        //当前正在跑跑步的activity
            int state = intent.getIntExtra("state", 4);
            Log.i("当前状态",state+"------" + cor);

            //判断当前activity是否在前台
            if(isForeground(MainActivity.this,"com.weiaibenpao.demo.chislim.ui.MainActivity")){
                //如果广播的跑步界面地址不为空，切跑步状态不为4，还没有跳转，则提示跳转

                if((cor != null && state != 4 ) || (cor != null && state != 0)){
                    if(!flag){
                        Log.i("当前状态",state+"------" + cor + "---  已经弹出来了");
                        flag = true;
                        showDialog(cor);
                        builder.show();
                        Log.e("MainActivity","onReceive；Main处于前台，并弹框提示");
                    }else {
                        Log.e("MainActivity","onReceive；Main处于前台，弹框已弹");
                    }
                }else {
                    Log.e("MainActivity","onReceive；Main处于前台，不用弹框提示");
                }
            }else {
//                Log.e("MainActivity","onReceive；Main非前台");
            }

        }
    }

    /*
    判断某个Activity是否在前台
     */
    private boolean isForeground(Context context, String className) {
        if (context == null || TextUtils.isEmpty(className)) {
            return false;
        }

        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (className.equals(cpn.getClassName())) {
                return true;
            }
        }

        return false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        /*unregisterReceiver(mMessageReceiver);
        unregisterReceiver(myBroadcast);
        mHandler.removeCallbacksAndMessages(null);*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(myBroadcast);
    }

    /**
     * 这是兼容的 AlertDialog
     */
    private void showDialog(final String str) {
      /*
      这里使用了 android.support.v7.app.AlertDialog.Builder
      可以直接在头部写 import android.support.v7.app.AlertDialog
      那么下面就可以写成 AlertDialog.Builder
      */
        builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle("跑步提示");
        builder.setMessage("当前有跑步还在进行中，是否前往！");
        builder.setNegativeButton("不了", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setPositiveButton("前往", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent1 = new Intent();
                intent1.setAction(str);//设置intent的Action属性值
                intent1.addCategory(Intent.CATEGORY_DEFAULT);//不加这行也行，因为这个值默认就是Intent.CATEGORY_DEFAULT
                startActivity( intent1 );
            }
        });
    }










    /**
     * 权限检查
     * @param
     */
    public void CheckPerMission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, Default.PERMISSIONS_STORAGE,
                    READ_EXTERNAL_STORAGE_CODE);
        }
    }

    /**
     * 权限申请的回调
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doNext(requestCode,grantResults);
    }

    /**
     * 权限的处理
     * @param requestCode
     * @param grantResults
     */
    private void doNext(int requestCode, int[] grantResults) {
        if (requestCode == READ_EXTERNAL_STORAGE_CODE) {
            
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted     同意

            } else {
                // Permission Denied       拒绝
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("权限申请")
                        .setMessage("在设置-应用-权限中开始读写权限，以保证功能的正常使用")
                        .setPositiveButton("去开启", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", getPackageName(), null);
                                intent.setData(uri);
                                startActivity(intent);

                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("取消", null)
                        .setCancelable(false)
                        .show();
            }
        }
    }

    public static String sHA1(Context context) {
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), PackageManager.GET_SIGNATURES);
            byte[] cert = info.signatures[0].toByteArray();
            MessageDigest md = MessageDigest.getInstance("SHA1");
            byte[] publicKey = md.digest(cert);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < publicKey.length; i++) {
                String appendString = Integer.toHexString(0xFF & publicKey[i])
                        .toUpperCase(Locale.US);
                if (appendString.length() == 1)
                    hexString.append("0");
                hexString.append(appendString);
            }
            return hexString.toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
