package com.weiaibenpao.demo.chislim.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.weiaibenpao.demo.chislim.Interface.GetInterfaceVideoListener;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.adater.DateRemberAdapter;
import com.weiaibenpao.demo.chislim.adater.RecyclerHisAdapter;
import com.weiaibenpao.demo.chislim.bean.SportHistoryResult;
import com.weiaibenpao.demo.chislim.bean.SportHistoryResultBean;
import com.weiaibenpao.demo.chislim.bean.UserBean;
import com.weiaibenpao.demo.chislim.util.ACache;
import com.weiaibenpao.demo.chislim.util.DateUtil;
import com.weiaibenpao.demo.chislim.util.GetIntentData;
import com.weiaibenpao.demo.chislim.util.LatLong_impl;
import com.weiaibenpao.demo.chislim.util.SharedPrefsUtil;
import com.weiaibenpao.demo.chislim.util.WriteReadSharedPrefs;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.AnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInRightAnimationAdapter;

public class MyHistoryActivity extends Activity  implements DateRemberAdapter.OnItemClickListener{
    private static final int MONTH_COUNT = 3;

    RecyclerView hisRecycler;
    RecyclerView hisMonthRecycler;
    ImageView back;
    GetIntentData getIntentData;
    Context context;
    @BindView(R.id.disAllText)
    TextView disAllText;
    private ACache mCache;
    AnimationAdapter animationAdapter ;
    RecyclerHisAdapter recyclerHisAdapter;

    LatLong_impl latLong_impl;
    private ArrayList travelList = new ArrayList();

    @Override
    public void onItemClick(List<SportHistoryResultBean.DataBean.SportRecord> sportRecords) {
        recyclerHisAdapter.refreshData(sportRecords);
    }

    enum Type {
        AlphaIn {
            @Override
            public AnimationAdapter get(Context context, List<SportHistoryResultBean.DataBean.SportRecord> list) {
                RecyclerHisAdapter adapter = new RecyclerHisAdapter(context, list);
                return new AlphaInAnimationAdapter(adapter);
            }
        },
        ScaleIn {
            @Override
            public AnimationAdapter get(Context context, List<SportHistoryResultBean.DataBean.SportRecord> list) {
                RecyclerHisAdapter adapter = new RecyclerHisAdapter(context, list);
                return new ScaleInAnimationAdapter(adapter);
            }
        },
        SlideInBottom {
            @Override
            public AnimationAdapter get(Context context, List<SportHistoryResultBean.DataBean.SportRecord> list) {
                RecyclerHisAdapter adapter = new RecyclerHisAdapter(context, list);
                return new SlideInBottomAnimationAdapter(adapter);
            }
        },
        SlideInLeft {
            @Override
            public AnimationAdapter get(Context context, List<SportHistoryResultBean.DataBean.SportRecord> list) {
                RecyclerHisAdapter adapter = new RecyclerHisAdapter(context, list);
                return new SlideInLeftAnimationAdapter(adapter);
            }
        },
        SlideInRight {
            @Override
            public AnimationAdapter get(Context context, List<SportHistoryResultBean.DataBean.SportRecord> list) {
                RecyclerHisAdapter adapter = new RecyclerHisAdapter(context, list);
                return new SlideInRightAnimationAdapter(adapter);
            }
        };

        public abstract AnimationAdapter get(Context context, List<SportHistoryResultBean.DataBean.SportRecord> list);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_history);
        StatusBarUtil.setColor(this , getResources().getColor(R.color.ku_bg ) , 0);
        ButterKnife.bind(this);
        getIntentData = new GetIntentData();
        context = getApplicationContext();
        mCache = ACache.get(context);

        hisRecycler = (RecyclerView) findViewById(R.id.hisRecycler);
        hisRecycler.setLayoutManager(new LinearLayoutManager(this));

        latLong_impl = new LatLong_impl(context);


        initData();
        AdapterListener();
        initView();
        // initRecyclerView();
        getAllDis();
    }

    //获取总里程
    public void getAllDis(){
//        disAllText.setText(mCache.getAsString("alldis") + "KM");
        disAllText.setText(SharedPrefsUtil.getValue(this, WriteReadSharedPrefs.PREFS_NAME,WriteReadSharedPrefs.TOTAL_DISTANCE,"0") + "KM");
    }

    private void initRecyclerView(List<SportHistoryResultBean.DataBean.MonthRecord> dateList) {
        hisMonthRecycler = (RecyclerView) findViewById(R.id.hisMonthRecycler);
        DateRemberAdapter dateRemberAdapter = new DateRemberAdapter(context, dateList);
        dateRemberAdapter.setOnItemClickListener(this);
        hisMonthRecycler.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
        hisMonthRecycler.setAdapter(dateRemberAdapter);

        /*DateRemberAdapter.registerOnItemClickListener(new DateRemberAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, ArrayList monthList, HashMap<String, String> hashMap) {

                Intent intent = new Intent(MyHistoryActivity.this, CumulativeActivity.class);

                SerializableHashMap myMap = new SerializableHashMap();
                myMap.setMap(hashMap);//将hashmap数据添加到封装的myMap中
                Bundle bundle = new Bundle();
                bundle.putSerializable("map", myMap);


                intent.putExtras(bundle);
                startActivity(intent);
            }
        });*/
    }

    public void initCatch() {
        //获取缓存数据
        final SportHistoryResult sportHistory = (SportHistoryResult) mCache.getAsObject("sportHistory");
        if (sportHistory != null) {
            initRecyclerView((ArrayList) sportHistory.getEveryDaySport());
           /* mAdapter1 = new RecyclerHisAdapter(context, (ArrayList) sportHistory.getEveryDaySport());
            hisRecycler.setAdapter(mAdapter1);*/
            animationAdapter = Type.values()[1].get(MyHistoryActivity.this, (ArrayList) sportHistory.getEveryDaySport());
            animationAdapter.setFirstOnly(false);
            animationAdapter.setDuration(500);
            animationAdapter.setInterpolator(new OvershootInterpolator(.5f));
            hisRecycler.setAdapter(animationAdapter);
        }
    }

    public void initData() {
        getIntentData.GetSportHsitoryData(UserBean.getUserBean().userId,MONTH_COUNT);
        getIntentData.setGetIntentDataListener(new GetInterfaceVideoListener() {
            @Override
            public void getDateList(ArrayList dateList) {
                if (dateList.size() > 0) {
                        initRecyclerView((List<SportHistoryResultBean.DataBean.MonthRecord>) dateList);
//                        animationAdapter = Type.values()[1].get(MyHistoryActivity.this, ((List<SportHistoryResultBean.DataBean.MonthRecord>) dateList).get(0).getSportHistoryList());//get(0)默认显示当前月份的记录
//                        animationAdapter.setFirstOnly(false);
//                        animationAdapter.setDuration(500);
//                        animationAdapter.setInterpolator(new OvershootInterpolator(.5f));
                        recyclerHisAdapter = new RecyclerHisAdapter(MyHistoryActivity.this,((List<SportHistoryResultBean.DataBean.MonthRecord>) dateList).get(0).getSportHistoryList());
                        hisRecycler.setAdapter(recyclerHisAdapter);
                }
            }
        });
    }


    public void AdapterListener() {
        RecyclerHisAdapter.setOnItemClickListener(new RecyclerHisAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, List<SportHistoryResultBean.DataBean.SportRecord> arrayList) {
                // Intent intent = new Intent(MyHistoryActivity.this,ShowMapActivity.class);
                Intent intent = new Intent(MyHistoryActivity.this, SportRecordMixedActivity.class);
                intent.putExtra("sportTime", ( arrayList.get(position)).getTotalTime());//运动所用时间
                intent.putExtra("calories", ( arrayList.get(position)).getCalories());//卡楼里
                intent.putExtra("distance", ( arrayList.get(position)).getTotalDistance());//运动距离
                intent.putExtra("dayTime",  DateUtil.getTimeYMD(( arrayList.get(position)).getSportDate()));//运动时间
                intent.putExtra("sportSped", ( arrayList.get(position)).getStepFrequency());//运动速度
                intent.putExtra("sportStep", ( arrayList.get(position)).getTotalStep());//运动步数
                intent.putExtra("sportImg", (arrayList.get(position)).getSportImgUrl());//运动图片url

                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position, List<SportHistoryResultBean.DataBean.SportRecord> arrayList) {

            }

        });
    }

    public void initView() {
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // adapter.registerOnItemClickListener(null);
    }
}
