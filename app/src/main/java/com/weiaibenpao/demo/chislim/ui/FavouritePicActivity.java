package com.weiaibenpao.demo.chislim.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.jaeger.library.StatusBarUtil;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.adater.ListRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FavouritePicActivity extends Activity implements PoiSearch.OnPoiSearchListener {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.sportDis)
    EditText sportDis;
    @BindView(R.id.setSport)
    RelativeLayout setSport;
    @BindView(R.id.mapView)
    MapView mapView;
    @BindView(R.id.sportCity)
    EditText sportCity;
    @BindView(R.id.select)
    ImageView select;
    private ImageView iv;
    private Bitmap baseBitmap;
    //private Canvas canvas = new Canvas();
    private Canvas canvas;
    private Paint paint;

    private Button button;

    AMap aMap;
    public static ArrayList<Float> xlists, ylists;

    int position;//标记  0 室内  1  室外

    double cenLat = 39.90866;      //当前地图中心点
    double cenLong = 116.397486;
    float zoom = 15;             //缩放比例

    MyBroadcast myBroadcast;  //广播对象
    boolean flag = false;

    PoiSearch poiSearch; //城市检索
    PoiSearch.Query query;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_pic);
        StatusBarUtil.setColor(this , getResources().getColor(R.color.ku_bg ) , 0);
        ButterKnife.bind(this);
        mapView.onCreate(savedInstanceState);         // 此方法必须重写
        Intent intent = getIntent();
        position = intent.getIntExtra("position", 0);

        xlists = new ArrayList<Float>();
        ylists = new ArrayList<Float>();

        button = (Button) findViewById(R.id.button1);

        //绘图
        WindowManager manager = this.getWindowManager();
        DisplayMetrics outMetrics = new DisplayMetrics();
        manager.getDefaultDisplay().getMetrics(outMetrics);
        int width = outMetrics.widthPixels;
        int height = outMetrics.heightPixels;

        this.iv = (ImageView) this.findViewById(R.id.iv); // 创建一张空白图片
        baseBitmap = Bitmap.createBitmap(width, height / 3, Bitmap.Config.ARGB_4444); // 创建一张画布
        //baseBitmap = Bitmap.createBitmap(width, 640, Bitmap.Config.ARGB_8888); // 创建一张画布
        canvas = new Canvas(baseBitmap);
        //canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);//绘制透明色  
        canvas.drawColor(Color.GRAY);// 画布背景为灰色
        paint = new Paint();  // 创建画笔 // 画笔颜色为红色
        paint.setColor(Color.RED); // 画笔颜色为红色
        paint.setStrokeWidth(15); // 宽度5个像素
        canvas.drawBitmap(baseBitmap,// 先将灰色背景画上
                new Matrix(), paint);
        iv.setImageBitmap(baseBitmap);
        iv.setOnTouchListener(new View.OnTouchListener() {
            int startX;
            int startY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: // 获取手按下时的坐标
                        startX = (int) event.getX();
                        startY = (int) event.getY();
                        break;
                    case MotionEvent.ACTION_MOVE: // 获取手移动后的坐标
                        int stopX = (int) event.getX();
                        int stopY = (int) event.getY(); // 在开始和结束坐标间画一条线
                        canvas.drawLine(startX, startY, stopX, stopY, paint); // 实时更新开始坐标
                        startX = (int) event.getX();
                        startY = (int) event.getY();
                        xlists.add(event.getX());
                        ylists.add(event.getY());
                        Log.i("测试", "x=" + event.getX() + "y=" + event.getY());
                        iv.setImageBitmap(baseBitmap);
                        break;
                    case MotionEvent.ACTION_UP:
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        break;
                }
                //MainActivity.this.info.setText("x=" + event.getX() + "y=" + event.getY());
                return true;
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("跑步", "室外  " + position);
                if (("").equals(sportDis.getText().toString().trim())) {
                    Snackbar.make(setSport, "请输入要跑的距离", Snackbar.LENGTH_SHORT).show();
                    return;
                }
                if (position == 0) {
                    Intent intent = new Intent(FavouritePicActivity.this, FavouritMapActivity.class);
                    intent.putExtra("dis", Integer.parseInt(sportDis.getText().toString().trim()) * 1000);
                    intent.putExtra("cenLat", cenLat);
                    intent.putExtra("cenLong", cenLong);
                    intent.putExtra("zoom", zoom);
                    startActivity(intent);
                    finish();
                }
                if (position == 1) {
                    Intent intent = new Intent(FavouritePicActivity.this, FavouriteMapOutHomeActivity.class);
                    intent.putExtra("dis", Integer.parseInt(sportDis.getText().toString().trim()) * 1000);
                    intent.putExtra("cenLat", cenLat);
                    intent.putExtra("cenLong", cenLong);
                    intent.putExtra("zoom", zoom);
                    startActivity(intent);
                    finish();
                }
            }
        });

        //选择距离，获得焦点是触发
        sportDis.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    // 此处为得到焦点时的处理内容
                    createBottomSheetDialog(2, new ArrayList(), new ArrayList());
                } else {
                    // 此处为失去焦点时的处理内容

                }
            }
        });

        initMap();

        //配置广播，接收当前是否有跑步正在进行中
        myBroadcast = new MyBroadcast();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.lijianbao.mapLocationLatLong");
        FavouritePicActivity.this.registerReceiver(myBroadcast, filter);


    }

    /**
     * 城市检索
     *
     * @param strCity
     */
    public void getCity(String strCity) {
        //搜索位置
        query = new PoiSearch.Query(strCity, "", "");
        query.setPageSize(10);// 设置每页最多返回多少条poiitem
        query.setPageNum(1);//设置查询页码
        //构造 PoiSearch 对象，并设置监听。
        poiSearch = new PoiSearch(this, query);
        poiSearch.setOnPoiSearchListener(this);
        //发送请求
        poiSearch.searchPOIAsyn();  //异步搜索
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
        aMap.setMapType(AMap.MAP_TYPE_NORMAL);// 地图模式

        aMap.setOnCameraChangeListener(new AMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {
                cenLat = cameraPosition.target.latitude;
                cenLong = cameraPosition.target.longitude;
                zoom = cameraPosition.zoom;
            }

            @Override
            public void onCameraChangeFinish(CameraPosition cameraPosition) {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mapView.onDestroy();
        unregisterReceiver(myBroadcast);
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

    /*地图检索*/
    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        List<String> strs = new ArrayList<String>();
        List<LatLonPoint> latLonList = new ArrayList<LatLonPoint>();
        ArrayList items = poiResult.getPois();
        if (items != null && items.size() > 0) {
            PoiItem item = null;
            for (int j = 0, count = items.size(); j < count; j++) {
                item = (PoiItem) items.get(j);
                latLonList.add(((PoiItem) items.get(j)).getLatLonPoint());
                strs.add(item.getTitle());
            }
            createBottomSheetDialog(1, (ArrayList) strs, (ArrayList) latLonList);
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    /**
     * 底部滑动显示
     *
     * @param page
     * @param num
     * @param n
     */
    private BottomSheetDialog mBottomSheetDialog;

    private void createBottomSheetDialog(final int n, ArrayList strs, ArrayList latLongList) {
        //new 一个BottomSheetDialog对象
        mBottomSheetDialog = new BottomSheetDialog(this);
        //获取要添加到BottomSheetDialog中的布局view
        View view = createBottomSheetDialogContent(n, strs, latLongList);
        //将布局添加到BottomSheetDialog中
        mBottomSheetDialog.setContentView(view);

        //显示
        mBottomSheetDialog.show();
    }

    /**
     * @param n
     * @return
     */
    private View createBottomSheetDialogContent(final int n, final ArrayList strs, final ArrayList latLongList) {
        View view = null;
        switch (n) {
            case 1:
                view = LayoutInflater.from(this).inflate(R.layout.dialog_bottom_sheet, null, false);
                RecyclerView recyclerViewCity = (RecyclerView) view.findViewById(R.id.recyclerView);
                //Item宽或者高不会变，recyclerView的高改变
                recyclerViewCity.setHasFixedSize(true);

                //设置为竖向布局
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                linearLayoutManager.setSmoothScrollbarEnabled(true);
                recyclerViewCity.setLayoutManager(linearLayoutManager);
                //设置适配器
                ListRecyclerAdapter adapter = new ListRecyclerAdapter(strs);
                recyclerViewCity.setAdapter(adapter);

                ListRecyclerAdapter.setOnItemClickListener(new ListRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position, ArrayList talkList) {
                        if (mBottomSheetDialog.isShowing()) {
                            mBottomSheetDialog.dismiss();
                            //Toast.makeText(MyUserInfoSetActivity.this, (position + page) + "", Toast.LENGTH_SHORT).show();

                            //设置显示坐标点
                            setIntro((LatLonPoint) latLongList.get(position));
                            sportCity.setText( strs.get(position) + "");

                        } else {
                            mBottomSheetDialog.show();
                        }
                    }
                });
                break;
            case 2:
                view = LayoutInflater.from(this).inflate(R.layout.dialog_bottom_sheet, null, false);
                RecyclerView recyclerViewDis = (RecyclerView) view.findViewById(R.id.recyclerView);
                //Item宽或者高不会变，recyclerView的高改变
                recyclerViewDis.setHasFixedSize(true);

                List<String> list = new ArrayList<>();
                for (int i = 1; i <= 100; i++) {
                    list.add(i + "");
                }
                //设置为竖向布局
                LinearLayoutManager linearLayoutManagerDis = new LinearLayoutManager(this);
                linearLayoutManagerDis.setSmoothScrollbarEnabled(true);
                recyclerViewDis.setLayoutManager(linearLayoutManagerDis);
                //设置适配器
                ListRecyclerAdapter adapterDis = new ListRecyclerAdapter(list);
                recyclerViewDis.setAdapter(adapterDis);

                ListRecyclerAdapter.setOnItemClickListener(new ListRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position, ArrayList talkList) {
                        if (mBottomSheetDialog.isShowing()) {
                            mBottomSheetDialog.dismiss();
                            //Toast.makeText(MyUserInfoSetActivity.this, (position + page) + "", Toast.LENGTH_SHORT).show();
                            sportDis.setText((position + 1) + "");
                        } else {
                            mBottomSheetDialog.show();
                        }
                    }
                });
                break;
        }
        return view;
    }

    /**
     * 切换地图显示中心点
     */
    public void setIntro(LatLonPoint latLonPoint) {
        double mLocatinLat = latLonPoint.getLatitude();
        double mLocationLon = latLonPoint.getLongitude();
        if (mLocatinLat != 0 && mLocationLon != 0) {

            LatLng marker1 = new LatLng(mLocatinLat, mLocationLon);
            //设置中心点和缩放比例
            aMap.moveCamera(CameraUpdateFactory.changeLatLng(marker1));
            aMap.moveCamera(CameraUpdateFactory.zoomTo(15));
        }
    }

    @OnClick({R.id.back, R.id.select})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.select:
                if(!"".equals(sportCity.getText().toString().trim())){
                    getCity(sportCity.getText().toString().trim());
                }else {
                    Snackbar.make(setSport, "请输入要跑的地点", Snackbar.LENGTH_SHORT).show();
                }
                break;
        }
    }

    /**
     * 内部类实现进度条和界面动态显示
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

            String cor = intent.getStringExtra("contextActivity");        //当前正在跑跑步的activity
            int state = intent.getIntExtra("state", 0);
            //如果广播的跑步界面地址不为空，切跑步状态不为4，还没有跳转，则提示跳转
            if (cor != null && state != 4 && !flag) {
                flag = true;
                Intent intent1 = new Intent();
                intent1.setAction(cor);//设置intent的Action属性值
                intent1.addCategory(Intent.CATEGORY_DEFAULT);//不加这行也行，因为这个值默认就是Intent.CATEGORY_DEFAULT
                startActivity(intent1);
            }
        }
    }
}
