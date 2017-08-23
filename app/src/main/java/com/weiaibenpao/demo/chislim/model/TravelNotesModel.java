package com.weiaibenpao.demo.chislim.model;


import com.weiaibenpao.demo.chislim.service.TravelNotesService;
import com.weiaibenpao.demo.chislim.util.Default;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lenovo on 2016/8/15.
 */

public class TravelNotesModel {

    private TravelNotesService service;

    //获取PhoneApi实例
    public static TravelNotesModel getModel(){
        return TravelNotesHolder.TravelNotesModel;
    }

    //内部类实现单例模式，延迟加载，线程安全（java中class加载时互斥的）,也减少了内存消耗
    private static class TravelNotesHolder{
        private static TravelNotesModel TravelNotesModel = new TravelNotesModel();//单例对象实例
    }

    private TravelNotesModel(){//private的构造函数用于避免外界直接使用new来实例化对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Default.url)
                //转换服务器数据到对象
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(TravelNotesService.class);
    }

    //获取PhoneService实例
    public TravelNotesService getService(){
        return service;
    }
}
