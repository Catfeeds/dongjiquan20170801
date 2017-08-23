package com.weiaibenpao.demo.chislim.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.weiaibenpao.demo.chislim.Interface.GetInterfaceBooleanListener;
import com.weiaibenpao.demo.chislim.Interface.GetInterfaceIntIdListener;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.adater.RecyclerMarkAdapter;
import com.weiaibenpao.demo.chislim.bean.UserBean;
import com.weiaibenpao.demo.chislim.util.GetIntentData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.AnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInBottomAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInLeftAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.SlideInRightAnimationAdapter;

public class MarkInfoActivity extends Activity {


    RecyclerView markRecycler;
    ImageView back;
    TextView guize;
    TextView getMark;
    TextView myMark;
    GetIntentData getIntentData;

    int nowMark;
    public static final String PREFS_NAME = "UserInfo";
    @BindView(R.id.activity_mark_info)
    RelativeLayout activityMarkInfo;
    @BindView(R.id.choujiangTv)
    TextView choujiangTv;
    @BindView(R.id.duihuan_layout)
    LinearLayout duihuanLayout;

    private SharedPreferences settings;

    @OnClick({R.id.choujiangTv, R.id.duihuan_layout})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.choujiangTv:
                Intent intent = new Intent(MarkInfoActivity.this, GetByCreditsActivity.class);
                intent.putExtra("nowMark",nowMark);
                startActivity(intent);
                break;
            case R.id.duihuan_layout:
                Intent intent1 = new Intent(MarkInfoActivity.this, GetByCreditsActivity.class);
                startActivity(intent1);
                break;
        }
    }


   /* @OnClick({R.id.duihuan_layout,R.id.choujiangTv})
    public void onClick() {
        Intent intent = new Intent(MarkInfoActivity.this, GetByCreditsActivity.class);
        startActivity(intent);
    }*/


    enum Type {
        AlphaIn {
            @Override
            public AnimationAdapter get(Context context) {
                RecyclerMarkAdapter adapter = new RecyclerMarkAdapter(context, 15);
                return new AlphaInAnimationAdapter(adapter);
            }
        },
        ScaleIn {
            @Override
            public AnimationAdapter get(Context context) {
                RecyclerMarkAdapter adapter = new RecyclerMarkAdapter(context, 15);
                return new ScaleInAnimationAdapter(adapter);
            }
        },
        SlideInBottom {
            @Override
            public AnimationAdapter get(Context context) {
                RecyclerMarkAdapter adapter = new RecyclerMarkAdapter(context, 15);
                return new SlideInBottomAnimationAdapter(adapter);
            }
        },
        SlideInLeft {
            @Override
            public AnimationAdapter get(Context context) {
                RecyclerMarkAdapter adapter = new RecyclerMarkAdapter(context, 15);
                return new SlideInLeftAnimationAdapter(adapter);
            }
        },
        SlideInRight {
            @Override
            public AnimationAdapter get(Context context) {
                RecyclerMarkAdapter adapter = new RecyclerMarkAdapter(context, 15);
                return new SlideInRightAnimationAdapter(adapter);
            }
        };

        public abstract AnimationAdapter get(Context context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_info);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this , getResources().getColor(R.color.ku_bg ) , 0);
        settings = getSharedPreferences(PREFS_NAME, 0);

        //用户积分
        myMark = (TextView) findViewById(R.id.myMark);

        getIntentData = new GetIntentData();
        initData();         //查询用户积分
        initView();
        initRecycleCiew();

        /*markRecycler.setLayoutManager(new LinearLayoutManager(this));
        RecyclerFriendAdapter mAdapter1 = new RecyclerFriendAdapter(this,15);
        markRecycler.setAdapter(mAdapter1);*/

        //返回
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        //查看规则
        guize = (TextView) findViewById(R.id.guize);
        guize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MarkInfoActivity.this, MarkActivity.class);
                startActivity(intent);
            }
        });
    }


    public void initView() {
        //用户签到
        getMark = (TextView) findViewById(R.id.getMark);
        getMark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getIntentData.updateMark(UserBean.getUserBean().userId, 10);
                getIntentData.setGetInterfaceBooleanListener(new GetInterfaceBooleanListener() {
                    @Override
                    public void getBoolean(boolean flag) {
                        if (flag) {
                            nowMark = nowMark + 10;
                            myMark.setText(nowMark + "");
                            //获得SharedPreferences.Editor对象，使SharedPreferences对象变为可编辑状态（生成编辑器）
                            SharedPreferences.Editor edit = settings.edit();

                            edit.putInt("userMark", nowMark);
                            //提交
                            edit.commit();
                        }
                    }
                });
            }
        });
    }

    public void initRecycleCiew() {
        markRecycler = (RecyclerView) findViewById(R.id.markRecycler);

        markRecycler.setLayoutManager(new LinearLayoutManager(this));
        AnimationAdapter adapter = Type.values()[2].get(MarkInfoActivity.this);
        adapter.setFirstOnly(false);
        adapter.setDuration(500);
        adapter.setInterpolator(new OvershootInterpolator(.5f));
        markRecycler.setAdapter(adapter);
    }

    public void initData() {
        getIntentData.getUserMark(UserBean.getUserBean().userId);
        getIntentData.setGetInterfaceIntIdListener(new GetInterfaceIntIdListener() {
            @Override
            public void getDateIntId(int intId) {
                nowMark = intId;
                myMark.setText(intId + "");
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
