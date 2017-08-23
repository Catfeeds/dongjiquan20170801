package com.weiaibenpao.demo.chislim.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.jaeger.library.StatusBarUtil;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.adater.SportHomeTypeAdapter;
import com.weiaibenpao.demo.chislim.bean.SportTypeBean;
import com.weiaibenpao.demo.chislim.util.Default;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.AnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInRightAnimationAdapter;

public class SportHomeTypeActivity extends Activity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.sportHomeType)
    RecyclerView sportHomeType;
    @BindView(R.id.muLayout)
    LinearLayout muLayout;

    @OnClick({R.id.back, R.id.muLayout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.muLayout:            //跳转到图形跑选择图形界面
                Intent intent = new Intent(SportHomeTypeActivity.this, FavouritePicActivity.class);
                startActivity(intent);
                break;
        }
    }

    enum Type {
        AlphaIn {
            @Override
            public AnimationAdapter get(Context context) {
                SportHomeTypeAdapter sportHomeTypeAdapter = new SportHomeTypeAdapter(context, Default.getSportTypeHome(), 6);
                return new AlphaInAnimationAdapter(sportHomeTypeAdapter);
            }
        },
        ScaleIn {
            @Override
            public AnimationAdapter get(Context context) {
                SportHomeTypeAdapter sportHomeTypeAdapter = new SportHomeTypeAdapter(context, Default.getSportTypeHome(), 6);
                return new ScaleInAnimationAdapter(sportHomeTypeAdapter);
            }
        },
        SlideInBottom {
            @Override
            public AnimationAdapter get(Context context) {
                SportHomeTypeAdapter sportHomeTypeAdapter = new SportHomeTypeAdapter(context, Default.getSportTypeHome(), 6);
                return new SlideInBottomAnimationAdapter(sportHomeTypeAdapter);
            }
        },
        SlideInLeft {
            @Override
            public AnimationAdapter get(Context context) {
                SportHomeTypeAdapter sportHomeTypeAdapter = new SportHomeTypeAdapter(context, Default.getSportTypeHome(), 6);
                return new SlideInLeftAnimationAdapter(sportHomeTypeAdapter);
            }
        },
        SlideInRight {
            @Override
            public AnimationAdapter get(Context context) {
                SportHomeTypeAdapter sportHomeTypeAdapter = new SportHomeTypeAdapter(context, Default.getSportTypeHome(), 6);
                return new SlideInRightAnimationAdapter(sportHomeTypeAdapter);
            }
        };

        public abstract AnimationAdapter get(Context context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sport_home_type);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this , getResources().getColor(R.color.ku_bg ) , 0);
        sportHomeType.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        AnimationAdapter adapter = Type.values()[2].get(SportHomeTypeActivity.this);
        adapter.setFirstOnly(false);
        adapter.setDuration(500);
        adapter.setInterpolator(new OvershootInterpolator(.5f));
        sportHomeType.setAdapter(adapter);

        SportHomeTypeAdapter.setOnItemClickListener(new SportHomeTypeAdapter.OnItemClickListener() {

            @Override
            public void onItemClick(View view, SportTypeBean sportTypeBean) {
                Intent intent = new Intent(SportHomeTypeActivity.this, SportHomeActivity.class);
                intent.putExtra("sportTypeBean", sportTypeBean);
                startActivity(intent);
                finish();
            }

            @Override
            public void onItemLongClick(View view, SportTypeBean sportTypeBean) {

            }
        });
    }

}
