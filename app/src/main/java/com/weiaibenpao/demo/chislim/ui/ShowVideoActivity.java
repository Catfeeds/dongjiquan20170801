package com.weiaibenpao.demo.chislim.ui;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.maning.mnvideoplayerlibrary.player.MNViderPlayer;
import com.weiaibenpao.demo.chislim.R;

public class ShowVideoActivity extends AppCompatActivity {

    private static final String TAG = "MNViderPlayer";

    private MNViderPlayer mnViderPlayer;

    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_video);
        StatusBarUtil.setColor(this , getResources().getColor(R.color.ku_bg ) , 0);
        mnViderPlayer = (MNViderPlayer) findViewById(R.id.mn_videoplayer);

        initPlayer();
    }


    private void initPlayer() {
        //初始化相关参数(必须放在Play前面)
        mnViderPlayer.setIsNeedBatteryListen(true);
        mnViderPlayer.setIsNeedNetChangeListen(true);

        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        //第一次进来先设置数据
        mnViderPlayer.setDataSource(url, "标题2");

        //播放完成监听
        mnViderPlayer.setOnCompletionListener(new MNViderPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                Log.i(TAG, "播放完成----");
            }
        });

        //网络监听
        mnViderPlayer.setOnNetChangeListener(new MNViderPlayer.OnNetChangeListener() {
            @Override
            public void onWifi(MediaPlayer mediaPlayer) {

            }

            @Override
            public void onMobile(MediaPlayer mediaPlayer) {
                //Toast.makeText(ShowVideoActivity.this, "请注意,当前网络状态切换为3G/4G网络", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onNoAvailable(MediaPlayer mediaPlayer) {
                Toast.makeText(ShowVideoActivity.this, "当前网络不可用,检查网络设置", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public void onBackPressed() {
        if (mnViderPlayer.isFullScreen()) {
            mnViderPlayer.setOrientationPortrait();
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mnViderPlayer.pauseVideo();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        //一定要记得销毁View
        if(mnViderPlayer != null){
            mnViderPlayer.destroyVideo();
            mnViderPlayer = null;
        }
        super.onDestroy();
    }
}
