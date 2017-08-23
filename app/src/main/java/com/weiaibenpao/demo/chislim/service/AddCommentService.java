package com.weiaibenpao.demo.chislim.service;


import com.weiaibenpao.demo.chislim.bean.AddCommentResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lenovo on 2016/8/15.
 * userId=2&humorId=1&comText=qwer
 */

public interface AddCommentService {

    @GET("/chislim2/comment/addComment")
    Call<AddCommentResult> getResult(
            @Query("userId") int userId,
            @Query("humorId") int humorId,
            @Query("comText") String comText);
}
