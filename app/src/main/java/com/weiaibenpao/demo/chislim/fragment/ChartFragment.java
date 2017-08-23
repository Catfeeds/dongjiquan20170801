package com.weiaibenpao.demo.chislim.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.customs.SpeedCustomView;

/**
 * Created by zhangxing on 2017/1/7.
 */

public class ChartFragment extends Fragment {
    private View view;
    private ImageView back;
    SpeedCustomView speedCustomView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.speed_item, container, false);
        speedCustomView = (SpeedCustomView) view.findViewById(R.id.speedpic);
        back = (ImageView) view.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //initView();

    }


}