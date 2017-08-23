package com.weiaibenpao.demo.chislim.hurui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.weiaibenpao.demo.chislim.Interface.GetObjectListener;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.bean.Li_ReportComment_Result;
import com.weiaibenpao.demo.chislim.bean.Li_UserInfoResult;
import com.weiaibenpao.demo.chislim.bean.Li_funsResult;
import com.weiaibenpao.demo.chislim.bean.Moment;
import com.weiaibenpao.demo.chislim.bean.UserBean;
import com.weiaibenpao.demo.chislim.hurui.adapte.UserDeailAdapter;
import com.weiaibenpao.demo.chislim.hurui.bean.MakePraiseBean;
import com.weiaibenpao.demo.chislim.hurui.httpClient.ApiClient;
import com.weiaibenpao.demo.chislim.hurui.httpClient.HHttpInterface;
import com.weiaibenpao.demo.chislim.map.customview.CircleImageView;
import com.weiaibenpao.demo.chislim.model.MyModel;
import com.weiaibenpao.demo.chislim.util.GetIntentData;
import com.weiaibenpao.demo.chislim.util.Li_Dialog_View;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.util.List;

import jameson.io.library.util.ToastUtils;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class UserDetailActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String USER_ID = "user_id";

    int userId = 0;   //要查用户的id，不是自己的

    private CircleImageView mIcon_iv;
    private TextView mAbout_tv;
    private TextView mFans_tv;
    private TextView mNick_tv;
    private ImageView mSex_img;
    private TextView mLevel_tv;
    private CheckBox mAdd_about_tv;
    private TextView mTitle_tv;
    private TextView mSignature_tv;
    private PullLoadMoreRecyclerView mPhotoRecyclerView;
    Context context = this;
    private int nowUserId;
    private UserDeailAdapter userDeailAdapter;
    private int num=0;
    private Toolbar mToolBar;
    private View view;
    private int eventY;
    private TextView title_tv;

    public HHttpInterface apiStores = ApiClient.getModel().getService();
    List<Moment> humorList;

    int hummerid;
    String is_us;
    Li_Dialog_View li_dialog_view = new Li_Dialog_View();


    GetIntentData getIntentData = new GetIntentData();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_deail);
        //透明状态栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);


        userId = getIntent().getIntExtra(USER_ID, 0);
        initAdapter();
        getData();
        initAction();
    }

    public static void openActivity(Context context, int id) {
        Intent intent = new Intent(context, UserDetailActivity.class);
        intent.putExtra(UserDetailActivity.USER_ID, id);
        context.startActivity(intent);
    }

    private void initAdapter() {

        mPhotoRecyclerView = (PullLoadMoreRecyclerView) findViewById(R.id.photoRecyclerView);
        initView();
        mToolBar = ((Toolbar) findViewById(R.id.tool_bar));
        findViewById(R.id.tool_back).setOnClickListener(this);
        title_tv = ((TextView) findViewById(R.id.toolbar_title_tv));
        userDeailAdapter = new UserDeailAdapter(context);
        mPhotoRecyclerView.setLinearLayout();
        userDeailAdapter.addHeadViewId(view);
        mPhotoRecyclerView.setAdapter(userDeailAdapter);
        mPhotoRecyclerView.setRefreshing(false);
        mPhotoRecyclerView.setPullRefreshEnable(false);


    }

    private void initView() {
         view = LayoutInflater.from(context).inflate(R.layout.user_detial_head_view, mPhotoRecyclerView,false);
        mIcon_iv = (CircleImageView) view.findViewById(R.id.icon_iv);
        mAbout_tv = (TextView)view.findViewById(R.id.about_tv);
        mFans_tv = (TextView) view.findViewById(R.id.fans_tv);
        mNick_tv = (TextView) view.findViewById(R.id.nick_tv);
        mSex_img = (ImageView)view.findViewById(R.id.sex_img);
        mLevel_tv = (TextView) view.findViewById(R.id.level_tv);
        mAdd_about_tv = (CheckBox)view.findViewById(R.id.add_about_tv);
        mTitle_tv = (TextView) view.findViewById(R.id.title_tv);
        mSignature_tv = (TextView) view.findViewById(R.id.signature_tv);


    }

    @Override
    protected void onStart() {
        super.onStart();
        nowUserId=UserBean.getUserBean().userId;

        mPhotoRecyclerView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
//                num=0;
//                getData();


            }

            @Override
            public void onLoadMore() {
                num++;
                getData();
                mPhotoRecyclerView.setPullLoadMoreCompleted();
            }
        });

        RecyclerView recyclerView = mPhotoRecyclerView.getRecyclerView();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                eventY += dy;
                if (dy > 0) {
                    if (eventY > view.getHeight()) {
                        title_tv.setText(mNick_tv.getText().toString());
                        mToolBar.setBackgroundResource(R.mipmap.img_bg);
                        title_tv.setVisibility(View.VISIBLE);
                    }

                } else if (dy < 0) {
                    if (eventY == 0 || eventY < 0) {
                        title_tv.setVisibility(View.GONE);
                        mToolBar.setBackgroundResource(0);

                    }
                }
            }
        });
    }

    private void getData() {
        MyModel.getModel().getService().getUserInfo(userId, nowUserId, num + "", "20").enqueue(new Callback<Li_UserInfoResult>() {
            @Override
            public void onResponse(Call<Li_UserInfoResult> call, Response<Li_UserInfoResult> response) {
                if (response.body() != null) {
                    if (response.body().getMsg().equals("成功")) {

                        Li_UserInfoResult.DataBean dataBean = response.body().getData();
                        initData(dataBean);
                        humorList = dataBean.getHumorlist();
                        Log.e("wlx11", "dataBean: " + humorList.size());

                        userDeailAdapter.refresh(humorList);
                    }
                } else
                    ToastUtils.show(context, "数据请求失败");

            }

            @Override
            public void onFailure(Call<Li_UserInfoResult> call, Throwable t) {
                ToastUtils.show(context,t.getMessage());
            }
        });

    }

    /**
     * 初始化用户信息
     *
     * @param data
     */
    private void initData(Li_UserInfoResult.DataBean data) {
        Picasso.with(context).load(data.getUser().getUserImage()).error(R.mipmap.boy).placeholder(R.mipmap.boy).into(mIcon_iv);
        mAbout_tv.setText(data.getCares()+"");//关注数
        mFans_tv.setText(data.getFanscount()+"");//粉丝数
        mNick_tv.setText(data.getUser().getUserName());//昵称
        //性别
        if (data.getUser().getUserSex().equals("男")){
            mSex_img.setBackgroundResource(R.mipmap.user_sex_boy);
        }else if (data.getUser().getUserSex().equals("女")){
            mSex_img.setBackgroundResource(R.mipmap.user_sex_girl);

        }
        //等级
        mLevel_tv.setText("Lv"+data.getGradeLevel());
        //是否关注
        Log.e("wlx32", "initData: "+data.getIsFans() );
        if (data.getIsFans().equals("请关注")){
            mAdd_about_tv.setText("+ 关 注");
            mAdd_about_tv.setChecked(false);
        } else  if(data.getIsFans().equals("已关注")){

            mAdd_about_tv.setText(data.getIsFans());
            mAdd_about_tv.setChecked(true);
            Log.e("wlx122", "initData: "+mAdd_about_tv.isChecked() );
        }

        mAdd_about_tv.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                requestFan();
                if (!isChecked){
                    mAdd_about_tv.setText("+ 关 注");
                    mFans_tv.setText(Integer.parseInt(mFans_tv.getText().toString())-1+"");
                    mAbout_tv.setText(Integer.parseInt(mAbout_tv.getText().toString())-1+"");
                }else{
                    mAdd_about_tv.setText("已 关 注");
                    mAbout_tv.setText(Integer.parseInt(mAbout_tv.getText().toString())+1+"");
                    mFans_tv.setText(Integer.parseInt(mFans_tv.getText().toString())+1+"");
                }

            }
        });

        mTitle_tv.setText(data.getName());
        mSignature_tv.setText(data.getUser().getUserIntru());
    }

    private void requestFan() {
        MyModel.getModel().getService().isFans(nowUserId,userId).enqueue(new Callback<Li_funsResult>() {
            @Override
            public void onResponse(Call<Li_funsResult> call, Response<Li_funsResult> response) {
                int code = response.body().getCode();
                if (code==0){
                    ToastUtils.show(context,response.body().getMsg());
                }
            }

            @Override
            public void onFailure(Call<Li_funsResult> call, Throwable t) {

            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tool_back:
                finish();
                break;
        }

    }


    public void initAction(){
        userDeailAdapter.setMyItemClick(new UserDeailAdapter.MyItemClick() {
            @Override
            public void dianzhan(final int postion) {
                Log.i("点赞","" + postion);
                Call<MakePraiseBean> call = apiStores.getMakePraiseService(UserBean.getUserBean().userId , humorList.get(postion).getId());
                call.enqueue(new Callback<MakePraiseBean>() {
                    @Override
                    public void onResponse(Call<MakePraiseBean> call, Response<MakePraiseBean> response) {
                        if(response.isSuccessful()){
                            MakePraiseBean bean = response.body() ;
                            if(bean.getCode() == 0){
                                if(bean.getMsg().equals("点赞成功")) {
                                    humorList.get(postion).setIsLike("YES");
                                    humorList.get(postion).setLikeNum(humorList.get(postion).getLikeNum() + 1);
                                }else{
                                    humorList.get(postion).setIsLike("NO");
                                    humorList.get(postion).setLikeNum(humorList.get(postion).getLikeNum() - 1);
                                }
                                userDeailAdapter.notifyItemChanged(postion+1);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<MakePraiseBean> call, Throwable t) {

                    }
                });
            }

            @Override
            public void jubao_shanchu(int hummer_id, String isus) {
                hummerid = hummer_id;
                is_us = isus;
                //OK  说明是自己的
                //Log.i("测试",isus);
                if("OK".equals(is_us)){
                    li_dialog_view.delHummer(context);

                }else {  //说明是别人的
                    li_dialog_view.reportHummer(context);
                }
            }

            @Override
            public void clickImage(String[] imageUrl, int imgposition) {

            }

            @Override
            public void clickIcon(int userId) {

            }

            @Override
            public void addpinLun(int id, int position) {

            }

            @Override
            public void replaypinLun(int position, int preUserid, String preUserName) {

            }
        });

        //举报删除说说
        li_dialog_view.setMyItemClick(new Li_Dialog_View.GetUserActionOnclickLisnter() {
            @Override
            public void userDelComment() {

                getIntentData.getDelectComment(context,hummerid);
            }

            @Override
            public void userReport(String str) {
                getIntentData.getReportComment(context,hummerid,str);
            }
        });

        getIntentData.setGetObjectListener(new GetObjectListener() {
            @Override
            public void getObject(Object object) {
                if(((Li_ReportComment_Result)object).getCode() == 0){
                    //TODO  这里是删除成功的回调
                    Toast.makeText(context,"删除成功",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }





































































































////
////                    }
////                });
////                break;
////            case R.id.isMessage:
////                break;
////        }
////    }
}
