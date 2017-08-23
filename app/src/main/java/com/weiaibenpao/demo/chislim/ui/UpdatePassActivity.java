package com.weiaibenpao.demo.chislim.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.bean.Li_UserBeanResult;
import com.weiaibenpao.demo.chislim.bean.SmsTestResult;
import com.weiaibenpao.demo.chislim.bean.UserResult;
import com.weiaibenpao.demo.chislim.model.MyModel;
import com.weiaibenpao.demo.chislim.model.SmsModel;
import com.weiaibenpao.demo.chislim.model.UserModel;
import com.weiaibenpao.demo.chislim.util.Default;
import com.weiaibenpao.demo.chislim.util.SharedPrefsUtil;
import com.weiaibenpao.demo.chislim.util.WriteReadSharedPrefs;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdatePassActivity extends AppCompatActivity {

    private EditText config_pass;
    private EditText newPass;
    private EditText newPassTwo;
    private Button updatePass;

    private String config_pass_Str;
    private String newPassStr;
    private String newPassStrTwo;
    private int userId;                             //值从本地文件获得，暂时未得到
    private String userPhone;                       //值从本地文件获得，暂时未得到
    private ImageView back;

    Context context;
    private EditText phone_et;
    private String contentStr = "【东极圈】 您的验证码是";
    private String content = "";
    private static final int tag = 2;
    private int numberTest;
    //验证码师傅正确
    private boolean isConfigCode=false;
    private Button sendSms_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_update_pass);
        context = getApplicationContext();

        userPhone = SharedPrefsUtil.getValue(context,WriteReadSharedPrefs.PREFS_NAME,"userPhone","");
        userId = SharedPrefsUtil.getValue(context,WriteReadSharedPrefs.PREFS_NAME,"userId",0);

        initView();
    }

    public void initView(){
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        newPass = (EditText) findViewById(R.id.newPass);

        newPassTwo = (EditText) findViewById(R.id.newPassTwo);



        updatePass = (Button) findViewById(R.id.updatePass);
        config_pass = (EditText) findViewById(R.id.config_pass);
        //输入书记号码
        phone_et = ((EditText) findViewById(R.id.phone_et));
        sendSms_btn = ((Button) findViewById(R.id.sendSms));

        //点击修改密码
        updatePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newPassStr = newPass.getText().toString().trim();
                newPassStrTwo = newPassTwo.getText().toString().trim();
                /*拿到user中的对象，先进行原始密码的验证，通过后进行新密码的修改*/

                   if (TextUtils.isEmpty(numberTest+"")) {
                       Toast.makeText(context,"验证码不能为空",Toast.LENGTH_SHORT).show();
                       return;
                   }else if (!config_pass.getText().toString().trim().equals(numberTest+"")){
                       Toast.makeText(context,"验证码输入有误",Toast.LENGTH_SHORT).show();
                       return;
                   }
                if(newPassStr == null || newPassStrTwo ==""){
                    Toast.makeText(UpdatePassActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }else if(newPassStrTwo == null || newPassStrTwo ==""){
                    Toast.makeText(UpdatePassActivity.this,"密码不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }else if(!newPassStr.equals(newPassStrTwo)){
                    Toast.makeText(UpdatePassActivity.this,"两次密码不一致",Toast.LENGTH_SHORT).show();
                    return;
                }
                //修改密码
                updatePass(context,userId,newPassStr);
                //原始密码的验证
//                getUserByPhone(userPhone,oldPassStr);

            }
        });
    }
    //点击发送验证码
    public void send_message(View v){
        String phone = phone_et.getText().toString().trim();
        if (TextUtils.isEmpty(phone)){
            Toast.makeText(UpdatePassActivity.this,"手机号码不能为空号",Toast.LENGTH_SHORT).show();
            return;
        }

        //获取6位随机数
        numberTest = nextInt(100000,999999);
        //拼接成验证码
        content = contentStr+numberTest + "，有效时间5分钟";
        sendSms(phone_et.getText().toString().trim(),content,tag);
        sendSms_btn.setClickable(false);
        changeTime();

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

                        handler.sendMessage(msg);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }
        }).start();
    }

    Handler handler = new Handler( ){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            sendSms_btn.setText(msg.arg1 + " 秒后重新发送");
            if(msg.arg1 == 0){
                sendSms_btn.setText("重新发送验证码");
                sendSms_btn.setClickable(true);
            }
        }
    };
    /**
     * 发送验证码
     */
    public void sendSms(String userPhone, final String content, int tag){
        Call<SmsTestResult> call = SmsModel.getModel().getService().getResult(Default.baiduKey,userPhone,content,tag);

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

            }
        });
    }
    /**
     * 修改密码
     * @param userID
     * @param userPass
     */
    public void updatePass(final Context context, int userID, String userPass){
        Call<Li_UserBeanResult> call = MyModel.getModel().getService().updateUserPass(userID,userPass);

        call.enqueue(new Callback<Li_UserBeanResult>() {
            @Override
            public void onResponse(Call<Li_UserBeanResult> call, Response<Li_UserBeanResult> response) {
                if (response.isSuccessful()) {
                    Li_UserBeanResult result = response.body();
                    if (result.getCode() == 0) {
                      /*  //获得SharedPreferences.Editor对象，使SharedPreferences对象变为可编辑状态（生成编辑器）
                        SharedPreferences.Editor edit = settings.edit();
                        //清除文件内容
                        edit.clear();
                        //提交
                        edit.commit();*/
                        //设置标示为无用户登陆状态
                        SharedPrefsUtil.putValue(context, WriteReadSharedPrefs.PREFS_NAME,"content",false);
                        Intent intent = new Intent(UpdatePassActivity.this,LoginActivity.class);
                        startActivity(intent);

                    }else{
                        Toast.makeText(UpdatePassActivity.this,"修改失败",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Li_UserBeanResult> call, Throwable t) {
                Toast.makeText(UpdatePassActivity.this,"修改失败",Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 通过电话号码查询服务器端原始密码的验证
     */
    public void getUserByPhone(String userPhone,String userPass){
        Call<UserResult> call = UserModel.getModel().getService().getResult(Default.getOneByPhone,userPhone,userPass);

        call.enqueue(new Callback<UserResult>() {
            @Override
            public void onResponse(Call<UserResult> call, Response<UserResult> response) {
                if (response.isSuccessful()) {
                    UserResult result = response.body();
                    Toast.makeText(UpdatePassActivity.this,result.getError()+"",Toast.LENGTH_SHORT).show();
                    if (result.getError() == 0) {
                        updatePass(context,userId,newPassStr);

                    }else{
                        Toast.makeText(UpdatePassActivity.this,"原始密码错误",Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserResult> call, Throwable t) {
                Toast.makeText(UpdatePassActivity.this,"原始密码错误",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
