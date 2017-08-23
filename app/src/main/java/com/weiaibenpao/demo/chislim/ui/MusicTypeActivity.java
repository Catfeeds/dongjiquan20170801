package com.weiaibenpao.demo.chislim.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.squareup.picasso.Picasso;
import com.weiaibenpao.demo.chislim.Interface.GetObjectListener;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.bean.Li_MusicResult;
import com.weiaibenpao.demo.chislim.hurui.activity.HMuiscListActivity;
import com.weiaibenpao.demo.chislim.map.customview.CircleImageView;
import com.weiaibenpao.demo.chislim.util.ACache;
import com.weiaibenpao.demo.chislim.util.GetIntentData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MusicTypeActivity extends AppCompatActivity {

    GetIntentData getIntentData;
    Context context;
    ACache aCache;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.musicType1text)
    TextView musicType1text;
    @BindView(R.id.musicType1)
    RelativeLayout musicType1;
    @BindView(R.id.musicType2text)
    TextView musicType2text;
    @BindView(R.id.musicType2)
    RelativeLayout musicType2;
    @BindView(R.id.musicType3text)
    TextView musicType3text;
    @BindView(R.id.musicType3)
    RelativeLayout musicType3;
    @BindView(R.id.musicType4text)
    TextView musicType4text;
    @BindView(R.id.musicType4)
    RelativeLayout musicType4;

    Li_MusicResult.DataBean musicResult;
    @BindView(R.id.musicType1img)
    CircleImageView musicType1img;
    @BindView(R.id.musicType2img)
    CircleImageView musicType2img;
    @BindView(R.id.musicType3img)
    CircleImageView musicType3img;
    @BindView(R.id.musicType4img)
    CircleImageView musicType4img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_music_type);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.ku_bg), 0);
        ButterKnife.bind(this);

        //实例化网络数据工具类
        getIntentData = new GetIntentData();

        context = getApplicationContext();
        aCache = ACache.get(context);


        initACache();
        initIntentData();
    }

    /**
     * 获取缓存数据
     */
    public void initACache() {
        musicResult = (Li_MusicResult.DataBean) aCache.getAsObject("music");
        if (musicResult != null) {
            initView(musicResult);
        }
    }

    /**
     * 获取数据
     */
    public void initIntentData() {
        getIntentData.getMusicList(context);
        getIntentData.setGetObjectListener(new GetObjectListener() {
            @Override
            public void getObject(Object object) {
                musicResult = (Li_MusicResult.DataBean) object;
                initView(musicResult);
            }
        });
    }

    /**
     * 更新界面
     */
    public void initView(Li_MusicResult.DataBean musicResult) {
        //音乐类别一
        Picasso.with(context)
                .load(musicResult.getMusicTypelist().get(0).getMusicTypeImg())
                //.placeholder()
                //.error()
                .into(musicType1img);
        musicType1text.setText(musicResult.getMusicTypelist().get(0).getMusicType());
        //音乐类别二
        Picasso.with(context)
                .load(musicResult.getMusicTypelist().get(1).getMusicTypeImg())
                //.placeholder()
                //.error()
                .into(musicType2img);
        musicType2text.setText(musicResult.getMusicTypelist().get(1).getMusicType());
        //音乐类别三
        Picasso.with(context)
                .load(musicResult.getMusicTypelist().get(2).getMusicTypeImg())
                //.placeholder()
                //.error()
                .into(musicType3img);
        musicType3text.setText(musicResult.getMusicTypelist().get(2).getMusicType());
        //音乐类别四
        Picasso.with(context)
                .load(musicResult.getMusicTypelist().get(3).getMusicTypeImg())
                //.placeholder()
                //.error()
                .into(musicType4img);
        musicType4text.setText(musicResult.getMusicTypelist().get(3).getMusicType());
    }

    @OnClick({R.id.musicType1img, R.id.musicType1text, R.id.musicType1, R.id.musicType2img,
            R.id.musicType2text, R.id.musicType2, R.id.musicType3img, R.id.musicType3text,
            R.id.musicType3, R.id.musicType4img, R.id.musicType4text, R.id.musicType4, R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.musicType1img:
                intentActivity(1);
                break;
            case R.id.musicType1text:
                intentActivity(1);
                break;
            case R.id.musicType1:
                intentActivity(1);
                break;
            case R.id.musicType2img:
                intentActivity(2);
                break;
            case R.id.musicType2text:
                intentActivity(2);
                break;
            case R.id.musicType2:
                intentActivity(2);
                break;
            case R.id.musicType3img:
                intentActivity(3);
                break;
            case R.id.musicType3text:
                intentActivity(3);
                break;
            case R.id.musicType3:
                intentActivity(3);
                break;
            case R.id.musicType4img:
                intentActivity(4);
                break;
            case R.id.musicType4text:
                intentActivity(4);
                break;
            case R.id.musicType4:
                intentActivity(4);
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    /**
     * 跳转
     */
    public void intentActivity(int i) {
        Intent intent = new Intent(MusicTypeActivity.this, HMuiscListActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("musicResult", musicResult);
        bundle.putInt("num", i);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @OnClick(R.id.back)
    public void onClick() {
        finish();
    }
}
