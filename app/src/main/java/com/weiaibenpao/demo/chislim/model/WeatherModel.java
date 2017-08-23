package com.weiaibenpao.demo.chislim.model;


import com.weiaibenpao.demo.chislim.service.WeatherService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lenovo on 2016/8/15.
 */

public class WeatherModel {
    private WeatherService service;

    private static final String BASE_URL = "http://op.juhe.cn";

    //获取PhoneApi实例
    public static WeatherModel getModel(){
        return WeatherHolder.userModel;
    }

    //内部类实现单例模式，延迟加载，线程安全（java中class加载时互斥的）,也减少了内存消耗
    private static class WeatherHolder{
        private static WeatherModel userModel = new WeatherModel();//单例对象实例
    }

    private WeatherModel(){//private的构造函数用于避免外界直接使用new来实例化对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                //转换服务器数据到对象
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(WeatherService.class);
    }

    //获取PhoneService实例
    public WeatherService getService(){
        return service;
    }
}
