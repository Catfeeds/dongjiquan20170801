package com.weiaibenpao.demo.chislim.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.weiaibenpao.demo.chislim.Interface.GetInterfaceVideoListener;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.adater.RecyclerTravelInfoAdapter;
import com.weiaibenpao.demo.chislim.bean.TravelInfoResult;
import com.weiaibenpao.demo.chislim.bean.TravelResult;
import com.weiaibenpao.demo.chislim.util.ACache;
import com.weiaibenpao.demo.chislim.util.Default;
import com.weiaibenpao.demo.chislim.util.GetIntentData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.squareup.picasso.MemoryPolicy.NO_CACHE;
import static com.squareup.picasso.MemoryPolicy.NO_STORE;

public class TravelInfoActivity extends Activity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.myScrollView)
    ScrollView myScrollView;
    @BindView(R.id.myBar)
    View myBar;
    @BindView(R.id.traInfo)
    RecyclerView traInfo;
    @BindView(R.id.textName)
    TextView textName;
    @BindView(R.id.textWord)
    TextView textWord;
    @BindView(R.id.activity_travel_info)
    LinearLayout activityTravelInfo;

    GetIntentData getIntentData;
    ArrayList travelList;

    Context context;

    TravelResult.TravelBean tra;

    RecyclerView recyclerView;
    @BindView(R.id.topPic)
    ImageView topPic;
    @BindView(R.id.selectMore)
    TextView selectMore;
    @BindView(R.id.traMap)
    ImageView traMap;
    @BindView(R.id.backTravel)
    TextView backTravel;
    @BindView(R.id.textIntro)
    TextView textIntro;

    private ACache mCache;
    RecyclerTravelInfoAdapter mAdapter;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_info);
        ButterKnife.bind(this);
        context = this.getApplicationContext();

        mCache = ACache.get(context);

       /* myScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View view, int i, int i1, int i2, int i3) {
                Log.i("ScrollView", i + " -- " + i1 + " -- " + i2 + " -- " + i3);
                Log.i("ScrollView", " -- " + i1 + " --============== ");
                myBar.setAlpha((float) i1 / 425);
            }
        });*/

        Intent intent = getIntent();
        tra = intent.getParcelableExtra("travel");

        initRecyclerView();
        initData(tra.getT_id());

        Log.i("t_id",tra.getT_id()+" ");
        initView();
    }

    public void initRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.traInfo);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
        //点击事件
        RecyclerTravelInfoAdapter.setOnItemClickListener(new RecyclerTravelInfoAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, String[] temp) {
                Log.i("旅游", temp.toString() + "------------" + position);
                Intent intent = new Intent(TravelInfoActivity.this, TravelPictureActivity.class);
                intent.putExtra("picture", temp);
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position, String[] temp) {

            }
        });
    }

    public void initData(int n) {
        getIntentData = new GetIntentData();

        //获取缓存数据
        TravelInfoResult notesResult = (TravelInfoResult) mCache.getAsObject("travelInfoResult" + n);
        if(notesResult != null){
            mAdapter = new RecyclerTravelInfoAdapter(context, (ArrayList) notesResult.getTravel_info(), 8);
            recyclerView.setAdapter(mAdapter);
        }

        getIntentData.getTravelInfo(context, "getAllTravelInfo", n);
        getIntentData.setGetIntentDataListener(new GetInterfaceVideoListener() {
            @Override
            public void getDateList(ArrayList dateList) {
                Log.i("子景点", dateList.toString());

                if(mAdapter == null){
                    mAdapter = new RecyclerTravelInfoAdapter(context, dateList, 8);
                    recyclerView.setAdapter(mAdapter);
                }else{
                    mAdapter.addMoreData(dateList);
                    mAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    public void initView() {
        Picasso.with(context)
                .load(Default.urlPic + tra.getT_image())
                .placeholder(R.mipmap.zhanwei)
                .error(R.mipmap.zhanwei)
                .memoryPolicy(NO_CACHE, NO_STORE)
                .config(Bitmap.Config.RGB_565)
                .into(topPic);

        Picasso.with(context)
                .load(R.mipmap.zhanwei)
                .placeholder(R.mipmap.zhanwei)
                .error(R.mipmap.zhanwei)
                .memoryPolicy(NO_CACHE)
                .config(Bitmap.Config.RGB_565)
                .into(traMap);
        textName.setText(tra.getT_name());
        textWord.setText(tra.getT_text());
    }

    @OnClick({R.id.topPic, R.id.selectMore, R.id.traMap, R.id.backTravel,R.id.textIntro,R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.topPic:
                String[] temp1 = new String[1];
                temp1[0] = tra.getT_image();
                Intent intent = new Intent(TravelInfoActivity.this, TravelPictureActivity.class);
                intent.putExtra("picture", temp1);
                startActivity(intent);
                break;
            case R.id.selectMore:
                break;
            case R.id.traMap:
                break;
            case R.id.backTravel:
                finish();
                break;
            case R.id.textIntro:
                break;
            case R.id.back:
                finish();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
