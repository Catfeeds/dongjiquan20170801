package com.weiaibenpao.demo.chislim.sportoutdoor.data.repository;


import com.weiaibenpao.demo.chislim.sportoutdoor.data.model.pedometer.IGetPedometerResult;

/**
 * Created by zoubo
 */
public interface PedometerRepository {

    /**
     * 获取计步器每日步数
     */
    void getPedometerStep(IGetPedometerResult result);
}
