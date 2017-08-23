package com.weiaibenpao.demo.chislim.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.weiaibenpao.demo.chislim.Interface.GetInterfaceVideoListener;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.adater.RecyclerTravelNotesUserItemAdapter2;
import com.weiaibenpao.demo.chislim.bean.TravelNotesUserItemResult;
import com.weiaibenpao.demo.chislim.util.ACache;
import com.weiaibenpao.demo.chislim.util.GetIntentData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.weiaibenpao.demo.chislim.R.id.srl;

public class NotesUserItemActivity extends Activity {

    @BindView(R.id.traNotes)
    RecyclerView traNotes;

    GetIntentData getIntentDataNotes;
    Context context;
    @BindView(R.id.back)
    ImageView back;

    RecyclerTravelNotesUserItemAdapter2 mAdapter1;
    int lastVisibleItem;
    LinearLayoutManager mLayoutManager;
    @BindView(srl)
    SwipeRefreshLayout swpr;

    int tn_id = 1;
    boolean flagTop = false;
    boolean flagLast = false;

    private ACache mCache;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_user_item);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this , getResources().getColor(R.color.ku_bg ) , 0);
        context = this;
        mCache = ACache.get(context);
        tn_id = getIntent().getIntExtra("position",1);
        Log.i("游记id",tn_id+"-------------");
        initView();
        initCatch();
        initData( );


        //srl下拉刷新只需要设置setOnRefreshListener就行了
        /*swpr.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if(flagTop = false){
                            //num++;
                        }
                        flagTop = true;
                        initData( );
                        //这里主要就是获取刷新的数据，传给adapter

                    }
                }, 2000);
            }
        });*/
        //rv要使用上啦加载更多。需要在activity中像下面这样做就行了。
        /*traNotes.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == mAdapter1.getItemCount()) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                           if(flagLast = false){
                               //num++;
                           }
                            flagLast = true;
                            initData( );
                        }
                    }, 1500);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
            }
        });*/
    }

    public void initView() {

        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(OrientationHelper.VERTICAL);
        traNotes.setLayoutManager(mLayoutManager);
        //  traNotes.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
    }

    public void initCatch(){
        //获取缓存数据
        TravelNotesUserItemResult notesResult = (TravelNotesUserItemResult) mCache.getAsObject("travelNotesUserItemResult" + tn_id);
        if(notesResult != null){
            if(mAdapter1 == null){
                mAdapter1 = new RecyclerTravelNotesUserItemAdapter2(context, (ArrayList) notesResult.getTravel_notes_item());
                traNotes.setAdapter(mAdapter1);
            }else{
                mAdapter1 = new RecyclerTravelNotesUserItemAdapter2(context, (ArrayList) notesResult.getTravel_notes_item());
                traNotes.setAdapter(mAdapter1);
            }
        }
    }

    public void initData() {
        getIntentDataNotes = new GetIntentData();

        getIntentDataNotes.getNotesUserItemMessage(context, "getAllNotesItem", tn_id);
        getIntentDataNotes.setGetIntentDataListener(new GetInterfaceVideoListener() {
            @Override
            public void getDateList(ArrayList dateList) {
                if(dateList.size() > 0){
                    if(mAdapter1 == null){
                        mAdapter1 = new RecyclerTravelNotesUserItemAdapter2(context, dateList);
                        traNotes.setAdapter(mAdapter1);
                        swpr.setRefreshing(false);
                    }else{
                        mAdapter1.changeData(dateList);
                        swpr.setRefreshing(false);
                        //mAdapter1.notifyDataSetChanged();
                    }
                }else if(dateList.size() == 0){
                    Toast.makeText(context,"检查网络连接...",Toast.LENGTH_SHORT).show();
                    flagTop = false;
                    flagLast = false;
                    //num--;
                    swpr.setRefreshing(false);
                }

                /*if(num == 1 && dateList.size() > 0){
                    if(mAdapter1 == null){
                        mAdapter1 = new RecyclerTravelNotesUserItemAdapter2(context, dateList);
                        traNotes.setAdapter(mAdapter1);
                    }else{
                        mAdapter1.changeData(dateList);
                        //mAdapter1.notifyDataSetChanged();
                    }
                }else if( num > 1 && dateList.size() > 0){
                    if(flagTop){
                        //这里主要就是获取刷新的数据，传给adapter
                        mAdapter1.refreshItem(dateList);
                        swpr.setRefreshing(false);
                        flagTop = false;
                    }
                    if(flagLast){
                        mAdapter1.addMoreData(dateList);
                        mAdapter1.notifyDataSetChanged();
                        flagLast = false;
                    }
                }else if(dateList.size() == 0){
                    Toast.makeText(context,"检查网络连接...",Toast.LENGTH_SHORT).show();
                    flagTop = false;
                    flagLast = false;
                    //num--;
                    swpr.setRefreshing(false);
                }*/
            }
        });
    }

    @OnClick(R.id.back)
    public void onClick() {
        finish();
    }
}
