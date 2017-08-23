package com.weiaibenpao.demo.chislim.service;


import com.weiaibenpao.demo.chislim.bean.NewTeachResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lenovo on 2016/8/15.
 */

public interface NewTeachTab2Service {

    @GET("/Chislim/NewTeachServlet")
    Call<NewTeachResult> getResult(
            @Query("dowhat") String dowhat,
            @Query("tab2") int tab2);
}
