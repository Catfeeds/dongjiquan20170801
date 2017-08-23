package com.weiaibenpao.demo.chislim.ui;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.bean.AddInterestSportResult;
import com.weiaibenpao.demo.chislim.bean.LatLongRemberBean;
import com.weiaibenpao.demo.chislim.bean.UserBean;
import com.weiaibenpao.demo.chislim.map.util.WalkUtil;
import com.weiaibenpao.demo.chislim.model.MyModel;
import com.weiaibenpao.demo.chislim.service.Map_Service;
import com.weiaibenpao.demo.chislim.settings.TtsSettings;
import com.weiaibenpao.demo.chislim.util.CircleChart02View;
import com.weiaibenpao.demo.chislim.util.LatLong_impl;
import com.weiaibenpao.demo.chislim.util.VoicePlayerUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SportOutDistrictActivity extends AppCompatActivity implements LocationSource {

    @BindView(R.id.mapView)
    MapView mapView;
    Context context;

    AMap aMap;

    PolygonOptions pOption;

    LatLonPoint centerLatLng;//行政区域的中心点坐标

    static ArrayList<LatLng> arrayList;
    ArrayList myLatLong;              //已绘制轨迹点存放

    Polyline polyline;
    PolylineOptions polylineOptions;

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
    ImageView start;
    @BindView(R.id.suo)
    ImageView suo;
    @BindView(R.id.stop)
    ImageView stop;
    @BindView(R.id.textWord)
    TextView textWord;
    @BindView(R.id.btn_goto)
    Button btnGoto;
    @BindView(R.id.myTime)
    TextView myTime;
    @BindView(R.id.circle_view)
    CircleChart02View circleView;
    @BindView(R.id.showView)
    LinearLayout showView;
    @BindView(R.id.startView)
    RelativeLayout startView;
    @BindView(R.id.timeView)
    RelativeLayout timeView;
    @BindView(R.id.lockView)
    RelativeLayout lockView;
    @BindView(R.id.myTop)
    RelativeLayout myTop;

    String disOld;               //运动记录
    private Map_Service map_Service;  //服务对象

    MyBroadcast myBroadcast;  //广播对象
    LatLong_impl latLong_impl;
    String provinceName;
    LatLongRemberBean latLongRemberBean;
    private double mLocatinLat;
    private double mLocationLon;

    private ArrayList<LatLng> mLocationList = new ArrayList<LatLng>();
    private VoicePlayerUtil voicePlayerUtil;
    private SharedPreferences mSharedPreferences;
    String disData;                //当前运动距离
    String cor;                    //卡路里
    String sporttime;             //时间
    String current_time;
    String step;
    String speed;
    double distance = 0;
    /**
     * 0 服务启动
     * 1 正常运动
     * 2 暂停
     * 3 继续
     * 4 停止
     */
    private int state;

    Intent intentServices;    //启动
    private boolean mIsStart = false;
    int i = 0;

    DecimalFormat decimalFormat = new DecimalFormat("0.00");

    WindowManager wm;
    int widthScreen;                  //获取手屏幕跨度
    int heightScreen;
    boolean screenFlag = false;         //是否地图大屏
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("onCreate","SportOutDistrictActivity");
        setContentView(R.layout.activity_sport_out_district);
        ButterKnife.bind(this);

        mapView.onCreate(savedInstanceState);         // 此方法必须重写
        context = getApplicationContext();
        mSharedPreferences = getSharedPreferences(TtsSettings.PREFER_NAME, MODE_PRIVATE);
        voicePlayerUtil = new VoicePlayerUtil(SportOutDistrictActivity.this.getApplicationContext(),mSharedPreferences);
        latLong_impl = new LatLong_impl(context);
        current_time = latLong_impl.getDate();
        arrayList = new ArrayList();
        myLatLong = new ArrayList();

        initMap();

        provinceName = getIntent().getStringExtra("provinceName");
        disOld = getIntent().getStringExtra("disOld");
        getDistrict(provinceName);

        //配置广播
        myBroadcast = new MyBroadcast();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.lijianbao.mapLocationLatLong");
        SportOutDistrictActivity.this.registerReceiver(myBroadcast, filter);

        circleView.setOnTouchListener(new View.OnTouchListener() {
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
                        myTop.setVisibility(View.VISIBLE);
                    }
                    circleView.setPercentage(i);

                } else if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    Log.i("周期", "结束");
                    i = 0;
                    circleView.setPercentage(i);
                }
                circleView.chartRender();
                circleView.invalidate();
                return true;
            }
        });
    }

    /**
     * 实例化地图
     */
    public void initMap() {
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        aMap.setTrafficEnabled(true);           // 显示实时交通状况
        //地图模式可选类型：MAP_TYPE_NORMAL,MAP_TYPE_SATELLITE,MAP_TYPE_NIGHT
        aMap.setMapType(AMap.MAP_TYPE_SATELLITE);// 地图模式

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

    public void getDistrict(String str) {
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

                    int count =0;


                    public void run() {

                        String[] polyStr = item.districtBoundary();
                        if (polyStr == null || polyStr.length == 0) {
                            return;
                        }
                        Log.e("SportOutDistrictBefore","arrayList size is "+arrayList.size());
                        for (String str : polyStr) {
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


                                count++;

                            }
                            if (firstLatLng != null) {
                                polylineOption.add(firstLatLng);
                            }
                            polylineOption.width(6).color(Color.BLUE);
                            getDistance(arrayList);
                            Message message = handler.obtainMessage();
                            message.obj = polylineOption;
                            handler.sendMessage(message);
                        }

                        Log.e("new Thread ","循环次数是"+count);
//                        Log.e("SportOutDistrict after","arrayList size is "+arrayList.size());

                        pOption = new PolygonOptions();
                        pOption.addAll(polylineOption.getPoints());//转换成PolygonOptions类型，为了判断marker是否在区域内

                    }
                }.start();
            }
        });            //绑定监听器

//        search.searchDistrictAnsy();//开始搜索
        search.searchDistrictAsyn();
    }

    /**
     * 绘制轨迹
     */
    int h = 0;
    public void huizhi(int dis) {          //dis  单位米

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
        polylineOptions.colorValues(WalkUtil.getColorList(myLatLong.size()/144+1,this));
        // polylineOptions.color(Color.argb(255, 222, 1, 1));
        //加上这个属性，表示使用渐变线
        polylineOptions.useGradient(true);
        polyline = aMap.addPolyline(polylineOptions);


        setMarkView();
    }

    private ArrayList<LatLng> mTable = new ArrayList<>();
    //设置没跑一公里的坐标标记点
    public void setMarkView(){
        for (int i = 0; i < mTable.size(); i++) {
            View view = View.inflate(SportOutDistrictActivity.this,R.layout.mark_layout, null);

            TextView sportMark = (TextView) view.findViewById(R.id.sportTitle);

            sportMark.setText("起");
            Bitmap bitmap = SportOutDistrictActivity.convertViewToBitmap(view);
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
     * 计算路线长度轨迹
     */
    public void getDistance(ArrayList<LatLng> arrayList) {
        for (int i = 0; i < arrayList.size() - 1; i++) {
            distance = distance + AMapUtils.calculateLineDistance(arrayList.get(i), arrayList.get(i + 1));
        }
        Log.i("规划", distance + "----");
    }

    /**
     * 切换地图显示中心点
     *
     * @param latitude
     * @param longitude
     */
    public void setIntro(double latitude, double longitude) {
        LatLng marker1 = new LatLng(latitude, longitude);
        //设置中心点和缩放比例
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(marker1));
        aMap.moveCamera(CameraUpdateFactory.zoomTo(15));
    }

    private Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            PolylineOptions polylineOption = (PolylineOptions) msg.obj;
            aMap.addPolyline(polylineOption);
            Log.i("行政区划", "---------" + arrayList.size());

//            getDistance(arrayList);
        }
    };

    @OnClick({ R.id.btn_goto, R.id.suo, R.id.stop, R.id.start})
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
                stopSports();
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
    int disTab = 0;

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {

    }

    @Override
    public void deactivate() {

    }


    /**
     * 上传数据
     */
    public void uploadData(String cityName,String finishPercent,String allDis,String nowDis,String sporttime){
        Call<AddInterestSportResult> call = MyModel.getModel().getService().AddInterestSportResult(UserBean.getUserBean().userId,
                cityName, finishPercent, allDis, nowDis,sporttime);

        call.enqueue(new Callback<AddInterestSportResult>() {
            @Override
            public void onResponse(Call<AddInterestSportResult> call, Response<AddInterestSportResult> response) {
                if (response.isSuccessful()) {
                    AddInterestSportResult result = response.body();

                    if (result.getCode() == 0) {
                    }else{
                        Toast.makeText(context,"教程获取失败",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<AddInterestSportResult> call, Throwable t) {
                // Toast.makeText(context,"教程获取失败",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 音乐广播
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
            LatLng latLng = (LatLng) intent.getParcelableExtra("latlong");
            if(latLng == null){
                return;//added by zjl
            }

            mLocatinLat = latLng.latitude;
            mLocationLon = latLng.longitude;                                          //当前定位
            mLocationList = intent.getParcelableArrayListExtra("mLocationList");     //当前运动过路线

            disData = intent.getStringExtra("dis");                              //距离  单位米
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

            if (disTab != Integer.parseInt(disData) && p%5 == 0) {
                huizhi(Integer.parseInt(disData) + (int)Double.parseDouble(disOld)*1000);
                disTab = Integer.parseInt(disData);
                p++;
            }
        }
    }
    int p = 0;
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
        intentServices = new Intent(SportOutDistrictActivity.this, Map_Service.class);
        if (!isServiceRunning(context, "com.weiaibenpao.demo.chislim.service.Map_Service")) {
            intentServices.putExtra("activityContext","com.jianbaopp.SportOutDistrictActivity");
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
                        //   btnStart.setText("停止");              //设置显示为停止
                    }
                    break;
            }
        }
    };

    /**
     * 重写onKeyDown方法
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if((keyCode == KeyEvent.KEYCODE_BACK)){
            if(state != 0){
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
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
        builder.setTitle("跑步提示");
        builder.setMessage("跑步还在进行中，请结束后退出？");
        builder.setNegativeButton("结束", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                stopSports();
            }
        });
        builder.setPositiveButton("继续", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.show();
    }

    /**
     * 跑步结束
     */
    public void stopSports(){
        voicePlayerUtil.startVoice("跑步停止！");

        if(TextUtils.isEmpty(disData)){
            disData="0";
        }

        if(TextUtils.isEmpty(disOld)){
            disOld = "0";
        }
        uploadData(provinceName,"",decimalFormat.format((distance/1000)),decimalFormat.format(Double.parseDouble(disData)/1000 + Double.parseDouble(disOld)),current_time);

        mIsStart = false;
        map_Service.setNum(4);

        Intent intent1 = new Intent(SportOutDistrictActivity.this, MySportDisRememerActivity.class);
        intent1.putExtra("provinceName",provinceName);
        Log.i("建宝",String.valueOf(Double.parseDouble(disData)/1000 + disOld));
        intent1.putExtra("huitu",String.valueOf(Double.parseDouble(disData)/1000 + Double.parseDouble(disOld)));
        intent1.putExtra("cor",cor);
        intent1.putExtra("sporttime",sporttime);
        intent1.putExtra("step",step);
        intent1.putExtra("speed",speed);
        intent1.putExtra("distance",distance);
        Log.e("stopSports","array list size is "+arrayList.size());
//        intent1.putParcelableArrayListExtra("latlongArraylist",arrayList);
//        int totalCount = arrayList.size();
//        int groupCount = 20;
//        int groupSize = totalCount/groupCount;
//        int remainder = totalCount % groupCount;
//        if(remainder>0){
//            groupCount +=1;
//        }
//        Log.e("stopSports","totalCount is "+totalCount+"\ngroupSize is "+groupSize+"\nremainder is "+remainder+"\ngroupCount is "+groupCount);
//        for(int i =0;i<groupCount;i++){
//            ArrayList<LatLng> latLng = new ArrayList<>();
//            int index;
//            for(int j=0;j<groupSize;j++){
//                index = j+i*groupSize;
//                if(index<=totalCount-1){
//                    latLng.add(arrayList.get(index));
//                }else {
//                    break;
//                }
//            }
//            intent1.putParcelableArrayListExtra("latlongArraylist"+i,latLng);
//        }
//        intent1.putExtra("group_count",groupCount);

        intent1.putExtra("centerLatLng",centerLatLng);
        startActivity(intent1);
        finish();
    }

    private void releaseResource() {
        unbindService(connection);                         //解除绑定服务
        unregisterReceiver(myBroadcast);
        hander.removeCallbacksAndMessages(null);
        map_Service.stopService(intentServices);         //关闭启动式服务
        map_Service.stopSelf();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseResource();
    }
}
