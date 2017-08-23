package com.weiaibenpao.demo.chislim.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.jaeger.library.StatusBarUtil;
import com.weiaibenpao.demo.chislim.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ActivityRegisterActivity extends Activity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.myWebView)
    WebView myWebView;

    String registerUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        StatusBarUtil.setColor(this , getResources().getColor(R.color.ku_bg ) , 0);
        ButterKnife.bind(this);

        initData();
        initWebView();
    }
    public void initData(){
        Intent intent = getIntent();
        registerUrl = intent.getStringExtra("registerUrl");
        myWebView.loadUrl("http://www.chislim.com/company.html");                   //跟换这里的链接
    }

    public void initWebView(){

        /**
         * WebSettings用来设置WebView的相关属性
         */
        WebSettings webSettings = myWebView.getSettings();
        //设置WebView支持JavaScript
        webSettings.setJavaScriptEnabled(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //设置支持缩放
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDefaultTextEncodingName("UTF-8");

        /**
         * 判断页面加载过程
         * 当网络加载缓慢,我们可以再页面加载出来之前设置显示进度条
         * setWebChromeClient一般处理加载进度,设置标题,处理js对话框
         */
        myWebView.setWebChromeClient(new WebChromeClient(){
            ProgressDialog dialog;
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);

                if(newProgress == 100){     //页面加载完成
                    if(dialog != null && dialog.isShowing()){
                        dialog.dismiss();
                    }
                }else {                     //页面正在加载中
                    if(dialog == null){
                        dialog = new ProgressDialog(ActivityRegisterActivity.this);
                        dialog.setTitle("正在加载");
                        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        dialog.setProgress(newProgress);
                        dialog.show();
                    }else{
                        dialog.setProgress(newProgress);
                    }
                }
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                ActivityRegisterActivity.this.setTitle(title);           //设置标题
                super.onReceivedTitle(view, title);

            }
        });

        /**
         * WebViewClient帮助webView处理一些页面控制和请求通知
         * 当点击webview控件中的链接时,在这里设置显示在webview中,否则,会在浏览器打开
         */
        myWebView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view,String url){
                //设置网页在webview控件中打开,false在浏览器中打开
                view.loadUrl(url);
                return true;
            }
        });
    }

    /**
     * 重写onKeyDown方法用于在WebView中点击回退键时,返回url的上一层,
     * 否则会直接跳出WebView
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack()){
            myWebView.goBack();    //返回上一页
            return true;
        }else {
            System.exit(0);      //某些Android版本不生效
        }
        return super.onKeyDown(keyCode, event);
    }



    @OnClick(R.id.back)
    public void onClick() {
    }
}
