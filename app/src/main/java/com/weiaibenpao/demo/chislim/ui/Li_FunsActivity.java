package com.weiaibenpao.demo.chislim.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.adater.RecyclerUserFunsAdapter;
import com.weiaibenpao.demo.chislim.bean.Li_funsResult;
import com.weiaibenpao.demo.chislim.bean.UserBean;
import com.weiaibenpao.demo.chislim.model.MyModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Li_FunsActivity extends AppCompatActivity {
    public final static String FANS="fans";
    RecyclerUserFunsAdapter adapter;
    Context context;
    RecyclerView recyclerView;
    ImageView backImg;

    Intent intent;
    int tab = 0;
    private int num=0;
    TextView viewTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_li__funs);
        StatusBarUtil.setColor(this , getResources().getColor(R.color.ku_bg ) , 0);
        context = getApplicationContext();
        viewTitle = (TextView) findViewById(R.id.viewTitle);
        backImg = (ImageView) findViewById(R.id.backImg);
        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        recyclerView = (RecyclerView) findViewById(R.id.funsRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,false));

        intent = getIntent();
        tab = intent.getIntExtra(FANS,0);
        //getDate();
        initView();

        if(tab == 0){
            viewTitle.setText("关注");
        }else {
            viewTitle.setText("粉丝");
        }
    }

    //获取数据
    public void getDate(){
        Call<Li_funsResult> call = MyModel.getModel().getService().getUserFuns(UserBean.getUserBean().userId,tab,num+"","20");

        call.enqueue(new Callback<Li_funsResult>() {
            @Override
            public void onResponse(Call<Li_funsResult> call, Response<Li_funsResult> response) {
                if (response.isSuccessful()) {
                    Li_funsResult result = response.body();

                    if (result.getCode() == 0) {
                        adapter = new RecyclerUserFunsAdapter(context, (ArrayList) result.getData().getList());
                        recyclerView.setAdapter(adapter);

                    }else{

                    }
                }
            }

            @Override
            public void onFailure(Call<Li_funsResult> call, Throwable t) {
                // Toast.makeText(context,"教程获取失败",Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void initView(){
        adapter.setOnItemClickListener(new RecyclerUserFunsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int id) {
                Toast.makeText(context,"教程获取失败",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int id) {

            }
        });
    }
}
