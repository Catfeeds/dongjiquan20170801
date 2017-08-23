package com.weiaibenpao.demo.chislim.service;


import com.weiaibenpao.demo.chislim.bean.NotesMessageResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lenovo on 2016/8/15.
 */

public interface NotesMessageService {

    @GET("/Chislim/Travel_message_Servlet")
    Call<NotesMessageResult> getResult(
            @Query("dowhat") String dowhat,
            @Query("i") int i);
}
