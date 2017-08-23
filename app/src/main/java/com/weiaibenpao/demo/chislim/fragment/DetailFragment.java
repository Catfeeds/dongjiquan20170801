package com.weiaibenpao.demo.chislim.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.weiaibenpao.demo.chislim.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by zhangxing on 2017/1/7.
 */

public class DetailFragment extends Fragment {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.mySportTime)
    TextView mySportTime;
    @BindView(R.id.myStep)
    TextView myStep;
    @BindView(R.id.myDis)
    TextView myDis;
    @BindView(R.id.myTime)
    TextView myTime;
    @BindView(R.id.mySped)
    TextView mySped;
    @BindView(R.id.myCalior)
    TextView myCalior;
    private View view;

    String sportTime;
    String sportDistance;
    String calories;
    String daytime;
    String sportSped;
    String sportStep;

    public static final String SPORTTIME = "2016-12-13";
    public static final String SPORTDISTANCE = "200";
    public static final String CALORIES = "24";
    public static final String DAYTIME = "2008";
    public static final String SPORTSPED = "30M/S";
    public static final String SPORTSTEP = "29";

    public static DetailFragment newInstance(String str) {

        Bundle args = new Bundle();
        args.putString(SPORTTIME, str);
        args.putString(SPORTDISTANCE,str);
        args.putString(CALORIES,str);
        args.putString(DAYTIME,str);
        args.putString(SPORTSPED,str);
        args.putString(SPORTSTEP,str);

        DetailFragment fragment = new DetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            Bundle bundle = getArguments();
            sportTime = bundle.getString(SPORTTIME);
            sportDistance = bundle.getString(SPORTDISTANCE);
            calories = bundle.getString(CALORIES);
            daytime = bundle.getString(DAYTIME);
            sportSped = bundle.getString(SPORTSPED);
            sportStep = bundle.getString(SPORTSTEP);

        }
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.showmap_layout, container, false);
        ButterKnife.bind(this, view);
        initview();
        initAction();
        return view;
    }

    private void initAction() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });
    }

    private void initview() {
        mySportTime.setText(daytime);
        myDis.setText(sportDistance);
        myCalior.setText(calories);
        myTime.setText(sportTime);
        mySped.setText(sportSped);
        myStep.setText(sportStep);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);


    }


}
