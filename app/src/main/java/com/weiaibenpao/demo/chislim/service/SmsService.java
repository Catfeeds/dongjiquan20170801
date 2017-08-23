package com.weiaibenpao.demo.chislim.service;


import com.weiaibenpao.demo.chislim.bean.SmsTestResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by lenovo on 2016/8/15.
 */

public interface SmsService {

    @GET("/kingtto_media/106sms/106sms")
    Call<SmsTestResult> getResult(
            @Header("apikey") String apikey,
            @Query("mobile") String mobile,
            @Query("content") String content,
            @Query("tag") int tag);
}
