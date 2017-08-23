package com.weiaibenpao.demo.chislim.music.service;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;

import com.weiaibenpao.demo.chislim.music.SendData.SendDataListener;
import com.weiaibenpao.demo.chislim.music.bean.Bean_music;

import java.util.ArrayList;

public class Start_Service extends Service {

    private final IBinder binder = new My_binder();
    private MediaPlayer mMediaPlayer;
    private ArrayList<Bean_music> music_list;
    private int postion;//当前播放歌曲
    private int pross;
    private Intent intent;
    private static final int PROSS_NUM = 1;

    private boolean stop = false;


    public class My_binder extends Binder {
        public Start_Service getService(){
            return Start_Service.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        intent = new Intent();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        postion =intent.getIntExtra("postion",0);
       // play(0,music_list.get(postion),postion);
        initMediPlay();
        if(music_list.size() > 0){
            getPross();
        }
        Log.i("服务",postion + "<<<<<<<<<<<<<<>>>>>>>>>>>>>");
        ;       return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        music_list = new ArrayList<>();
        music_list = intent.getParcelableArrayListExtra("music");
        Log.i("服务",music_list.size()+"---------------->>>>>>>>>");
        return super.onStartCommand(intent, flags, startId);
    }

    //初始化播放器
    public void initMediPlay(){
        if(mMediaPlayer == null){
            mMediaPlayer = new MediaPlayer();
        }else{
            mMediaPlayer.reset();
        }
    }

    public void play(int pro,Bean_music music,int pos) {
        postion = pos;
        if(mMediaPlayer == null){
            mMediaPlayer = new MediaPlayer();
        }else{
            mMediaPlayer.reset();
        }
        try {
            mMediaPlayer.setDataSource(music.getMusic_url());
            mMediaPlayer.prepareAsync();	//进行缓冲

            //进度监听
            mMediaPlayer.setOnPreparedListener(new PreparedListener(pro));  //注册一个监听器

            //歌曲放完监听
            mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    next();
                }
            });
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * 实现一个OnPrepareLister接口,当音乐准备好的时候开始播放
     *
     */
    private final class PreparedListener implements MediaPlayer.OnPreparedListener {

        public PreparedListener(int pro) {

        }
        @Override
        public void onPrepared(MediaPlayer mp) {
            if(pross > 0) {	//如果音乐不是从头播放
                mMediaPlayer.seekTo(pross);
            }
            mMediaPlayer.start();	//开始播放
        }
    }

    /**
     * 暂停音乐
     */
    public void pause() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            pross = mMediaPlayer.getCurrentPosition();
            mMediaPlayer.pause();
        }
    }

    //下一曲
    public void next(){

        if(postion == music_list.size()-1){
            postion = 0;
            pross = 0;
            play(0,music_list.get(postion),postion);
        }else{
            postion = postion + 1;
            pross = 0;
            play(0,music_list.get(postion),postion);
        }
    }

    //上一曲
    public void up(){

        if(postion == 0){
            postion = music_list.size()-1;
            pross = 0;
            play(0,music_list.get(postion),postion);
        }else{
            postion = postion - 1;
            pross = 0;
            play(0,music_list.get(postion),postion);
        }
    }

    //返回状态
    public boolean getState(){
        if(mMediaPlayer.isPlaying()){
            return true;
        }else{
            return false;
        }
    }

    //设置播放位置
    public void setPross(int pro){
        pross = pro;
        mMediaPlayer.seekTo(pro);
    }

    public void getPross(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(!stop ){
                    try {
                        Thread.sleep(500);
                        Message message = new Message();
                        message.what = PROSS_NUM;
                        handler.sendMessage(message);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case PROSS_NUM:
                    if(mMediaPlayer != null){
                        intent.putExtra("pross",mMediaPlayer.getCurrentPosition());   //歌曲播放的进度
                        intent.putExtra("position",postion);        //歌曲播放的序列号
                        intent.putExtra("countTime",music_list.get(postion).getMusic_duration());   //当前歌曲总时长
                        intent.putExtra("musicBean",music_list.get(postion));
                        intent.putExtra("isPlaying",mMediaPlayer.isPlaying());
                        intent.setAction("com.lijianbao.cn");
                        sendBroadcast(intent);
                    }else{
                        intent.putExtra("pross",0);   //歌曲播放的进度
                        intent.putExtra("position",0);        //歌曲播放的序列号
                        intent.putExtra("countTime",0);   //当前歌曲总时长
                        intent.putExtra("musicBean",0);
                        intent.putExtra("isPlaying",false);
                        intent.setAction("com.lijianbao.cn");
                        sendBroadcast(intent);
                    }
                    break;
            }
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMediaPlayer.stop();
        mMediaPlayer.release();
    }

    SendDataListener sendDataListener;
    public  void OnSendDataListener(SendDataListener sendDataListener){
        this.sendDataListener = sendDataListener;
    }

}
