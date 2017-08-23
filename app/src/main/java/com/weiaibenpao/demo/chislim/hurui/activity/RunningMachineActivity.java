package com.weiaibenpao.demo.chislim.hurui.activity;

import android.os.Bundle;

import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.hurui.adapte.RunMachineAdapter;
import com.weiaibenpao.demo.chislim.hurui.bean.RunMachineBean;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 跑步机列表
 */
public class RunningMachineActivity extends HBaseActivity {
    public int num = 0 ;

    @BindView(R.id.runmachine_lv)
    PullLoadMoreRecyclerView runmacheine_lv ;

    @OnClick(R.id.close_iv)
    void close_iv(){
        finish();
    }

    private List<RunMachineBean.DataBean.TreadmillBean> lists ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_running_machine);
        initPullRecycleView();
        initData();
        initAdapter();
        initAction();
    }


    /**
     * 给pullrecycleView进行设置
     */
    private void initPullRecycleView() {
        runmacheine_lv.setRefreshing(true);
    }

    private void initData() {
        lists = new ArrayList<RunMachineBean.DataBean.TreadmillBean>();
        initNetData();
    }



    private void initAction() {
        runmacheine_lv.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
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


    //发送网络请求请求数据
    public void initNetData(){
        Call<RunMachineBean> call =  apiStores.getToursimService(num+"" , "20");
        call.enqueue(new Callback<RunMachineBean>() {
            @Override
            public void onResponse(Call<RunMachineBean> call, Response<RunMachineBean> response) {
                if(response.isSuccessful()){
                    RunMachineBean toursimBean = response.body() ;
                    if(toursimBean.getCode() == 0){
                        //如果为 0 表示刷新或者第一次进入这个页面，需清空列表数据
                        if(num == 0){
                            lists.clear();
                            runmacheine_lv.setPushRefreshEnable(true);
                        }

                        //访问成功则  页数+1 ；
                        num += 1 ;
                        lists.addAll(toursimBean.getData().getTreadmill());
                        runmacheine_lv.getRecyclerView().getAdapter().notifyDataSetChanged();

                        //如果列表里面的数据等于总数据则停止加载
                        if(lists.size() >= toursimBean.getData().getExtra().getTotalSize()){
                            runmacheine_lv.setPushRefreshEnable(false);
                        }

                        runmacheine_lv.setPullLoadMoreCompleted();
                    }
                }
            }

            @Override
            public void onFailure(Call<RunMachineBean> call, Throwable t) {
                runmacheine_lv.setPullLoadMoreCompleted();
            }
        });

        addCalls(call);
    }

    private void initAdapter() {
        runmacheine_lv.setLinearLayout();
        runmacheine_lv.setAdapter(new RunMachineAdapter(this , lists));
    }

}
