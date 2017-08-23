package com.weiaibenpao.demo.chislim.model;


import com.weiaibenpao.demo.chislim.service.MusciSinerService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lenovo on 2016/8/15.
 */

public class MusicSinerModel {
    private MusciSinerService service;
    private static final String BASE_URL = "http://apis.baidu.com";

    //获取PhoneApi实例
    public static MusicSinerModel getModel(){
        return MusicSinerHolder.userModel;
    }

    //内部类实现单例模式，延迟加载，线程安全（java中class加载时互斥的）,也减少了内存消耗
    private static class MusicSinerHolder{
        private static MusicSinerModel userModel = new MusicSinerModel();//单例对象实例
    }

    private MusicSinerModel(){//private的构造函数用于避免外界直接使用new来实例化对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                //转换服务器数据到对象
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(MusciSinerService.class);
    }

    //获取PhoneService实例
    public MusciSinerService getService(){
        return service;
    }
}
