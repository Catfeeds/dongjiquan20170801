package com.weiaibenpao.demo.chislim.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.weiaibenpao.demo.chislim.Interface.GetInterfaceIntIdListener;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.adater.MonthRemberAdapter;
import com.weiaibenpao.demo.chislim.bean.UserBean;
import com.weiaibenpao.demo.chislim.util.GetIntentData;
import com.weiaibenpao.demo.chislim.util.SerializableHashMap;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CumulativeActivity extends AppCompatActivity {

    Intent intent = getIntent();
    HashMap hashMap;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.disAllText)
    TextView disAllText;
    @BindView(R.id.disAll)
    RecyclerView disAll;

    Context context;
    MonthRemberAdapter monthRemberAdapter;
    GetIntentData getIntentData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cumulative);
        StatusBarUtil.setColor(this , getResources().getColor(R.color.ku_bg ) , 0);
        ButterKnife.bind(this);

        context = getApplicationContext();

        getIntentData = new GetIntentData();
        getIntentData.getDis(UserBean.getUserBean().userId);
        getIntentData.setGetInterfaceIntIdListener(new GetInterfaceIntIdListener() {
            @Override
            public void getDateIntId(int intId) {
                disAllText.setText(intId + "KM");
            }
        });

        Bundle bundle = getIntent().getExtras();
        SerializableHashMap serializableHashMap = (SerializableHashMap) bundle.get("map");
        hashMap = serializableHashMap.getMap();

        disAll.setLayoutManager(new LinearLayoutManager(this));
        monthRemberAdapter = new MonthRemberAdapter(context, hashMap);
        disAll.setAdapter(monthRemberAdapter);
    }

    @OnClick(R.id.back)
    public void onClick() {
        finish();
    }
}
