package com.weiaibenpao.demo.chislim.hurui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qiniu.android.common.Zone;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UploadManager;
import com.weiaibenpao.demo.chislim.Interface.GetObjectListener;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.bean.Li_ReportComment_Result;
import com.weiaibenpao.demo.chislim.bean.Moment;
import com.weiaibenpao.demo.chislim.bean.UserBean;
import com.weiaibenpao.demo.chislim.hurui.activity.H_PhotoActivity;
import com.weiaibenpao.demo.chislim.hurui.activity.PublishHumorActivity;
import com.weiaibenpao.demo.chislim.hurui.activity.UserDetailActivity;
import com.weiaibenpao.demo.chislim.hurui.adapte.HumorAdapter;
import com.weiaibenpao.demo.chislim.hurui.base.HBaseFragment;
import com.weiaibenpao.demo.chislim.hurui.bean.HumorBean;
import com.weiaibenpao.demo.chislim.hurui.bean.MakePraiseBean;
import com.weiaibenpao.demo.chislim.hurui.bean.ReplayCommentBean;
import com.weiaibenpao.demo.chislim.util.Default;
import com.weiaibenpao.demo.chislim.util.GetIntentData;
import com.weiaibenpao.demo.chislim.util.Li_Dialog_View;
import com.wuxiaolong.pullloadmorerecyclerview.PullLoadMoreRecyclerView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.app.Activity.RESULT_OK;

/**
 * Created by lenovo on 2017/4/17.
 */

public class HR_Second_Fragment extends HBaseFragment {
    UploadManager uploadManager;
    OkHttpClient client;
    Request request;
    String url = Default.url + "user/uptoken";

    private final static int GOTO_PUBLISH_HUMOR_REQUEST = 1 ;
    public int num = 0 ;

    private View rootView ;

    private View view ;//输入的弹窗
    private EditText shuru_ev ;
    private Button send_btn ;

    private Handler myHandler = new Handler();

    Li_Dialog_View li_dialog_view = new Li_Dialog_View();

    GetIntentData getIntentData = new GetIntentData();

    @BindView(R.id.send_fragment)
    RelativeLayout send_fragment ;

    @BindView(R.id.pullRecycleView)
    PullLoadMoreRecyclerView pullRecycleView ;


    @BindView(R.id.camers_iv)
    ImageView camers_iv ;

    int hummerid;
    String is_us;

    @OnClick(R.id.camers_iv)
    void clickCamers(){
        //发送七牛请求
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onResponse(okhttp3.Call call, okhttp3.Response response) throws IOException {

                String token = response.body().string();
                Log.i("七牛", token);

                //第三步跳转至 发布说说页面
                Intent intent = new Intent(getActivity() , PublishHumorActivity.class);
                intent.putExtra("token" , token) ;
                startActivityForResult(intent , GOTO_PUBLISH_HUMOR_REQUEST);
            }

            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                Log.i("七牛", e.getMessage());
            }
        });
    }


    private HumorAdapter humorAdapter ;



    private List<Moment> humorLists ; //数据形式
    private List<HumorBean.DataBean.MarklistBean> humor_markLists ;//关注人信息


    private InputMethodManager imm ;// 输入键盘


    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.hr_second_fragment , null);
            //第一步 初始化 七牛
            initConfig();
            //实例化client
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //创建OkHttpClient
                    client = new OkHttpClient();
                    // 创建请求
                    request = new Request.Builder()//
                            .url(url)//
                            .get()//
                            .build();
                }
            }).start();
            imm = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        }
        return rootView;
    }

    private void initConfig() {
        Configuration config = new Configuration.Builder()
                .chunkSize(256 * 1024)  //分片上传时，每片的大小。 默认256K
                .putThreshhold(512 * 1024)  // 启用分片上传阀值。默认512K
                .connectTimeout(10) // 链接超时。默认10秒
                .responseTimeout(60) // 服务器响应超时。默认60秒
                //       .recorder(recorder)  // recorder分片上传时，已上传片记录器。默认null
                //       .recorder(recorder, keyGen)  // keyGen 分片上传时，生成标识符，用于片记录器区分是那个文件的上传记录
                .zone(Zone.zone0) // 设置区域，指定不同区域的上传域名、备用域名、备用IP。默认 Zone.zone0
                .build();
        // 重用uploadManager。一般地，只需要创建一个uploadManager对象
        uploadManager = new UploadManager(config);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();

        initAdapter() ;

        initAction();
    }

    //初始化Adapter ; 这个adapter比较复杂包含了头部的一个recycleView  ， 图片的一个recycleView(一张则显示一张大图，3张显示一排) ， 点赞图像的recycleView，
    // 回复的recycleView
    private void initAdapter() {
        humorAdapter = new HumorAdapter(getActivity() , humorLists , humor_markLists);
        pullRecycleView.setLinearLayout();
        pullRecycleView.setAdapter(humorAdapter);


        pullRecycleView.getRecyclerView().addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                int pos = ((LinearLayoutManager)pullRecycleView.getLayoutManager()).findFirstCompletelyVisibleItemPosition();
                if(pos == 0){
                    pullRecycleView.setPullRefreshEnable(true);
                }else{
                    pullRecycleView.setPullRefreshEnable(false);
                }
                    Log.e("这个是我的测试",pos+"");
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
              /*  if(dy > 0){
                    pullRecycleView.setPullRefreshEnable(false);
                }else{
                    pullRecycleView.setPullRefreshEnable(true);
                }*/
            }
        });


        humorAdapter.setMyItemClick(new HumorAdapter.MyItemClick() {
            @Override
            public void dianzhan(final int position) {
                Call<MakePraiseBean> call = apiStores.getMakePraiseService(UserBean.getUserBean().userId , humorLists.get(position).getId());
                call.enqueue(new Callback<MakePraiseBean>() {
                    @Override
                    public void onResponse(Call<MakePraiseBean> call, Response<MakePraiseBean> response) {
                        if(response.isSuccessful()){
                            MakePraiseBean bean = response.body() ;
                            if(bean.getCode() == 0){
                                if(bean.getMsg().equals("点赞成功")) {
                                    humorLists.get(position).setIsLike("YES");
                                    humorLists.get(position).setLikeNum(humorLists.get(position).getLikeNum() + 1);
                                }else{
                                    humorLists.get(position).setIsLike("NO");
                                    humorLists.get(position).setLikeNum(humorLists.get(position).getLikeNum() - 1);
                                }
                                humorAdapter.notifyItemChanged(position+1);
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<MakePraiseBean> call, Throwable t) {

                    }
                });

                addCalls(call);
            }

            @Override
            public void jubao_shanchu(int hummer_id, String isus) {
                hummerid = hummer_id;
                is_us = isus;
                //OK  说明是自己的
                //Log.i("测试",isus);
                if("OK".equals(is_us)){
                    li_dialog_view.delHummer(mActivity);

                }else {  //说明是别人的
                    li_dialog_view.reportHummer(mActivity);
                }
            }

            @Override
            public void clickImage(String[] imageUrl, int position) {
                Intent intent = new Intent(mActivity , H_PhotoActivity.class);
                H_PhotoActivity.bitmap = Arrays.asList(imageUrl) ;
                intent.putExtra("ID" , position) ;
                startActivity(intent);
            }

            @Override
            public void clickIcon(int userId) {
                Intent intent = new Intent(getActivity() , UserDetailActivity.class);
                intent.putExtra(UserDetailActivity.USER_ID , userId);
                startActivity(intent);
            }

            @Override
            public void addpinLun(final int id, final int position) {
                Log.e("addPinLun---> "," soft input state "+getActivity().getWindow().getAttributes().softInputMode);
//                getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
                if (view == null) {
                    view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_popupwindow1 , null);
                    shuru_ev = (EditText) view.findViewById(R.id.shuru_ev);
                    send_btn = (Button) view.findViewById(R.id.send_btn);
                }

                if(imm.isActive() == false) {
                    myHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                            Log.e("addPinLun","弹出软键盘");
                        }
                    }, 0);
                }else {
                    Log.e("addPinLun","isActive is true");
                }
                final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
                popupWindow.setFocusable(true);
                popupWindow.setOutsideTouchable(true);
//                popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                View parent = LayoutInflater.from(getActivity()).inflate(R.layout.hr_second_fragment
                        , null);
                popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
                //popupWindow在弹窗的时候背景半透明
                final WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
                params.alpha = 0.5f;
                getActivity().getWindow().setAttributes(params);
                send_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Call<ReplayCommentBean> call = apiStores.getAddCommentService(UserBean.getUserBean().userId+"",
                                id+"" ,shuru_ev.getText().toString());
                        call.enqueue(new Callback<ReplayCommentBean>() {
                            @Override
                            public void onResponse(Call<ReplayCommentBean> call, Response<ReplayCommentBean> response) {
                                if(response.isSuccessful()){
                                    ReplayCommentBean bean = response.body() ;
                                    if(bean.getCode() == 0){
                                        //Toast.makeText(getActivity(), "回复成功", Toast.LENGTH_SHORT).show();
                                        if(imm.isActive() == false) {
                                            myHandler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                                                }
                                            }, 0);
                                        }
                                        popupWindow.dismiss();
                                        Moment.CommlistBean commlistBean = new Moment.CommlistBean();
                                        commlistBean.setId(bean.getData().getId());
                                        commlistBean.setCommentTime((int) bean.getData().getCommentTime());
                                        commlistBean.setComText(bean.getData().getComText());
                                        commlistBean.setHumorId(bean.getData().getHumorId());
                                        commlistBean.setUserId(bean.getData().getUserId());
                                        commlistBean.setParentId(bean.getData().getParentId());
                                        commlistBean.setUserNickName(UserBean.getUserBean().userName);

                                        humorLists.get(position).getCommlist().add(commlistBean);
                                        humorAdapter.notifyDataSetChanged();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<ReplayCommentBean> call, Throwable t) {

                            }
                        });

                        addCalls(call);
                    }
                });
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        params.alpha = 1.0f;
                        getActivity().getWindow().setAttributes(params);
//                        if (imm != null) {
//                            imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//                            Log.e("addPinLun","hideeeeee :");
//                        }
                        Log.e("addPinLun-onDismiss "," soft input state "+getActivity().getWindow().getAttributes().softInputMode);
//                        if(getActivity().getWindow().getAttributes().softInputMode==WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE){
//                            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
//                            Log.e("addPinLun-onDismiss "," soft input set state hidden");
//                        }

                        if(imm.isActive()){
//                            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                            Log.e("addPinLun-onDismiss "," soft input is active then hide soft input");
                        }else {
                            Log.e("addPinLun-onDismiss "," soft input is not active");
                        }
                    }
                });
            }



            @Override
            public void replaypinLun(final int position, final int preUserid,final String preUserName) {
               /* shuru_lv.setVisibility(View.VISIBLE);
                shuru_ev.requestFocus();
                shuru_ev.setHint("回复"+preUserName+":");*/
                if (view == null) {
                    view = LayoutInflater.from(getActivity()).inflate(R.layout.layout_popupwindow1 , null);
                    shuru_ev = (EditText) view.findViewById(R.id.shuru_ev);
                    send_btn = (Button) view.findViewById(R.id.send_btn);
                }
                if(imm.isActive() == false) {
                    myHandler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                        }
                    }, 0);
                }
                shuru_ev.setText("");
                shuru_ev.setHint("回复:"+preUserName);
                final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
                popupWindow.setFocusable(true);
                popupWindow.setOutsideTouchable(true);
//                popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
                View parent = LayoutInflater.from(getActivity()).inflate(R.layout.hr_second_fragment
                        , null);
                popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
                //popupWindow在弹窗的时候背景半透明
                final WindowManager.LayoutParams params = getActivity().getWindow().getAttributes();
                params.alpha = 0.5f;
                getActivity().getWindow().setAttributes(params);
                send_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Call<ReplayCommentBean> call = apiStores.getReplayCommentService(UserBean.getUserBean().userId+"",
                                humorLists.get(position).getId()+"" ,shuru_ev.getText().toString(),preUserid+"");
                        call.enqueue(new Callback<ReplayCommentBean>() {
                            @Override
                            public void onResponse(Call<ReplayCommentBean> call, Response<ReplayCommentBean> response) {
                                if(response.isSuccessful()){
                                    ReplayCommentBean bean = response.body() ;
                                    if(bean.getCode() == 0){
                                        //Toast.makeText(getActivity(), "回复成功", Toast.LENGTH_SHORT).show();
                                        if(imm.isActive() == false) {
                                            myHandler.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
                                                }
                                            }, 0);
                                        }
                                        popupWindow.dismiss();
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<ReplayCommentBean> call, Throwable t) {

                            }
                        });

                        addCalls(call);
                    }
                });
                popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        params.alpha = 1.0f;
                        getActivity().getWindow().setAttributes(params);
                    }
                });

            }
        });


    }


    @Override
    public void onResume() {
        super.onResume();
    }

    private void initData() {
        //fragment有可能不是第一次加载，所以要判断其是否为null
        if(humorLists == null) {
            humorLists = new ArrayList<>();
        }else{
            //不 == 0的时候要刷新一下ui
            num = 0 ;
        }
        if(humor_markLists == null){
            humor_markLists = new ArrayList<HumorBean.DataBean.MarklistBean>();
        }else{
            num = 0 ;
        }
        initNet();
    }

    private void initAction() {
        pullRecycleView.setOnPullLoadMoreListener(new PullLoadMoreRecyclerView.PullLoadMoreListener() {
            @Override
            public void onRefresh() {
                num = 0 ;
                initNet();
            }

            @Override
            public void onLoadMore() {
                initNet();
            }
        });

        //举报删除说说
        li_dialog_view.setMyItemClick(new Li_Dialog_View.GetUserActionOnclickLisnter() {
            @Override
            public void userDelComment() {

                getIntentData.getDelectComment(mActivity,hummerid);
            }

            @Override
            public void userReport(String str) {
                getIntentData.getReportComment(mActivity,hummerid,str);
            }
        });

        getIntentData.setGetObjectListener(new GetObjectListener() {
            @Override
            public void getObject(Object object) {
                if(((Li_ReportComment_Result)object).getCode() == 0){
                    //TODO  这里是删除成功的回调
                    Toast.makeText(mActivity,"删除成功",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void refresh(){
        num = 0 ;
        initNet();
    }
    //发送网络请求
    private void initNet() {
        //todo 单例的userID改为 sharePreferse里面的
        Call<HumorBean> call =  apiStores.getHoumService( UserBean.getUserBean().userId + "" , num + "" , "20");
        call.enqueue(new Callback<HumorBean>() {
            @Override
            public void onResponse(Call<HumorBean> call, Response<HumorBean> response) {
                if(response.isSuccessful()){
                    HumorBean houmbean = response.body() ;
                    Log.e("HRSecond_initNet","json is "+new Gson().toJson(houmbean));
                    if(houmbean.getCode() == 0){

                        //如果为 0 表示刷新或者第一次进入这个页面，需清空列表数据
                        if(num == 0){
                            humorLists.clear();
                            humor_markLists.clear();
                            humor_markLists.addAll(houmbean.getData().getMarklist()) ;
                            pullRecycleView.setPushRefreshEnable(true);
                        }

                        //访问成功则  页数+1 ；
                        num = num + 1 ;
                        humorLists.addAll(houmbean.getData().getHumorlist());

                        pullRecycleView.getRecyclerView().getAdapter().notifyDataSetChanged();

                        //如果列表里面的数据等于总数据则停止加载
                        if(humorLists.size() >= houmbean.getData().getExtra().getTotalSize()){
                            pullRecycleView.setPushRefreshEnable(false);
                        }

                        pullRecycleView.setPullLoadMoreCompleted();

                    }
                }
            }

            @Override
            public void onFailure(Call<HumorBean> call, Throwable t) {
              if(pullRecycleView != null)  pullRecycleView.setPullLoadMoreCompleted();
            }
        });

        addCalls(call);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            switch (requestCode){
                case GOTO_PUBLISH_HUMOR_REQUEST:
                    if (data.getBooleanExtra(PublishHumorActivity.RELEASE,false)){
                        refresh();
                    }
                    break;
            }
        }
    }
}
