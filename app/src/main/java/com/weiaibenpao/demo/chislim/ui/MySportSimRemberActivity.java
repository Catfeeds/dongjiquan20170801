package com.weiaibenpao.demo.chislim.ui;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
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
import com.qiniu.android.common.Zone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.weiaibenpao.demo.chislim.Interface.GetInterfaceBooleanListener;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.bean.SpiceSportRemberBean;
import com.weiaibenpao.demo.chislim.bean.UserBean;
import com.weiaibenpao.demo.chislim.customs.UploadProgressDialog;
import com.weiaibenpao.demo.chislim.util.ACache;
import com.weiaibenpao.demo.chislim.util.Default;
import com.weiaibenpao.demo.chislim.util.GetIntentData;
import com.weiaibenpao.demo.chislim.util.LatLong_impl;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zhangxing on 2017/2/28.
 */

public class MySportSimRemberActivity extends Activity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.titleTv)
    TextView titleTv;
    @BindView(R.id.toplayout)
    RelativeLayout toplayout;
    @BindView(R.id.mapView)
    MapView map;
    @BindView(R.id.mAllDis)
    TextView mAllDis;
    @BindView(R.id.myAllTime)
    TextView myAllTime;
    @BindView(R.id.disTime)
    LinearLayout disTime;
    @BindView(R.id.imgLine)
    ImageView imgLine;
    @BindView(R.id.myStep)
    TextView myStep;
    @BindView(R.id.myStepPace)
    TextView myStepPace;
    @BindView(R.id.myCorlic)
    TextView myCorlic;
    @BindView(R.id.centenLinLayout)
    LinearLayout centenLinLayout;
    @BindView(R.id.mySave)
    Button mySave;
    @BindView(R.id.activity_my_sport_rember)
    RelativeLayout activityMySportRember;
    OkHttpClient client;
    Context context;
    Request request;
    String url = "http://112.74.28.179:8080/Chislim/Travel_notes_Servlet?dowhat=getUpLoadToken";
    ArrayList arrayList;              //存放规划路线点的坐标
    ArrayList myLatLong;              //已绘制轨迹点存放
    double latDou;
    double longDou;
    Polyline polyline;
    PolylineOptions polylineOptions;
    double distance;          //路线规划的总距离
    int pos;                   //路线规划的总点数
    private AMapLocationClient locationClient = null;
    ACache mCache;
    int widthScreen;
    int heightScreen;
    boolean screenFlag = false;
    WindowManager wm;
    private AMap aMap;   //定义一个地图对象
    String firstCity;
    String secondCity;
    double fromLat;
    double fromLong;
    double toLat;
    double toLong;
    int position;
    int disNow;
    String mapStr;
    String dis;
    String cor;                    //卡路里
    String sporttime;             //时间
    String step;
    String speed;
    String token;
    Intent intentServices;    //启动
    LatLong_impl latLong_impl;     //用来获取系统日期
    SpiceSportRemberBean spiceSportRemberBean;
    UploadProgressDialog progressDialog;
    UploadManager uploadManager;
    DecimalFormat myFormatter;
    StringBuffer mResultsName;
    GetIntentData getIntentData;
    String imageUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sport_out_sim_item);
        StatusBarUtil.setColor(this , getResources().getColor(R.color.ku_bg ) , 0);
        ButterKnife.bind(this);
        progressDialog = new UploadProgressDialog(this, "正在上传数据，请稍等。。。");
        context = getApplicationContext();
        arrayList = new ArrayList();
        myLatLong = new ArrayList();
        map.onCreate(savedInstanceState);// 此方法必须重写
        mCache = ACache.get(context);    //实例化缓存
        getIntentData = new GetIntentData();
        dis = mCache.getAsString("disData");
        cor = mCache.getAsString("cor");
        sporttime = mCache.getAsString("sporttime");
        step = mCache.getAsString("step");
        speed = mCache.getAsString("speed");
       /* progressDialog = new UploadProgressDialog(this,"正在加载数据中...");
        progressDialog.showDialog();*/
        //第一步获取起点坐标和终点坐标
        initData();
        //第二部实例化地图
        initMap();
        //第三步，路径规划，并绘提前制路线图
        SearchLine(fromLat, fromLong, toLat, toLong);

        //第一步
        //实例化client
        new Thread(new Runnable() {
            @Override
            public void run() {
                //创建OkHttpClient
                client = new OkHttpClient();
                // 创建请求
                request = new Request.Builder()//
                        .url(url)//
                        .get()//
                        .build();
            }
        }).start();
        latLong_impl = new LatLong_impl(context);
        initConfig();

    }


    public void initConfig() {
        Configuration config = new Configuration.Builder()
                .chunkSize(256 * 1024)  //分片上传时，每片的大小。 默认256K
                .putThreshhold(512 * 1024)  // 启用分片上传阀值。默认512K
                .connectTimeout(10) // 链接超时。默认10秒
                .responseTimeout(60) // 服务器响应超时。默认60秒
                //       .recorder(recorder)  // recorder分片上传时，已上传片记录器。默认null
                //       .recorder(recorder, keyGen)  // keyGen 分片上传时，生成标识符，用于片记录器区分是那个文件的上传记录
                .zone(Zone.zone0) // 设置区域，指定不同区域的上传域名、备用域名、备用IP。默认 Zone.zone0
                .build();
        // 重用uploadManager。一般地，只需要创建一个uploadManager对象
        uploadManager = new UploadManager(config);
    }

    //第二步获取tocken
    public void getTocken() {
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {

                token = response.body().string();
                Log.i("七牛", token);
                Log.i("七牛", mapStr);
                //第三步上传
                getUpimg(mapStr, 0);
            }

            @Override
            public void onFailure(Call call, IOException e) {
                Log.i("七牛", e.getMessage());
            }
        });
    }

    //第四步上传
    public void getUpimg(final String imagePath, final int i) {
        myFormatter = new DecimalFormat("0");
        mResultsName = new StringBuffer();
        new Thread() {
            public void run() {
                // 图片上传到七牛 重用 uploadManager。一般地，只需要创建一个 uploadManager 对象
                UploadManager uploadManager = new UploadManager();
                uploadManager.put(imagePath, null, token,
                        new UpCompletionHandler() {
                            @Override
                            public void complete(String key, ResponseInfo info,
                                                 JSONObject res) {
                                // res 包含hash、key等信息，具体字段取决于上传策略的设置。
                                //Log.i("七牛", key + ",\r\n " + info + ",\r\n " + res);
                                try {
                                    /*String key1 = (String) res.get("key");
                                    Log.i("七牛七牛", key1);*/
                                    mResultsName.append(res.get("key"));
                                    imageUrl = Default.qiniu + String.valueOf(mResultsName);
                                    getIntentData.GetSportData(UserBean.getUserBean().userId,2, "","","",cor,"","",latLong_impl.getDate(), Default.qiniu + String.valueOf(mResultsName),(float) Double.parseDouble(myFormatter.format(Double.parseDouble(dis)))
                                            , "","",sporttime , step);
                                    getIntentData.setGetInterfaceBooleanListener(new GetInterfaceBooleanListener() {
                                        @Override
                                        public void getBoolean(boolean flag) {
                                            if (flag) {
                                                //数据上传完，progressDialog消失
                                                progressDialog.cancelDialog();
                                                // Snackbar.make(activityMySportRember,"提交成功",Snackbar.LENGTH_SHORT).show();

                                                //getShareDialog();
                                               // finish();
                                                Intent intent = new Intent(MySportSimRemberActivity.this, SportRecordMixedActivity.class);
                                                intent.putExtra("sportTime",Transfer(sporttime) );//运动所用时间
                                                intent.putExtra("calories", Integer.parseInt(cor));//卡楼里
                                                intent.putExtra("distance", Integer.parseInt(myFormatter.format(Double.parseDouble(dis))));//运动距离
                                                intent.putExtra("dayTime", latLong_impl.getDate());//运动时间
                                                intent.putExtra("sportSped", speed);//运动速度
                                                intent.putExtra("sportStep", Integer.parseInt(step));//运动步数
                                                intent.putExtra("sportImg", Default.qiniu + String.valueOf(mResultsName));//运动图片url
                                                startActivity(intent);
                                            }
                                        }
                                    });
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new UploadOptions(null, null, false,             //上传进度
                                new UpProgressHandler() {
                                    public void progress(String key, double percent) {
                                        double progress = percent * 100;
                                        if (progress >= 100) {

                                        }
                                    }
                                }, null));
            }
        }.start();
    }

    public int Transfer(String sportTime) {
        int senCount = 0;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

        try {
            Date date = sdf.parse(sportTime);
            int hour = date.getHours();
            int min = date.getMinutes();
            int second = date.getSeconds();
            senCount = hour * 3600 + min * 60 + second;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return senCount;
    }

    @OnClick({R.id.back, R.id.mySave})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                showDialog();
                break;
            case R.id.mySave:
                //显示progressDialog
                progressDialog.showDialog();
                cutMapScreen();
                break;
        }
    }

    public void initData() {
       /* Intent getIntent = getIntent();
        firstCity = getIntent.getStringExtra("fistCity");
        secondCity = getIntent.getStringExtra("secondCity");
        disNow = getIntent.getIntExtra("disNow",0);
        fromLat = getIntent.getDoubleExtra("fromLat", 0);
        fromLong = getIntent.getDoubleExtra("fromLong", 0);
        toLat = getIntent.getDoubleExtra("toLat", 0);
        toLong = getIntent.getDoubleExtra("toLong", 0);*/
        spiceSportRemberBean = (SpiceSportRemberBean) mCache.getAsObject("sportSpice");
        firstCity = spiceSportRemberBean.getFistCity();
        secondCity = spiceSportRemberBean.getSecondCity();
        disNow = spiceSportRemberBean.getDisNow();
        fromLat = spiceSportRemberBean.getFromLat();
        fromLong = spiceSportRemberBean.getFromLong();
        toLat = spiceSportRemberBean.getToLat();
        toLong = spiceSportRemberBean.getToLong();

        mAllDis.setText(dis);
        myAllTime.setText(sporttime);
        myStep.setText(step);
        myStepPace.setText(speed);
        myCorlic.setText(cor);
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
                      //  progressDialog.cancelDialog();
                        Log.i("测试","------------------");
                        clearMap();
                      //  huizhi(Integer.parseInt(dis));
                      //  huizhi(1000);


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


    private void clearMap() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
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

        for(int i =0;i<saveMarkerList.size();i++){
            saveMarkerList.get(i).remove();
        }
       //addMarker();
       huizhi(Integer.parseInt(dis));
       // huizhi(1000);
    }

    /**
     * 绘制轨迹
     */
    int h = 0;
    public void huizhi(int dis) {

        dis = dis + disNow * 1000;
        //clearMarkers(); // 清除marker
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
        // polylineOptions.add((LatLng) arrayList.get(h));
        polylineOptions.addAll(myLatLong);
        polylineOptions.visible(true).width(20).zIndex(200);
        // 加入对应的颜色,使用colorValues 即表示使用多颜色，使用color表示使用单色线
        //polylineOptions.colorValues(WalkUtil.getColorList(myLatLong.size()/144+1,this));
        polylineOptions.color(Color.argb(255, 1, 1, 1));
        //加上这个属性，表示使用渐变线
        polylineOptions.useGradient(true);
        polyline = aMap.addPolyline(polylineOptions);
        clearMap();
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
        builder.setTitle("记录提示");
        builder.setMessage("当前记录未做保存，是否放弃？");
        builder.setNegativeButton("放弃", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.setPositiveButton("保存", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                progressDialog.showDialog();
                cutMapScreen();
            }
        });
        builder.show();
    }


    //存储地图截屏
    public void cutMapScreen() {

        aMap.getMapScreenShot(new AMap.OnMapScreenShotListener() {
            @Override
            public void onMapScreenShot(Bitmap bitmap) {

            }

            @Override
            public void onMapScreenShot(Bitmap bitmap, int status) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                if (null == bitmap) {
                    return;
                }
                try {
                    FileOutputStream fos = new FileOutputStream(
                            Environment.getExternalStorageDirectory() + "/test.png");
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
                        mapStr = Environment.getExternalStorageDirectory() + "/test.png";
                        getTocken();
                    } else {
                        buffer.append("地图未渲染完成，截屏有网格");
                    }
                    // Snackbar.make(activityMySportRember,buffer.toString(),Snackbar.LENGTH_SHORT).show();

                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        });
    }
}
