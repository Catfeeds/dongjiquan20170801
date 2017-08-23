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
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.location.AMapLocationClient;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.jaeger.library.StatusBarUtil;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.service.Map_Service;
import com.weiaibenpao.demo.chislim.settings.TtsSettings;
import com.weiaibenpao.demo.chislim.util.ACache;
import com.weiaibenpao.demo.chislim.util.CircleChart02View;
import com.weiaibenpao.demo.chislim.util.FileIOUtil;
import com.weiaibenpao.demo.chislim.util.MyTestView;
import com.weiaibenpao.demo.chislim.util.VoicePlayerUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FavouriteMapOutHomeActivity extends Activity implements LocationSource {

    private static final int LOCATION_TIME_INTERVAL = 4000;

    Bundle mSavedInstanceState;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.titleTv)
    TextView titleTv;
    @BindView(R.id.toplayout)
    RelativeLayout toplayout;
    @BindView(R.id.mapView)
    MapView mapView;
    @BindView(R.id.dis)
    TextView dis;
    @BindView(R.id.tv_steps)
    TextView tvSteps;
    @BindView(R.id.myCalories)
    TextView myCalories;
    @BindView(R.id.showView)
    LinearLayout showView;
    @BindView(R.id.textWord)
    TextView textWord;
    @BindView(R.id.btn_goto)
    Button btnGoto;
    @BindView(R.id.startView)
    RelativeLayout startView;
    @BindView(R.id.myTime)
    TextView myTime;
    @BindView(R.id.timeView)
    RelativeLayout timeView;
    @BindView(R.id.start)
    ImageView start;
    @BindView(R.id.suo)
    ImageView suo;
    @BindView(R.id.stop)
    ImageView stop;
    @BindView(R.id.mySpeed)
    TextView mySpeed;
    @BindView(R.id.lockView)
    RelativeLayout lockView;
    @BindView(R.id.sportTime)
    TextView sportTime;

    @BindView(R.id.rootView)
    RelativeLayout rootView ;
    private ImageView imageView1 ; //起点
    private ImageView imageView2 ; //终点

    private ACache mCache;   //保存零食数据

    private AMap aMap;
    // private OnLocationChangedListener mLocationLinstener;
    private AMapLocationClient locationClient = null;


    private ArrayList<LatLng> mLocationList = new ArrayList<LatLng>();

    private double mLocatinLat;
    private double mLocationLon;

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

    MyTestView mytestviewtop;
    MyTestView myTestView;
    public ArrayList<Float> xlists, ylists;

    MyThread myThread;
    Thread thread;
    List<Float> mxlists;
    List<Float> mylists;

    List<Float> mxlistsTop;
    List<Float> mylistsTop;

    Intent myIntent;
    int disPlan;// 计划跑步距离

    String disData;                //当前运动距离
    String cor;                    //卡路里
    String sporttime;             //时间

    Intent intentServices;    //启动

    WindowManager wm;
    int widthScreen;
    int heightScreen;
    boolean screenFlag = false;

    //圆,用于解锁
    CircleChart02View chart = null;
    int i= 0;

    double cenLat = 39.90866;      //当前地图中心点
    double cenLong = 116.397486;
    float zoom = 15;             //缩放比例

    String imageUrl;
    String imageUrlline;

    private VoicePlayerUtil voicePlayerUtil;
    private SharedPreferences mSharedPreferences;
    DecimalFormat decimalFormat;


    private ImageView imageView ; //坐标点的小人标记
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_map_out_home);
        StatusBarUtil.setColor(this , getResources().getColor(R.color.ku_bg ) , 0);
        ButterKnife.bind(this);



        mSavedInstanceState = savedInstanceState;
        mSharedPreferences = getSharedPreferences(TtsSettings.PREFER_NAME, MODE_PRIVATE);
        voicePlayerUtil = new VoicePlayerUtil(FavouriteMapOutHomeActivity.this,mSharedPreferences);
        context = getApplicationContext();
        mCache = ACache.get(context);
        mCache.put("state", "0");

        xlists = FavouritePicActivity.xlists;
        ylists = FavouritePicActivity.ylists;

        /*imageView.setImageResource(R.mipmap.run);
        RelativeLayout.LayoutParams lpB = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT*//* height*//*);
        imageView.setLayoutParams(lpB);
        imageView.setVisibility(View.GONE);
        rootView.addView(imageView);*/
        imageView1 = new ImageView(this);
        imageView1.setImageResource(R.mipmap.qi1);
        RelativeLayout.LayoutParams lpB1 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT/* height*/);
        imageView1.setLayoutParams(lpB1);
        imageView1.setVisibility(View.INVISIBLE);
        rootView.addView(imageView1);

        imageView2 = new ImageView(this);
        imageView2.setImageResource(R.mipmap.qizhong);
        RelativeLayout.LayoutParams lpB2 = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT/* height*/);
        imageView2.setLayoutParams(lpB2);
        imageView2.setVisibility(View.INVISIBLE);
        rootView.addView(imageView2);

        imageView = new ImageView(this);
        imageView.setImageResource(R.mipmap.run);
        RelativeLayout.LayoutParams lpB = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT/* height*/);
        imageView.setLayoutParams(lpB);
        imageView.setVisibility(View.INVISIBLE);
        rootView.addView(imageView);

        //要绘制的点的坐标
        mxlists = new ArrayList<Float>();
        mylists = new ArrayList<Float>();

        mxlistsTop = new ArrayList<Float>();
        mylistsTop = new ArrayList<Float>();

        myTestView = (MyTestView) findViewById(R.id.mytestview);     //用户选择位置路线
        myTestView.setXlists(mxlists);
        myTestView.setYlists(mylists);

        mytestviewtop = (MyTestView) findViewById(R.id.mytestviewtop);    //用户跑步路线

        myThread = new MyThread();
        thread = new Thread(myThread);
        thread.start();

        mapView.onCreate(savedInstanceState);                         // 此方法必须重写
        initMap();
        getState();

        //配置广播
        myBroadcast = new MyBroadcast();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.lijianbao.mapLocationLatLong");
        FavouriteMapOutHomeActivity.this.registerReceiver(myBroadcast, filter);


        myIntent = getIntent();
        disPlan = myIntent.getIntExtra("dis", 1000);
        cenLat = myIntent.getDoubleExtra("cenLat",39.90866);     //获取地图中心点
        cenLong = myIntent.getDoubleExtra("cenLong",116.397486);   //获取地图中心点
        zoom = myIntent.getFloatExtra("zoom",15);
        setIntro(cenLat,cenLong,zoom);                        //设置地图中心点


        chart = (CircleChart02View)findViewById(R.id.circle_view);            //解锁用的圆
        chart.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    Log.i("周期","开始");
                    i = 0;
                } else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                    Log.i("周期","运行");
                    i++;
                    if(i == 100){
                        lockView.setVisibility(View.GONE);
                        suo.setVisibility(View.VISIBLE);
                        stop.setVisibility(View.VISIBLE);
                        start.setVisibility(View.VISIBLE);
                        toplayout.setVisibility(View.VISIBLE);
                    }
                    chart.setPercentage(i);

                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    Log.i("周期","结束");
                    i = 0;
                    chart.setPercentage(i);
                }
                chart.chartRender();
                chart.invalidate();
                return true;
            }
        });
    }

    public void initMap() {
        aMap = mapView.getMap();

        aMap.setTrafficEnabled(true);// 显示实时交通状况
        //地图模式可选类型：MAP_TYPE_NORMAL,MAP_TYPE_SATELLITE,MAP_TYPE_NIGHT
        aMap.setMapType(AMap.MAP_TYPE_NORMAL);// 卫星地图模式

        wm = this.getWindowManager();

        widthScreen = wm.getDefaultDisplay().getWidth();
        heightScreen = wm.getDefaultDisplay().getHeight();

        aMap.setOnMapClickListener(new AMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                ViewGroup.LayoutParams lp;
                lp = mapView.getLayoutParams();
                if (screenFlag) {
                    screenFlag = false;
                    lp.width = widthScreen;
                    lp.height = (int) ((float) heightScreen * 0.27);
                    mapView.setLayoutParams(lp);
                } else {
                    screenFlag = true;
                    lp.width = widthScreen;
                    lp.height = heightScreen;
                    mapView.setLayoutParams(lp);
                }
            }
        });
    }

    /*@OnClick({R.id.back, R.id.btn_goto, R.id.start, R.id.suo, R.id.stop})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();

                break;
            case R.id.btn_goto:                               //倒计时前的开始按钮
                startView.setVisibility(View.GONE);
                timeView.setVisibility(View.VISIBLE);
                setTime();
                break;
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
                getScreen();         //截屏getScreen();         //截屏
                Toast.makeText(FavouriteMapOutHomeActivity.this,"截屏",Toast.LENGTH_SHORT).show();
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
                getScreen();         //截屏getScreen();         //截屏
                //Toast.makeText(FavouriteMapOutHomeActivity.this,"截屏",Toast.LENGTH_SHORT).show();
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

    public class MyThread implements Runnable {
        int time = 10;

        public void setData(int time) {
            this.time = time;
        }

        public void run() {

            for (int i = 0; i < xlists.size(); i++) {

                setView(mxlists, mylists, i, xlists.size(), xlists.size());
                try {
                    thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }



    /**
     * @param mxlists
     * @param mylists
     * @param dis     当前距离
     * @param disAll  总距离
     * @param num     //总点数
     * @return
     */
    private boolean Wan_Chen = false ;
    public int setTopView(List<Float> mxlists, List<Float> mylists, float dis, int disAll, int num) {

        int i;
        if(dis >= disAll){
            i = num;
            if(Wan_Chen == false){
                Wan_Chen = true ;
                voicePlayerUtil.startVoice("您的目标已完成,跑步还在继续！");
            }
        }else{
            i = (int) (((float) (dis) / disAll) * num);
        }

        //Log.i("趣味跑重回", "当前里程" + dis + "------" + "总里程" + disAll + "------" + "总点数" + num + "---------" + "当前点数" + i);
        mxlists.clear();
        mylists.clear();
        for(int j = 0;j<i;j++){
            mxlists.add(xlists.get(j));
            mylists.add(ylists.get(j));
        }



        Paint paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint1.setColor(Color.BLUE); // 画笔颜色为蓝色
        paint1.setStrokeWidth(15); // 宽度5个像素
        paint1.setFilterBitmap(true);
        paint1.setDither(true);

        mytestviewtop.setmBitPaint(paint1);
        mytestviewtop.setXlists(mxlists);
        mytestviewtop.setYlists(mylists);
        mytestviewtop.setIcon(true);
        mytestviewtop.setImageView(imageView);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mytestviewtop.changeView();

            }
        });
        return i;
    }


    private boolean isFrist = true ;
    /**
     * @param mxlists
     * @param mylists
     * @param dis     当前距离
     * @param disAll  总距离
     * @param num     //总点数
     * @return
     */
    public int setView(List<Float> mxlists, List<Float> mylists, float dis, int disAll, int num) {
        final int i = (int) (((float) dis / disAll) * num);
        Log.i("趣味跑", dis + "------" + disAll + "------" + num + "---------" + i);
        mxlists.add(xlists.get(i));
        mylists.add(ylists.get(i));

        Paint paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);   //创建画笔
        paint2.setColor(Color.RED); // 画笔颜色为红色
        paint2.setStrokeWidth(15); // 宽度5个像素
        paint2.setFilterBitmap(true);
        paint2.setDither(true);
        myTestView.setmBitPaint(paint2);

        myTestView.setXlists(mxlists);
        myTestView.setYlists(mylists);

        final int topM = mylists.get(0).intValue();
        final int left = mxlists.get(0).intValue();

        final int ftopm = ylists.get(ylists.size() - 1).intValue() ;
        final int fleft = xlists.get(xlists.size() - 1).intValue() ;

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if(isFrist){
                    RelativeLayout.LayoutParams lpB = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT/* height*/);
                    lpB.topMargin =  topM - imageView.getMeasuredHeight();
                    lpB.leftMargin = left - imageView.getMeasuredWidth();
                    imageView.setLayoutParams(lpB);
                    imageView.setVisibility(View.VISIBLE);


                    RelativeLayout.LayoutParams lpB1 = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT/* height*/);
                    lpB1.topMargin =  topM - imageView1.getMeasuredHeight();
                    lpB1.leftMargin = left - imageView1.getMeasuredWidth()/2;
                    imageView1.setLayoutParams(lpB1);
                    imageView1.setVisibility(View.VISIBLE);

                    RelativeLayout.LayoutParams lpB2 = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT/* height*/);
                    lpB2.topMargin =  ftopm - imageView2.getMeasuredHeight();
                    lpB2.leftMargin = fleft ;
                    imageView2.setLayoutParams(lpB2);
                    imageView2.setVisibility(View.VISIBLE);

                    isFrist = false ;
                }
                myTestView.changeView();
            }
        });
        return i;
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
        }else if(state == 2){

            startView.setVisibility(View.GONE);
            timeView.setVisibility(View.GONE);
            showView.setVisibility(View.VISIBLE);

            start.setImageResource(R.mipmap.sportcontinue);
        }
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
        intentServices = new Intent(FavouriteMapOutHomeActivity.this, Map_Service.class);
        if (!isServiceRunning(context, "com.weiaibenpao.demo.chislim.service.Map_Service")) {
            intentServices.putExtra("activityContext","com.jianbaopp.FavouriteMapOutHomeActivity");
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
        unregisterReceiver(myBroadcast);
    }


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
            String cor = intent.getStringExtra("cor");                                   //卡路里
            sporttime = intent.getStringExtra("sporttime");                     //时间
            String step = intent.getStringExtra("step");                                  //步数
            String speed = intent.getStringExtra("speed");                             //速度
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
                setTopView(mxlistsTop, mylistsTop, Integer.parseInt(disData), disPlan, xlists.size());
                disTab = Integer.parseInt(disData);
            }
        }
    }

    /**
     * 切换地图显示中心点
     */
    public void setIntro(double latNow,double longNow,float n) {
        LatLng marker1 = new LatLng(latNow, longNow);
        //设置中心点和缩放比例
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(marker1));
        aMap.moveCamera(CameraUpdateFactory.zoomTo(n));
    }

    //这种方法状态栏是空白，显示不了状态栏的信息
    /**
     * 截屏
     */
    private void saveCurrentImage() {
        //获取当前屏幕的大小
        int width = getWindow().getDecorView().getRootView().getWidth();
        int height = getWindow().getDecorView().getRootView().getHeight();
        //生成相同大小的图片
        Bitmap temBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        //找到当前页面的跟布局
        View view = getWindow().getDecorView().getRootView();
        //设置缓存
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache();
        //从缓存中获取当前屏幕的图片
        temBitmap = view.getDrawingCache();

        //输出到sd卡
        if (FileIOUtil.getExistStorage()) {
            imageUrl = FileIOUtil.GetInstance().getFilePathAndName();
            FileIOUtil.GetInstance().onFolderAnalysis(imageUrl);
            File file = new File(FileIOUtil.GetInstance().getFilePathAndName());
            try {
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileOutputStream foStream = new FileOutputStream(file);
                temBitmap.compress(Bitmap.CompressFormat.PNG, 100, foStream);
                foStream.flush();
                foStream.close();
                Log.i("截屏", "截屏文件已保存至" + imageUrl);
            } catch (Exception e) {
                Log.i("Show", e.toString());
            }
        }
    }

    //保存自定义view的截图
    private void saveCustomViewBitmap() {
        //获取自定义view图片的大小
        Bitmap temBitmap = Bitmap.createBitmap(mytestviewtop.getWidth(), mytestviewtop.getHeight(), Bitmap.Config.ARGB_8888);
        //使用Canvas，调用自定义view控件的onDraw方法，绘制图片
        Canvas canvas = new Canvas(temBitmap);
        mytestviewtop.setIcon(false);
        mytestviewtop.draw(canvas);

        //输出到sd卡
        if (FileIOUtil.getExistStorage()) {
            imageUrlline = FileIOUtil.GetInstance().getFilePathAndName();
            FileIOUtil.GetInstance().onFolderAnalysis(imageUrlline);
            File file = new File(FileIOUtil.GetInstance().getFilePathAndName());
            try {
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileOutputStream foStream = new FileOutputStream(file);
                temBitmap.compress(Bitmap.CompressFormat.PNG, 100, foStream);
                foStream.flush();
                foStream.close();
                Log.i("截屏", "截屏文件已保存至imageUrlline" + imageUrlline);
            } catch (Exception e) {
                Log.i("Show", e.toString());
            }
        }
    }

    //存储地图截屏
    public void cutMapScreen() {

        aMap.getMapScreenShot(new AMap.OnMapScreenShotListener() {
            @Override
            public void onMapScreenShot(Bitmap bitmap) {

            }

            @Override
            public void onMapScreenShot(Bitmap bitmap, int status) {
                //SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                if (null == bitmap) {
                    return;
                }
                try {
                    imageUrl = Environment.getExternalStorageDirectory() + "/test.png";
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
                        Log.i("截屏", "截屏文件已保存至imageUrl" + imageUrl);
                        goIntent();
                    } else {
                        buffer.append("地图未渲染完成，截屏有网格");
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getScreen() {
        //saveCurrentImage();          //Activity截屏

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("截屏", "截屏文件已保存至");
                //开一个线程来截图，就不会出现屏幕卡顿的现象 /storage/emulated/0/test_20161220155212.png   /storage/emulated/0/Test_ScreenShots/Screenshot_2017-01-10-11-34-54.png
                saveCustomViewBitmap();             //view截屏
                cutMapScreen();             //地图截屏
            }
        }).start();
    }

    public void goIntent() {
        Log.i("截屏", "跳转跳转跳转跳转跳转");
        Intent intent = new Intent(context, SportHomeRemberActivity.class);

        intent.putExtra("sportDis", Integer.parseInt(disData));
        intent.putExtra("sportTime", sporttime);
        //intent.putExtra("sportCor", Integer.parseInt(cor));
        intent.putExtra("sportCor", 15);
        intent.putExtra("imageUrl", imageUrl);
        intent.putExtra("imageUrlline", imageUrlline);

        startActivity(intent);

        mIsStart = false;
        map_Service.setNum(4);
        unbindService(connection);                         //解除绑定服务
        map_Service.stopService(intentServices);         //关闭启动式服务
        map_Service.stopSelf();
        finish();
    }
}
