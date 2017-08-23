package com.weiaibenpao.demo.chislim.ui;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.jaeger.library.StatusBarUtil;
import com.qiniu.android.common.Zone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.weiaibenpao.demo.chislim.Interface.GetInterfaceBooleanListener;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.bean.LatLongRemberBean;
import com.weiaibenpao.demo.chislim.bean.UserBean;
import com.weiaibenpao.demo.chislim.customs.UploadProgressDialog;
import com.weiaibenpao.demo.chislim.map.activity.DrawTraceActivity;
import com.weiaibenpao.demo.chislim.map.util.WalkUtil;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class MySportRemberActivity extends Activity {
    UploadManager uploadManager;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.mapView)
    MapView mapView;
    @BindView(R.id.hisDis)
    TextView hisDis;
    @BindView(R.id.hisTime)
    TextView hisTime;
    @BindView(R.id.cor)
    TextView cor;
    @BindView(R.id.step)
    TextView step;
    @BindView(R.id.pace)
    TextView pace;
    String token;
    @BindView(R.id.mySave)
    Button mySave;
    private AMap aMap;
    UploadProgressDialog progressDialog;
    private UiSettings mUiSettings;
    String url = "http://112.74.28.179:8080/Chislim/Travel_notes_Servlet?dowhat=getUpLoadToken";
    Bundle mSavedInstanceState;
    OkHttpClient client;
    Context context;
    Request request;
    double latStart = 0;
    double longStart = 0;
    String sporttime="";
    String sportdis="";
    String sportcor="";
    String sportstep="";
    String sportspeed="";

    String speedspace="";           //配速
    String stepfrequency="";       //步频
    String stepscope="";           //步幅
    String elevation="";           //海拔

    String mydate;
    StringBuffer mResultsName;
    GetIntentData getIntentData;
    private static final int REQUEST_CODE = 732;
    LatLong_impl latLong_impl;     //用来获取系统日期
    private ArrayList<String> mResultsPath = new ArrayList<>();

    String mapStr;
    DecimalFormat myFormatter;

    WindowManager wm;
    int widthScreen;
    int heightScreen;
    boolean screenFlag = false;
    String imageUrl;

    DecimalFormat decimalFormat;

    boolean flag = false;

    PolylineOptions polylineOptions;

    ArrayList<LatLng> mTable;

    TextView sportMark;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_sport_rember);
        StatusBarUtil.setColor(this , getResources().getColor(R.color.ku_bg ) , 0);
        mSavedInstanceState = savedInstanceState;
        ButterKnife.bind(this);
        context = getApplicationContext();
        progressDialog = new UploadProgressDialog(this, "正在上传数据，请稍等。。。");

        mTable = getIntent().getParcelableArrayListExtra("mTable");

        getIntentData = new GetIntentData();

        initView();    //实例化地图控件
        huitu();         //从数据库拿去数据，绘制轨迹
        setIntro();        //设置轨迹为中心点

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
                                    getIntentData.GetSportData(UserBean.getUserBean().userId,2, "","","",sportcor,"","",latLong_impl.getDate(), Default.qiniu + String.valueOf(mResultsName), (float) Double.parseDouble(myFormatter.format(Double.parseDouble(sportdis)/1000))
                                            , "","",sporttime , sportstep);
                                    getIntentData.setGetInterfaceBooleanListener(new GetInterfaceBooleanListener() {
                                        @Override
                                        public void getBoolean(boolean flag) {
                                            if (flag) {
                                                //数据上传完，progressDialog消失
                                                progressDialog.cancelDialog();
                                                // Snackbar.make(activityMySportRember,"提交成功",Snackbar.LENGTH_SHORT).show();

                                                //getShareDialog();
                                               // finish();
                                                Intent intent = new Intent(MySportRemberActivity.this, SportRecordMixedActivity.class);
                                                intent.putExtra("sportTime",sporttime);//运动所用时间
                                                intent.putExtra("calories", sportcor);//卡路里
                                                intent.putExtra("distance", Double.parseDouble(sportdis) / 1000);//运动距离
                                                intent.putExtra("dayTime", latLong_impl.getDate());//运动日期
                                                intent.putExtra("sportSped", sportspeed);//运动速度
                                                intent.putExtra("sportStep", sportstep);//运动步数
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
            Log.i("时间", "和" + senCount + "--小时--" + hour + "--分钟--" + min + "--秒--" + second);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return senCount;
    }

    protected void initView() {
        mapView.onCreate(mSavedInstanceState);              // 此方法必须重写
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        mUiSettings = aMap.getUiSettings();
        mUiSettings.setLogoPosition(2);                      //设置高德地图logo位置
        mUiSettings.setZoomControlsEnabled(false);


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
                } else {
                    screenFlag = true;
                    lp.width = widthScreen;
                    lp.height = heightScreen;
                    mapView.setLayoutParams(lp);
                }
            }
        });
    }

    public void initSetData() {
        if (decimalFormat == null) {
            decimalFormat = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        }

        hisDis.setText(decimalFormat.format(Double.parseDouble(sportdis) / 1000) + ("km"));
        hisTime.setText(sporttime);
        cor.setText(sportcor+"Kcal");
        step.setText(sportstep + "步");
        pace.setText(speedspace);
    }

    /**
     * 切换地图显示中心点
     */
    public void setIntro() {
        if (latStart != 0 && longStart != 0) {

            LatLng marker1 = new LatLng(latStart, longStart);
            //设置中心点和缩放比例
            aMap.moveCamera(CameraUpdateFactory.changeLatLng(marker1));
            aMap.moveCamera(CameraUpdateFactory.zoomTo(15));
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

    /**
     * 绘图
     * 数据库中每五秒打一个点
     */
    public void huitu() {

        ArrayList<LatLng> latLngs = new ArrayList<LatLng>();                        //用来存放轨迹坐标
        LatLong_impl latLong_impl = new LatLong_impl(context);                 //实例化数据库daoimpl类
        ArrayList sportInfo = latLong_impl.getLatLong(latLong_impl.getTab());   //从数据库获取轨迹坐标值

        int n = sportInfo.size() - 1;
        for (int i = 0; i < n; i++) {
            latStart = ((LatLongRemberBean) sportInfo.get(i)).getLatitude();
            longStart = ((LatLongRemberBean) sportInfo.get(i)).getLongitude();
            sporttime = ((LatLongRemberBean) sportInfo.get(i)).getSporttime();
            sportdis = ((LatLongRemberBean) sportInfo.get(i)).getSportdis();
            sportcor = ((LatLongRemberBean) sportInfo.get(i)).getSportcor();
            sportstep = ((LatLongRemberBean) sportInfo.get(i)).getSportstep();
            sportspeed = ((LatLongRemberBean) sportInfo.get(i)).getSportspeed();

            speedspace = ((LatLongRemberBean) sportInfo.get(i)).getSpeedspace();            //配速
            stepfrequency = ((LatLongRemberBean) sportInfo.get(i)).getStepfrequency();      //步频
            stepscope = ((LatLongRemberBean) sportInfo.get(i)).getStepscope();               //步幅
            elevation = ((LatLongRemberBean) sportInfo.get(i)).getElevation();               //海拔
            latLngs.add(new LatLng(latStart, longStart));
            initSetData();        //设置数据的显示
            Log.i("地图服务", sporttime + "时间" + sportstep + "步数" + speedspace + "---配速---" + stepfrequency + "---步频---" + stepscope + "---步幅---" + elevation + "---海拔---");
        }
        //Toast.makeText(MySportRemberActivity.this,speedspace + "---配速---" + stepfrequency + "---步频---" + stepscope + "---步幅---" + elevation + "---海拔---",Toast.LENGTH_SHORT).show();
        //绘制地图轨迹
       /* aMap.addPolyline(new PolylineOptions().
                addAll(latLngs).width(10).color(Color.argb(255, 1, 1, 1)));*/
        DrawRideTraceTotal(latLngs);
        //设置每整公里标记
        setMarkView();
    }

    private Polyline TotalLine;

    //绘制轨迹
    private void DrawRideTraceTotal(ArrayList latLngs) {
        if (TotalLine != null) {
            TotalLine.remove();
            TotalLine = null;
        }
        if(polylineOptions == null){
            polylineOptions = new PolylineOptions();
        }
        polylineOptions.addAll(latLngs);
        polylineOptions.visible(true).width(15).zIndex(200);
        //加入对应的颜色,使用colorValues 即表示使用多颜色，使用color表示使用单色线
        polylineOptions.colorValues(WalkUtil.getColorList(latLngs.size() / 144 + 1, this));
        //加上这个属性，表示使用渐变线
        polylineOptions.useGradient(true);
        TotalLine = aMap.addPolyline(polylineOptions);

    }

    //设置没跑一公里的坐标标记点
    public void setMarkView(){
        if(mTable == null) {
            return;
        }
        for (int i = 0; i < mTable.size(); i++) {
            View view = View.inflate(MySportRemberActivity.this,R.layout.mark_layout, null);

            sportMark = (TextView) view.findViewById(R.id.sportTitle);


            if (i == 0) {
                sportMark.setText("起");
                Bitmap bitmap = DrawTraceActivity.convertViewToBitmap(view);
                drawMarkerOnMap(mTable.get(i), bitmap, String.valueOf("起"));
            }else if(i == mTable.size() - 1){
                sportMark.setText("终");
                Bitmap bitmap = DrawTraceActivity.convertViewToBitmap(view);
                drawMarkerOnMap(mTable.get(i), bitmap, String.valueOf("终"));
            }else if(i > 0 && i < mTable.size() - 1){
                sportMark.setText(String.valueOf(i));
                Bitmap bitmap = DrawTraceActivity.convertViewToBitmap(view);
                drawMarkerOnMap(mTable.get(i), bitmap, String.valueOf(i));
            }
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
                    .visible(true)
                    .icon(BitmapDescriptorFactory.fromBitmap(markerIcon)));
            return marker;
        }
        return null;
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (progressDialog != null) {
            progressDialog.cancelDialog();

        }

        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mapView.onDestroy();
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


    @OnClick({R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                showDialog();
                break;
        }
    }

    /**
     * 分享回调
     */
    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.i("分享", "-------------成功-----------");
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(MySportRemberActivity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Log.i("分享", "-------------取消-----------");
        }
    };

    /**
     * 分享面板添加按钮的回调
     */
    private ShareBoardlistener shareBoardlistener = new ShareBoardlistener() {

        @Override
        public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
            if (share_media == null) {
                if (snsPlatform.mKeyword.equals("11")) {
                    Log.i("分享面板添加按钮的回调", "-------------add button success-----------");
                }
            } else {
                new ShareAction(MySportRemberActivity.this).setPlatform(share_media).setCallback(umShareListener)
                        .withText("多平台分享")
                        .share();
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 数据上传成功后分享
     */
    public void getShareDialog() {
        View view = LayoutInflater.from(context).inflate(R.layout.share_sportdata, null);
        ((TextView) view.findViewById(R.id.versionCode)).setText("炫耀一下");
        ((TextView) view.findViewById(R.id.versionText)).setText("今日跑步时间: " + sporttime + " 跑步距离: " + sportdis + " 消耗的卡路里: " + sportcor + " 总共行走 : " + sportstep);
        final PopupWindow popWnd = new PopupWindow(context);

        popWnd.setContentView(view);
        popWnd.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popWnd.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popWnd.setOutsideTouchable(true);  //设置点击屏幕其它地方弹出框消失
        //View rootview = LayoutInflater.from(context).inflate(R.id.root_tv, null);
        //相对于父控件的位置（例如正中央Gravity.CENTER，下方Gravity.BOTTOM等），可以设置偏移或无偏移
        popWnd.showAtLocation(findViewById(R.id.button), Gravity.CENTER, 0, 0);
        ((TextView) view.findViewById(R.id.startBut)).setText("放弃");

        view.findViewById(R.id.startBut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popWnd.dismiss();
                finish();
            }
        });
        view.findViewById(R.id.againBut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popWnd.dismiss();
                final SHARE_MEDIA[] displaylist = new SHARE_MEDIA[]
                        {
                                SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA,
                                SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE
                        };
                new ShareAction(MySportRemberActivity.this).setDisplayList(displaylist)
                        .withText("今日跑步时间: " + sporttime + "\n 跑步距离: " + sportdis + "\n 消耗的卡路里: " + sportcor + "\n 总共行走 : " + sportstep)
                        .withTitle("        亲爱的小伙伴们，请不要跟随我的脚步...")
                        .withTargetUrl(imageUrl)
                        // .withMedia(image)
                        .setListenerList(umShareListener)
                        .open();
            }
        });
    }

    @OnClick(R.id.mySave)
    public void onClick() {
        //显示progressDialog
        progressDialog.showDialog();
        cutMapScreen();
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
     * 这是兼容的 AlertDialog
     */
    private void showDialog() {
      /*
      这里使用了 android.support.v7.app.AlertDialog.Builder
      可以直接在头部写 import android.support.v7.app.AlertDialog
      那么下面就可以写成 AlertDialog.Builder
      */
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(this);
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
