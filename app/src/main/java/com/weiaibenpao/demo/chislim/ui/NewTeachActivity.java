package com.weiaibenpao.demo.chislim.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.gjiazhe.scrollparallaximageview.parallaxstyle.VerticalMovingStyle;
import com.jaeger.library.StatusBarUtil;
import com.weiaibenpao.demo.chislim.Interface.GetInterfaceVideoListener;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.adater.TeachListAdapter2;
import com.weiaibenpao.demo.chislim.bean.NewTeachResult;
import com.weiaibenpao.demo.chislim.util.ACache;
import com.weiaibenpao.demo.chislim.util.GetIntentData;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewTeachActivity extends Activity implements PullLoadMoreRecyclerView.PullLoadMoreListener {


    @BindView(R.id.myBar)
    View myBar;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.pullLoadMoreRecyclerView)
    PullLoadMoreRecyclerView pullLoadMoreRecyclerView;
    @BindView(R.id.rootActivity)
    RelativeLayout rootActivity;
    private ACache mCache;
    ArrayList bunnerList;

    GetIntentData intentData;
    Context context;
    int pos;

    TeachListAdapter2 mAdapter;
    private RecyclerView mRecyclerView;
    int num;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_teach);
        StatusBarUtil.setColor(this , getResources().getColor(R.color.ku_bg ) , 0);
        ButterKnife.bind(this);
        intentData = new GetIntentData();
        context = this;
        mCache = ACache.get(context);
        //获取mRecyclerView对象
        mRecyclerView = pullLoadMoreRecyclerView.getRecyclerView();
        mRecyclerView.setVerticalScrollBarEnabled(true);
        //设置下拉刷新是否可见
        //mPullLoadMoreRecyclerView.setRefreshing(true);
        //设置是否可以下拉刷新
        //mPullLoadMoreRecyclerView.setPullRefreshEnable(true);
        //设置是否可以上拉刷新
        //mPullLoadMoreRecyclerView.setPushRefreshEnable(false);
        //显示下拉刷新
        pullLoadMoreRecyclerView.setRefreshing(true);
        //设置上拉刷新文字
        pullLoadMoreRecyclerView.setFooterViewText("正在加载更多");
        //设置上拉刷新文字颜色
        //设置加载更多背景色
        //mPullLoadMoreRecyclerView.setFooterViewBackgroundColor(R.color.colorBackground);
        pullLoadMoreRecyclerView.setLinearLayout();

        pullLoadMoreRecyclerView.setOnPullLoadMoreListener(this);
        mAdapter = new TeachListAdapter2(this,new VerticalMovingStyle());

        pullLoadMoreRecyclerView.setAdapter(mAdapter);

        Intent intent = getIntent();
        initClassCatch(intent.getIntExtra("position", 1));
        num = intent.getIntExtra("position", 1);
        clickStart(num);

        // initData();


        bunnerList = new ArrayList();



        initTeach();
        // initGetData();


    }


    private void getData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        bindData(num);
                        pullLoadMoreRecyclerView.setPullLoadMoreCompleted();
                    }
                });

            }
        }, 1000);

    }


    private void clickStart( int n){
        bindData(n);
        pullLoadMoreRecyclerView.setPullLoadMoreCompleted();

    }


    private void bindData(int n) {
        intentData.getAllTeachTab2(context, "getAllTeachTab2", n);

        intentData.setGetIntentDataListener(new GetInterfaceVideoListener() {

            @Override
            public void getDateList(ArrayList dateList) {

                mAdapter.addAllData(dateList);

            }
        });

    }

  /* public void initView(final int n) {
        intentData.getAllTeachTab2(context, "getAllTeachTab2", n);

       *//* mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(mLayoutManager);*//*
    }*/

    //获取不同种类缓存数据
    public void initClassCatch(int tab2) {
        //获取缓存数据
        NewTeachResult newTeachResult = (NewTeachResult) mCache.getAsObject("newTeachResult" + tab2);
        if (newTeachResult != null && mAdapter == null) {
            mAdapter = new TeachListAdapter2(context, new VerticalMovingStyle());
            mRecyclerView.setAdapter(mAdapter);
        } else if (newTeachResult != null && mAdapter != null) {
            mAdapter.changeData((ArrayList) newTeachResult.getNewTeachBean());
        }
    }

   /* //请求网络返回数据
    public void initGetData() {
        intentData.setGetIntentDataListener(new GetInterfaceVideoListener() {
            @Override
            public void getDateList(ArrayList dateList) {

                if (num == 1 && dateList.size() > 0) {
                    if (mAdapter == null) {
                        mAdapter = new TeachListAdapter2(context, new VerticalMovingStyle());
                        mRecyclerView.setAdapter(mAdapter);

                    } else {
                        mAdapter.changeData(dateList);
                    }

                } else if (num > 1 && dateList.size() > 0) {


                } else if (num > 6 && dateList.size() == 0) {
                    Toast.makeText(context, "对不起，新数据都加载完了!", Toast.LENGTH_SHORT).show();
                    num = 6;
                } else if (num >= 1 && num <= 6 && dateList.size() == 0) {
                    Toast.makeText(context, "请检查您的网络!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }*/

    /**
     * 内容布局
     */
    public void initTeach() {
        TeachListAdapter2.setOnItemClickListener(new TeachListAdapter2.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, ArrayList list) {
                Intent intent = new Intent(NewTeachActivity.this, NewTeachPlanActivity.class);
                intent.putExtra("picture", ((NewTeachResult.NewTeachBeanBean) list.get(position)).getTeach_image());
                intent.putExtra("teachid", ((NewTeachResult.NewTeachBeanBean) list.get(position)).getTeach_id());
                intent.putExtra("teacName", ((NewTeachResult.NewTeachBeanBean) list.get(position)).getTeachName());
                intent.putExtra("teacUsehad", ((NewTeachResult.NewTeachBeanBean) list.get(position)).getTeach_userhad());
                intent.putExtra("teacText", ((NewTeachResult.NewTeachBeanBean) list.get(position)).getTeach_text());
                intent.putExtra("textWord", ((NewTeachResult.NewTeachBeanBean) list.get(position)).getTeach_word());

                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position, ArrayList list) {

            }
        });
    }

    @OnClick({R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter.setOnItemClickListener(null);

    }

    @Override
    public void onRefresh() {
        setRefresh();
        getData();
    }

    @Override
    public void onLoadMore() {
        num = num + 1;
        if (num <= 6) {
            getData();
        } else {
            /*num = 6;
            initData();*/
            pullLoadMoreRecyclerView.setPullLoadMoreCompleted();
            Snackbar.make(rootActivity,"对不起，没有更新的数据了...", Snackbar.LENGTH_SHORT).show();
        }

    }

    private void setRefresh() {
        mAdapter.clearData();
        num = 1;
    }
}
