package com.weiaibenpao.demo.chislim.ui;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.jaeger.library.StatusBarUtil;
import com.qiniu.android.common.Zone;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.adater.EquitySpeedAdapter;
import com.weiaibenpao.demo.chislim.bean.LatLongRemberBean;
import com.weiaibenpao.demo.chislim.customs.HeartCustomView;
import com.weiaibenpao.demo.chislim.customs.SeaCustomView;
import com.weiaibenpao.demo.chislim.customs.SpeedCustomView;
import com.weiaibenpao.demo.chislim.customs.StepCustomView;
import com.weiaibenpao.demo.chislim.customs.UploadProgressDialog;
import com.weiaibenpao.demo.chislim.util.Default;
import com.weiaibenpao.demo.chislim.util.FileIOUtil;
import com.weiaibenpao.demo.chislim.util.GetIntentData;
import com.weiaibenpao.demo.chislim.util.LatLong_impl;

import org.json.JSONException;
import org.json.JSONObject;
import org.xclcharts.chart.PointD;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Iterator;
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
 * Created by zhangxing on 2017/1/14.
 */
public class SportRecordMixedActivity extends Activity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.trace_map)
    ImageView traceMap;
    LatLong_impl latLong_impl;
    String sportTime;
    double sportDistance;
    String calories;
    String daytime;
    String sportMapStr;
    String sportSped;
    String sportStep;
    @BindView(R.id.myDis)
    TextView myDis;
    @BindView(R.id.myStep)
    TextView myStep;
    @BindView(R.id.myTime)
    TextView myTime;
    @BindView(R.id.myCalior)
    TextView myCalior;
    @BindView(R.id.mySportTime)
    TextView mySportTime;
    @BindView(R.id.equity_speed)
    TextView equitySpeed;
    @BindView(R.id.fast_speed)
    TextView fastSpeed;
    @BindView(R.id.lower_speed)
    TextView lowerSpeed;
    @BindView(R.id.SpeedRecyclerView)
    RecyclerView SpeedRecyclerView;
    @BindView(R.id.shareTv)
    TextView shareTv;
    @BindView(R.id.myStepPin)
    TextView myStepPin;
    @BindView(R.id.myStepFu)
    TextView myStepFu;
    @BindView(R.id.mySeaHeight)
    TextView mySeaHeight;
    @BindView(R.id.zoom_layout)
    LinearLayout zoomLayout;
    @BindView(R.id.title_layout)
    LinearLayout titleLayout;
    @BindView(R.id.flutlypic)
    StepCustomView flutlypic;
    @BindView(R.id.speedpic)
    SpeedCustomView speedpic;
    @BindView(R.id.heartpic)
    HeartCustomView heartpic;
    @BindView(R.id.seapic)
    SeaCustomView seapic;
    @BindView(R.id.activity_update_pass1)
    RelativeLayout activityUpdatePass1;
    @BindView(R.id.scrollLayout)
    ScrollView scrollLayout;
    @BindView(R.id.eachStepFu)
    TextView eachStepFu;
    @BindView(R.id.fastStepFu)
    TextView fastStepFu;
    @BindView(R.id.eachStepPin)
    TextView eachStepPin;
    @BindView(R.id.fastStepPin)
    TextView fastStepPin;
    @BindView(R.id.eachPeiSu)
    TextView eachPeiSu;
    @BindView(R.id.fastPeiSu)
    TextView fastPeiSu;
    @BindView(R.id.eachHaiBa)
    TextView eachHaiBa;
    @BindView(R.id.fastHaiBa)
    TextView fastHaiBa;
    private ArrayList<String> mKiloList, mSpeedList, mTimeList;
    EquitySpeedAdapter adapter;
    Context context;
    String imageUrl;
    String mapStr;
    String url = "http://112.74.28.179:8080/Chislim/Travel_notes_Servlet?dowhat=getUpLoadToken";
    OkHttpClient client;
    Request request;
    double totalDis = 0.0;
    UploadProgressDialog progressDialog;
    UploadManager uploadManager;
    String token;
    DecimalFormat myFormatter;
    StringBuffer mResultsName;
    GetIntentData getIntentData;

    DecimalFormat decimalFormat;
    int fialDis =0;
    ArrayList<String> speedlist;                //配速
    ArrayList<String> stepfrequencylist;        //步频
    ArrayList<String> stepscopelist;            //步幅
    ArrayList<String> elevationlist;            //海拔
    String speedspace;           //配速
    String stepfrequency;        //步频
    String stepscope;            //步幅
    String elevation;            //海拔
    List<PointD> linePoint1;
    List<PointD> linePoint2;
    List<PointD> linePoint3;
    List<PointD> linePoint4;
    int n = 0;
    int m = 0;
    int p = 0;
    int f = 0;
    ArrayList<Double> stf = new ArrayList<>();
    ArrayList<Double> ssl = new ArrayList<>();
    ArrayList<Double> elt = new ArrayList<>();
    ArrayList<Double> sel = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_mixed_layout);
        StatusBarUtil.setColor(this , getResources().getColor(R.color.ku_bg ) , 0);
        ButterKnife.bind(this);
        context = getApplicationContext();
        progressDialog = new UploadProgressDialog(this, "正在上传数据!");
        getIntentData = new GetIntentData();
        latLong_impl = new LatLong_impl(context);
        initData();
        huitu();
        initLayout();
        //第一步 初始化
        initConfig();

        //实例化client
        new Thread(new Runnable() {
            @Override
            public void run() {
                //创建OkHttpClient
                client = new OkHttpClient();
                // 创建请求
                request = new Request.Builder()
                        .url(url)
                        .get()
                        .build();
            }
        }).start();

        shareTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog.showDialog();
                getScreen();
            }
        });

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
                Log.i("七牛", imageUrl);
                //第三步上传
                getUpimg(imageUrl, 0);
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
                                    mapStr = Default.qiniu + String.valueOf(mResultsName);
                                    if (mapStr != null) {
                                        progressDialog.cancelDialog();
                                        shareToPlaforms(mapStr);
                                    }
                                    if(imageUrl != null && imageUrl != ""){
                                        File deletefile1 = new File(imageUrl);
                                        deletefile1.delete();
                                    }

                                    if(sportMapStr != null && sportMapStr != ""){
                                        File deletefile2 = new File(sportMapStr);
                                        deletefile2.delete();
                                    }

                                    //  shareToPlaforms(mapStr);sportMapStr
                                    Log.i("图片", mapStr);

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        },
                        new UploadOptions(null, null, false,             //上传进度
                                new UpProgressHandler() {
                                    public void progress(String key, double percent) {
                                        double newProgress = percent * 100;

                                    }
                                }, null));
            }
        }.start();
    }

    public void shareToPlaforms(String url) {
        UMImage image = new UMImage(SportRecordMixedActivity.this, url);
        final SHARE_MEDIA[] displaylist = new SHARE_MEDIA[]
                {
                        SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA,
                        SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE
                };
        new ShareAction(SportRecordMixedActivity.this).setDisplayList(displaylist)
                .withText("跑步记录")
                .withTitle("我的东极圈运动")
                .withTargetUrl(url)
                .withMedia(image)
                .setListenerList(umShareListener)
                .open();
    }

    private void initData() {
        //得到上一个Actitvity传来的数据
        sportTime = getIntent().getStringExtra("sportTime");//所用时间
        sportDistance = getIntent().getDoubleExtra("distance", 0);//距离
        calories = getIntent().getStringExtra("calories");//大卡
        daytime = getIntent().getStringExtra("dayTime");//日期
        sportSped = getIntent().getStringExtra("sportSped");//速度
        sportMapStr = getIntent().getStringExtra("sportImg");//url
        sportStep = getIntent().getStringExtra("sportStep");//步数

        speedlist = new ArrayList<>();
        stepfrequencylist = new ArrayList<>();
        stepscopelist = new ArrayList<>();
        elevationlist = new ArrayList<>();

    }

    private void initLayout() {
        //运动轨迹
        Glide.with(this)
                .load(sportMapStr)
                .error(R.mipmap.zhanwei)
                .placeholder(R.mipmap.zhanwei)
                .into(traceMap);

        if (decimalFormat == null) {
            decimalFormat = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        }

        //运动参数
        mySportTime.setText(daytime);
        myDis.setText(decimalFormat.format((double) sportDistance) + "");
        totalDis = Double.parseDouble(decimalFormat.format((double) sportDistance));
        myCalior.setText(calories + "");

        myTime.setText(sportTime);

        myStep.setText(sportStep + "");

        //运动图表
        linePoint1 = new ArrayList<PointD>();
        linePoint2 = new ArrayList<PointD>();
        linePoint3 = new ArrayList<PointD>();
        linePoint4 = new ArrayList<PointD>();

        if (stepscopelist == null || stepfrequencylist == null || elevationlist == null || speedlist == null) {
            return;
        } else {
            ArrayList<String> newStepscopelist = clearAcrossReapte(stepscopelist);
            for (int i = 0; i < newStepscopelist.size() && n < 45; i++) {

                if (Double.parseDouble(newStepscopelist.get(i))*100 >= 0.0 && Double.parseDouble(newStepscopelist.get(i))*100 <= 130) {
                    try {
                        linePoint1.add(new PointD((double) n, Double.parseDouble(newStepscopelist.get(i))*100));
                        n++;
                        ssl.add(Double.valueOf(newStepscopelist.get(i))*100);
                        Log.i("stepscopelist", Double.valueOf(newStepscopelist.get(i))*100 + "");
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }

            ArrayList<String> newStepfrequencylist = clearAcrossReapte(stepfrequencylist);
            for (int i = 0; i < newStepfrequencylist.size() && m <= 10; i++) {
                if (Double.parseDouble(newStepfrequencylist.get(i)) != 0.0) {
                    try {
                        linePoint2.add(new PointD((double) m, Double.parseDouble(newStepfrequencylist.get(i))));
                        m++;
                        //double x = Collections.max(Double.parseDouble(String.valueOf(stepfrequencylist)));
                        stf.add(Double.valueOf(newStepfrequencylist.get(i)));
                        Log.i("stepfrequencylist", Double.valueOf(newStepfrequencylist.get(i)) + "");
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }

            ArrayList<String> newElevationlist = clearAcrossReapte(elevationlist);
            for (int i = 0; i < newElevationlist.size() && p <= 10; i++) {
                if (Double.parseDouble(newElevationlist.get(i)) != 0.0) {
                    try {

                        linePoint3.add(new PointD((double) p, Double.parseDouble(newElevationlist.get(i))));
                        p++;
                        elt.add(Double.valueOf(newElevationlist.get(i)));
                        Log.i("elevationlist", Double.valueOf(newElevationlist.get(i)) + "");
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }

            ArrayList<String> arrayNewList = clearAcrossReapte(speedlist);
            ArrayList<String> newArrayListnew = new ArrayList();
            for(int j = 0;j < arrayNewList.size();j++){
                newArrayListnew.add(arrayNewList.get(j).replace(":","."));
            }

            for (int i = 0; i < newArrayListnew.size() && f <= 10; i++) {
                Log.i("speedlist配速2","**************---" + newArrayListnew.get(i) + "---**************");

                if (Double.parseDouble(newArrayListnew.get(i)) != 0.0) {
                    try {
                        Log.i("speedlist配速2","**************---" + newArrayListnew.get(i) + "---**************");
                        linePoint4.add(new PointD((double) f, Double.parseDouble(newArrayListnew.get(i))));
                        f++;
                        sel.add(Double.valueOf(newArrayListnew.get(i)));
                        //Log.i("speedlist", Double.valueOf(speedlist.get(i)) + "");
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
            }

            flutlypic.setStepLinePoint(linePoint1);//步幅
            Log.i("纵坐标",getStepFu(getMaxArrayList(ssl) / 8.0) + "");
            eachStepFu.setText(getEachArrayList(ssl)+"");
            fastStepFu.setText(printNum(getMaxArrayList(ssl))+"");

            speedpic.setSpeedLinePoint(linePoint2, printNum(getMaxArrayList(stf)), printNum(getMaxArrayList(stf) / 8));//步频
            eachStepPin.setText(getEachArrayList(stf)+"");
            fastStepPin.setText(printNum(getMaxArrayList(stf))+"");

            seapic.setSeaLinePoint(linePoint3, printNum(getMaxArrayList(elt)), printNum(getMaxArrayList(elt) / 8));//海拔
            eachHaiBa.setText(getEachArrayList(elt)+"");
            fastHaiBa.setText(printNum(getMaxArrayList(elt))+"");

            heartpic.setHeartLinePoint(linePoint4, printNum(getMaxArrayList(sel)), printNum(getMaxArrayList(sel) / 5));//配速
            eachPeiSu.setText(getEachArrayList(sel)+"");
            fastPeiSu.setText(printNum(getMaxArrayList(sel))+"");

            //步频
            myStepPin.setText(printNum(getMaxArrayList(stf))+"");

            double disTo = printNum(getMaxArrayList(elt) - getMinArrayList(elt));

            Log.i("海拔差",disTo+"");
            mySeaHeight.setText(disTo+"");

            //步幅
            myStepFu.setText(getEachArrayList(ssl)+"");
            //配速
            equitySpeed.setText(String.valueOf(getEachArrayList(sel)).replaceAll("\\.","'")+"''");
            fastSpeed.setText(String.valueOf(printNum(getMaxArrayList(sel))).replaceAll("\\.","'")+"''");
            lowerSpeed.setText(String.valueOf(printNum(getMinArrayList(sel))).replaceAll("\\.","'")+"''");

        }

        //运动配速
        mKiloList = new ArrayList<>();
        mSpeedList = new ArrayList<>();

        if(totalDis%1.0==0){
            fialDis = (int) (totalDis / 1.0);
        }else{
            fialDis = (int) (totalDis / 1.0) + 1;
        }

        for (int i = 0; i < sel.size(); i++) {
            if(totalDis > 1){

                mKiloList.add((i+1)+"");
                totalDis = totalDis -1;
                mSpeedList.add(printNum(sel.get(i))+"");

            }else if(totalDis>0 && totalDis<1){
                mKiloList.add("<"+fialDis+"");
                totalDis = totalDis -1;
                mSpeedList.add(printNum(sel.get(i))+"");
            }
        }
        adapter = new EquitySpeedAdapter(context, mKiloList, mSpeedList);
        //设置布局管理器，否则不出数据
        SpeedRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        SpeedRecyclerView.setAdapter(adapter);

    }

    /**
     * 保留一位小数
     *
     * @param d
     * @return
     */
    private double printNum(double d) {
        BigDecimal bdc = new BigDecimal(d);
        System.out.println(bdc);
        double d1 = bdc.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
        return d1;
    }


    private double getStepFu(double d){
        BigDecimal bdc = new BigDecimal(d);
        System.out.println(bdc);
        double d1 = bdc.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();

        return d1;
    }
    /**
     * 得到arraylist中最大的数
     *
     * @param list
     * @return
     */
    public double getMaxArrayList(ArrayList<Double> list) {
        double max = 0.0;
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i) > max) {
                max = list.get(i);
            }
        }
        return max;
    }

    /**
     * 得到ArrayList中最小值
     * @param list
     * @return
     */
    public double getMinArrayList(ArrayList<Double> list){
        double min = 0;
        if(list.size() > 0){
            min = list.get(0);
            for(int i = 0; i<list.size();i++){
                if(min>list.get(i)){
                    min = list.get(i);
                }
            }
        }
        return min;
    }

    @OnClick(R.id.back)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }


    public void getScreen() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("截屏", "截屏文件已保存至");
                //开一个线程来截图，就不会出现屏幕卡顿的现象 /storage/emulated/0/test_20161220155212.png   /storage/emulated/0/Test_ScreenShots/Screenshot_2017-01-10-11-34-54.png
                getScrollViewBitmap(scrollLayout);
            }
        }).start();
    }


    /**
     * 截取scrollview的屏幕
     **/
    public void getScrollViewBitmap(ScrollView scrollView) {
        int h = 0;
        Bitmap bitmap;
        // 获取listView实际高度
        for (int i = 0; i < scrollView.getChildCount(); i++) {
            h += scrollView.getChildAt(i).getHeight();
        }
        Log.d("scroll-h", "实际高度:" + h);
        Log.d("scroll-w", " 高度:" + scrollView.getHeight());
        // 创建对应大小的bitmap
        bitmap = Bitmap.createBitmap(scrollView.getWidth(), h,
                Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(bitmap);
        scrollView.draw(canvas);


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
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, foStream);
                foStream.flush();
                foStream.close();
                Log.i("截屏111", "截屏文件已保存至" + imageUrl);
                getTocken();
            } catch (Exception e) {
                Log.i("Show", e.toString());
            }
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
            Toast.makeText(SportRecordMixedActivity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Log.i("分享", "-------------取消-----------");
        }
    };


    /**
     * 绘图
     * 数据库中每1秒打一个点
     */
    public void huitu() {

        LatLong_impl latLong_impl = new LatLong_impl(context);                 //实例化数据库daoimpl类
        Log.i("时间",daytime.replace("-",""));
        ArrayList sportInfo = latLong_impl.getLatLong(daytime.replace("-",""));   //从数据库获取轨迹坐标值

        int n = sportInfo.size() - 1;
        for (int i = 0; i < n; i++) {

            speedspace = ((LatLongRemberBean) sportInfo.get(i)).getSpeedspace();           //配速

            if (speedspace.equals("Infinity")) {
                speedspace = 0 + "";
            }
            speedlist.add(speedspace);
            Log.i("speedlist配速1","**************---" + speedlist.get(i) + "---**************");

            stepfrequency = ((LatLongRemberBean) sportInfo.get(i)).getStepfrequency();      //步频
            if (stepfrequency.equals("Infinity")) {
                stepfrequency = 0 + "";
            }
            stepfrequencylist.add(stepfrequency);

            stepscope = ((LatLongRemberBean) sportInfo.get(i)).getStepscope();               //步幅
            if (stepscope.equals("Infinity")) {
                stepscope = 0 + "";
            }
            Log.i("步幅",stepscope);
            stepscopelist.add(String.valueOf(getStepFu(Double.parseDouble(stepscope))));

            elevation = ((LatLongRemberBean) sportInfo.get(i)).getElevation();               //海拔
            if (elevation.equals("Infinity")) {
                elevation = 0 + "";
            }
            elevationlist.add(elevation);

            Log.i("shit", speedspace + "---配速---" + stepfrequency + "---步频---" + stepscope + "---步幅---" + elevation + "---海拔---");
        }
    }


    public ArrayList<String> clearAcrossReapte(ArrayList list){
        ArrayList newList = new ArrayList();     //创建新集合
        Iterator it = list.iterator();        //根据传入的集合(旧集合)获取迭代器
        while(it.hasNext()){          //遍历老集合
            Object obj = it.next();       //记录每一个元素
            Log.i("去重前","------" + obj + "------");
            if(!newList.contains(obj)){      //如果新集合中不包含旧集合中的元素
                newList.add(obj);       //将元素添加
                Log.i("去重后","------" + newList.size() + "------");
            }
        }
        return newList;
    }

    /*public ArrayList<String> clearAcrossReapte(ArrayList list){
        for(int i = 0; i<list.size()-1;i++){
            if(list.get(i).equals(list.get(i+1))){
                list.remove(i+1);
            }

        }
        return list;
    }*/

    /**
     * ArrayList相邻数据去重
     */
   /* public ArrayList<String> clearAcrossReapte(ArrayList<String> list) {

        ArrayList objectList = new ArrayList();
        for (int i = 0; i < list.size() - 1; i++) {
            Log.i("去重前","------" + list.get(i) + "------");
            if (!list.get(i).equals(list.get(i + 1))) {
                Log.i("去重后","------" + list.get(i) + "------");
                list.remove(i+1);
                objectList.add(list.get(i));
            }
        }
        return objectList;

    }
*/
    /**
     * ArrrayList的平均值
     *
     * @param list
     * @return
     */
    public double getEachArrayList(ArrayList<Double> list) {
        double sum = 0.0;
        double result = 0.0;
        for (int i = 0; i < list.size(); i++) {
            sum =sum + list.get(i);
        }
        double eachNum = sum / (list.size());
        try {
            result = printNum(eachNum);

        }catch (NumberFormatException e){
            e.printStackTrace();
        }
        return result;
    }


}
