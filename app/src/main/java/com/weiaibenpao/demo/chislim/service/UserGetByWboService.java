package com.weiaibenpao.demo.chislim.service;


import com.weiaibenpao.demo.chislim.bean.UserResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lenovo on 2016/8/15.
 */

public interface UserGetByWboService {

    @GET("/Chislim/UserServlet")
    Call<UserResult> getResult(
            @Query("dowhat") String dowhat,
            @Query("userWboName") String userName,
            @Query("userOpenID") String userOpenID,
            @Query("channelId") String channelId,
            @Query("userImage") String userImage);
}
