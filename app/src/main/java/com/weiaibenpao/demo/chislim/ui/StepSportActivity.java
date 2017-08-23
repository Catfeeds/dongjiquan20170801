package com.weiaibenpao.demo.chislim.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.map.activity.DrawTraceActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StepSportActivity extends Activity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.one)
    TextView one;
    @BindView(R.id.two)
    TextView two;
    @BindView(R.id.three)
    TextView three;
    @BindView(R.id.five)
    TextView five;
    @BindView(R.id.ten)
    TextView ten;
    @BindView(R.id.half)
    TextView half;
    @BindView(R.id.full)
    TextView full;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step_sport);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back, R.id.one, R.id.two, R.id.three, R.id.five, R.id.ten, R.id.half, R.id.full})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.one:
                goBack(1000);
                break;
            case R.id.two:
                goBack(2000);
                break;
            case R.id.three:
                goBack(3000);
                break;
            case R.id.five:
                goBack(5000);
                break;
            case R.id.ten:
                goBack(10000);
                break;
            case R.id.half:
                goBack(15000);
                break;
            case R.id.full:
                goBack(20000);
                break;
        }
    }

    public void goBack(int num){
        Intent intent = new Intent(StepSportActivity.this, DrawTraceActivity.class);
        intent.putExtra("disNum",num);
        startActivity(intent);
        finish();
    }
}
