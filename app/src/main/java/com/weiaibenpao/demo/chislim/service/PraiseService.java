package com.weiaibenpao.demo.chislim.service;


import com.weiaibenpao.demo.chislim.bean.PraiseResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lenovo on 2016/8/15.
 */

public interface PraiseService {
    @GET("/chislim2/praise/makepraise")
    Call<PraiseResult> getResult(
            @Query("userId") int userId,
            @Query("humorId") int humorId);
}
