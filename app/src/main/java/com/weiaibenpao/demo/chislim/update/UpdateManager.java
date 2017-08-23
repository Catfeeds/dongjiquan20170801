package com.weiaibenpao.demo.chislim.update;

/**
 * Created by lenovo on 2016/11/10.
 */

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.weiaibenpao.demo.chislim.Interface.GetInterfaceApkVersionListener;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.util.GetIntentData;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static com.weiaibenpao.demo.chislim.update.DeviceUtils.getVersionName;

/**
 * Created by zs on 2016/7/7.
 */
public class UpdateManager {

    private Context mContext;
    /* 下载中 */
    private static final int DOWNLOAD = 1;
    /* 下载结束 */
    private static final int DOWNLOAD_FINISH = 2;

    /* 更新进度条 */
    private ProgressBar mProgress;
    private Dialog mDownloadDialog;

    /* 下载保存路径 */
    private String mSavePath;
    /* 记录进度条数量 */
    private int progress;
    /* 是否取消更新 */
    private boolean cancelUpdate = false;

    public UpdateManager(Context context) {
        this.mContext = context;
    }

    private Handler mHandler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            switch (msg.what)
            {
                // 正在下载
                case DOWNLOAD:
                    // 设置进度条位置
                    mProgress.setProgress(progress);
                    break;
                case DOWNLOAD_FINISH:
                    // 安装文件
                    installApk();
                    break;
                default:
                    break;
            }
        };
    };

    /**
     * 检测软件更新
     */
    public void checkUpdate(){
        GetIntentData get = new GetIntentData();
        final String version_info = " 发现新版本，是否更新 ";
        final String mVersion_code = String.valueOf(getVersionName(mContext));// 当前的版本号

        get.getApkVersion("getApkVersion");
        get.setGetInterfaceApkVersionListener(new GetInterfaceApkVersionListener() {
            @Override
            public void getDateApkVersion(String apkVersion, String apkText) {
                Log.i("版本",mVersion_code + "--" + apkVersion);
                if (!mVersion_code.equals(apkVersion)) {
                    Log.i("版本",mVersion_code + "--" + apkVersion);
                    // 显示提示对话
                    //showNoticeDialog ("本次更新了。。。。。。");
//                    sexDialog(apkVersion,apkText);
                    showDialog(apkText);
                }else{
                    //Toast.makeText(mContext,"已是最新版本...",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 版本更新弹框
     */
    public void sexDialog(String version,String intro){
        View view = LayoutInflater.from(mContext).inflate(R.layout.update_version,null);

        ((TextView) view.findViewById(R.id.versionCode)).setText(String.valueOf(version));
        ((TextView) view.findViewById(R.id.versionText)).setText(intro);

        final PopupWindow popWnd = new PopupWindow(mContext);
        popWnd.setContentView(view);
        popWnd.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popWnd.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popWnd.setOutsideTouchable(true);  //设置点击屏幕其它地方弹出框消失
        View rootview = LayoutInflater.from(mContext).inflate(R.layout.activity_main, null);
        //相对于父控件的位置（例如正中央Gravity.CENTER，下方Gravity.BOTTOM等），可以设置偏移或无偏移
        popWnd.showAtLocation(rootview, Gravity.CENTER, 0, 0);

        view.findViewById(R.id.startBut).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 显示下载对话框
                //showDownloadDialog();

                Intent intent = new Intent(mContext,UpdateService.class);
                intent.putExtra("Key_App_Name","东极圈");
                intent.putExtra("Key_Down_Url","http://112.74.28.179:8080/Chislim/apk/app-release.apk");
                mContext.startService(intent);

                popWnd.dismiss();
            }
        });
    }

    public void showDialog(String intro){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("提示");
        builder.setMessage("是否下载最新版本？");

        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(mContext,UpdateService.class);
                intent.putExtra("Key_App_Name","东极圈");
                intent.putExtra("Key_Down_Url","http://112.74.28.179:8080/Chislim/apk/app-release.apk");
                mContext.startService(intent);
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(dialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#189ff6"));
        dialog.getButton(dialog.BUTTON_NEGATIVE).setTextColor(Color.parseColor("#189ff6"));
    }

    /**
     * 显示软件下载对话框
     */
    private void showDownloadDialog()
    {
        // 构造软件下载对话框
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("正在下载... ");
        // 给下载对话框增加进度条
        final LayoutInflater inflater = LayoutInflater.from(mContext);
        View v = inflater.inflate(R.layout.softupdate_progress, null);
        mProgress = (ProgressBar) v.findViewById(R.id.update_progress);
        builder.setView(v);
        // 取消更新
        builder.setNegativeButton(R.string.soft_update_cancel, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
                // 设置取消状态
                cancelUpdate = true;
            }
        });
        mDownloadDialog = builder.create();
        mDownloadDialog.show();
        // 现在文件
        downloadApk();
    }


    /**
     * 下载apk文件
     */
    private void downloadApk()
    {
        // 启动新线程下载软件
        new downloadApkThread().start();
    }

    /**
     * 下载文件线程
     *
     * @author coolszy
     *@date 2012-4-26
     *@blog http://blog.92coding.com
     */
    private class downloadApkThread extends Thread
    {
        @Override
        public void run()
        {
            try
            {
                // 判断SD卡是否存在，并且是否具有读写权限
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
                {
                    // 获得存储卡的路径
                    String sdpath = Environment.getExternalStorageDirectory() + "/";
                    mSavePath = sdpath + "download";
                    URL url = new URL("http://112.74.28.179:8080/Chislim/apk/app-release.apk");
                    // 创建连接
                    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                    conn.connect();
                    // 获取文件大小
                    int length = conn.getContentLength();
                    // 创建输入流
                    InputStream is = conn.getInputStream();

                    File file = new File(mSavePath);
                    // 判断文件目录是否存在
                    if (!file.exists())
                    {
                        file.mkdir();
                    }
                    File apkFile = new File(mSavePath, "chislim");
                    FileOutputStream fos = new FileOutputStream(apkFile);
                    int count = 0;
                    // 缓存
                    byte buf[] = new byte[1024];
                    // 写入到文件中
                    do
                    {
                        int numread = is.read(buf);
                        count += numread;
                        // 计算进度条位置
                        progress = (int) (((float) count / length) * 100);
                        // 更新进度
                        mHandler.sendEmptyMessage(DOWNLOAD);
                        if (numread <= 0)
                        {
                            // 下载完成
                            mHandler.sendEmptyMessage(DOWNLOAD_FINISH);
                            break;
                        }
                        // 写入文件
                        fos.write(buf, 0, numread);
                    } while (!cancelUpdate);// 点击取消就停止下载.
                    fos.close();
                    is.close();
                }
            } catch (MalformedURLException e)
            {
                e.printStackTrace();
            } catch (IOException e)
            {
                e.printStackTrace();
            }
            // 取消下载对话框显示
            mDownloadDialog.dismiss();
        }
    };

    /**
     * 安装APK文件
     */
    private void installApk()
    {
        File apkfile = new File(mSavePath, "chislim");
        if (!apkfile.exists())
        {
            return;
        }
        // 通过Intent安装APK文件
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
        mContext.startActivity(i);
    }
}