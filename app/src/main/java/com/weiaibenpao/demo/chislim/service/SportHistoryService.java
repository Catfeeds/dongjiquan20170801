package com.weiaibenpao.demo.chislim.service;


import com.weiaibenpao.demo.chislim.bean.SportHistoryResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lenovo on 2016/8/15.
 */

public interface SportHistoryService {

    @GET("/Chislim/EveryDaySportServlet")
    Call<SportHistoryResult> getResult(
            @Query("dowhat") String dowhat,
            @Query("userID") int userID,
            @Query("n") int n,
            @Query("m") int m);
}
