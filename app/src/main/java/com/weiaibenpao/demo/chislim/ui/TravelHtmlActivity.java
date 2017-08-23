package com.weiaibenpao.demo.chislim.ui;

/*public class TravelHtmlActivity extends Activity {

    private static final String TAG = MainActivity.class.getSimpleName();
    //设置缓存webview的路径
    private static final String APP_CACAHE_DIRNAME = "/webcache";


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.titleTv)
    TextView titleTv;

    TravelResult.TravelBean tra;
    GetIntentData getIntentData;
    @BindView(R.id.share)
    TextView share;

    ProgressDialog dialog;
<<<<<<< HEAD

=======
    @BindView(R.id.myWebView)
    WebView myWebView;
>>>>>>> origin/master

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_html);
        ButterKnife.bind(this);
        getIntentData = new GetIntentData();

        initWebView();
        initData();
    }

    public void initData() {
        Intent intent = getIntent();
        tra = intent.getParcelableExtra("travel");
        titleTv.setText(tra.getT_name());

        myWebView.loadUrl(tra.getT_text());
    }

    public void initWebView() {
        *//**
         * WebSettings用来设置WebView的相关属性
<<<<<<< HEAD

        myWebView = (WebView) findViewById(R.id.webview_layout);
=======
         *//*

>>>>>>> origin/master

        WebSettings webSettings = myWebView.getSettings();
        webSettings.setLoadWithOverviewMode(true);

        webSettings.setRenderPriority(WebSettings.RenderPriority.HIGH);

        // 开启 DOM storage API 功能
        webSettings.setDomStorageEnabled(true);
        //开启 database storage API 功能
        webSettings.setDatabaseEnabled(true);
        String cacheDirPath = getFilesDir().getAbsolutePath() + APP_CACAHE_DIRNAME;
        //设置数据库缓存路径
        webSettings.setDatabasePath(cacheDirPath);
        //设置  Application Caches 缓存目录
        webSettings.setAppCachePath(cacheDirPath);
        //开启 Application Caches 功能
        webSettings.setAppCacheEnabled(true);

        webSettings.setLoadWithOverviewMode(true);
        //设置WebView支持JavaScript
        webSettings.setJavaScriptEnabled(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //设置支持缩放
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDefaultTextEncodingName("UTF-8");


        if (isNetworkAvailable(getApplicationContext())) {
            //dialog.dismiss();
            //有网络连接，设置默认缓存模式
            webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            //无网络连接，设置本地缓存模式
            webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
            //dialog.dismiss();
        }

        *//**
         * 判断页面加载过程
         * 当网络加载缓慢,我们可以再页面加载出来之前设置显示进度条
         * setWebChromeClient一般处理加载进度,设置标题,处理js对话框
         *//*
        myWebView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);

                if (newProgress == 100) {     //页面加载完成
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                } else {                     //页面正在加载中
                    if (dialog == null) {
                        dialog = new ProgressDialog(TravelHtmlActivity.this);
                        dialog.setTitle("正在加载");
                        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        dialog.setProgress(newProgress);
                        dialog.show();
                    } else {
                        dialog.setProgress(newProgress);
                    }
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                TravelHtmlActivity.this.setTitle(title);           //设置标题
                super.onReceivedTitle(view, title);

            }
        });


        *//**
         * WebViewClient帮助webView处理一些页面控制和请求通知
         * 当点击webview控件中的链接时,在这里设置显示在webview中,否则,会在浏览器打开
         *//*
        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //设置网页在webview控件中打开,false在浏览器中打开
                view.loadUrl(url);
                return true;
            }
        });
    }

    @OnClick({R.id.back, R.id.share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.share:
                  *//*目前支持文本、图片（本地及URL）、音频及视频URL的分享,要分享图片的URL*//*
                UMImage image = new UMImage(TravelHtmlActivity.this, tra.getT_image());
                final SHARE_MEDIA[] displaylist = new SHARE_MEDIA[]
                        {
                                SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA,
                                SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE
                        };
                new ShareAction(this).setDisplayList(displaylist)
                        .withText("")
                        .withTitle(tra.getT_name())
                        .withTargetUrl(tra.getT_text())
                        .withMedia(image)
                        .setListenerList(umShareListener)
                        .open();
                break;
        }
    }

    *//**
     * 分享回调
     *//*
    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Log.i("分享", "-------------成功-----------");
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(TravelHtmlActivity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Log.i("分享", "-------------取消-----------");
        }
    };

    *//**
     * 分享面板添加按钮的回调
     *//*
    private ShareBoardlistener shareBoardlistener = new ShareBoardlistener() {

        @Override
        public void onclick(SnsPlatform snsPlatform, SHARE_MEDIA share_media) {
            if (share_media == null) {
                if (snsPlatform.mKeyword.equals("11")) {
                    Log.i("分享面板添加按钮的回调", "-------------add button success-----------");
                }
            } else {
                new ShareAction(TravelHtmlActivity.this).setPlatform(share_media).setCallback(umShareListener)
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

    *//**
     * 检测当前网络可用
     *
     * @param context
     * @return
     *//*
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }

    *//**
     * 清除WebView缓存
     *//*
    public void clearWebViewCache() {

        //清理Webview缓存数据库
        try {
            deleteDatabase("webview.db");
            deleteDatabase("webviewCache.db");
        } catch (Exception e) {
            e.printStackTrace();
        }

        //WebView 缓存文件
        File appCacheDir = new File(getFilesDir().getAbsolutePath() + APP_CACAHE_DIRNAME);
        Log.e(TAG, "appCacheDir path=" + appCacheDir.getAbsolutePath());

        File webviewCacheDir = new File(getCacheDir().getAbsolutePath() + "/webviewCache");
        Log.e(TAG, "webviewCacheDir path=" + webviewCacheDir.getAbsolutePath());

        //删除webview 缓存目录
        if (webviewCacheDir.exists()) {
            deleteFile(webviewCacheDir);
        }
        //删除webview 缓存 缓存目录
        if (appCacheDir.exists()) {
            deleteFile(appCacheDir);
        }
    }

/**
 * 递归删除 文件/文件夹
 *
 *//*
    public void deleteFile(File file) {

        Log.i(TAG, "delete file path=" + file.getAbsolutePath());

        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            } else if (file.isDirectory()) {
                File files[] = file.listFiles();
                for (int i = 0; i < files.length; i++) {
                    deleteFile(files[i]);
                }
            }
            file.delete();
        } else {
            Log.e(TAG, "delete file no exists " + file.getAbsolutePath());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        myWebView = null;

    }

}*/


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.Log;
import com.umeng.socialize.utils.ShareBoardlistener;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.bean.TravelResult;
import com.weiaibenpao.demo.chislim.util.Default;
import com.weiaibenpao.demo.chislim.util.GetIntentData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class TravelHtmlActivity extends Activity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.titleTv)
    TextView titleTv;
    @BindView(R.id.myWebView)
    WebView myWebView;
    TravelResult.TravelBean tra;
    GetIntentData getIntentData;
    @BindView(R.id.share)
    TextView share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_html);
        StatusBarUtil.setTranslucent(TravelHtmlActivity.this, 70);
        ButterKnife.bind(this);
        getIntentData = new GetIntentData();

        initData();
        initWebView();
    }

    public void initData() {
        Intent intent = getIntent();
        tra = intent.getParcelableExtra("travel");
        titleTv.setText(tra.getT_name());
        myWebView.loadUrl(tra.getT_text());
    }

    public void initWebView() {

/**
 * WebSettings用来设置WebView的相关属性
 * <p>
 * 判断页面加载过程
 * 当网络加载缓慢,我们可以再页面加载出来之前设置显示进度条
 * setWebChromeClient一般处理加载进度,设置标题,处理js对话框
 * <p>
 * WebViewClient帮助webView处理一些页面控制和请求通知
 * 当点击webview控件中的链接时,在这里设置显示在webview中,否则,会在浏览器打开
 * <p>
 * 分享回调
 * <p>
 * 分享面板添加按钮的回调
 * <p>
 * 判断页面加载过程
 * 当网络加载缓慢,我们可以再页面加载出来之前设置显示进度条
 * setWebChromeClient一般处理加载进度,设置标题,处理js对话框
 * <p>
 * WebViewClient帮助webView处理一些页面控制和请求通知
 * 当点击webview控件中的链接时,在这里设置显示在webview中,否则,会在浏览器打开
 * <p>
 * 分享回调
 * <p>
 * 分享面板添加按钮的回调
 */

        WebSettings webSettings = myWebView.getSettings();
        webSettings.setLoadWithOverviewMode(true);
        //设置WebView支持JavaScript
        webSettings.setJavaScriptEnabled(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //设置支持缩放
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDefaultTextEncodingName("UTF-8");
        //webSettings.setUseWideViewPort(true);// 这个很关键


    /**
     * 判断页面加载过程
     * 当网络加载缓慢,我们可以再页面加载出来之前设置显示进度条
     * setWebChromeClient一般处理加载进度,设置标题,处理js对话框
     */

        myWebView.setWebChromeClient(new WebChromeClient() {
            ProgressDialog dialog;

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);

                if (newProgress == 100) {     //页面加载完成
                    if (dialog != null && dialog.isShowing()) {
                        dialog.dismiss();
                    }
                } else {                     //页面正在加载中
                    if (dialog == null) {
                        dialog = new ProgressDialog(TravelHtmlActivity.this);
                        dialog.setTitle("正在加载");
                        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        dialog.setProgress(newProgress);
                        dialog.show();
                    } else {
                        dialog.setProgress(newProgress);
                    }
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                TravelHtmlActivity.this.setTitle(title);           //设置标题
                super.onReceivedTitle(view, title);

            }
        });


    /**
         * WebViewClient帮助webView处理一些页面控制和请求通知
         * 当点击webview控件中的链接时,在这里设置显示在webview中,否则,会在浏览器打开
         */

        myWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //设置网页在webview控件中打开,false在浏览器中打开
                view.loadUrl(url);
                return true;
            }
        });
    }

    @OnClick({R.id.back, R.id.share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.share:

    /*目前支持文本、图片（本地及URL）、音频及视频URL的分享,要分享图片的URL*/
                Log.i("图片",Default.urlPic + tra.getT_image() + tra.getT_text());
                String imgUrl = tra.getT_text();
                UMImage image = new UMImage(TravelHtmlActivity.this,tra.getT_image());
                final SHARE_MEDIA[] displaylist = new SHARE_MEDIA[]
                        {
                                 SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA,
                                SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE
                        };
                new ShareAction(this).setDisplayList(displaylist)
                        .withText("精美的景点，就等一双发现美的眼睛！")
                        .withTitle(tra.getT_name())
                        .withTargetUrl(imgUrl)
                        .withMedia(image)
                        .setListenerList(umShareListener)
                        .open();
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
            Toast.makeText(TravelHtmlActivity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
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
                new ShareAction(TravelHtmlActivity.this).setPlatform(share_media).setCallback(umShareListener)
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
}

