package com.weiaibenpao.demo.chislim.service;


import com.weiaibenpao.demo.chislim.bean.TravelInfoResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lenovo on 2016/8/15.
 */

public interface TravelInfoService {

    @GET("/Chislim/TravelInfo_servlet")
    Call<TravelInfoResult> getResult(
            @Query("dowhat") String dowhat,
            @Query("t_id") int t_id);
}
