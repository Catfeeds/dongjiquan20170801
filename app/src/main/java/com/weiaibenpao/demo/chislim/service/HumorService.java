package com.weiaibenpao.demo.chislim.service;


import com.weiaibenpao.demo.chislim.bean.HumorResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lenovo on 2016/8/15.themeTitleStr=&nowUserId=0&page=0&size=10
 */

public interface HumorService {
    @GET("/chislim2/humor/list")
    Call<HumorResult> getResult(
            @Query("themeTitleStr") String themeTitleStr,
            @Query("nowUserId") int nowUserId,
            @Query("page") int page,
            @Query("size") int size);
}
