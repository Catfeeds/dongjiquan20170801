package com.weiaibenpao.demo.chislim.service;


import com.weiaibenpao.demo.chislim.bean.MainBeanResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lenovo on 2016/8/15.
 */

public interface MainBeanService {

    @GET("/Chislim/ActivityServlet")
    Call<MainBeanResult> getResult(
            @Query("dowhat") String dowhat,
            @Query("talk") int talk,
            @Query("talkNum") int talkNum,
            @Query("activity") int activity,
            @Query("activityNum") int activityNum);
}
