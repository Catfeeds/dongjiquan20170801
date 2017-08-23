package com.weiaibenpao.demo.chislim.ui;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import com.jaeger.library.StatusBarUtil;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.adater.DisListAdapter;
import com.weiaibenpao.demo.chislim.util.Default;

import java.util.ArrayList;

public class DistenceActivity extends AppCompatActivity {

    ArrayList disList;
    ListView disListView;
    ImageView back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_distence);
        StatusBarUtil.setColor(this , getResources().getColor(R.color.ku_bg ) , 0);

        initDate();
        initView();

        /*ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                R.layout.disitem, disList);*/

        DisListAdapter disListAdapter = new DisListAdapter(this,disList);

        disListView.setAdapter(disListAdapter);
        disListView.setItemsCanFocus(false);// ListView的item获得焦点
        disListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);//设置为单选模式
        //disListView.setDivider(null);// 去除每条记录的分割线
        disListView.setDivider(new ColorDrawable(getResources().getColor(R.color.backLine)));
        disListView.setDividerHeight(2);

        disListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("DistenceActivity",i+" --------------------------");
                Intent intent = new Intent();
                intent.putExtra("position",Integer.parseInt(disList.get(i).toString()));
                setResult(2,intent);
                finish();
            }
        });
    }

    /**
     * 第一步定义数据源
     */
    public void initDate(){
        disList = Default.initList();
    }

    /**
     * 第二部实例化控件
     */
    public void initView(){
        disListView = (ListView) findViewById(R.id.disListView);
        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
