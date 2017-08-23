package com.weiaibenpao.demo.chislim.service;


import com.weiaibenpao.demo.chislim.bean.TravelNotesUserItemResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lenovo on 2016/8/15.
 */

public interface TravelNotesUserItemService {

    @GET("/Chislim/Travel_notes_item_Servlet")
    Call<TravelNotesUserItemResult> getResult(
            @Query("dowhat") String dowhat,
            @Query("i") int id);
}
