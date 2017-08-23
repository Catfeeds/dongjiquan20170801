package com.weiaibenpao.demo.chislim.service;


import com.weiaibenpao.demo.chislim.bean.BooleanResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lenovo on 2016/8/15.
 */

public interface AddUserBackService {

    @GET("/Chislim/UserBackServlet")
    Call<BooleanResult> getResult(
            @Query("dowhat") String dowhat,
            @Query("userId") int userId,
            @Query("context") String context);
}
