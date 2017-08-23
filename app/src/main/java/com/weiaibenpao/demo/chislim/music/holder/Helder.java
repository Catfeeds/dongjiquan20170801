package com.weiaibenpao.demo.chislim.music.holder;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by 建宝 on 2016/4/2.
 * 用于ListView的缓存
 */
public class Helder {

    private ImageView my_imageView;
    private TextView my_songname;
    private TextView my_musicname;
    private Button listen;
    private Button down;

    public ImageView getMy_imageView() {
        return my_imageView;
    }

    public void setMy_imageView(ImageView my_imageView) {
        this.my_imageView = my_imageView;
    }

    public TextView getMy_songname() {
        return my_songname;
    }

    public void setMy_songname(TextView my_songname) {
        this.my_songname = my_songname;
    }

    public TextView getMy_musicname() {
        return my_musicname;
    }

    public void setMy_musicname(TextView my_musicname) {
        this.my_musicname = my_musicname;
    }

    public Button getListen() {
        return listen;
    }

    public void setListen(Button listen) {
        this.listen = listen;
    }

    public Button getDown() {
        return down;
    }

    public void setDown(Button down) {
        this.down = down;
    }
}
