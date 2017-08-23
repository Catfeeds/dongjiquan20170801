package com.weiaibenpao.demo.chislim.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.jaeger.library.StatusBarUtil;
import com.weiaibenpao.demo.chislim.Interface.GetInterfaceIntIdListener;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.bean.UserBean;
import com.weiaibenpao.demo.chislim.util.GetIntentData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AddThemeActivity extends Activity {

    GetIntentData getIntentData;
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.themeTitle)
    EditText themeTitle;
    @BindView(R.id.themeAddress)
    EditText themeAddress;
    @BindView(R.id.themeIntro)
    EditText themeIntro;
    @BindView(R.id.submit)
    Button submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_theme);
        StatusBarUtil.setColor(this , getResources().getColor(R.color.ku_bg ) , 0);
        ButterKnife.bind(this);
        getIntentData = new GetIntentData();
    }



    @OnClick({R.id.back, R.id.submit})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.submit:
                getIntentData.AddNotesThemeUpLoad(getApplicationContext(), "addOneNotes", themeTitle.getText().toString().trim(), themeAddress.getText().toString().trim(), UserBean.getUserBean().userId);
                getIntentData.setGetInterfaceIntIdListener(new GetInterfaceIntIdListener() {
                    @Override
                    public void getDateIntId(int intId) {
                    }
                });
                break;
        }
    }
}
