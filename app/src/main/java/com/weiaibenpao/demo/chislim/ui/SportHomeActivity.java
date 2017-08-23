package com.weiaibenpao.demo.chislim.ui;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
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

import com.jaeger.library.StatusBarUtil;
import com.weiaibenpao.demo.chislim.BuildConfig;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.adater.RecyclerSportSelectAdapter;
import com.weiaibenpao.demo.chislim.bean.BluResult;
import com.weiaibenpao.demo.chislim.bean.Blutetooth;
import com.weiaibenpao.demo.chislim.ble.adapter.DeviceAdapter;
import com.weiaibenpao.demo.chislim.ble.entity.EntityDevice;
import com.weiaibenpao.demo.chislim.ble.service.BLEService;
import com.weiaibenpao.demo.chislim.ble.utils.BluetoothController;
import com.weiaibenpao.demo.chislim.ble.utils.ConstantUtils;
import com.weiaibenpao.demo.chislim.ble.utils.Util;
import com.weiaibenpao.demo.chislim.music.dao.Dao_Get_music;
import com.weiaibenpao.demo.chislim.util.BluDataUtil;
import com.weiaibenpao.demo.chislim.util.BluetoothUtil;
import com.weiaibenpao.demo.chislim.util.Default;
import com.weiaibenpao.demo.chislim.util.LatLong_impl;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class SportHomeActivity extends Activity {

    @BindView(R.id.stateText)
    TextView stateText;
    @BindView(R.id.stateImg)
    ImageView stateImg;
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

    BluetoothAdapter mAdapter;             //蓝牙适配器
    @BindView(R.id.time)
    TextView time;
    @BindView(R.id.dis)
    TextView dis;
    @BindView(R.id.cor)
    TextView cor;
    @BindView(R.id.heart)
    TextView heart;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.addFastSpeed)
    ImageView addFastSpeed;
    @BindView(R.id.lowFastSpeed)
    ImageView lowFastSpeed;
    @BindView(R.id.startStopState_Text)
    TextView startStopStateText;
    @BindView(R.id.kuaijie)
    RelativeLayout kuaijie;
    @BindView(R.id.jieshu)
    RelativeLayout jieshu;
    private PopupWindow mPopupWindow;
    private ListView listView;
    private Blutetooth blutetooth;
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

    int step = 0;

    public int bluState = 0;
    public double sportDis;   //运动里程
    public String sportTime;   //运动时间
    public int sportCor;    //消耗卡路里
    public float minSpeed;    //最小速度
    public float maxSpeed;      //最大速度
    public int maxSlope;     //最大坡度

    boolean flag = false;
    private Intent intent;
    int pross;     //进度数据

    //获取本地歌曲数据源
    private Dao_Get_music get_music;
    //用来存放本地歌曲
    private ArrayList localMusicList;

    DecimalFormat decimalFormat;

    LatLong_impl latLong_impl;          //用来获取系统日期

    SetBlue ds1 = null;
    Thread t1 = null;
    ExecutorService es;           //线程池

    //快捷速度
    ImageView imgThree;
    ImageView imgFive;
    ImageView imgSeven;
    ImageView imgNine;
    ImageView imgTen;
    ImageView imgTravel;
    private PopupWindow mPopupSpeed;
    RecyclerView fastSelect;

    //private SharedPreferences settings;
    // VoiceUtil voiceUtil;

    ArrayList arraySpeedList;
    RecyclerSportSelectAdapter adapterSpeed;


    //todo 彩屏
    BluetoothDevice device;
    BluetoothUtil util;
    static final boolean d = BuildConfig.DEBUG;

    int mCaiSpeed;
    int mCaiIncline;
    int mCaiMinSpeed;
    int mCaiMaxSpeed;
    int mCaiMaxIncline;

    //性别选择框
    ImageView boyImg;                //性别选择框中的男
    ImageView girlImg;
    TextView sexText;

    boolean caipingorlanpinFlag = false;    //默认蓝屏
    boolean connFlag = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("SportHome","onCreate");

        //settings = getSharedPreferences(getPackageName(), MODE_PRIVATE);
        //voiceUtil = new VoiceUtil(getApplicationContext(), settings);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //让一个activity 浮在锁屏界面的上方，返回即进入解锁界面
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
        //设置屏幕常亮
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //设置窗体背景模糊
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND, WindowManager.LayoutParams.FLAG_BLUR_BEHIND);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED
                | WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON);

        setContentView(R.layout.activity_sport_home);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.ku_bg), 0);
        ButterKnife.bind(this);
        context = getApplicationContext();


        //打开蓝牙
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


        //sexDialog();              //判断是否为彩屏
        initReceiver2();          //蓝牙模块状态


        initService();
        initData();
        registerReceiver();
        MyConTrol(0);
        onLongClickListenerEvent();
        decimalFormat = new DecimalFormat("0.0");//构造方法的字符格式这里如果小数不足2位,会以0补足.

        latLong_impl = new LatLong_impl(context);

        //test1();

        /**创建具一个可重用的，有固定数量的线程池
         * 每次提交一个任务就提交一个线程，直到线程达到线城池大小，就不会创建新线程了
         * 线程池的大小达到最大后达到稳定不变，如果一个线程异常终止，则会创建新的线程
         */
        es = Executors.newFixedThreadPool(1);
        es.shutdown();


        /*voiceUtil.setGetIntentDataListener(new VoiceUtil.VoiceListene() {
            //1 启动   0停止
            @Override
            public void controlStopStart(int n) {
                //Log.i("回调", "回调``````回调" + n);
                if (n == 1) {
                    controller.write(bluDataUtil.startStop("0"));         //停止
                } else if (n == 0) {
                    controller.write(bluDataUtil.startStop("1"));         //启动
                }
            }

            //1 加  2减速   3 高   4降
            @Override
            public void controlSpeedSlope(int n) {
                if (n == 1) {
                    Log.i("回调", "速度增加1");
                    float speed1 = Float.parseFloat(speedText.getText().toString());
                    if (speed1 < maxSpeed) {
                        speed1 = speed1 + (float) 1;
                    } else {
                        speed1 = maxSpeed;
                    }
                    controller.write(bluDataUtil.addSpeed(decimalFormat.format(speed1)));
                } else if (n == 2) {
                    Log.i("回调", "速度减少");
                    float speed2 = Float.parseFloat(speedText.getText().toString());
                    if (speed2 > minSpeed) {
                        speed2 = speed2 - (float) 1;
                    } else {
                        speed2 = minSpeed;
                    }
                    controller.write(bluDataUtil.addSpeed(decimalFormat.format(speed2)));
                } else if (n == 3) {
                    Log.i("回调", "坡度增加");
                    int slope1 = Integer.parseInt(slopeText.getText().toString());
                    if (slope1 < maxSlope) {
                        slope1 = slope1 + 1;
                    } else {
                        slope1 = maxSlope;
                    }
                    controller.write(bluDataUtil.addSlope(slope1 + "")); //坡度增加
                } else if (n == 4) {
                    Log.i("回调", "坡度减少");
                    int slope2 = Integer.parseInt(slopeText.getText().toString());
                    if (slope2 > 1) {
                        slope2 = slope2 - 1;
                    } else {
                        slope2 = 0;
                    }
                    controller.write(bluDataUtil.addSlope(slope2 + ""));   //坡度减少
                }
            }

            //1 速度   2坡度
            @Override
            public void controlSpeedSlopeData(int n, String str) {
                String regex = "\\d*";
                Pattern p = Pattern.compile(regex);
                Matcher m = p.matcher(str);
                while (m.find()) {
                    if (!"".equals(m.group())) {
                        if (n == 1) {
                            Log.i("回调", String.valueOf(m.group()));
                            controller.write(bluDataUtil.addSpeed(String.valueOf(m.group())));
                        } else if (n == 2) {
                            Log.i("回调", String.valueOf(m.group()));
                            controller.write(bluDataUtil.addSlope(String.valueOf(m.group())));   //坡度减少
                        }
                    }
                }
            }

            @Override
            public void controlStyle(int n) {
                if (n == 0) {              //开始说话
                    stateImg.setImageResource(R.mipmap.voice0);
                } else if (n == 1) {        // 结束说话
                    stateImg.setImageResource(R.mipmap.voice1);
                }
            }

            @Override
            public void controlResult(String str) {
                stateText.setText(str);
            }
        });*/


        arraySpeedList = new ArrayList();
        for (int i = 3; i < 15; i++) {
            arraySpeedList.add(i + "km/h");
        }
        adapterSpeed = new RecyclerSportSelectAdapter(context, arraySpeedList);

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
                        Toast.makeText(SportHomeActivity.this, "您的手机不支持蓝牙",
                                Toast.LENGTH_SHORT).show();
                        return;  //手机不支持蓝牙就啥也不用干了，关电脑睡觉去吧
                    }
                    //test1();
                    startStopStateText.setText("搜索蓝牙");
                    new GetDataTask().execute();// 搜索任务

                    //-------------------------------------------------------------------------
                    //Log.i("蓝牙",device.toString());
                    MyConTrol(1);
                    break;
                case BluetoothAdapter.STATE_TURNING_OFF:
                    Log.i("蓝牙", "蓝牙模块正在关闭");
                    startStopStateText.setText("搜索蓝牙");
                    break;
                case BluetoothAdapter.STATE_OFF:
                    Log.i("蓝牙", "蓝牙模块处于关闭状态");
                    startStopStateText.setText("搜索蓝牙");
                    break;
            }
        }
    };


    /**
     * 0--9 有坡度的跑步机
     * 10--19  没坡度的跑步机
     * R.id.addFastSpeed, R.id.lowFastSpeed
     *
     * @param stateNum
     */
    public void MyConTrol(int stateNum) {
        switch (stateNum) {
            case 0:                                           //蓝牙未开启状态
                speedLayout.setVisibility(View.GONE);
                slopeLayout.setVisibility(View.GONE);
                addFastSpeed.setVisibility(View.GONE);
                lowFastSpeed.setVisibility(View.GONE);
                kuaijie.setVisibility(View.GONE);
                jieshu.setVisibility(View.GONE);

                //stateImg.setBackgroundColor(getResources().getColor(R.color.red_bg));
                break;
            case 1:                                           //蓝牙未连接状态
                speedLayout.setVisibility(View.GONE);
                slopeLayout.setVisibility(View.GONE);
                addFastSpeed.setVisibility(View.GONE);
                lowFastSpeed.setVisibility(View.GONE);
                kuaijie.setVisibility(View.GONE);
                jieshu.setVisibility(View.GONE);
                // stateImg.setBackgroundColor(getResources().getColor(R.color.yellow_bg));
                break;
            case 2:                                           //蓝牙连接成功状态
                speedLayout.setVisibility(View.VISIBLE);
                slopeLayout.setVisibility(View.VISIBLE);
                addFastSpeed.setVisibility(View.VISIBLE);
                lowFastSpeed.setVisibility(View.VISIBLE);
                kuaijie.setVisibility(View.VISIBLE);
                jieshu.setVisibility(View.VISIBLE);

                // stateImg.setBackgroundColor(getResources().getColor(R.color.green_bg));
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
     * 更新界面strTmp = strTmp.replaceAll ("A", "X");
     */
    public void changeActivity(BluResult bluResult) {
        speedText.setText(bluResult.speed + "");
        slopeText.setText(bluResult.slope + "");
        time.setText(bluResult.time.replace(".", ":"));
        cor.setText(bluResult.calories + "");
        heart.setText(bluResult.heart + "");
        dis.setText(bluResult.distance + "");
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

        if (bluState == 60) {     //安全锁脱落
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
    DisplayMetrics dm;

    private void showPopwindow(View parent, final ArrayList bluList) {

        HashSet h = new HashSet(bluList);
        bluList.clear();
        bluList.addAll(h);

        if (dm == null) {
            dm = new DisplayMetrics();
        }
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

                //upTextStayle("蓝牙正在连接", "蓝牙正在连接");
                if (mPopupWindow != null) {
                    mPopupWindow.dismiss();
                }
            }
        });
    }


    @OnClick({R.id.root_rv, R.id.mySpeedAdd, R.id.mySpeedMinu, R.id.mySlopeAdd, R.id.mySlopeMinu, R.id.back, R.id.addFastSpeed, R.id.lowFastSpeed})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.root_rv:
                if (caipingorlanpinFlag) {
                    if (connFlag) {
                        start();
                    } else {
                        util.connect(device);
                    }
                    break;
                }

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
                        Toast.makeText(SportHomeActivity.this, "您的手机不支持蓝牙",
                                Toast.LENGTH_SHORT).show();
                        return;//手机不支持蓝牙就啥也不用干了，关电脑睡觉去吧
                    }
                    //list.clear();
                    //test1();
                    new GetDataTask().execute();// 搜索任务

                    //-------------------------------------------------------------------------
                    //Log.i("蓝牙",device.toString());
                }
                if (bluState == 10) {               //正在停止

                    if (caipingorlanpinFlag) {      //如果是彩屏
                        start();
                    } else {
                        controller.write(bluDataUtil.startStop("1"));     //启动
                    }

                } else if (bluState == 20) {       //正在运转
                    controller.write(bluDataUtil.startStop("0"));         //停止
                    Intent intent = new Intent(SportHomeActivity.this, SportHomeRemberActivity.class);
                    intent.putExtra("sportDis", sportDis);
                    intent.putExtra("sportTime", sportTime);
                    intent.putExtra("sportCor", sportCor);
                    intent.putExtra("imageUrl", "http://ofplk6att.bkt.clouddn.com/qms.jpg");
                    startActivity(intent);
                    finish();
                }
                break;
            case R.id.mySpeedAdd:
                Log.i("速度", "速度增加");
                if (caipingorlanpinFlag) {      //如果是彩屏
                    if (mCaiSpeed < mCaiMaxSpeed)
                        mCaiSpeed++;
                    speedCtrl(mCaiSpeed);
                } else {
                    float speed1 = Float.parseFloat(speedText.getText().toString());
                    if (speed1 < maxSpeed) {
                        speed1 = speed1 + (float) 0.1;
                    } else {
                        speed1 = maxSpeed;
                    }
                    controller.write(bluDataUtil.addSpeed(decimalFormat.format(speed1)));
                }

                break;
            case R.id.mySpeedMinu:
                Log.i("速度", "速度减少");
                if (caipingorlanpinFlag) {      //如果是彩屏
                    if (mCaiSpeed > mCaiMinSpeed)
                        mCaiSpeed--;
                    speedCtrl(mCaiSpeed);
                } else {
                    float speed2 = Float.parseFloat(speedText.getText().toString());
                    if (speed2 > minSpeed) {
                        speed2 = speed2 - (float) 0.1;
                    } else {
                        speed2 = minSpeed;
                    }
                    controller.write(bluDataUtil.addSpeed(decimalFormat.format(speed2)));
                }

                break;
            case R.id.mySlopeAdd:
                Log.i("坡度", "坡度增加");
                if (caipingorlanpinFlag) {      //如果是彩屏
                    if (mCaiMaxIncline == 0)
                        return;
                    if (mCaiIncline < mCaiMaxIncline)
                        mCaiIncline++;
                    inclineCtrl(mCaiIncline);
                } else {
                    int slope1 = Integer.parseInt(slopeText.getText().toString());
                    if (slope1 < maxSlope) {
                        slope1 = slope1 + 1;
                    } else {
                        slope1 = maxSlope;
                    }
                    controller.write(bluDataUtil.addSlope(slope1 + "")); //坡度增加
                }

                break;
            case R.id.mySlopeMinu:
                Log.i("坡度", "坡度减少");
                if (caipingorlanpinFlag) {      //如果是彩屏
                    if (mCaiMaxIncline == 0)
                        return;
                    if (mCaiIncline < mCaiMaxIncline)
                        mCaiIncline++;
                    inclineCtrl(mCaiIncline);
                } else {
                    int slope2 = Integer.parseInt(slopeText.getText().toString());
                    if (slope2 > 1) {
                        slope2 = slope2 - 1;
                    } else {
                        slope2 = 0;
                    }
                    controller.write(bluDataUtil.addSlope(slope2 + ""));   //坡度减少
                }

                break;
            case R.id.back:

                finish();
                break;

            //暂停
            case R.id.addFastSpeed:
                if (caipingorlanpinFlag) {
                    stop();
                    Intent intent = new Intent(SportHomeActivity.this, SportHomeRemberActivity.class);
                    intent.putExtra("sportDis", Double.parseDouble(decimalFormat.format(sportDis)));
                    intent.putExtra("sportTime", sportTime);
                    intent.putExtra("sportCor", sportCor);
                    intent.putExtra("imageUrl", "http://ofplk6att.bkt.clouddn.com/qms.jpg");
                    startActivity(intent);
                    finish();
                } else {
                    controller.write(bluDataUtil.startStop("0"));         //停止
                }
                break;
            //快捷
            case R.id.lowFastSpeed:
                showSpeedPopwindow(getWindow().getDecorView());
        }
    }

    /**
     * 长按事件
     */
    public void onLongClickListenerEvent() {
        //加速
        mySpeedAdd.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    try {
                        float speed1 = Float.parseFloat(speedText.getText().toString());
                        if (speed1 < maxSpeed) {
                            speed1 = speed1 + (float) 0.1;
                        } else {
                            speed1 = maxSpeed;
                        }
                        BigDecimal bdc = new BigDecimal(speed1);
                        double d1 = bdc.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();

                        Log.i("速度长按", "速度增加" + d1 + "---" + maxSpeed);
                        controller.write(bluDataUtil.addSpeed(d1 + ""));
                    } catch (Exception e) {

                    }
                } else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                    step = 1;
                    try {
                        if (ds1 == null) {
                            ds1 = new SetBlue(step, handler);
                            es.execute(ds1);
                        }
                        if (t1 == null) {
                            t1 = new Thread(ds1);
                        }
                        if (!t1.isAlive()) {
                            if (t1.getState() == Thread.State.NEW) {
                                t1.start();
                            }
                        }
                    } catch (Exception e) {

                    }
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    step = 0;
                    try {
                        if (ds1 != null) {
                            ds1.setStep(step);
                            ds1 = null;
                        }
                        if (t1 != null) {
                            t1.interrupt();
                            t1 = null;
                        }
                    } catch (Exception e) {

                    }
                }
                return true;
            }
        });
        //减速
        mySpeedMinu.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    try {

                        float speed2 = Float.parseFloat(speedText.getText().toString());
                        if (speed2 > minSpeed) {
                            speed2 = speed2 - (float) 0.1;
                        } else {
                            speed2 = minSpeed;
                        }
                        BigDecimal bdc = new BigDecimal(speed2);
                        double d1 = bdc.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
                        Log.i("速度长按", "速度减少" + d1 + "---------------");
                        controller.write(bluDataUtil.addSpeed(d1 + ""));
                    } catch (Exception e) {

                    }

                } else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                    step = 2;
                    try {
                        if (ds1 == null) {
                            ds1 = new SetBlue(step, handler);
                            es.execute(ds1);
                        }
                        if (t1 == null) {
                            t1 = new Thread(ds1);
                        }
                        if (!t1.isAlive()) {
                            if (t1.getState() == Thread.State.NEW) {
                                t1.start();
                            }
                        }
                    } catch (Exception e) {

                    }
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    step = 0;
                    try {
                        if (ds1 != null) {
                            ds1.setStep(step);
                            ds1 = null;
                        }
                        if (t1 != null) {
                            t1.interrupt();
                            t1 = null;
                        }
                    } catch (Exception e) {

                    }
                }
                return true;
            }
        });

        mySlopeAdd.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                String s = slopeText.getText().toString();
                int mSlope = Integer.parseInt(s);
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    if (mSlope < 15) {
                        mSlope += 1;
//                        slopeText.setText(mSlope+"");
                        controller.write(bluDataUtil.addSlope(mSlope + "")); //坡度增加
                    } else {
                        Toast.makeText(context, "已经是最大坡度", Toast.LENGTH_SHORT).show();
                    }
//                    try {
//                        int slope1 = Integer.parseInt(slopeText.getText().toString());
//                        Log.i("坡度长按", "坡度增加");
//                        if (slope == slope1) {
//                            if (slope1 < maxSlope) {
////                                slope1 = slope1 + 1;
//                            } else {
//                                slope1 = maxSlope;
//                            }
//                        }
//                        slope = slope1;
//                        controller.write(bluDataUtil.addSlope(slope1 + "")); //坡度增加
//
//                    } catch (Exception e) {
//
//                    }

                } else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                    step = 3;
                    try {
                        if (ds1 == null) {
                            ds1 = new SetBlue(step, handler);
                            es.execute(ds1);
                        }
                        if (t1 == null) {
                            t1 = new Thread(ds1);
                        }
                        if (!t1.isAlive()) {
                            if (t1.getState() == Thread.State.NEW) {
                                t1.start();
                            }
                        }
                    } catch (Exception e) {

                    }
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    step = 0;
                    try {
                        if (ds1 != null) {
                            ds1.setStep(step);
                            ds1 = null;
                        }
                        if (t1 != null) {
                            t1.interrupt();
                            t1 = null;
                        }
                    } catch (Exception e) {

                    }
                }
                return true;
            }
        });

        mySlopeMinu.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                String s = slopeText.getText().toString();
                int mSlope = Integer.parseInt(s);
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {

                    if (mSlope > 0) {
                        mSlope -= 1;
//                        slopeText.setText(mSlope+"");
                        controller.write(bluDataUtil.addSlope(mSlope + "")); //坡度减小
                    } else {
                        Toast.makeText(context, "已经是最小坡度", Toast.LENGTH_SHORT).show();
                    }
//                    try {
//                        int slope2 = Integer.parseInt(slopeText.getText().toString());
//                        Log.i("坡度长按", "坡度减少");
//                        if (slope == slope2) {
//                            if (slope2 > 1) {
////                                slope2 = slope2 - 1;
//                            } else {
//                                slope2 = 0;
//                            }
//                        }
//                        slope = slope2;
//                        controller.write(bluDataUtil.addSlope(slope2 + ""));   //坡度减少
//
//                    } catch (Exception e) {
//
//                    }

                } else if (motionEvent.getAction() == MotionEvent.ACTION_MOVE) {
                    step = 4;
                    try {
                        if (ds1 == null) {
                            ds1 = new SetBlue(step, handler);
                            es.execute(ds1);
                        }
                        if (t1 == null) {
                            t1 = new Thread(ds1);
                        }
                        if (!t1.isAlive()) {
                            if (t1.getState() == Thread.State.NEW) {
                                t1.start();
                            }
                        }
                    } catch (Exception e) {

                    }
                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    step = 0;
                    try {
                        if (ds1 != null) {
                            ds1.setStep(step);
                            ds1 = null;
                        }
                        if (t1 != null) {
                            t1.interrupt();
                            t1 = null;
                        }
                    } catch (Exception e) {

                    }
                }

                return true;
            }
        });

    }

    /**
     * 实现Runnable接口的类
     *
     * @author leizhimin 2008-9-1 18:12:10
     */
    public static class SetBlue implements Runnable {
        public volatile int step;
        private Handler handler;

        public SetBlue(int step, Handler handler) {
            this.step = step;
            this.handler = handler;
        }

        public void setStep(int step) {
            this.step = step;
        }

        public void run() {
            while (step > 0) {
                Message msg = Message.obtain();
                msg.what = step;
                handler.sendMessage(msg);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:                         //加速
                    if (caipingorlanpinFlag) {      //如果是彩屏
                        if (mCaiSpeed < mCaiMaxSpeed)
                            mCaiSpeed++;
                        speedCtrl(mCaiSpeed);
                    } else {
                        float speed1 = Float.parseFloat(speedText.getText().toString());
                        if (speed1 < maxSpeed) {
                            speed1 = speed1 + (float) 0.1;
                        } else {
                            speed1 = maxSpeed;
                        }
                        controller.write(bluDataUtil.addSpeed(decimalFormat.format(speed1)));
                    }

                   /* float speed1 = Float.parseFloat(speedText.getText().toString());
                    if (speed1 < maxSpeed) {
                        speed1 = speed1 + (float) 0.1;
                    } else {
                        speed1 = maxSpeed;
                    }
                    BigDecimal bdc = new BigDecimal(speed1);
                    double d1 = bdc.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();

                    Log.i("加速", "加速" + d1);
                    controller.write(bluDataUtil.addSpeed(d1 + ""));*/
                    break;
                case 2:                         //减速
                    if (caipingorlanpinFlag) {      //如果是彩屏
                        if (mCaiSpeed > mCaiMinSpeed)
                            mCaiSpeed--;
                        speedCtrl(mCaiSpeed);
                    } else {
                        float speed2 = Float.parseFloat(speedText.getText().toString());
                        if (speed2 > minSpeed) {
                            speed2 = speed2 - (float) 0.1;
                        } else {
                            speed2 = minSpeed;
                        }
                        controller.write(bluDataUtil.addSpeed(decimalFormat.format(speed2)));
                    }
                    /*float speed2 = Float.parseFloat(speedText.getText().toString());
                    if (speed2 > minSpeed) {
                        speed2 = speed2 - (float) 0.1;
                    } else {
                        speed2 = minSpeed;
                    }
                    Log.i("减速", "减速-------------" + speed2);
                    controller.write(bluDataUtil.addSpeed(decimalFormat.format(speed2)));*/
                    break;
                case 3:                         //坡度加
                    if (caipingorlanpinFlag) {      //如果是彩屏
                        if (mCaiMaxIncline == 0)
                            return;
                        if (mCaiIncline < mCaiMaxIncline)
                            mCaiIncline++;
                        inclineCtrl(mCaiIncline);
                    } else {
                        int slope1 = Integer.parseInt(slopeText.getText().toString());
                        if (slope1 < maxSlope) {
                            slope1 = slope1 + 1;
                        } else {
                            slope1 = maxSlope;
                        }
                        controller.write(bluDataUtil.addSlope(slope1 + "")); //坡度增加
                    }


                    /*int slope1 = Integer.parseInt(slopeText.getText().toString());
                    Log.i(TAG, "坡度增加");
                    if (slope1 < maxSlope) {
                        slope1 = slope1 + 1;
                    } else {
                        slope1 = maxSlope;
                    }
                    controller.write(bluDataUtil.addSlope(slope1 + "")); //坡度增加*/
                    break;
                case 4:                          //坡度减
                    if (caipingorlanpinFlag) {      //如果是彩屏
                        if (mCaiMaxIncline == 0)
                            return;
                        if (mCaiIncline > 0)
                            mCaiIncline--;
                        inclineCtrl(mCaiIncline);
                    } else {
                        int slope2 = Integer.parseInt(slopeText.getText().toString());
                        if (slope2 > 1) {
                            slope2 = slope2 - 1;
                        } else {
                            slope2 = 0;
                        }
                        controller.write(bluDataUtil.addSlope(slope2 + ""));   //坡度减少
                    }


                    /*int slope2 = Integer.parseInt(slopeText.getText().toString());
                    Log.i(TAG, "坡度减少");
                    if (slope2 > 1) {
                        slope2 = slope2 - 1;
                    } else {
                        slope2 = 0;
                    }
                    controller.write(bluDataUtil.addSlope(slope2 + ""));   //坡度减少*/
                    break;
            }

        }
    };


    private void initData() {
        bluList = new ArrayList();
        deviceAdapter = new DeviceAdapter(this, list);
    }


    /**
     * 实例化服务
     */
    private void initService() {
        //开始服务
        intentService = new Intent(SportHomeActivity.this, BLEService.class);
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
            //list.clear();
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
                //toast("连接已断开");
                startStopStateText.setText("搜索蓝牙");
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
                        //Log.i("蓝牙", bluState + "------" + bluResult.state);

                    }
                }
            }
        }
    }

    //调试用，模拟收到的字符串
    public void test() {
        String str = "EE,c0,sf0,st10,mp16.0,ms15,mi0.6,ca0,dis0.00,h00,s0,p0.0,t0.00,255,FFEE,c0,sf0,st10,mp16.0,ms15,mi0.6,ca0,dis0.00,h00,s0,p0.0,t0.00,255,FF";
        bluResult = bluDataUtil.SplitStr(str, context);
        if (bluResult != null) {
            if (bluResult.code.equals("0")) {
                bluState = Integer.parseInt(bluResult.state);
                sportDis = Double.parseDouble(bluResult.distance);
                sportCor = Integer.parseInt(bluResult.calories);
                maxSpeed = Float.parseFloat(bluResult.speed);                   //最大速度
                maxSlope = Integer.parseInt(bluResult.slope);                   //最大坡度
                minSpeed = Float.parseFloat(bluResult.minSpeed);                //最小速度
                sportTime = bluResult.time;
                //Log.i("蓝牙",bluState + "--" + sportDis + "--" + sportCor + "--" + maxSpeed + "--" + maxSlope + "--" + minSpeed);
                changeActivity(bluResult);
            }
        }
    }

    BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
    Class<BluetoothAdapter> bluetoothAdapterClass = BluetoothAdapter.class;//得到BluetoothAdapter的Class对象

    public void test1() {
        try {//得到连接状态的方法
            Method method = bluetoothAdapterClass.getDeclaredMethod("getConnectionState", (Class[]) null);
            //打开权限
            method.setAccessible(true);
            int state = (int) method.invoke(adapter, (Object[]) null);

            if (state == BluetoothAdapter.STATE_DISCONNECTED) {
                Log.i("BLUETOOTH", "BluetoothAdapter.STATE_CONNECTED");
                Set<BluetoothDevice> devices = adapter.getBondedDevices();
                Log.i("BLUETOOTH", "devices:" + devices.size());

                for (BluetoothDevice device : devices) {
                    Method isConnectedMethod = BluetoothDevice.class.getDeclaredMethod("isConnected", (Class[]) null);
                    method.setAccessible(true);
                    // boolean isConnected = (boolean) isConnectedMethod.invoke(device, (Object[]) null);
                    // if(devices.size() > 0){
                    Log.i("BLUETOOTH", "connected:" + device.getName());
                    EntityDevice temp = new EntityDevice();
                    temp.setName(device.getName());
                    temp.setAddress(device.getAddress());
                    list.add(temp);
                    // }
                }

                showPopwindow(getWindow().getDecorView(), list);              //显示蓝牙列表
                deviceAdapter.notifyDataSetChanged();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 点击停止按钮弹框，跑步距离少于一千米，不上传数据，询问是否继续
     */
    public void getDialog(final int dis, final int cor, final int time) {
        View view = LayoutInflater.from(context).inflate(R.layout.update_homesportdata, null);
        ((TextView) view.findViewById(R.id.versionCode)).setText("跑步记录");
        ((TextView) view.findViewById(R.id.versionText)).setText("当期跑步距离少于 1 千米" + "\n" + "无法保存记录");

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

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * 销毁
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (caipingorlanpinFlag) {

        } else {
            stopService(intentService);
        }
        unregisterReceiver(receiver);
        unregisterReceiver(bluetoothState);
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
                    if (caipingorlanpinFlag) {
                        speedCtrl((position + 3) * 10);
                    } else {
                        controller.write(bluDataUtil.addSpeed(String.valueOf(position + 3)));
                    }
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

    //todo 彩屏控制
//    private Handler mHandler = new Handler(new Handler.Callback() {
//        @Override
//        public boolean handleMessage(Message msg) {
//            switch (msg.what) {
//                case BluetoothUtil.MSG_READ:
//                    String data = (String) msg.obj;
//                    try {
//                        JSONObject object = new JSONObject(data);
//
//                        mCaiMinSpeed = object.getInt("minSpeed");
//                        mCaiMaxSpeed = object.getInt("maxSpeed");
//                        mCaiMaxIncline = object.getInt("maxIncline");
//
//                        JSONObject param = object.getJSONObject("param");
//                        mCaiSpeed = param.getInt("speed");            //速度
//                        mCaiIncline = param.getInt("incline");       //坡度
//
//                        sportTime = DateUtil.changeTime(param.getInt("duration"));
//                        sportDis = param.getDouble("distance");      //距离
//                        sportCor = (int)param.getDouble("calorie");      //卡路里
//                        int hr = param.getInt("heartBeat");          //心率
//
//                        speedText.setText(decimalFormat.format((float)mCaiSpeed/10) + "");
//                        slopeText.setText(decimalFormat.format(mCaiIncline) + "");
//                        time.setText(sportTime);
//                        cor.setText(decimalFormat.format(sportCor) + "");
//                        heart.setText(decimalFormat.format(hr) + "");
//                        dis.setText(decimalFormat.format(sportDis) + "");
//
//                        //收到的数据
//                        //  String str = String.format(Locale.getDefault(),"speed = %.1f, incline = %d, time = %s, dist = %.1f, cal = %.1f,hr = %d,",mCaiSpeed / 10f,mCaiIncline,withFormatTime(dur),dis,cal,hr);
//                    } catch (JSONException e) {
//                        e.printStackTrace();
//                    }
//                    break;
//                case BluetoothUtil.MSG_STATE_CHANGE:
//                    int state = msg.arg1;
//                    switch (state) {
//                        case BluetoothUtil.STATE_CONNECTING:
//                            toast("正在连接！");
//                            break;
//                        case BluetoothUtil.STATE_CONNECT_FAIL:
//                            toast("连接失败！");
//                            connFlag = false;
//                            break;
//                        case BluetoothUtil.STATE_CONNECTED:
//                            toast("已连接，可以通信了！");
//                            startStopStateText.setText("开始");
//                            connFlag = true;
//                            bluState = 20;
//                            speedLayout.setVisibility(View.VISIBLE);
//                            slopeLayout.setVisibility(View.VISIBLE);
//                            addFastSpeed.setVisibility(View.VISIBLE);
//                            lowFastSpeed.setVisibility(View.VISIBLE);
//                            kuaijie.setVisibility(View.VISIBLE);
//                            jieshu.setVisibility(View.VISIBLE);
//                            break;
//                        case BluetoothUtil.STATE_CONNECTION_LOST:
//                            toast("连接已断开！");
//                            connFlag = false;
//                            break;
//                    }
//                    break;
//            }
//            return true;
//        }
//    });

    void toast(String str) {
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    public String withFormatTime(int time) {
        int dur = time / 1000;
        int h = dur / 3600;
        int m = (dur - h * 3600) / 60;
        int s = dur - (h * 3600 + m * 60);
        String durationValue;
        if (h == 0) {
            durationValue = String.format(Locale.getDefault(), "%02d:%02d", m, s);
        } else {
            durationValue = String.format(Locale.getDefault(), "%d:%02d:%02d", h, m, s);
        }
        return durationValue;
    }


    void start() {
//        try {
//            JSONObject object = new JSONObject();
//            object.put("type", 100);
//
//            util.write(object.toString().getBytes());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }

    void stop() {
//        try {
//            JSONObject object = new JSONObject();
//            object.put("type", 101);
//
//            util.write(object.toString().getBytes());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }

    void speedCtrl(int speed) {
//        try {
//            JSONObject object = new JSONObject();
//            object.put("type", 102);
//            object.put("speed", speed);
//            util.write(object.toString().getBytes());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }

    void inclineCtrl(int incline) {
//        try {
//            JSONObject object = new JSONObject();
//            object.put("type", 103);
//            object.put("incline", incline);
//            util.write(object.toString().getBytes());
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (util != null) {
            util.disconnect();
        }
    }

    /**
     * 性别弹出框
     */
    public void sexDialog() {
//        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.caipingorlanping_layout, null);
//        sexText = (TextView) view.findViewById(R.id.sexText);
//        boyImg = (ImageView) view.findViewById(R.id.boyImg);
//        girlImg = (ImageView) view.findViewById(R.id.girlImg);
//        boyImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                boyImg.setAlpha((float) 1);
//                girlImg.setAlpha((float) 0.3);
//                sexText.setText("蓝屏");
//                caipingorlanpinFlag = false;
//            }
//        });
//        girlImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                boyImg.setAlpha((float) 0.3);
//                girlImg.setAlpha((float) 1);
//                sexText.setText("彩屏");
//
//                caipingorlanpinFlag = true;
//            }
//        });
//        AlertDialog alertDialog = new AlertDialog.Builder(this)
//                .setView(view)
//                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int arg1) {
//                        if (caipingorlanpinFlag) {
//                            //todo 彩屏
//                            util = new BluetoothUtil(mHandler);
//                            Set<BluetoothDevice> devices = BluetoothAdapter.getDefaultAdapter().getBondedDevices();
//                            device = devices.iterator().next();//只限于有一个绑定设备的情况
//
//                            if (device != null && d)
//                                Log.e(TAG, "name = " + device.getName() + ", addr = " + device.getAddress());
//
//                            util.connect(device);
//
//                        } else {
//                            initService();
//                        }
//                    }
//                }).create();
////        alertDialog.show();
    }
}
