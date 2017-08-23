package com.weiaibenpao.demo.chislim.sportoutdoor.presentation.module;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.multidex.MultiDex;
import android.util.Log;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.iflytek.cloud.SpeechConstant;
import com.umeng.socialize.PlatformConfig;
import com.weiaibenpao.demo.chislim.BuildConfig;
import com.weiaibenpao.demo.chislim.service.Map_Service;
import com.weiaibenpao.demo.chislim.settings.TtsSettings;
import com.weiaibenpao.demo.chislim.sportoutdoor.presentation.common.BaseApplication;
import com.weiaibenpao.demo.chislim.util.VoicePlayerUtil;

public class PedometerApplication extends BaseApplication {

    private static final String TAG = "JPush";
    public PedometerApplication() {
        super();
    }
    public static PedometerApplication app;
    private static VoicePlayerUtil voicePlayerUtil;

    public static VoicePlayerUtil getVoicePlayerUtil(){
        return voicePlayerUtil;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        ApplicationModule.initSingleton().onCreateMainProcess();
        app=this;
        // 记得使用这个fresco要在Application中初始化啊！
        //Fresco.initialize(this);
        // the following line is important Fresco.initialize(getApplicationContext());
        Fresco.initialize(getApplicationContext());

        PlatformConfig.setWeixin("wx221705f86c71d1bf", "225c77b86158d2acf80eaef2c79b433f");
        //微信 appid appsecret

        PlatformConfig.setSinaWeibo("1959713611","e62e0d810039469a8852ba366afdb0bc");
        //新浪微博 appkey appsecret

        PlatformConfig.setQQZone("1105642894", "3cvNf8EoHe17VQ93");
        // QQ和Qzone appid appkey


        // 应用程序入口处调用,避免手机内存过小,杀死后台进程后通过历史intent进入Activity造成SpeechUtility对象为null
        // 注意：此接口在非主进程调用会返回null对象，如需在非主进程使用语音功能，请增加参数：SpeechConstant.FORCE_LOGIN+"=true"
        // 参数间使用“,”分隔。
        // 设置你申请的应用appid

        // 注意： appid 必须和下载的SDK保持一致，否则会出现10407错误

        StringBuffer param = new StringBuffer();
        param.append("appid=588020d9"  );
        param.append(",");
        // 设置使用v5+
        param.append(SpeechConstant.ENGINE_MODE+"="+SpeechConstant.MODE_MSC);
//        SpeechUtility.createUtility(getApplicationContext(), param.toString());


        //唤醒跑步服务
        //startAlarm();
        SharedPreferences sharedPreferences = getSharedPreferences(TtsSettings.PREFER_NAME, MODE_PRIVATE);
        voicePlayerUtil = new VoicePlayerUtil(this.getApplicationContext(),sharedPreferences);
        if(BuildConfig.DEBUG){
//            CrashHandler.getInstance().init(this);
        }
        Log.e("PedometerApplication","onCreate");
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(base);
    }

    public void startAlarm(){
        /**
         * 首先获得系统服务
         */
        AlarmManager am = (AlarmManager)
                getSystemService(Context.ALARM_SERVICE);

        /** 设置闹钟的意图，我这里是去调用一个服务，该服务功能就是获取位置并且上传*/
        Intent intent = new Intent(this, Map_Service.class);
        PendingIntent pendSender = PendingIntent.getService(this, 0, intent, 0);
        am.cancel(pendSender);

        /**AlarmManager.RTC_WAKEUP 这个参数表示系统会唤醒进程；我设置的间隔时间是10分钟 */
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 60*1000, pendSender);
    }



    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if(voicePlayerUtil != null) voicePlayerUtil.startVoice("内存不足 onLowMemory");
    }


    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Log.e("onTrimMemory","level is "+level);
        if(voicePlayerUtil != null && level >= 80) {
            voicePlayerUtil.startVoice(" onTrimMemory level is "+level);
        }
    }

    /**
     * This method is for use in emulated process environments.
     * It will never be called on a production Android device,
     * where processes are removed by simply killing them;
     */

    @Override
    public void onTerminate() {
        super.onTerminate();
        if(voicePlayerUtil != null) voicePlayerUtil.startVoice("程序被终止 onTerminate");
    }

//    @Override
//    public void registerActivityLifecycleCallbacks(ActivityLifecycleCallbacks callback) {
//        super.registerActivityLifecycleCallbacks(callback);
//    }


//    @Override
//    public void unregisterActivityLifecycleCallbacks(ActivityLifecycleCallbacks callback) {
//        super.unregisterActivityLifecycleCallbacks(callback);
//    }
//
//    @Override
//    public void registerComponentCallbacks(ComponentCallbacks callback) {
//        super.registerComponentCallbacks(callback);
//    }
//
//    @Override
//    public void unregisterComponentCallbacks(ComponentCallbacks callback) {
//        super.unregisterComponentCallbacks(callback);
//    }
}
