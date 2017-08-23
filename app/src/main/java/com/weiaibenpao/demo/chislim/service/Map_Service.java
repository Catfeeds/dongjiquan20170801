package com.weiaibenpao.demo.chislim.service;

import android.Manifest;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Parcelable;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.model.LatLng;
import com.squareup.leakcanary.RefWatcher;
import com.weiaibenpao.demo.chislim.BuildConfig;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.bean.UserBean;
import com.weiaibenpao.demo.chislim.settings.TtsSettings;
import com.weiaibenpao.demo.chislim.sportoutdoor.data.model.database.core.PedometerCardEntity;
import com.weiaibenpao.demo.chislim.sportoutdoor.presentation.common.BaseApplication;
import com.weiaibenpao.demo.chislim.sportoutdoor.presentation.common.utils.HardwarePedometerUtil;
import com.weiaibenpao.demo.chislim.sportoutdoor.presentation.module.ApplicationModule;
import com.weiaibenpao.demo.chislim.sportoutdoor.presentation.presenter.PedometerPresenter;
import com.weiaibenpao.demo.chislim.sportoutdoor.presentation.view.iview.IPedometerView;
import com.weiaibenpao.demo.chislim.util.ACache;
import com.weiaibenpao.demo.chislim.util.LatLong_impl;
import com.weiaibenpao.demo.chislim.util.UtilService;
import com.weiaibenpao.demo.chislim.util.VoicePlayerUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;


public class Map_Service extends Service implements LocationSource, AMapLocationListener, IPedometerView {

    private final String Process_Name = "com.weiaibenpao.demo.chislim.service.RemoteService";

    private static String TAG = "地图服务";
    private static final int LOCATION_TIME_INTERVAL = 1000;
    private static final int HAS_LA_LONG = 0;

    private final IBinder binder = new My_binder();

    //记录轨迹坐标点
    public static ArrayList<LatLng> mLocationList = new ArrayList<>();


    //记录整公里坐标点
    private ArrayList<LatLng> mTable = new ArrayList<>();

    private LocationSource.OnLocationChangedListener mLocationLinstener;
    private AMapLocationClient locationClient = null;
//    private AMapLocationClientOption locationOption = null;

    private double mLocatinLat;             //经度-------维度
    private double mLocationLon;            //纬度-------经度
    private double mLocationElevation;     //海拔

    private Intent intent1;
    private LatLng latLng1 = null;
    private LatLng latLng = null;
    private int num, n = 1, p = 0, model = 0;
    private float dis = 0;
    private float disOld = 0;

    private Context context = this;

    private LatLong_impl latLong_impl;
    private DecimalFormat decimalFormat;
    private int step = 0;       //行走的步数
    private long start;       //运动开始时间
    private long end;          //结束时间
    private long stopTime = 0;     //暂停结束时的时间
    private long paseTime = 0;     //暂停的时间

    private float s = 0;
    private float ss = 0;
    private float sss = 0;
    private float ssskm = 0;
    private int nowStep = 0;
    private int nowSteps = 0;
    private int stepEnd = 0;
    private String contextActivity;

    private int tab = 1;

    private VoicePlayerUtil voicePlayerUtil;
//    private SharedPreferences mSharedPreferences;

    private LocationManager locationManager;

    private boolean flags = false;

    private ACache aCache;

    public class My_binder extends Binder {
        public Map_Service getService() {
            return Map_Service.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if(BuildConfig.DEBUG) {
            Toast.makeText(this,"Map Service onCreate",Toast.LENGTH_SHORT).show();
            Log.e("MapService","onCreate state is "+num);
        }
        aCache = ACache.get(context);
        aCache.put("isService","true");                       //双进程监听

        intent1 = new Intent();
        if (latLong_impl == null) {
            latLong_impl = new LatLong_impl(context);          //实例化数据库操作
        }
        mLocationList.clear();                                   //清空以前的数据
        SharedPreferences sharedPreferences = getSharedPreferences(TtsSettings.PREFER_NAME, MODE_PRIVATE);
        voicePlayerUtil = new VoicePlayerUtil(this.getApplicationContext(), sharedPreferences);
        //GetGPSStatus();
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        locationManager.addGpsStatusListener(listener);

        keepService2();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        if (latLong_impl == null) {
            latLong_impl = new LatLong_impl(context);    //实例化数据库操作
        }
        init();
        return binder;
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(BuildConfig.DEBUG) Toast.makeText(this,"Map Service onStartCommand",Toast.LENGTH_SHORT).show();
        if (latLong_impl == null) {
            latLong_impl = new LatLong_impl(context);    //实例化数据库操作
        }
        initLocation();

        try{
            contextActivity = intent.getStringExtra("activityContext");
            aCache.put("contextActivity",contextActivity);              //点击跳转的界面
        }catch (Exception e){
            aCache.put("isService","false");              //双进程监听
            this.stopService2();
        }

        //设置为前台服务
        Intent intentActivity = new Intent(aCache.getAsString("contextActivity"));
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intentActivity, PendingIntent.FLAG_UPDATE_CURRENT);
        Notification notification = new Notification.Builder(context)
                .setAutoCancel(true)
                .setContentTitle("东极圈")                               //标题
                .setContentText("跑步正在进行中，您已经运动了"+dis+"公里")           //内容
                .setContentIntent(contentIntent)                          //点击跳转
                .setSmallIcon(R.mipmap.ndjq)                             //图标
                .setWhen(System.currentTimeMillis())
                .build();
        startForeground(1, notification);

//        return super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(locationManager != null) locationManager.removeGpsStatusListener(listener);
//        if(handler != null) handler.removeCallbacksAndMessages(null);

        if (num != 4) {
            Intent localIntent = new Intent();
            localIntent.setClass(this, Map_Service.class); //销毁时重新启动Service
            this.startService(localIntent);
        }

        if(BuildConfig.DEBUG){//如果检测 Fragment可以在BaseFragment里面写一遍即可--奈何该项目居然木有。。
            RefWatcher refWatcher = BaseApplication.getRefWatcher(getApplicationContext());
            refWatcher.watch(this);
        }
    }

    private void initLocation() {

        locationClient = new AMapLocationClient(getApplicationContext());
        AMapLocationClientOption locationOption = new AMapLocationClientOption();
        //关闭缓存机制
        //当开启定位缓存功能，在高精度模式和低功耗模式下进行的网络定位结果均会生成本地缓存，不区分单次定位还是连续定位。GPS定位结果不会被缓存。
        locationOption.setLocationCacheEnable(false);
        //设置是否强制刷新WIFI，默认为true，强制刷新。
        //每次定位主动刷新WIFI模块会提升WIFI定位精度，但相应的会多付出一些电量消耗。
        locationOption.setWifiActiveScan(false);
        // 设置定位模式为高精度模式
        //设置定位模式为Hight_Accuracy高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模
        //高精度模式:会同时使用网络定位和GPS定位，优先返回最高精度的定位结果，以及对应的地址描述信息。
        //低功耗定位模式：不会使用GPS和其他传感器，只会使用网络定位（Wi-Fi和基站定位）；
        //仅用设备定位模式：不需要连接网络，只使用GPS进行定位，这种模式下不支持室内环境的定位，自 v2.9.0 版本支持返回地址描述信息。
        locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        locationOption.setNeedAddress(false);
        //设置定位监听
        locationClient.setLocationListener(this);
        //每秒定位一次
        locationOption.setInterval(LOCATION_TIME_INTERVAL);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        locationOption.setMockEnable(false);
        //设置是否定位一次
        locationOption.setOnceLocation(false);

        // 获取最近3s内精度最高的一次定位结果：
        // 设置setOnceLocationLatest(boolean
        // b)接口为true，启动定位时SDK会返回最近3s内精度最高的一次定位结果。如果设置其为true，setOnceLocation(boolean
        // b)接口也会被设置为true，反之不会，默认为false。
        locationOption.setOnceLocationLatest(false);
        // 设置定位参数
        locationClient.setLocationOption(locationOption);
        // 启动定位
        locationClient.startLocation();
    }

    /*
        location.getAccuracy();    精度
        location.getAltitude();    高度 : 海拔
        location.getBearing();     导向
        location.getSpeed();       速度
        location.getLatitude();    纬度
        location.getLongitude();   经度
        location.getTime();        UTC时间 以毫秒计d
    */
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
            //通过以上方法获取定位类型，如果对定位类型要求比较高，可以过滤掉基站定位（类型6）结果 aMapLocation.getLocationType() != 6
            //通过以上方法获取定位精度，例如超过500M精度的定位结果可以考虑不在业务场景里使用
            if (aMapLocation != null
                    && aMapLocation.getErrorCode() == 0 && aMapLocation.getAccuracy() <100) {
                if (mLocationLinstener != null) {
                    mLocationLinstener.onLocationChanged(aMapLocation);// 显示系统小蓝点
                }

                mLocatinLat = aMapLocation.getLatitude();
                mLocationLon = aMapLocation.getLongitude();
                mLocationElevation = aMapLocation.getAltitude();    //高度 : 海拔
                //Log.i(TAG,mLocationElevation+"-----------海拔");
                latLng = new LatLng(mLocatinLat, mLocationLon);

                if (num == 1 && flags) {
                    mLocationList.add(latLng);
                    //获取起始点，放到整点集合中
                    if(tab == 1){
                        setTable(latLng);
                        tab --;
                    }
                }
                if (num == 2) {
                    //不添加
                }
                if (num == 3 && flags) {
                    mLocationList.add(latLng);
                }
                if (num == 4) {

                }

                //为了减少起步漂移，前五个点不要了
                if (n > 5 && flags  && aMapLocation.getLocationType() != 6) {
                    dis = dis + AMapUtils.calculateLineDistance(latLng1, new LatLng(mLocatinLat, mLocationLon));
                } else {
                    if (stepEnd - step != 0) {
                        if (UserBean.getUserBean().userHeight != 0) {

                            dis = (float) (dis + UserBean.getUserBean().userHeight / 100 * 0.48 * (Math.abs(stepEnd - step)));
                        } else {
                            dis = (float) (dis + 1.68 * 0.48 * (Math.abs(stepEnd - step)));
                        }
                        stepEnd = step;
                    }
                }
                n++;
                Log.i("距离",dis+"");

                latLng1 = new LatLng(mLocatinLat, mLocationLon);
                keepService2();
                getLatLong(latLng);
            }
    }

    //设置起点，每整公里坐标点
    private void setTable(LatLng latLng){
        mTable.add(latLng);
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mLocationLinstener = onLocationChangedListener;
    }

    @Override
    public void deactivate() {
        mLocationLinstener = null;
    }

    private void init() {
        PedometerPresenter pedometerPresenter = new PedometerPresenter(this);
        pedometerPresenter.resume();
        if (HardwarePedometerUtil.supportsHardwareAccelerometer(this)) {                           //判断是否支持Accelerometer算法

        } else {

        }
        if (HardwarePedometerUtil.supportsHardwareStepCounter(this)) {                             //判断是否支持Step_counter算法

        } else {

        }
    }

    @Override
    public void onReaderPedometer(PedometerCardEntity cardEntity) {
        if (cardEntity != null) {
            //获取实时步数
            step = cardEntity.getStepCount();
            //设置目标步数
            //cardEntity.setTargetStepCount(10);
            //获取目标步数
            //mProgress = (int) (100 * cardEntity.getStepCount() / (cardEntity.getTargetStepCount() * 1.0f));
        }
    }

    private long getCostTime() {
        if (num == 3 || num == 1) {
            if (stopTime == 0) {
                end = System.currentTimeMillis();
            } else {
                paseTime = paseTime + (stopTime - end);
                long now = stopTime - start - paseTime;     //用时
                stopTime = 0;
                return now;
            }

        } else if (num == 2) {
            stopTime = System.currentTimeMillis();
        }
        return end - start - paseTime;     //用时
    }

    public long getCostTimeq() {
        if (num == 3 || num == 1 && stopTime == 0) {
            end = System.currentTimeMillis();
        } else if (num == 3 || num == 1 && stopTime != 0) {
            paseTime = paseTime + (stopTime - end);
            stopTime = 0;
        } else if (num == 2) {
            stopTime = System.currentTimeMillis();
        }
        return end - start - paseTime;     //用时
    }

    /**
     * 获取运动时间
     */
    private String getSportTime() {
        String hms = "";
        int costtime = 0;
        if (start > 0) {
            costtime = (int) ((getCostTime()) / 1000);
            hms = secToTime(costtime);        //字符串输出
        }
        return hms;
    }

    /**
     * 获取配速   分钟/公里
     */
    private int t = (int) System.currentTimeMillis() / 1000;
    private int peisu = 0;
    private int time = 0;

    private String getSpeedPace() {
        float a = (dis - sss);
        if (a >= 100) {         //如果运动距离达到1000米
            time = (int) System.currentTimeMillis() / 1000;
            peisu = time - t;
            t = time;
            sss = dis;
        }
        return speedToTime(peisu * 10);
        // return secToTime(150);
    }

    /**
     * 获取配速   分钟/公里
     */
    private int tkm = (int) System.currentTimeMillis() / 1000;
    private int peisukm = 0;
    private int timekm = 0;

    private String getSpeedPacekm() {
        float a = (dis - ssskm);
        if (a >= 1000) {         //如果运动距离达到1000米
            timekm = (int) System.currentTimeMillis() / 1000;
            peisukm = timekm - tkm;
            tkm = timekm;
            ssskm = dis;
        }
        return speedToTime(peisukm);
        // return secToTime(150);
    }

    /**
     * 获取步频
     */
    private int steptStartTime = (int) System.currentTimeMillis() / 1000;
    private int stepEndtime = 0;
    private int stepStepNum = 0;

    private String getStepFrequency() {
        stepEndtime = (int) System.currentTimeMillis() / 1000;
        if (stepEndtime - steptStartTime >= 60) {
            stepStepNum = step - nowStep;
            steptStartTime = stepEndtime;
            nowStep = step;
        }
        //Toast.makeText(context,String.valueOf(a*60),Toast.LENGTH_SHORT).show();
        return String.valueOf(stepStepNum);
        //return String.valueOf(50);
    }


    /**
     * 取步幅
     */
    private float a = 0;
    private int b = 0;

    private String getStepScope() {
        a = (dis - ss);            //每秒距离
        ss = dis;
        b = step - nowSteps;         //每秒步数
        nowSteps = step;

        if (b != 0) {
            //Toast.makeText(context,String.valueOf(a/b), Toast.LENGTH_SHORT).show();
            Log.i(TAG, String.valueOf(a / (float) b) + " ==");
            return String.valueOf(a / (float) b);
            //return String.valueOf(60);
        }
        return "0";
    }

    /**
     * 获取速度
     * @return
     */
    private String getSpeed() {
        float as = (dis - s) * (float) 3.6;
        s = dis;

        if (decimalFormat == null) {
            decimalFormat = new DecimalFormat("0.0");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        }
        //Log.i("TAG",as +  "---------------速度");
        return decimalFormat.format(as) + "";
    }

    /**
     * 计算卡路里
     * 体重（kg）×距离（公里）×1.036
     */
    private String getCor() {
        int weight = 65;
        try {
            Float userweight = UserBean.getUserBean().userWeight;
            weight = userweight.intValue();
        } catch (Exception e) {
            weight = 65;
        }
        return String.valueOf((int) (((dis / 1000) * weight) * 1.036));
    }

    private int voiceDis = 0;

    private void voice() {
        int nowDis = (int) dis / 1000;
        if (nowDis - voiceDis >= 1) {
            voicePlayerUtil.startVoice("你已经跑了" + nowDis + "公里，当前配速为" + getSpeedPacekm().replace(":", "分") + "秒");
            voiceDis = nowDis;
            setTable(latLng);
        }
    }

    //设置当前跑步状态
    public void setNum(int num) {
        Log.e("MapService","before setNum state is "+this.num);
        this.num = num;
        if (num == 1) {
            start = System.currentTimeMillis();    //获取开始时间
            ApplicationModule.getInstance().getPedometerManager().startPedometerService();            //开始计步
            aCache.put("isService","true");              //双进程监听
            mLocationList.clear();
        } else if (num == 2) {
            ApplicationModule.getInstance().getPedometerManager().stopPedometerService();             //停止计步
        } else if (num == 3) {
            ApplicationModule.getInstance().getPedometerManager().startPedometerService();            //开始计步
        } else if (num == 4) {
            end = System.currentTimeMillis();
            step = 0;
            dis = 0;
            start = 0;
            end = 0;
            if(aCache == null){
                aCache = ACache.get(context);
            }
            aCache.put("isService","false");              //双进程监听
            locationClient.unRegisterLocationListener(this); //解除定位监听
            ApplicationModule.getInstance().getPedometerManager().stopPedometerService();             //停止计步
        }
        Log.e("MapService","after setNum state is "+this.num);
    }

    public void setModel(int m) {
        model = m;
    }


    private void getLatLong(final LatLng latLng) {
        Message message = new Message();
        message.what = HAS_LA_LONG;
        message.obj = latLng;
        handler.sendMessage(message);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Log.i(TAG, num + "-------------------状态"+"what is "+msg.what);

            switch (msg.what) {
                case HAS_LA_LONG:
                    if (num == 1 || num == 2 || num == 3) {
                        intent1.putExtra("latlong", (Parcelable) msg.obj);                               //经纬度
//                        intent1.putParcelableArrayListExtra("mLocationList", mLocationList);           //轨迹坐标点
                        intent1.putParcelableArrayListExtra("mTable",mTable);                            //整公里坐标点
                        intent1.putExtra("dis", String.valueOf((int) dis));                              //距离
                        intent1.putExtra("cor", getCor());                                                //卡路里
                        String time = getSportTime();
                        intent1.putExtra("sporttime", time);                                    //时间
                        Log.e("handleMessage","sport time is "+time);
                        intent1.putExtra("step", String.valueOf(step));                                   //步数
                        intent1.putExtra("speed", getSpeedPace().replaceAll(":","'") + "''");            //速度
                        intent1.putExtra("state", num);                                                   //相当于状态码
                        intent1.putExtra("flags",flags);                                                  //判断当前GPS信号，true为有GPS信号
                        intent1.putExtra("contextActivity", contextActivity);                          //与当前服务绑定的activity
                        intent1.setAction("com.lijianbao.mapLocationLatLong");
                        sendBroadcast(intent1);
                        Log.e(TAG,"send Broadcast state is "+num +"-----current object is "+this);

                        p++;
                        if (p == 5 && model == 1) {
                            String speed = getSpeed();
                            String speedSpace = getSpeedPacekm();          //配速
                            String stepFrequency = getStepFrequency();     //步频
                            String stepScope = getStepScope();             //步幅

                            latLong_impl.addLatLong(mLocatinLat, mLocationLon, getSportTime(),
                                    String.valueOf((int) dis), getCor(), String.valueOf(step), speed,
                                    speedSpace, stepFrequency, stepScope, String.valueOf(mLocationElevation));    //添加数据库
                            voice();     //语音播报
                            //Toast.makeText(context,"卡路里" + getCor() + "配速：" + getSpeedPace() + "  步频：" + getStepFrequency() + "步幅：" + getStepScope() + "", Toast.LENGTH_SHORT).show();
                            p = 0;
                        }
                    }
                    if (num == 4) {
                        //Log.i("当前状态","num" + "------" + num);
                        stopService2();
                        intent1.putExtra("contextActivity", "");                                      //与当前服务绑定的activity
                        intent1.putExtra("state", num);                                                //相当于状态码
                        intent1.setAction("com.lijianbao.mapLocationLatLong");
                        sendBroadcast(intent1);
                        locationClient.stopLocation();         //停止定位监听
                    }
                    break;
            }
        }
    };

    //把秒转换为时分秒格式
    private static String secToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = "00:" + unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    //把秒转换为时分秒格式
    private static String speedToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    //把秒转换为时分秒格式
    public static String secToTimeSpeed(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + "." + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + "小时" + unitFormat(minute) + "分钟" + unitFormat(second) + "秒";
            }
        }
        return timeStr;
    }


    private static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }

    private GpsStatus.Listener listener = new GpsStatus.Listener() {
        @Override
        public void onGpsStatusChanged(int event) {
            switch (event) {                        //第一次定位
                case GpsStatus.GPS_EVENT_FIRST_FIX:
                   // Log.i("lh", "第一次定位");
                    break;
                //卫星状态改变
                case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
                    //Log.i("lh", "卫星状态改变");
                    //获取当前状态
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    GpsStatus gpsStatus = locationManager.getGpsStatus(null);
                    //获取卫星颗数的默认最大值
                    int maxSatellites=gpsStatus.getMaxSatellites();
                    //创建一个迭代器保存所有卫星
                    Iterator<GpsSatellite> iters = gpsStatus.getSatellites().iterator();
                    int count = 0;
                    while (iters.hasNext() && count <= maxSatellites) {
                        GpsSatellite s =  iters.next();

//                        count++;//commented by zjl

                        if(s.getSnr()>30)  ///获取卫星的信躁比的函数   s.getSnr()>30
                        {
                            count++;
                            //表示有信号
//信号弱或无信号
                            flags = count >= 4;
                        }
                       // Log.i("GPS信号",flags + " --- " + count + " ---  " + s.getSnr());
                    }
                //System.out.println("搜索到：" + count + "颗卫星  max :"+ maxSatellites);
                break;//定位启动
            case GpsStatus.GPS_EVENT_STARTED:
            Log.i("lh", "定位启动");

            break;
            //定位结束
            case GpsStatus.GPS_EVENT_STOPPED:
            Log.i("lh", "定位结束");
            break;
        }
    }
};

    /**
     *启动Service2
     */
    private StrongService startS2 = new StrongService.Stub() {
        @Override
        public void stopService() throws RemoteException {
            Intent i = new Intent(getBaseContext(), RemoteService.class);
            getBaseContext().stopService(i);
        }
        @Override
        public void startService() throws RemoteException {
            Log.i("服务","启动服务 RemoteService");
            Intent i = new Intent(getBaseContext(), RemoteService.class);
            getBaseContext().startService(i);
        }
    };


    @Override
    public void onTrimMemory(int level){
        //Toast.makeText(getBaseContext(), "Service1 onTrimMemory..."+level, Toast.LENGTH_SHORT).show();
        if(aCache == null){
            aCache = ACache.get(context);
        }
        if(aCache.getAsString("isService").equals("true")){
            keepService2();
        }else{
            stopService2();
        }
    }



    /**
     * 判断Service2是否还在运行，如果不是则启动Service2
     */
    private  void keepService2(){
        boolean isRun = UtilService.isServiceRunning(Map_Service.this, Process_Name);
        Log.i("服务",isRun + " --- 保护进程的运行状态");
        if (!isRun) {
            try {
                //Toast.makeText(getBaseContext(), "重新启动 Service2保护进程", Toast.LENGTH_SHORT).show();
                startS2.startService();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 判断Service2是否还在运行，如果是则停止Service2
     */
    private  void stopService2(){
        boolean isRun = UtilService.isServiceRunning(Map_Service.this, Process_Name);
        if (isRun ) {
            try {
                startS2.stopService();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }
}
