package com.weiaibenpao.demo.chislim.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.jaeger.library.StatusBarUtil;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.update.UpdateManager;
import com.weiaibenpao.demo.chislim.util.ACache;
import com.weiaibenpao.demo.chislim.util.DataCleanManager;
import com.weiaibenpao.demo.chislim.util.SharedPrefsUtil;
import com.weiaibenpao.demo.chislim.util.StackManager;
import com.weiaibenpao.demo.chislim.util.WriteReadSharedPrefs;

import butterknife.BindView;
import butterknife.ButterKnife;


public class SetActivity extends Activity {

    public static final String PREFS_NAME = "UserInfo";
    @BindView(R.id.voice_btn)
    CheckBox voiceBtn;
    @BindView(R.id.dataNum)
    TextView dataNum;
    private SharedPreferences settings;

    private ImageView back;
    StackManager manager = new StackManager();

    private ACache mCache;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_set);
        StatusBarUtil.setColor(this , getResources().getColor(R.color.ku_bg ) , 0);
        ButterKnife.bind(this);
        context = getApplicationContext();
        mCache = ACache.get(context);
        settings = getSharedPreferences(PREFS_NAME, 0);

        back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        try {
            dataNum.setText(DataCleanManager.getTotalCacheSize(context));
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (SharedPrefsUtil.getValue(context, "MySetting", "voices", 1) == 1) {
            SharedPrefsUtil.putValue(context, "MySetting", "voices", 1);        //语音开
            voiceBtn.setChecked(true);        //设置开关初始状态 开
        } else {
            voiceBtn.setChecked(false);        //设置开关初始状态 关
        }

    }


    /**
     * 修改密码
     *
     * @param v
     */
    public void updateUserPass(View v) {
        Intent intent = new Intent(SetActivity.this, UpdatePassActivity.class);
        startActivity(intent);
    }

    /**
     * 关于
     *
     * @param v
     */
//    public void about(View v) {
//        Intent intent = new Intent(SetActivity.this, AboutActivity.class);
//        startActivity(intent);
//    }

    /**
     * 切换账号
     */
    public void changeUser(View v) {
        SharedPrefsUtil.putValue(context, WriteReadSharedPrefs.PREFS_NAME,"content",false);
        //获得SharedPreferences.Editor对象，使SharedPreferences对象变为可编辑状态（生成编辑器）
        /*SharedPreferences.Editor edit = settings.edit();
        //清除文件内容
        *//*edit.clear();*//*
        edit.putBoolean("content", false);
        //提交
        edit.commit();*/
        manager.popAllActivitys();
        Intent intent = new Intent(SetActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 检查更新
     *
     * @param v
     */
    public void checkUpdate(View v) {
        new UpdateManager(SetActivity.this).checkUpdate();     //监测更新
    }

    /**
     * 清除缓存
     *
     * @param v
     */
    public void clearCatch(View v) {


       /* mCache.clear();*/
        DataCleanManager.clearAllCache(context);
        try {
            dataNum.setText(DataCleanManager.getTotalCacheSize(context));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /** * 计算 cacheSize和cacheCount */
    /*private void calculateCacheSizeAndCacheCount() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                //int size = 0;
                long size = 0;  //这里long类型才对——by牧之丶
                int count = 0;
                File[] cachedFiles = cacheDir.listFiles();  //返回缓存目录cacheDir下的文件数组
                if (cachedFiles != null) {
                    for (File cachedFile : cachedFiles) {   //对文件数组遍历
                        size += calculateSize(cachedFile);
                        count += 1;
                        lastUsageDates.put(cachedFile, cachedFile.lastModified());  //将缓存文件和最后修改时间插入map
                    }
                    cacheSize.set(size);        //设置为给定值
                    cacheCount.set(count);      //设置为给定值
                }
            }
        }).start();
    }*/

    /**
     * 用户反馈
     */
    public void addUserBack(View v) {
        Intent intent = new Intent(SetActivity.this, UserBackActivity.class);
        startActivity(intent);
    }
}
