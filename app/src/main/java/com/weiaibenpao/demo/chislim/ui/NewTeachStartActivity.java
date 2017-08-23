package com.weiaibenpao.demo.chislim.ui;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.facebook.drawee.view.SimpleDraweeView;
import com.jaeger.library.StatusBarUtil;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.bean.NewTeachGifImageResult;
import com.weiaibenpao.demo.chislim.customs.CircleProgressBar;
import com.weiaibenpao.demo.chislim.settings.TtsSettings;
import com.weiaibenpao.demo.chislim.util.VoicePlayerUtil;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class NewTeachStartActivity extends AppCompatActivity {

    @BindView(R.id.myGif)
    SimpleDraweeView myGif;
    @BindView(R.id.fidback)
    ImageView fidback;
    @BindView(R.id.titleTv)
    TextView titleTv;
    @BindView(R.id.text)
    TextView text;

    ArrayList arrayList;
    @BindView(R.id.singleTime)
    TextView singleTime;
    @BindView(R.id.pratice_layout)
    LinearLayout praticeLayout;
    @BindView(R.id.first_layout)
    LinearLayout firstLayout;
    @BindView(R.id.bottomLayout)
    RelativeLayout bottomLayout;
    @BindView(R.id.rest_layout)
    LinearLayout restLayout;
    @BindView(R.id.allTime)
    TextView allTime;
    @BindView(R.id.turn_left)
    ImageView turnLeft;
    @BindView(R.id.turn_right)
    ImageView turnRight;
    @BindView(R.id.leak_line)
    TextView leakLine;
    private int position;//全局定位gif变量
    private int length;
    NewTeachGifImageResult.NewTeachGifImageBeanBean data;
    Context context;
    @BindView(R.id.up)
    RelativeLayout up;
    @BindView(R.id.next)
    RelativeLayout next;
    @BindView(R.id.fenzi)
    TextView fenzi;
    @BindView(R.id.fenmu)
    TextView fenmu;
    @BindView(R.id.calu_time)
    TextView caluTime;
    @BindView(R.id.progress_layout)
    RelativeLayout progressLayout;
    @BindView(R.id.calu_layout)
    LinearLayout caluLayout;
    @BindView(R.id.circle_bar)
    RelativeLayout circleBar;
    @BindView(R.id.sportTime)
    Chronometer sportTime;
    @BindView(R.id.myProgressRetangeBar)
    ProgressBar myProgressRetangeBar;
    private static final int STOP = 0;
    private static final int NEXT = 1;
    @BindView(R.id.activity_new_teach_start)
    RelativeLayout activityNewTeachStart;
    @BindView(R.id.circle_chart)
    CircleProgressBar circleChart;
    private int currentPregress = 0;
    int total;
    Message msg;//圆形进度条的消息
    // 底部进度条消息
    Message msg0;
    int j =1;
    int n =1;
    boolean flag_left = false;//向左
    boolean flag_right = false;
    boolean normal = true;//是否有快进快退的情况

    private VoicePlayerUtil voicePlayerUtil;
    private SharedPreferences mSharedPreferences;

    /*   MyThread1 t1;
       Thread thread;*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_teach_start);
        ButterKnife.bind(this);
        StatusBarUtil.setColor(this , getResources().getColor(R.color.ku_bg ) , 0);
        context = getApplicationContext();

        mSharedPreferences = getSharedPreferences(TtsSettings.PREFER_NAME, MODE_PRIVATE);
        voicePlayerUtil = new VoicePlayerUtil(context,mSharedPreferences);

        Intent intent = getIntent();

        arrayList = intent.getParcelableArrayListExtra("datalist");
        position = intent.getIntExtra("position", 0);
        Log.i("开始", position + "");
        data = intent.getParcelableExtra("dataObject");
        length = arrayList.size();
        total = 46* length-10;
        startBottomProgress();
        initView(position);
        setTime();
        //测试Runnable
        /*t1 = new MyThread1();
        thread = new Thread(t1);
        thread.start();//同一个t1，如果在Thread中就不行，会报错*/
    }


    /**
     * 底部进度条
     */
    private void startBottomProgress() {

        myProgressRetangeBar.setProgress(0);
        myProgressRetangeBar.setMax(total);

        new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < (total+50); i++) {
                    try {
                        int iCount = i + 1;
                        Thread.sleep(1000);

                        if (i == total) {
                            msg0 = new Message();
                            msg0.what = STOP;
                            msg0.arg1 = iCount;
                            mHandler.sendMessage(msg0);
                            break;
                        } else {
                            msg0 = new Message();
                            msg0.what = NEXT;
                            msg0.arg1 = iCount;
                            mHandler.sendMessage(msg0);
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }

        }).start();
    }


    /**
     * 加载gif图片
     *
     * @param p
     */
    public void initView(int p) {

        Glide.with(context)
                .load(((NewTeachGifImageResult.NewTeachGifImageBeanBean) arrayList.get(p)).getGif_Image())
                .asGif() // 判断加载的url资源是否为gif格式的资源
                .skipMemoryCache(true) //跳过内存缓存
                /**
                 * //  NONE:跳过硬盘缓存 SOURCE：仅仅只缓存原来的全分辨率的图像  RESULT:仅仅缓存最终的图像，即降低分辨率后的（或者是转换后的）
                 * //ALL 缓存所有版本的图像（默认行为）
                 */
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)     //缓存设置
                .priority(Priority.NORMAL)    //优先级  LOW     NORMAL      HIGH      IMMEDIATE
                .placeholder(R.mipmap.zhanwei)    //占位图
                .error(R.mipmap.zhanwei)
                .into(myGif);


        text.setText("         " + ((NewTeachGifImageResult.NewTeachGifImageBeanBean) arrayList.get(p)).getGif_text());
        titleTv.setText(((NewTeachGifImageResult.NewTeachGifImageBeanBean) arrayList.get(p)).getGif_name());
        if (position < arrayList.size()) {
            fenzi.setText(position + 1 + "");
            fenmu.setText(arrayList.size() + "");
        } else {
            fenzi.setText(arrayList.size() + "");
            fenmu.setText(arrayList.size() + "");
        }
    }


    @OnClick({R.id.fidback, R.id.up, R.id.next,R.id.turn_left,R.id.turn_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.up:
                position--;

                if(position>=0&&position<arrayList.size()){
                    //jian++;
                    if(currentPregress-46 >0){
                        currentPregress =currentPregress-46-1;
                    }else{
                        currentPregress =46- currentPregress-1;
                    }
                    flag_left = true;
                    normal =false;

                    // currentPregress= -(msg0.arg1%46);
                }
                if (position < 0) {

                    //当gif为第一节的时候，重置position
                    position = 0;
                    // hander.removeMessages(msg.what);
                    // initView(0);
                    Toast.makeText(NewTeachStartActivity.this, "已经是第一节", Toast.LENGTH_SHORT).show();
                    // Log.i("zhang1", position + "");
                    return;
                } else if (position == arrayList.size() - 1) {
                    if(fenzi.getText().toString().equals("30")){
                        currentPregress = total -msg0.arg1;
                        Log.i("haha2",currentPregress+"");
                    }
                    return;
                } else {
                    //运动进度的控制

                    firstLayout.setVisibility(View.VISIBLE);
                    bottomLayout.setVisibility(View.VISIBLE);
                    restLayout.setVisibility(View.GONE);
                    turnLeft.setVisibility(View.VISIBLE);
                    turnRight.setVisibility(View.VISIBLE);
                    singleTime.setText("");
                    leakLine.setVisibility(View.GONE);
                    allTime.setText("");
                    circleChart.setPercentage(0);
                    circleChart.chartRender();
                    circleChart.invalidate();
                    // position++;
                    i = -3;
                    //进入下一节
                    initView(position);

                    Log.i("教程", "进入下一节---999999" + msg.arg1);
                }
                break;
            case R.id.turn_left:
                position--;

                if(position>=0&&position<arrayList.size()){
                    //jian++;
                    if(currentPregress-46 >0){
                        currentPregress =currentPregress-46-1;
                    }else{
                        currentPregress =46- currentPregress-1;
                    }
                    flag_left = true;
                    normal =false;

                    // currentPregress= -(msg0.arg1%46);
                }
                if (position < 0) {

                    //当gif为第一节的时候，重置position
                    position = 0;
                    // hander.removeMessages(msg.what);
                    // initView(0);
                    Toast.makeText(NewTeachStartActivity.this, "已经是第一节", Toast.LENGTH_SHORT).show();
                    // Log.i("zhang1", position + "");
                    return;
                } else if (position == arrayList.size() - 1) {
                    if(fenzi.getText().toString().equals("30")){
                        currentPregress = total -msg0.arg1;
                        Log.i("haha2",currentPregress+"");
                    }
                    return;
                } else {
                    //运动进度的控制

                    firstLayout.setVisibility(View.VISIBLE);
                    bottomLayout.setVisibility(View.VISIBLE);
                    restLayout.setVisibility(View.GONE);
                    turnLeft.setVisibility(View.VISIBLE);
                    turnRight.setVisibility(View.VISIBLE);
                    singleTime.setText("");
                    leakLine.setVisibility(View.GONE);
                    allTime.setText("");
                    circleChart.setPercentage(0);
                    circleChart.chartRender();
                    circleChart.invalidate();
                    // position++;
                    i = -3;
                    //进入下一节
                    initView(position);

                    Log.i("教程", "进入下一节---999999" + msg.arg1);
                }
                break;
            case R.id.next:
                position++;

                if(position>=0&&position<arrayList.size()){
                    //flag_left = true;
                    normal =false;
                    //jia++;
                    currentPregress=currentPregress+46-1;
                   /* currentPregress = 46 - (msg0.arg1%46);*/
                }


                if (position >= arrayList.size()) {
                    //  initView(arrayList.size() - 1);
                    //当gif为最后一节的时候，重置position
                    position = arrayList.size() - 1;
                    Toast.makeText(NewTeachStartActivity.this, "正在播放最后一节", Toast.LENGTH_SHORT).show();
                    return;

                } else if (position == arrayList.size() - 1) {
                    if(fenzi.getText().toString().equals("30")){
                        currentPregress = total -msg0.arg1;
                        Log.i("haha1",currentPregress+"");
                    }
                    return;
                } else {

                    firstLayout.setVisibility(View.VISIBLE);
                    bottomLayout.setVisibility(View.VISIBLE);
                    restLayout.setVisibility(View.GONE);
                    turnLeft.setVisibility(View.VISIBLE);
                    turnRight.setVisibility(View.VISIBLE);
                    // singleTime.setText((msg.arg1-30)+"");
                    singleTime.setText("");
                    leakLine.setVisibility(View.GONE);
                    allTime.setText("");
                    circleChart.setPercentage(0);
                    circleChart.chartRender();
                    circleChart.invalidate();
                    // position++;

                    i = -3;
                    //进入下一节
                    initView(position);

                    // Log.i("教程", "进入下一节---999999" + msg.arg1);
                }

                break;
            case R.id.turn_right:
                position++;

                if(position>=0&&position<arrayList.size()){
                    //flag_left = true;
                    normal =false;
                    //jia++;
                    currentPregress=currentPregress+46-1;
                   /* currentPregress = 46 - (msg0.arg1%46);*/
                }


                if (position >= arrayList.size()) {
                    //  initView(arrayList.size() - 1);
                    //当gif为最后一节的时候，重置position
                    position = arrayList.size() - 1;
                    Toast.makeText(NewTeachStartActivity.this, "正在播放最后一节", Toast.LENGTH_SHORT).show();
                    return;

                } else if (position == arrayList.size() - 1) {
                    if(fenzi.getText().toString().equals("30")){
                        currentPregress = total -msg0.arg1;
                        Log.i("haha1",currentPregress+"");
                    }
                    return;
                } else {

                    firstLayout.setVisibility(View.VISIBLE);
                    bottomLayout.setVisibility(View.VISIBLE);
                    restLayout.setVisibility(View.GONE);
                    turnLeft.setVisibility(View.VISIBLE);
                    turnRight.setVisibility(View.VISIBLE);
                    // singleTime.setText((msg.arg1-30)+"");
                    singleTime.setText("");
                    leakLine.setVisibility(View.GONE);
                    allTime.setText("");
                    circleChart.setPercentage(0);
                    circleChart.chartRender();
                    circleChart.invalidate();
                    // position++;

                    i = -3;
                    //进入下一节
                    initView(position);

                    // Log.i("教程", "进入下一节---999999" + msg.arg1);
                }

                break;
            case R.id.fidback:
                /*i = 42;
                msg.arg1 = 42;
                position = length - 1;
                hander.sendMessage(msg);*/
                finish();
                break;
        }
    }

    int i = -3;

    //三秒倒计时
    public void setTime() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                for (; i <= 50; i++) {
                    msg = Message.obtain();
                    try {
                        Thread.sleep(1500);
                        msg.what = 0;
                        msg.arg1 = i;
                        hander.sendMessage(msg);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    /*class MyThread1 implements Runnable{
        @Override
        //记得要资源公共，要在run方法之前加上synchronized关键字，要不然会出现抢资源的情况
        public synchronized  void  run() {
            for (; i <= 50; i++) {
                if(msg == null){
                    msg = Message.obtain();
                }
                try {
                    Thread.sleep(1000);
                    msg.what = 0;
                    msg.arg1 = i;
                    hander.sendMessage(msg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }*/

    Handler hander = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    singleTime.setText(msg.arg1 + "");
                    if (msg.arg1 == -3) {
                        caluLayout.setVisibility(View.VISIBLE);
                        praticeLayout.setVisibility(View.GONE);
                        caluTime.setText(msg.arg1 + 6 + "");
                        voicePlayerUtil.startVoice(msg.arg1 + 6 + "");
                    } else if (msg.arg1 == -2) {
                        caluLayout.setVisibility(View.VISIBLE);
                        praticeLayout.setVisibility(View.GONE);
                        caluTime.setText(msg.arg1 + 4 + "");
                        voicePlayerUtil.startVoice(msg.arg1 + 4 + "");
                    } else if (msg.arg1 == -1) {
                        caluLayout.setVisibility(View.VISIBLE);
                        praticeLayout.setVisibility(View.GONE);
                        caluTime.setText(msg.arg1 + 2 + "");
                        voicePlayerUtil.startVoice(msg.arg1 + 2 + "");
                    } else if (msg.arg1 == 0) {
                        caluLayout.setVisibility(View.VISIBLE);
                        praticeLayout.setVisibility(View.GONE);
                        caluTime.setText("Go!");
                        voicePlayerUtil.startVoice("开始运动");

                        //开始计时
                        sportTime.start();
                    } else if (msg.arg1 == 1) {
                        caluLayout.setVisibility(View.GONE);
                        praticeLayout.setVisibility(View.VISIBLE);
                        singleTime.setText("");
                        leakLine.setVisibility(View.GONE);
                        allTime.setText("");
                        voicePlayerUtil.startVoice(msg.arg1 + "");

                    } else if (msg.arg1 > 1 && msg.arg1 < 31) {
                        voicePlayerUtil.startVoice(msg.arg1 + "");
                        firstLayout.setVisibility(View.VISIBLE);
                        bottomLayout.setVisibility(View.VISIBLE);
                        restLayout.setVisibility(View.GONE);
                        turnLeft.setVisibility(View.VISIBLE);
                        turnRight.setVisibility(View.VISIBLE);
                        singleTime.setText((msg.arg1-1)+ "");
                        leakLine.setVisibility(View.VISIBLE);
                        allTime.setText("30'");
                        circleChart.setPercentage((int) (100 * ((float) (msg.arg1-1) / 30)));
                        circleChart.chartRender();
                        circleChart.invalidate();
                    } else if (msg.arg1 >= 31 && msg.arg1 <42) {
                        if (msg.arg1 == 41) {
                            firstLayout.setVisibility(View.GONE);
                            bottomLayout.setVisibility(View.GONE);
                            restLayout.setVisibility(View.VISIBLE);
                            turnLeft.setVisibility(View.GONE);
                            turnRight.setVisibility(View.GONE);

                            singleTime.setText(10 + "");
                            leakLine.setVisibility(View.VISIBLE);
                            allTime.setText("10'");
                            circleChart.setPercentage((int) (100 * ((float) (40 - 30) / 10)));
                            // circleChart.setPercentage(100);
                            circleChart.chartRender();
                            circleChart.invalidate();
                        } else {
                            if(msg.arg1 == 31){
                                voicePlayerUtil.startVoice("休息十秒");
                            }
                            firstLayout.setVisibility(View.GONE);
                            bottomLayout.setVisibility(View.GONE);
                            restLayout.setVisibility(View.VISIBLE);
                            turnLeft.setVisibility(View.GONE);
                            turnRight.setVisibility(View.GONE);
                            leakLine.setVisibility(View.VISIBLE);
                            singleTime.setText((msg.arg1 - 31) + "");
                            allTime.setText("10'");
                            circleChart.setPercentage((int) (100 * ((float) (msg.arg1 - 31) / 10)));
                            // circleChart.setPercentage(100);
                            circleChart.chartRender();
                            circleChart.invalidate();
                        }
                    } else if (msg.arg1 == 42) {
                        if (position + 1 == length) {
                            finish();            //教程结束
                        } else {
                            firstLayout.setVisibility(View.VISIBLE);
                            bottomLayout.setVisibility(View.VISIBLE);
                            restLayout.setVisibility(View.GONE);
                            turnLeft.setVisibility(View.VISIBLE);
                            turnRight.setVisibility(View.VISIBLE);
                            // singleTime.setText((msg.arg1 - 31) + "");
                            singleTime.setText("");
                            leakLine.setVisibility(View.GONE);
                            allTime.setText("");
                            // circleChart.setPercentage((int) (100 * ((float) (msg.arg1 - 30) / 10)));
                            circleChart.setPercentage(0);
                            circleChart.chartRender();
                            circleChart.invalidate();
                            position++;
                            i = -3;
                            //进入下一节
                            initView(position);
                        }
                    }

                    break;
            }
        }
    };


    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg0) {
            switch (msg0.what) {
                case STOP:// 到了最大值
                    // textView.setText(String.valueOf(msg.arg1) + "%" + "完成");
                    // myProgressRetangeBar.setVisibility(View.GONE);//隐藏进度条
                    break;
                case NEXT:

                    // currentPregress = msg0.arg1;
                    //底部条形进度条的进度动态显示
                    if(normal) {
                        myProgressRetangeBar.setProgress(msg0.arg1);
                    }else{
                        int p = msg0.arg1+currentPregress;
                        if((total-36)>=p){
                            myProgressRetangeBar.setProgress(p);
                        }else{
                            if(flag_left){
                                myProgressRetangeBar.setProgress(total-36-j);
                                j++;
                                flag_left = false;
                            }else{
                                myProgressRetangeBar.setProgress(total-36+n);
                                n++;

                            }
                        }
                    }
                    break;
            }
            super.handleMessage(msg0);
        }
    };

    @Override
    protected void onPause() {
        super.onPause();
        i = 42;
        msg.arg1 = 42;
        position = length - 1;
        hander.sendMessage(msg);
    }
/*
    @Override
    protected void onDestroy() {
        super.onDestroy();

    }*/
}
