package com.weiaibenpao.demo.chislim.hurui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.hurui.activity.RunningMachineActivity;
import com.weiaibenpao.demo.chislim.hurui.activity.TourismActivity;
import com.weiaibenpao.demo.chislim.hurui.base.HBaseFragment;
import com.weiaibenpao.demo.chislim.hurui.bean.BannerItemBean;
import com.weiaibenpao.demo.chislim.hurui.bean.FlexibleBean;
import com.weiaibenpao.demo.chislim.hurui.utils.Utils;
import com.weiaibenpao.demo.chislim.ui.MusicTypeActivity;
import com.weiaibenpao.demo.chislim.util.GlideImageLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerClickListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by lenovo on 2017/4/10.
 */

public class HR_Frist_Fragment extends HBaseFragment {


    private View rootView ;

    private Banner banner ; //轮播图

    private LinearLayout gotoRunmacheine ; //去跑步机的界面

    private LinearLayout gotoMusic ; //去音乐的界面

    private LinearLayout gotoTourism ; // 旅游的界面

    private List<FlexibleBean.DataBean.ActivitylistBean> lists ; // 活动数据

    private List<BannerItemBean.DataBean.ImgCastinglistBean> bannlists ; //banner数据

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if(rootView == null){
            rootView  = inflater.inflate(R.layout.hr_frist_fragment_2 , null);
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView(view);

    }

    private void initView(View view) {
        banner=((Banner) view.findViewById(R.id.banner_first));
        gotoRunmacheine = (LinearLayout) view.findViewById(R.id.gotoRunmacheine);
        gotoMusic = (LinearLayout) view.findViewById(R.id.gotoMusic);
        gotoTourism = (LinearLayout)view.findViewById(R.id.gotoTourism);

    }


    @Override
    public void onStart() {
        super.onStart();
        initData();
        initEvent();
        banner.startAutoPlay();
    }

    /**
     * 网络请求数据
     */
    private void initData() {
        initBannerData();
//        initActivityData();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
        banner.stopAutoPlay();
    }

    /**
     * 轮播图的网络请求
     */
    private void initBannerData() {

        banner.setImageLoader(new GlideImageLoader());
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.ScaleInOut);
        //设置标题集合（当banner样式有显示title时）
        //banner.setBannerTitles(Arrays.asList(titles));
        //设置自动轮播，默认为true
        banner.isAutoPlay(true);
        //设置轮播时间
        banner.setDelayTime(4000);
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER);

        Call<BannerItemBean> call = apiStores.getBannerImageService("","1");
        call.enqueue(new Callback<BannerItemBean>() {
            @Override
            public void onResponse(Call<BannerItemBean> call, Response<BannerItemBean> response) {
                if(response.isSuccessful()){
                    BannerItemBean bannerbean = response.body() ;
                    bannlists = bannerbean.getData().getImgCastinglist() ;
                    List<String> imageUrl = new ArrayList<String>();
                    for(BannerItemBean.DataBean.ImgCastinglistBean b :
                            bannerbean.getData().getImgCastinglist()){
                        imageUrl.add(b.getImgCastingImgUrl());
                    }
                    banner.setImages(imageUrl);
                    banner.start();
                }
            }

            @Override
            public void onFailure(Call<BannerItemBean> call, Throwable t) {

            }
        });

        addCalls(call);
    }
//
//    /**
//     * 活动数据
//     */
//    public void initActivityData(){
//        Call<FlexibleBean> call = apiStores.getFlexibleBeanService("0" , "");
//        call.enqueue(new Callback<FlexibleBean>() {
//            @Override
//            public void onResponse(Call<FlexibleBean> call, Response<FlexibleBean> response) {
//                if(response.isSuccessful()){
//                    FlexibleBean bean = response.body() ;
//                    if(bean.getCode() == 0){
//                        lists = bean.getData().getActivitylist();
//
//                    }
//                }
//            }
//
//            @Override
//            public void onFailure(Call<FlexibleBean> call, Throwable t) {
//
//            }
//        });
//    }

    //事件
    private void initEvent() {
        gotoRunmacheine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity() , RunningMachineActivity.class) ;
                startActivity(intent);
            }
        });

        gotoTourism.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity() , TourismActivity.class);
                startActivity(intent);
            }
        });

        gotoMusic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity() , MusicTypeActivity.class);
                startActivity(intent);
            }
        });

        banner.setOnBannerClickListener(new OnBannerClickListener() {
            @Override
            public void OnBannerClick(int position) {
                Utils.gotoTmailShop(getActivity() ,
                        bannlists.get(position - 1).getImgCastingTitle() , bannlists.get(position - 1).getImgCastingContentUrl());
            }
        });

    }
}
