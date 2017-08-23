package com.weiaibenpao.demo.chislim.util;

import com.weiaibenpao.demo.chislim.bean.LatLongRemberBean;

import java.util.ArrayList;

/**
 * Created by lenovo on 2016/12/1.
 */

interface latlong_Dao {
    void addLatLong(double latitude,double longitude,String sporttime,String sportdis,String sportcor,
                    String sportstep,String sportspeed,String speedspace,String stepfrequency,String stepscope,String elevation);
    ArrayList<LatLongRemberBean> getLatLong(String tab);
}
