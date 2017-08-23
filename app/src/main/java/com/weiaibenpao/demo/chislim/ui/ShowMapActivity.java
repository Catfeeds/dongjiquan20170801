package com.weiaibenpao.demo.chislim.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.weiaibenpao.demo.chislim.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhangxing on 2016/12/19.
 */

public class ShowMapActivity extends AppCompatActivity {

    ImageView backImg;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.titleTv)
    TextView titleTv;
    @BindView(R.id.myTop)
    RelativeLayout myTop;

    @BindView(R.id.mySportTime)
    TextView mySportTime;
    @BindView(R.id.myDis)
    TextView myDis;
    @BindView(R.id.myTime)
    TextView myTime;
    @BindView(R.id.mySped)
    TextView mySped;
    @BindView(R.id.myCalior)
    TextView myCalior;
    @BindView(R.id.activity_medal)
    RelativeLayout activityMedal;
    @BindView(R.id.myStep)
    TextView myStep;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.showmap_layout);
        StatusBarUtil.setColor(this , getResources().getColor(R.color.ku_bg ) , 0);
        ButterKnife.bind(this);
        backImg = (ImageView) findViewById(R.id.back);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        int sportTime = getIntent().getIntExtra("sportTime", 0);//所用时间
        int sportDistance = getIntent().getIntExtra("distance", 0);//距离
        int calories = getIntent().getIntExtra("calories", 0);//大卡
        String daytime = getIntent().getStringExtra("dayTime");//日期
        int sportSped = getIntent().getIntExtra("sportSped", 0);//速度
        String sportMapStr = getIntent().getStringExtra("sportImg");//url
        int sportStep = getIntent().getIntExtra("sportStep", 0);//步数

        mySportTime.setText(daytime);
        myDis.setText(sportDistance + "");
        myCalior.setText(calories + "");
        myTime.setText(sportTime + "");
        mySped.setText(sportSped + "");
        myStep.setText(sportStep+"");
       /* Picasso.with(this)
                .load(sportMapStr)
                .error(R.mipmap.zhanwei)
                .placeholder(R.mipmap.zhanwei)
                .into(sportImg);*/
    }


}
