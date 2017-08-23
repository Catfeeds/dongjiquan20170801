package com.weiaibenpao.demo.chislim.video;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.util.Utils;

public class VideoActivity extends AppCompatActivity {
    public static final String VIDEO_URL = "video_url";
    public static final String VIDEO_COVER_URL = "video_cover_url";
    public static final String VIDEO_WIDTH = "video_width";
    public static final String VIDEO_HEIGHT = "video_height";
    private static final int DEFAULT_HEIGHT = 250;

    private SurfaceVideoViewCreator surfaceVideoViewCreator;
    private String videoUrl = "";
    private String videoCoverUrl = "";
    private int videoWidth, videoHeight;
    private int maxWidth = Utils.getScreenWidth();
    private int maxHeight = Utils.getScreenHeight();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_show);
        videoUrl = getIntent().getStringExtra(VIDEO_URL);
        videoCoverUrl = getIntent().getStringExtra(VIDEO_COVER_URL);
        videoWidth = getIntent().getIntExtra(VIDEO_WIDTH, 0);
        videoHeight = getIntent().getIntExtra(VIDEO_HEIGHT, DEFAULT_HEIGHT);
        Log.e("videoActivity", "max width is " + maxWidth + "---max height is " + maxHeight);
        Log.e("videoActivity", "before sample videoWidth is " + videoWidth + "---videoHeight is " + videoHeight);
//        Log.e("videoActivity", "125/2 == " + 125 / 2);
        if (videoWidth > maxWidth) {
            double sampleSize = videoWidth / (double) maxWidth;
            videoWidth = maxWidth;
            videoHeight = (int) (videoHeight / sampleSize);
        }

        if (videoHeight > maxHeight) {
            double sampleSize = videoHeight / (double) maxHeight;
            videoHeight = maxHeight;
            videoWidth = (int) (videoWidth / sampleSize);
        }

        Log.e("videoActivity", "after sample videoWidth is " + videoWidth + "---videoHeight is " + videoHeight);


        Log.e("VideoActivity", "video videoUrl is " + videoUrl + "\nvideo cover url " + videoCoverUrl + "\n");
        ActivityCompat.requestPermissions(
                VideoActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1
        );

        surfaceVideoViewCreator =
                new SurfaceVideoViewCreator(this, (RelativeLayout) findViewById(R.id.root)) {


                    @Override
                    protected Activity getActivity() {
                        return VideoActivity.this;     /** 当前的 Activity */
                    }

                    @Override
                    protected boolean setAutoPlay() {
                        return true;                 /** true 适合用于，已进入就自动播放的情况 */
                    }

                    @Override
                    protected int getSurfaceWidth() {
                        return 720;                     /** Video 的显示区域宽度，0 就是适配手机宽度 */
                    }

                    @Override
                    protected int geturfaceHeight() {
                        return 405;                   /** Video 的显示区域高度，dp 为单位 */
                    }

                    @Override
                    protected void setThumbImage(ImageView thumbImageView) {
                        Glide.with(VideoActivity.this)
                                .load(videoCoverUrl)
                                .centerCrop()
                                .diskCacheStrategy(DiskCacheStrategy.ALL)
                                .placeholder(R.drawable.all_darkbackground)
                                .dontAnimate()
                                .into(thumbImageView);
                    }

                    /** 这个是设置返回自己的缓存路径，
                     * 应对这种情况：
                     *     录制的时候是在另外的目录，播放的时候默认是在下载的目录，所以可以在这个方法处理返回缓存
                     * */
                    @Override
                    protected String getSecondVideoCachePath() {
                        return null;
                    }

                    @Override
                    protected String getVideoPath() {
                        return videoUrl;
                    }


                };
        surfaceVideoViewCreator.debugModel = true;
        surfaceVideoViewCreator.setUseCache(getIntent().getBooleanExtra("useCache", false));

        //surfaceVideoViewCreator.setUseCache(false);
    }


    @Override
    protected void onPause() {
        super.onPause();
        surfaceVideoViewCreator.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        surfaceVideoViewCreator.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        surfaceVideoViewCreator.onDestroy();
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        surfaceVideoViewCreator.onKeyEvent(event); /** 声音的大小调节 */
        return super.dispatchKeyEvent(event);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    break;
                }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }
}
