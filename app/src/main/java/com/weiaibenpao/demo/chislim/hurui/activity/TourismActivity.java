package com.weiaibenpao.demo.chislim.hurui.activity;

import android.os.Bundle;

import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.hurui.adapte.TourismAdapter;
import com.weiaibenpao.demo.chislim.hurui.bean.TravelBean;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 *
 * 旅游列表
 */

public class TourismActivity extends HBaseActivity {
    public int num = 0;

    @BindView(R.id.tourism_lv)
    PullLoadMoreRecyclerView tourism_lv ;

    @OnClick(R.id.close_iv)
    public void close_iv(){
        finish();
    }



    private List<TravelBean.DataBean.TravelistBean> lists ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourism);
        initPullRecycleView();
        initData();
        initAdapter();
        initAction();
    }

    //设置布局的模式
    private void initPullRecycleView() {
        tourism_lv.setRefreshing(true);
    }

    //适配器
    private void initAdapter() {
        tourism_lv.setLinearLayout();
        tourism_lv.setAdapter(new TourismAdapter(this , lists));
    }


    //发送网络请求
    private void initNetData() {
        Call<TravelBean> call =  apiStores.getTravelService("0","",num+"" , "10");
        call.enqueue(new Callback<TravelBean>() {
            @Override
            public void onResponse(Call<TravelBean> call, Response<TravelBean> response) {
                if(response.isSuccessful()){
                    TravelBean travelBean = response.body() ;
                    if(travelBean.getCode() == 0){
                        //如果为 0 表示刷新或者第一次进入这个页面，需清空列表数据
                        if(num == 0){
                            lists.clear();
                            tourism_lv.setPushRefreshEnable(true);
                        }

                        //访问成功则  页数+1 ；
                        num = num + 1 ;
                        lists.addAll(travelBean.getData().getTravelist());
                        tourism_lv.getRecyclerView().getAdapter().notifyDataSetChanged();

                        //如果列表里面的数据等于总数据则停止加载
                        if(lists.size() >= travelBean.getData().getExtra().getTotalSize()){
                            tourism_lv.setPushRefreshEnable(false);
                        }

                        tourism_lv.setPullLoadMoreCompleted();
                    }
                }
            }

            @Override
            public void onFailure(Call<TravelBean> call, Throwable t) {
                tourism_lv.setPullLoadMoreCompleted();
            }
        });

        addCalls(call);
    }


    //事件
    private void initAction() {
        tourism_lv.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                num = 0 ;
                initNetData();
            }

            @Override
            public void onLoadMore() {
                initNetData();
            }
        });
    }


    public void initData(){
        //tourism_lv.setAdapter(adapter);
        lists = new ArrayList<TravelBean.DataBean.TravelistBean>();
        initNetData();
    }
}
