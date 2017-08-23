package com.weiaibenpao.demo.chislim.service;


import com.weiaibenpao.demo.chislim.bean.UploadResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lenovo on 2016/8/15.
 *  userId  用户id
 *  altitudeHigh  海拔高度
 *  average   平均
 *  boolPressure  血压
 *  calories       消耗卡路里
 *  heartRate      心率
 *  matchNumber      配步数
 *  sportDate   运动日期
 *  sportImgUrl  运动轨迹图片(七牛返回的图片地址)
 *  totalDistance     //全程距离
 *  totalShin         //累计攀爬
 *  stepFrequency       //步频率
 *  totalTime         //运动时长
 *  totalStep   //总步数
 */

public interface UploadService {

    @GET("/chislim2/sports/addsports")
    Call<UploadResult> getResult(
            @Query("userId") int userId,
            @Query("altitudeHigh") String altitudeHigh,
            @Query("average") String average,
            @Query("boolPressure") String boolPressure,
            @Query("calories") String calories,
            @Query("heartRate") String heartRate,
            @Query("matchNumber") String matchNumber,
            @Query("sportDate") String sportDate,
            @Query("sportImgUrl") String sportImgUrl,
            @Query("totalDistance") String totalDistance,
            @Query("totalShin") String totalShin,
            @Query("stepFrequency") String stepFrequency,
            @Query("totalTime") String totalTime,
            @Query("totalStep") String totalStep);
}
