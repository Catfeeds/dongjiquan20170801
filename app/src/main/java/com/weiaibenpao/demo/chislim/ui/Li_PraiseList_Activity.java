package com.weiaibenpao.demo.chislim.ui;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.weiaibenpao.demo.chislim.Interface.GetObjectListener;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.adater.Li_PraistListAdapter;
import com.weiaibenpao.demo.chislim.bean.Li_Praise_Result;
import com.weiaibenpao.demo.chislim.util.GetIntentData;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Li_PraiseList_Activity extends AppCompatActivity {

    public static int PAGE_NUM = 0;
    @BindView(R.id.myPrsiseList)
    PullLoadMoreRecyclerView myPrsiseList;

    GetIntentData getIntentData;
    Context context;
    int page = PAGE_NUM;
    int size = 50;

    int humorId;

    Li_PraistListAdapter adapter;
    @BindView(R.id.back)
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_li__praiselist);
        ButterKnife.bind(this);
        context = getApplicationContext();
        getIntentData = new GetIntentData();


        initView();
        initData();
        initAction();
    }

    public void initView() {

        adapter = new Li_PraistListAdapter(context);
        myPrsiseList.setGridLayout(5);
        myPrsiseList.setAdapter(adapter);

    }

    public void initData() {

       humorId = getIntent().getIntExtra("humorId",0);
        // humorId = 130;
        getInitentData(0, humorId, page, size);

    }

    public void initAction() {
        myPrsiseList.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                getInitentData(0, humorId, PAGE_NUM, size);
            }

            @Override
            public void onLoadMore() {
                page++;
                getInitentData(1, humorId, page, size);
            }
        });

        adapter.setOnItemClickListener(new Li_PraistListAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int userId) {
                //TODO 点击用户头像的回调

            }
        });
    }

    public void getInitentData(final int state, int humorId, int page, int size) {
        getIntentData.getPraiseList(context, humorId, String.valueOf(page), String.valueOf(size));

        getIntentData.setGetObjectListener(new GetObjectListener() {
            @Override
            public void getObject(Object object) {
                Li_Praise_Result.DataBean dataBean = (Li_Praise_Result.DataBean) object;

                //0 为刷新   1为加载
                if (state == 0) {
                    adapter.refreshData((ArrayList) dataBean.getPraiselist());
                } else {
                    adapter.loadMoreData((ArrayList) dataBean.getPraiselist());
                }

                myPrsiseList.setIsLoadMore(false);

                myPrsiseList.setIsRefresh(false);
                myPrsiseList.setRefreshing(false);
            }
        });
    }


    @OnClick(R.id.back)
    public void onClick() {
        finish();
    }
}
