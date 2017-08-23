package com.weiaibenpao.demo.chislim.service;


import com.weiaibenpao.demo.chislim.bean.BooleanResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lenovo on 2016/8/15.
 */

public interface AddNotesService {

    @GET("/Chislim/Travel_notes_item_Servlet")
    Call<BooleanResult> getResult(
            @Query("dowhat") String dowhat,
            @Query("tn_id") int tn_id,
            @Query("tn_item_name") String tn_item_name,
            @Query("tn_item_image") StringBuffer tn_item_image,
            @Query("tn_item_text") String tn_item_text);
}
