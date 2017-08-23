package com.weiaibenpao.demo.chislim.service;


import com.weiaibenpao.demo.chislim.bean.TravelNotesResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lenovo on 2016/8/15.
 */

public interface TravelNotesService {

    @GET("/Chislim/Travel_notes_Servlet")
    Call<TravelNotesResult> getResult(
            @Query("dowhat") String dowhat,
            @Query("i") int i,
            @Query("num") int num);
}
