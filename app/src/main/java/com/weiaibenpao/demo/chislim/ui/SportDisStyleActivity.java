package com.weiaibenpao.demo.chislim.ui;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.lljjcoder.citypickerview.widget.CityPicker;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.adater.SportDisStyleAdapter;
import com.weiaibenpao.demo.chislim.adater.SportDisStyleAdapter2;
import com.weiaibenpao.demo.chislim.bean.GetInterestSportResult;
import com.weiaibenpao.demo.chislim.bean.SportCustomsBean;
import com.weiaibenpao.demo.chislim.bean.SportDistrictBean;
import com.weiaibenpao.demo.chislim.bean.UserBean;
import com.weiaibenpao.demo.chislim.model.MyModel;
import com.weiaibenpao.demo.chislim.util.ACache;
import com.weiaibenpao.demo.chislim.util.Default;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.AnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInRightAnimationAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by zhangxing on 2017/1/21.
 */

public class SportDisStyleActivity extends Activity {

    SportDisStyleAdapter sportDisStyleAdapter;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.titleTv)
    TextView titleTv;
    @BindView(R.id.mySet)
    TextView mySet;
    @BindView(R.id.myTop)
    RelativeLayout myTop;
    @BindView(R.id.traInfo)
    RecyclerView traInfo;
    @BindView(R.id.activity_sport_style)
    RelativeLayout activitySportStyle;

    Intent getIntent;
    @BindView(R.id.oldSport)
    RecyclerView oldSport;

    private ACache mCache;
    SportCustomsBean sportCustomsBean;
    SportDistrictBean sportDistrictBean;
    Context context;
    int page;
    String city_selected;
    AnimationAdapter adapter;

    boolean flag = false;

    List<GetInterestSportResult.DataBean> dataBean;
    SportDisStyleAdapter2 adapter2;
    enum Type {
        AlphaIn {
            @Override
            public AnimationAdapter get(Context context, ArrayList list) {
                SportDisStyleAdapter adapter = new SportDisStyleAdapter(context, list);
                return new AlphaInAnimationAdapter(adapter);
            }
        },
        ScaleIn {
            @Override
            public AnimationAdapter get(Context context, ArrayList list) {
                SportDisStyleAdapter adapter = new SportDisStyleAdapter(context, list);
                return new ScaleInAnimationAdapter(adapter);
            }
        },
        SlideInBottom {
            @Override
            public AnimationAdapter get(Context context, ArrayList list) {
                SportDisStyleAdapter adapter = new SportDisStyleAdapter(context, list);
                return new SlideInBottomAnimationAdapter(adapter);
            }
        },
        SlideInLeft {
            @Override
            public AnimationAdapter get(Context context, ArrayList list) {
                SportDisStyleAdapter adapter = new SportDisStyleAdapter(context, list);
                return new SlideInLeftAnimationAdapter(adapter);
            }
        },
        SlideInRight {
            @Override
            public AnimationAdapter get(Context context, ArrayList list) {
                SportDisStyleAdapter adapter = new SportDisStyleAdapter(context, list);
                return new SlideInRightAnimationAdapter(adapter);
            }
        };

        public abstract AnimationAdapter get(Context context, ArrayList list);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sport_dis_style);
        Log.e("onCreate","SportDisStyleActivity");
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this , getResources().getColor(R.color.ku_bg ) , 0);
        context = getApplicationContext();
        getIntent = getIntent();
        page = getIntent.getIntExtra("position", 0);
        mCache = ACache.get(context);    //实例化缓存

        oldSport.setLayoutManager(new LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL,false));

        initView();
        initData();

        adapter2.setOnItemClickListener(new SportDisStyleAdapter2.OnItemClickListener() {
            @Override
            public void OnItemClick(View v, int position, ArrayList list) {
                if (page == 0) {                  //室内行政跑
//                    Toast.makeText(SportDisStyleActivity.this,"头部列表之_室内行政跑?",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SportDisStyleActivity.this, SportHomeDistrictActivity.class);
                    intent.putExtra("position", position);
                    intent.putExtra("provinceName", ((GetInterestSportResult.DataBean) (list.get(position))).getCityName());
                    intent.putExtra("disOld", ((GetInterestSportResult.DataBean) (list.get(position))).getNowDis());
                    startActivity(intent);
                    finish();
                }
                if (page == 1) {                  //室外行政跑
//                    Toast.makeText(SportDisStyleActivity.this,"头部列表之_室外行政跑?",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SportDisStyleActivity.this, SportOutDistrictActivity.class);
                    intent.putExtra("position", position);
                    intent.putExtra("provinceName", ((GetInterestSportResult.DataBean) (list.get(position))).getCityName());
                    intent.putExtra("disOld", ((GetInterestSportResult.DataBean) (list.get(position))).getNowDis());
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    //获取跑步记录
    public void initData() {
        Call<GetInterestSportResult> call = MyModel.getModel().getService().GetInterestSportResult(UserBean.getUserBean().userId);

        call.enqueue(new Callback<GetInterestSportResult>() {
            @Override
            public void onResponse(Call<GetInterestSportResult> call, Response<GetInterestSportResult> response) {
                if (response.isSuccessful()) {
                    GetInterestSportResult result = response.body();

                    if (result.getCode() == 0) {
                        dataBean = result.getData();
                        adapter2= new SportDisStyleAdapter2(context, (ArrayList) dataBean);
                        oldSport.setAdapter(adapter2);

                    } else {
                        //隐藏记录布局
                    }
                }
            }

            @Override
            public void onFailure(Call<GetInterestSportResult> call, Throwable t) {
                // Toast.makeText(context,"教程获取失败",Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * 保留一位小数
     *
     * @param d
     */
    private double printNum(double d) {
        BigDecimal bdc = new BigDecimal(d);
        double d1 = bdc.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue();
        return d1;
    }

    public void initView() {
        traInfo.setLayoutManager(new LinearLayoutManager(this));
        adapter = Type.values()[2].get(context, Default.getSportDistrict());
        adapter.setFirstOnly(false);
        adapter.setDuration(500);
        adapter.setInterpolator(new OvershootInterpolator(.5f));
        traInfo.setAdapter(adapter);

        sportDisStyleAdapter.setOnItemClickListener(new SportDisStyleAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View v, int position, ArrayList list) {
//                Log.e("onItemClick","position is "+position+"\n district name is "+((SportDistrictBean) (list.get(position))).getName());
                if (page == 0) {                  //室内行政跑
//                    Toast.makeText(SportDisStyleActivity.this,"body列表之_室内行政跑?",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SportDisStyleActivity.this, SportHomeDistrictActivity.class);
                    intent.putExtra("position", position);
                    intent.putExtra("provinceName", ((SportDistrictBean) (list.get(position))).getName());
                    intent.putExtra("disOld", 0+"");
                    startActivity(intent);
                    finish();
                }
                if (page == 1) {                  //室外行政跑
//                    Toast.makeText(SportDisStyleActivity.this,"body列表之_室外行政跑?",Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(SportDisStyleActivity.this, SportOutDistrictActivity.class);
                    intent.putExtra("position", position);
                    intent.putExtra("provinceName", ((SportDistrictBean) (list.get(position))).getName());
                    intent.putExtra("disOld", 0+"");
                    startActivity(intent);
                    finish();
                }
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
                CityPicker cityPicker = new CityPicker.Builder(SportDisStyleActivity.this).textSize(20)
                        .title("")
                        .titleTextColor("#18A0F6")
                       // .backgroundPop(0xa0000000)
                        // .backgroundPop(000000)
                       // .titleBackgroundColor("#00CD66")
                        .titleBackgroundColor("#ffffff")
                        .confirTextColor("#18A0F6")
                        .cancelTextColor("#18A0F6")
                        .province("湖北省")
                        .city("武汉市")
                        .district("洪山区")
                        .textColor(Color.parseColor("#000000"))
                        .provinceCyclic(true)
                        .cityCyclic(false)
                        .districtCyclic(false)
                        .visibleItemsCount(7)
                        .itemPadding(10)
                        .build();

                cityPicker.show();
                cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
                    @Override
                    public void onSelected(String... citySelected) {
                        city_selected = citySelected[2];
                        if (page == 0) {
                            //自定义室内行政跑
                            Intent intent = new Intent(SportDisStyleActivity.this, SportHomeDistrictActivity.class);
                            intent.putExtra("provinceName", city_selected);
                            intent.putExtra("disOld", 0+"");
                            startActivity(intent);
                            finish();
                        }
                        if (page == 1) {
                            //自定义室外行政跑
                            Intent intent = new Intent(SportDisStyleActivity.this, SportOutDistrictActivity.class);
                            intent.putExtra("provinceName", city_selected);
                            intent.putExtra("disOld", 0+"");
                            startActivity(intent);
                            finish();
                        }
                    }
                });
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

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
            if (cor != null && state != 4 && !flag) {
                flag = true;
                Intent intent1 = new Intent();
                intent1.setAction(cor);//设置intent的Action属性值
                intent1.addCategory(Intent.CATEGORY_DEFAULT);//不加这行也行，因为这个值默认就是Intent.CATEGORY_DEFAULT
                startActivity(intent1);
            }
        }
    }
}
