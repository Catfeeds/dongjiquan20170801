package com.weiaibenpao.demo.chislim.hurui.activity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.weiaibenpao.demo.chislim.R;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jameson.io.library.util.ToastUtils;


public class HWebViewActivity extends HBaseActivity {

    @BindView(R.id.close_iv)
    ImageView closeIv;
    private String urlStr; // 网络路径
    private String titleStr; //标题

    @BindView(R.id.tv_title)
    TextView tv_title;

    private static final String APP_CACAHE_DIRNAME = "/webcache";

    @BindView(R.id.webView)
    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hweb_view);
        ButterKnife.bind(this);


        urlStr = getIntent().getStringExtra("urlpath");
        titleStr = getIntent().getStringExtra("title");
        if (titleStr != null) {
            tv_title.setText(titleStr);
        } else {
            tv_title.setText("活动");
        }

        initWebView();

    }

    private void initWebView() {

        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);  //设置 缓存模式 LOAD_CACHE_ONLY
        //mWebView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ONLY);
        // 开启 DOM storage API 功能
        mWebView.getSettings().setDomStorageEnabled(true);
        //开启 database storage API 功能
        mWebView.getSettings().setDatabaseEnabled(true);
        String cacheDirPath = getFilesDir().getAbsolutePath() + APP_CACAHE_DIRNAME;
//      String cacheDirPath = getCacheDir().getAbsolutePath()+Constant.APP_DB_DIRNAME;

        //设置数据库缓存路径
        mWebView.getSettings().setDatabasePath(cacheDirPath);
        //设置  Application Caches 缓存目录
        mWebView.getSettings().setAppCachePath(cacheDirPath);
        //开启 Application Caches 功能
        mWebView.getSettings().setAppCacheEnabled(true);
        showProgressDialog();
        //第二种加载进度条的方法
//        mWebView.setWebChromeClient(new WebChromeClient(){
//            @Override
//            public void onProgressChanged(WebView view, int newProgress) {
//
//                if (newProgress==100)
//                    dismissProgressDialog();
//                super.onProgressChanged(view, newProgress);
//            }
//        });
        mWebView.loadUrl(urlStr);

        //第一种加载进度条的方法
        mWebView.setWebViewClient(new WebViewClient(){
            @Override
            public void onPageFinished(WebView view, String url) {

                   dismissProgressDialog();

                super.onPageFinished(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

               mWebView.loadUrl(url);
                return true;
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                ToastUtils.show( mActivity,"网页已不存在");
                super.onReceivedError(view, request, error);
            }
        } );





    }


    /**
     * 清除WebView缓存
     */
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

        File webviewCacheDir = new File(getCacheDir().getAbsolutePath() + "/webviewCache");

        //删除webview 缓存目录
        if (webviewCacheDir.exists()) {
            deleteFile(webviewCacheDir);
        }
        //删除webview 缓存 缓存目录
        if (appCacheDir.exists()) {
            deleteFile(appCacheDir);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        dismissProgressDialog();
    }

    /**
     * 递归删除 文件/文件夹
     *
     * @param file
     */
    public void deleteFile(File file) {


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

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
            mWebView.goBack();    //返回上一页
            return true;
        } else {
            finish();      //某些Android版本不生效        System.exit(0);    //某些Android版本不生效
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick(R.id.close_iv)
    public void onClick() {
        finish();
    }
}
