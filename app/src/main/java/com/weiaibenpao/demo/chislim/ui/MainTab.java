package com.weiaibenpao.demo.chislim.ui;

import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.fragment.SportFragment;
import com.weiaibenpao.demo.chislim.hurui.fragment.HR_Frist_Fragment;
import com.weiaibenpao.demo.chislim.hurui.fragment.HR_Second_Fragment;
import com.weiaibenpao.demo.chislim.hurui.fragment.UserFragment;

/**
 * Created by Administrator on 2016/4/22.
 */
public enum MainTab {

    FIRST(0, R.string.first,R.drawable.tab_icon_one, HR_Frist_Fragment.class),
    SECOND(1,R.string.second, R.drawable.tab_icon_two, HR_Second_Fragment.class),    //隐藏了第二个界面
    FIFTH(3,R.string.fifth,R.drawable.tab_icon_five,SportFragment.class),
    //THIRD(1,R.string.third, R.drawable.tab_icon_three, HR_Fouth_Fragment.class),
    FOURTH(2,R.string.fourth,R.drawable.tab_icon_four,UserFragment.class);


    private int index,resName,resIcon;
    Class<?> clz;
    private MainTab(int index,int resName,int resIcon,Class<?> clz){
        this.index=index;
        this.resName=resName;
        this.resIcon=resIcon;
        this.clz=clz;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getResName() {
        return resName;
    }

    public void setResName(int resName) {
        this.resName = resName;
    }

    public int getResIcon() {
        return resIcon;
    }

    public void setResIcon(int resIcon) {
        this.resIcon = resIcon;
    }

    public Class<?> getClz() {
        return clz;
    }

    public void setClz(Class<?> clz) {
        this.clz = clz;
    }
}
