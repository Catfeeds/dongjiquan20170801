package com.weiaibenpao.demo.chislim.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jaeger.library.StatusBarUtil;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.bean.Li_UserBeanResult;
import com.weiaibenpao.demo.chislim.bean.SmsTestResult;
import com.weiaibenpao.demo.chislim.bean.UserBean;
import com.weiaibenpao.demo.chislim.bean.UserResult;
import com.weiaibenpao.demo.chislim.model.MyModel;
import com.weiaibenpao.demo.chislim.model.SmsModel;
import com.weiaibenpao.demo.chislim.model.UserModel;
import com.weiaibenpao.demo.chislim.util.Default;
import com.weiaibenpao.demo.chislim.util.SharedPrefsUtil;
import com.weiaibenpao.demo.chislim.util.WriteReadSharedPrefs;

import java.util.List;
import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistActivity extends AppCompatActivity {

    private EditText userPhone;
    private EditText keyPhone;
    private TextView sendKey;
    private EditText userPass;
    private EditText userPassTwo;
    private Button userSubmit;
    private LinearLayout goLogin;

    //短信验证的key,http://apistore.baidu.com/apiworks/servicedetail/1018.html
    private String contentStr = "【东极圈】 您的验证码是";
    private String content = "";
    private static final int tag = 2;
    private int numberTest;                 //验证码
    private String userPhoneString;

    UserBean user;

    private ImageView back;
    UserBean userBean;

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_regist);
        StatusBarUtil.setColor(this , getResources().getColor(R.color.ku_bg ), 0);
        context = getApplicationContext();
        userBean =  UserBean.getUserBean();
        initView();
    }

    public void initView(){
        userPhone = (EditText) findViewById(R.id.userPhone);
        keyPhone = (EditText) findViewById(R.id.keyPhone);
        sendKey = (TextView) findViewById(R.id.sendKey);

        //发送手机验证码
        sendKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取用户手机号
                userPhoneString = userPhone.getText().toString().trim();
                content = "";
                //获取6位随机数
                numberTest = nextInt(100000,999999);
                Log.i("验证码","------ " + numberTest + " ------");
                //拼接成验证码
                content = contentStr+numberTest + "，有效时间5分钟";
               if (TextUtils.isEmpty(userPhoneString)){
                   Toast.makeText(context, "请输入手机号码", Toast.LENGTH_SHORT).show();
                   return;
               }
                //发送验证码
                sendSms(userPhoneString,content,tag);

                sendKey.setClickable(false);

                changeTime();
            }
        });

        userPass = (EditText) findViewById(R.id.userPass);
        userPassTwo = (EditText) findViewById(R.id.userPassTwo);
        userSubmit = (Button) findViewById(R.id.userSubmit);

        /**
         * 注册
         */
        userSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String keyPhoneStr = keyPhone.getText().toString().trim();
                String userPassStr = userPass.getText().toString().trim();
                String userPassTwoStr = userPassTwo.getText().toString().trim();

                if(!String.valueOf(numberTest).equals(keyPhoneStr)){
                    Toast.makeText(RegistActivity.this,"验证码错误",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(userPassStr.length() == 0){
                    Toast.makeText(RegistActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(userPassTwoStr.length() == 0){
                    Toast.makeText(RegistActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!userPassStr.equals(userPassTwoStr)){
                    Toast.makeText(RegistActivity.this,"两次密码不一致",Toast.LENGTH_SHORT).show();
                    return;
                }

                regist(userPhoneString,"phone",userPassStr);
            }
        });
        goLogin = (LinearLayout) findViewById(R.id.goLogin);
        //跳转到登录界面
        goLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        user = UserBean.getUserBean();

        //返回
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * 产生随机数
     * @param min
     * @param max
     * @return
     */
    public int nextInt(final int min, final int max){

        Random rand= new Random();

        int tmp = Math.abs(rand.nextInt());

        return tmp % (max - min + 1) + min;
    }

    /**
     * 发送验证码
     */
    public void sendSms(String userPhone,String content,int tag){
        Call<SmsTestResult> call = SmsModel.getModel().getService().getResult(Default.baiduKey,userPhone,content,tag);

        call.enqueue(new Callback<SmsTestResult>() {
            @Override
            public void onResponse(Call<SmsTestResult> call, Response<SmsTestResult> response) {
                if (response.isSuccessful()) {

                    SmsTestResult result = response.body();
                    if (result.getMessage()=="ok") {
                        //Toast.makeText(RegistActivity.this,result.getSuccessCounts(),Toast.LENGTH_SHORT).show();

                    }else{
                        //Toast.makeText(RegistActivity.this,result.getSuccessCounts()+"",Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<SmsTestResult> call, Throwable t) {
                Toast.makeText(RegistActivity.this,"登录失败",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 注册
     * @param userPhone
     * @param userPass
     */
    public void regist(final String userPhone, String sourse, final String userPass){
        Call<Li_UserBeanResult> call = MyModel.getModel().getService().getPhoneRegistResult(userPhone,sourse,userPass);

        call.enqueue(new Callback<Li_UserBeanResult>() {
            @Override
            public void onResponse(Call<Li_UserBeanResult> call, Response<Li_UserBeanResult> response) {
                if (response.isSuccessful()) {
                    Li_UserBeanResult result = response.body();
                    Log.e("regist","result is "+new Gson().toJson(result));
                    if (result.getCode() == 0) {
                       /* userBean.userPhoone = result.initData().getUserPhone();
                        userBean.userId = result.initData().getUserID();*/
                        //将数据写入本地
                        WriteReadSharedPrefs.writeUser(context,result.getData());
                        WriteReadSharedPrefs.readUser(context , userBean);

                        Intent intent = new Intent(RegistActivity.this,PerfectActivity.class);
                        startActivity(intent);
                        finish();

                    }else {
                        Log.e("register","error code is "+result.getCode()+"\n message is "+result.getMsg());
                        Toast.makeText(RegistActivity.this,"该电话号码已存在",Toast.LENGTH_SHORT).show();
                    }
                }
            }
            @Override
            public void onFailure(Call<Li_UserBeanResult> call, Throwable t) {
                Toast.makeText(RegistActivity.this,"注册失败",Toast.LENGTH_SHORT).show();
            }
        });
    }


    /**
     * 通过电话号码查询服务器端
     */
    public void getUserByPhone(String userPhone,String userPass){
        Call<UserResult> call = UserModel.getModel().getService().getResult(Default.getOneByPhone, userPhone,userPass);

        call.enqueue(new Callback<UserResult>() {
            @Override
            public void onResponse(Call<UserResult> call, Response<UserResult> response) {
                if (response.isSuccessful()) {
                    UserResult result = response.body();
                    if (result.getError() == 0) {

                        List<UserResult.UserBean> userList = result.getUser();
                        UserResult.UserBean userBean = userList.get(0);
                        user.userPhoone = userBean.getUserPhone();
                        user.userId = userBean.getUserId();
                        SharedPrefsUtil.putValue(context,WriteReadSharedPrefs.PREFS_NAME,"userId",userBean.getUserId());
                        Intent intent = new Intent(RegistActivity.this,PerfectActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                    }
                }
            }

            @Override
            public void onFailure(Call<UserResult> call, Throwable t) {
            }
        });
    }

    //六十秒倒计时
    public void changeTime(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i = 60;i >= 0; i--){
                    try {
                        Message msg = Message.obtain();

                        Thread.sleep(1000);

                        msg.arg1 = i;

                        hander.sendMessage(msg);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();
    }

    Handler hander = new Handler( ){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            sendKey.setText(msg.arg1 + " 秒后重新发送");
            if(msg.arg1 == 0){
                sendKey.setText("重新发送验证码");
                sendKey.setClickable(true);
            }
        }
    };
}
