package com.weiaibenpao.demo.chislim.service;


import com.weiaibenpao.demo.chislim.bean.BooleanResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lenovo on 2016/8/15.
 */

public interface AddSportService {

    @GET("/Chislim/EveryDaySportServlet")
    Call<BooleanResult> getResult(
            @Query("dowhat") String dowhat,
            @Query("userID") int userID,
            @Query("dayTime") String dayTime,
            @Query("distance") int distance,
            @Query("calories") int calories,
            @Query("sportTime") int sportTime,
            @Query("sportImage") String sportImage,
            @Query("sportSpeed") String sportSpeed,
            @Query("sportStep") int sportStep);
}
