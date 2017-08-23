package com.weiaibenpao.demo.chislim.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.weiaibenpao.demo.chislim.util.Default.url;

public class MyActivityActivity extends Activity {

    private WebView myWebView;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.titleTv)
    TextView titleTv;
    @BindView(R.id.goActivity)
    TextView goActivity;
    String title;
    String texturl;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_activity);
        StatusBarUtil.setColor(this , getResources().getColor(R.color.ku_bg ) , 0);
        ButterKnife.bind(this);

        initWebView();
        initData();


    }

    public void initData() {
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        texturl = intent.getStringExtra("texturl");
        titleTv.setText(title);
        myWebView.loadUrl(texturl);
    }

    public void initWebView() {
        /**
         * WebSettings用来设置WebView的相关属性
         */
        myWebView = (WebView) findViewById(R.id.relative_webview);


        WebSettings webSettings = myWebView.getSettings();

        webSettings.setLoadWithOverviewMode(true);
        //设置WebView支持JavaScript
        webSettings.setJavaScriptEnabled(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //设置支持缩放
        webSettings.setBuiltInZoomControls(true);
        webSettings.setDefaultTextEncodingName("UTF-8");
        webSettings.setUseWideViewPort(true);// 这个很关键
       // webSettings.setDefaultFontSize(56);
        //webSettings.setTextSize(WebSettings.TextSize.LARGER);


         /*
         * 判断页面加载过程
         * 当网络加载缓慢,我们可以再页面加载出来之前设置显示进度条
         * setWebChromeClient一般处理加载进度,设置标题,处理js对话框
         */
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
                        dialog = new ProgressDialog(MyActivityActivity.this);
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
                MyActivityActivity.this.setTitle(title);           //设置标题
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

    /**
     * 重写onKeyDown方法用于在WebView中点击回退键时,返回url的上一层,
     * 否则会直接跳出WebView
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && myWebView.canGoBack()) {
            myWebView.goBack();    //返回上一页
            return true;
        } else {
            finish();      //某些Android版本不生效
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick({R.id.back, R.id.goActivity})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.goActivity:
                /*目前支持文本、图片（本地及URL）、音频及视频URL的分享,要分享图片的URL*/
                UMImage image = new UMImage(MyActivityActivity.this, url);
                final SHARE_MEDIA[] displaylist = new SHARE_MEDIA[]
                        {
                                SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA,
                                SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE
                        };
                new ShareAction(this).setDisplayList(displaylist)
                        .withText("")
                        .withTitle(title)
                        .withTargetUrl(texturl)
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
            Toast.makeText(MyActivityActivity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
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
                new ShareAction(MyActivityActivity.this).setPlatform(share_media).setCallback(umShareListener)
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

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
