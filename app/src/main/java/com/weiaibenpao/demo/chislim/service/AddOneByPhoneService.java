package com.weiaibenpao.demo.chislim.service;


import com.weiaibenpao.demo.chislim.bean.RegistResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lenovo on 2016/8/15.
 */

public interface AddOneByPhoneService {

    @GET("/Chislim/UserServlet")
    Call<RegistResult> getResult(
            @Query("dowhat") String dowhat,
            @Query("userPhone") String userPhone,
            @Query("userPass") String userPass);
}
