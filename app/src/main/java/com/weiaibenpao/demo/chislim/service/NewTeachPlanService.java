package com.weiaibenpao.demo.chislim.service;


import com.weiaibenpao.demo.chislim.bean.NewTeachPlanResut;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lenovo on 2016/8/15.
 */

public interface NewTeachPlanService {

    @GET("/Chislim/NewTeachMenuServlet")
    Call<NewTeachPlanResut> getResult(
            @Query("dowhat") String dowhat,
            @Query("teachID") int teachID);
}
