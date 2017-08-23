package com.weiaibenpao.demo.chislim.music.dao;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;

import com.weiaibenpao.demo.chislim.music.bean.Bean_music;

import java.util.ArrayList;

/**
 * Created by 建宝 on 2016/4/11.
 */
public class Dao_Get_music extends ContentResolver {
    Context context;
    private ArrayList music_Array = new ArrayList();

    public ArrayList getMusic_Array() {
        getMusic();
        return music_Array;
    }

    public Dao_Get_music(Context context) {
        super(context);
        this.context = context;
        //getMusic();
    }

    public void getMusic(){
        ContentResolver cr = this.context.getContentResolver();
        if (cr == null) {
            return;
        }
        context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse("file://" + Environment.getExternalStorageDirectory())));
        // 获取所有歌曲
        Cursor cursor = cr.query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                null,null, null, MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        if (null == cursor) {
            return;
        }

        int i = 0;
        int cursorCount = cursor.getCount();
        if (cursorCount > 0) {
            cursor.moveToFirst();
            do {
                Bean_music music = new Bean_music();
                //获取标题
                String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
                music.setMusic_title(title);

                //获取歌手名
                String singer = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
                music.setMusic_singer(singer);

                //获取歌手专辑名
                String album = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM));
                music.setMusic_album(album);

                //获取歌曲文件的大小
                long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
                music.setMusic_size(size);

                //歌曲的播放时长
                int duration = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                music.setMusic_duration(duration);

                //获取歌曲的文件路径
                String url = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                music.setMusic_url(url);

                // music.setSong_image(R.mipmap.bz10);

                music_Array.add(i,music);
                i++;

            } while (cursor.moveToNext());
        }
    }



    /**
     * 查询SD卡里可以上传的文档
     */
  /*  private void queryFiles(){
        String[] projection = new String[] { MediaStore.Files.FileColumns._ID,
                MediaStore.Files.FileColumns.DATA,
                MediaStore.Files.FileColumns.SIZE
        };
        Cursor cursor = this.context.getContentResolver().query(
                Uri.parse("content://media/external/file"),
                projection,
                MediaStore.Files.FileColumns.DATA + " like ?",
                new String[]{"%.mp3"},
                null);

        if (cursor != null) {
            if (cursor.moveToFirst()) {

                int idindex = cursor
                        .getColumnIndex(MediaStore.Files.FileColumns._ID);
                int dataindex = cursor
                        .getColumnIndex(MediaStore.Files.FileColumns.DATA);
                int sizeindex = cursor
                        .getColumnIndex(MediaStore.Files.FileColumns.SIZE);
                do {
                    String id = cursor.getString(idindex);
                    String path = cursor.getString(dataindex);
                    String size = cursor.getString(sizeindex);
                    docBean.setId(id);
                    docBean.setPath(path);
                    docBean.setSize(size);
                    int dot=path.lastIndexOf("/");
                    String name=path.substring(dot+1);
                    Log.e("test",name);
                } while (cursor.moveToNext());
            }
        }
        cursor.close();
    }*/


}
