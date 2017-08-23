package com.weiaibenpao.demo.chislim.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.lichfaker.scaleview.HorizontalScaleScrollView;
import com.lichfaker.scaleview.VerticalScaleScrollView;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.bean.Li_UserBeanResult;
import com.weiaibenpao.demo.chislim.bean.UserBean;
import com.weiaibenpao.demo.chislim.hurui.utils.AppSPUtils;
import com.weiaibenpao.demo.chislim.model.MyModel;
import com.weiaibenpao.demo.chislim.util.WriteReadSharedPrefs;

import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 完善个人信息，在注册完后出现
 */
public class PerfectActivity extends AppCompatActivity {

    @BindView(R.id.selectboy)
    ImageView selectboy;
    @BindView(R.id.selectgirl)
    ImageView selectgirl;
    //性别布局中的控件
    private ImageView boy;
    private ImageView girl;
    // private Button sexUp;
    private ImageView sexNext;

    //昵称布局中的控件
    private Button nameNext;

    //身高选择布局整的控件
    //private Button heightUp;
    private ImageView heightNext;
    private TextView myHeightText;

    //体重布局控件
    //private Button weightUp;
    private ImageView weightNext;
    private TextView myWeightText;

    //日期布局
    private DatePicker mDatePicker;
    // 定义5个记录当前时间的变量
    private int year;
    private int month;
    private int day;

    private ImageView datebut;


    FrameLayout viewGroup;

    RelativeLayout viewSex;     //性别布局
    RelativeLayout viewWeight;   //体重布局
    RelativeLayout viewHeight;   //身高布局
    RelativeLayout viewBirth;     //出生年月

    UserBean user;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfect);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this , getResources().getColor(R.color.ku_bg ) , 0);
        context = getApplicationContext();
        user = UserBean.getUserBean();

        initLayout();
        initView();
    }

    /**
     * 实例化布局控件
     */
    public void initLayout() {

        viewGroup = (FrameLayout) findViewById(R.id.activity_perfect);
        viewSex = (RelativeLayout) viewGroup.findViewById(R.id.mySex);
        viewWeight = (RelativeLayout) viewGroup.findViewById(R.id.myWeight);
        viewHeight = (RelativeLayout) viewGroup.findViewById(R.id.myHeight);
        viewBirth = (RelativeLayout) viewGroup.findViewById(R.id.myBirth);
        viewSex.setVisibility(View.VISIBLE);
        viewWeight.setVisibility(View.GONE);
        viewHeight.setVisibility(View.GONE);
        viewBirth.setVisibility(View.GONE);
    }

    /**
     * 实例化控件
     */
    public void initView() {
        //性别选择
        boy = (ImageView) findViewById(R.id.boy);
        boy.setAlpha((float) 1);
        user.userSex = "男";
        boy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boy.setAlpha((float) 1);
                girl.setAlpha((float) 0.5);
                user.userSex = "男";
                selectboy.setImageResource(R.mipmap.mr);
                selectgirl.setImageResource(R.mipmap.w_round);
                user.userImage = "http://ofplk6att.bkt.clouddn.com/boy.png";
            }
        });

        girl = (ImageView) findViewById(R.id.girl);
        girl.setAlpha((float) 0.5);
        girl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boy.setAlpha((float) 0.5);
                girl.setAlpha((float) 1);
                user.userSex = "女";
                selectboy.setImageResource(R.mipmap.w_round);
                selectgirl.setImageResource(R.mipmap.miss);
                user.userImage = "http://ofplk6att.bkt.clouddn.com/girl.png";
            }
        });

        //性别下一步
        sexNext = (ImageView) findViewById(R.id.sexNext);
        sexNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewSex.setVisibility(View.GONE);
                viewHeight.setVisibility(View.VISIBLE);
            }
        });


        //身高选择
        myHeightText = (TextView) findViewById(R.id.myHeightText);
        user.userHeight = 171;
        VerticalScaleScrollView vScaleScrollView = (VerticalScaleScrollView) findViewById(R.id.verticalScale);
        vScaleScrollView.setOnScrollListener(new HorizontalScaleScrollView.OnScrollListener() {
            @Override
            public void onScaleScroll(int scale) {
                user.userHeight = scale;
                myHeightText.setText(String.valueOf(scale));
            }
        });

        //身高中的上一步
        /*heightUp = (Button) findViewById(R.id.heightUp);
        heightUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewSex.setVisibility(View.VISIBLE);
                viewHeight.setVisibility(View.GONE);
            }
        });*/

        //身高中的下一步
        heightNext = (ImageView) findViewById(R.id.heightNext);
        heightNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHeight.setVisibility(View.GONE);
                viewWeight.setVisibility(View.VISIBLE);
            }
        });

        //体重选择
        myWeightText = (TextView) findViewById(R.id.myWeightText);
        user.userWeight = (float) 65.5;
        HorizontalScaleScrollView scaleScrollView = (HorizontalScaleScrollView) findViewById(R.id.horizontalScale);
        scaleScrollView.setOnScrollListener(new HorizontalScaleScrollView.OnScrollListener() {
            @Override
            public void onScaleScroll(int scale) {
                user.userWeight = (float) scale;
                myWeightText.setText(String.valueOf(scale));
            }
        });


        //体重中的下一步
        weightNext = (ImageView) findViewById(R.id.weightNext);
        weightNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewWeight.setVisibility(View.GONE);
                viewBirth.setVisibility(View.VISIBLE);
            }
        });

        //日期布局
        mDatePicker = (DatePicker) findViewById(R.id.datePicker);
        user.userBirth = "1990.07.07";
        Calendar c = Calendar.getInstance();
        /*year = c.get(Calendar.YEAR);*/
        year = 1990;
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);

        //初始化DatePicker组件，初始化时指定监听器
        mDatePicker.init(year, month, day, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker arg0, int year, int month, int day) {
                // 显示当前日期、时间
                month = month + 1;
                String date = year + "." + month + "." + day;
                Log.i("日期", date);

                user.userBirth = date;
            }
        });
        //完成
        datebut = (ImageView) findViewById(R.id.datebut);
        datebut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //上传数据
                startMain();
            }
        });
    }

    /**
     * 上传用户数据，跳转至首界面
     */
    public void startMain() {
        Call<Li_UserBeanResult> call = MyModel.getModel().getService().updateUserInfo(user.userId,
                user.userName, user.userSex, user.userBirth, "", "", "",  user.userWeight, user.userHeight);
//        Log.e("startMain","id "+user.userId+"\nname "+user.userName+"\nSex "+user.userSex+"\nbirth "+user.userBirth+"\nweight "+user.userWeight+"\nheight "+user.userHeight);

//        Call<Li_UserBeanResult> call = MyModel.getModel().getService().updateUserInfo(43,
//                "Test", "男", "2008", "", "", "",  60f, 180);

        call.enqueue(new Callback<Li_UserBeanResult>() {
            @Override
            public void onResponse(Call<Li_UserBeanResult> call, Response<Li_UserBeanResult> response) {
                if (response.isSuccessful()) {
                    Li_UserBeanResult result = response.body();
                    Log.e("onResponse","isSuccessful is true");
                    if(result != null) {
                        if (result.getCode() == 0) {
                            //把用户信息写入数据
                            WriteReadSharedPrefs.writeUser(context, result.getData());
                            AppSPUtils.put(PerfectActivity.this, "name", UserBean.getUserBean().userName);//注册成功时默认返回一个13位数字作为用户名
                            AppSPUtils.put(PerfectActivity.this, "logoUrl", UserBean.getUserBean().userImage);
                            startActivity(new Intent(PerfectActivity.this, MainActivity.class));
                            finish();
                        } else {
                            Log.e("startMain","updateUserInfo Failed error code is"+result.getCode());
                            Toast.makeText(PerfectActivity.this,"个人信息设置失败",Toast.LENGTH_LONG).show();
                        }
                    }
                }else {
                    Log.e("onResponse","response is failed");
                }
            }

            @Override
            public void onFailure(Call<Li_UserBeanResult> call, Throwable t) {
                Toast.makeText(PerfectActivity.this, "OK---", Toast.LENGTH_SHORT).show();
            }
        });
    }
}


