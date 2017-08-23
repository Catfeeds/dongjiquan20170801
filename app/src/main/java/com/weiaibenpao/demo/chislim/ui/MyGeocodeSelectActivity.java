package com.weiaibenpao.demo.chislim.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.amap.api.services.geocoder.GeocodeAddress;
import com.amap.api.services.geocoder.GeocodeQuery;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.jaeger.library.StatusBarUtil;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.bean.SpiceSportRemberBean;
import com.weiaibenpao.demo.chislim.util.ACache;
import com.weiaibenpao.demo.chislim.util.LatLong_impl;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyGeocodeSelectActivity extends Activity {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.cityFistName)
    EditText cityFistName;
    @BindView(R.id.verifyFirstImg)
    ImageView verifyFirstImg;
    @BindView(R.id.citySecondName)
    EditText citySecondName;
    @BindView(R.id.verifySecondImg)
    ImageView verifySecondImg;
    @BindView(R.id.goSport)
    Button goSport;
    @BindView(R.id.activity_my_geocode_select)
    RelativeLayout activityMyGeocodeSelect;

    GeocodeSearch geocoderSearchFirst;
    GeocodeSearch geocoderSearchSecond;
    GeocodeQuery queryFirst;
    GeocodeQuery querySecond;

    String fistCity;
    String secondCity;

    Context context;
    private ACache mCache;

    SpiceSportRemberBean spiceSportRemberBean;
    LatLong_impl latLong_impl;

    Intent getIntent;
    int page;
    boolean fisrtBoo = false;
    boolean secondBoo = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_geocode_select);
        StatusBarUtil.setColor(this , getResources().getColor(R.color.ku_bg ) , 0);
        ButterKnife.bind(this);
        context = getApplicationContext();
        spiceSportRemberBean = new SpiceSportRemberBean();
        latLong_impl = new LatLong_impl();
        EditTextLision();   //EditText焦点监听
        getGeocode();       //地理编码回调

        mCache = ACache.get(context);    //实例化缓存

        getIntent = getIntent();
        page = getIntent.getIntExtra("page",0);
    }

    /**
     * EditText焦点监听
     */
    public void EditTextLision(){
        //起始位置监听
        cityFistName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    // 此处为得到焦点时的处理内容

                } else {
                    // 此处为失去焦点时的处理内容
                    getFirstCity();
                }
            }
        });
        //结束位置监听
        citySecondName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    // 此处为得到焦点时的处理内容

                } else {
                    // 此处为失去焦点时的处理内容
                    getSecondCity();
                }
            }
        });
    }

    public void getSecondCity(){
        secondCity = citySecondName.getText().toString().trim();
        if(!secondCity.isEmpty()){
            //第一个参数为要查询的城市，第二个为查询的范围，为空则在全国查询
            querySecond = new GeocodeQuery(secondCity, "");
            geocoderSearchSecond.getFromLocationNameAsyn(querySecond);
            spiceSportRemberBean.setSecondCity(secondCity);
        }else{
            verifySecondImg.setImageResource(R.mipmap.falseimage);
            verifySecondImg.setVisibility(View.VISIBLE);
        }
    }

    public void getFirstCity(){
        fistCity = cityFistName.getText().toString().trim();
        if(!fistCity.isEmpty()){
            //第一个参数为要查询的城市，第二个为查询的范围，为空则在全国查询
            queryFirst = new GeocodeQuery(fistCity, "");
            geocoderSearchFirst.getFromLocationNameAsyn(queryFirst);
            spiceSportRemberBean.setFistCity(fistCity);
        }else{
            verifyFirstImg.setImageResource(R.mipmap.falseimage);
            verifyFirstImg.setVisibility(View.VISIBLE);
        }
    }


    /**
     * 地理编码
     */
    public void getGeocode(){
        geocoderSearchFirst = new GeocodeSearch(context);
        geocoderSearchFirst.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {

            }
            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
                Log.i("地理编码",i + "------");
                if (i == 0 || i==1000) {
                    if (geocodeResult != null && geocodeResult.getGeocodeAddressList() != null
                            && geocodeResult.getGeocodeAddressList().size() > 0) {
                        GeocodeAddress address = geocodeResult.getGeocodeAddressList().get(0);

                        spiceSportRemberBean.setFromLat(address.getLatLonPoint().getLatitude());
                        spiceSportRemberBean.setFromLong(address.getLatLonPoint().getLongitude());

                        verifyFirstImg.setImageResource(R.mipmap.trueimage);
                        verifyFirstImg.setVisibility(View.VISIBLE);
                        fisrtBoo = true;

                    } else {
                        verifyFirstImg.setImageResource(R.mipmap.falseimage);
                        verifyFirstImg.setVisibility(View.VISIBLE);
                        fisrtBoo = false;
                    }
                } else {
                    // ToastUtil.showerror(this, i);
                }
            }
        });

        geocoderSearchSecond = new GeocodeSearch(context);
        geocoderSearchSecond.setOnGeocodeSearchListener(new GeocodeSearch.OnGeocodeSearchListener() {
            @Override
            public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {

            }

            @Override
            public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {
                Log.i("地理编码",i + "------");
                if (i == 0|| i== 1000) {
                    if (geocodeResult != null && geocodeResult.getGeocodeAddressList() != null
                            && geocodeResult.getGeocodeAddressList().size() > 0) {
                        GeocodeAddress address = geocodeResult.getGeocodeAddressList().get(0);
                        spiceSportRemberBean.setToLat(address.getLatLonPoint().getLatitude());
                        spiceSportRemberBean.setToLong(address.getLatLonPoint().getLongitude());

                        verifySecondImg.setImageResource(R.mipmap.trueimage);
                        verifySecondImg.setVisibility(View.VISIBLE);
                        secondBoo = true;
                    } else {
                        verifySecondImg.setImageResource(R.mipmap.falseimage);
                        verifySecondImg.setVisibility(View.VISIBLE);
                        secondBoo = false;
                    }
                } else {
                    // ToastUtil.showerror(this, i);
                }
            }
        });
    }

    @OnClick({R.id.back, R.id.goSport})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.back:
                finish();
                break;
            case R.id.goSport:
                getFirstCity();
                getSecondCity();

                spiceSportRemberBean.setPosition(9999);
                spiceSportRemberBean.setDisNow(0);
                mCache.put("sportSpice",spiceSportRemberBean);

                if(fisrtBoo == true && secondBoo == true){
                    if (page == 0) {
                        Intent intent = new Intent(MyGeocodeSelectActivity.this, SportHomeDisSpiceActivity.class);
                        intent.putExtra("fistCity",spiceSportRemberBean.getFistCity());
                        intent.putExtra("secondCity",spiceSportRemberBean.getSecondCity());
                        intent.putExtra("position", spiceSportRemberBean.getPosition());
                        intent.putExtra("fromLat", spiceSportRemberBean.getFromLat());
                        intent.putExtra("fromLong", spiceSportRemberBean.getFromLong());
                        intent.putExtra("toLat", spiceSportRemberBean.getToLat());
                        intent.putExtra("toLong", spiceSportRemberBean.getToLong());
                        intent.putExtra("disNow", 0);

                        mCache.put("fistCity",spiceSportRemberBean.getFistCity());
                        mCache.put("secondCity",spiceSportRemberBean.getSecondCity());
                        mCache.put("position","0");
                        mCache.put("fromLat",String.valueOf(spiceSportRemberBean.getFromLat()));
                        mCache.put("fromLong",String.valueOf(spiceSportRemberBean.getFromLong()));
                        mCache.put("toLat",String.valueOf(spiceSportRemberBean.getToLat()));
                        mCache.put("toLong",String.valueOf(spiceSportRemberBean.getToLong()));
                        mCache.put("disNow","0");

                        startActivity(intent);
                        finish();
                    }
                    if (page == 1) {
                        Intent intent = new Intent(MyGeocodeSelectActivity.this, SportOutDisSpiceActivity.class);
                        intent.putExtra("fistCity",spiceSportRemberBean.getFistCity());
                        intent.putExtra("secondCity",spiceSportRemberBean.getSecondCity());
                        intent.putExtra("position", spiceSportRemberBean.getPosition());
                        intent.putExtra("fromLat", spiceSportRemberBean.getFromLat());
                        intent.putExtra("fromLong", spiceSportRemberBean.getFromLong());
                        intent.putExtra("toLat", spiceSportRemberBean.getToLat());
                        intent.putExtra("toLong", spiceSportRemberBean.getToLong());
                        intent.putExtra("disNow", 0);

                        mCache.put("fistCity",spiceSportRemberBean.getFistCity());
                        mCache.put("secondCity",spiceSportRemberBean.getSecondCity());
                        mCache.put("position","0");
                        mCache.put("fromLat",String.valueOf(spiceSportRemberBean.getFromLat()));
                        mCache.put("fromLong",String.valueOf(spiceSportRemberBean.getFromLong()));
                        mCache.put("toLat",String.valueOf(spiceSportRemberBean.getToLat()));
                        mCache.put("toLong",String.valueOf(spiceSportRemberBean.getToLong()));
                        mCache.put("disNow","0");

                        startActivity(intent);
                        finish();
                    }
                }
                break;
        }
    }

}
