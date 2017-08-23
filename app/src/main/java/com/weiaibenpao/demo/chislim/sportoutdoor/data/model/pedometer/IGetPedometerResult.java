package com.weiaibenpao.demo.chislim.sportoutdoor.data.model.pedometer;


import com.weiaibenpao.demo.chislim.sportoutdoor.data.model.database.core.PedometerCardEntity;

/**
 * Created by zoubo
 */
public interface IGetPedometerResult {

    void onSuccessGet(final PedometerCardEntity cardEntity);

}
