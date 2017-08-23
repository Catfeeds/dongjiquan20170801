package com.weiaibenpao.demo.chislim.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.weiaibenpao.demo.chislim.R;

public class TraceFragment extends Fragment {

    public static final String RESID_KEY = "HELLO";
    private View view;
    private ImageView map;
    private  String res;
    private ImageView back;

    public static TraceFragment newInstance(String str) {

        Bundle args = new Bundle();
        args.putString(RESID_KEY, str);
        TraceFragment fragment = new TraceFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            Bundle bundle = getArguments();
            res = bundle.getString(RESID_KEY);
        }
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.trace_item, container, false);
         map = (ImageView) view.findViewById(R.id.trace_map);
         back = (ImageView) view.findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().finish();
            }
        });

       Picasso.with(getActivity())
                .load(res)
                .error(R.mipmap.zhanwei)
                .placeholder(R.mipmap.zhanwei)
                .into(map);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //initView();

    }


    }

