package com.weiaibenpao.demo.chislim.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.adater.RecyclerTravelPictureAdapter;

public class TravelPictureActivity extends Activity {
    RecyclerTravelPictureAdapter mAdapter;
    RecyclerView recyclerView;
    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if ((getWindow().getDecorView().getSystemUiVisibility()
                & View.SYSTEM_UI_FLAG_FULLSCREEN) != 0) {
            getWindow().getDecorView().setSystemUiVisibility(0);
        } else {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_FULLSCREEN);
        }

        setContentView(R.layout.activity_travel_picture);

        Intent intent = getIntent();
        String[] temp = intent.getStringArrayExtra("picture");

        context = this.getApplicationContext();

        recyclerView = (RecyclerView) findViewById(R.id.traInfo);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL));
        mAdapter = new RecyclerTravelPictureAdapter(context, temp, 8);
        recyclerView.setAdapter(mAdapter);


        //点击事件
        RecyclerTravelPictureAdapter.setOnItemClickListener(new RecyclerTravelPictureAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, String[] temp) {
                finish();
            }

            @Override
            public void onItemLongClick(View view, int position, String[] temp) {

            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        mAdapter.setOnItemClickListener(null);

    }
}
