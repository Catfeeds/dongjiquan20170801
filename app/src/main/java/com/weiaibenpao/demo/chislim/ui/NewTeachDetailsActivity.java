package com.weiaibenpao.demo.chislim.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.jaeger.library.StatusBarUtil;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.bean.NewTeachGifImageResult;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class NewTeachDetailsActivity extends Activity {


    @BindView(R.id.back)
    ImageView back;
    @BindView(R.id.titleTv)
    TextView titleTv;
    @BindView(R.id.details_gif)
    ImageView detailsGif;
    @BindView(R.id.details_text)
    TextView detailsText;
    ArrayList arrayList;
    int position;
    Context context;
    @BindView(R.id.left_layout)
    LinearLayout leftLayout;
    @BindView(R.id.right_layout)
    LinearLayout rightLayout;
    @BindView(R.id.selector_left)
    ImageView selectorLeft;
    @BindView(R.id.selector_right)
    ImageView selectorRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_teachdetails_layout);
        StatusBarUtil.setColor(this , getResources().getColor(R.color.ku_bg ) , 0);
        ButterKnife.bind(this);
        context = getApplicationContext();
        Intent intent = getIntent();

        arrayList = intent.getParcelableArrayListExtra("datalist");
        position = intent.getIntExtra("position", 0);
        initData(position);

    }

    private void initData(int p) {
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
                .into(detailsGif);

        detailsText.setText("         " + ((NewTeachGifImageResult.NewTeachGifImageBeanBean) arrayList.get(p)).getGif_text());
        titleTv.setText(((NewTeachGifImageResult.NewTeachGifImageBeanBean) arrayList.get(p)).getGif_name());

    }


    @OnClick(R.id.back)
    public void onClick() {
        finish();
    }

    @OnClick({R.id.left_layout, R.id.right_layout,R.id.selector_left,R.id.selector_right})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.left_layout:
                position--;

                if (position < 0) {

                    //当gif为第一节的时候，重置position
                    position = 0;
                    // initView(0);
                    //Toast.makeText(NewTeachDetailsActivity.this, "当前已经是第一节!", Toast.LENGTH_SHORT).show();
                    // Log.i("zhang1", position + "");
                    return;
                } else if (position == arrayList.size() - 1) {

                    return;
                } else {

                    //进入下一节
                    initData(position);
                }
                break;
            case R.id.right_layout:
                position++;
                if (position >= arrayList.size()) {
                    //  initView(arrayList.size() - 1);
                    //当gif为最后一节的时候，重置position
                    position = arrayList.size() - 1;
                    // Toast.makeText(NewTeachDetailsActivity.this, "当前已经是最后节!", Toast.LENGTH_SHORT).show();
                    return;

                } else if (position == arrayList.size() - 1) {

                    return;
                } else {

                    //进入下一节
                    initData(position);

                }

                break;
            case R.id.selector_left:
                position--;

                if (position < 0) {

                    //当gif为第一节的时候，重置position
                    position = 0;
                    // initView(0);
                    //Toast.makeText(NewTeachDetailsActivity.this, "当前已经是第一节!", Toast.LENGTH_SHORT).show();
                    // Log.i("zhang1", position + "");
                    return;
                } else if (position == arrayList.size() - 1) {

                    return;
                } else {

                    //进入下一节
                    initData(position);
                }
                break;
            case R.id.selector_right:
                position++;
                if (position >= arrayList.size()) {
                    //  initView(arrayList.size() - 1);
                    //当gif为最后一节的时候，重置position
                    position = arrayList.size() - 1;
                    // Toast.makeText(NewTeachDetailsActivity.this, "当前已经是最后节!", Toast.LENGTH_SHORT).show();
                    return;

                } else if (position == arrayList.size() - 1) {

                    return;
                } else {

                    //进入下一节
                    initData(position);

                }

                break;




        }
    }
}
