package com.weiaibenpao.demo.chislim.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.adater.EquitySpeedAdapter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhangxing on 2017/1/7.
 */

public class SpeedFragment extends Fragment {

    @BindView(R.id.equity_speed)
    TextView equitySpeed;
    @BindView(R.id.fast_speed)
    TextView fastSpeed;
    @BindView(R.id.lower_speed)
    TextView lowerSpeed;
    @BindView(R.id.zoom_layout)
    LinearLayout zoomLayout;
    @BindView(R.id.title_layout)
    LinearLayout titleLayout;
    @BindView(R.id.SpeedRecyclerView)
    RecyclerView SpeedRecyclerView;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.myTop)
    RelativeLayout myTop;
    @BindView(R.id.myBar)
    View myBar;
    private View inflate;
    private Context context;
    private ArrayList<String> mKiloList,mSpeedList;
    private EquitySpeedAdapter adapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        inflate = inflater.inflate(R.layout.speed_get_item, container, false);
        ButterKnife.bind(this, inflate);
        context = getActivity().getApplicationContext();
        mKiloList = new ArrayList<>();
        mSpeedList = new ArrayList<>();
        initData();
        adapter = new EquitySpeedAdapter(context,mKiloList,mSpeedList);
        //设置布局管理器，否则不出数据
        SpeedRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        SpeedRecyclerView.setAdapter(adapter);

        return inflate;
    }

    private void initData() {

        equitySpeed.setText("5'15");
        fastSpeed.setText("7'14");
        lowerSpeed.setText("6'15");
        for(int i = 1; i<20; i++){
            mKiloList.add(i+"");
            mSpeedList.add("0"+i+"'"+"2"+i);
        }

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //initView();

    }


    @OnClick({R.id.equity_speed, R.id.fast_speed, R.id.lower_speed, R.id.SpeedRecyclerView, R.id.back})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                getActivity().finish();
                break;
            default:
                break;
        }
    }
}