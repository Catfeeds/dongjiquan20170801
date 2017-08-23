package com.weiaibenpao.demo.chislim.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.map.activity.DrawTraceActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DisSportStyleActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_dis_sport_style);
        StatusBarUtil.setColor(this , getResources().getColor(R.color.ku_bg ) , 0);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.back, R.id.one, R.id.two, R.id.three, R.id.five, R.id.ten, R.id.half, R.id.full})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.one:
                goBack(1);
                break;
            case R.id.two:
                goBack(2);
                break;
            case R.id.three:
                goBack(3);
                break;
            case R.id.five:
                goBack(5);
                break;
            case R.id.ten:
                goBack(10);
                break;
            case R.id.half:
                goBack(500);
                break;
            case R.id.full:
                goBack(600);
                break;
        }
    }

    public void goBack(int num){
        Intent intent = new Intent(DisSportStyleActivity.this, DrawTraceActivity.class);
        intent.putExtra("disNum",num);
        startActivity(intent);
        finish();
    }
}
