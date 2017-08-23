package com.weiaibenpao.demo.chislim.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.squareup.picasso.Picasso;
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

public class MedalInfoActivity extends Activity {
    String imageUrl;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.mySuccess)
    ImageView mySuccess;
    @BindView(R.id.share)
    TextView share;

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medal);
        StatusBarUtil.setColor(this , getResources().getColor(R.color.ku_bg ) , 0);
        ButterKnife.bind(this);
        context = getApplicationContext();

        Intent intent = getIntent();
        imageUrl = intent.getStringExtra("medal");

        Picasso.with(context).load(imageUrl).into(mySuccess);
    }

    @OnClick({R.id.back, R.id.mySuccess, R.id.share})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.mySuccess:
                break;
            case R.id.share:
                //String media = GetandSaveCurrentImage();
                //mListener.shareSport("IPO时刻为您的健康服务", "爱运动爱生活", "http://112.74.28.179:8080/Weiaibenpao/Image/test6.png", "http://112.74.28.179:8080/Weiaibenpao/video/qms006.mp4");
                /*目前支持文本、图片（本地及URL）、音频及视频URL的分享,要分享图片的URL*/
                UMImage image = new UMImage(MedalInfoActivity.this, imageUrl);
                final SHARE_MEDIA[] displaylist = new SHARE_MEDIA[]
                        {
                                SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE, SHARE_MEDIA.SINA,
                                SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE
                        };
                new ShareAction(MedalInfoActivity.this).setDisplayList(displaylist)
                        .withText("今天我是冠军")            //内容
                        .withTitle("健身将是一种乐趣")            //标题
                        .withTargetUrl(imageUrl)            //链接
                        .withMedia(image)               //图片
                        .setListenerList(umShareListener)
                        .open();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
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
            Toast.makeText(MedalInfoActivity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
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
                new ShareAction(MedalInfoActivity.this).setPlatform(share_media).setCallback(umShareListener)
                        .withText("多平台分享")
                        .share();
            }
        }
    };

}
