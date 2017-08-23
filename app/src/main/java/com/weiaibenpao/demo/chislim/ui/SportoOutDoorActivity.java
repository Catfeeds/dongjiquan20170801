package com.weiaibenpao.demo.chislim.ui;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.PolylineOptions;
import com.jaeger.library.StatusBarUtil;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.service.Map_Service;
import com.weiaibenpao.demo.chislim.sportoutdoor.data.model.database.core.PedometerCardEntity;
import com.weiaibenpao.demo.chislim.sportoutdoor.presentation.common.utils.HardwarePedometerUtil;
import com.weiaibenpao.demo.chislim.sportoutdoor.presentation.module.ApplicationModule;
import com.weiaibenpao.demo.chislim.sportoutdoor.presentation.presenter.PedometerPresenter;
import com.weiaibenpao.demo.chislim.sportoutdoor.presentation.view.component.WindmillView;
import com.weiaibenpao.demo.chislim.sportoutdoor.presentation.view.iview.IPedometerView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SportoOutDoorActivity extends Activity implements IPedometerView {

    PedometerPresenter mPedometerPresenter;
    @BindView(R.id.btn_start)
    Button btnStart;
    @BindView(R.id.tv_steps)
    TextView tvSteps;
    @BindView(R.id.tv_target_steps)
    TextView tvTargetSteps;
    @BindView(R.id.view_windmill)
    WindmillView viewWindmill;

    @BindView(R.id.dis)
    TextView dis_text;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.myCalories)
    TextView myCalories;
    @BindView(R.id.myTime)
    TextView myTime;
    @BindView(R.id.textWord)
    TextView textWord;
    @BindView(R.id.sportType)
    TextView sportType;
    @BindView(R.id.btn_goto)
    Button btnGoto;
    @BindView(R.id.showView)
    LinearLayout showView;
    @BindView(R.id.timeView)
    RelativeLayout timeView;
    @BindView(R.id.startView)
    RelativeLayout startView;
    @BindView(R.id.titleTv)
    TextView titleTv;
    @BindView(R.id.sportTime)
    Chronometer sportTime;
    @BindView(R.id.map)
    MapView map;

    //private MapView mMapView = null;
    private AMap aMap;   //定义一个地图对象
    private UiSettings mUiSettings;//定义一个UiSettings对象

    private AMapLocationClient locationClient = null;
    private AMapLocationClientOption locationOption = new AMapLocationClientOption();

    private LocationSource.OnLocationChangedListener mListener;

    private AMapLocation aml;
    private PolylineOptions polylineOptions = new PolylineOptions();

    private boolean mIsStart = false;

    private Map_Service map_Service;  //服务对象

    private int mProgress;
    //起始点
    LatLng start;
    //终点
    LatLng end;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //让一个activity 浮在锁屏界面的上方，返回即进入解锁界面
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        //设置屏幕常亮
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //设置窗体背景模糊
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND, WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        setContentView(R.layout.activity_sporto_out_door);
        StatusBarUtil.setColor(this , getResources().getColor(R.color.ku_bg ) , 0);
        ButterKnife.bind(this);
       /* map.onCreate(savedInstanceState);// 此方法必须重写
        init();

        initLocation();
        startLocation();*/

        checkPerssion();
    }


    private void init() {
        mPedometerPresenter = new PedometerPresenter(this);
        mPedometerPresenter.resume();
        if (HardwarePedometerUtil.supportsHardwareAccelerometer(this)) {                           //判断是否支持Accelerometer算法

        } else {

        }
        if (HardwarePedometerUtil.supportsHardwareStepCounter(this)) {                             //判断是否支持Step_counter算法

        } else {

        }

        //开始动画，风扇动画
        viewWindmill.startAnim();


        //以下为地图及定位
        if (aMap == null) {
            aMap = map.getMap();
        }

        // 自定义系统定位小蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory
                .fromResource(R.drawable.local2));// 设置小蓝点的图标
        myLocationStyle.strokeColor(Color.BLACK);// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(30, 180, 0, 180));// 设置圆形的填充颜色
        myLocationStyle.strokeWidth(0.05f);// 设置圆形的边框粗细
        aMap.setMyLocationStyle(myLocationStyle);

        //设置缩放比例
        aMap.moveCamera(CameraUpdateFactory.zoomTo(18));

        //实例化UiSettings类
        mUiSettings = aMap.getUiSettings();
        /**
         * 可以控制地图的缩放级别，每次点击改变1个级别，
         * 此控件默认打开，可以通过UiSettings.setZoomControlsEnabled(boolean)接口控制此控件的显示和隐藏。
         */
        mUiSettings.setZoomControlsEnabled(true);   //缩放
        mUiSettings.setCompassEnabled(true);        //指南针

        final boolean[] flag = {mUiSettings.isCompassEnabled()};      //指南针控件
        mUiSettings.isMyLocationButtonEnabled();             //定位控件
        mUiSettings.isZoomControlsEnabled();                 //缩放控件
        mUiSettings.isZoomGesturesEnabled();                 //缩放控件
        mUiSettings.isScrollGesturesEnabled();               //滑动控件

        // 设置定位监听,点击地图上的定位按钮触发
        LocationSource locationSource = new LocationSource() {
            @Override
            public void activate(OnLocationChangedListener onLocationChangedListener) {

                mListener = onLocationChangedListener;
                if (locationClient == null) {

                    locationOption = new AMapLocationClientOption();
                    locationOption.setInterval(3000);
                    locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Battery_Saving);
                    locationClient = new AMapLocationClient(getApplicationContext());
                    locationClient.setLocationOption(locationOption);

                    /**
                     * 这里把locationOption发送打服务中，由服务起来保存，然后广播出来，在这里绘图.
                     * 或者把坐标发过去，在服务端保存，广播出来进行绘图
                     * 在服务端计算距离，时间，步数，然后广播
                     */

                    locationClient.setLocationListener(new AMapLocationListener() {
                        @Override
                        public void onLocationChanged(AMapLocation aMapLocation) {
                            if (aMapLocation != null) {
                                if (start == null) {
                                    end = new LatLng(aMapLocation.getLongitude(), aMapLocation.getLatitude());
                                }
                                end = start;
                                start = new LatLng(aMapLocation.getLongitude(), aMapLocation.getLatitude());


                                float dis = AMapUtils.calculateLineDistance(start, end);

                                aMapLocation.getLocationType();
                                if (dis < 150) {
                                    polylineOptions.add(new LatLng(end.latitude, end.longitude));

                                    //int dis1 = dis1+dis;
                                    dis_text.setText((int) dis + "----");
                                }
                                polylineOptions.width(5);     //轨迹的宽度
                                polylineOptions.color(Color.argb(255, 33, 255, 33));     //设置轨迹颜色
                                aMap.addPolyline(polylineOptions);

                                mListener.onLocationChanged(aMapLocation);
                            }
                        }
                    });
                    locationClient.startLocation();
                }
            }

            @Override
            public void deactivate() {
                mListener = null;
                if (locationClient != null) {
                    locationClient.stopLocation();
                    locationClient.onDestroy();
                }
                locationClient = null;
            }
        };
        aMap.setLocationSource(locationSource);

        mUiSettings.setMyLocationButtonEnabled(true); // 显示默认的定位按钮
        aMap.setMyLocationEnabled(true);// 可触发定位并显示定位层

        mUiSettings.setScaleControlsEnabled(true);//显示比例尺控件

        mUiSettings.setAllGesturesEnabled(true);    //所有手势
    }

    @Override
    public void onReaderPedometer(PedometerCardEntity cardEntity) {

        if (cardEntity != null) {
            //获取实时步数
            tvSteps.setText(cardEntity.getStepCount() + "步");
            //设置目标步数
            cardEntity.setTargetStepCount(10);
            //获取目标步数
            tvTargetSteps.setText("目标步数：" + cardEntity.getTargetStepCount() + "步");
            //当前进度
            mProgress = (int) (100 * cardEntity.getStepCount() / (cardEntity.getTargetStepCount() * 1.0f));
        }
    }

    @OnClick({R.id.btn_start, R.id.sportType, R.id.btn_goto, R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start:
                if (!mIsStart) {
                    mIsStart = true;
                    ApplicationModule.getInstance().getPedometerManager().startPedometerService();
                    //开始计时
                    sportTime.start();
                    btnStart.setText("停止");
                } else {
                    mIsStart = false;
                    ApplicationModule.getInstance().getPedometerManager().stopPedometerService();
                    aml = null;
                    //停止计时
                    sportTime.stop();
                    btnStart.setText("开始");
                    Intent intent = new Intent(SportoOutDoorActivity.this,MySportRemberActivity.class);
                    startActivity(intent);
                }
                break;
            case R.id.sportType:
                Intent intent = new Intent(SportoOutDoorActivity.this, SportStyleActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_goto:
                startView.setVisibility(View.GONE);
                timeView.setVisibility(View.VISIBLE);
                setTime();
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    //三秒倒计时
    public void setTime() {
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
                    myTime.setText(msg.arg1 + "");
                    if (msg.arg1 == 0) {
                        myTime.setText("go!");
                    } else if (msg.arg1 == -1) {
                        timeView.setVisibility(View.GONE);
                        showView.setVisibility(View.VISIBLE);
                        mIsStart = true;
                        ApplicationModule.getInstance().getPedometerManager().startPedometerService();
                        btnStart.setText("停止");

                        // 将计时器清零
                        sportTime.setBase(SystemClock.elapsedRealtime());
                        //开始计时
                        sportTime.start();
                    }
                    break;
            }
        }
    };


    /**
     * 默认的定位参数
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private AMapLocationClientOption getDefaultOption() {
        AMapLocationClientOption mOption = new AMapLocationClientOption();
        mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
        mOption.setGpsFirst(true);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
        mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
        mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
        mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是ture
        mOption.setOnceLocation(true);//可选，设置是否单次定位。默认是false
        mOption.setOnceLocationLatest(true);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
        AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
        return mOption;
    }

    /**
     * 销毁定位
     *
     * @author hongming.wang
     * @since 2.8.0
     */
    private void destroyLocation() {
        if (null != locationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            locationClient.onDestroy();
            locationClient = null;
            locationOption = null;
        }
    }

    /**
     * 重写该方法得到屏幕的坐标，在将屏幕坐标转换为地理坐标
     */
    @Override
    public boolean onTouchEvent(MotionEvent arg0) {
        // 获得点击屏幕的坐标
        int x = (int) arg0.getX();
        int y = (int) arg0.getY();
        // 将坐标转化为地理坐标
        LatLng latLngLocation = aMap.getProjection().fromScreenLocation(new Point(x, y));
        Log.i("定位", latLngLocation.toString() + "    onTouch");
        //mMapView.setVisibility(View.GONE);

        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
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
        destroyLocation();
    }


    int WRITE_COARSE_LOCATION_REQUEST_CODE = 0;

    public void checkPerssion(){
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
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.READ_PHONE_STATE},
                    WRITE_COARSE_LOCATION_REQUEST_CODE);//自定义的code

        }

        /*if(Build.VERSION.SDK_INT>=23){
            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.CALL_PHONE,Manifest.permission.READ_LOGS,
                    Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.SET_DEBUG_APP,
                    Manifest.permission.SYSTEM_ALERT_WINDOW,Manifest.permission.GET_ACCOUNTS,Manifest.permission.WRITE_APN_SETTINGS};
            ActivityCompat.requestPermissions(this,mPermissionList,REQUEST_PERM);
        }*/
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //可在此继续其他操作。
        if(WRITE_COARSE_LOCATION_REQUEST_CODE == 0){
            Intent intent1 = new Intent(SportoOutDoorActivity.this, Map_Service.class);
            bindService(intent1, connection, Context.BIND_AUTO_CREATE);
        }
    }
}
