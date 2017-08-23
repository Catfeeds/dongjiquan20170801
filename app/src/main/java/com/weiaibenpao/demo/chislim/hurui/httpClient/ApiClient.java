package com.weiaibenpao.demo.chislim.hurui.httpClient;

import com.weiaibenpao.demo.chislim.hurui.utils.AppNetUtil;
import com.weiaibenpao.demo.chislim.sportoutdoor.presentation.module.PedometerApplication;
import com.weiaibenpao.demo.chislim.util.Default;

import java.io.File;
import java.io.IOException;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lenovo on 2017/4/10.
 */

public class ApiClient {

    private HHttpInterface hHttpInterface ;

    public static ApiClient getModel(){
        return ApiClientHolder.userModel;
    }

    //内部类实现单例模式，延迟加载，线程安全（java中class加载时互斥的）,也减少了内存消耗
    private static class ApiClientHolder{
        private static ApiClient userModel = new ApiClient();//单例对象实例
    }

    private ApiClient(){//private的构造函数用于避免外界直接使用new来实例化对象
        OkHttpClient.Builder builder = new OkHttpClient.Builder() ;
        File cacheFile = new File(PedometerApplication.app.getExternalCacheDir(),"hr_cache");
        Cache cache = new Cache(cacheFile, 1024 * 1024 * 50);
        Interceptor cacheInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!AppNetUtil.isHaveNet(PedometerApplication.app)) {
                    request = request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build();
                }
                Response response = chain.proceed(request);
                if (AppNetUtil.isHaveNet(PedometerApplication.app)) {
                    int maxAge = 0;
                    // 有网络时 设置缓存超时时间0个小时
                    response.newBuilder()
                            .header("Cache-Control", "public, max-age=" + maxAge)
                            .removeHeader("WuXiaolong")// 清除头信息，因为服务器如果不支持，会返回一些干扰信息，不清除下面无法生效
                            .build();
                } else {
                    // 无网络时，设置超时为4周
                    int maxStale = 60 * 60 * 24 * 14;
                    response.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                            .removeHeader("nyn")
                            .build();
                }
                return response;
            }
        };
        builder.cache(cache).addInterceptor(cacheInterceptor);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Default.url)
                .client(builder.build())
                //转换服务器数据到对象
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        hHttpInterface = retrofit.create(HHttpInterface.class);
    }

    //获取PhoneService实例
    public HHttpInterface getService(){
        return hHttpInterface;
    }
}
