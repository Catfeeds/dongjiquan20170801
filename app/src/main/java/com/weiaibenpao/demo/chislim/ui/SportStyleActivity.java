package com.weiaibenpao.demo.chislim.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.adater.RecyclerSportSpiceDisAdapter;
import com.weiaibenpao.demo.chislim.bean.SpiceSportRemberBean;
import com.weiaibenpao.demo.chislim.bean.SportDisSpiceBean;
import com.weiaibenpao.demo.chislim.util.ACache;
import com.weiaibenpao.demo.chislim.util.Default;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.AnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInRightAnimationAdapter;

import static com.squareup.picasso.MemoryPolicy.NO_CACHE;
import static com.squareup.picasso.MemoryPolicy.NO_STORE;

public class SportStyleActivity extends Activity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.traInfo)
    RecyclerView traInfo;

    Context context;
    // RecyclerSportSpiceDisAdapter adapter;

    Intent getIntent;
    int page;
    @BindView(R.id.mySet)
    TextView mySet;
    @BindView(R.id.startCity)
    TextView startCity;
    @BindView(R.id.nowDis)
    TextView nowDis;
    @BindView(R.id.endCity)
    TextView endCity;
    @BindView(R.id.activity_sport_style)
    RelativeLayout activitySportStyle;
    @BindView(R.id.rember)
    LinearLayout rember;
    @BindView(R.id.myBackground)
    ImageView myBackground;
    @BindView(R.id.myLayout)
    RelativeLayout myLayout;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    //private GoogleApiClient client;

    private ACache mCache;
    SpiceSportRemberBean spiceSportRemberBean;
    AnimationAdapter adapter;

    MyBroadcast myBroadcast;  //广播对象
    boolean flag = false;

    enum Type {
        AlphaIn {
            @Override
            public AnimationAdapter get(Context context, ArrayList list) {
                RecyclerSportSpiceDisAdapter adapter = new RecyclerSportSpiceDisAdapter(context, list);
                return new AlphaInAnimationAdapter(adapter);
            }
        },
        ScaleIn {
            @Override
            public AnimationAdapter get(Context context, ArrayList list) {
                RecyclerSportSpiceDisAdapter adapter = new RecyclerSportSpiceDisAdapter(context, list);
                return new ScaleInAnimationAdapter(adapter);
            }
        },
        SlideInBottom {
            @Override
            public AnimationAdapter get(Context context, ArrayList list) {
                RecyclerSportSpiceDisAdapter adapter = new RecyclerSportSpiceDisAdapter(context, list);
                return new SlideInBottomAnimationAdapter(adapter);
            }
        },
        SlideInLeft {
            @Override
            public AnimationAdapter get(Context context, ArrayList list) {
                RecyclerSportSpiceDisAdapter adapter = new RecyclerSportSpiceDisAdapter(context, list);
                return new SlideInLeftAnimationAdapter(adapter);
            }
        },
        SlideInRight {
            @Override
            public AnimationAdapter get(Context context, ArrayList list) {
                RecyclerSportSpiceDisAdapter adapter = new RecyclerSportSpiceDisAdapter(context, list);
                return new SlideInRightAnimationAdapter(adapter);
            }
        };


        public abstract AnimationAdapter get(Context context, ArrayList list);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport_style);
        ButterKnife.bind(this);
        context = getApplicationContext();
        getIntent = getIntent();
        page = getIntent.getIntExtra("position", 0);
        mCache = ACache.get(context);    //实例化缓存
        initView();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        //client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();

        initCache();

        //配置广播，接收当前是否有跑步正在进行中
        myBroadcast = new MyBroadcast();
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.lijianbao.mapLocationLatLong");
        SportStyleActivity.this.registerReceiver(myBroadcast, filter);
    }

    //获取记录
    public void initCache() {
        spiceSportRemberBean = (SpiceSportRemberBean) mCache.getAsObject("sportSpice");

        if (spiceSportRemberBean != null) {
            myLayout.setVisibility(View.VISIBLE);
            startCity.setText(spiceSportRemberBean.getFistCity());
            nowDis.setText(spiceSportRemberBean.getDisNow() + "");
            endCity.setText(spiceSportRemberBean.getSecondCity());
        }

//绑定数据
        Picasso.with(context)
                .load("http://img.hb.aicdn.com/453c1cefb19fb73566a6e036dc8109f7883113ba16c5b-JicWZg_fw658")
                .placeholder(R.mipmap.zhanwei)
                .error(R.mipmap.zhanwei)
                .config(Bitmap.Config.RGB_565)
                .memoryPolicy(NO_CACHE, NO_STORE)
                .into(myBackground);

        rember.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (page == 0) {
                    Intent intent = new Intent(SportStyleActivity.this, SportHomeDisSpiceActivity.class);
                    intent.putExtra("fistCity", spiceSportRemberBean.getFistCity());
                    intent.putExtra("secondCity", spiceSportRemberBean.getSecondCity());
                    intent.putExtra("position", spiceSportRemberBean.getPosition());
                    intent.putExtra("fromLat", spiceSportRemberBean.getFromLat());
                    intent.putExtra("fromLong", spiceSportRemberBean.getFromLong());
                    intent.putExtra("toLat", spiceSportRemberBean.getToLat());
                    intent.putExtra("toLong", spiceSportRemberBean.getToLong());
                    intent.putExtra("disNow", spiceSportRemberBean.getDisNow());

                    mCache.put("fistCity",spiceSportRemberBean.getFistCity());
                    mCache.put("secondCity",spiceSportRemberBean.getSecondCity());
                    mCache.put("position","0");
                    mCache.put("fromLat",String.valueOf(spiceSportRemberBean.getFromLat()));
                    mCache.put("fromLong",String.valueOf(spiceSportRemberBean.getFromLong()));
                    mCache.put("toLat",String.valueOf(spiceSportRemberBean.getToLat()));
                    mCache.put("toLong",String.valueOf(spiceSportRemberBean.getToLong()));
                    mCache.put("disNow",String.valueOf(spiceSportRemberBean.getDisNow()));
                    startActivity(intent);
                    finish();
                }
                if (page == 1) {
                    Intent intent = new Intent(SportStyleActivity.this, SportOutDisSpiceActivity.class);
                    intent.putExtra("fistCity", spiceSportRemberBean.getFistCity());
                    intent.putExtra("secondCity", spiceSportRemberBean.getSecondCity());
                    intent.putExtra("position", spiceSportRemberBean.getPosition());
                    intent.putExtra("fromLat", spiceSportRemberBean.getFromLat());
                    intent.putExtra("fromLong", spiceSportRemberBean.getFromLong());
                    intent.putExtra("toLat", spiceSportRemberBean.getToLat());
                    intent.putExtra("toLong", spiceSportRemberBean.getToLong());
                    intent.putExtra("disNow", spiceSportRemberBean.getDisNow());

                    mCache.put("fistCity",spiceSportRemberBean.getFistCity());
                    mCache.put("secondCity",spiceSportRemberBean.getSecondCity());
                    mCache.put("position","0");
                    mCache.put("fromLat",String.valueOf(spiceSportRemberBean.getFromLat()));
                    mCache.put("fromLong",String.valueOf(spiceSportRemberBean.getFromLong()));
                    mCache.put("toLat",String.valueOf(spiceSportRemberBean.getToLat()));
                    mCache.put("toLong",String.valueOf(spiceSportRemberBean.getToLong()));
                    mCache.put("disNow",String.valueOf(spiceSportRemberBean.getDisNow()));
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    public void initView() {
       /* traInfo.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new RecyclerSportSpiceDisAdapter(context, Default.getSportDisSpice());
        traInfo.setAdapter(adapter);*/

        traInfo.setLayoutManager(new LinearLayoutManager(this));
        adapter = Type.values()[2].get(context, Default.getSportDisSpice());
        adapter.setFirstOnly(false);
        adapter.setDuration(500);
        adapter.setInterpolator(new OvershootInterpolator(.5f));
        traInfo.setAdapter(adapter);

        RecyclerSportSpiceDisAdapter.setOnItemClickListener(new RecyclerSportSpiceDisAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, ArrayList list) {
                if (page == 0) {                  //室内里程趣味跑
                    Intent intent = new Intent(SportStyleActivity.this, SportHomeDisSpiceActivity.class);
                    intent.putExtra("fistCity", ((SportDisSpiceBean) (list.get(position))).fistCity);
                    intent.putExtra("secondCity", ((SportDisSpiceBean) (list.get(position))).secondCity);
                    intent.putExtra("position", position);
                    intent.putExtra("fromLat", ((SportDisSpiceBean) (list.get(position))).fromLat);
                    intent.putExtra("fromLong", ((SportDisSpiceBean) (list.get(position))).fromLong);
                    intent.putExtra("toLat", ((SportDisSpiceBean) (list.get(position))).toLat);
                    intent.putExtra("toLong", ((SportDisSpiceBean) (list.get(position))).toLong);
                    intent.putExtra("disNow", 0);

                    mCache.put("fistCity",((SportDisSpiceBean) (list.get(position))).fistCity);
                    mCache.put("secondCity",((SportDisSpiceBean) (list.get(position))).secondCity);
                    mCache.put("position","0");
                    mCache.put("fromLat",String.valueOf(((SportDisSpiceBean) (list.get(position))).fromLat));
                    mCache.put("fromLong",String.valueOf(((SportDisSpiceBean) (list.get(position))).fromLong));
                    mCache.put("toLat",String.valueOf(((SportDisSpiceBean) (list.get(position))).toLat));
                    mCache.put("toLong",String.valueOf(((SportDisSpiceBean) (list.get(position))).toLong));
                    mCache.put("disNow","0");

                    startActivity(intent);
                    finish();
                }
                if (page == 1) {                   //室外里程趣味跑
                    Intent intent = new Intent(SportStyleActivity.this, SportOutDisSpiceActivity.class);
                    intent.putExtra("fistCity", ((SportDisSpiceBean) (list.get(position))).fistCity);
                    intent.putExtra("secondCity", ((SportDisSpiceBean) (list.get(position))).secondCity);
                    intent.putExtra("position", "0");
                    intent.putExtra("fromLat", ((SportDisSpiceBean) (list.get(position))).fromLat);
                    intent.putExtra("fromLong", ((SportDisSpiceBean) (list.get(position))).fromLong);
                    intent.putExtra("toLat", ((SportDisSpiceBean) (list.get(position))).toLat);
                    intent.putExtra("toLong", ((SportDisSpiceBean) (list.get(position))).toLong);
                    intent.putExtra("disNow", 0);

                    mCache.put("fistCity",((SportDisSpiceBean) (list.get(position))).fistCity);
                    mCache.put("secondCity",((SportDisSpiceBean) (list.get(position))).secondCity);
                    mCache.put("position","0");
                    mCache.put("fromLat",String.valueOf(((SportDisSpiceBean) (list.get(position))).fromLat));
                    mCache.put("fromLong",String.valueOf(((SportDisSpiceBean) (list.get(position))).fromLong));
                    mCache.put("toLat",String.valueOf(((SportDisSpiceBean) (list.get(position))).toLat));
                    mCache.put("toLong",String.valueOf(((SportDisSpiceBean) (list.get(position))).toLong));
                    mCache.put("disNow","0");

                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onItemLongClick(View view, int position, ArrayList list) {

            }
        });
    }

    @OnClick({R.id.back, R.id.mySet})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.mySet:
                Intent intent = new Intent(SportStyleActivity.this, MyGeocodeSelectActivity.class);
                intent.putExtra("page", page);
                startActivity(intent);
                break;
        }
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    /*public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("SportStyle Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }*/

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        /*client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());*/
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        /*AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();*/
    }


    /**
     * 内部类实现进度条和界面动态显示
     */
    public class MyBroadcast extends BroadcastReceiver {

        public MyBroadcast() {

        }

        /**
         * @param context
         * @param intent  intent1.putExtra("state",num);
         */
        @Override
        public void onReceive(Context context, Intent intent) {

            String cor = intent.getStringExtra("contextActivity");        //当前正在跑跑步的activity
            int state = intent.getIntExtra("state", 0);
            //如果广播的跑步界面地址不为空，切跑步状态不为4，还没有跳转，则提示跳转
            if(cor != null && state != 4 && !flag){
                flag = true;
                Intent intent1 = new Intent();
                intent1.setAction(cor);//设置intent的Action属性值
                intent1.addCategory(Intent.CATEGORY_DEFAULT);//不加这行也行，因为这个值默认就是Intent.CATEGORY_DEFAULT
                startActivity( intent1 );
            }
        }
    }
}
