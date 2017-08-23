package com.weiaibenpao.demo.chislim.service;


import com.weiaibenpao.demo.chislim.bean.GetCollectionResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lenovo on 2016/8/15.
 */

public interface GetCollectionService {

    @GET("/chislim2/collect/list")
    Call<GetCollectionResult> getResult(
            @Query("userId") int userId);
}
