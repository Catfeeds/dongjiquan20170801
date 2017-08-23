package com.weiaibenpao.demo.chislim.service;


import com.weiaibenpao.demo.chislim.bean.BooleanResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lenovo on 2016/8/15.
 */

public interface TravelMesageService {

    @GET("/Chislim/Travel_message_Servlet")
    Call<BooleanResult> getResult(
            @Query("dowhat") String dowhat,
            @Query("tra_tm_id") int tra_tm_id,
            @Query("tn_id") int tn_id,
            @Query("userID") int userID,
            @Query("tm_text") String tm_text);
}
