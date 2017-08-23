package com.weiaibenpao.demo.chislim.service;

import com.weiaibenpao.demo.chislim.bean.ResultBean;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by zhangxing on 2017/3/9.
 */

public interface PostService {

    @POST("api")
    @FormUrlEncoded
    Call<ResultBean> getResult(@Field("key") String key, @Field("info") String info);
}
