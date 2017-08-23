package com.weiaibenpao.demo.chislim.service;


import com.weiaibenpao.demo.chislim.bean.AddCollectionResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lenovo on 2016/8/15.
 */

public interface AddCollectionService {

    @GET("/chislim2/collect/collection")
    Call<AddCollectionResult> getResult(
            @Query("collectionType") String collectionType,
            @Query("userId") int userId,
            @Query("objectId") int objectId);
}
