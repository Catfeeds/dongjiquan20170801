package com.weiaibenpao.demo.chislim.service;


import com.weiaibenpao.demo.chislim.bean.GetIntId;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lenovo on 2016/8/15.
 */

public interface AddNotesThemeService {

    @GET("/Chislim/Travel_notes_Servlet")
    Call<GetIntId> getResult(
            @Query("dowhat") String dowhat,
            @Query("tn_title") String tn_title,
            @Query("tn_address") String tn_address,
            @Query("userID") int userID);
}
