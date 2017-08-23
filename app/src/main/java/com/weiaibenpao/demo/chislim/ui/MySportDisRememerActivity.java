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
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
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
import com.weiaibenpao.demo.chislim.bean.UserBean;
import com.weiaibenpao.demo.chislim.customs.UploadProgressDialog;
import com.weiaibenpao.demo.chislim.map.util.WalkUtil;
import com.weiaibenpao.demo.chislim.util.ACache;
import com.weiaibenpao.demo.chislim.util.Default;
import com.weiaibenpao.demo.chislim.util.GetIntentData;
import com.weiaibenpao.demo.chislim.util.LatLong_impl;
import com.weiaibenpao.demo.chislim.util.TestClass;

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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MySportDisRememerActivity extends Activity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.mapView)
    MapView mapView;
    @BindView(R.id.mAllDis)
    TextView mAllDis;
    @BindView(R.id.myAllTime)
    TextView myAllTime;
    @BindView(R.id.disTime)
    LinearLayout disTime;
    @BindView(R.id.myCorlic)
    TextView myCorlic;
    @BindView(R.id.myStep)
    TextView myStep;
    @BindView(R.id.myStepPace)
    TextView myStepPace;
    int disOld = 0;
    String token;
    ArrayList<LatLng> arrayList;
    ArrayList myLatLong;
    AMap aMap;
    String url = "http://112.74.28.179:8080/Chislim/Travel_notes_Servlet?dowhat=getUpLoadToken";
    double distance = 0;
    String provinceName;
    String dis;
    OkHttpClient client;
    Context context;
    Request request;
    UploadProgressDialog progressDialog;
    PolygonOptions pOption;
    @BindView(R.id.mySave)
    Button mySave;
    private double mLocatinLat;
    private double mLocationLon;
    Polyline polyline;
    UploadManager uploadManager;
    PolylineOptions polylineOptions;
    StringBuffer mResultsName;
    GetIntentData getIntentData;
    String imageUrl;
    String mapStr;
    DecimalFormat myFormatter;
    LatLong_impl latLong_impl;     //用来获取系统日期
    private ArrayList<LatLng> mLocationList = new ArrayList<LatLng>();
    String sportcor;
    String sporttime;
    String step;
    String speed;

    DecimalFormat decimalFormat;

    LatLonPoint centerLatLng;//行政区域的中心点坐标
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.district_remember_item);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this , getResources().getColor(R.color.ku_bg ) , 0);
        context = getApplicationContext();
        mapView.onCreate(savedInstanceState);         // 此方法必须重写
        arrayList = new ArrayList();
        myLatLong = new ArrayList();
        progressDialog = new UploadProgressDialog(this, "正在上传数据，请稍等。。。");
        getIntentData = new GetIntentData();
        initMap();
        provinceName = getIntent().getStringExtra("provinceName");
        dis = getIntent().getStringExtra("huitu");
        sportcor = getIntent().getStringExtra("cor");
        sporttime = getIntent().getStringExtra("sporttime");
        step = getIntent().getStringExtra("step");
        speed = getIntent().getStringExtra("speed");
        distance = getIntent().getDoubleExtra("distance",0);
        arrayList = SportOutDistrictActivity.arrayList;
        Log.e("SportDisRemember","arrayList size is "+arrayList.size());
//        arrayList = getIntent().getParcelableArrayListExtra("latlongArraylist");
        centerLatLng = getIntent().getParcelableExtra("centerLatLng");
        initView();
        //通过关键字进行行政区域搜索
        //getDistrict(provinceName);
        //通过传入的距离进行绘图
        huizhi((int)(Double.parseDouble(dis)*1000));

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

    private void initView() {
        Log.i("建宝",String.valueOf(Double.parseDouble(dis)));
        if (decimalFormat == null) {
            decimalFormat = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        }

        mAllDis.setText(decimalFormat.format(Double.parseDouble(dis)) + "km");
        myCorlic.setText(sportcor);
        myStep.setText(step);
        myAllTime.setText(sporttime);
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
                                    getIntentData.GetSportData(UserBean.getUserBean().userId,2,"","","",sportcor,"","",latLong_impl.getDate(), Default.qiniu + String.valueOf(mResultsName),(float) Double.parseDouble(myFormatter.format(Double.parseDouble(dis)))
                                            , "","",sporttime , step);
                                    getIntentData.setGetInterfaceBooleanListener(new GetInterfaceBooleanListener() {
                                        @Override
                                        public void getBoolean(boolean flag) {
                                            if (flag) {
                                                //数据上传完，progressDialog消失
                                                progressDialog.cancelDialog();
                                                // Snackbar.make(activityMySportRember,"提交成功",Snackbar.LENGTH_SHORT).show();

                                                //getShareDialog();
                                                //finish();
                                                Intent intent = new Intent(MySportDisRememerActivity.this, SportRecordMixedActivity.class);
                                                intent.putExtra("sportTime",sporttime);//运动所用时间
                                                intent.putExtra("calories", sportcor);//卡楼里
                                                intent.putExtra("distance", Double.parseDouble(dis));//运动距离
                                                intent.putExtra("dayTime", latLong_impl.getDate());//运动时间
                                                intent.putExtra("sportSped", speed);//运动速度
                                                intent.putExtra("sportStep", step);//运动步数
                                                intent.putExtra("sportImg", Default.qiniu + String.valueOf(mResultsName));//运动图片url
                                                startActivity(intent);
                                                finish();
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
                }finally {
                    if(progressDialog !=null) {
                        progressDialog.cancelDialog();
                    }
                    finish();
                }

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
    }


    /**
     * 绘制轨迹
     */
    int h = 0;

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
        polylineOptions.colorValues(WalkUtil.getColorList(myLatLong.size()/144+1,this));
//        polylineOptions.color(Color.argb(255, 222, 1, 1));
        //加上这个属性，表示使用渐变线
        polylineOptions.useGradient(true);
        polyline = aMap.addPolyline(polylineOptions);

        setMarkView();
        setIntro(centerLatLng.getLatitude(), centerLatLng.getLongitude());
    }

    private ArrayList<LatLng> mTable = new ArrayList<>();
    //设置没跑一公里的坐标标记点
    public void setMarkView(){
        for (int i = 0; i < mTable.size(); i++) {
            View view = View.inflate(MySportDisRememerActivity.this,R.layout.mark_layout, null);

            TextView sportMark = (TextView) view.findViewById(R.id.sportTitle);

            sportMark.setText("起");
            Bitmap bitmap = MySportDisRememerActivity.convertViewToBitmap(view);
            drawMarkerOnMap(mTable.get(i), bitmap, String.valueOf("起"));
        }

        if (centerLatLng != null) {  //地图加载时就显示行政区域
            aMap.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(new LatLng(centerLatLng.getLatitude(), centerLatLng.getLongitude()), 5));//13为缩放级别
            aMap.moveCamera(CameraUpdateFactory.zoomTo(12));
            //CameraUpdateFactory.zoomToSpan();               //// 缩放地图，使所有overlay都在合适的视野范围内
            CameraUpdateFactory.zoomBy(10);

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
     * 重写onKeyDown方法
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if((keyCode == KeyEvent.KEYCODE_BACK)){
            showDialog();
            return true;
        }
        return super.onKeyDown(keyCode, event);
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
        aMap.moveCamera(CameraUpdateFactory.zoomTo(10));
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
}
