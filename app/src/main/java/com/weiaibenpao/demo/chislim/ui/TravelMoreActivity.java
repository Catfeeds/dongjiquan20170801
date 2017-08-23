package com.weiaibenpao.demo.chislim.ui;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.gjiazhe.scrollparallaximageview.parallaxstyle.HorizontalMovingStyle;
import com.view.jameson.library.CardScaleHelper;
import com.view.jameson.library.SpeedRecyclerView;
import com.weiaibenpao.demo.chislim.Interface.GetInterfaceVideoListener;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.adater.RecyclerTravelMoreAdapter;
import com.weiaibenpao.demo.chislim.adater.RecyclerTravelMoreAdapter2;
import com.weiaibenpao.demo.chislim.bean.TravelResult;
import com.weiaibenpao.demo.chislim.util.ACache;
import com.weiaibenpao.demo.chislim.util.GetIntentData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * 这个是国内更多，国外更多，港澳台查看更多界面
 */

public class TravelMoreActivity extends Activity {

    private CardScaleHelper mCardScaleHelper = null;
    GetIntentData getIntent;
    GetIntentData getIntentFavour;

    String address;

    //声明一个LinearLayoutManager
    LinearLayoutManager layoutManager;
    RecyclerTravelMoreAdapter mAdapter1;
    RecyclerTravelMoreAdapter2 mAdapter2;
    ArrayList myArrayList;
    Context context;
    @BindView(R.id.myImageWord)
    TextView myImageWord;
    @BindView(R.id.littleTitle)
    TextView littleTitle;
    @BindView(R.id.traInfo)
    SpeedRecyclerView traInfo;


    private MaterialRefreshLayout materialRefreshLayout;
    private boolean isLoadMore = true;

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.traMore)
    RecyclerView traMore;
    @BindView(R.id.activity_travel_more)
    RelativeLayout activityTravelMore;

    private ACache mCache;

    int page = 1;
    int pageMore = 1;
    boolean flagShang = false;
    boolean flagXia = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_travel_more);
        ButterKnife.bind(this);

        context = getApplicationContext();
        mCache = ACache.get(context);
        myArrayList = new ArrayList();
        Intent intent = getIntent();
        context = this;

        address = intent.getStringExtra("address");
        getIntentFavour = new GetIntentData();
        getIntent = new GetIntentData();

        initView();
        // initCache();
        initData(page);
        initRefresh();


    }

    private void initRefresh() {

        materialRefreshLayout.setLoadMore(isLoadMore);//允许上拉更多操作
        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {

            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
                //一般加载数据都是在子线程中，这里用到了handler
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        if (!flagXia) {
                            pageMore++;
                            flagXia = true;
                            initMore(pageMore);
                        }

                       /* if (num == 1) {
                            initData(1);
                            num++;
                            mAdapter2.notifyDataSetChanged();
                        } else {
                            initData(num - 1);
                            mAdapter2.notifyDataSetChanged();
                        }*/

                        materialRefreshLayout.finishRefresh();
                    }
                }, 1500);
            }

            /*上啦加载*/
            @Override
            public void onRefreshLoadMore(final MaterialRefreshLayout materialRefreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!flagShang) {
                            page++;
                            flagShang = true;
                            initData(page);
                        }


                        isLoadMore = false;
                        /*if (num == 2) {
                            initData(2);
                            //通知刷新
                            mAdapter2.notifyDataSetChanged();
                        } else {
                            initData(num + 1);
                            //通知刷新
                            mAdapter2.notifyDataSetChanged();
                        }*/
                        //Toast.makeText(TravelMoreActivity.this, "  亲，这是最新数据哦!", Toast.LENGTH_SHORT).show();

                        //Snackbar.make(activityTravelMore, "   亲，这是最新数据哦!", Snackbar.LENGTH_SHORT).show();
                        materialRefreshLayout.finishRefreshLoadMore();
                    }
                }, 1500);
            }
        });

        // initView();
        initCache();
        //initData(1);
        initMore(1);

    }

    public void initCache() {
        String tab = "travelNotesResult" + address;
        //获取缓存数据
        TravelResult travelResult = (TravelResult) mCache.getAsObject(tab);
        if (travelResult != null) {
            if (mAdapter1 == null) {
                mAdapter1 = new RecyclerTravelMoreAdapter(TravelMoreActivity.this, (ArrayList) travelResult.getTravel(),new HorizontalMovingStyle(), 6);
                traInfo.setAdapter(mAdapter1);
                mCardScaleHelper = new CardScaleHelper();
                mCardScaleHelper.setCurrentItemPos(2);
                mCardScaleHelper.attachToRecyclerView(traInfo);

            }
        }

        TravelResult travelResultFavour = (TravelResult) mCache.getAsObject("travelNotesResultfavotite");
        if (travelResult != null) {
            if (mAdapter2 == null) {
                mAdapter2 = new RecyclerTravelMoreAdapter2(TravelMoreActivity.this, (ArrayList) travelResultFavour.getTravel(), 6);
                traMore.setAdapter(mAdapter2);
            }
        }
    }


    public void initData(int i) {
        getIntent.getTravel(context, "getAllTravel", address, i, 6);
        getIntent.setGetIntentDataListener(new GetInterfaceVideoListener() {
            @Override
            public void getDateList(ArrayList dateList) {
                if (dateList.size() > 0) {
                    if (page == 1 && mAdapter2 == null) {
                        mAdapter2 = new RecyclerTravelMoreAdapter2(TravelMoreActivity.this, dateList, 6);
                        traInfo.setAdapter(mAdapter2);
                        mCardScaleHelper = new CardScaleHelper();
                        mCardScaleHelper.setCurrentItemPos(2);
                        mCardScaleHelper.attachToRecyclerView(traInfo);
                    } else if (page == 1 && mAdapter2 != null) {
                        mAdapter2.changeData(dateList);
                    } else if (page > 1 && flagShang) {
                        mAdapter2.addMoreData(dateList);
                        flagShang = false;
                    } else if (page > 1 && flagXia) {
                        mAdapter2.refreshItem(dateList);
                        flagXia = false;
                    }
                } else {
                    page--;
                }
            }
        });
    }

    public void initMore(int i) {
        getIntent.getTravel(context, "getAllTravel", "favotite", i, 3);
        getIntent.setGetIntentDataListener(new GetInterfaceVideoListener() {
            @Override
            public void getDateList(ArrayList dateList) {
                if (dateList.size() > 0) {
                    if (pageMore == 1 && mAdapter1 == null) {
                        mAdapter1 = new RecyclerTravelMoreAdapter(TravelMoreActivity.this, dateList,new HorizontalMovingStyle() ,3);
                        traInfo.setAdapter(mAdapter1);
                        mCardScaleHelper = new CardScaleHelper();
                        mCardScaleHelper.setCurrentItemPos(2);
                        mCardScaleHelper.attachToRecyclerView(traInfo);

                    } else if (pageMore == 1 && mAdapter1 != null) {
                        mAdapter1.changeData(dateList);
                    } else if (pageMore > 1 && flagShang) {
                        mAdapter1.addMoreData(dateList);
                        flagShang = false;
                    } else if (pageMore > 1 && flagXia) {
                        mAdapter1.refreshItem(dateList);
                        flagXia = false;
                    }
                } else {
                    pageMore--;
                }
            }
        });
    }


    @TargetApi(Build.VERSION_CODES.M)
    public void initView() {
        Log.i("测试", "-------" + address + "------");
        if (address.equals("location")) {
            myImageWord.setText("国 内 旅 游");
            littleTitle.setText("国 内 精 选 旅 游 圣 地");
        } else if (address.equals("taiwan")) {
            myImageWord.setText("港 澳 台 旅 游");
            littleTitle.setText("港 澳 台 选 旅 游 圣 地");
        } else if (address.equals("foreign")) {
            myImageWord.setText("国 际 旅 游");
            littleTitle.setText("国 际 精 选 旅 游 圣 地");
        } else if (address.equals("favotite")) {
            myImageWord.setText("猜 你 喜 欢");
            littleTitle.setText("猜 你 喜 欢 精 选 旅 游 圣 地");
        }

        materialRefreshLayout = (MaterialRefreshLayout) findViewById(R.id.refresh);
        layoutManager = new LinearLayoutManager(this);         //竖线列表
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        traMore.setLayoutManager(layoutManager);

        traInfo.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));     //横向列表

        //纵向列表
        RecyclerTravelMoreAdapter.setOnItemClickListener(new RecyclerTravelMoreAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, ArrayList list) {
                Intent intent = new Intent(context, TravelHtmlActivity.class);
                intent.putExtra("travel", (Parcelable) list.get(position));
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position, ArrayList list) {

            }
        });

        //横向列表
        RecyclerTravelMoreAdapter2.setOnItemClickListener(new RecyclerTravelMoreAdapter2.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, ArrayList list) {
                Intent intent = new Intent(context, TravelHtmlActivity.class);
                intent.putExtra("travel", (Parcelable) list.get(position));
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position, ArrayList list) {

            }
        });


    }

    @OnClick(R.id.back)
    public void onClick() {
        finish();
    }


/*    public void initSwipeRefresh() {
        //设置下拉刷新旋转进度的背景颜色
        srl.setProgressBackgroundColorSchemeResource(android.R.color.white);
        //旋转圈圈的颜色，渐变色
        srl.setColorSchemeResources(android.R.color.holo_blue_light,
                android.R.color.holo_red_light, android.R.color.darker_gray,
                android.R.color.holo_green_light);

        srl.setProgressViewOffset(false, 8, (int) TypedValue
                .applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources()
                        .getDisplayMetrics()));
    }*/

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter1.setOnItemClickListener(null);
    }
}
