package com.weiaibenpao.demo.chislim.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.lljjcoder.citypickerview.widget.CityPicker;
import com.weiaibenpao.demo.chislim.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zhangxing on 2017/1/21.
 */

public class SelectCitySportActivity extends Activity {
    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.titleTv)
    TextView titleTv;
    @BindView(R.id.submitTv)
    TextView submitTv;
    @BindView(R.id.myLine)
    View myLine;
    @BindView(R.id.myTop)
    RelativeLayout myTop;
    @BindView(R.id.tv_result)
    TextView tvResult;
    @BindView(R.id.select_city)
    Button selectCity;
   String city_selected;
    int page;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("onCreate","SelectCitySportActivity");
        setContentView(R.layout.select_city);
        StatusBarUtil.setColor(this , getResources().getColor(R.color.ku_bg ) , 0);
        ButterKnife.bind(this);
        page = getIntent().getIntExtra("page",0);
        submitTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(city_selected.equals("")||city_selected == null){
                    return ;
                }else{
                    if(page == 0){
                        //自定义室内行政跑
                        Intent intent = new Intent(SelectCitySportActivity.this, SportHomeDistrictActivity.class);
                        //intent.putExtra("position", position);
                        intent.putExtra("provinceName",city_selected);
                        startActivity(intent);
                        finish();
                    }

                    if(page == 1){
                        //自定义室外行政跑
                        Intent intent = new Intent(SelectCitySportActivity.this, SportOutDistrictActivity.class);
                        //intent.putExtra("position", position);
                        intent.putExtra("provinceName",city_selected);
                        startActivity(intent);
                        finish();
                    }
                }
            }
        });
    }

    @OnClick({R.id.back, R.id.select_city})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.select_city:
                tvResult.setVisibility(View.VISIBLE);
                CityPicker cityPicker = new CityPicker.Builder(SelectCitySportActivity.this).textSize(20)
                        .title("")
                        .titleTextColor("#000000")
                        .backgroundPop(0xa0000000)
                        .titleBackgroundColor("#f26a44")
                        .confirTextColor("#000000")
                        .cancelTextColor("#000000")
                        .province("湖北省")
                        .city("武汉市")
                        .district("洪山区")
                        .textColor(Color.parseColor("#000000"))
                        .provinceCyclic(true)
                        .cityCyclic(false)
                        .districtCyclic(false)
                        .visibleItemsCount(7)
                        .itemPadding(10)
                        .build();

                cityPicker.show();
                cityPicker.setOnCityItemClickListener(new CityPicker.OnCityItemClickListener() {
                    @Override
                    public void onSelected(String... citySelected) {
                        tvResult.setText("当前选择的城市：\n省：" + citySelected[0] + "\n市：" + citySelected[1] + "\n区："
                                + citySelected[2] + "\n邮编：" + citySelected[3]);
                        city_selected = citySelected[2];
                    }
                });

                break;
        }
    }
}
