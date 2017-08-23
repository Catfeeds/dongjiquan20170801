package com.weiaibenpao.demo.chislim.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.weiaibenpao.demo.chislim.Li_RecyclerViewCardGallery.CardAdapter;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;


/**
 * Created by Administrator on 2016/4/22.
 */
public class SportFragment extends BaseFragment {



    Context context;
    private RecyclerView mRecyclerView;
    private List<Integer> mList;
    View view;
    LinearLayoutManager linearLayoutManager;
    CardAdapter cardAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(view == null){
            view = inflater.inflate(R.layout.fragment_sport, container, false);
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if(parent != null){
            parent.removeView(view);
        }

      //  view = inflater.inflate(R.layout.fragment_sport, null);
        ButterKnife.bind(this, view);
        context = getActivity();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        init();

        return view;
    }

    //todo  下边为横向滑动
    private void init() {
        if(mList == null){
            mList = new ArrayList<>();
            mList.add(R.mipmap.biaozhun);
            mList.add(R.mipmap.tuxin);
            //mList.add(R.mipmap.sport_img);
            mList.add(R.mipmap.xingzheng);
        }

        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);

        mRecyclerView.setLayoutManager(linearLayoutManager);

        if(cardAdapter == null){
            cardAdapter = new CardAdapter(context,mList);
        }

        mRecyclerView.setAdapter(cardAdapter);
        // mRecyclerView绑定scale效果
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

    }
}
