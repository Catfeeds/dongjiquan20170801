package com.weiaibenpao.demo.chislim.service;

import com.weiaibenpao.demo.chislim.bean.SendHumorResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lenovo on 2016/12/9.
 */

public interface SendHumorService {
    @GET("/chislim2/humor/addHumor")
    Call<SendHumorResult> getResult(
            @Query("userId") int userId,
            @Query("themeTitleStr") String themeTitleStr,
            @Query("humorContent") String humorContent,
            @Query("humorImgUrl") String humorImgUrl);
}
