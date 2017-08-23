package com.weiaibenpao.demo.chislim.model;


import com.weiaibenpao.demo.chislim.service.UpdateUserService;
import com.weiaibenpao.demo.chislim.util.Default;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lenovo on 2016/8/15.
 */

public class UpdateUserModel {

    private UpdateUserService service;

    //获取PhoneApi实例
    public static UpdateUserModel getModel(){
        return UpdateUserHolder.userModel;
    }

    //内部类实现单例模式，延迟加载，线程安全（java中class加载时互斥的）,也减少了内存消耗
    private static class UpdateUserHolder{
        private static UpdateUserModel userModel = new UpdateUserModel();//单例对象实例
    }

    private UpdateUserModel(){//private的构造函数用于避免外界直接使用new来实例化对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Default.url)
                //转换服务器数据到对象
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(UpdateUserService.class);
    }

    //获取PhoneService实例
    public UpdateUserService getService(){
        return service;
    }
}
