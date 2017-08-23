package com.weiaibenpao.demo.chislim.util;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by lenovo on 2017/2/16.
 */

public class SerializableHashMap implements Serializable {

    private HashMap<String,String> map;

    public HashMap<String, String> getMap() {
        return map;
    }

    public void setMap(HashMap<String, String> map) {
        this.map = map;
    }
}