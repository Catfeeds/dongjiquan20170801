package com.weiaibenpao.demo.chislim.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.gjiazhe.scrollparallaximageview.parallaxstyle.HorizontalMovingStyle;
import com.squareup.picasso.Picasso;
import com.weiaibenpao.demo.chislim.Interface.GetInterfaceVideoListener;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.adater.MainTalkAdapter;
import com.weiaibenpao.demo.chislim.adater.MainTalkAdapter2;
import com.weiaibenpao.demo.chislim.bean.ThemeResule;
import com.weiaibenpao.demo.chislim.bean.UserBean;
import com.weiaibenpao.demo.chislim.util.ACache;
import com.weiaibenpao.demo.chislim.util.GetIntentData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TalkActivity extends Activity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.myImage)
    ImageView myImage;
    @BindView(R.id.myTitle)
    TextView myTitle;
    @BindView(R.id.mtText)
    TextView mtText;
    @BindView(R.id.talkMessage)
    RecyclerView talkMessage;
    @BindView(R.id.talkMessageMore)
    RecyclerView talkMessageMore;

    Context context;
    ArrayList arrayList;

    int page = 1;

    GetIntentData getIntentData;
    GetIntentData getIntentData2;

    MainTalkAdapter2 mAdapter2;
    MainTalkAdapter mAdapter;
    @BindView(R.id.refresh)
    MaterialRefreshLayout refresh;
    @BindView(R.id.activity_talk1)
    RelativeLayout activityTalk1;

    private ACache mCache;
    private boolean isLoadMore = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_talk);
        ButterKnife.bind(this);

        context = getApplicationContext();

        getIntentData = new GetIntentData();
        getIntentData2 = new GetIntentData();

        mCache = ACache.get(context);

        Intent intent = getIntent();

        Picasso.with(context)
                .load(intent.getStringExtra("talkResult"))
                .placeholder(R.mipmap.zhanwei)
                .error(R.mipmap.zhanwei)
                .into(myImage);

        initCatch();
        initData();
        initRefresh();
    }

    private void initRefresh() {

        refresh.setLoadMore(isLoadMore);//允许上拉更多操作
        refresh.setMaterialRefreshListener(new MaterialRefreshListener() {


            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
                //一般加载数据都是在子线程中，这里用到了handler
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (page == 1) {
                            getIntentData2.getTheme(context, "", UserBean.getUserBean().userId, 1, 5);
                            page++;
                            mAdapter2.notifyDataSetChanged();
                        } else {
                            getIntentData2.getTheme(context, "", UserBean.getUserBean().userId, page-1, 5);
                            mAdapter2.notifyDataSetChanged();
                        }

                        materialRefreshLayout.finishRefresh();
                    }
                }, 1500);
            }


            @Override
            public void onRefreshLoadMore(final MaterialRefreshLayout materialRefreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        isLoadMore = false;
                        if (page == 2) {
                            getIntentData2.getTheme(context, "", UserBean.getUserBean().userId, 2, 5);
                            //通知刷新
                            mAdapter2.notifyDataSetChanged();
                        } else {
                            getIntentData2.getTheme(context, "", UserBean.getUserBean().userId, page+1, 5);
                            //通知刷新
                            mAdapter2.notifyDataSetChanged();
                        }
                        //Toast.makeText(TravelMoreActivity.this, "  亲，这是最新数据哦!", Toast.LENGTH_SHORT).show();

                        Snackbar.make(activityTalk1,"   亲，这是最新数据哦!",Snackbar.LENGTH_SHORT).show();
                        materialRefreshLayout.finishRefreshLoadMore();
                    }
                }, 1500);
            }
        });

    }

    public void initCatch() {
        ThemeResule.DataBean dataBean1 = (ThemeResule.DataBean) mCache.getAsObject("theme");
        if (dataBean1 != null) {
            mAdapter = new MainTalkAdapter(context, (ArrayList) dataBean1.getThemelist(), new HorizontalMovingStyle());                         //话题适配
            talkMessage.setAdapter(mAdapter);
        }
        ThemeResule.DataBean dataBean2 = (ThemeResule.DataBean) mCache.getAsObject("theme");
        if (dataBean2 != null) {
            mAdapter2 = new MainTalkAdapter2(context, (ArrayList) dataBean2.getThemelist(), new HorizontalMovingStyle());                        //话题适配
            talkMessageMore.setAdapter(mAdapter2);
        }
    }

    public void initData() {
        getIntentData.getTheme(context, "", UserBean.getUserBean().userId, page, 5);                                  //获取话题
        getIntentData2.getTheme(context, "", UserBean.getUserBean().userId, page + 1, 5);                               //获取话题

        talkMessage.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));     //设置横向布局
        talkMessageMore.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));       //设置竖向布局

        getIntentData.setGetIntentDataListener(new GetInterfaceVideoListener() {
            @Override
            public void getDateList(ArrayList dateList) {
                if (mAdapter == null && dateList.size() > 0) {
                    mAdapter = new MainTalkAdapter(context, dateList, new HorizontalMovingStyle());                    //话题适配
                    talkMessage.setAdapter(mAdapter);
                } else if (mAdapter != null && dateList.size() > 0) {
                    mAdapter.changeData(dateList);
                } else {
                    Toast.makeText(TalkActivity.this, "暂无更多", Toast.LENGTH_SHORT).show();
                }
            }
        });

        getIntentData2.setGetIntentDataListener(new GetInterfaceVideoListener() {
            @Override
            public void getDateList(ArrayList dateList) {

                if (mAdapter2 == null && dateList.size() > 0) {
                    mAdapter2 = new MainTalkAdapter2(context, dateList, new HorizontalMovingStyle());                    //话题适配
                    talkMessageMore.setAdapter(mAdapter2);
                } else if (mAdapter2 != null && dateList.size() > 0) {
                    mAdapter2.changeData(dateList);
                } else {
                    Toast.makeText(TalkActivity.this, "暂无更多", Toast.LENGTH_SHORT).show();
                }
            }
        });

        MainTalkAdapter.setOnItemClickListener(new MainTalkAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, ArrayList talkList) {
                /*Intent intent = new Intent(context, ThemeActivity.class);
                intent.putExtra("theme", (Parcelable) talkList.get(position));
                startActivity(intent);*/
            }

            @Override
            public void onItemLongClick(View view, int position, ArrayList talkList) {

            }
        });

        MainTalkAdapter2.setOnItemClickListener(new MainTalkAdapter2.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, ArrayList talkList) {
                /*Intent intent = new Intent(context, ThemeActivity.class);
                intent.putExtra("theme", (Parcelable) talkList.get(position));
                startActivity(intent);*/
            }

            @Override
            public void onItemLongClick(View view, int position, ArrayList talkList) {

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
        mAdapter2.setOnItemClickListener(null);
    }
}
