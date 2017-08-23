package com.weiaibenpao.demo.chislim.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.bean.UserBean;

public class UserInfoWordSetActivity extends Activity {

    Intent intent;
    String title;
    String word;
    String oldWord;

    EditText wordText;
    TextView wordIntro;
    Button commit;
    ImageView back;
    TextView titleTv;

    /*
    1  修改昵称
    2  修改爱好
    3  修改签名
     */
    int tab = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info_word_set);

        intent = getIntent();
        title = intent.getStringExtra("title");
        word = intent.getStringExtra("word");
        tab = intent.getIntExtra("tab",1);
        oldWord = intent.getStringExtra("oldWord");
        initView();
    }

    public void initView(){
        wordText = (EditText) findViewById(R.id.wordText);
        if(oldWord == null || oldWord == ""){
            wordText.setHint("请输入您的" + title);
        }else{
            wordText.setText(oldWord);
        }

        wordIntro = (TextView) findViewById(R.id.wordIntro);
        wordIntro.setText(word);

        titleTv = (TextView) findViewById(R.id.titleTv);
        titleTv.setText(title);

        commit = (Button) findViewById(R.id.commit);
        commit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(tab == 1){
                    UserBean.getUserBean().userName = wordText.getText().toString().trim();
                }else if(tab == 2){
                    UserBean.getUserBean().userHobby = wordText.getText().toString().trim();
                }else if(tab == 3){
                    UserBean.getUserBean().userTntru = wordText.getText().toString().trim();
                }
                finish();
            }
        });

        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
