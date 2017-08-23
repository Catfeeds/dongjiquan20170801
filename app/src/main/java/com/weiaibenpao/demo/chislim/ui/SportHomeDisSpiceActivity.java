package com.weiaibenpao.demo.chislim.ui;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
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
import com.squareup.picasso.Picasso;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.bean.BluResult;
import com.weiaibenpao.demo.chislim.bean.SpiceSportRemberBean;
import com.weiaibenpao.demo.chislim.ble.adapter.DeviceAdapter;
import com.weiaibenpao.demo.chislim.ble.entity.EntityDevice;
import com.weiaibenpao.demo.chislim.ble.service.BLEService;
import com.weiaibenpao.demo.chislim.ble.utils.BluetoothController;
import com.weiaibenpao.demo.chislim.ble.utils.ConstantUtils;
import com.weiaibenpao.demo.chislim.ble.utils.Util;
import com.weiaibenpao.demo.chislim.customs.UploadProgressDialog;
import com.weiaibenpao.demo.chislim.music.bean.Bean_music;
import com.weiaibenpao.demo.chislim.music.dao.Dao_Get_music;
import com.weiaibenpao.demo.chislim.music.service.Start_Service;
import com.weiaibenpao.demo.chislim.util.ACache;
import com.weiaibenpao.demo.chislim.util.BluDataUtil;
import com.weiaibenpao.demo.chislim.util.DateUtil;
import com.weiaibenpao.demo.chislim.util.Default;
import com.weiaibenpao.demo.chislim.util.LatLong_impl;
import com.weiaibenpao.demo.chislim.util.RippleView;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SportHomeDisSpiceActivity extends Activity {

    BluetoothAdapter mAdapter;             //蓝牙适配器

    @BindView(R.id.map)
    MapView map;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.stateText)
    TextView stateText;
    @BindView(R.id.stateImg)
    ImageView stateImg;
    @BindView(R.id.mode)
    LinearLayout mode;
    @BindView(R.id.heart)
    TextView heart;
    @BindView(R.id.speedAll)
    TextView speedAll;
    @BindView(R.id.cor)
    TextView cor;
    @BindView(R.id.topLine)
    LinearLayout topLine;
    @BindView(R.id.dis)
    TextView dis;
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.nextLine)
    LinearLayout nextLine;
    @BindView(R.id.upMusic)
    ImageView upMusic;
    @BindView(R.id.startSportMusic)
    ImageView startSportMusic;
    @BindView(R.id.nextMusic)
    ImageView nextMusic;
    @BindView(R.id.musicName)
    TextView musicName;
    @BindView(R.id.myMusic)
    LinearLayout myMusic;
    @BindView(R.id.mySpeedAdd)
    ImageView mySpeedAdd;
    @BindView(R.id.speedText)
    TextView speedText;
    @BindView(R.id.mySpeedMinu)
    ImageView mySpeedMinu;
    @BindView(R.id.speedLayout)
    LinearLayout speedLayout;
    @BindView(R.id.mySlopeAdd)
    ImageView mySlopeAdd;
    @BindView(R.id.slopeText)
    TextView slopeText;
    @BindView(R.id.mySlopeMinu)
    ImageView mySlopeMinu;
    @BindView(R.id.slopeLayout)
    LinearLayout slopeLayout;
    @BindView(R.id.root_rv)
    RippleView rootRv;
    @BindView(R.id.root_tv)
    TextView rootTv;

    private PopupWindow mPopupWindow;
    private ListView listView;
    private static final String TAG = "蓝牙";
    private ArrayList bluList;
    private ArrayList<EntityDevice> list;
    Intent intentService;
    DeviceAdapter deviceAdapter;

    private MsgReceiver receiver;
    BluetoothController controller = BluetoothController.getInstance();
    BluResult bluResult;
    Util u = new Util();
    BluDataUtil bluDataUtil = new BluDataUtil();

    public int bluState = 0;
    public double sportDis;   //运动里程
    public String sportTime;   //运动时间
    public int sportCor;    //消耗卡路里
    public float minSpeed;    //最小速度
    public float maxSpeed;      //最大速度
    public int maxSlope;     //最大坡度

    MyBroadcast myBroadcast;  //广播对象
    boolean flag = false;
    private Intent intent;
    private Bean_music music;           //当前播放歌曲对象
    private Start_Service start_service;  //服务对象
    int pross;     //进度数据
    private int postion;  //当前播放歌曲的序列号

    //获取本地歌曲数据源
    private Dao_Get_music get_music;
    //用来存放本地歌曲
    private ArrayList localMusicList;


    AMap aMap;

    LatLong_impl latLong_impl;   //用来获取系统日期
    DecimalFormat decimalFormat;

    WindowManager wm;
    int widthScreen;                  //获取手屏幕跨度
    int heightScreen;
    boolean screenFlag = false;         //是否地图大屏

    Intent myIntent;
    int disPlan;// 计划跑步距离

    //快捷速度
    ImageView imgThree;
    ImageView imgFive;
    ImageView imgSeven;
    ImageView imgNine;
    ImageView imgTen;
    ImageView imgTravel;
    private PopupWindow mPopupSpeed;

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
    int disOld;               //运动记录
    UploadProgressDialog progressDialog;

    String imageUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport_home_dis_spice);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this , getResources().getColor(R.color.ku_bg ) , 0);
        context = getApplicationContext();
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，实现地图生命周期管理
        map.onCreate(savedInstanceState);// 此方法必须重写

        arrayList = new ArrayList();
        myLatLong = new ArrayList();

        list = new ArrayList<EntityDevice>();

        mCache = ACache.get(context);    //实例化缓存
        progressDialog = new UploadProgressDialog(this,"正在加载数据中...");
        progressDialog.showDialog();
        //第一步获取起点坐标和终点坐标
        initDataIntent();
        //第二部实例化地图
        initMap();
        //第三步，路径规划，并绘提前制路线图
        SearchLine(fromLat, fromLong, toLat, toLong);
        //第四步，重绘制轨迹


        //getCache();           //获取运动记录
        initReceiver2();          //蓝牙模块状态
        initRipple();
        initService();
        initData();
        registerReceiver();
        MyConTrol(0);


        //配置广播,音乐
        myBroadcast = new MyBroadcast();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.lijianbao.cn");
        SportHomeDisSpiceActivity.this.registerReceiver(myBroadcast, filter);

        latLong_impl = new LatLong_impl(context);

        decimalFormat = new DecimalFormat("0.0");//构造方法的字符格式这里如果小数不足2位,会以0补足.

        myIntent = getIntent();
        disPlan = myIntent.getIntExtra("dis", 1000);
    }

    /**
     * 获取传过来的坐标点
     */
    public void initDataIntent() {
        //Intent getIntent = getIntent();

        /*firstCity = getIntent.getStringExtra("fistCity");
        secondCity = getIntent.getStringExtra("secondCity");
        disNow = getIntent.getIntExtra("disNow",0);
        position = getIntent.getIntExtra("position",0);
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
        aMap.setTrafficEnabled(true);           // 显示实时交通状况
        //地图模式可选类型：MAP_TYPE_NORMAL,MAP_TYPE_SATELLITE,MAP_TYPE_NIGHT
        aMap.setMapType(AMap.LOCATION_TYPE_MAP_FOLLOW);// 地图模式

        wm = this.getWindowManager();

        widthScreen = wm.getDefaultDisplay().getWidth();
        heightScreen = wm.getDefaultDisplay().getHeight();
        aMap.setOnMapClickListener(new AMap.OnMapClickListener() {
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
        });
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
                        aMap.clear();// 清理地图上的所有覆盖物

                        DrivingRouteOverlay drivingRouteOverlay = new DrivingRouteOverlay(context,
                                aMap, drivePath, driveRouteResult.getStartPos(),
                                driveRouteResult.getTargetPos());

                        drivingRouteOverlay.removeFromMap();
                        drivingRouteOverlay.addToMap();
                        drivingRouteOverlay.zoomToSpan();

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
                        //取消dialog
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
                Log.i("错误码", "----------" + i + "----------" );
            }

            @Override
            public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

            }
        });
    }

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
        clearMarkers(); // 清除marker
        myLatLong.clear();

        if (dis >= distance) {
            h = pos;
        }else{
            h  = (int) (pos * (dis / distance));
        }
        for (int m = 0; m < h; m++) {
            setIntro(((LatLng)arrayList.get(m)).latitude,((LatLng)arrayList.get(m)).longitude);            //设置地图中心点
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
        // }
        // polylineOptions.add((LatLng) arrayList.get(h));
        polylineOptions.addAll(myLatLong);
        polylineOptions.visible(true).width(20).zIndex(200);
        // 加入对应的颜色,使用colorValues 即表示使用多颜色，使用color表示使用单色线
        // polylineOptions.colorValues(WalkUtil.getColorList(myLatLong.size()/144+1,this));
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
     */
    public void setIntro(double latNow,double longNow) {
        LatLng marker1 = new LatLng(latNow, longNow);
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
     * 销毁
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        map.onDestroy();

        stopService(intentService);
        unregisterReceiver(receiver);
        unregisterReceiver(myBroadcast);
        unregisterReceiver(bluetoothState);
    }

    @OnClick(R.id.back)
    public void onClick() {
        finish();
    }

    @OnClick({R.id.upMusic, R.id.startSportMusic, R.id.nextMusic, R.id.mySpeedAdd, R.id.mySpeedMinu, R.id.mySlopeAdd, R.id.mySlopeMinu, R.id.root_rv, R.id.root_tv, R.id.addFastSpeed, R.id.lowFastSpeed})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.upMusic:
                start_service.up();
                break;
            case R.id.startSportMusic:
                if (start_service.getState() == true) {
                    start_service.pause();
                } else {
                    start_service.play(pross, music, postion);
                }
                break;
            case R.id.nextMusic:
                start_service.next();
                break;
            case R.id.mySpeedAdd:
                Log.i(TAG, "速度增加");
                float speed1 = Float.parseFloat(speedText.getText().toString());
                Log.i(TAG, "速度增加" + speed1 + "---" + maxSpeed);
                if (speed1 < maxSpeed) {
                    speed1 = speed1 + (float) 0.1;
                } else {
                    speed1 = maxSpeed;
                }
                Log.i(TAG, "速度增加" + speed1 + "---" + maxSpeed);
                controller.write(bluDataUtil.addSpeed(decimalFormat.format(speed1)));
                break;
            case R.id.mySpeedMinu:
                Log.i(TAG, "速度减少");
                float speed2 = Float.parseFloat(speedText.getText().toString());
                if (speed2 > minSpeed) {
                    speed2 = speed2 - (float) 0.1;
                } else {
                    speed2 = minSpeed;
                }
                controller.write(bluDataUtil.addSpeed(decimalFormat.format(speed2)));
                break;
            case R.id.mySlopeAdd:
                Log.i(TAG, "坡度增加");
                int slope1 = Integer.parseInt(slopeText.getText().toString());
                if (slope1 < maxSlope) {
                    slope1 = slope1 + 1;
                } else {
                    slope1 = maxSlope;
                }
                controller.write(bluDataUtil.addSlope(slope1 + "")); //坡度增加
                break;
            case R.id.mySlopeMinu:
                Log.i(TAG, "坡度减少");
                int slope2 = Integer.parseInt(slopeText.getText().toString());
                if (slope2 > 1) {
                    slope2 = slope2 - 1;
                } else {
                    slope2 = 0;
                }
                controller.write(bluDataUtil.addSlope(slope2 + ""));   //坡度减少
                break;
            case R.id.root_rv:
                break;
            case R.id.root_tv:
                if (mAdapter == null) {
                    mAdapter = BluetoothAdapter.getDefaultAdapter();
                }
                if (!mAdapter.isEnabled()) {                       //判断蓝牙是否打开
                    initReceiver2();
                    //弹出对话框提示用户是后打开
                    Intent enabler = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivityForResult(enabler, Default.FIND);
                    //不做提示，强行打开
                    // mAdapter.enable();
                }

                if (mAdapter.isEnabled() && bluState == 0) {                //判断蓝牙是否连接
                    list.clear();//清空以前发现的蓝牙
                    deviceAdapter.notifyDataSetChanged();
                    if (!BluetoothController.getInstance().initBLE()) {///手机不支持蓝牙
                        Toast.makeText(SportHomeDisSpiceActivity.this, "您的手机不支持蓝牙",
                                Toast.LENGTH_SHORT).show();
                        return;//手机不支持蓝牙就啥也不用干了，关电脑睡觉去吧
                    }
                    new GetDataTask().execute();// 搜索任务

                    //-------------------------------------------------------------------------
                    //Log.i("蓝牙",device.toString());
                    start();                     //水波纹启动,运行一下
                }
                if (bluState == 10) {               //正在运转
                    controller.write(bluDataUtil.startStop("1"));     //启动

                } else if (bluState == 20) {       //正在停止
                    //DecimalFormat myFormatter = new DecimalFormat("0");
                    /*if (sportDis >= 1) {
                        sexDialog(sportDis, sportCor, Integer.parseInt(myFormatter.format(Double.parseDouble(sportTime))));      //弹框，显示本次运动数据
                    } else {
                        getDialog(sportDis, sportCor, Integer.parseInt(myFormatter.format(Double.parseDouble(sportTime))));      //弹框，显示本次运动数据
                    }*/
                   // String fistCity, String secondCity, double fromLat, double fromLong, double toLat, double toLong, int position, int disNow
                    getScreen();
                    mCache.put("sportSpice",new SpiceSportRemberBean(firstCity,secondCity,fromLat,fromLong,toLat,toLong,position,disNow));
                    controller.write(bluDataUtil.startStop("0"));         //停止
                   /* Intent intent = new Intent(SportHomeDisSpiceActivity.this,SportHomeRemberActivity.class);
                    intent.putExtra("sportDis",sportDis);
                    intent.putExtra("sportTime",sportTime);
                    intent.putExtra("sportCor",sportCor);
                    startActivity(intent);
                    finish();*/

                }
                break;
            case R.id.addFastSpeed:
                controller.write(bluDataUtil.addSpeed(String.valueOf(0.6)));
                break;
            case R.id.lowFastSpeed:
                showSpeedPopwindow(getWindow().getDecorView());
                break;
        }
    }


    //注册广播，用于监听蓝牙状态
    public void initReceiver2() {
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(bluetoothState, filter);
        filter = new IntentFilter(BluetoothAdapter.ACTION_STATE_CHANGED);
        registerReceiver(bluetoothState, filter);
    }

    //接收蓝牙模块状态广播
    BroadcastReceiver bluetoothState = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String stateExtra = BluetoothAdapter.EXTRA_STATE;
            int state = intent.getIntExtra(stateExtra, -1);
            switch (state) {
                case BluetoothAdapter.STATE_TURNING_ON:
                    Log.i("蓝牙", "蓝牙模块正在打开");
                    break;
                case BluetoothAdapter.STATE_ON:
                    Log.i("蓝牙", "蓝牙已打开");

                    list.clear();//清空以前发现的蓝牙
                    deviceAdapter.notifyDataSetChanged();
                    if (!BluetoothController.getInstance().initBLE()) {///手机不支持蓝牙
                        Toast.makeText(SportHomeDisSpiceActivity.this, "您的手机不支持蓝牙",
                                Toast.LENGTH_SHORT).show();
                        return;//手机不支持蓝牙就啥也不用干了，关电脑睡觉去吧
                    }
                    new GetDataTask().execute();// 搜索任务

                    //-------------------------------------------------------------------------
                    //Log.i("蓝牙",device.toString());
                    start();                     //水波纹启动,运行一下
                    MyConTrol(1);
                    break;
                case BluetoothAdapter.STATE_TURNING_OFF:
                    Log.i("蓝牙", "蓝牙模块正在关闭");
                    stop();
                    break;
                case BluetoothAdapter.STATE_OFF:
                    Log.i("蓝牙", "蓝牙模块处于关闭状态");
                    stop();
                    break;
            }
        }
    };

    //修改app界面水波球上的文字
    public void upTextStayle(String str1, String str2) {
        rootTv.setText(str1);
    }


    //实例化水波球
    public void initRipple() {

/** 设置空心内圆颜色 */
        rootRv.setInStrokePaintColor(getResources().getColor(R.color.backThem));
        /** 设置实心内圆颜色 */
        rootRv.setInPaintColor(getResources().getColor(R.color.backThem));
        /** 设置空心外圆颜色 */
        rootRv.setOutStrokePaintColor(getResources().getColor(R.color.backThem));

        //设置动画次数
        rootRv.setRepeatCount(20);

        //设置动画时间
        rootRv.setDuration(3000);

        //水波纹状态监听
        rootRv.setRippleStateListener(new RippleView.RippleStateListener() {
            @Override
            public void startRipple() {

            }

            @Override
            public void stopRipple() {

            }

            @Override
            public void onRippleUpdate(ValueAnimator animation) {

            }
        });
    }


    //开始动画
    public void start() {
        rootRv.startRipple();
    }

    //结束动画
    public void stop() {
        rootRv.stopRipple();
    }

    /**
     * 0--9 有坡度的跑步机
     * 10--19  没坡度的跑步机
     *
     * @param stateNum
     */
    public void MyConTrol(int stateNum) {
        switch (stateNum) {
            case 0:                                           //蓝牙未开启状态
                speedLayout.setVisibility(View.GONE);
                slopeLayout.setVisibility(View.GONE);
                stateImg.setBackgroundColor(getResources().getColor(R.color.red_yuan));
                break;
            case 1:                                           //蓝牙未连接状态
                speedLayout.setVisibility(View.GONE);
                slopeLayout.setVisibility(View.GONE);
                stateImg.setBackgroundColor(getResources().getColor(R.color.yellow_bg));
                break;
            case 2:                                           //蓝牙连接成功状态
                speedLayout.setVisibility(View.VISIBLE);
                slopeLayout.setVisibility(View.VISIBLE);
                stateImg.setBackgroundColor(getResources().getColor(R.color.green_yuan));
                break;
            case 3:
                break;
            case 4:
                break;
            case 10:
                speedLayout.setVisibility(View.GONE);
                slopeLayout.setVisibility(View.GONE);
                break;
            case 11:
                break;
            case 12:
                break;
            case 13:
                break;
            case 14:
                Log.i("蓝牙", "连接成功！");
                break;
        }
    }

    /**
     * 更新界面
     */
    int disTab = 0;
    public void changeActivity(BluResult bluResult) {
        speedText.setText(bluResult.speed + "");
        slopeText.setText(bluResult.slope + "");
        time.setText(bluResult.time + "");
        cor.setText(bluResult.calories + "");
        heart.setText(bluResult.heart + "");
        dis.setText(bluResult.distance + "");

        if(disTab != (int)(Double.parseDouble(bluResult.distance)* 1000)){
            huizhi((int)(Double.parseDouble(bluResult.distance) * 1000));
            //huizhi(sportDis*1000);
            disTab = (int)(Double.parseDouble(bluResult.distance)* 1000);
        }

        if (bluState == 20) {
            upTextStayle("停止", "蓝牙已连接");
            rootRv.setInStrokePaintColor(getResources().getColor(R.color.green_yuan));
            /** 设置实心内圆颜色 */
            rootRv.setInPaintColor(getResources().getColor(R.color.green_yuan));
            /** 设置空心外圆颜色 */
            rootRv.setOutStrokePaintColor(getResources().getColor(R.color.green_yuan));
        }

        if (bluState == 30) {
            upTextStayle("正在加速", "蓝牙已连接");
            rootRv.setInStrokePaintColor(getResources().getColor(R.color.track_line_55));
            /** 设置实心内圆颜色 */
            rootRv.setInPaintColor(getResources().getColor(R.color.track_line_55));
            /** 设置空心外圆颜色 */
            rootRv.setOutStrokePaintColor(getResources().getColor(R.color.track_line_55));
        }

        if (bluState == 40) {
            upTextStayle("正在减速", "蓝牙已连接");
            rootRv.setInStrokePaintColor(getResources().getColor(R.color.track_line_55));
            /** 设置实心内圆颜色 */
            rootRv.setInPaintColor(getResources().getColor(R.color.track_line_55));
            /** 设置空心外圆颜色 */
            rootRv.setOutStrokePaintColor(getResources().getColor(R.color.track_line_55));
        }

        if (bluState == 10) {
            upTextStayle("开始", "蓝牙已连接");
            rootRv.setInStrokePaintColor(getResources().getColor(R.color.red_yuan));
            /** 设置实心内圆颜色 */
            rootRv.setInPaintColor(getResources().getColor(R.color.red_yuan));
            /** 设置空心外圆颜色 */
            rootRv.setOutStrokePaintColor(getResources().getColor(R.color.red_yuan));
        }
        MyConTrol(2);
        stop();
    }


    /**
     * 显示蓝牙搜索结果
     *
     * @param parent
     */
    private void showPopwindow(View parent, final ArrayList bluList) {

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;

        if (mPopupWindow == null) {
            LayoutInflater mLayoutInflater = LayoutInflater.from(this);
            View contentView = mLayoutInflater.inflate(R.layout.group_list, null);
            listView = (ListView) contentView.findViewById(R.id.lv_group);

            deviceAdapter = new DeviceAdapter(this, bluList);
            listView.setAdapter(deviceAdapter);

            mPopupWindow = new PopupWindow(contentView, screenWidth / 1, screenHeight / 3);
        }
        mPopupWindow.setFocusable(true);
        mPopupWindow.setOutsideTouchable(true);  //设置点击屏幕其它地方弹出框消失
        mPopupWindow.setBackgroundDrawable(new BitmapDrawable());

        // 显示的位置为:屏幕的宽度的1/16
        // mPopupWindow.showAsDropDown(parent, screenWidth / 1, screenHeight/2);
        mPopupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        //蓝牙设备选择连接，点击列表中的蓝牙名称，连接对应设备
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                if (BluetoothController.reBtGatt() == null) {
                    //连接设备
                    BluetoothController.getInstance().connect((EntityDevice) bluList.get(position));
                } else {
                    BluetoothController.clearBtGatt();
                    BluetoothController.getInstance().connect((EntityDevice) bluList.get(position));
                }

                start();
                upTextStayle("蓝牙正在连接", "蓝牙正在连接");
                if (mPopupWindow != null) {
                    mPopupWindow.dismiss();
                }
            }
        });
    }

    private void initData() {
        bluList = new ArrayList();
        deviceAdapter = new DeviceAdapter(this, list);
        music = new Bean_music();

        get_music = new Dao_Get_music(context);
        localMusicList = new ArrayList();
        localMusicList = get_music.getMusic_Array();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (intent == null && localMusicList.size() > 0) {
            intent = new Intent(SportHomeDisSpiceActivity.this, Start_Service.class);
            Bundle bundle = new Bundle();
            if (!isServiceRunning(context, "com.weiaibenpao.demo.chislim.music.service.Start_Service")) {

                //getFraMusic();   //获取本地歌曲数据源
                bundle.putParcelableArrayList("music", localMusicList);
                intent.putExtras(bundle);
                startService(intent);
            }

            bundle.putInt("postion", 0);
            getApplicationContext().bindService(intent, connection, Context.BIND_AUTO_CREATE);
        }
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
     * 实例化服务
     */
    private void initService() {
        //开始服务
        intentService = new Intent(SportHomeDisSpiceActivity.this, BLEService.class);
        startService(intentService);
        //初始化蓝牙
        BluetoothController.getInstance().initBLE();
    }

    /**
     * 注册广播
     */
    private void registerReceiver() {
        receiver = new MsgReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConstantUtils.ACTION_UPDATE_DEVICE_LIST);
        intentFilter.addAction(ConstantUtils.ACTION_CONNECTED_ONE_DEVICE);
        intentFilter.addAction(ConstantUtils.ACTION_RECEIVE_MESSAGE_FROM_DEVICE);
        intentFilter.addAction(ConstantUtils.ACTION_STOP_CONNECT);
        registerReceiver(receiver, intentFilter);
    }


    private class GetDataTask extends AsyncTask<Void, Void, String[]> {

        @Override
        protected String[] doInBackground(Void... params) {
            if (BluetoothController.getInstance().isBleOpen()) {
                BluetoothController.getInstance().startScanBLE();
            }
            ;// 开始扫描
            return null;
        }

        @Override
        protected void onPostExecute(String[] result) {
            super.onPostExecute(result);
        }
    }

    /**
     * 接收广播，接收蓝牙模块发过来的值
     */
    public class MsgReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equalsIgnoreCase(
                    ConstantUtils.ACTION_UPDATE_DEVICE_LIST)) {
                String name = intent.getStringExtra("name");
                String address = intent.getStringExtra("address");
                boolean found = false;                                 //记录该条记录是否已在list中，
                for (EntityDevice device : list) {
                    if (device.getAddress().equals(address)) {
                        found = true;
                        break;
                    }
                }// for
                if (!found) {                                                       //发现蓝牙
                    EntityDevice temp = new EntityDevice();
                    temp.setName(name);
                    temp.setAddress(address);
                    list.add(temp);
                    showPopwindow(getWindow().getDecorView(), list);              //显示蓝牙列表
                    stop();
                    deviceAdapter.notifyDataSetChanged();
                }
            } else if (intent.getAction().equalsIgnoreCase(ConstantUtils.ACTION_CONNECTED_ONE_DEVICE)) {
                // connectedDevice.setText("连接的蓝牙是："+intent.getStringExtra("address"));
            } else if (intent.getAction().equalsIgnoreCase(ConstantUtils.ACTION_STOP_CONNECT)) {
                // connectedDevice.setText("");
                toast("连接已断开");
            } else if (intent.getAction().equalsIgnoreCase(ConstantUtils.ACTION_RECEIVE_MESSAGE_FROM_DEVICE)) {
                //receivedMessage.append(u.toStringHex(intent.getStringExtra("message")).trim());
                //Log.i("蓝牙数据",u.toStringHex(intent.getStringExtra("message")).trim());
                bluResult = bluDataUtil.SplitStr(u.toStringHex(intent.getStringExtra("message")).trim(), context);
                if (bluResult != null) {
                    if (bluResult.code.equals("0")) {
                        bluState = Integer.parseInt(bluResult.state);
                        sportDis = Double.parseDouble(bluResult.distance);
                        sportCor = Integer.parseInt(bluResult.calories);
                        maxSpeed = (int) (Double.parseDouble(bluResult.maxSpeed));      //最大速度
                        maxSlope = Integer.parseInt(bluResult.maxSlope);                 //最大坡度
                        minSpeed = Float.parseFloat(bluResult.minSpeed);                 //最小速度
                        sportTime = bluResult.time;
                        changeActivity(bluResult);                                           ///更新界面
                    }
                }
            }
        }
    }

    private void toast(String str) {
        Toast.makeText(SportHomeDisSpiceActivity.this, str, Toast.LENGTH_SHORT).show();
    }

    /**
     * 内部类实现进度条和界面动态显示,音乐播放
     */
    public class MyBroadcast extends BroadcastReceiver {

        public MyBroadcast() {

        }

        @Override
        public void onReceive(Context context, Intent intent) {
            //获取当前实时进度
            pross = intent.getIntExtra("pross", 0);
            //获取当前播放歌曲序列号
            postion = intent.getIntExtra("position", 0);
            //返回当前播放状态
            flag = intent.getBooleanExtra("isPlaying", false);

            music = intent.getParcelableExtra("musicBean");

            if (flag) {
                Picasso.with(context).load(R.mipmap.stop).into(startSportMusic);
            } else {
                Picasso.with(context).load(R.mipmap.start).into(startSportMusic);
            }
            musicName.setText(music.getMusic_title());
        }
    }

    /**
     * 获取服务对象
     */
    public ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Start_Service.My_binder binder = (Start_Service.My_binder) service;
            start_service = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            start_service.onDestroy();
        }
    };




    /**
     * 点击停止按钮弹框，跑步距离少于一千米，不上传数据，询问是否继续
     */
    public void getDialog(final int dis, final int cor, final int time) {
        View view = LayoutInflater.from(context).inflate(R.layout.update_homesportdata, null);
        ((TextView) view.findViewById(R.id.versionCode)).setText("跑步记录");
        ((TextView) view.findViewById(R.id.versionText)).setText("距离:" + dis + "\n" + "当期跑步距离少于 1 千米，无法保存记录");

        final PopupWindow popWnd = new PopupWindow(context);
        popWnd.setContentView(view);
        popWnd.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popWnd.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popWnd.setOutsideTouchable(true);  //设置点击屏幕其它地方弹出框消失
        //View rootview = LayoutInflater.from(context).inflate(R.id.root_tv, null);
        //相对于父控件的位置（例如正中央Gravity.CENTER，下方Gravity.BOTTOM等），可以设置偏移或无偏移
        popWnd.showAtLocation(findViewById(R.id.root_tv), Gravity.CENTER, 0, 0);
        ((TextView) view.findViewById(R.id.startBut)).setText("放弃");
        view.findViewById(R.id.startBut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                controller.write(bluDataUtil.startStop("0"));         //停止
                //GetIntentData intentData = new GetIntentData();
                //intentData.GetSportData(context, "addOne", UserBean.getUserBean().userId, latLong_impl.getDate(), dis, cor, time, "钻石", 55);
                popWnd.dismiss();
            }
        });
        view.findViewById(R.id.againBut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popWnd.dismiss();
            }
        });
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    /**
     * 显示快捷操作结果
     *
     * @param parent
     *
     */
    private void showSpeedPopwindow(View parent) {

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;

        if (mPopupSpeed == null) {
            LayoutInflater mLayoutInflater = LayoutInflater.from(this);
            View contentView = mLayoutInflater.inflate(R.layout.pop_blucontrol, null);
            imgThree = (ImageView) contentView.findViewById(R.id.speedThree);
            imgFive = (ImageView) contentView.findViewById(R.id.speedFive);
            imgSeven = (ImageView) contentView.findViewById(R.id.speedSeven);
            imgNine = (ImageView) contentView.findViewById(R.id.speedNine);
            imgTen = (ImageView) contentView.findViewById(R.id.speedTen);
            imgTravel = (ImageView) contentView.findViewById(R.id.speedTravel);

            imgThree.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    controller.write(bluDataUtil.addSpeed(String.valueOf(3)));
                    mPopupSpeed.dismiss();
                }
            });
            imgFive.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    controller.write(bluDataUtil.addSpeed(String.valueOf(5)));
                    mPopupSpeed.dismiss();
                }
            });
            imgSeven.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    controller.write(bluDataUtil.addSpeed(String.valueOf(7)));
                    mPopupSpeed.dismiss();
                }
            });
            imgNine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    controller.write(bluDataUtil.addSpeed(String.valueOf(9)));
                    mPopupSpeed.dismiss();
                }
            });
            imgTen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    controller.write(bluDataUtil.addSpeed(String.valueOf(10)));
                    mPopupSpeed.dismiss();
                }
            });
            imgTravel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    controller.write(bluDataUtil.addSpeed(String.valueOf(12)));
                    mPopupSpeed.dismiss();
                }
            });
            mPopupSpeed = new PopupWindow(contentView, screenWidth / 1, screenHeight / 3);
        }
        mPopupSpeed.setFocusable(true);
        mPopupSpeed.setOutsideTouchable(true);  //设置点击屏幕其它地方弹出框消失
        mPopupSpeed.setBackgroundDrawable(new BitmapDrawable());

        // 显示的位置为:屏幕的宽度的1/16
        // mPopupSpeed.showAsDropDown(parent, screenWidth / 1, screenHeight/2);
        mPopupSpeed.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
    }


    //存储地图截屏
    public void cutMapScreen000() {

        aMap.getMapScreenShot(new AMap.OnMapScreenShotListener() {
            @Override
            public void onMapScreenShot(Bitmap bitmap) {

            }

            @Override
            public void onMapScreenShot(Bitmap bitmap, int status) {
                if (null == bitmap) {
                    return;
                }
                try {
                    imageUrl = Environment.getExternalStorageDirectory() + "/qms" + DateUtil.getTime() + ".png";
                    FileOutputStream fos = new FileOutputStream(imageUrl);
                    boolean b = bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    try {
                        fos.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    StringBuffer buffer = new StringBuffer();
                    if (b) {
                        buffer.append("截屏成功 ");
                    } else {
                        buffer.append("截屏失败 ");
                    }
                    if (status != 0) {
                        buffer.append("地图渲染完成，截屏无网格");
                        goIntent();
                    } else {
                        buffer.append("地图未渲染完成，截屏有网格");
                    }
                    Log.i("截屏", "截屏文件已保存至" + imageUrl);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //存储地图截屏
    public void cutMapScreen() {

        aMap.getMapScreenShot(new AMap.OnMapScreenShotListener() {
            @Override
            public void onMapScreenShot(Bitmap bitmap) {

            }

            @Override
            public void onMapScreenShot(Bitmap bitmap, int status) {
                if (null == bitmap) {
                    return;
                }
                try {
                    imageUrl = Environment.getExternalStorageDirectory() + "/qms" + DateUtil.getTime() + ".png";
                    FileOutputStream fos = new FileOutputStream(imageUrl);
                    boolean b = bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
                    try {
                        fos.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    StringBuffer buffer = new StringBuffer();
                    if (b) {
                        buffer.append("截屏成功 ");
                    } else {
                        buffer.append("截屏失败 ");
                    }
                    if (status != 0) {
                        buffer.append("地图渲染完成，截屏无网格");
                        goIntent();
                    } else {
                        buffer.append("地图未渲染完成，截屏有网格");
                    }
                    Log.i("截屏", "截屏文件已保存至" + imageUrl);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getScreen() {
        // saveCurrentImage();          //Activity截屏

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("截屏", "截屏文件已保存至");
                //开一个线程来截图，就不会出现屏幕卡顿的现象 /storage/emulated/0/test_20161220155212.png   /storage/emulated/0/Test_ScreenShots/Screenshot_2017-01-10-11-34-54.png
                cutMapScreen();             //地图截屏
            }
        }).start();
    }


    public void goIntent() {
        Intent intent = new Intent(SportHomeDisSpiceActivity.this, SportHomeRemberActivity.class);
        intent.putExtra("sportDis", sportDis);
        intent.putExtra("sportTime", sportTime);
        intent.putExtra("sportCor", sportCor);
        intent.putExtra("imageUrl", imageUrl);
        startActivity(intent);
        finish();
    }
}
