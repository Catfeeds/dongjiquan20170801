package com.weiaibenpao.demo.chislim.map.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.jaeger.library.StatusBarUtil;
import com.weiaibenpao.demo.chislim.BuildConfig;
import com.weiaibenpao.demo.chislim.DebugConstant;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.map.util.LogUtil;
import com.weiaibenpao.demo.chislim.map.util.WalkUtil;
import com.weiaibenpao.demo.chislim.service.Map_Service;
import com.weiaibenpao.demo.chislim.settings.TtsSettings;
import com.weiaibenpao.demo.chislim.ui.MySportRemberActivity;
import com.weiaibenpao.demo.chislim.util.CircleChart02View;
import com.weiaibenpao.demo.chislim.util.VoicePlayerUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * Created by yhy on 2016/5/4.
 */
public class DrawTraceActivity extends Activity implements LocationSource {

    Bundle mSavedInstanceState;
    @BindView(R.id.dis)
    TextView dis;
    @BindView(R.id.myCalories)
    TextView myCalories;
    @BindView(R.id.showView)
    LinearLayout showView;
    @BindView(R.id.myTime)
    TextView myTime;
    @BindView(R.id.timeView)
    RelativeLayout timeView;
    @BindView(R.id.textWord)
    TextView textWord;
    @BindView(R.id.btn_goto)
    Button btnGoto;
    @BindView(R.id.startView)
    RelativeLayout startView;
    @BindView(R.id.mapView)
    MapView mapView;

    @BindView(R.id.mySpeed)
    TextView mySpeed;
    @BindView(R.id.suo)
    ImageView suo;
    @BindView(R.id.stop)
    ImageView stop;
    @BindView(R.id.tv_steps)
    TextView tvSteps;
    @BindView(R.id.start)
    ImageView start;
    @BindView(R.id.lockView)
    RelativeLayout lockView;
    @BindView(R.id.sportsTime)
    TextView sportime;
    @BindView(R.id.circle_view)
    CircleChart02View circleView;

    private VoicePlayerUtil voicePlayerUtil;
    private SharedPreferences mSharedPreferences;

    private AMap aMap;
    private UiSettings mUiSettings;
    // private OnLocationChangedListener mLocationLinstener;
    private AMapLocationClient locationClient = null;
    //private AMapLocationClientOption locationOption = null;

    private ArrayList<LatLng> mLocationList = new ArrayList<LatLng>();
    private ArrayList<LatLng> mTable = new ArrayList<>();

    private boolean mIsFirstLocation = true;
    private Marker mMarkMyLocation;
    private double mLocatinLat;
    private double mLocationLon;
    private double mBestLat;
    private double mBestLon;
    private double currLength;
    private long lastTime = 0;
    private long currTime = 0;
    private int errorCnt = 0;
    private long minusTime;
    //当前经纬度
    private LatLng mCurrentLatLng;
    //上次经纬度
    private LatLng mLastLatLng;
    private LatLng currLa;
    private LatLng lastLa = new LatLng(0, 0);
    private LatLng overLa = new LatLng(0, 0);

    private boolean mIsStart = false;

    private Map_Service map_Service;  //服务对象

    MyBroadcast myBroadcast;  //广播对象

    Context context;

    /**
     * 0 服务启动
     * 1 正常运动
     * 2 暂停
     * 3 继续
     * 4 停止
     */
    private int state;

    WindowManager wm;
    int widthScreen;
    int heightScreen;
    boolean screenFlag = false;

    Intent intentServices;    //启动
    Intent intentBindService;   //绑定

    String disData;                //当前运动距离
    String cor;                    //卡路里
    String sporttime;             //时间

    //圆
    CircleChart02View chart = null;
    int i = 0;

    DecimalFormat decimalFormat;

    //PolylineOptions polylineOptions;

    LatLng latLng;

    boolean flags = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("DrawTraceActivity","onCreate");
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //让一个activity 浮在锁屏界面的上方，返回即进入解锁界面
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        //设置屏幕常亮
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //设置窗体背景模糊
        /*getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND, WindowManager.LayoutParams.FLAG_BLUR_BEHIND);*/

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        setContentView(R.layout.activity_draw_trace);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this , getResources().getColor(R.color.ku_bg ) , 0);
        checkPerssion();             //权限监测

        mSharedPreferences = getSharedPreferences(TtsSettings.PREFER_NAME, MODE_PRIVATE);
        voicePlayerUtil = new VoicePlayerUtil(this.getApplicationContext(), mSharedPreferences);
        mSavedInstanceState = savedInstanceState;
        context = getApplicationContext();
        //mapView.onCreate(savedInstanceState);                          // 此方法必须重写
        initView();            //实例化定位
        //initStyleView();      //运动模式

        // intentBindService = new Intent(DrawTraceActivity.this, Map_Service.class);

        //配置广播
        myBroadcast = new MyBroadcast();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.lijianbao.mapLocationLatLong");
        DrawTraceActivity.this.registerReceiver(myBroadcast, filter);

        chart = (CircleChart02View) findViewById(R.id.circle_view);            //解锁用的圆
        chart.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    Log.i("周期", "开始");
                    i = 0;
                } else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                    Log.i("周期", "运行");
                    i++;
                    if (i == 100) {
                        lockView.setVisibility(View.GONE);
                        suo.setVisibility(View.VISIBLE);
                        stop.setVisibility(View.VISIBLE);
                        start.setVisibility(View.VISIBLE);
                        showView.setVisibility(View.VISIBLE);
                    }
                    chart.setPercentage(i);

                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    Log.i("周期", "结束");
                    i = 0;
                    chart.setPercentage(i);
                }
                chart.chartRender();
                chart.invalidate();
                return true;
            }
        });
    }

    protected void initView() {
        mapView.onCreate(mSavedInstanceState);// 此方法必须重写
        if (aMap == null) {
            aMap = mapView.getMap();
            aMap.setOnMapLoadedListener(new AMap.OnMapLoadedListener() {
                @Override
                public void onMapLoaded() {
                    aMap.setMapType(AMap.MAP_TYPE_NORMAL);
                    setMyLocationStyleIcon();
//                  aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(laQuick, loQuick), 17));
                }
            });
        }
        mUiSettings = aMap.getUiSettings();
        mUiSettings.setLogoPosition(2);//设置高德地图logo位置
        mUiSettings.setZoomControlsEnabled(false);
        // mUiSettings.setTiltGesturesEnabled(false);
        aMap.setLocationSource(this);// 设置定位监听
        aMap.setMyLocationEnabled(true);
        //     initLocation();

        wm = this.getWindowManager();

        widthScreen = wm.getDefaultDisplay().getWidth();
        heightScreen = wm.getDefaultDisplay().getHeight();

        aMap.setOnMapClickListener(new AMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                if(startView != null && startView.getVisibility() == View.VISIBLE){
                    return;
                }
                ViewGroup.LayoutParams lp;
                lp = mapView.getLayoutParams();
                if (screenFlag) {
                    screenFlag = false;
                    lp.width = widthScreen;
                    lp.height = (int) ((float) heightScreen * 0.3);
                    mapView.setLayoutParams(lp);

                    suo.setVisibility(View.VISIBLE);
                    stop.setVisibility(View.VISIBLE);
                    start.setVisibility(View.VISIBLE);

                } else {
                    screenFlag = true;
                    lp.width = widthScreen;
                    lp.height = (int) ((float) heightScreen * 0.65);;
                    mapView.setLayoutParams(lp);

                    suo.setVisibility(View.GONE);
                    stop.setVisibility(View.GONE);
                    start.setVisibility(View.GONE);
                }
            }
        });

    }

    private void setMyLocationStyleIcon() {
//		 自定义系统定位小蓝点

        MyLocationStyle myLocationStyle = new MyLocationStyle();
        ImageView iv = new ImageView(this);
        FrameLayout.LayoutParams fmIv = new FrameLayout.LayoutParams(1, 1);
        iv.setImageResource(R.mipmap.location);
        iv.setLayoutParams(fmIv);
        BitmapDescriptor markerIcon = BitmapDescriptorFactory.fromView(iv);
        myLocationStyle.myLocationIcon(markerIcon);          // 设置小蓝点的图标
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));       // 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));      // 设置圆形的填充颜色
        myLocationStyle.strokeWidth(0f);// 设置圆形的边框粗细
//				myLocationStyle.
        myLocationStyle.anchor(0.5f, 0.9f);
        aMap.setMyLocationStyle(myLocationStyle);

//        aMap.setMyLocationEnabled(true);
////				// 设置定位的类型为 跟随模式
//        aMap.setMyLocationType(AMap.MAP_TYPE_NORMAL);
    }


    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        //mLocationLinstener = onLocationChangedListener;
    }

    @Override
    public void deactivate() {
        // mLocationLinstener = null;

    }

    public void getState() {
        Log.i("跑步", state + "-------------------------------------");

        if (state == 1 || state == 3) {                   //运动
            startView.setVisibility(View.GONE);
            timeView.setVisibility(View.GONE);
            showView.setVisibility(View.VISIBLE);

            start.setImageResource(R.mipmap.stop);
        } else if (state == 2) {

            startView.setVisibility(View.GONE);
            timeView.setVisibility(View.GONE);
            showView.setVisibility(View.VISIBLE);

            start.setImageResource(R.mipmap.sportcontinue);
        }
    }


    //显示当前位置
    private void setMyStopLoca(final LatLng latlng) {
        aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 17f));

        if (mMarkMyLocation != null) {
            mMarkMyLocation.destroy();
            // mMarkMyLocation = null;
        }

        if (mMarkMyLocation == null) {
            final MarkerOptions markerOptions = new MarkerOptions();
            //markerOptions.snippet(dogId);
            // 设置Marker点击之后显示的标题R.drawable.local2R.mipmap.location
            //markerOptions.setFlat(false);
            markerOptions.anchor(0.5f, 0.7f);
            markerOptions.zIndex(25);
            markerOptions.zIndex(90);
            ImageView iv = new ImageView(this);
            FrameLayout.LayoutParams fmIv = new FrameLayout.LayoutParams(100, 100);
            iv.setImageResource(R.mipmap.ic_gps);
            iv.setLayoutParams(fmIv);
            BitmapDescriptor markerIcon = BitmapDescriptorFactory.fromView(iv);
            markerOptions.icon(markerIcon);
            markerOptions.position(latlng);
            mMarkMyLocation = aMap.addMarker(markerOptions);

        } else {
            mMarkMyLocation.setPosition(latlng);
        }
    }



    //设置没跑一公里的坐标标记点
    public void setMarkView(){
        for (int i = 0; i < mTable.size(); i++) {
            View view = View.inflate(DrawTraceActivity.this,R.layout.mark_layout, null);

            TextView sportMark = (TextView) view.findViewById(R.id.sportTitle);


            if (i == 0) {
                sportMark.setText("起");
                Bitmap bitmap = DrawTraceActivity.convertViewToBitmap(view);
                drawMarkerOnMap(mTable.get(i), bitmap, String.valueOf("起"));
            }else if(i > 0 && i < mTable.size()){
                sportMark.setText(String.valueOf(i));
                Bitmap bitmap = DrawTraceActivity.convertViewToBitmap(view);
                drawMarkerOnMap(mTable.get(i), bitmap, String.valueOf(i));
            }
        }
    }

    //view 转bitmap
    /*public static Bitmap convertViewToBitmap(View view){
        view.buildDrawingCache();
        Bitmap bitmap = view.getDrawingCache();
        return bitmap;
    }*/
    public static Bitmap convertViewToBitmap(View view) {

        view.measure(View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED), View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());

        view.buildDrawingCache();

        Bitmap bitmap = view.getDrawingCache();

        return bitmap;
    }

    /**
     * 在地图上画marker
     * @param point      marker坐标点位置（example:LatLng point = new LatLng(39.963175,
     *                   116.400244); ）
     * @param markerIcon 图标
     * @return Marker对象
     */
    private Marker drawMarkerOnMap(LatLng point, Bitmap markerIcon, String id) {

        if (aMap != null && point != null) {

            Marker marker = aMap.addMarker(new MarkerOptions().anchor(0.5f, 1)
                    .position(point)
                    .title(id)
                    .icon(BitmapDescriptorFactory.fromBitmap(markerIcon)));
            return marker;
        }
        return null;
    }

    private Polyline TotalLine;

    //绘制轨迹
    private void DrawRideTraceTotal() {
        if (TotalLine != null) {
            TotalLine.remove();
            TotalLine = null;
        }
        /*if(polylineOptions == null){
            polylineOptions = new PolylineOptions();
        }*/
        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.addAll(mLocationList);
        polylineOptions.visible(true).width(15).zIndex(200);
        //加入对应的颜色,使用colorValues 即表示使用多颜色，使用color表示使用单色线
        polylineOptions.colorValues(WalkUtil.getColorList(mLocationList.size() / 144 + 1, this));
        //加上这个属性，表示使用渐变线
        polylineOptions.useGradient(true);
        TotalLine = aMap.addPolyline(polylineOptions);

    }
    private boolean mOver = false;

    public void getLatLong() {

        if (mIsFirstLocation) {
            //设置地图的中心
            mIsFirstLocation = false;
            //定位当前坐标点
            setMyStopLoca(new LatLng(mLocatinLat, mLocationLon));
            //向集合中添加坐标点
            mLocationList.add(new LatLng(mLocatinLat, mLocationLon));
        } else if(flags){
            if (mLastLatLng == null) {
                mLastLatLng = new LatLng(mLocatinLat, mLocationLon);
            } else {
                findBest();
            }
        }
    }

    private void findBest() {

        //当前坐标点
        currLa = new LatLng(mLocatinLat, mLocationLon);
        //获取时间
        currTime = System.currentTimeMillis();
        LogUtil.d("test walk la is" + currLa + "");
        LogUtil.d("test walk last is" + lastLa + "");

        currLength = AMapUtils.calculateLineDistance(
                lastLa, currLa);
        //TODO 传感器检测
        if (!lastLa.equals(currLa)) {

            minusTime = currTime - lastTime;

            LogUtil.d("yhy time testzzz" + minusTime);

            if (currLength < ((minusTime + 1) / 1000) * 5) {

                errorCnt = 0;
                lastLa = currLa;
                lastTime = currTime;

                mBestLat = mLocatinLat;
                mBestLon = mLocationLon;

                mCurrentLatLng = new LatLng(mBestLat, mBestLon);
                LogUtil.d("yhy 发给轩哥了>>>>>>>>>" + currLength);
                mLocationList.add(mCurrentLatLng);
                mMarkMyLocation.setPosition(mCurrentLatLng);
                DrawRideTraceTotal();

            } else if (minusTime >= 20000) {

                if (mOver) {

                    if (!overLa.equals(currLa)) {
                        errorCnt = 0;
                        lastLa = currLa;
                        lastTime = currTime;
                        LogUtil.d("yhy 确定大于距离，发给轩哥>>>>>>>>>" + currLength);

                        mBestLat = mLocatinLat;
                        mBestLon = mLocationLon;

                        mCurrentLatLng = new LatLng(mBestLat, mBestLon);
                        mLocationList.add(mCurrentLatLng);
                        mMarkMyLocation.setPosition(mCurrentLatLng);
                        DrawRideTraceTotal();
                        mOver = false;
                    } else {
                        errorCnt = 0;
                        lastTime = currTime;
                        mOver = false;
                    }
                } else {
                    if (currLength > ((minusTime + 1) / 1000) * 5) {
                        mOver = true;
                        overLa = currLa;
                        LogUtil.d("yhy 第一次大于距离" + currLength);
                    }
                }

            } else {
                errorCnt++;
                LogUtil.d("yhy +++++++++++++++++++++++++++++++++++++++++++++距离太大，是漂移，不发" + currLength + "定位是" + mLocatinLat + "^^^" + mLocationLon);
            }


        } else {
            LogUtil.d("yhy -------------------------------------------------距离太小，没有移动，不发");
//                Toast.makeText(MainActivity.this,"------距离太小，没有移动，不发",Toast.LENGTH_SHORT).show();
            lastTime = currTime;
        }
//        }
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        intentServices = new Intent(DrawTraceActivity.this, Map_Service.class);
        if (!isServiceRunning(context, "com.weiaibenpao.demo.chislim.service.Map_Service")) {
            intentServices.putExtra("activityContext", "com.jianbaopp.DrawTraceActivity");
            startService(intentServices);
        }
        if (map_Service == null) {
            startView.setVisibility(View.VISIBLE);
        }
        bindService(intentServices, connection, Context.BIND_AUTO_CREATE);
    }

    // 检测服务是否正在运行
    private boolean isServiceRunning(Context context, String service_Name) {
        ActivityManager manager =
                (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (service_Name.equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }


    /**
     * 获取服务对象
     */
    public ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Map_Service.My_binder binder = (Map_Service.My_binder) service;
            map_Service = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            map_Service.onDestroy();
        }
    };

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        deactivate();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if (null != locationClient) {
            locationClient.onDestroy();
        }
        releaseResource();
        if(voicePlayerUtil!= null && BuildConfig.DEBUG) {
            voicePlayerUtil.startVoice("Draw Trace 被销毁");
        }
    }

    @OnClick({R.id.btn_goto, R.id.suo, R.id.stop, R.id.start})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_goto:                               //倒计时前的开始按钮
                startView.setVisibility(View.GONE);
                timeView.setVisibility(View.VISIBLE);
                setTime();

                break;
            case R.id.suo:
                lockView.setVisibility(View.VISIBLE);
                suo.setVisibility(View.GONE);
                stop.setVisibility(View.GONE);
                start.setVisibility(View.GONE);
                showView.setVisibility(View.GONE);
                break;
            case R.id.stop:
                voicePlayerUtil.startVoice("跑步停止！");
                goToRecord();
                mIsStart = false;
                map_Service.setNum(4);

//                map_Service.stopService(intentServices);         //关闭启动式服务
//                map_Service.stopSelf();
                finish();
                break;
            case R.id.start:             //倒计时后  开始 暂停 的按钮
                if (state == 1 || state == 3) {                   //运动
                    voicePlayerUtil.startVoice("跑步暂停！");
                    map_Service.setNum(2);                        //暂停跑步
                    start.setImageResource(R.mipmap.sportcontinue);
                } else if (state == 2) {                 //暂停
                    voicePlayerUtil.startVoice("跑步继续！");
                    map_Service.setNum(3);               //继续 跑步
                    start.setImageResource(R.mipmap.stop);
                }
                break;
        }
    }

    private void goToRecord() {
        Intent intent1 = new Intent(DrawTraceActivity.this, MySportRemberActivity.class);
        mTable.add(latLng);
        intent1.putParcelableArrayListExtra("mTable",mTable);
        startActivity(intent1);
    }


    //三秒倒计时
    public void setTime() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                voicePlayerUtil.startVoice("3");
                for (int i = 3; i >= -1; i--) {
                    Message msg = Message.obtain();
                    try {
                        Thread.sleep(1000);
                        msg.what = 0;
                        msg.arg1 = i;
                        hander.sendMessage(msg);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

     Handler hander = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    if (msg.arg1 == 3) {
                        myTime.setText(msg.arg1 + "");
                        myTime.setTextSize(180);
                    }
                    if (msg.arg1 == 2) {
                        voicePlayerUtil.startVoice("2");
                        myTime.setText(msg.arg1 + "");
                        myTime.setTextSize(130);
                    }
                    if (msg.arg1 == 1) {
                        voicePlayerUtil.startVoice("1");
                        myTime.setText(msg.arg1 + "");
                        myTime.setTextSize(80);
                    }
                    if (msg.arg1 == 0) {
                        voicePlayerUtil.startVoice("跑步开始");
                        myTime.setText("Go!");
                        myTime.setTextSize(150);
                    } else if (msg.arg1 == -1) {
                        timeView.setVisibility(View.GONE);
                        showView.setVisibility(View.VISIBLE);
                        map_Service.setNum(1);                       //开始跑步
                        map_Service.setModel(1);
                    }
                    break;
            }
        }
    };

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
            latLng = (LatLng) intent.getParcelableExtra("latlong");
            if(latLng == null) {
                return;//临时解决方案，因为其中一次闪退报了NullPointerException，也就是说发来的latlong为null,得查看MapService
            }

            mLocatinLat = latLng.latitude;
            mLocationLon = latLng.longitude;                                            //当前定位
//            mLocationList = intent.getParcelableArrayListExtra("mLocationList");        //当前运动过路线
            mLocationList = Map_Service.mLocationList;
            mTable = intent.getParcelableArrayListExtra("mTable");                        //整公里坐标点
            disData = intent.getStringExtra("dis");                                       //距离
            String cor = intent.getStringExtra("cor");                                     //卡路里
            String sporttime = intent.getStringExtra("sporttime");                        //时间
            String step = intent.getStringExtra("step");                                   //步数
            String speed = intent.getStringExtra("speed");                                 //速度
            flags = intent.getBooleanExtra("flags",true);                                 //判断是否为GPS  true代表为Gps

            state = intent.getIntExtra("state", 0);
            getState();

            if (decimalFormat == null) {
                decimalFormat = new DecimalFormat("0.00");       //构造方法的字符格式这里如果小数不足2位,会以0补足.
            }

            dis.setText(decimalFormat.format(Double.parseDouble(disData) / 1000) + ("km"));
            sportime.setText(sporttime);
            mySpeed.setText(speed);
            tvSteps.setText(step);
            myCalories.setText(cor);
            //绘制轨迹
            getLatLong();

            //设置整公里标记
            if(DebugConstant.IS_EXPERIMENTAL){
//                if(BuildConfig.DEBUG) Log.e("DrawTrace","no setMarkView");
            }else {
                setMarkView();
//                if(BuildConfig.DEBUG) Log.e("DrawTrace","yes setMarkView");
            }

            setIntro();
            if(BuildConfig.DEBUG) Log.e("DrawTrace","onReceive");
        }
    }

    /**
     * 切换地图显示中心点
     */
    public void setIntro() {
        if (mLocatinLat != 0 && mLocationLon != 0) {

            LatLng marker1 = new LatLng(mLocatinLat, mLocationLon);
            //设置中心点和缩放比例
            aMap.moveCamera(CameraUpdateFactory.changeLatLng(marker1));
            aMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    int WRITE_COARSE_LOCATION_REQUEST_CODE = 0;

    public void checkPerssion() {
        //SDK在Android 6.0下需要进行运行检测的权限如下：
        /*Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.READ_PHONE_STATE,*/

        //这里以ACCESS_COARSE_LOCATION为例
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE},
                    WRITE_COARSE_LOCATION_REQUEST_CODE);//自定义的code
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //可在此继续其他操作。
        if (WRITE_COARSE_LOCATION_REQUEST_CODE == 0) {

        }
    }

    /**
     * 重写onKeyDown方法
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
            Log.e("onKeyDown","------------------------------->");
            if (state != 0) {
                showDialog();
                return true;
            }
        }
        return super.onKeyDown(keyCode, event);
    }


    /**
     * 这是兼容的 AlertDialog
     */
    private void showDialog() {
      /*
          这里使用了 android.support.v7.app.AlertDialog.Builder
          可以直接在头部写 import android.support.v7.app.AlertDialog
          那么下面就可以写成 AlertDialog.Builder
      */
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("跑步提示");
        builder.setMessage("跑步还在进行中，请结束后退出？");
        builder.setNegativeButton("结束", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                goToRecord();
                mIsStart = false;
                map_Service.setNum(4);
                finish();
            }
        });
        builder.setPositiveButton("继续", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }

    private void releaseResource() {
        unbindService(connection);                         //解除绑定服务
        unregisterReceiver(myBroadcast);
        hander.removeCallbacksAndMessages(null);
        map_Service.stopService(intentServices);         //关闭启动式服务
        map_Service.stopSelf();
    }
}
