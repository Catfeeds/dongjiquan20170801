package com.weiaibenpao.demo.chislim.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.weiaibenpao.demo.chislim.Interface.GetInterfaceStringListener;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.adater.RecyclerSuccessAdapter;
import com.weiaibenpao.demo.chislim.bean.UserBean;
import com.weiaibenpao.demo.chislim.util.GetIntentData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SuccessActivity extends AppCompatActivity {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.getkm)
    TextView getkm;
    @BindView(R.id.mykm)
    RecyclerView mykm;
    @BindView(R.id.getgrade)
    TextView getgrade;
    @BindView(R.id.mygrade)
    RecyclerView mygrade;

    GetIntentData getIntentData;
    @BindView(R.id.getmark)
    TextView getmark;
    @BindView(R.id.mymark)
    RecyclerView mymark;

    private ArrayList disListB;
    private ArrayList disListD;

    private ArrayList gradeListB;
    private ArrayList gradeListD;

    private ArrayList markListB;
    private ArrayList markListD;

    RecyclerSuccessAdapter adapter1;
    RecyclerSuccessAdapter adapter2;
    RecyclerSuccessAdapter adapter3;

    ArrayList disList = new ArrayList();
    ArrayList gradeList = new ArrayList();
    ArrayList markList = new ArrayList();
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);
        ButterKnife.bind(this);

        context = getApplicationContext();

        initRecycleView();
        initLocalData();
        initIntentData();
    }

    public void initRecycleView() {
        mykm.setLayoutManager(new GridLayoutManager(this, 3));              //跑步勋章
        mygrade.setLayoutManager(new GridLayoutManager(this, 3));           //等级勋章
        mymark.setLayoutManager(new GridLayoutManager(this, 3));            //签到勋章
    }

    public void initLocalData() {
        disListB = new ArrayList();

        disListB.add("http://ofplk6att.bkt.clouddn.com/km10b.png");
        disListB.add("http://ofplk6att.bkt.clouddn.com/km20b.png");
        disListB.add("http://ofplk6att.bkt.clouddn.com/km30b.png");
        disListB.add("http://ofplk6att.bkt.clouddn.com/km50b.png");
        disListB.add("http://ofplk6att.bkt.clouddn.com/km80b.png");
        disListB.add("http://ofplk6att.bkt.clouddn.com/km100b.png");

        disListD = new ArrayList();
        disListD.add("http://ofplk6att.bkt.clouddn.com/km10d.png");
        disListD.add("http://ofplk6att.bkt.clouddn.com/km20d.png");
        disListD.add("http://ofplk6att.bkt.clouddn.com/km30d.png");
        disListD.add("http://ofplk6att.bkt.clouddn.com/km50d.png");
        disListD.add("http://ofplk6att.bkt.clouddn.com/km80d.png");
        disListD.add("http://ofplk6att.bkt.clouddn.com/km100d.png");

        gradeListB = new ArrayList();
        gradeListB.add("http://ofplk6att.bkt.clouddn.com/grade1b.png");
        gradeListB.add("http://ofplk6att.bkt.clouddn.com/grade2b.png");
        gradeListB.add("http://ofplk6att.bkt.clouddn.com/grade3b.png");
        gradeListB.add("http://ofplk6att.bkt.clouddn.com/grade4b.png");
        gradeListB.add("http://ofplk6att.bkt.clouddn.com/grade5b.png");
        gradeListB.add("http://ofplk6att.bkt.clouddn.com/grade6b.png");
        gradeListB.add("http://ofplk6att.bkt.clouddn.com/grade7b.png");
        gradeListB.add("http://ofplk6att.bkt.clouddn.com/grade8b.png");
        gradeListB.add("http://ofplk6att.bkt.clouddn.com/grade9b.png");
        gradeListB.add("http://ofplk6att.bkt.clouddn.com/grade10b.png");

        gradeListD = new ArrayList();
        gradeListD.add("http://ofplk6att.bkt.clouddn.com/grade1d.png");
        gradeListD.add("http://ofplk6att.bkt.clouddn.com/grade2d.png");
        gradeListD.add("http://ofplk6att.bkt.clouddn.com/grade3d.png");
        gradeListD.add("http://ofplk6att.bkt.clouddn.com/grade4d.png");
        gradeListD.add("http://ofplk6att.bkt.clouddn.com/grade5d.png");
        gradeListD.add("http://ofplk6att.bkt.clouddn.com/grade6d.png");
        gradeListD.add("http://ofplk6att.bkt.clouddn.com/grade7d.png");
        gradeListD.add("http://ofplk6att.bkt.clouddn.com/grade8d.png");
        gradeListD.add("http://ofplk6att.bkt.clouddn.com/grade9d.png");
        gradeListD.add("http://ofplk6att.bkt.clouddn.com/grade10d.png");

        markListB = new ArrayList();
        markListB.add("http://ofplk6att.bkt.clouddn.com/mark500b.png");
        markListB.add("http://ofplk6att.bkt.clouddn.com/mark1000b.png");
        markListB.add("http://ofplk6att.bkt.clouddn.com/mark3000b.png");
        markListB.add("http://ofplk6att.bkt.clouddn.com/mark10000b.png");

        markListD = new ArrayList();
        markListD.add("http://ofplk6att.bkt.clouddn.com/mark500d.png");
        markListD.add("http://ofplk6att.bkt.clouddn.com/mark1000d.png");
        markListD.add("http://ofplk6att.bkt.clouddn.com/mark3000d.png");
        markListD.add("http://ofplk6att.bkt.clouddn.com/mark10000d.png");
    }

    public void initIntentData() {
        getIntentData = new GetIntentData();
        getIntentData.getSuccess(UserBean.getUserBean().userId);
        getIntentData.setGetStringListener(new GetInterfaceStringListener() {
            @Override
            public void getDateString(String str) {
                String[] temp1 = str.split("#");
                int n = temp1.length;
                for (int i = 0; i < 3; i++) {
                    String[] temp2 = temp1[i].split(",");
                    if (i == 0) {
                        getkm.setText("（已获得" + (temp2.length-1) + "）");
                        disList.addAll(disListB.subList(0,temp2.length - 1));
                        disList.addAll(disListD.subList(temp2.length - 1,disListD.size()));
                        adapter1 = new RecyclerSuccessAdapter(context, disList, 8);
                        mykm.setAdapter(adapter1);
                    }
                    if (i == 1) {
                        getgrade.setText("（已获得" + (temp2.length-1) + "）");
                        gradeList.addAll(gradeListB.subList(0,temp2.length - 1));
                        gradeList.addAll(gradeListD.subList(temp2.length - 1,gradeListD.size()));
                        adapter2 = new RecyclerSuccessAdapter(context, gradeList, 8);
                        mygrade.setAdapter(adapter2);
                    }
                    if (i == 2) {
                        getkm.setText("（已获得" + (temp2.length-1) + "）");
                        markList.addAll(markListB.subList(0,temp2.length - 1));
                        markList.addAll(markListD.subList(temp2.length - 1,markListD.size()));
                        adapter3 = new RecyclerSuccessAdapter(context, markList, 8);
                        mymark.setAdapter(adapter3);
                    }
                }
            }
        });

        RecyclerSuccessAdapter.setOnItemClickListener(new RecyclerSuccessAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, ArrayList list) {
                Intent intent = new Intent(SuccessActivity.this,MedalInfoActivity.class);
                intent.putExtra("medal",list.get(position).toString());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position, ArrayList list) {

            }
        });
    }


    @OnClick(R.id.back)
    public void onClick() {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        adapter1.setOnItemClickListener(null);
        adapter2.setOnItemClickListener(null);
        adapter3.setOnItemClickListener(null);
    }
}
