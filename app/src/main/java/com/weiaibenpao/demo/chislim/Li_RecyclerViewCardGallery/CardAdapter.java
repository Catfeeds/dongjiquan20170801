package com.weiaibenpao.demo.chislim.Li_RecyclerViewCardGallery;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.view.jameson.library.CardAdapterHelper;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.bean.SportTypeBean;
import com.weiaibenpao.demo.chislim.map.activity.DrawTraceActivity;
import com.weiaibenpao.demo.chislim.ui.FavouritePicActivity;
import com.weiaibenpao.demo.chislim.ui.SportDisStyleActivity;
import com.weiaibenpao.demo.chislim.ui.SportHomeActivity;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by jameson on 8/30/16.
 */
public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder> {
    Context context;
    private List<Integer> mList = new ArrayList<>();
    private CardAdapterHelper mCardAdapterHelper = new CardAdapterHelper();

    public CardAdapter(Context context,List<Integer> mList) {
        this.context = context;
        this.mList = mList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_card_item, parent, false);
        mCardAdapterHelper.onCreateViewHolder(parent, itemView);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        mCardAdapterHelper.onBindViewHolder(holder.itemView, position, getItemCount());
//        holder.mImageView.setImageResource(mList.get(position));
        Glide.with(context)
                .load(mList.get(position))
                .placeholder(R.mipmap.zhanwei)
                .error(R.mipmap.zhanwei)
               // .crossFade()
                //默认再带淡入淡出动画，默认300毫秒，
//                .crossFade(5000)
                .centerCrop()
                //取消动画
                .dontAnimate()
                //是否跳过内存缓存，true为跳过
                .skipMemoryCache(true)
                //图片压缩
                //.override(300,500)
                // .transform(new GlideCircxleTransform(context,10))
                //磁盘缓存1.ALL:缓存原图(SOURCE)和处理图(RESULT)
                // 2.NONE:什么都不缓存
                // 3.SOURCE:只缓存原图(SOURCE)
                //  4.RESULT:只缓存处理图(RESULT) —默认值
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .into(holder.mImageView);

        if(position == 0){
            holder.sportTitle.setText("标准跑步");
        }else if(position == 1){
            holder.sportTitle.setText("图形跑步");
        }/*else if(position == 2){
            holder.sportTitle.setText("模拟跑步");
        }*/else if(position == 2){
            holder.sportTitle.setText("行政跑步");
        }

        holder.sporthome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  intent;
                switch (position) {
                    case 0:
                        intent = new Intent(context, SportHomeActivity.class);
                        intent.putExtra("sportTypeBean", new SportTypeBean("", "趣味跑步", ""));
                        context.startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(context, FavouritePicActivity.class);
                        intent.putExtra("position", 0);
                        context.startActivity(intent);
                        break;
                    /*case 2:
                        intent = new Intent(context, SportStyleActivity.class);
                        intent.putExtra("position", 0);
                        context.startActivity(intent);
                        break;*/
                    case 2:
                        //室内行政跑
                        intent = new Intent(context, SportDisStyleActivity.class);
                        intent.putExtra("position", 0);
                        context.startActivity(intent);
                        break;
                }
            }
        });
        holder.sportout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  intent;
                switch (position) {
                    case 0:
                        intent = new Intent(context, DrawTraceActivity.class);
                        intent.putExtra("disNum", 0);
                        context.startActivity(intent);
                        break;
                    case 1:
                        intent = new Intent(context, FavouritePicActivity.class);
                        intent.putExtra("position", 1);
                        context.startActivity(intent);
                        break;
                    /*case 2:
                        intent = new Intent(context, SportStyleActivity.class);
                        intent.putExtra("position", 1);
                        context.startActivity(intent);
                        break;*/
                    case 2:
                        //室外行政跑
                        intent = new Intent(context, SportDisStyleActivity.class);
                        intent.putExtra("position", 1);
                        context.startActivity(intent);
                        break;
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final RelativeLayout sporthome;
        public final RelativeLayout sportout;
        public final ImageView mImageView;
        public final TextView sportTitle;

        public ViewHolder(final View itemView) {
            super(itemView);
            sporthome = (RelativeLayout) itemView.findViewById(R.id.sporthome);
            sportout = (RelativeLayout) itemView.findViewById(R.id.sportout);
            mImageView = (ImageView) itemView.findViewById(R.id.imageView);
            sportTitle = (TextView) itemView.findViewById(R.id.sportTitle);
        }
    }
}
