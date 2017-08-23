package com.weiaibenpao.demo.chislim.viewpagerfragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.gjiazhe.scrollparallaximageview.parallaxstyle.HorizontalMovingStyle;
import com.gjiazhe.scrollparallaximageview.parallaxstyle.VerticalMovingStyle;
import com.squareup.picasso.Picasso;
import com.view.jameson.library.CardScaleHelper;
import com.view.jameson.library.SpeedRecyclerView;
import com.weiaibenpao.demo.chislim.Interface.GetInterfaceVideoListener;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.adater.RecyclerTravelAdapter;
import com.weiaibenpao.demo.chislim.adater.RecyclerTravelFavoriteAdapter;
import com.weiaibenpao.demo.chislim.base.BaseFragment;
import com.weiaibenpao.demo.chislim.bean.TravelResult;
import com.weiaibenpao.demo.chislim.gravityresult.WindowView;
import com.weiaibenpao.demo.chislim.ui.TravelHtmlActivity;
import com.weiaibenpao.demo.chislim.ui.TravelMoreActivity;
import com.weiaibenpao.demo.chislim.util.ACache;
import com.weiaibenpao.demo.chislim.util.GetIntentData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static com.squareup.picasso.MemoryPolicy.NO_CACHE;
import static com.squareup.picasso.MemoryPolicy.NO_STORE;


/**
 * Created by Administrator on 2016/4/26.
 */
public class ThirdViewPagerFragment extends BaseFragment {

    View view;

    String[] images, titles;
    Context context;

    GetIntentData getIntentDataLocation;
    ArrayList travelLocationList;
    @BindView(R.id.localTwoHot)
    SpeedRecyclerView localTwoHot;
    @BindView(R.id.foreignHot)
    SpeedRecyclerView foreignHot;
    private CardScaleHelper mCardScaleHelper = null;
    GetIntentData getIntentDataForeign;
    ArrayList travelForeignList;

    GetIntentData getIntentDataGAT;
    ArrayList travelGATList;

    GetIntentData getIntentDataFavotite;
    ArrayList travelFavotiteList;

    private final static String TAG = "RecyclerView";

    @BindView(R.id.getLocation)
    TextView getLocation;
    @BindView(R.id.getGAT)
    TextView getGAT;
    @BindView(R.id.getForeign)
    TextView getForeign;

    ArrayList bunnerList;
    GetIntentData intentData;
    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.localLayout)
    LinearLayout localLayout;
    @BindView(R.id.gatLayout)
    LinearLayout gatLayout;
    @BindView(R.id.foreignLayout)
    LinearLayout foreignLayout;
    @BindView(R.id.getfavotite)
    TextView getfavotite;
    @BindView(R.id.favotiteLayout)
    LinearLayout favotiteLayout;
    @BindView(R.id.favotiteHot)
    RecyclerView favotiteHot;
    @BindView(R.id.windowView1)
    WindowView windowView1;

    @BindView(R.id.activity_travel)
    RelativeLayout activityTravel;
    @BindView(R.id.localHot)
    SpeedRecyclerView localHot;
    private ACache mCache;

    RecyclerTravelAdapter mAdapter1;
    RecyclerTravelAdapter mAdapter2;
    RecyclerTravelAdapter mAdapter3;
    RecyclerTravelFavoriteAdapter mAdapter4;
    //private boolean isLoadMore = true;

    int page = 1;
    boolean flagShang = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_travel, container, false);
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }
        images = getResources().getStringArray(R.array.url);
        titles = getResources().getStringArray(R.array.title);

        context = getActivity().getApplicationContext();

        intentData = new GetIntentData();
        bunnerList = new ArrayList();

        mCache = ACache.get(context);

        ButterKnife.bind(this, view);

        getIntentDataLocation = new GetIntentData();
        getIntentDataForeign = new GetIntentData();
        getIntentDataGAT = new GetIntentData();
        getIntentDataFavotite = new GetIntentData();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initView(view);
        initCatch();
        initData();

        Picasso.with(context)
                .load(R.mipmap.pic3)
                .config(Bitmap.Config.RGB_565)
                .memoryPolicy(NO_CACHE, NO_STORE)
                .resize(360, 180)
                .centerCrop().into(windowView1);
        //Glide.with(context).load(R.mipmap.pic5).into(windowView1);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //initRefresh();
    }

    @Override
    public void initView(View view) {
        //localHot.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        localHot.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));

        localTwoHot.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));

        foreignHot.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));

        favotiteHot.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));

        //点击事件
        RecyclerTravelAdapter.setOnItemClickListener(new RecyclerTravelAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, ArrayList list) {
                Log.i(TAG, "点击了第" + position + "图片");
                Intent intent = new Intent(getActivity(), TravelHtmlActivity.class);
                intent.putExtra("travel", (Parcelable) list.get(position));
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position, ArrayList list) {
                Log.i(TAG, "长按了第" + position + "图片");
            }
        });

        RecyclerTravelFavoriteAdapter.setOnItemClickListener(new RecyclerTravelFavoriteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, ArrayList list) {
                Intent intent = new Intent(getActivity(), TravelHtmlActivity.class);
                intent.putExtra("travel", (Parcelable) list.get(position));
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position, ArrayList list) {

            }
        });
    }


    public void initRefresh() {
        MaterialRefreshLayout refresh = (MaterialRefreshLayout) view.findViewById(R.id.refresh);
        // refresh.setLoadMore(isLoadMore);//允许上拉更多操作
        refresh.setLoadMore(true);//允许上拉更多操作
        refresh.setMaterialRefreshListener(new MaterialRefreshListener() {

            @Override
            public void onRefresh(final MaterialRefreshLayout materialRefreshLayout) {
                //一般加载数据都是在子线程中，这里用到了handler
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //                      Snackbar.make(activityTravel, " 已经是最新的数据了，亲！", Snackbar.LENGTH_SHORT).show();
                        materialRefreshLayout.finishRefresh();
                    }
                }, 1300);

                /*new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            materialRefreshLayout.finishRefresh();
                        }
                    }
                }).start();*/

            }

            @Override
            public void onRefreshLoadMore(final MaterialRefreshLayout materialRefreshLayout) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // isLoadMore = false;

                        if (!flagShang) {
                            page++;
                            flagShang = true;
                            initFavourit(page);
                        }

//                        Snackbar.make(activityTravel, " 给小主的新数据哦!", Snackbar.LENGTH_SHORT).show();
                        materialRefreshLayout.finishRefreshLoadMore();
                    }
                }, 1300);

                /*new Thread(new Runnable() {
                    @Override
                    public void run() {

                        try {
                            isLoadMore = true;

                            if(!flagShang){
                                page++;
                                flagShang = true;
                                initFavourit(page);
                            }
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } finally {
                            materialRefreshLayout.finishRefreshLoadMore();
                        }
                    }
                }).start();*/

            }
        });
    }

    @Override
    public void initData() {
        travelLocationList = new ArrayList();
        travelForeignList = new ArrayList();
        travelGATList = new ArrayList();
        travelFavotiteList = new ArrayList();

        getDate(context, "getAllTravel", "location", 1, 6);
        getDate(context, "getAllTravel", "foreign", 1, 6);
        getDate(context, "getAllTravel", "taiwan", 1, 6);
        initFavourit(1);
    }

    /**
     * 获取缓存数据
     */
    public void initCatch() {
        //获取缓存数据
        TravelResult travelResultlocation = (TravelResult) mCache.getAsObject("travelNotesResultlocation");
        if (travelResultlocation != null) {     //如果travelResultlocation为空   则说明无缓存
            if (mAdapter1 == null) {           //如果适配器等于空，说明是第一次刚进入app，页面无数据展示
                mAdapter1 = new RecyclerTravelAdapter(getActivity(), (ArrayList) travelResultlocation.getTravel(), new HorizontalMovingStyle(), 6);
                localHot.setAdapter(mAdapter1);
                // mRecyclerView绑定scale效果
                mCardScaleHelper = new CardScaleHelper();
                mCardScaleHelper.setCurrentItemPos(2);
                mCardScaleHelper.attachToRecyclerView(localHot);

            }
        }

        //获取缓存数据
        TravelResult travelResultforeign = (TravelResult) mCache.getAsObject("travelNotesResultforeign");
        if (travelResultforeign != null) {
            if (mAdapter3 == null) {
                mAdapter3 = new RecyclerTravelAdapter(getActivity(), (ArrayList) travelResultforeign.getTravel(),new HorizontalMovingStyle(), 8);
                foreignHot.setAdapter(mAdapter3);
                // mRecyclerView绑定scale效果
                mCardScaleHelper = new CardScaleHelper();
                mCardScaleHelper.setCurrentItemPos(2);
                mCardScaleHelper.attachToRecyclerView(foreignHot);


            }
        }

        //获取缓存数据
        TravelResult travelResulttaiwan = (TravelResult) mCache.getAsObject("travelNotesResulttaiwan");
        if (travelResulttaiwan != null) {
            if (mAdapter2 == null) {
                mAdapter2 = new RecyclerTravelAdapter(getActivity(), (ArrayList) travelResulttaiwan.getTravel(), new HorizontalMovingStyle(), 3);
                localTwoHot.setAdapter(mAdapter2);
                // mRecyclerView绑定scale效果
                mCardScaleHelper = new CardScaleHelper();
                mCardScaleHelper.setCurrentItemPos(2);
                mCardScaleHelper.attachToRecyclerView(localTwoHot);


            }
        }
        //获取缓存数据
        TravelResult travelResultfavotite = (TravelResult) mCache.getAsObject("travelNotesResultfavotite");
        if (travelResultfavotite != null) {
            if (mAdapter4 == null) {
                mAdapter4 = new RecyclerTravelFavoriteAdapter(getActivity(), (ArrayList) travelResultfavotite.getTravel(),new VerticalMovingStyle(), 3);
                favotiteHot.setAdapter(mAdapter4);
            }
        }
    }

    /**
     * 从网络拉去数据
     *
     * @param context
     * @param dowhat
     * @param tab
     * @param i
     * @param num
     */
    public void getDate(final Context context, String dowhat, String tab, int i, int num) {
        switch (tab) {
            case "location":
                getIntentDataLocation.getTravel(context, dowhat, tab, i, num);
                getIntentDataLocation.setGetIntentDataListener(new GetInterfaceVideoListener() {
                    @Override
                    public void getDateList(ArrayList dateList) {
                        if (dateList.size() > 0 && mAdapter1 == null) {
                            mAdapter1 = new RecyclerTravelAdapter(getActivity(), dateList, new HorizontalMovingStyle(), 6);
                            localHot.setAdapter(mAdapter1);
                            // mRecyclerView绑定scale效果
                            mCardScaleHelper = new CardScaleHelper();
                            mCardScaleHelper.setCurrentItemPos(2);
                            mCardScaleHelper.attachToRecyclerView(localHot);

                        } else if (dateList.size() > 0 && mAdapter1 != null) {
                            mAdapter1.changeMoreData(dateList);
                        }
                    }
                });
                break;
            case "foreign":
                getIntentDataForeign.getTravel(context, dowhat, tab, i, num);
                getIntentDataForeign.setGetIntentDataListener(new GetInterfaceVideoListener() {
                    @Override
                    public void getDateList(ArrayList dateList) {
                        if (dateList.size() > 0 && mAdapter3 == null) {
                            mAdapter3 = new RecyclerTravelAdapter(getActivity(), dateList,new HorizontalMovingStyle(), 8);
                            foreignHot.setAdapter(mAdapter3);
                            // mRecyclerView绑定scale效果
                            mCardScaleHelper = new CardScaleHelper();
                            mCardScaleHelper.setCurrentItemPos(2);
                            mCardScaleHelper.attachToRecyclerView(foreignHot);



                        } else if (dateList.size() > 0 && mAdapter3 != null) {
                            mAdapter3.changeMoreData(dateList);
                        }
                    }
                });
                break;
            case "taiwan":
                getIntentDataGAT.getTravel(context, dowhat, tab, i, num);
                getIntentDataGAT.setGetIntentDataListener(new GetInterfaceVideoListener() {
                    @Override
                    public void getDateList(ArrayList dateList) {
                        if (dateList.size() > 0 && mAdapter2 == null) {
                            mAdapter2 = new RecyclerTravelAdapter(getActivity(), dateList, new HorizontalMovingStyle(), 3);
                            localTwoHot.setAdapter(mAdapter2);
                            // mRecyclerView绑定scale效果
                            mCardScaleHelper = new CardScaleHelper();
                            mCardScaleHelper.setCurrentItemPos(2);
                            mCardScaleHelper.attachToRecyclerView(localTwoHot);

                        } else if (dateList.size() > 0 && mAdapter2 != null) {
                            mAdapter2.changeMoreData(dateList);
                        }
                    }
                });
                break;
        }
    }

    public void initFavourit(int i) {
        getIntentDataFavotite.getTravel(context, "getAllTravel", "favotite", i, 6);
        getIntentDataFavotite.setGetIntentDataListener(new GetInterfaceVideoListener() {
            @Override
            public void getDateList(ArrayList dateList) {

                if (dateList.size() > 0) {
                    if (mAdapter4 == null) {
                        mAdapter4 = new RecyclerTravelFavoriteAdapter(context, dateList,new VerticalMovingStyle(), 6);
                        favotiteHot.setAdapter(mAdapter4);
                    } else if (page == 1 && mAdapter4 != null) {
                        mAdapter4.changeMoreData(dateList);
                    } else if (page > 1 && flagShang) {
                        mAdapter4.addMoreData(dateList);
                        flagShang = false;
                    }
                } else {
                    page--;
                }
            }
        });
    }

    //如果你需要考虑更好的体验，可以这么操作
    @Override
    public void onStart() {
        super.onStart();
        Log.i("--", "onStart");
        //开始轮播
        //banner.startAutoPlay();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i("--", "onStop");
        //结束轮播
//        banner.stopAutoPlay();
    }

    @OnClick({R.id.getLocation, R.id.getGAT, R.id.getForeign, R.id.localLayout, R.id.gatLayout, R.id.foreignLayout, R.id.getfavotite, R.id.favotiteLayout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.getLocation:
                Intent intent1 = new Intent(getActivity(), TravelMoreActivity.class);
                intent1.putExtra("address", "location");
                startActivity(intent1);
                break;
            case R.id.getGAT:
                Intent intent2 = new Intent(getActivity(), TravelMoreActivity.class);
                intent2.putExtra("address", "taiwan");
                startActivity(intent2);
                break;
            case R.id.getForeign:
                Intent intent3 = new Intent(getActivity(), TravelMoreActivity.class);
                intent3.putExtra("address", "foreign");
                startActivity(intent3);
                break;
            case R.id.localLayout:
                Intent intent4 = new Intent(getActivity(), TravelMoreActivity.class);
                intent4.putExtra("address", "location");
                startActivity(intent4);
                break;
            case R.id.gatLayout:
                Intent intent5 = new Intent(getActivity(), TravelMoreActivity.class);
                intent5.putExtra("address", "taiwan");
                startActivity(intent5);
                break;
            case R.id.foreignLayout:
                Intent intent6 = new Intent(getActivity(), TravelMoreActivity.class);
                intent6.putExtra("address", "foreign");
                startActivity(intent6);
                break;
            case R.id.getfavotite:
                Intent intent7 = new Intent(getActivity(), TravelMoreActivity.class);
                intent7.putExtra("address", "favotite");
                startActivity(intent7);
                break;
            case R.id.favotiteLayout:
                Intent intent8 = new Intent(getActivity(), TravelMoreActivity.class);
                intent8.putExtra("address", "favotite");
                startActivity(intent8);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (getIntentDataLocation != null) {
            getIntentDataLocation.setGetIntentDataListener(null);
        }

        if (getIntentDataForeign != null) {
            getIntentDataForeign.setGetIntentDataListener(null);
        }

        if (getIntentDataGAT != null) {
            getIntentDataGAT.setGetIntentDataListener(null);
        }

        if (getIntentDataFavotite != null) {
            getIntentDataFavotite.setGetIntentDataListener(null);
        }

        travelLocationList = null;
        travelForeignList = null;
        travelGATList = null;
        travelFavotiteList = null;

        RecyclerTravelAdapter.setOnItemClickListener(null);
        RecyclerTravelFavoriteAdapter.setOnItemClickListener(null);
        mCache = null;
    }

}
