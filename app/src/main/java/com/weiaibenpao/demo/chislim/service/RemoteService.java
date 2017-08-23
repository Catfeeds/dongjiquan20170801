package com.weiaibenpao.demo.chislim.service;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;

import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.util.ACache;
import com.weiaibenpao.demo.chislim.util.PowerManagerUtil;
import com.weiaibenpao.demo.chislim.util.UtilService;

public class RemoteService extends Service {
    private String TAG = getClass().getName();
    private String Process_Name = "com.weiaibenpao.demo.chislim.service.Map_Service";
    Context context = this;
    ACache aCache;
    PowerManagerUtil powerManagerUtil;
    String contextActivity;
    //设置为前台服务
    Intent intentActivity;
    public RemoteService() {
    }

    @Override
    public void onCreate() {
        aCache = ACache.get(context);
        Log.i("服务",aCache.getAsString("isService"));
        if(aCache.getAsString("isService").equals("true")){
            keepService1();
        }

        /*//创建Alarm并启动
        Intent intent = new Intent("LOCATION_CLOCK");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
        // 每五秒唤醒一次
        long second = 10 * 1000;
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), second, pendingIntent);*/

        setService();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return (IBinder) startS1;
    }

    @SuppressLint("WrongConstant")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i("服务","保护进程被启动");

        try{
            contextActivity = intent.getStringExtra("activityContext");
            intentActivity = new Intent(contextActivity);
        }catch (Exception e){
            aCache.put("isService","false");              //双进程监听
            this.stopService1();
        }

        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intentActivity, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(context)
                .setAutoCancel(true)
                .setContentTitle("东极圈")                               //标题
                .setContentText("跑步正在进行中")                       //内容
                .setContentIntent(contentIntent)                          //点击跳转
                .setSmallIcon(R.mipmap.ndjq)                             //图标
                .setWhen(System.currentTimeMillis())
                .build();
        startForeground(2, notification);


        keepService1();
        return START_STICKY;
    }


    public void setService() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (-1!=0){
                    Message message = new Message();
                    boolean isRun = UtilService.isServiceRunning(RemoteService.this, Process_Name);
                    Log.i("服务",isRun + " --- 主进程的状态");
                    //如果主线程停止，并且没有收到停止命令
                    if(!isRun && aCache.getAsString("isService").equals("true")){
                        message.arg1 = 0;
                    }else{
                        message.arg1 = 1;
                    }
                    handler.sendMessage(message);

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.arg1 == 0){
                keepService1();
            }else{
                stopService1();
            }
        }
    };

    /**
     * *启动Service1
     */
     private StrongService startS1 = new StrongService.Stub() {
        @Override
        public void stopService() throws RemoteException {
            Intent i = new Intent(getBaseContext(), Map_Service.class);
            getBaseContext().stopService(i);
        }

        @Override
        public void startService() throws RemoteException {
            Intent i = new Intent(getBaseContext(), Map_Service.class);
            getBaseContext().startService(i);
        }
    };


    @Override
    public void onTrimMemory(int level){
        //Toast.makeText(getBaseContext(), "Service2 onTrimMemory..."+level, Toast.LENGTH_SHORT).show();
        if(aCache == null){
            aCache = ACache.get(context);
        }
        if(aCache.getAsString("isService").equals("true")){
            keepService1();
            //commented by zjl
//            if(powerManagerUtil == null){
//                powerManagerUtil = new PowerManagerUtil();
//            }
//            //如果屏幕不亮 ，点亮屏幕
//            if(!powerManagerUtil.isScreenOn(context)){
//                powerManagerUtil.wakeUpScreen(context);
//            }
        }else{
            stopService1();
        }
    }

    /**
     * 判断Service1是否还在运行，如果不是则启动Service1
     */
    private  void keepService1(){
        boolean isRun = UtilService.isServiceRunning(RemoteService.this, Process_Name);
        Log.i("服务",isRun + " --- 主进程的状态keepService1");
        if (isRun == false) {
            try {
                //Toast.makeText(getBaseContext(), "重新启动 Service1主进程", Toast.LENGTH_SHORT).show();
                startS1.startService();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 判断Service1是否还在运行，如果不是则关闭Service1
     */
    private  void stopService1(){
        boolean isRun = UtilService.isServiceRunning(RemoteService.this, Process_Name);
        if (isRun == false) {
            try {
                //Toast.makeText(getBaseContext(), "重新启动 Service1", Toast.LENGTH_SHORT).show();
                startS1.stopService();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
