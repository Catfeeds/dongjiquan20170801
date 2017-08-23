package com.weiaibenpao.demo.chislim.service;


import com.weiaibenpao.demo.chislim.bean.ThemeResule;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lenovo on 2016/8/15.
 */

public interface ThemeService {
    @GET("/chislim2/theme/list")
    Call<ThemeResule> getResult(
            @Query("themeTitle") String themeTitle,
            @Query("nowUserId") int nowUserId,
            @Query("page") int page,
            @Query("size") int size);
}
