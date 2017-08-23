package com.weiaibenpao.demo.chislim.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/8/15.
 */

public class DeleteEventBus {
    private List<Integer> index;

    public DeleteEventBus(List<Integer> index) {
        this.index = index;
    }

    public List<Integer> getIndex() {
        return index;
    }

    public void setIndex(List<Integer> index) {
        this.index = index;
    }
}
