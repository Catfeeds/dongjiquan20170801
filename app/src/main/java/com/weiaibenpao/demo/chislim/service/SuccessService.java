package com.weiaibenpao.demo.chislim.service;


import com.weiaibenpao.demo.chislim.bean.SuccessResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lenovo on 2016/8/15.
 */

public interface SuccessService {

    @GET("/Chislim/MySuccessServlet")
    Call<SuccessResult> getResult(
            @Query("dowhat") String dowhat,
            @Query("userID") int userID);
}
