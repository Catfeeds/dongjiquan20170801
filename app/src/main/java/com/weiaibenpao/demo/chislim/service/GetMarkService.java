package com.weiaibenpao.demo.chislim.service;


import com.weiaibenpao.demo.chislim.bean.GetIntResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lenovo on 2016/8/15.themeTitleStr=&nowUserId=0&page=0&size=10
 */

public interface GetMarkService {
    @GET("/Chislim/UserServlet")
    Call<GetIntResult> getResult(
            @Query("dowhat") String dowhat,
            @Query("userID") int userID);
}
