package com.weiaibenpao.demo.chislim.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.gjiazhe.scrollparallaximageview.parallaxstyle.HorizontalMovingStyle;
import com.gjiazhe.scrollparallaximageview.parallaxstyle.VerticalMovingStyle;
import com.view.jameson.library.CardScaleHelper;
import com.weiaibenpao.demo.chislim.Interface.GetInterfaceVideoListener;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.adater.RecyclerTravelAdapter;
import com.weiaibenpao.demo.chislim.adater.RecyclerTravelFavoriteAdapter;
import com.weiaibenpao.demo.chislim.base.BaseFragment;
import com.weiaibenpao.demo.chislim.bean.LunBoTuBeanResult;
import com.weiaibenpao.demo.chislim.bean.TravelResult;
import com.weiaibenpao.demo.chislim.ui.TravelInfoActivity;
import com.weiaibenpao.demo.chislim.ui.TravelMoreActivity;
import com.weiaibenpao.demo.chislim.util.ACache;
import com.weiaibenpao.demo.chislim.util.GetIntentData;
import com.weiaibenpao.demo.chislim.util.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerClickListener;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/4/22.
 */
public class C_FirstFragment extends BaseFragment  {

    View view;
    static final int REFRESH_COMPLETE = 0X1112;
    SwipeRefreshLayout mSwipeLayout;

    Banner banner;
    String[] images, titles;
    Context context;

    GetIntentData getIntentDataLocation;
    ArrayList travelLocationList;

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
    @BindView(R.id.localHot)
    RecyclerView localHot;
    @BindView(R.id.localTwoHot)
    RecyclerView localTwoHot;
    @BindView(R.id.foreignHot)
    RecyclerView foreignHot;
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
    private ACache mCache;

    RecyclerTravelAdapter mAdapter1;
    RecyclerTravelAdapter mAdapter2;
    RecyclerTravelAdapter mAdapter3;
    RecyclerTravelFavoriteAdapter mAdapter4;
    private CardScaleHelper mCardScaleHelper = null;

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
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initView(view);
        initCatch();
        initData();
        //initRefresh(view);
        initBannerData(2, 1, 8);
    }

    @Override
    public void initView(View view) {
        //localHot.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        localHot.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));

        localTwoHot.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));

        foreignHot.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));

        favotiteHot.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

        //点击事件
        RecyclerTravelAdapter.setOnItemClickListener(new RecyclerTravelAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, ArrayList list) {
                Log.i(TAG, "点击了第" + position + "图片");
                Intent intent = new Intent(getActivity(), TravelInfoActivity.class);
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
                Intent intent = new Intent(getActivity(), TravelInfoActivity.class);
                intent.putExtra("travel", (Parcelable) list.get(position));
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View view, int position, ArrayList list) {

            }
        });
    }

    public void initBannerData(int tab, int i, int n) {
        //获取缓存数据
        LunBoTuBeanResult lunBoTuBeanResult = (LunBoTuBeanResult) mCache.getAsObject("lunBoTuBeanResultTravel");
        if (lunBoTuBeanResult != null) {
            int num = lunBoTuBeanResult.getLunboTuBean().size();
            for (int j = 0; j < num; j++) {
                bunnerList.add((lunBoTuBeanResult.getLunboTuBean().get(j)).getPic_url());
            }
            initBanner(view, bunnerList);
        }

        intentData.getLunBoTu(context, "getAllLunBo", tab, i, n);
        intentData.setGetIntentDataListener(new GetInterfaceVideoListener() {
            @Override
            public void getDateList(ArrayList dateList) {
                int num = dateList.size();
                for (int i = 0; i < num; i++) {
                    bunnerList.add(((LunBoTuBeanResult.LunboTuBeanBean) dateList.get(i)).getPic_url());
                }
                initBanner(view, bunnerList);
            }
        });
    }

    public void initBanner(View view, ArrayList image) {
        /**
         * 如果在当前布局文件就直接使用
         * banner = (Banner) findViewById(R.id.banner);
         */
        banner = (Banner) view.findViewById(R.id.banner);
        //简单使用
        //banner.setImages(Arrays.asList(images)).setImageLoader(new GlideImageLoader()).start();

        //设置banner样式
        banner.setBannerStyle(BannerConfig.NOT_INDICATOR);
        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        banner.setImages(image);
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.DepthPage);
        //设置标题集合（当banner样式有显示title时）
        banner.setBannerTitles(Arrays.asList(titles));
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(6000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);
        //banner设置方法全部调用完毕时最后调用
        banner.start();
/*        banner.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_MOVE:
                        mSwipeLayout.setEnabled(false);
                        break;
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_CANCEL:
                        mSwipeLayout.setEnabled(true);
                        break;
                }
                return false;
            }
        });*/
        banner.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {
                //Toast.makeText(getActivity().getApplicationContext(), "点击：" + position, Toast.LENGTH_SHORT).show();

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
        getDate(context, "getAllTravel", "favotite", 1, 6);
    }

    /**
     * 获取缓存数据
     */
    public void initCatch() {
        //获取缓存数据
        TravelResult travelResultlocation = (TravelResult) mCache.getAsObject("travelNotesResultlocation");
        if (travelResultlocation != null) {     //如果travelResultlocation为空   则说明无缓存
            if (mAdapter1 == null) {           //如果适配器等于空，说明是第一次刚进入app，页面无数据展示
                mAdapter1 = new RecyclerTravelAdapter(getActivity(), (ArrayList) travelResultlocation.getTravel(),new HorizontalMovingStyle(), 6);
                localHot.setAdapter(mAdapter1);
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
                mAdapter2 = new RecyclerTravelAdapter(getActivity(), (ArrayList) travelResulttaiwan.getTravel(),new HorizontalMovingStyle(), 3);
                localTwoHot.setAdapter(mAdapter2);
                // mRecyclerView绑定scale效果
                mCardScaleHelper = new CardScaleHelper();
                mCardScaleHelper.setCurrentItemPos(2);
                mCardScaleHelper.attachToRecyclerView(localTwoHot);
            }
        }
        //获取缓存数据
        TravelResult travelResultfavotite = (TravelResult) mCache.getAsObject("travelNotesResultfavotite");
        if (travelResulttaiwan != null) {
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
                getIntentDataLocation = new GetIntentData();
                getIntentDataLocation.getTravel(context, dowhat, tab, i, num);
                getIntentDataLocation.setGetIntentDataListener(new GetInterfaceVideoListener() {
                    @Override
                    public void getDateList(ArrayList dateList) {
                        if (dateList.size() > 0 && mAdapter1 == null) {
                            mAdapter1 = new RecyclerTravelAdapter(getActivity(), dateList,new HorizontalMovingStyle(), 6);
                            localHot.setAdapter(mAdapter1);

                        } else if(dateList.size() > 0 && mAdapter1 != null){
                            mAdapter1.changeMoreData(dateList);
                        }
                    }
                });
                break;
            case "foreign":
                getIntentDataForeign = new GetIntentData();
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
                        } else if(dateList.size() > 0 && mAdapter3 != null) {
                            mAdapter3.changeMoreData(dateList);
                        }
                    }
                });
                break;
            case "taiwan":
                getIntentDataGAT = new GetIntentData();
                getIntentDataGAT.getTravel(context, dowhat, tab, i, num);
                getIntentDataGAT.setGetIntentDataListener(new GetInterfaceVideoListener() {
                    @Override
                    public void getDateList(ArrayList dateList) {
                        if (dateList.size() > 0 && mAdapter2 == null) {
                            mAdapter2 = new RecyclerTravelAdapter(getActivity(), dateList,new HorizontalMovingStyle() ,3);
                            localTwoHot.setAdapter(mAdapter2);
                            // mRecyclerView绑定scale效果
                            mCardScaleHelper = new CardScaleHelper();
                            mCardScaleHelper.setCurrentItemPos(2);
                            mCardScaleHelper.attachToRecyclerView(localTwoHot);
                        } else if(dateList.size() > 0 && mAdapter2 != null) {
                            mAdapter2.changeMoreData(dateList);
                        }
                    }
                });
                break;
            case "favotite":
                getIntentDataFavotite = new GetIntentData();
                getIntentDataFavotite.getTravel(context, dowhat, tab, i, num);
                getIntentDataFavotite.setGetIntentDataListener(new GetInterfaceVideoListener() {
                    @Override
                    public void getDateList(ArrayList dateList) {
                        if (dateList.size() > 0 && mAdapter4 == null) {
                            mAdapter4 = new RecyclerTravelFavoriteAdapter(getActivity(), dateList, new VerticalMovingStyle(),3);
                            favotiteHot.setAdapter(mAdapter4);
                        } else if(dateList.size() > 0 && mAdapter4 != null){
                            mAdapter4.changeMoreData(dateList);
                        }
                    }
                });
                break;
        }
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
}
