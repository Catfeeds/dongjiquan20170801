package com.weiaibenpao.demo.chislim.music;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.util.Log;

import java.io.IOException;
import java.util.List;

/**
 * Created by ZJL on 2017/8/9.
 */

public class MusicManager implements MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener {
    private static final String TAG = "MusicManager";
    private MediaPlayer mediaPlayer;
    private boolean isPlaying;
    private boolean isLoop;
    private int currentPosition = -1;
    private int playingType = -1;
    private OnPlayingListener listener;
    private List<String> musicList;

    public static MusicManager getInstance() {
        return ManagerHolder.sInstance;
    }

    private static class ManagerHolder {
        private static final MusicManager sInstance = new MusicManager();
    }

    private MusicManager() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnErrorListener(this);
        mediaPlayer.setOnCompletionListener(this);
    }

    public void registerOnPlayingListener(OnPlayingListener listener) {
        this.listener = listener;
    }

    public void unregisterOnPlayingListener() {
        this.listener = null;
    }

    public void setMusicUrlList(List<String> musicList) {
        this.musicList = musicList;
    }

    public void setIsLoop(boolean isLoop) {
        this.isLoop = isLoop;
    }

    public void play(String url) {
        play(url, -1);
    }

    public void play(String url, int position) {
        play(url, position, -1);
    }

    public void play(String url, int position, int type) {
        init(url,position,type);
    }

    private void init(String url,int position ,int type) {
        mediaPlayer.reset();
        try {
            mediaPlayer.setDataSource(url);
        } catch (IOException e) {
            e.printStackTrace();
            Log.e(TAG, "MediaPlayer setDataSource() 失败 ：" + e.getMessage());
        }
        try {
            mediaPlayer.prepareAsync();
            currentPosition = position;
            playingType = type;
        } catch (IllegalStateException e) {//传过来的url如果是无效路径，例如乱码“sdfa”,那么prepareAsync会直接抛异常
            e.printStackTrace();
            Log.e(TAG, "MediaPlayer prepareAsync() 准备失败 ：" + e.getMessage());
        }
    }

    public void stop() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            isPlaying = false;
            resetData();
        }
    }

    private void resetData() {
        currentPosition = -1;
        playingType = -1 ;
    }

    public void release() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }


    public boolean isPlaying() {
//        return  isPlaying;
        if (mediaPlayer != null) {
            return mediaPlayer.isPlaying();
        }
        return false;
    }

    public int getPlayingPosition() {
        return currentPosition;
    }

    public int getPlayingType() {
        return playingType;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {//MediaPlayer比较奇葩的一点：如果出现错误,回调onError后还会回调一次onCompletion
        Log.e("onCompletion--","onCompletion");
        isPlaying = false;
        if (listener != null) {
            listener.onCompleted(currentPosition);
//            Log.e("onCompletion--","回调onCompleted"+currentPosition);
        }else {
//            Log.e("onCompletion--","bu回调onCompleted"+currentPosition);
        }
        if (isLoop && musicList != null && musicList.size() > 0) {
            int newPosition = (currentPosition + 1) % musicList.size();
            play(musicList.get(newPosition), newPosition, playingType);
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Log.e("onError--","onError");
        isPlaying = false;
        if (listener != null) {
            listener.onError(currentPosition, what, extra);
        }
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        if (mediaPlayer != null) {
            mediaPlayer.start();
            isPlaying = true;
        }
    }


    public interface OnPlayingListener {

        void onCompleted(int position);

        void onError(int position, int what, int extra);
    }
}
