package com.weiaibenpao.demo.chislim.service;


import com.weiaibenpao.demo.chislim.bean.BooleanResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lenovo on 2016/8/15.
 */

public interface GetStartService {

    @GET("/Chislim/Support_Servlet")
    Call<BooleanResult> getResult(
            @Query("dowhat") String dowhat,
            @Query("userID") int userID,
            @Query("tn_id") int tn_id,
            @Query("tab2") int tab2);
}
