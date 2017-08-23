package com.weiaibenpao.demo.chislim.hurui.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;
import com.weiaibenpao.demo.chislim.Interface.OnGetPro;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.base.BaseFragment;
import com.weiaibenpao.demo.chislim.bean.FansBean;
import com.weiaibenpao.demo.chislim.bean.UserBean;
import com.weiaibenpao.demo.chislim.hurui.activity.UserDetailActivity;
import com.weiaibenpao.demo.chislim.model.MyModel;
import com.weiaibenpao.demo.chislim.ui.AboutActivity;
import com.weiaibenpao.demo.chislim.ui.Li_FunsActivity;
import com.weiaibenpao.demo.chislim.ui.Li_Medal_Activity;
import com.weiaibenpao.demo.chislim.ui.MyHistoryActivity;
import com.weiaibenpao.demo.chislim.ui.SetActivity;
import com.weiaibenpao.demo.chislim.ui.UpdateUserActivity;
import com.weiaibenpao.demo.chislim.util.ACache;
import com.weiaibenpao.demo.chislim.util.CircleTransform;
import com.weiaibenpao.demo.chislim.util.SharedPrefsUtil;
import com.weiaibenpao.demo.chislim.util.WriteReadSharedPrefs;

import java.text.DecimalFormat;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by Administrator on 2016/4/22.
 */
public class UserFragment extends BaseFragment implements View.OnClickListener {

    /*界面控件*/
    public TextView userIntro;          ///用户个性签名
    private ImageView userIcon;          //用户头像
    private ImageView userSetIcon;       //用户信息编辑
    private TextView userName;
    //用户名称*/

    private TextView userHeight;        //用户身高
    private TextView userWeight;        //用户体重
    private TextView userBMI;           //用户的BMI

    private TextView careId;
    private TextView funsId;
    private TextView dynamicsId;

    private ImageView gradeProgressd;
    private ImageView gradeProgress;

    private View sportSucces;
    private TextView gradePlan;

    private View view;

    private UserBean user;

    Context context;
    ViewGroup.LayoutParams para;


    int dis = 0;


    DecimalFormat decimalFormat;

    private ACache mCache;
    private TextView runDistance;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = getContext();

        user = UserBean.getUserBean();
        //透明状态栏
//        getContext().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //透明导航栏
//        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_user, container, false);
        }
        ViewGroup parent = (ViewGroup) view.getParent();
        if (parent != null) {
            parent.removeView(view);
        }



        return view;
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        initView(view);
    }

    @Override
    public void onStart() {

        super.onStart();

        mCache = ACache.get(context);    //实例化缓存
//        WriteReadSharedPrefs.readUser(context,user);
        Log.e("wlx", "onResume: "+user.toString() );

        getUserInfo();
        showUserInfo();

    }


    private void showUserInfo() {

        if (user.userImage == null || user.userImage == "") {

        } else {
            Picasso.with(context)
                    .load(user.userImage)
                    .resize(200, 200)
                    .error(R.mipmap.boy)
                    .transform(new CircleTransform())
                    .into(userIcon);
        }
        if (user.userSex.equals("男")) {
            userSetIcon.setBackgroundResource(R.mipmap.user_sex_boy);
        } else if (user.userSex.equals("女"))
            userSetIcon.setBackgroundResource(R.mipmap.user_sex_girl);


   /*     userName.setText(user.userName);*/
        userHeight.setText(String.valueOf(user.userHeight));
        userWeight.setText(String.valueOf(user.userWeight));
        //float bmi = (float) (user.userWeight/((user.userHeigh*0.01)*(user.userHeigh*0.01)));
        userBMI.setText(String.valueOf(user.userBmi));
        // userBMI.setText(printNum(bmi));
        if (UserBean.getUserBean().userTntru != null)
            userIntro.setText(UserBean.getUserBean().userTntru);

        userName.setText(UserBean.getUserBean().userName);
    }



    @Override
    public void initView(View view) {
        userIcon = (ImageView) view.findViewById(R.id.userIcon);
        userIcon.setOnClickListener(this);
        view.findViewById(R.id.careLayout).setOnClickListener(this);
        view.findViewById(R.id.funslayout).setOnClickListener(this);
        view.findViewById(R.id.dynamicslayout).setOnClickListener(this);
        userSetIcon = (ImageView) view.findViewById(R.id.userSetIcon);


        userIntro = (TextView) view.findViewById(R.id.userIntro);

        userName = (TextView) view.findViewById(R.id.userName);

        //跑了几公里
        runDistance = ((TextView) view.findViewById(R.id.run_distance));
        careId = (TextView) view.findViewById(R.id.careId);
        funsId = (TextView) view.findViewById(R.id.funsId);
        dynamicsId = (TextView) view.findViewById(R.id.dynamicsId);
        //用户设置
        view.findViewById(R.id.set_layout).setOnClickListener(this);
        //关于界面
        view.findViewById(R.id.about_layout).setOnClickListener(this);
        //跑步记录
        view.findViewById(R.id.sportRecord_layout).setOnClickListener(this);

/*
        updateUser = (TextView) view.findViewById(R.id.updateUser);
        updateUser.setOnMomentClickListener(this);
*/

        userHeight = (TextView) view.findViewById(R.id.heighData);
        userHeight.setOnClickListener(this);

        userWeight = (TextView) view.findViewById(R.id.weightData);
        userWeight.setOnClickListener(this);

        userBMI = (TextView) view.findViewById(R.id.bmiData);
        userBMI.setOnClickListener(this);

        gradePlan = (TextView) view.findViewById(R.id.gradePlan);

        gradeProgressd = (ImageView) view.findViewById(R.id.gradeProgressd);
        gradeProgress = (ImageView) view.findViewById(R.id.gradeProgress);
        para = gradeProgress.getLayoutParams();


        //获取用户的今日运动距离
        // getOneSport(user.userId, getNowDate());

        //获取用户的当月累计运动距离
        // getDis(user.userId);

        sportSucces = view.findViewById(R.id.sportSucces);
        sportSucces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Li_Medal_Activity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.userIcon:
                goUserInfo();
                break;
/*            case userName:
                goUserInfo();
                break;*/
            case R.id.set_layout:
                userSet();
                break;
          /*  case R.id.updateUser:
                goUserInfo();
                break;*/
            case R.id.heighData:
                goUserInfo();
                break;
            case R.id.weightData:
                goUserInfo();
                break;
            case R.id.bmiData:
                goUserInfo();
                break;

            case R.id.about_layout:
                aboutStart();
                break;
            //跑步记录
            case R.id.sportRecord_layout:
                startRecord();
                break;
            case R.id.dynamicslayout:
                intentQzone();
                break;
            case R.id.careLayout:
                intentFuns(0);
                break;
            case R.id.funslayout:
                intentFuns(1);
                break;
        }
    }

    /**
     * 跑步记录
     */
    private void startRecord() {
        startActivity(new Intent(context, MyHistoryActivity.class));
    }

    /**
     * 跳转到个人详情页面
     */
    public void goUserInfo() {
        Intent intent = new Intent(context, UpdateUserActivity.class);
        startActivity(intent);
    }

    /**
     * 跳转到设置界面
     */
    public void userSet() {
        Intent intent = new Intent(context, SetActivity.class);
        startActivity(intent);
    }

    /**
     * 关于界面
     */
    private void aboutStart() {
        startActivity(new Intent(context, AboutActivity.class));
    }

    //获取网络数据
    public void getUserInfo() {
        Call<FansBean> call = MyModel.getModel().getService().getFans(UserBean.getUserBean().userId);

        call.enqueue(new Callback<FansBean>() {
            @Override
            public void onResponse(Call<FansBean> call, Response<FansBean> response) {
                if (response.isSuccessful()) {
                    FansBean result = response.body();
                    Log.e("UserFragment","json result :"+new Gson().toJson(result));
                    if (result.getCode() == 0) {
                        updateView(result);

                    } else {

                    }
                }else {
                    Log.e("UserFragment","getFans response failed ");
                }
            }

            @Override
            public void onFailure(Call<FansBean> call, Throwable t) {

            }
        });
    }

    //修改界面
    public void updateView(FansBean result) {
        if (result.getData()==null) {
           return;
        }

        //跑步的公里数
        dis = result.getData().getCountSport();
        double countSport = result.getData().getCountSport();
        runDistance.setText("已跑里程：" + getDouble(countSport)+"km");
        careId.setText(result.getData().getCares() + "");
        funsId.setText(result.getData().getFanscount() + "");
        dynamicsId.setText(result.getData().getTotalHumor() + "");

        //设置等级
        if (dis!=0){
            setLevel(dis);
        }else{
            para.width=0;
            gradeProgress.setLayoutParams(para);
        }

//        mCache.put("alldis",String.valueOf(dis));
        SharedPrefsUtil.putValue(getActivity(),WriteReadSharedPrefs.PREFS_NAME,WriteReadSharedPrefs.TOTAL_DISTANCE,String.valueOf(dis));

        //计算用户的跑步等级

    }/**
     */
     private String getDouble(double num){
         DecimalFormat   df   = new DecimalFormat("#0.00");
         return df.format(num);
     }
    /**
     *
     * @param distance  当前里程
     */
    private void setLevel(double distance) {
        //得到等级
        double level = getLevel(distance);
        //里程差
        double distance1 = getDistance(level, distance);

      //计算进度条
        setProgress(level,distance);

        Log.e("cc2222", "setLevel: "+getDouble(distance1) );
            gradePlan.setText("还差"+getDouble(distance1)+"km->Lv"+((int)level+1));


    }

    /**
     *  计算进度条
     * @param level
     * @param distance
     */
    private void setProgress(double level,double distance) {
        double with= gradeProgressd.getWidth()/3.0;
        if (level==0){
            para.width= (int) ((distance/50)*with);
            gradePlan.setLayoutParams(para);
        }
        else if (level<4){
            para.width= (int) ((distance- Math.pow(2,(level-1))*50)*with/((Math.pow(2,level)-Math.pow(2,(level-1)))*50)+level*with);
            Log.e("wlx20333", "1:=== "+((distance- Math.pow(2,(level-1))*50) ));
            Log.e("wlx20202", "2:==== ");


            gradeProgress.setLayoutParams(para);
        }
        else if (level>3&& level<7){
            para.width= (int) ((distance- Math.pow(2,(level-1))*50)/(Math.pow(2,level)*50-Math.pow(2,(level-1)))*with+(level-4)*with);
            gradeProgress.setLayoutParams(para);
        }

    }



    //计算距离差
    private double getDistance(double level,double  runDistance){

        double pow = Math.pow(2, level);
        double v = pow * 50 - runDistance;
        return v;
    }
    //计算等级

    private double getLevel(double input) {
        Log.e("getLevel","input is "+input);
        double y = input / 50;
        if(y <= 0){
           return  0 ;
        }
        double divisor = Math.log(y);
        double dividend = Math.log(2);
        double quotient  = divisor/dividend;
        double  result =  Math.floor(quotient)+1;
//        Log.e("getLevel","y is "+y+"\ndivisor is "+divisor+"\ndividend is "+dividend+"\nquotient is "+quotient+"\nresult  is "+result);
        if(result <=0 ){
            return  0 ;
        }
        Log.e("getLevel","result is "+result);

        return result;
    }


    /**
     *
     *  0查看自己关注的用户
     *  1查看自己的粉丝
     *
     * @param type
     */
    public void intentFuns(int type){
        Intent intent = new Intent(context,Li_FunsActivity.class);
        intent.putExtra(Li_FunsActivity.FANS,0);
        startActivity(intent);
    }



    /**
     * 查看自己的动态
     */
    public void intentQzone(){
        Intent intent = new Intent(context,UserDetailActivity.class);
        intent.putExtra(UserDetailActivity.USER_ID,user.userId);
        startActivity(intent);

    }

    OnGetPro mListener;

//    @Subscribe
//    public void onMessageEvent(Boolean flag) {
//        if (flag==true){
//            showUserInfo();
//        }
//
//
//    }
    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        //在onAttach方法中实例化myListener
        mListener = (OnGetPro) activity;
    }


}
