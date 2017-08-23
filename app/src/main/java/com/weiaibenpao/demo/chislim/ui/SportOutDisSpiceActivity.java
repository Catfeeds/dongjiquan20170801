package com.weiaibenpao.demo.chislim.ui;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.maps.overlay.DrivingRouteOverlay;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.jaeger.library.StatusBarUtil;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.bean.SpiceSportRemberBean;
import com.weiaibenpao.demo.chislim.customs.UploadProgressDialog;
import com.weiaibenpao.demo.chislim.service.Map_Service;
import com.weiaibenpao.demo.chislim.settings.TtsSettings;
import com.weiaibenpao.demo.chislim.util.ACache;
import com.weiaibenpao.demo.chislim.util.CircleChart02View;
import com.weiaibenpao.demo.chislim.util.MyTestView;
import com.weiaibenpao.demo.chislim.util.VoicePlayerUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SportOutDisSpiceActivity extends Activity implements LocationSource {

    private static final int LOCATION_TIME_INTERVAL = 4000;

    Bundle mSavedInstanceState;

    @BindView(R.id.map)
    MapView map;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.dis)
    TextView dis;
    @BindView(R.id.sportTime)
    TextView sportTime;
    @BindView(R.id.tv_steps)
    TextView tvSteps;
    @BindView(R.id.mySpeed)
    TextView mySpeed;
    @BindView(R.id.myCalories)
    TextView myCalories;
    @BindView(R.id.start)
    FloatingActionButton start;
    @BindView(R.id.suo)
    FloatingActionButton suo;
    @BindView(R.id.stop)
    FloatingActionButton stop;
    @BindView(R.id.showView)
    LinearLayout showView;
    @BindView(R.id.textWord)
    TextView textWord;
    @BindView(R.id.sportType)
    TextView sportType;
    @BindView(R.id.btn_goto)
    Button btnGoto;
    @BindView(R.id.startView)
    RelativeLayout startView;
    @BindView(R.id.myTime)
    TextView myTime;
    @BindView(R.id.timeView)
    RelativeLayout timeView;
    @BindView(R.id.circle_view)
    CircleChart02View circleView;
    @BindView(R.id.lockView)
    RelativeLayout lockView;
    @BindView(R.id.activity_sport_out_dis_spice)
    RelativeLayout activitySportOutDisSpice;
    @BindView(R.id.toplayout)
    RelativeLayout toplayout;
    String mapStr;
    private AMap aMap;   //定义一个地图对象
    private AMapLocationClient locationClient = null;
    private VoicePlayerUtil voicePlayerUtil;
    private SharedPreferences mSharedPreferences;


    private ArrayList<LatLng> mLocationList = new ArrayList<LatLng>();


    private double mLocatinLat;
    private double mLocationLon;

    private boolean mIsStart = false;

    private Map_Service map_Service;  //服务对象

    MyBroadcast myBroadcast;  //广播对象

    /**
     * 0 服务启动
     * 1 正常运动
     * 2 暂停
     * 3 继续
     * 4 停止
     */
    private int state;

    MyTestView mytestviewtop;
    MyTestView myTestView;
    public ArrayList<Float> xlists, ylists;


    Intent myIntent;
    int disPlan;// 计划跑步距离

    String disData;                //当前运动距离
    String cor;                    //卡路里
    String sporttime;             //时间
    String step;
    String speed;
    Intent intentServices;    //启动

    WindowManager wm;
    int widthScreen;
    int heightScreen;
    boolean screenFlag = false;

    //圆,用于解锁
    CircleChart02View chart = null;
    int i = 0;


    ArrayList arrayList;              //存放规划路线点的坐标
    ArrayList myLatLong;              //已绘制轨迹点存放

    Context context;

    double latDou;
    double longDou;
    Polyline polyline;
    PolylineOptions polylineOptions;
    double distance;          //路线规划的总距离
    int pos;                   //路线规划的总点数

    String firstCity;
    String secondCity;
    double fromLat;
    double fromLong;
    double toLat;
    double toLong;
    int position;
    int disNow;

    private ACache mCache;
    int disOld = 0;               //运动记录
    UploadProgressDialog progressDialog;

    DecimalFormat decimalFormat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport_out_dis_spice);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this , getResources().getColor(R.color.ku_bg ) , 0);
        context = getApplicationContext();
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，实现地图生命周期管理
        map.onCreate(savedInstanceState);// 此方法必须重写

        mCache = ACache.get(context);    //实例化缓存
        mSharedPreferences = getSharedPreferences(TtsSettings.PREFER_NAME, MODE_PRIVATE);
        voicePlayerUtil = new VoicePlayerUtil(SportOutDisSpiceActivity.this.getApplicationContext(),mSharedPreferences);
        progressDialog = new UploadProgressDialog(this,"正在加载数据中...");
        progressDialog.showDialog();
        arrayList = new ArrayList();
        myLatLong = new ArrayList();

        //第一步获取起点坐标和终点坐标
        initData();
        //第二部实例化地图
        initMap();
        //第三步，路径规划，并绘提前制路线图
        SearchLine(fromLat, fromLong, toLat, toLong);
        //第四步，重绘制轨迹
        //huizhi(2550);

        mCache = ACache.get(context);
        mCache.put("state", "0");
        //getCache();

        initMap();
        getState();

        //配置广播
        myBroadcast = new MyBroadcast();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.lijianbao.mapLocationLatLong");
        SportOutDisSpiceActivity.this.registerReceiver(myBroadcast, filter);

        myIntent = getIntent();
        disPlan = myIntent.getIntExtra("dis", 1000);

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
                        toplayout.setVisibility(View.VISIBLE);
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

    @OnClick(R.id.back)
    public void onClick() {
        finish();
    }

    /**
     * 获取传过来的坐标点
     */
    public void initData() {
        /*Intent getIntent = getIntent();
        firstCity = getIntent.getStringExtra("fistCity");
        secondCity = getIntent.getStringExtra("secondCity");
        disNow = getIntent.getIntExtra("disNow",0);
        fromLat = getIntent.getDoubleExtra("fromLat", 0);
        fromLong = getIntent.getDoubleExtra("fromLong", 0);
        toLat = getIntent.getDoubleExtra("toLat", 0);
        toLong = getIntent.getDoubleExtra("toLong", 0);*/

        firstCity = mCache.getAsString("fistCity");
        secondCity = mCache.getAsString("secondCity");
        disNow = Integer.parseInt(mCache.getAsString("disNow"));
        position = Integer.parseInt(mCache.getAsString("position"));
        fromLat = Double.parseDouble(mCache.getAsString("fromLat"));
        fromLong = Double.parseDouble(mCache.getAsString("fromLong"));
        toLat = Double.parseDouble(mCache.getAsString("toLat"));
        toLong = Double.parseDouble(mCache.getAsString("toLong"));
    }


    private void clearMap() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    clearMarker();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    /**
     * 添加指定的marker
     */
    public void addMarker(){

        MarkerOptions markerOptions = new MarkerOptions();
        //添加一个位置--经度，维度---marker对应一个markerOptions，用来设置marker的属性等
        markerOptions.position(new LatLng(fromLat, fromLong));
        //添加图标
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_marker_start));;
        //添加marker
        aMap.addMarker(markerOptions);
    }

    /**
     * 清除marker
     */

    public void clearMarker(){
        List<Marker> saveMarkerList = aMap.getMapScreenMarkers();
        if (saveMarkerList == null || saveMarkerList.size() <= 0)
            return;

        for(int i =0;i<saveMarkerList.size()-1;i++){
            saveMarkerList.get(i).remove();
        }
        addMarker();


    }

    /**
     * 实例化地图
     */
    public void initMap() {
        if (aMap == null) {
            aMap = map.getMap();
        }

        aMap.setTrafficEnabled(true);// 显示实时交通状况
        //地图模式可选类型：MAP_TYPE_NORMAL,MAP_TYPE_SATELLITE,MAP_TYPE_NIGHT
        aMap.setMapType(AMap.MAP_TYPE_SATELLITE);// 卫星地图模式
        wm = this.getWindowManager();

        widthScreen = wm.getDefaultDisplay().getWidth();
        heightScreen = wm.getDefaultDisplay().getHeight();

        /*aMap.setOnMapClickListener(new AMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                ViewGroup.LayoutParams lp;
                lp = map.getLayoutParams();
                if (screenFlag) {
                    screenFlag = false;
                    lp.width = widthScreen;
                    lp.height = (int) ((float) heightScreen * 0.3);
                    map.setLayoutParams(lp);
                } else {
                    screenFlag = true;
                    lp.width = widthScreen;
                    lp.height = heightScreen;
                    map.setLayoutParams(lp);
                }
            }
        });*/
    }

    /**
     * 路线搜索
     */
    public void SearchLine(double fromLat, double fromLong, double toLat, double toLong) {
        RouteSearch mRouteSearch = new RouteSearch(this);

        LatLonPoint latLonPoint1 = new LatLonPoint(fromLat, fromLong);     //起始坐标点
        LatLonPoint latLonPoint2 = new LatLonPoint(toLat, toLong);     //终点坐标点

        RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(latLonPoint1, latLonPoint2);
        // 第三个参数表示途经点（最多支持16个），第四个参数表示避让区域（最多支持32个），第五个参数表示避让道路
        RouteSearch.DriveRouteQuery query1 = new RouteSearch.DriveRouteQuery(fromAndTo, 1, null, null, "");
        mRouteSearch.calculateDriveRouteAsyn(query1);

        mRouteSearch.setRouteSearchListener(new RouteSearch.OnRouteSearchListener() {
            @Override
            public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

            }

            @Override
            public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {
                Log.i("错误码","驾车 ----------" + i + "-----------");
                if (i == 0 || i == 1000) {
                    if (driveRouteResult != null && driveRouteResult.getPaths() != null
                            && driveRouteResult.getPaths().size() > 0) {
                        DrivePath drivePath = driveRouteResult.getPaths().get(0);
                       // aMap.clear();// 清理地图上的所有覆盖物
                        DrivingRouteOverlay drivingRouteOverlay = new DrivingRouteOverlay(context,
                                aMap, drivePath, driveRouteResult.getStartPos(),
                                driveRouteResult.getTargetPos());

                        drivingRouteOverlay.removeFromMap();           //删除以前的
                        drivingRouteOverlay.addToMap();                //添加新的
                        drivingRouteOverlay.zoomToSpan();               //// 缩放地图，使所有overlay都在合适的视野范围内

                        int kb = drivePath.getSteps().size();
                        for (int j = 0; j < kb; j++) {
                            distance = distance + drivePath.getSteps().get(j).getDistance();        //获取距离
                        }

                        ArrayList<LatLonPoint> latLonPoint = new ArrayList<LatLonPoint>();
                        for (int j = 0; j < kb; j++) {
                            latLonPoint.addAll(drivePath.getSteps().get(j).getPolyline());        //获取当前路线的经纬度坐标点
                        }

                        pos = latLonPoint.size();
                        for (int j = 0; j < pos; j++) {
                            latDou = latLonPoint.get(j).getLatitude();
                            longDou = latLonPoint.get(j).getLongitude();

                            arrayList.add(new LatLng(latDou, longDou));
                        }
                        progressDialog.cancelDialog();
                        clearMap();

                    } else {
                        Toast.makeText(context, "对不起，没查询到结果", Toast.LENGTH_SHORT)
                                .show();
                    }
                } else if (i == 27) {
                    Toast.makeText(context, "net error", Toast.LENGTH_SHORT).show();
                } else if (i == 32) {
                    Toast.makeText(context, "key error", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "other--------error-----" + i, Toast.LENGTH_SHORT).show();
                }
                System.out.println("========onWalkRouteSearched()=========");
            }

            @Override
            public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {

            }

            @Override
            public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

            }
        });
    }

    public void getState() {
        Log.i("跑步", state + "-------------------------------------");
        if (state == 1 || state == 2 || state == 3) {
            startView.setVisibility(View.GONE);
            timeView.setVisibility(View.GONE);
            showView.setVisibility(View.VISIBLE);
            //  btnStart.setText("停止");     //设置显示为停止
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        intentServices = new Intent(SportOutDisSpiceActivity.this, Map_Service.class);
        if (!isServiceRunning(context, "com.weiaibenpao.demo.chislim.service.Map_Service")) {
            intentServices.putExtra("activityContext","com.jianbaopp.SportOutDisSpiceActivity");
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

    //三秒倒计时
    public void setTime() {
        voicePlayerUtil.startVoice("3,,2,,1,,跑步开始！");
        new Thread(new Runnable() {
            @Override
            public void run() {
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
                    if(msg.arg1 == 3 ){
                        myTime.setText(msg.arg1 + "");
                        myTime.setTextSize(180);
                    }
                    if(msg.arg1 == 2){
                        myTime.setText(msg.arg1 + "");
                        myTime.setTextSize(130);
                    }
                    if(msg.arg1 == 1){
                        myTime.setText(msg.arg1 + "");
                        myTime.setTextSize(80);
                    }
                    if (msg.arg1 == 0) {
                        myTime.setText("Go!");
                        myTime.setTextSize(150);
                    } else if (msg.arg1 == -1) {
                        timeView.setVisibility(View.GONE);
                        showView.setVisibility(View.VISIBLE);
                        map_Service.setNum(1);                       //开始跑步
                        map_Service.setModel(1);
                        //   btnStart.setText("停止");     //设置显示为停止
                    }
                    break;
            }
        }
    };


    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {

    }

    @Override
    public void deactivate() {

    }

    /**
     * 内部类实现进度条和界面动态显示
     */
    int disTab;
    public class MyBroadcast extends BroadcastReceiver {

        public MyBroadcast() {

        }

        /**
         * @param context
         * @param intent  intent1.putExtra("state",num);
         */
        @Override
        public void onReceive(Context context, Intent intent) {
            LatLng latLng = (LatLng) intent.getParcelableExtra("latlong");

            mLocatinLat = latLng.latitude;
            mLocationLon = latLng.longitude;                                          //当前定位
            mLocationList = intent.getParcelableArrayListExtra("mLocationList");     //当前运动过路线

            disData = intent.getStringExtra("dis");                              //距离
            cor = intent.getStringExtra("cor");                                   //卡路里
            sporttime = intent.getStringExtra("sporttime");                     //时间
            step = intent.getStringExtra("step");                                  //步数
            speed = intent.getStringExtra("speed");                             //速度
            state = intent.getIntExtra("state", 0);
            Log.i("状态", state + "====================" + sporttime);
            getState();

            if(decimalFormat == null){
                decimalFormat = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
            }

            dis.setText(decimalFormat.format(Double.parseDouble(disData)/1000) + ("km"));                                                //当前运动近距离
            sportTime.setText(sporttime);
            mySpeed.setText(speed);
            tvSteps.setText(step);
            myCalories.setText(cor);

            if(disTab != Integer.parseInt(disData)){
                huizhi(Integer.parseInt(disData));
                disTab = Integer.parseInt(disData);
            }
        }
    }

    /**
     * 获取运动记录
     */
    public void getCache(){
        try{
            if(mCache.getAsString("SportDisSpiceActivity" + position) != null){
                disOld = Integer.parseInt(mCache.getAsString("SportDisSpiceActivity" + position));
                Log.i("运动记录","--------获取记录成功--------");
            }if(mCache.getAsObject("sportSpice") != null){
                SpiceSportRemberBean spiceSportRemberBean = (SpiceSportRemberBean) mCache.getAsObject("sportSpice");
                disOld = spiceSportRemberBean.getDisNow();
            } else{
                Log.i("运动记录","--------获取记录失败--------");
            }
        }catch (Exception e){
            Log.i("运动记录","--------获取记录异常--------");
        }

    }

    /**
     * 绘制轨迹
     */
    int h = 0;
    public void huizhi(int dis) {

        dis = dis + disNow * 1000;
      //  clearMarkers(); // 清除marker
        myLatLong.clear();

        if (dis >= distance) {
            h = pos;
        }else{
            h  = (int) (pos * (dis / distance));
        }

        for (int m = 0; m < h; m++) {
            setIntro(((LatLng)arrayList.get(m)).latitude,((LatLng)arrayList.get(m)).longitude);    //设置地图中心点
            myLatLong.add(arrayList.get(m));
        }

        if (polyline != null) {
            polyline.remove();
            polyline = null;
        }
            /*polyline = aMap.addPolyline(new PolylineOptions().
                    addAll(myLatLong).width(10).color(Color.argb(255, 1, 1, 1)));*/

        // if(polylineOptions == null){
        polylineOptions = new PolylineOptions();
        // polylineOptions.add((LatLng) arrayList.get(h));
        polylineOptions.addAll(myLatLong);
        polylineOptions.visible(true).width(20).zIndex(200);
        // 加入对应的颜色,使用colorValues 即表示使用多颜色，使用color表示使用单色线
        //polylineOptions.colorValues(WalkUtil.getColorList(myLatLong.size()/144+1,this));
        polylineOptions.color(Color.argb(255, 1, 1, 1));
        //加上这个属性，表示使用渐变线
        polylineOptions.useGradient(true);
        polyline = aMap.addPolyline(polylineOptions);
    }

    /**
     * 清除marker
     */
    public void clearMarkers(){
        List<Marker> saveMarkerList = aMap.getMapScreenMarkers();
        if (saveMarkerList == null || saveMarkerList.size() <= 0)
            return;
        for (Marker marker : saveMarkerList) {
            marker.remove();
        }
    }


    /**
     * 切换地图显示中心点
     * @param latitude
     * @param longitude
     */
    public void setIntro(double latitude, double longitude) {
        LatLng marker1 = new LatLng(latitude, longitude);
        //设置中心点和缩放比例
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(marker1));
        aMap.moveCamera(CameraUpdateFactory.zoomTo(15));
    }


    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        map.onResume();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        map.onPause();
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        map.onSaveInstanceState(outState);
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        map.onDestroy();
        if (null != locationClient) {
            locationClient.onDestroy();
        }
        unregisterReceiver(myBroadcast);
    }

   /* @OnClick({R.id.start, R.id.suo, R.id.stop, R.id.btn_goto})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.start:
                if (state == 1 || state == 3) {                   //运动
                    map_Service.setNum(2);                  //暂停跑步
                    start.setImageResource(R.mipmap.startn);
                } else if (state == 2) {             //暂停
                    map_Service.setNum(3);              //继续 跑步
                    start.setImageResource(R.mipmap.stopn);
                }
                break;
            case R.id.suo:
                lockView.setVisibility(View.VISIBLE);
                suo.setVisibility(View.GONE);
                stop.setVisibility(View.GONE);
                start.setVisibility(View.GONE);
                toplayout.setVisibility(View.GONE);
                break;
            case R.id.stop:
                //cutMapScreen();

                int nowDis;
                try {
                    nowDis = Integer.parseInt(disData);
                } catch (Exception e) {
                    nowDis = 0;
                }
                //  if (nowDis >= 1000) {
                Intent intent1 = new Intent(SportOutDisSpiceActivity.this, MySportRemberActivity.class);
                startActivity(intent1);
                mIsStart = false;
                map_Service.setNum(4);
                unbindService(connection);                         //解除绑定服务
                map_Service.stopService(intentServices);         //关闭启动式服务
                map_Service.stopSelf();
                mCache.put("SportDisSpiceActivity" + position,String.valueOf(Integer.parseInt(disData)));
                finish();
                //  } else {
                //    getDialog();
                //}
                break;
            case R.id.btn_goto:
                startView.setVisibility(View.GONE);
                timeView.setVisibility(View.VISIBLE);
                setTime();
                break;
        }
    }*/


    @OnClick({R.id.back, R.id.btn_goto, R.id.suo, R.id.stop, R.id.start})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
/*            case R.id.sportType:                               //跑步模式选择
                Intent intent = new Intent(DrawTraceActivity.this, FavouritePicActivity.class);
                intent.putExtra("position", 1);
                startActivity(intent);
                break;*/
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
                toplayout.setVisibility(View.GONE);
                showView.setVisibility(View.GONE);
                break;
            case R.id.stop:
                voicePlayerUtil.startVoice("跑步停止！");

                int nowDis;
                try {
                    nowDis = Integer.parseInt(disData);
                } catch (Exception e) {
                    nowDis = 0;
                }
                //  if (nowDis >= 1000) {

                mIsStart = false;
                map_Service.setNum(4);
                unbindService(connection);                         //解除绑定服务
                map_Service.stopService(intentServices);         //关闭启动式服务
                map_Service.stopSelf();
                mCache.put("sportSpice",new SpiceSportRemberBean(firstCity,secondCity,fromLat,fromLong,toLat,toLong,position,Integer.parseInt(disData)/1000 + disNow));
               // Intent intent1 = new Intent(SportOutDisSpiceActivity.this, MySportRemberActivity.class);

                mCache.put("disData",disData);
                mCache.put("cor",cor);
                mCache.put("sporttime",sporttime);
                mCache.put("step",step);
                mCache.put("speed",speed);
                Intent intent1 = new Intent(SportOutDisSpiceActivity.this, MySportSimRemberActivity.class);
                startActivity(intent1);
                finish();
                //  } else {
                //    getDialog();
                //}
                break;
            case R.id.start:             //倒计时后  开始 暂停 的按钮
                if (state == 1 || state == 3) {                   //运动
                    voicePlayerUtil.startVoice("跑步暂停！");
                    map_Service.setNum(2);                        //暂停跑步
                    start.setImageResource(R.mipmap.startn);
                } else if (state == 2) {                 //暂停
                    voicePlayerUtil.startVoice("跑步继续！");
                    map_Service.setNum(3);               //继续 跑步
                    start.setImageResource(R.mipmap.stopn);
                }
                break;
        }
    }
}
