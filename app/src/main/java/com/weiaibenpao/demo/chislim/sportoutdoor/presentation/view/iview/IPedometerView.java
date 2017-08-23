package com.weiaibenpao.demo.chislim.sportoutdoor.presentation.view.iview;


import com.weiaibenpao.demo.chislim.sportoutdoor.data.model.database.core.PedometerCardEntity;

/**
 * Created by zoubo
 * 计步器view接口
 */
public interface IPedometerView {
    void onReaderPedometer(PedometerCardEntity cardEntity);
}
