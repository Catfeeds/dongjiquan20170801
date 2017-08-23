package com.weiaibenpao.demo.chislim.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.bean.Li_UserBeanResult;
import com.weiaibenpao.demo.chislim.bean.SmsTestResult;
import com.weiaibenpao.demo.chislim.model.MyModel;
import com.weiaibenpao.demo.chislim.model.SmsModel;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FindPassActivity extends Activity {

    private EditText oldPhone;
    private EditText keyPhone;
    private TextView sendKey;
    private EditText newPass;
    private EditText newPassTwo;
    private Button findPass;

    //短信验证的key,http://apistore.baidu.com/apiworks/servicedetail/1018.html
    private static final String appKey = "467fa36385f73a29f31a6be49a221857";
    private String contentStr = "【东极圈】 您的验证码是";
    private String content = "";
    private static final int tag = 2;
    private int numberTest;   //验证码
    private String userPhoneString;

    private ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_find_pass);
        StatusBarUtil.setColor(this , getResources().getColor(R.color.ku_bg ) , 0);
        initView();
    }

    public void initView(){

        //返回
        back = (ImageView) findViewById(R.id.fidback);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        oldPhone = (EditText) findViewById(R.id.oldPhone);
        keyPhone = (EditText) findViewById(R.id.keyPhone);
        sendKey = (TextView) findViewById(R.id.sendKey);
        //发送验证码
        sendKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                content = "";
                userPhoneString = oldPhone.getText().toString();
                numberTest = nextInt(100000,999999);
                content = contentStr + numberTest + "，有效时间5分钟";

                sendSms(appKey,userPhoneString,content,tag);

                sendKey.setClickable(false);
                changeTime();

            }
        });
        newPass = (EditText) findViewById(R.id.newPass);
        newPassTwo = (EditText) findViewById(R.id.newPassTwo);
        findPass = (Button) findViewById(R.id.findPass);
        //找回密码
        findPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String pass1 = newPass.getText().toString().trim();
                String pass2 = newPassTwo.getText().toString().trim();
                if(pass1 == null || pass1 == ""){
                    Toast.makeText(FindPassActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }else if(pass2 == null || pass2 == ""){
                    Toast.makeText(FindPassActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }else if(!pass1.equals(pass2)){
                    Toast.makeText(FindPassActivity.this,"两次密码不一致",Toast.LENGTH_SHORT).show();
                    return;
                }else if(!String.valueOf(numberTest).equals(keyPhone.getText().toString())){
                    Toast.makeText(FindPassActivity.this,"验证码错误",Toast.LENGTH_SHORT).show();
                    return;
                }

                findPass(userPhoneString,pass1);
            }
        });
    }


    /**
     * 发送验证码
     */
    public void sendSms(String appKey,String userPhone,String content,int tag){
        Call<SmsTestResult> call = SmsModel.getModel().getService().getResult(appKey,userPhone,content,tag);

        call.enqueue(new Callback<SmsTestResult>() {
            @Override
            public void onResponse(Call<SmsTestResult> call, Response<SmsTestResult> response) {
                if (response.isSuccessful()) {
                    SmsTestResult result = response.body();
                    if (result.getMessage()=="ok") {

                    }else{

                    }
                }
            }

            @Override
            public void onFailure(Call<SmsTestResult> call, Throwable t) {
                Toast.makeText(FindPassActivity.this,"登录失败",Toast.LENGTH_SHORT).show();
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
     * 找回密码
     * @param userPhoneString
     * @param userPass
     */
    public void findPass(String userPhoneString,String userPass){
        Call<Li_UserBeanResult> call = MyModel.getModel().getService().FindUserPass(userPhoneString,userPass);

        call.enqueue(new Callback<Li_UserBeanResult>() {
            @Override
            public void onResponse(Call<Li_UserBeanResult> call, Response<Li_UserBeanResult> response) {
                if (response.isSuccessful()) {
                    Li_UserBeanResult result = response.body();
                    if (result.getCode() == 0) {
                        Intent intent = new Intent(FindPassActivity.this,LoginActivity.class);
                        startActivity(intent);
                        finish();

                    }else{
                        Toast.makeText(FindPassActivity.this,"找回失败",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Li_UserBeanResult> call, Throwable t) {
                Toast.makeText(FindPassActivity.this,"找回失败",Toast.LENGTH_SHORT).show();
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
