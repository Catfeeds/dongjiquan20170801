package com.weiaibenpao.demo.chislim.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.gjiazhe.scrollparallaximageview.parallaxstyle.VerticalMovingStyle;
import com.jaeger.library.StatusBarUtil;
import com.weiaibenpao.demo.chislim.Interface.GetInterfaceVideoListener;
import com.weiaibenpao.demo.chislim.Interface.GetObjectListener;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.adater.CollectAdapter;
import com.weiaibenpao.demo.chislim.bean.GetCollectionResult;
import com.weiaibenpao.demo.chislim.bean.UserBean;
import com.weiaibenpao.demo.chislim.util.ACache;
import com.weiaibenpao.demo.chislim.util.GetIntentData;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MyCollectActivity extends Activity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.myCollect)
    RecyclerView myCollect;

    GetIntentData getIntentData;
    Context context;
    LinearLayoutManager layoutManager;
    CollectAdapter collectAdapter;
    private ACache mCache;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_collect);
        StatusBarUtil.setColor(this , getResources().getColor(R.color.ku_bg ) , 0);
        ButterKnife.bind(this);
        context = getApplicationContext();
        mCache = ACache.get(context);

        initView();
        initCache();
        initData();
        initRecycleView();
    }

    public void initData(){
        getIntentData = new GetIntentData();
        getIntentData.getCollection(context, UserBean.getUserBean().userId);
        getIntentData.setGetObjectListener(new GetObjectListener() {
            @Override
            public void getObject(Object object) {
                collectAdapter = new CollectAdapter(context, (ArrayList) ((GetCollectionResult.DataBean)object).getCollectionlist(),new VerticalMovingStyle());
                myCollect.setAdapter(collectAdapter);
            }
        });
    }

    public void initView(){
        layoutManager = new LinearLayoutManager(this);         //竖线列表
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        myCollect.setLayoutManager(layoutManager);
    }

    //点击recyclerview的item实现的跳转逻辑
    public void initRecycleView(){

        CollectAdapter.setOnItemClickListener(new CollectAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, ArrayList talkList) {
                ((GetCollectionResult.DataBean.CollectionlistBean)(talkList.get(position))).getTitle();
                getIntentData.getTheme(context,((GetCollectionResult.DataBean.CollectionlistBean)(talkList.get(position))).getTitle(),
                        UserBean.getUserBean().userId,0,1);

                getIntentData.setGetIntentDataListener(new GetInterfaceVideoListener() {
                    @Override
                    public void getDateList(ArrayList dateList) {

                        /*Intent intent = new Intent(MyCollectActivity.this, ThemeActivity.class);
                        intent.putExtra("theme", (Parcelable) dateList.get(0));
                        startActivity(intent);*/
                    }
                });



            }

            @Override
            public void onItemLongClick(View view, int position, ArrayList list) {

            }
        });

    }

    @OnClick(R.id.back)
    public void onClick() {
        finish();
    }
    /**
     * 获取缓存数据
     * mCache.put("humor" + id,result.initData());
     */
    public void initCache() {
       GetCollectionResult.DataBean collectionlistBean = (GetCollectionResult.DataBean) mCache.getAsObject("collection");
        if (collectionlistBean != null) {
            collectAdapter = new CollectAdapter(context, (ArrayList) collectionlistBean.getCollectionlist(),new VerticalMovingStyle());
            //收藏适配
            myCollect.setAdapter(collectAdapter);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        collectAdapter.setOnItemClickListener(null);
    }
}
