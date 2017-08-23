package com.weiaibenpao.demo.chislim.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.squareup.picasso.Picasso;
import com.weiaibenpao.demo.chislim.Interface.GetInterfaceSinerListener;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.music.bean.Bean_music;
import com.weiaibenpao.demo.chislim.music.bean.MusicSinerResult;
import com.weiaibenpao.demo.chislim.music.service.Start_Service;
import com.weiaibenpao.demo.chislim.util.GetIntentData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MusicActivity extends Activity {

    @BindView(R.id.back)
    ImageView back;
    private ImageButton imageButton_shang;
    private ImageButton imageButton_start;
    private ImageButton imageButton_xia;

    private ImageView mImage;
    private TextView mSongName;
    private TextView mMusicName;

    private SeekBar music_SeekBar;      //进度条控件
    private TextView when_time;         //当前进度时间控件
    private TextView coun_time;         // 歌曲总时间控件

    private Bean_music music;           //当前播放歌曲对象

    int pro;       //进度条滑动后的位置进度
    int pross;     //进度数据
    int count;     //歌曲时间总长

    private ArrayList music_list; //歌曲列表清单
    private int postion;  //当前播放歌曲的序列号

    private Intent intent;
    private Start_Service start_service;  //服务对象


    MyBroadcast myBroadcast;  //广播对象

    boolean flag = false;

    GetIntentData getIntentData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);
        StatusBarUtil.setColor(this , getResources().getColor(R.color.ku_bg ) , 0);
        ButterKnife.bind(this);
        getIntentData = new GetIntentData();

        //配置广播
        myBroadcast = new MyBroadcast();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.lijianbao.cn");
        MusicActivity.this.registerReceiver(myBroadcast, filter);

        //实例化布局控件
        initView();

        initData();

        Intent intent = getIntent();
        music_list = intent.getParcelableArrayListExtra("localMusicList");


    }

    public void initView() {

        imageButton_shang = (ImageButton) findViewById(R.id.shang);
        //上一曲按钮监听
        imageButton_shang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start_service.up();
                music_SeekBar.setProgress(0);
            }
        });

        imageButton_start = (ImageButton) findViewById(R.id.start_butt);
        //播放按钮监听
        imageButton_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (start_service.getState() == true) {
                    start_service.pause();
                } else {
                    start_service.play(pross, music, postion);
                }
            }
        });

        imageButton_xia = (ImageButton) findViewById(R.id.xia);
        //下一曲按钮监听
        imageButton_xia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start_service.next();
                music_SeekBar.setProgress(0);
            }
        });

        //播放界面大图
        mImage = (ImageView) findViewById(R.id.mImage);
        //歌曲名称
        mSongName = (TextView) findViewById(R.id.mSongName);
        //歌手名称
        mMusicName = (TextView) findViewById(R.id.mMusicName);

        //总时长显示TextView
        coun_time = (TextView) findViewById(R.id.coun_time);
        //进度条
        music_SeekBar = (SeekBar) findViewById(R.id.music_SeekBar);
        music_SeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (b == true) {
                    start_service.setPross(i);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


        //当前播放时间
        when_time = (TextView) findViewById(R.id.when_time);

    }

    public void initData() {
        music = new Bean_music();
    }

    /**
     * 根据传进去的Bean_music对象展示歌曲信息在播放界面
     * 歌曲图片,歌曲名称,歌曲作者
     *
     * @param music Bean_music歌曲对象
     */
    public void showMusicInfo(Bean_music music, boolean f) {

        mSongName.setText(music.getMusic_title());

        mMusicName.setText(music.getMusic_singer());

        if (f) {
            Picasso.with(this)
                    //      .load(music.getSong_image())
                    .load(R.mipmap.stop)
                    .placeholder(R.mipmap.stop)
                    .error(R.mipmap.stop)
                    .into(imageButton_start);
        } else {
            Picasso.with(this)
                    //      .load(music.getSong_image())
                    .load(R.mipmap.start)
                    .placeholder(R.mipmap.start)
                    .error(R.mipmap.start)
                    .into(imageButton_start);
        }
      //  getIntentData.getMusicSiner(Default.baiduKey,music.getMusic_singer());
        getIntentData.setGetInterfaceSinerListener(new GetInterfaceSinerListener() {
            @Override
            public void getDateList(MusicSinerResult.DataBean data) {

                Picasso.with(getApplicationContext())
                        // .load(music.getSong_image())
                        .load(data.getImage())
                        .placeholder(R.mipmap.zhanwei)
                        .error(R.mipmap.zhanwei)
                        .resize(130, 130)
                        .into(mImage);
            }
        });


    }

    @Override
    protected void onStart() {
        super.onStart();
        if (intent == null) {
            intent = new Intent(MusicActivity.this, Start_Service.class);
            bindService(intent, connection, Context.BIND_AUTO_CREATE);
        }
    }

    /**
     * 获取服务对象
     */
    public ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Start_Service.My_binder binder = (Start_Service.My_binder) service;
            start_service = binder.getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            start_service.onDestroy();
        }
    };

    @OnClick(R.id.back)
    public void onClick() {
        finish();
    }


    /**
     * 内部类实现进度条和界面动态显示
     */
    public class MyBroadcast extends BroadcastReceiver {

        public MyBroadcast() {

        }

        @Override
        public void onReceive(Context context, Intent intent) {
            //获取当前实时进度
            pross = intent.getIntExtra("pross", 0);
            //获取当前播放歌曲序列号
            postion = intent.getIntExtra("position", 0);
            //歌曲时间总长
            count = intent.getIntExtra("countTime", 0);
            //返回当前播放状态
            flag = intent.getBooleanExtra("isPlaying", false);

            //设置进度条最大值
            music_SeekBar.setMax(count);
            //设置进度条当前值，数据实时更新
            music_SeekBar.setProgress(pross);
            //设置进度条当前进度显示时间
            when_time.setText(getStandardTime((long) (pross / 1000)));
            //设置两位小数
            coun_time.setText(getStandardTime((long) (count / 1000)));

            music = intent.getParcelableExtra("musicBean");
            showMusicInfo(music, flag);
        }
    }

    /**
     * 将毫秒转换为分钟格式
     */
    public String getStandardTime(long countTime) {
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss", Locale.getDefault());
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+0"));
        Date date = new Date(countTime * 1000);
        sdf.format(date);
        return sdf.format(date);
    }
}

