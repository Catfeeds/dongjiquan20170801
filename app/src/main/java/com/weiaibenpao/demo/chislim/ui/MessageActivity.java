package com.weiaibenpao.demo.chislim.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.weiaibenpao.demo.chislim.Interface.GetInterfaceBooleanListener;
import com.weiaibenpao.demo.chislim.Interface.GetInterfaceVideoListener;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.adater.RecyclerNotesMessageAdapter;
import com.weiaibenpao.demo.chislim.bean.UserBean;
import com.weiaibenpao.demo.chislim.util.GetIntentData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MessageActivity extends Activity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.message)
    RecyclerView message;

    Context context;

    GetIntentData getIntent;
    @BindView(R.id.messageText)
    EditText messageText;
    @BindView(R.id.messageSend)
    TextView messageSend;

    int tn_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        StatusBarUtil.setColor(this , getResources().getColor(R.color.ku_bg ) , 0);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        tn_id = intent.getIntExtra("tn_id",0);

        context = getApplicationContext();

        initView();

        initData(tn_id);
    }

    public void initView() {
        message.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


    }

    public void initData(int tnid) {
        getIntent = new GetIntentData();
        getIntent.getMessage(context, "getAllMessage", tnid);
        message.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        getIntent.setGetIntentDataListener(new GetInterfaceVideoListener() {
            @Override
            public void getDateList(ArrayList dateList) {
                RecyclerNotesMessageAdapter mAdapter = new RecyclerNotesMessageAdapter(context, dateList);
                message.setAdapter(mAdapter);
            }
        });
    }

    @OnClick({R.id.back, R.id.messageSend})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.messageSend:
                  if(messageText.getText().toString().trim() == null || messageText.getText().toString().trim().length() == 0){
                      Toast.makeText(MessageActivity.this,"请输入内容",Toast.LENGTH_SHORT).show();
                  }else{
                      getIntent.TravelMessageSend("addOneMessage",0,tn_id, UserBean.getUserBean().userId,messageText.getText().toString().trim());
                      getIntent.setGetInterfaceBooleanListener(new GetInterfaceBooleanListener() {
                          @Override
                          public void getBoolean(boolean flag) {
                              Toast.makeText(MessageActivity.this,"成功"+flag,Toast.LENGTH_SHORT).show();
                              messageText.setText("");
                          }
                      });
                  }
                break;
        }
    }
}
