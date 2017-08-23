package com.weiaibenpao.demo.chislim.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.map.activity.DrawTraceActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TimeSportStyleActivity extends AppCompatActivity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.oneMin)
    TextView oneMin;
    @BindView(R.id.twoMin)
    TextView twoMin;
    @BindView(R.id.threeMin)
    TextView threeMin;
    @BindView(R.id.oneHour)
    TextView oneHour;
    @BindView(R.id.twoHour)
    TextView twoHour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_sport_style);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back, R.id.oneMin, R.id.twoMin, R.id.threeMin, R.id.oneHour, R.id.twoHour})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.oneMin:
                goBack(10);
                break;
            case R.id.twoMin:
                goBack(20);
                break;
            case R.id.threeMin:
                goBack(30);
                break;
            case R.id.oneHour:
                goBack(60);
                break;
            case R.id.twoHour:
                goBack(120);
                break;
        }
    }

    public void goBack(int num){
        Intent intent = new Intent(TimeSportStyleActivity.this, DrawTraceActivity.class);
        intent.putExtra("disNum",num);
        startActivity(intent);
        finish();
    }
}
