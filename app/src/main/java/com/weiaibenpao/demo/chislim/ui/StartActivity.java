package com.weiaibenpao.demo.chislim.ui;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;

import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.util.SharedPrefsUtil;
import com.weiaibenpao.demo.chislim.util.WriteReadSharedPrefs;

import java.io.IOException;

import static com.weiaibenpao.demo.chislim.util.Default.READ_EXTERNAL_STORAGE_CODE;


public class StartActivity extends Activity {

    //----------------------------------------
    //背景动画
    private SurfaceView surfaceview;
    private MediaPlayer mediaPlayer;

    private int postion = 0;

    //----------立即体验按钮
    Button myStart;


    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((getWindow().getDecorView().getSystemUiVisibility()
                & View.SYSTEM_UI_FLAG_FULLSCREEN) != 0) {
            getWindow().getDecorView().setSystemUiVisibility(0);
        } else {
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_FULLSCREEN);
        }

        setContentView(R.layout.activity_start);
        context = getApplicationContext();
        CheckPerMission();
        //如果文件中有用户数据保存，直接进入首页
        if(SharedPrefsUtil.getValue(context, WriteReadSharedPrefs.PREFS_NAME,"isFirst",0) == 1){

            Intent intent = new Intent(StartActivity.this,LeadActivity.class);

            startActivity(intent);

            StartActivity.this.finish();
        }
        initSurface();

    }

    //背景动画
    public void initSurface(){
        //动画
        surfaceview = (SurfaceView) findViewById(R.id.surfaceView);
        mediaPlayer = new MediaPlayer();
        surfaceview.getHolder().setKeepScreenOn(true);
        surfaceview.getHolder().addCallback(new SurfaceViewLis());

        myStart = (Button) findViewById(R.id.myStart);
        myStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(StartActivity.this,LoginActivity.class);
                startActivity(intent);
                StartActivity.this.finish();
            }
        });
    }


    private class SurfaceViewLis implements SurfaceHolder.Callback {

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                                   int height) {

        }

        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            if (postion == 0) {
                try {
                    play();
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (SecurityException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IllegalStateException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {

        }
    }

    public void play() throws IllegalArgumentException, SecurityException,
            IllegalStateException, IOException {
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        AssetFileDescriptor fd = this.getAssets().openFd("qms.mp4");
        mediaPlayer.setDataSource(fd.getFileDescriptor(), fd.getStartOffset(),
                fd.getLength());
        mediaPlayer.setLooping(true);
        mediaPlayer.setDisplay(surfaceview.getHolder());
        // 通过异步的方式装载媒体资源
        mediaPlayer.prepareAsync();
        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                // 装载完毕回调
                mediaPlayer.start();
            }
        });
    }

    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_SETTINGS,
            Manifest.permission.READ_CONTACTS};

    /**
     * 权限检查
     * @param
     */
    public void CheckPerMission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, PERMISSIONS_STORAGE,
                    READ_EXTERNAL_STORAGE_CODE);
        }
    }

    /**
     * 权限申请的回调
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        doNext(requestCode,grantResults);
    }

    /**
     * 权限的处理
     * @param requestCode
     * @param grantResults
     */
    private void doNext(int requestCode, int[] grantResults) {
        if (requestCode == READ_EXTERNAL_STORAGE_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted     同意

            } else {
                // Permission Denied       拒绝
                new AlertDialog.Builder(StartActivity.this)
                        .setTitle("权限申请")
                        .setMessage("在设置-应用-权限中开始读写权限，以保证功能的正常使用")
                        .setPositiveButton("去开启", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                                Uri uri = Uri.fromParts("package", getPackageName(), null);
                                intent.setData(uri);
                                startActivity(intent);

                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("取消", null)
                        .setCancelable(false)
                        .show();
            }
        }
    }
}
