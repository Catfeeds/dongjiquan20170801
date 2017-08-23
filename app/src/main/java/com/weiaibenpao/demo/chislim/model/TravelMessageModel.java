package com.weiaibenpao.demo.chislim.model;

import com.weiaibenpao.demo.chislim.service.TravelMesageService;
import com.weiaibenpao.demo.chislim.util.Default;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lenovo on 2016/8/15.
 */

public class TravelMessageModel {

    private TravelMesageService service;

    //获取PhoneApi实例
    public static TravelMessageModel getModel(){
        return TravelMessageHolder.userModel;
    }

    //内部类实现单例模式，延迟加载，线程安全（java中class加载时互斥的）,也减少了内存消耗
    private static class TravelMessageHolder{
        private static TravelMessageModel userModel = new TravelMessageModel();//单例对象实例
    }

    private TravelMessageModel(){//private的构造函数用于避免外界直接使用new来实例化对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Default.url)
                //转换服务器数据到对象
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(TravelMesageService.class);
    }

    //获取PhoneService实例
    public TravelMesageService getService(){
        return service;
    }
}
