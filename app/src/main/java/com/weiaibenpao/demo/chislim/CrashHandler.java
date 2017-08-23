package com.weiaibenpao.demo.chislim;

import android.content.Context;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.weiaibenpao.demo.chislim.sportoutdoor.presentation.module.PedometerApplication;
import com.weiaibenpao.demo.chislim.util.VoicePlayerUtil;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by ZJL on 2016/5/27.
 * 使用UncaughtExceptionHandler捕获全局异常：
 * 比如NullPointerException空指针异常抛出时，如果没有try catch捕获，
 * 那么，Android系统会弹出对话框的“XXX程序异常退出”，
 * 给应用的用户体验造成不良影响。为了捕获应用运行时异常并给出友好提示，
 * 便可继承UncaughtExceptionHandler类来处理。
 * 应用绑定异常处理方法：
 * 在Application或者Activity的onCreate方法中加入以下两句调用即可：
 * CrashHandler crashHandler = CrashHandler.getInstance();
 * crashHandler.init(getApplicationContext());-------另外一个优势就是即使手机没连接IDE也可以看到错误信息
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {
    private static final String TAG = CrashHandler.class.getSimpleName();
    private static CrashHandler instance;
    private Context context;
    private Thread.UncaughtExceptionHandler defaultHandler;// 系统默认的UncaughtException处理类
    private DateFormat formatter = new SimpleDateFormat(
            "yyyy-MM-dd_HH-mm-ss.SSS", Locale.CHINA);

    public static CrashHandler getInstance() { // 单例模式
        if (instance == null) {
            synchronized (CrashHandler.class) {
                if (instance == null) {
                    instance = new CrashHandler();
                }
            }
        }
        return instance;
    }

    public void init(Context context) {
        this.context = context;
        defaultHandler = Thread.getDefaultUncaughtExceptionHandler(); // 获取系统默认的UncaughtException处理器
        Thread.setDefaultUncaughtExceptionHandler(this); // 设置该CrashHandler为程序的默认处理器
    }


    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        boolean isHandled = handleException(ex);// 自定义错误处理
        if(!isHandled && defaultHandler != null){
            defaultHandler.uncaughtException(thread,ex);// 如果自定义方法没有处理则让系统默认的异常处理器来处理
        }else {
            try {
                Thread.sleep(30000);
            } catch (InterruptedException e) {
                Log.e(TAG, "error : "+ e);
            }
            // 退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(1);
        }

    }
    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(final Throwable ex) {
        if (ex == null) {
            return false;
        }
        new Thread() {
            @Override
            public void run() {
                Looper.prepare();

                VoicePlayerUtil voicePlayerUtil = PedometerApplication.getVoicePlayerUtil();
                if(voicePlayerUtil != null) {
                    voicePlayerUtil.startVoice("异常出现,请注意查看");
                }
                ex.printStackTrace();
                String err = "[" + ex.getMessage() + "]";
                Toast toast = Toast.makeText(context, "Oops,error occurs : " + err, Toast.LENGTH_LONG);
                showMyToast(toast,500000);
                Looper.loop();
            }
        }.start();
//        // 收集设备参数信息 \日志信息
//        String errInfo = collectDeviceInfo(context, ex);
//        // 保存日志文件
//        saveCrashInfo2File(errInfo);
        return true;

    }

    public void showMyToast(final Toast toast, final int cnt) {
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                toast.show();
            }
        }, 0, 3000);
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                toast.cancel();
                timer.cancel();
            }
        }, cnt );
    }


}
