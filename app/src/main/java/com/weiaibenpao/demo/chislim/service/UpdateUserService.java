package com.weiaibenpao.demo.chislim.service;


import com.weiaibenpao.demo.chislim.bean.BooleanResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lenovo on 2016/8/15.
 */

public interface UpdateUserService {

    @GET("/Chislim/UserServlet")
    Call<BooleanResult> getResult(
            @Query("dowhat") String dowhat,
            @Query("userId") int userId,
            @Query("userName") String userName,
            @Query("userSex") String userSex,
            @Query("userHeight") int userHeigh,
            @Query("userWeight") float userWeight,
            @Query("userBirth") String userBirth,
            @Query("userHobby") String userHobby,
            @Query("userEmail") String userEmail,
            @Query("userIntru") String userIntru);
}
