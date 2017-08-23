package com.weiaibenpao.demo.chislim.ui;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Window;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jaeger.library.StatusBarUtil;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.bean.UserBean;
import com.weiaibenpao.demo.chislim.util.SharedPrefsUtil;
import com.weiaibenpao.demo.chislim.util.WriteReadSharedPrefs;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.weiaibenpao.demo.chislim.util.Default.READ_EXTERNAL_STORAGE_CODE;

public class LeadActivity extends Activity {

    public static final String PREFS_NAME = "UserInfo";
    @BindView(R.id.mySport)
    ImageView mySport;
    private SharedPreferences settings;
    //用户信息
    UserBean user;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        /*if ((getWindow().getDecorView().getSystemUiVisibility()
                & View.SYSTEM_UI_FLAG_FULLSCREEN) != 0) {
            getWindow().getDecorView().setSystemUiVisibility(0);
        } else {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_FULLSCREEN);
        }*/
        setContentView(R.layout.activity_lead);
        StatusBarUtil.setColor(this , getResources().getColor(R.color.ku_bg ) , 0);
        ButterKnife.bind(this);
        CheckPerMission();
        settings = getSharedPreferences(PREFS_NAME, 0);
        context = getApplicationContext();
        Handler x = new Handler();//定义一个handle对象

        x.postDelayed(new splashhandler(), 2000);//设置3秒钟延迟执行splashhandler线程。其实你这里可以再新建一个线程去执行初始化工作，如判断SD,网络状态等

        user = UserBean.getUserBean();

        //判断网络是否连接
        ConnectivityManager con = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        boolean wifi = con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
        boolean internet = con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();
        if (wifi | internet) {
            //执行相关操作
            Log.i("网络", "网络已连接");
        } else {
            Toast.makeText(getApplicationContext(),
                    "亲，网络连了么？", Toast.LENGTH_LONG)
                    .show();
        }

        Glide.with(context)
                .load(R.mipmap.lock_screen)
                .placeholder(R.mipmap.zhanwei)
                .error(R.mipmap.zhanwei)
                .crossFade()
                // .skipMemoryCache(true)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(mySport);
    }


    class splashhandler implements Runnable {
        public void run() {
            getUser();
            /*startActivity(new Intent(getApplication(),MainActivity.class));// 这个线程的作用3秒后就是进入到你的主界面
            LeadActivity.this.finish();// 把当前的LaunchActivity结束掉*/
        }
    }


    public void getUser() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                //如果文件中有用户数据保存，直接进入首页,为true则为用用户信息，false时为没有用户，跳转至登录界面
                if(SharedPrefsUtil.getValue(context,PREFS_NAME,"content",false)){
                    WriteReadSharedPrefs.readUser(context,user);
                //if (settings.getBoolean("content", false)) {
                    //从SharedPreferences拿到数据，第一个参数为键，第二个为默认替换值
                    /*user.userId = settings.getInt("userId", 0);
                    user.userName = settings.getString("userName", "未登录");
                    user.userPhoone = settings.getString("userPhone", "");
                    user.userBirth = settings.getString("userBirth", "");
                    user.userSex = settings.getString("userSex", "");
                    user.userEmail = settings.getString("userEmail", "");
                    user.userImage = settings.getString("userImage", "");
                    user.userTntru = settings.getString("userIntru", "");
                    user.userBmi = settings.getInt("userBmi", 0);
                    user.userHeight = settings.getInt("userHeight", 0);
                    user.userWeight = settings.getFloat("userWeight", 0);
                    user.userProject = settings.getInt("userProject", 0);
                    user.userMark = settings.getInt("userMark", 0);
                    user.userHobby = settings.getString("userHobby", "");*/

                    Message msg = new Message();

                    msg.arg1 = 1;

                    handler.sendMessage(msg);

                } else {
                    Message msg = new Message();

                    msg.arg1 = 0;

                    handler.sendMessage(msg);

                }
            }
        }).start();
    }

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            Intent intent;
            switch (msg.arg1) {
                case 1:                  //跳转至首页
                    intent = new Intent(LeadActivity.this, MainActivity.class);
                    startActivity(intent);
                    LeadActivity.this.finish();
                    break;
                case 0:                 //跳转至登录界面
                    intent = new Intent(LeadActivity.this, LoginActivity.class);
                    startActivity(intent);
                    LeadActivity.this.finish();
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    /**
     * 6.0的权限监测
     */
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,};

    /**
     * 权限检查
     * @param
     */
    public void CheckPerMission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this,PERMISSIONS_STORAGE,
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
                new AlertDialog.Builder(LeadActivity.this)
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
}
