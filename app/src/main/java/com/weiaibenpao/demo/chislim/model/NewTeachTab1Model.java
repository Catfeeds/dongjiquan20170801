package com.weiaibenpao.demo.chislim.model;


import com.weiaibenpao.demo.chislim.service.NewTeachTab1Service;
import com.weiaibenpao.demo.chislim.util.Default;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lenovo on 2016/8/15.
 */

public class NewTeachTab1Model {

    private NewTeachTab1Service service;

    //获取PhoneApi实例
    public static NewTeachTab1Model getModel(){
        return NewTeachTab1Holder.NewTeachModel;
    }

    //内部类实现单例模式，延迟加载，线程安全（java中class加载时互斥的）,也减少了内存消耗
    private static class NewTeachTab1Holder{
        private static NewTeachTab1Model NewTeachModel = new NewTeachTab1Model();//单例对象实例
    }

    private NewTeachTab1Model(){//private的构造函数用于避免外界直接使用new来实例化对象
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Default.url)
                //转换服务器数据到对象
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        service = retrofit.create(NewTeachTab1Service.class);
    }

    //获取PhoneService实例
    public NewTeachTab1Service getService(){
        return service;
    }
}
