package com.weiaibenpao.demo.chislim.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.umeng.socialize.Config;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.bean.Li_UserBeanResult;
import com.weiaibenpao.demo.chislim.bean.UserBean;
import com.weiaibenpao.demo.chislim.hurui.utils.AppSPUtils;
import com.weiaibenpao.demo.chislim.model.MyModel;
import com.weiaibenpao.demo.chislim.util.ProgressDialog;
import com.weiaibenpao.demo.chislim.util.SharedPrefsUtil;
import com.weiaibenpao.demo.chislim.util.WriteReadSharedPrefs;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.umeng.socialize.bean.SHARE_MEDIA.QQ;


public class LoginActivity extends AppCompatActivity {


    private EditText userNumber;
    private EditText userPass;
    private Button myLogin;
    private TextView registText;
    private TextView findPasss;

    public static final String PREFS_NAME = "UserInfo";

    private ImageView myImageAnmi;


    Context context;
    //-----------------------------------------------------------
    /**
     * 首先获取UMShareAPI
     */
    UMShareAPI mShareAPI;

    SHARE_MEDIA platform;

    UserBean userBean;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.ku_bg), 0);
        //获取SharedPreferences对象，第一个参数文件名，第二个参数值
        context = getApplicationContext();

        userBean = UserBean.getUserBean();

        progressDialog = new ProgressDialog(this);
        //如果文件中有用户数据保存，直接进入首页
        if (SharedPrefsUtil.getValue(context, WriteReadSharedPrefs.PREFS_NAME, "content", false)) {
            //if(settings.getBoolean("content",false)){

            Intent intent = new Intent(LoginActivity.this, MainActivity.class);

            startActivity(intent);

            finish();
        }

        //实例化控件
        initView();

        /**
         * 首先获取UMShareAPI
         */
        mShareAPI = UMShareAPI.get(context);


        //微博的回调地址
        Config.REDIRECT_URL = "http://www.chislim.com";

        //第三方登录跳转回登录界面展示动画
        myImageAnmi = (ImageView) findViewById(R.id.myImageAnmi);
    }

    /**
     * 授权监听
     */
    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(final SHARE_MEDIA platform, int action, Map<String, String> data) {
            Log.i("授权", "------------------succeed----------------");
            /*初始化UMShareAPI，然后进行用户信息获取：*/
            //获取用户信息,
            /**
             * 新浪微博获取用户信息
             */

            /**
             * QQ   和  微信   获取信息
             */
            mShareAPI.getPlatformInfo(LoginActivity.this, platform, new UMAuthListener() {

                @Override
                public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> map) {

                   /*Set<String> set = map.keySet();
                        for (String string : set) {
                        Log.i("msg", "=======================Map=========================");
                        Log.i("msg", string + "::::" + map.get(string));
                            Toast.makeText(LoginActivity.this,i + "------------------获取数据成功----------------",Toast.LENGTH_SHORT).show();
                        String str = map.get(string);
                        // 设置头像
                        }*/

                    switch (platform) {
                        case QQ:
                            userBean.userImage = map.get("profile_image_url");
                            userBean.userName = map.get("screen_name");
                            userBean.userOpenid = map.get("openid");
                            userBean.userSex = map.get("gender");

                            getUserByThree(map.get("screen_name"), map.get("openid"), map.get("profile_image_url"), map.get("gender"), "", "QQ");
                            break;
                        case SINA:
                            userBean.userImage = map.get("profile_image_url");
                            userBean.userName = map.get("screen_name");
                            userBean.userOpenid = map.get("id");
                            //user.userSex = map.get()
                            getUserByThree(map.get("screen_name"), map.get("id"), map.get("profile_image_url"), "", "", "weibo");
                            break;
                        case WEIXIN:
                            userBean.userImage = map.get("profile_image_url");
                            userBean.userName = map.get("screen_name");
                            userBean.userOpenid = map.get("openid");
                            getUserByThree(map.get("screen_name"), map.get("openid"), map.get("profile_image_url"), "", "", "weixin");
                            break;
                    }
                }

                @Override
                public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                    Toast.makeText(LoginActivity.this, i + "------------------获取数据失败----------------", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onCancel(SHARE_MEDIA share_media, int i) {
                    Toast.makeText(LoginActivity.this, i + "------------------获取数据取消----------------", Toast.LENGTH_SHORT).show();
                }
            });
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Log.i("授权", action + "------------------失败----------------");
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Log.i("授权", "------------------取消----------------");
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {

            if (mShareAPI != null) mShareAPI.onActivityResult(requestCode, resultCode, data);
            // mShareAPI.onActivityResult(requestCode, resultCode, data);
            Log.i("第三方登录回调", requestCode + " " + resultCode + " " + data.toString() + "=========");

//            myImageAnmi.setBackgroundResource(R.drawable.frame);
            //启动动画
       /* AnimationDrawable anim = (AnimationDrawable) myImageAnmi.getBackground();
        anim.start();*/
            progressDialog.showDialog();
        }
    }

    public void initView() {

        userNumber = (EditText) findViewById(R.id.userNumber);
        userPass = (EditText) findViewById(R.id.userPass);
        myLogin = (Button) findViewById(R.id.myLogin);
        //登陆监听
        myLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String userPhoneStr = userNumber.getText().toString();
                String userPassStr = userPass.getText().toString();
                if (userPhoneStr == "" || userPhoneStr == null) {
                    Toast.makeText(LoginActivity.this, "电话号码不能为空", Toast.LENGTH_SHORT).show();
                } else if (userPhoneStr.length() != 11) {
                    Toast.makeText(LoginActivity.this, "请输入正确的手机号码", Toast.LENGTH_SHORT).show();
                }
                progressDialog.showDialog();
                //调用电话登录
                loginByPhone(userPhoneStr, userPassStr);
            }
        });
        registText = (TextView) findViewById(R.id.registText);
        //前往注册跳转
        registText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegistActivity.class);
                startActivity(intent);
                finish();
            }
        });
        findPasss = (TextView) findViewById(R.id.findPasss);
        //找回密码
        findPasss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, FindPassActivity.class);
                startActivity(intent);
            }
        });

    }

    /**
     * 通过电话登录
     */
    public void loginByPhone(String userPhone, String userPass) {
        this.getUserByPhone(userPhone, userPass);
    }

    /**
     * QQ登录
     *
     * @param v
     */
    public void loginByQq(View v) {
        /*获取客户端安装信息*/
        boolean qqBoolean = mShareAPI.isInstall(this, QQ);
        /*选取需要授权的平台，并进行授权，其中umAuthLisrener是回调监听器*/
        if (qqBoolean) {
            platform = QQ;
            mShareAPI.doOauthVerify(this, platform, umAuthListener);
        }
    }

    /**
     * 微博登录
     *
     * @param v
     */
    public void loginByWbo(View v) {
        /*获取客户端安装信息*/
        boolean wbBoolean = mShareAPI.isInstall(this, SHARE_MEDIA.SINA);
        /*选取需要授权的平台，并进行授权，其中umAuthLisrener是回调监听器*/
        //if(wbBoolean){
        platform = SHARE_MEDIA.SINA;
        mShareAPI.doOauthVerify(this, platform, umAuthListener);
        //}
    }

    /**
     * 微信登陆
     *
     * @param v
     */
    public void loginByWxin(View v) {
        //判断是否安装了客户端
        boolean wxBoolean = mShareAPI.isInstall(this, SHARE_MEDIA.WEIXIN);
        /*选取需要授权的平台，并进行授权，其中umAuthLisrener是回调监听器*/
        if (wxBoolean == true) {
            platform = SHARE_MEDIA.WEIXIN;
            mShareAPI.doOauthVerify(this, platform, umAuthListener);
        }
    }

    /**
     * 通过电话号码查询服务器端
     */
    public void getUserByPhone(final String userPhone, String userPass) {

        Call<Li_UserBeanResult> call = MyModel.getModel().getService().getLoginByPhone(userPhone, userPass);

        call.enqueue(new Callback<Li_UserBeanResult>() {
            @Override
            public void onResponse(Call<Li_UserBeanResult> call, Response<Li_UserBeanResult> response) {
                if (response.isSuccessful()) {
                    final Li_UserBeanResult result = response.body();
                    if (result.getCode() == 0) {

                        /*userBean.userPhoone = userPhone;
                        userBean.userId = result.initData().getUserID();*/

                        //向文件中写入数据
                        //writeUser(result.initData());
                        if(progressDialog != null )progressDialog.cancelDialog();//added by zjl for fixing bug of window leaked
                        WriteReadSharedPrefs.writeUser(context, result.getData());
                        WriteReadSharedPrefs.readUser(context, userBean);
                        AppSPUtils.put(LoginActivity.this, "name", result.getData().getUserName());
                        AppSPUtils.put(LoginActivity.this, "logoUrl", result.getData().getId());

                        goToMain();
                    } else {
                        Toast.makeText(LoginActivity.this, "账号或密码错误", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Li_UserBeanResult> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 通过第三方登录
     */
    public void getUserByThree(final String userName, final String openId, final String userImage, final String userSex, final String userBirth, String sourse) {
        Call<Li_UserBeanResult> call = MyModel.getModel().getService().getThreeRegistResult(openId, sourse, userImage, userName, userSex, userBirth);

        call.enqueue(new Callback<Li_UserBeanResult>() {
            @Override
            public void onResponse(Call<Li_UserBeanResult> call, Response<Li_UserBeanResult> response) {
                if (response.isSuccessful()) {
                    final Li_UserBeanResult result = response.body();
                    AppSPUtils.put(LoginActivity.this, "name", UserBean.getUserBean().userName);
                    AppSPUtils.put(LoginActivity.this, "logoUrl", UserBean.getUserBean().userImage);
                    if (result.getCode() == 0) {
                        //向文件中写入数据
                        // writeUser(result.initData());
                       /* userBean.userName = userName;
                        userBean.userOpenid = openId;
                        userBean.userImage = userImage;
                        userBean.userSex = userSex;
                        userBean.userBirth = userBirth;
                        userBean.userId = result.initData().getUserID();*/
                        //写数据
                        WriteReadSharedPrefs.writeUser(context, result.getData());
                        //读到单例中
                        WriteReadSharedPrefs.readUser(context, userBean);
                        goToMain();
                    } else if (result.getCode() == 1) {
                        goToMain();
                        // TODO: 2017/7/17   李建宝 添加
                        //写数据
                        WriteReadSharedPrefs.writeUser(context, result.getData());
                        //读到单例中
                        WriteReadSharedPrefs.readUser(context, userBean);
                    } else {
                        Toast.makeText(LoginActivity.this, "登录失败1", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<Li_UserBeanResult> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "登录失败2", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void goToMain() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if(progressDialog != null ){
//            progressDialog.cancelDialog();
//            progressDialog = null ;
//        }
    }
}
