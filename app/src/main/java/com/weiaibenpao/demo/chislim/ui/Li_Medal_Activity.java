package com.weiaibenpao.demo.chislim.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.bean.MyMedal;
import com.weiaibenpao.demo.chislim.bean.UserBean;
import com.weiaibenpao.demo.chislim.hurui.adapte.MedalsAdapter;
import com.weiaibenpao.demo.chislim.model.MyModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Li_Medal_Activity extends Activity{

    ImageView backImg;
    TextView medalNum;
    private Context context=this;
    private RecyclerView grid_medal;
    private MedalsAdapter medalsAdapter;
    List<MyMedal> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_li__medal_);
        StatusBarUtil.setColor(this , getResources().getColor(R.color.ku_bg ) , 0);




        initView();
        initAdapter();

    }


    public void initView(){
        medalNum = (TextView) findViewById(R.id.medalNum);
        grid_medal = ((RecyclerView) findViewById(R.id.medal_grid));
        backImg = (ImageView) findViewById(R.id.backImg);



    }

    @Override
    protected void onStart() {
        super.onStart();
        initMedal();

        backImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void initAdapter() {
        medalsAdapter = new MedalsAdapter(context);
        grid_medal.setLayoutManager(new GridLayoutManager(context,2));
        grid_medal.setAdapter(medalsAdapter);
    }

    /**
     * 请求勋章
     */
    private void initMedal() {
        MyModel.getModel().getService().getMedals(UserBean.getUserBean().userId).enqueue(new Callback<MyMedal>() {
            @Override
            public void onResponse(Call<MyMedal> call, Response<MyMedal> response) {
                if (response.body().getCode()==0){
                    List<MyMedal.DataBean.BadgeListBean> badgeList = response.body().getData().getBadgeList();
                    medalNum.setText("（已获得"+response.body().getData().getTotalbage()+"枚）");
                        medalsAdapter.refreshData(badgeList,response.body().getData().getTotalbage());

                }else{


                }

            }

            @Override
            public void onFailure(Call<MyMedal> call, Throwable t) {

            }
        });

    }

}
