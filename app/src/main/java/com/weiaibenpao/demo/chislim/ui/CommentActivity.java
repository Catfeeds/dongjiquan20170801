package com.weiaibenpao.demo.chislim.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.weiaibenpao.demo.chislim.Interface.GetInterfaceVideoListener;
import com.weiaibenpao.demo.chislim.Interface.GetObjectListener;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.adater.RecyclerNotesMessageAdapter;
import com.weiaibenpao.demo.chislim.bean.UserBean;
import com.weiaibenpao.demo.chislim.util.GetIntentData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CommentActivity extends AppCompatActivity {

    GetIntentData getIntentData;
    Context context;
    int humorId;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.hummerMessage)
    RecyclerView hummerMessage;

    RecyclerNotesMessageAdapter adapter;
    @BindView(R.id.hummerText)
    EditText hummerText;
    @BindView(R.id.hummerSend)
    TextView hummerSend;

    GetIntentData getIntentComm;

    String themeTitleStr;
    int page;

    /**
     * 发表评论
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        StatusBarUtil.setColor(this , getResources().getColor(R.color.ku_bg ) , 0);
        ButterKnife.bind(this);

        context = getApplicationContext();

        Intent intent = getIntent();
        humorId = intent.getIntExtra("comment", 0);
        themeTitleStr = intent.getStringExtra("themeTitleStr");
        page = intent.getIntExtra("page",0);
        getIntentComm = new GetIntentData();

        initView();
        initData();
    }

    public void initView() {
        hummerMessage.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false));
    }

    public void initData() {
        getIntentData = new GetIntentData();
        getIntentData.getComment(context, humorId);
        getIntentData.setGetIntentDataListener(new GetInterfaceVideoListener() {
            @Override
            public void getDateList(ArrayList dateList) {
                adapter = new RecyclerNotesMessageAdapter(context, dateList);
                hummerMessage.setAdapter(adapter);
            }
        });
    }

    @OnClick({R.id.back, R.id.hummerSend})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                Intent intent = new Intent();
                intent.putExtra("themeTitleStr",themeTitleStr);
                intent.putExtra("page",page);
                setResult(0,intent);
                finish();
                break;
            case R.id.hummerSend:
                if(hummerText.getText().toString().trim() == null || hummerText.getText().toString().trim().length() == 0){
                    Toast.makeText(CommentActivity.this,"请输入内容",Toast.LENGTH_SHORT).show();
                }else{
                    getIntentComm.setComment(context,UserBean.getUserBean().userId,humorId,hummerText.getText().toString().trim());
                    getIntentComm.setGetObjectListener(new GetObjectListener() {
                        @Override
                        public void getObject(Object object) {
                            hummerText.setText("");
                            getIntentData.getComment(context, humorId);
                        }
                    });
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent();
        intent.putExtra("themeTitleStr",themeTitleStr);
        intent.putExtra("page",page);
        setResult(0,intent);
    }
}
