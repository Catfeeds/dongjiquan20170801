package com.weiaibenpao.demo.chislim.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TabHost;
import android.widget.TextView;

import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.fragment.ChartFragment;
import com.weiaibenpao.demo.chislim.fragment.DetailFragment;
import com.weiaibenpao.demo.chislim.fragment.SpeedFragment;
import com.weiaibenpao.demo.chislim.fragment.TraceFragment;

/**
 * Created by zhangxing on 2017/1/7.
 */

public class SportRecordActivity extends AppCompatActivity {
    private FragmentTabHost fragmentTabHost;
    String sportMapStr;
    int sportTime;
    int sportDistance;
    int calories;
    String daytime;
    int sportSped;
    int sportStep;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.record_layout);
        fragmentTabHost = (FragmentTabHost) findViewById(R.id.fragment_tab_host);

        sportTime = getIntent().getIntExtra("sportTime", 0);//所用时间
        sportDistance = getIntent().getIntExtra("distance", 0);//距离
        calories = getIntent().getIntExtra("calories", 0);//大卡
        daytime = getIntent().getStringExtra("dayTime");//日期
        sportSped = getIntent().getIntExtra("sportSped", 0);//速度
        sportStep = getIntent().getIntExtra("sportStep", 0);//步数

        sportMapStr = getIntent().getStringExtra("sportImg");//url

        initView();
        updateTab(fragmentTabHost);//初始化字体和颜色
        //设置tab改变的监听事件，很重要
        fragmentTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                updateTab(fragmentTabHost);
            }
        });

    }

    private void initView() {

        fragmentTabHost.setup(this, getSupportFragmentManager(), R.id.content);

        TabHost.TabSpec trace = fragmentTabHost.newTabSpec("trace");
        trace.setIndicator("轨迹");
        Bundle bundle1 = new Bundle();
        bundle1.putString(TraceFragment.RESID_KEY,sportMapStr);
        fragmentTabHost.addTab(trace, TraceFragment.class, bundle1);

        TabHost.TabSpec detail = fragmentTabHost.newTabSpec("detail");
        detail.setIndicator("详情");
        Bundle bundle2 = new Bundle();
        bundle2.putString(DetailFragment.SPORTTIME,sportTime+"");
        bundle2.putString(DetailFragment.SPORTDISTANCE,sportDistance+"");
        bundle2.putString(DetailFragment.CALORIES,calories+"");
        bundle2.putString(DetailFragment.DAYTIME,daytime);
        bundle2.putString(DetailFragment.SPORTSPED,sportSped+"");
        bundle2.putString(DetailFragment.SPORTSTEP,sportStep+"");
        fragmentTabHost.addTab(detail, DetailFragment.class, bundle2);

        TabHost.TabSpec speed = fragmentTabHost.newTabSpec("speed");
        speed.setIndicator("配速");
        fragmentTabHost.addTab(speed, SpeedFragment.class, null);

        TabHost.TabSpec chart = fragmentTabHost.newTabSpec("chart");
        chart.setIndicator("图表");
        fragmentTabHost.addTab(chart, ChartFragment.class, null);
        //去掉分割线
        fragmentTabHost.getTabWidget().setDividerDrawable(null);
       // fragmentTabHost.getTabWidget().setBackgroundDrawable(getResources().getDrawable(R.drawable.tabhost_focus));
    }


   private void updateTab(final TabHost tabHost) {

        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
            View view = tabHost.getTabWidget().getChildAt(i);
            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextSize(16);
            //tv.setTypeface(Typeface.SERIF, 2); // 设置字体和风格
            if (tabHost.getCurrentTab() == i) {//选中
               view.setBackgroundDrawable(getResources().getDrawable(R.drawable.tabhost_focus));//选中后的背景
                tv.setTextColor(this.getResources().getColorStateList(
                        android.R.color.black));
            } else {//不选中
                view.setBackgroundDrawable(getResources().getDrawable(R.drawable.tabhost_no_focus));//非选择的背景
                tv.setTextColor(this.getResources().getColorStateList(
                        android.R.color.white));
            }
        }
    }



}




