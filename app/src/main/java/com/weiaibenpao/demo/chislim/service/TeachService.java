package com.weiaibenpao.demo.chislim.service;


import com.weiaibenpao.demo.chislim.bean.TeachResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lenovo on 2016/8/15.
 */

public interface TeachService {

    @GET("/Chislim/TeachServlet")
    Call<TeachResult> getResult(
            @Query("dowhat") String dowhat);
}
