package com.weiaibenpao.demo.chislim.service;


import com.weiaibenpao.demo.chislim.bean.TravelResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lenovo on 2016/8/15.
 */

public interface TravelService {

    @GET("/Chislim/Travel_Servlet")
    Call<TravelResult> getResult(
            @Query("dowhat") String dowhat,
            @Query("tab") String tab,
            @Query("i") int i,
            @Query("num") int num);
}
