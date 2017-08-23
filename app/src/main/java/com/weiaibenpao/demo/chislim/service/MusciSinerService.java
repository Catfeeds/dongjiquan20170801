package com.weiaibenpao.demo.chislim.service;


import com.weiaibenpao.demo.chislim.music.bean.MusicSinerResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by lenovo on 2016/8/15.
 */

public interface MusciSinerService {

    @GET("/geekery/music/singer")
    Call<MusicSinerResult> getResult(
            @Header("apikey") String apikey,
            @Query("name") String name);
}
