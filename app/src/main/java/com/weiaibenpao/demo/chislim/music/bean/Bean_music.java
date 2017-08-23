package com.weiaibenpao.demo.chislim.music.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by 建宝 on 2016/6/21.
 */
public class Bean_music implements Parcelable {
    private int song_image;
    private String music_title;    //标题
    private String music_singer;   //歌手名
    private int music_duration;    //播放时长
    private String music_url;      //歌曲路径
    private long music_size;       //歌曲大小
    private String music_album;   //专辑名
    private int status = 2 ;//本地歌曲
    private boolean flag = true;  //没有播放
    private int songId;

    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
//音乐当前状态
    //3为本地音乐,2为下载完成，0为待下载，1为正在下载
    //private int status = 3;

    public Bean_music() {
    }

    public Bean_music(int song_image, String music_title, String music_singer, int music_duration,
                      String music_url, long music_size, String music_album) {
        this.song_image = song_image;
        this.music_title = music_title;
        this.music_singer = music_singer;
        this.music_duration = music_duration;
        this.music_url = music_url;
        this.music_size = music_size;
        this.music_album = music_album;
    }

    protected Bean_music(Parcel in) {
        song_image = in.readInt();
        music_title = in.readString();
        music_singer = in.readString();
        music_duration = in.readInt();
        music_url = in.readString();
        music_size = in.readLong();
        music_album = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(song_image);
        dest.writeString(music_title);
        dest.writeString(music_singer);
        dest.writeInt(music_duration);
        dest.writeString(music_url);
        dest.writeLong(music_size);
        dest.writeString(music_album);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Bean_music> CREATOR = new Creator<Bean_music>() {
        @Override
        public Bean_music createFromParcel(Parcel in) {
            return new Bean_music(in);
        }

        @Override
        public Bean_music[] newArray(int size) {
            return new Bean_music[size];
        }
    };

    public int getSong_image() {
        return song_image;
    }

    public void setSong_image(int song_image) {
        this.song_image = song_image;
    }

    public String getMusic_title() {
        return music_title;
    }

    public void setMusic_title(String music_title) {
        this.music_title = music_title;
    }

    public String getMusic_singer() {
        return music_singer;
    }

    public void setMusic_singer(String music_singer) {
        this.music_singer = music_singer;
    }

    public int getMusic_duration() {
        return music_duration;
    }

    public void setMusic_duration(int music_duration) {
        this.music_duration = music_duration;
    }

    public String getMusic_url() {
        return music_url;
    }

    public void setMusic_url(String music_url) {
        this.music_url = music_url;
    }

    public long getMusic_size() {
        return music_size;
    }

    public void setMusic_size(long music_size) {
        this.music_size = music_size;
    }

    public String getMusic_album() {
        return music_album;
    }

    public void setMusic_album(String music_album) {
        this.music_album = music_album;
    }
}
