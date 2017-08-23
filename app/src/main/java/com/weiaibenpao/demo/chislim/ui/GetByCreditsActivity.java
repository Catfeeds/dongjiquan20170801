package com.weiaibenpao.demo.chislim.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jaeger.library.StatusBarUtil;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.customs.EnvironmentLayout;
import com.weiaibenpao.demo.chislim.customs.LuckPanLayout;
import com.weiaibenpao.demo.chislim.customs.RotatePan;
import com.weiaibenpao.demo.chislim.util.SpanUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class GetByCreditsActivity extends Activity implements RotatePan.AnimationEndListener {

    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.creditTv)
    TextView creditTv;//当前积分
    @BindView(R.id.hintTV)
    TextView hintTV;
    private EnvironmentLayout layout;
    private RotatePan rotatePan;
    private LuckPanLayout luckPanLayout;
    private ImageView goBtn;
    private ImageView yunIv;
    int nowMark;

    private String[] strs = {"T600跑步机", "谢谢惠顾", "综合训练器", "美腰机", "新仰卧板", "沙袋"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gets_by_credit);
        StatusBarUtil.setColor(this , getResources().getColor(R.color.ku_bg ) , 0);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        nowMark = intent.getIntExtra("nowMark", 0);
        creditTv.setText(nowMark + "");

        luckPanLayout = (LuckPanLayout) findViewById(R.id.luckpan_layout);
        luckPanLayout.startLuckLight();
        rotatePan = (RotatePan) findViewById(R.id.rotatePan);
        rotatePan.setAnimationEndListener(this);
        goBtn = (ImageView) findViewById(R.id.go);
        yunIv = (ImageView) findViewById(R.id.yun);

        luckPanLayout.post(new Runnable() {
            @Override
            public void run() {
                int height = getWindow().getDecorView().getHeight();
                int width = getWindow().getDecorView().getWidth();

                int backHeight = 0;

                int MinValue = Math.min(width, height);
                MinValue -= SpanUtil.dip2px(GetByCreditsActivity.this, 10) * 2;
                backHeight = MinValue / 2;

                RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) luckPanLayout.getLayoutParams();
                lp.width = MinValue;
                lp.height = MinValue;

                luckPanLayout.setLayoutParams(lp);

                MinValue -= SpanUtil.dip2px(GetByCreditsActivity.this, 28) * 2;
                lp = (RelativeLayout.LayoutParams) rotatePan.getLayoutParams();
                lp.height = MinValue;
                lp.width = MinValue;

                rotatePan.setLayoutParams(lp);


                lp = (RelativeLayout.LayoutParams) goBtn.getLayoutParams();
                lp.topMargin += backHeight;
                lp.topMargin -= (goBtn.getHeight() / 2);
                goBtn.setLayoutParams(lp);

                getWindow().getDecorView().requestLayout();
            }
        });
    }

    public void rotation(View view) {
        if (nowMark >= 10) {
            rotatePan.startRotate(1);
            luckPanLayout.setDelayTime(100);
            goBtn.setEnabled(false);
            nowMark = nowMark - 10;
            creditTv.setText(nowMark + "");
        } else {
            Toast.makeText(GetByCreditsActivity.this, "当前积分不足，请继续集满至10分哦!", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void endAnimation(int position) {
        goBtn.setEnabled(true);
        luckPanLayout.setDelayTime(500);
        if (strs[position].equals("谢谢惠顾")) {
           // Toast.makeText(this, "谢谢惠顾,请珍惜每次抽奖机会!", Toast.LENGTH_SHORT).show();
            hintTV.setText("抱歉，再接再厉哟!");
        } else {
           // Toast.makeText(this, "恭喜您，抽获" + strs[position], Toast.LENGTH_SHORT).show();
            hintTV.setText("恭喜您，抽获"+strs[position]);
        }
    }


    @OnClick(R.id.back)
    public void onClick() {

        // 上传积分至服务器
        //..........to do sth
        finish();
    }
}

