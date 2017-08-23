package com.weiaibenpao.demo.chislim.ui;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.weiaibenpao.demo.chislim.Interface.GetInterfaceBooleanListener;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.bean.UserBean;
import com.weiaibenpao.demo.chislim.util.GetIntentData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class UserBackActivity extends Activity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.userBack)
    EditText userBack;
    @BindView(R.id.submit)
    Button submit;

    @BindView(R.id.activity_user_back)
    RelativeLayout activityUserBack;

    GetIntentData getIntentData;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_back);
        ButterKnife.bind(this);
        getIntentData = new GetIntentData();
        context = getApplicationContext();
    }

    public void sentData() {
        if (userBack.getText().toString().trim() != "" && userBack.getText().toString().trim() != null) {
            getIntentData.addUserBack(context, UserBean.getUserBean().userId, "Android:    " + userBack.getText().toString().trim());
            getIntentData.setGetInterfaceBooleanListener(new GetInterfaceBooleanListener() {
                @Override
                public void getBoolean(boolean flag) {
                    Snackbar.make(activityUserBack,"提交成功,谢谢你的宝贵意见。",Snackbar.LENGTH_SHORT).show();
                    userBack.setText("");
                }
            });
        } else {
            Snackbar.make(activityUserBack,"请输入内容。。。",Snackbar.LENGTH_SHORT).show();
        }
    }

    @OnClick({R.id.back, R.id.submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.submit:
                sentData();
                break;
        }
    }
}
