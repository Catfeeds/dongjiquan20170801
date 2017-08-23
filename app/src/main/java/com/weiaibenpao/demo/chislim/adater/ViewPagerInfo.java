package com.weiaibenpao.demo.chislim.adater;

import android.os.Bundle;

/**
 * Created by Administrator on 2016/4/25.
 */
public final class ViewPagerInfo {
    public final String tag;
    public final Class<?> clss;
    public final Bundle args;
    public final String title;

    public ViewPagerInfo(String _title,String _tag,Class<?> _class,Bundle _args){
        title=_title;
        tag=_tag;
        clss=_class;
        args=_args;
    }
}
