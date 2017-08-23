package com.weiaibenpao.demo.chislim.ui;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
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
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.PolygonOptions;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.district.DistrictItem;
import com.amap.api.services.district.DistrictResult;
import com.amap.api.services.district.DistrictSearch;
import com.amap.api.services.district.DistrictSearchQuery;
import com.jaeger.library.StatusBarUtil;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.bean.AddInterestSportResult;
import com.weiaibenpao.demo.chislim.bean.BluResult;
import com.weiaibenpao.demo.chislim.bean.UserBean;
import com.weiaibenpao.demo.chislim.ble.adapter.DeviceAdapter;
import com.weiaibenpao.demo.chislim.ble.entity.EntityDevice;
import com.weiaibenpao.demo.chislim.ble.service.BLEService;
import com.weiaibenpao.demo.chislim.ble.utils.BluetoothController;
import com.weiaibenpao.demo.chislim.ble.utils.ConstantUtils;
import com.weiaibenpao.demo.chislim.ble.utils.Util;
import com.weiaibenpao.demo.chislim.model.MyModel;
import com.weiaibenpao.demo.chislim.music.bean.Bean_music;
import com.weiaibenpao.demo.chislim.music.dao.Dao_Get_music;
import com.weiaibenpao.demo.chislim.music.service.Start_Service;
import com.weiaibenpao.demo.chislim.util.BluDataUtil;
import com.weiaibenpao.demo.chislim.util.DateUtil;
import com.weiaibenpao.demo.chislim.util.Default;
import com.weiaibenpao.demo.chislim.util.LatLong_impl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//室内行政 区域跑
public class SportHomeDistrictActivity extends Activity implements LocationSource {

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
    RelativeLayout mode;
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
    @BindView(R.id.music_mode)
    LinearLayout musicMode;
    @BindView(R.id.maplocationLayout)
    LinearLayout maplocationLayout;
    @BindView(R.id.kuaijie)
    RelativeLayout kuaijie;
    @BindView(R.id.startStopState_Text)
    TextView startStopStateText;
    @BindView(R.id.addFastSpeed)
    ImageView addFastSpeed;
    @BindView(R.id.lowFastSpeed)
    ImageView lowFastSpeed;
    @BindView(R.id.jieshu)
    RelativeLayout jieshu;
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
    PolygonOptions pOption;
    public int bluState = 0;
    public double sportDis;   //运动里程
    public String sportTime;   //运动时间
    public int sportCor;    //消耗卡路里
    public float minSpeed;    //最小速度
    public float maxSpeed;      //最大速度
    public int maxSlope;     //最大坡度

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

    ArrayList<LatLng> arrayList;              //存放规划路线点的坐标
    ArrayList myLatLong;              //已绘制轨迹点存放

    Context context;

    Polyline polyline;
    PolylineOptions polylineOptions;

    int position;
    String current_time;
    double distance;               //路线规划的总距离
    String provinceName;
    String currentDistance;
    String disOld;               //运动记录
    LatLonPoint centerLatLng;//行政区域的中心点坐标

    int state;
    String imageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport_home_district);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this , getResources().getColor(R.color.ku_bg ) , 0);
        context = getApplicationContext();
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，实现地图生命周期管理
        map.onCreate(savedInstanceState);// 此方法必须重写
        latLong_impl = new LatLong_impl(context);
        current_time = latLong_impl.getDate();
        arrayList = new ArrayList();
        myLatLong = new ArrayList();

        list = new ArrayList<EntityDevice>();

        //第一步实例化地图
        initMap();

        provinceName = getIntent().getStringExtra("provinceName");
        disOld = getIntent().getStringExtra("disOld");
        getDistrict(provinceName);

        initReceiver2();          //蓝牙模块状态
        initService();
        initData();
        registerReceiver();
        MyConTrol(0);

        latLong_impl = new LatLong_impl(context);

        decimalFormat = new DecimalFormat("0.0");//构造方法的字符格式这里如果小数不足2位,会以0补足.

        myIntent = getIntent();
        disPlan = myIntent.getIntExtra("dis", 1000);
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
        aMap.setMapType(AMap.MAP_TYPE_NORMAL);// 地图模式

        aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {

            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {

            }
        });
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


    public void getDistrict(String str) {
        Log.e("getDistrict","province is "+str);
        final String province  = str;
        DistrictSearch search = new DistrictSearch(context);
        DistrictSearchQuery query = new DistrictSearchQuery();
        query.setKeywords(str);//传入关键字
        //query.setPageNum();
        query.setShowBoundary(true);//是否返回边界值
        search.setQuery(query);

        search.setOnDistrictSearchListener(new DistrictSearch.OnDistrictSearchListener() {
            @Override
            public void onDistrictSearched(DistrictResult districtResult) {
                //在回调函数中解析districtResult获取行政区划信息
                //在districtResult.getAMapException().getErrorCode()=1000时调用districtResult.getDistrict()方法
                //获取查询行政区的结果，详细信息可以参考DistrictItem类。

                if (districtResult == null || districtResult.getDistrict() == null) {
                    return;
                }
                final DistrictItem item = districtResult.getDistrict().get(0);

                if (item == null) {
                    return;
                }
                centerLatLng = item.getCenter();//得到行政中心点坐标
                if (centerLatLng != null) {  //地图加载时就显示行政区域
                    aMap.moveCamera(
                            CameraUpdateFactory.newLatLngZoom(new LatLng(centerLatLng.getLatitude(), centerLatLng.getLongitude()), 5));//13为缩放级别
                    aMap.moveCamera(CameraUpdateFactory.zoomTo(12));
                    //CameraUpdateFactory.zoomToSpan();               //// 缩放地图，使所有overlay都在合适的视野范围内
                    CameraUpdateFactory.zoomBy(10);
                }

                new Thread() {
                    private PolylineOptions polylineOption;


                    public void run() {

                        String[] polyStr = item.districtBoundary();
                        if (polyStr == null || polyStr.length == 0) {
                            return;
                        }
                        Log.e("new Thread","polyStr length is "+polyStr.length);//浙江是522,上海11 重庆10,天津和北京都是1,河北是1,河南是7
//                        int count =0 ;
                        for (String str : polyStr) {//当城市是重庆时,这个循环体第九次执行耗费时间竟然接近10秒!!! 原因很简单,因为第九个字符串长度比其他长好几倍!
//                            Log.e("poly str array ",province+" poly str is "+str);
//                            Log.e("loop "+count," before execute  "+System.currentTimeMillis());
                            if(isFinishing()){
                                return;
                            }


                            String[] lat = str.split(";");
                            polylineOption = new PolylineOptions();
                            boolean isFirst = true;
                            LatLng firstLatLng = null;
                            for (String latstr : lat) {
                                if(isFinishing()){
                                    return;
                                }
                                String[] lats = latstr.split(",");
                                if (isFirst) {
                                    isFirst = false;
                                    firstLatLng = new LatLng(Double
                                            .parseDouble(lats[1]), Double
                                            .parseDouble(lats[0]));
                                }
                                polylineOption.add(new LatLng(Double
                                        .parseDouble(lats[1]), Double
                                        .parseDouble(lats[0])));

                                Log.i("行政区划", "---------" + Double
                                        .parseDouble(lats[1]) + " ==========" + Double
                                        .parseDouble(lats[0]));
                                arrayList.add(new LatLng(Double
                                        .parseDouble(lats[1]), Double
                                        .parseDouble(lats[0])));
                            }
                            if (firstLatLng != null) {
                                polylineOption.add(firstLatLng);
                            }

                            polylineOption.width(6).color(Color.BLUE);
                            getDistance(arrayList);
                            Message message = handler.obtainMessage();
                            message.obj = polylineOption;
                            handler.sendMessage(message);
//                            Log.e("iterate loop "+count," after execute  "+System.currentTimeMillis());
//                            count++;
                        }

                        pOption = new PolygonOptions();
                        pOption.addAll(polylineOption.getPoints());//转换成PolygonOptions类型，为了判断marker是否在区域内

                    }
                }.start();
            }
        });//绑定监听器

//        search.searchDistrictAnsy();//开始搜索
        search.searchDistrictAsyn();//开始搜索
    }


    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            PolylineOptions polylineOption = (PolylineOptions) msg.obj;
            aMap.addPolyline(polylineOption);
            Log.e("行政区划", "---------" + arrayList.size());

//            getDistance(arrayList);
        }
    };


    /**
     * 计算路线长度轨迹
     */
    public void getDistance(ArrayList<LatLng> arrayList) {
        for (int i = 0; i < arrayList.size() - 1; i++) {
            distance = distance + AMapUtils.calculateLineDistance(arrayList.get(i), arrayList.get(i + 1));
        }

        //总里程
        Log.e("getDistance", distance + "----is the distance");
    }

    /**
     * 绘制轨迹
     */
    int h = 0;
    int disp = 0;
    public void huizhi(int dis) {

        myLatLong.clear();

        if (dis >= distance) {
            h = arrayList.size();
        } else {
            h = (int) (arrayList.size() * (dis / distance));
            Log.i("行政区划", "---------" + dis / distance);
        }
        mTable.add(arrayList.get(0));
        for (int m = 0; m < h; m++) {
            //setIntro(((LatLng)arrayList.get(m)).latitude,((LatLng)arrayList.get(m)).longitude);            //设置地图中心点
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
        polylineOptions.color(Color.argb(255, 222, 1, 1));
        //加上这个属性，表示使用渐变线
        polylineOptions.useGradient(true);
        polyline = aMap.addPolyline(polylineOptions);

        setMarkView();
    }


    private ArrayList<LatLng> mTable = new ArrayList<>();
    //设置没跑一公里的坐标标记点
    public void setMarkView(){
        for (int i = 0; i < mTable.size(); i++) {
            View view = View.inflate(SportHomeDistrictActivity.this,R.layout.mark_layout, null);

            TextView sportMark = (TextView) view.findViewById(R.id.sportTitle);

            sportMark.setText("起");
            Bitmap bitmap = SportHomeDistrictActivity.convertViewToBitmap(view);
            drawMarkerOnMap(mTable.get(i), bitmap, String.valueOf("起"));
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

    /**
     * 切换地图显示中心点
     */
    public void setIntro(double latNow, double longNow) {
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
        unregisterReceiver(bluetoothState);
        if(handler!=null) {
            handler.removeCallbacksAndMessages(null);
        }
    }

    @OnClick(R.id.back)
    public void onClick() {
        finish();
    }

    @OnClick({R.id.mySpeedAdd, R.id.mySpeedMinu, R.id.mySlopeAdd, R.id.mySlopeMinu, R.id.root_rv, R.id.addFastSpeed, R.id.lowFastSpeed, R.id.music_mode, R.id.maplocationLayout})
    public void onClick(View view) {
        switch (view.getId()) {
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
                        Toast.makeText(SportHomeDistrictActivity.this, "您的手机不支持蓝牙",
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
                    uploadData(provinceName, "", decimalFormat.format(distance / 1000), decimalFormat.format(Double.parseDouble(currentDistance) + Double.parseDouble(disOld)), current_time);
                    getScreen();
                    controller.write(bluDataUtil.startStop("0"));         //停止
                }
                break;
            case R.id.addFastSpeed:
                controller.write(bluDataUtil.addSpeed(String.valueOf(0.6)));
                break;
            case R.id.lowFastSpeed:
                showSpeedPopwindow(getWindow().getDecorView());
                break;
            case R.id.music_mode:

                break;
            case R.id.maplocationLayout:
                if (!maplocationLayoutflag) {
                    map.setVisibility(View.VISIBLE);
                    maplocationLayoutflag = true;
                } else {
                    map.setVisibility(View.GONE);
                    maplocationLayoutflag = false;
                }
                break;
        }
    }

    boolean maplocationLayoutflag = false;


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
                        Toast.makeText(SportHomeDistrictActivity.this, "您的手机不支持蓝牙",
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
                break;
            case 1:                                           //蓝牙未连接状态
                speedLayout.setVisibility(View.INVISIBLE);
                slopeLayout.setVisibility(View.INVISIBLE);
                break;
            case 2:                                           //蓝牙连接成功状态
                speedLayout.setVisibility(View.VISIBLE);
                slopeLayout.setVisibility(View.VISIBLE);
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
    int disTab = 0;

    public void changeActivity(BluResult bluResult) {
        speedText.setText(bluResult.speed + "");
        slopeText.setText(bluResult.slope + "");
        time.setText(bluResult.time.replace (".", ":"));
        cor.setText(bluResult.calories + "");
        heart.setText(bluResult.heart + "");
        currentDistance = bluResult.distance;
        //当前实时距离
        dis.setText(currentDistance);

        if (disTab != (int) (Double.parseDouble(bluResult.distance) * 1000)) {
            huizhi((int) (Double.parseDouble(bluResult.distance) * 1000) + (int) Double.parseDouble(disOld) * 1000);
            disTab = (int) (Double.parseDouble(bluResult.distance) * 1000);
        }

        if (bluState == 20) {
            //upTextStayle("停止", "蓝牙已连接");
            startStopStateText.setText("停止");
            speedLayout.setVisibility(View.VISIBLE);
            slopeLayout.setVisibility(View.VISIBLE);
            addFastSpeed.setVisibility(View.VISIBLE);
            lowFastSpeed.setVisibility(View.VISIBLE);
            kuaijie.setVisibility(View.VISIBLE);
            jieshu.setVisibility(View.VISIBLE);
        }

        if (bluState == 30) {
            //upTextStayle("正在加速", "蓝牙已连接");
            startStopStateText.setText("正在加速");
            speedLayout.setVisibility(View.VISIBLE);
            slopeLayout.setVisibility(View.VISIBLE);
            addFastSpeed.setVisibility(View.VISIBLE);
            lowFastSpeed.setVisibility(View.VISIBLE);
            kuaijie.setVisibility(View.VISIBLE);
            jieshu.setVisibility(View.VISIBLE);
        }

        if (bluState == 40) {
            //upTextStayle("正在减速", "蓝牙已连接");
            startStopStateText.setText("正在减速");
            speedLayout.setVisibility(View.VISIBLE);
            slopeLayout.setVisibility(View.VISIBLE);
            addFastSpeed.setVisibility(View.VISIBLE);
            lowFastSpeed.setVisibility(View.VISIBLE);
            kuaijie.setVisibility(View.VISIBLE);
            jieshu.setVisibility(View.VISIBLE);
        }

        if (bluState == 10) {
            //upTextStayle("开始", "蓝牙已连接");
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

        MyConTrol(2);
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

                // upTextStayle("蓝牙正在连接", "蓝牙正在连接");
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

    /**
     * 实例化服务
     */
    private void initService() {
        //开始服务
        intentService = new Intent(SportHomeDistrictActivity.this, BLEService.class);
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

    int disTab1 = 0;

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {

    }

    @Override
    public void deactivate() {

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
        Toast.makeText(SportHomeDistrictActivity.this, str, Toast.LENGTH_SHORT).show();
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
        map.setVisibility(View.VISIBLE);
        maplocationLayoutflag = true;
        new Thread(new Runnable() {
            @Override
            public void run() {

                Log.i("截屏", "截屏文件已保存至");
                //开一个线程来截图，就不会出现屏幕卡顿的现象 /storage/emulated/0/test_20161220155212.png   /storage/emulated/0/Test_ScreenShots/Screenshot_2017-01-10-11-34-54.png
                cutMapScreen();             //地图截屏
            }
        }).start();
    }


    /**
     * 上传数据
     */
    public void uploadData(String cityName, String finishPercent, String allDis, String nowDis, String sporttime) {
        Call<AddInterestSportResult> call = MyModel.getModel().getService().AddInterestSportResult(UserBean.getUserBean().userId,
                cityName, finishPercent, allDis, nowDis, sporttime);

        call.enqueue(new Callback<AddInterestSportResult>() {
            @Override
            public void onResponse(Call<AddInterestSportResult> call, Response<AddInterestSportResult> response) {
                if (response.isSuccessful()) {
                    AddInterestSportResult result = response.body();

                    if (result.getCode() == 0) {
                    } else {
                        Toast.makeText(context, "上传数据失败", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<AddInterestSportResult> call, Throwable t) {
                // Toast.makeText(context,"教程获取失败",Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void goIntent() {
        Intent intent = new Intent(SportHomeDistrictActivity.this, SportHomeRemberActivity.class);
        intent.putExtra("sportDis", sportDis);
        intent.putExtra("sportTime", sportTime);
        intent.putExtra("sportCor", sportCor);
        intent.putExtra("imageUrl", imageUrl);
        startActivity(intent);
        finish();
    }

}
