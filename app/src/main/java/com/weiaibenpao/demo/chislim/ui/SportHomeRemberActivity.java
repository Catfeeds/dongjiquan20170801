package com.weiaibenpao.demo.chislim.ui;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
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
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.customs.UploadProgressDialog;
import com.weiaibenpao.demo.chislim.util.Default;
import com.weiaibenpao.demo.chislim.util.FileIOUtil;
import com.weiaibenpao.demo.chislim.util.LatLong_impl;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SportHomeRemberActivity extends Activity {

    @BindView(R.id.mySportDis)
    TextView mySportDis;
    @BindView(R.id.mySportTime)
    TextView mySportTime;
    @BindView(R.id.myCor)
    TextView myCor;

    Intent getIntent;
    double sportDis;
    int sportCorli;
    String sportTime;

    Context context;
    @BindView(R.id.sportDate)
    TextView sportDate;
    LatLong_impl latLong_impl;

    DecimalFormat decimalFormat;
    String cutUrl;
    String cutImgUrl;
    String imageUrlline;
    String mapStr;
    @BindView(R.id.myImage)
    ImageView myImage;
    @BindView(R.id.myImageLine)
    ImageView myImageLine;
    @BindView(R.id.activity_sport_home_rember)
    RelativeLayout activitySportHomeRember;
    @BindView(R.id.myShare)
    ImageView myShare;
    @BindView(R.id.back)
    ImageView back;
    UploadProgressDialog progressDialog;
    String url = "http://112.74.28.179:8080/Chislim/Travel_notes_Servlet?dowhat=getUpLoadToken";
    OkHttpClient client;
    Request request;
    UploadManager uploadManager;
    String token;
    DecimalFormat myFormatter;
    StringBuffer mResultsName;
    @BindView(R.id.share_layout)
    RelativeLayout shareLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport_home_rember);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this , getResources().getColor(R.color.ku_bg ) , 0);
        context = getApplicationContext();
        progressDialog = new UploadProgressDialog(this, "正在上传数据!");
        latLong_impl = new LatLong_impl();

        initData();  //获取数据
        initView();

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
    }

    public void initData() {
        getIntent = getIntent();
       // sportDis = getIntent.getIntExtra("sportDis", 0);
        sportDis =getIntent.getDoubleExtra("sportDis", 0);
        Log.i("蓝牙距离2",sportDis+"");
        sportCorli = getIntent.getIntExtra("sportCor", 0);
        sportTime = getIntent.getStringExtra("sportTime");
        cutUrl = getIntent.getStringExtra("imageUrl");
        imageUrlline = getIntent.getStringExtra("imageUrlline");

        // sportSpeed = ((double) sportDis / (DateUtil.getTime(sportTime) * 60)) * 3.6;
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

    public void initView() {
        sportDate.setText("跑步结果");
        mySportDis.setText(sportDis + "");
        mySportTime.setText(sportTime + "");
        myCor.setText(sportCorli + "");


        Glide.with(context)
                .load(cutUrl)
                .skipMemoryCache(true) //跳过内存缓存
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)     //缓存设置
                .priority(Priority.NORMAL)    //优先级  LOW     NORMAL      HIGH      IMMEDIATE
                .into(myImage);

        Glide.with(context)
                .load(imageUrlline)
                .skipMemoryCache(true) //跳过内存缓存
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)     //缓存设置
                .priority(Priority.NORMAL)    //优先级  LOW     NORMAL      HIGH      IMMEDIATE
                .into(myImageLine);

        if (decimalFormat == null) {
            decimalFormat = new DecimalFormat("0.0");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        }
        // mySpeed.setText(decimalFormat.format(sportSpeed));
    }

    //保存自定义view的截图
    private void saveCustomViewBitmap() {
        //获取自定义view图片的大小
        Bitmap temBitmap = Bitmap.createBitmap(activitySportHomeRember.getWidth(), activitySportHomeRember.getHeight(), Bitmap.Config.ARGB_8888);
        //使用Canvas，调用自定义view控件的onDraw方法，绘制图片
        Canvas canvas = new Canvas(temBitmap);
        activitySportHomeRember.draw(canvas);

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

    @OnClick({R.id.back, R.id.myShare})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                showDialog();
                break;
            case R.id.myShare:
                progressDialog.showDialog();
                getScreen();
                break;
        }
    }

    //第二步获取tocken
    public void getTocken() {
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) throws IOException {

                token = response.body().string();
                Log.i("七牛", token);
                Log.i("七牛", cutUrl);
                //第三步上传
                getUpimg(cutImgUrl, 0);
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
                                    //  shareToPlaforms(mapStr);
                                    // cutUrl = getIntent.getStringExtra("imageUrl");
                                    //imageUrlline = getIntent.getStringExtra("imageUrlline");cutImgUrl
                                    Log.i("图片", mapStr);
                                    if(cutUrl != "" && cutUrl != null){
                                           File deletefile1 = new File(cutUrl);
                                           deletefile1.delete();
                                    }
                                    if(imageUrlline != "" && imageUrlline != null){
                                           File deletefile2 = new File(imageUrlline);
                                           deletefile2.delete();
                                    }
                                    if(cutImgUrl != "" && cutImgUrl != null){
                                           File deletefile3 = new File(cutImgUrl);
                                           deletefile3.delete();
                                    }

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

    //分享
    public void shareToPlaforms(String url) {

                /*目前支持文本、图片（本地及URL）、音频及视频URL的分享,要分享图片的URL*/
        UMImage image = new UMImage(SportHomeRemberActivity.this, url);
        final SHARE_MEDIA[] displaylist = new SHARE_MEDIA[]
                {
                        SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA,
                        SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE
                };
        new ShareAction(this).setDisplayList(displaylist)
                .withText("精美的景点，就等一双发现美的眼睛！")
                .withTitle("我的有趣跑法")
                .withTargetUrl(url)
                .withMedia(image)
                .setListenerList(umShareListener)
                .open();

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
            Toast.makeText(SportHomeRemberActivity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
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
                new ShareAction(SportHomeRemberActivity.this).setPlatform(share_media).setCallback(umShareListener)
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
     * 重写onKeyDown方法
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK)) {
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
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("运动提示");
        builder.setMessage("你尚未分析今天的跑步故事，是否分享？");
        builder.setNegativeButton("放弃", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });
        builder.setPositiveButton("分享", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                progressDialog.showDialog();
                getScreen();
            }
        });
        builder.show();
    }

    public void getScreen() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("截屏", "截屏文件已保存至");
                //开一个线程来截图，就不会出现屏幕卡顿的现象 /storage/emulated/0/test_20161220155212.png   /storage/emulated/0/Test_ScreenShots/Screenshot_2017-01-10-11-34-54.png
                saveCustomViewBitmapc();
            }
        }).start();
    }

    //保存自定义view的截图
    private void saveCustomViewBitmapc() {
        //获取自定义view图片的大小
        Bitmap temBitmap = Bitmap.createBitmap(shareLayout.getWidth(), shareLayout.getHeight(), Bitmap.Config.ARGB_8888);
        //使用Canvas，调用自定义view控件的onDraw方法，绘制图片
        Canvas canvas = new Canvas(temBitmap);
        //shareLayout.setIcon(false);
        shareLayout.draw(canvas);

        //输出到sd卡
        if (FileIOUtil.getExistStorage()) {
            cutImgUrl = FileIOUtil.GetInstance().getFilePathAndName();
            FileIOUtil.GetInstance().onFolderAnalysis(cutImgUrl);
            File file = new File(FileIOUtil.GetInstance().getFilePathAndName());
            try {
                if (!file.exists()) {
                    file.createNewFile();
                }
                FileOutputStream foStream = new FileOutputStream(file);
                temBitmap.compress(Bitmap.CompressFormat.PNG, 100, foStream);
                foStream.flush();
                foStream.close();
                Log.i("截屏", "截屏文件已保存至" + cutImgUrl);
                getTocken();
            } catch (Exception e) {
                Log.i("Show", e.toString());
            }
        }
    }
}
