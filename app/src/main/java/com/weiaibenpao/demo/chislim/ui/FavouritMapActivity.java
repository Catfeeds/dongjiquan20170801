package com.weiaibenpao.demo.chislim.ui;

import android.app.ActivityManager;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.LatLng;
import com.jaeger.library.StatusBarUtil;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.adater.RecyclerSportSelectAdapter;
import com.weiaibenpao.demo.chislim.bean.BluResult;
import com.weiaibenpao.demo.chislim.ble.adapter.DeviceAdapter;
import com.weiaibenpao.demo.chislim.ble.entity.EntityDevice;
import com.weiaibenpao.demo.chislim.ble.service.BLEService;
import com.weiaibenpao.demo.chislim.ble.utils.BluetoothController;
import com.weiaibenpao.demo.chislim.ble.utils.ConstantUtils;
import com.weiaibenpao.demo.chislim.ble.utils.Util;
import com.weiaibenpao.demo.chislim.settings.TtsSettings;
import com.weiaibenpao.demo.chislim.util.BluDataUtil;
import com.weiaibenpao.demo.chislim.util.DateUtil;
import com.weiaibenpao.demo.chislim.util.Default;
import com.weiaibenpao.demo.chislim.util.FileIOUtil;
import com.weiaibenpao.demo.chislim.util.LatLong_impl;
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

import static android.view.View.VISIBLE;

public class FavouritMapActivity extends AppCompatActivity {

    BluetoothAdapter mAdapter;             //蓝牙适配器
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.titleTv)
    TextView titleTv;
    @BindView(R.id.myTop)
    RelativeLayout myTop;
    @BindView(R.id.mytestview)
    MyTestView mytestview;
    @BindView(R.id.stateImg)
    ImageView stateImg;
    @BindView(R.id.heart)
    TextView heart;
    @BindView(R.id.cor)
    TextView cor;
    @BindView(R.id.dis)
    TextView dis;
    @BindView(R.id.time)
    TextView time;
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
    ImageView rootRv;
    @BindView(R.id.addFastSpeed)
    ImageView addFastSpeed;
    @BindView(R.id.lowFastSpeed)
    ImageView lowFastSpeed;
    @BindView(R.id.activity_my_view)
    RelativeLayout activityMyView;

    //加载图形的图层
    @BindView(R.id.rootView)
    RelativeLayout rootView;
    @BindView(R.id.startStopState_Text)
    TextView startStopStateText;
    @BindView(R.id.maplocationLayout)
    LinearLayout maplocationLayout;
    @BindView(R.id.kuaijie)
    RelativeLayout kuaijie;
    @BindView(R.id.jieshu)
    RelativeLayout jieshu;
    private ImageView imageView; //人物头像
    private ImageView imageView1; //起点
    private ImageView imageView2; //终点

    private PopupWindow mPopupWindow;
    private ListView listView;
    private static final String TAG = "蓝牙";
    private ArrayList bluList;
    private ArrayList<EntityDevice> list = new ArrayList<EntityDevice>();
    Intent intentService;
    DeviceAdapter deviceAdapter;

    private MsgReceiver receiver;
    BluetoothController controller = BluetoothController.getInstance();
    BluResult bluResult;
    Util u = new Util();
    BluDataUtil bluDataUtil = new BluDataUtil();
    Context context;

    public int bluState = 0;
    public double sportDis;   //运动里程
    public String sportTime;   //运动时间
    public int sportCor;    //消耗卡路里
    public float minSpeed;    //最小速度
    public float maxSpeed;      //最大速度
    public int maxSlope;     //最大坡度

    boolean flag = false;

    private VoicePlayerUtil voicePlayerUtil;

    MapView mapView = null;
    AMap aMap;
    MyTestView mytestviewtop;
    private MyTestView myTestView;
    public ArrayList<Float> xlists, ylists;

    MyThread myThread;
    Thread thread;
    List<Float> mxlists;
    List<Float> mylists;

    List<Float> mxlistsTop;
    List<Float> mylistsTop;

    LatLong_impl latLong_impl;   //用来获取系统日期
    DecimalFormat decimalFormat;

    WindowManager wm;
    int widthScreen;
    int heightScreen;
    boolean screenFlag = false;
    private SharedPreferences mSharedPreferences;
    Intent myIntent;
    int disPlan;// 计划跑步距离

    private PopupWindow mPopupSpeed;

    double cenLat;
    double cenLong;
    float zoom;

    String imageUrl;
    String imageUrlline;

    RecyclerView fastSelect;
    ArrayList arraySpeedList;
    RecyclerSportSelectAdapter adapterSpeed;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourit_map);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this , getResources().getColor(R.color.ku_bg ) , 0);
        //获取地图控件的引用
        mapView = (MapView) findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);         // 此方法必须重写
        initMap();

        xlists = FavouritePicActivity.xlists;
        ylists = FavouritePicActivity.ylists;


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

        myTestView = (MyTestView) findViewById(R.id.mytestview);     //y用户选择位置路线
        myTestView.setXlists(mxlists);
        myTestView.setYlists(mylists);

        mytestviewtop = (MyTestView) findViewById(R.id.mytestviewtop);    //用户跑步路线
        //mytestviewtop.setXlists(mxlists);
        //mytestviewtop.setYlists(mylists);

        myThread = new MyThread();
        thread = new Thread(myThread);
        thread.start();

        ButterKnife.bind(this);
        context = getApplicationContext();

        initReceiver2();          //蓝牙模块状态
        initService();
        initData();
        registerReceiver();
        MyConTrol(0);

        mSharedPreferences = getSharedPreferences(TtsSettings.PREFER_NAME, MODE_PRIVATE);
        voicePlayerUtil = new VoicePlayerUtil(this, mSharedPreferences);

        latLong_impl = new LatLong_impl(context);

        decimalFormat = new DecimalFormat("0.0");//构造方法的字符格式这里如果小数不足2位,会以0补足.

        myIntent = getIntent();
        disPlan = myIntent.getIntExtra("dis", 1000);             //
        cenLat = myIntent.getDoubleExtra("cenLat", 39.90866);     //获取地图中心点
        cenLong = myIntent.getDoubleExtra("cenLong", 116.397486);   //获取地图中心点
        zoom = myIntent.getFloatExtra("zoom", 15);
        setIntro(cenLat, cenLong, zoom);                        //设置地图中心点


        arraySpeedList = new ArrayList();
        for (int i = 3; i < 15; i++) {
            arraySpeedList.add(i + "km/h");
        }
        adapterSpeed = new RecyclerSportSelectAdapter(context, arraySpeedList);
    }

    @OnClick({R.id.back, R.id.mySpeedAdd, R.id.mySpeedMinu, R.id.mySlopeAdd, R.id.mySlopeMinu, R.id.root_rv, R.id.addFastSpeed, R.id.lowFastSpeed})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.root_rv:
                if (mAdapter == null) {
                    mAdapter = BluetoothAdapter.getDefaultAdapter();
                }
                if (!mAdapter.isEnabled()) {                                  //判断蓝牙是否打开
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
                        Toast.makeText(FavouritMapActivity.this, "您的手机不支持蓝牙",
                                Toast.LENGTH_SHORT).show();
                        return;//手机不支持蓝牙就啥也不用干了，关电脑睡觉去吧
                    }
                    new GetDataTask().execute();// 搜索任务

                    //-------------------------------------------------------------------------
                    //Log.i("蓝牙",device.toString());
                }
                if (bluState == 10) {               //正在运转
                    controller.write(bluDataUtil.startStop("1"));     //启动

                } else if (bluState == 20) {       //正在停止
                    // DecimalFormat myFormatter = new DecimalFormat("0");
                    // if (sportDis >= 1) {
                    // sexDialog(sportDis, sportCor, Integer.parseInt(myFormatter.format(Double.parseDouble(sportTime))));      //弹框，显示本次运动数据
                    controller.write(bluDataUtil.startStop("0"));         //停止
                    getScreen();         //截屏
                    //} else {
                    //   getDialog(sportDis, sportCor, Integer.parseInt(myFormatter.format(Double.parseDouble(sportTime))));      //弹框，显示本次运动数据
                    // }
                    //controller.write(bluDataUtil.startStop("0"));         //停止
                }
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
            case R.id.back:
                finish();
                break;
            /*case R.id.myMode:
                *//*Intent intent = new Intent(SportHomeActivity.this, SportHomeTypeActivity.class);
                startActivity(intent);*//*
                Intent intent = new Intent(FavouritMapActivity.this, FavouritePicActivity.class);
                startActivity(intent);
                break;*/
            case R.id.addFastSpeed:
                controller.write(bluDataUtil.addSpeed(String.valueOf(0.6)));
                break;
            case R.id.lowFastSpeed:
                showSpeedPopwindow(getWindow().getDecorView());
                break;
        }
    }

    /**
     * 地图的显示与隐藏
     */
    boolean flagMaplocationLayout = false;

    @OnClick(R.id.maplocationLayout)
    public void onClick() {
        if (!flagMaplocationLayout) {
            rootView.setVisibility(VISIBLE);
            flagMaplocationLayout = true;
        } else {
            rootView.setVisibility(View.GONE);
            flagMaplocationLayout = false;
        }
    }

    public class MyThread implements Runnable {
        int time = 10;

        public void setData(int time) {
            this.time = time;
        }

        public void run() {

            for (int i = 0; i < xlists.size(); i++) {

                int n = setView(mxlists, mylists, i, xlists.size(), xlists.size());
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
    private boolean Wan_Chen = false;

    public int setTopView(List<Float> mxlists, List<Float> mylists, int dis, int disAll, int num) {
        int i;
        if (dis >= disAll) {

            i = num;

            if (Wan_Chen == false) {
                Wan_Chen = true;
                voicePlayerUtil.startVoice("您的目标已完成,跑步还在继续！");
            }

        } else {
            i = (int) (((float) (dis) / disAll) * num);
        }

        mxlists.clear();
        mylists.clear();
        for (int j = 0; j < i; j++) {
            mxlists.add(xlists.get(j));
            mylists.add(ylists.get(j));
        }

        Paint paint1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint1.setColor(Color.BLUE); // 画笔颜色为蓝色
        paint1.setStrokeWidth(10); // 宽度5个像素
        paint1.setFilterBitmap(true);
        paint1.setDither(true);
        paint1.setFlags(R.mipmap.ic_launcher);
        mytestviewtop.setmBitPaint(paint1);

        mytestviewtop.setIcon(true);
        mytestviewtop.setImageView(imageView);

        mytestviewtop.setXlists(mxlists);
        mytestviewtop.setYlists(mylists);

        // Log.i("趣味跑重回", "当前里程" + dis + "------" + "总里程" + disAll + "------" + "总点数" + num + "---------" + "当前点数" + i);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mytestviewtop.changeView();
            }
        });
        return i;
    }

    private boolean isFrist = true;

    /**
     * @param mxlists
     * @param mylists
     * @param dis     当前距离
     * @param disAll  总距离
     * @param num     //总点数
     * @return
     */
    public int setView(List<Float> mxlists, List<Float> mylists, float dis, int disAll, int num) {
        int i = (int) (((float) dis / disAll) * num);
        Log.i("趣味跑", dis + "------" + disAll + "------" + num + "---------" + i);
        mxlists.add(xlists.get(i));
        mylists.add(ylists.get(i));

        Paint paint2 = new Paint(Paint.ANTI_ALIAS_FLAG);   //创建画笔
        paint2.setColor(Color.RED); // 画笔颜色为红色
        paint2.setStrokeWidth(10); // 宽度5个像素
        paint2.setFilterBitmap(true);
        paint2.setDither(true);
        myTestView.setmBitPaint(paint2);

        myTestView.setXlists(mxlists);
        myTestView.setYlists(mylists);

        final int topM = mylists.get(0).intValue();
        final int left = mxlists.get(0).intValue();

        final int ftopm = ylists.get(ylists.size() - 1).intValue();
        final int fleft = xlists.get(xlists.size() - 1).intValue();

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (isFrist) {
                    RelativeLayout.LayoutParams lpB = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT/* height*/);
                    lpB.topMargin = topM - imageView.getMeasuredHeight();
                    lpB.leftMargin = left - imageView.getMeasuredWidth();
                    imageView.setLayoutParams(lpB);
                    imageView.setVisibility(VISIBLE);

                    RelativeLayout.LayoutParams lpB1 = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT/* height*/);
                    lpB1.topMargin = topM - imageView1.getMeasuredHeight();
                    lpB1.leftMargin = left - imageView1.getMeasuredWidth();
                    imageView1.setLayoutParams(lpB1);
                    imageView1.setVisibility(VISIBLE);

                    RelativeLayout.LayoutParams lpB2 = new RelativeLayout.LayoutParams(
                            RelativeLayout.LayoutParams.WRAP_CONTENT,
                            RelativeLayout.LayoutParams.WRAP_CONTENT/* height*/);
                    lpB2.topMargin = ftopm - imageView2.getMeasuredHeight();
                    lpB2.leftMargin = fleft;
                    imageView2.setLayoutParams(lpB2);
                    imageView2.setVisibility(VISIBLE);


                    isFrist = false;
                }
                myTestView.changeView();
            }
        });
        return i;
    }

    public void initMap() {
        aMap = mapView.getMap();

        aMap.setTrafficEnabled(true);// 显示实时交通状况
        //地图模式可选类型：MAP_TYPE_NORMAL,MAP_TYPE_SATELLITE,MAP_TYPE_NIGHT
        aMap.setMapType(AMap.MAP_TYPE_NORMAL);// 地图模式

        wm = this.getWindowManager();

        widthScreen = wm.getDefaultDisplay().getWidth();
        heightScreen = wm.getDefaultDisplay().getHeight();

    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


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
                        Toast.makeText(FavouritMapActivity.this, "您的手机不支持蓝牙",
                                Toast.LENGTH_SHORT).show();
                        return;//手机不支持蓝牙就啥也不用干了，关电脑睡觉去吧
                    }
                    new GetDataTask().execute();// 搜索任务

                    //-------------------------------------------------------------------------
                    //Log.i("蓝牙",device.toString());
                    MyConTrol(1);
                    break;
                case BluetoothAdapter.STATE_TURNING_OFF:
                    Log.i("蓝牙", "蓝牙模块正在关闭");
                    break;
                case BluetoothAdapter.STATE_OFF:
                    Log.i("蓝牙", "蓝牙模块处于关闭状态");
                    break;
            }
        }
    };

    /**
     * 0--9 有坡度的跑步机
     * 10--19  没坡度的跑步机
     *
     * @param stateNum
     */
    public void MyConTrol(int stateNum) {
        switch (stateNum) {
            case 0:                                           //蓝牙未开启状态
                speedLayout.setVisibility(View.INVISIBLE);
                slopeLayout.setVisibility(View.INVISIBLE);
                //    stateImg.setBackgroundColor(getResources().getColor(R.color.red_yuan));
                break;
            case 1:                                           //蓝牙未连接状态
                speedLayout.setVisibility(View.INVISIBLE);
                slopeLayout.setVisibility(View.INVISIBLE);
                //    stateImg.setBackgroundColor(getResources().getColor(R.color.yellow_bg));
                break;
            case 2:                                           //蓝牙连接成功状态
                speedLayout.setVisibility(VISIBLE);
                slopeLayout.setVisibility(VISIBLE);
                //    stateImg.setBackgroundColor(getResources().getColor(R.color.green_yuan));
                break;
            case 3:
                break;
            case 4:
                break;
            case 10:
                speedLayout.setVisibility(View.INVISIBLE);
                slopeLayout.setVisibility(View.INVISIBLE);
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
    int disTab;

    public void changeActivity(BluResult bluResult) {
        speedText.setText(bluResult.speed + "");
        slopeText.setText(bluResult.slope + "");
        time.setText(bluResult.time.replace (".", ":"));
        cor.setText(bluResult.calories + "");
        heart.setText(bluResult.heart + "");
        dis.setText(bluResult.distance + "");

        if ((int) (Double.parseDouble(bluResult.distance) * 1000) != disTab) {
            setTopView(mxlistsTop, mylistsTop, (int) (Double.parseDouble(bluResult.distance) * 1000), disPlan, xlists.size());
            disTab = (int) (Double.parseDouble(bluResult.distance) * 1000);
        }

        MyConTrol(2);
        if (bluState == 20) { //跑步机正在运转
            startStopStateText.setText("停止");
            speedLayout.setVisibility(View.VISIBLE);
            slopeLayout.setVisibility(View.VISIBLE);
            addFastSpeed.setVisibility(View.VISIBLE);
            lowFastSpeed.setVisibility(View.VISIBLE);
            kuaijie.setVisibility(View.VISIBLE);
            jieshu.setVisibility(View.VISIBLE);
        }

        if (bluState == 30) {   //跑步机正在加速
            startStopStateText.setText("正在加速");
            speedLayout.setVisibility(View.VISIBLE);
            slopeLayout.setVisibility(View.VISIBLE);
            addFastSpeed.setVisibility(View.VISIBLE);
            lowFastSpeed.setVisibility(View.VISIBLE);
            kuaijie.setVisibility(View.VISIBLE);
            jieshu.setVisibility(View.VISIBLE);
        }

        if (bluState == 40) {   //跑步机正在减速
            startStopStateText.setText("正在减速");
            speedLayout.setVisibility(View.VISIBLE);
            slopeLayout.setVisibility(View.VISIBLE);
            addFastSpeed.setVisibility(View.VISIBLE);
            lowFastSpeed.setVisibility(View.VISIBLE);
            kuaijie.setVisibility(View.VISIBLE);
            jieshu.setVisibility(View.VISIBLE);
        }

        if (bluState == 10) {  //跑步处于停止状态
            startStopStateText.setText("开始");
            speedLayout.setVisibility(View.VISIBLE);
            slopeLayout.setVisibility(View.VISIBLE);
            addFastSpeed.setVisibility(View.VISIBLE);
            lowFastSpeed.setVisibility(View.VISIBLE);
            kuaijie.setVisibility(View.VISIBLE);
            jieshu.setVisibility(View.VISIBLE);
        }

        if (bluState == 60) {      //安全锁脱落
            speedLayout.setVisibility(View.GONE);
            slopeLayout.setVisibility(View.GONE);
            addFastSpeed.setVisibility(View.GONE);
            lowFastSpeed.setVisibility(View.GONE);
            kuaijie.setVisibility(View.GONE);
            jieshu.setVisibility(View.GONE);
            startStopStateText.setText("安全锁脱落");
        }
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

                if (mPopupWindow != null) {
                    mPopupWindow.dismiss();
                }
            }
        });
    }

    private void initData() {
        bluList = new ArrayList();
        deviceAdapter = new DeviceAdapter(this, list);
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
        intentService = new Intent(FavouritMapActivity.this, BLEService.class);
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
                        maxSpeed = (int) (Double.parseDouble(bluResult.maxSpeed));    //最大速度
                        maxSlope = Integer.parseInt(bluResult.maxSlope);    //最大坡度
                        minSpeed = Float.parseFloat(bluResult.minSpeed);    //最小速度
                        sportTime = bluResult.time;
                        changeActivity(bluResult);
                    }
                }
            }
        }
    }

    private void toast(String str) {
        Toast.makeText(FavouritMapActivity.this, str, Toast.LENGTH_SHORT).show();
    }

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
     * 销毁
     */

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mapView.onDestroy();

        stopService(intentService);
        unregisterReceiver(receiver);
        unregisterReceiver(bluetoothState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView.onResume ()，实现地图生命周期管理
        mapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView.onPause ()，实现地图生命周期管理
        mapView.onPause();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，实现地图生命周期管理
        mapView.onSaveInstanceState(outState);
    }


    /**
     * 显示快捷操作结果
     *
     * @param parent
     */
    private void showSpeedPopwindow(View parent) {

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenWidth = dm.widthPixels;
        int screenHeight = dm.heightPixels;

        if (mPopupSpeed == null) {
            LayoutInflater mLayoutInflater = LayoutInflater.from(this);
            View contentView = mLayoutInflater.inflate(R.layout.pop_blucontrol, null);

            fastSelect = (RecyclerView) contentView.findViewById(R.id.fastSelect);
            fastSelect.setLayoutManager(new LinearLayoutManager(this,
                    LinearLayoutManager.VERTICAL, false));

            fastSelect.setAdapter(adapterSpeed);
            RecyclerSportSelectAdapter.setOnItemClickListener(new RecyclerSportSelectAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {
                    controller.write(bluDataUtil.addSpeed(String.valueOf(position + 3)));
                    mPopupSpeed.dismiss();
                }
            });

            mPopupSpeed = new PopupWindow(contentView, screenWidth / 1, screenHeight / 3);
        }
        mPopupSpeed.setFocusable(true);
        mPopupSpeed.setOutsideTouchable(true);                                                   //设置点击屏幕其它地方弹出框消失
        mPopupSpeed.setBackgroundDrawable(new BitmapDrawable());

        // 显示的位置为:屏幕的宽度的1/16
        // mPopupSpeed.showAsDropDown(parent, screenWidth / 1, screenHeight/2);
        mPopupSpeed.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 切换地图显示中心点
     */
    public void setIntro(double latNow, double longNow, float n) {
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
                Log.i("截屏", "截屏文件已保存至" + imageUrlline);
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
        rootView.setVisibility(VISIBLE);
        flagMaplocationLayout = true;

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
        Intent intent = new Intent(FavouritMapActivity.this, SportHomeRemberActivity.class);
        intent.putExtra("sportDis", sportDis);
        Log.i("蓝牙距离", sportDis + "");
        intent.putExtra("sportTime", sportTime);
        intent.putExtra("sportCor", sportCor);
        intent.putExtra("imageUrl", imageUrl);
        intent.putExtra("imageUrlline", imageUrlline);
        startActivity(intent);
        finish();
    }
}
