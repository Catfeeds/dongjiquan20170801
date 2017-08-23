package com.weiaibenpao.demo.chislim.adater;


import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gjiazhe.scrollparallaximageview.ScrollParallaxImageView;
import com.squareup.picasso.Picasso;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.bean.NewTeachResult;
import com.weiaibenpao.demo.chislim.util.Default;

import java.util.ArrayList;

/**
 * 教程界面
 * Created by lenovo on 2016/10/11.
 */

public class TeachListAdapter2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private LayoutInflater mInflater;
    private ArrayList travelList = new ArrayList();
    Context context;

    private ScrollParallaxImageView.ParallaxStyle parallaxStyle;

    private static OnItemClickListener mOnItemClickListener;
    //设置点击和长按事件

    public interface OnItemClickListener {
        void onItemClick(View view, int position, ArrayList list); //设置点击事件
        void onItemLongClick(View view, int position, ArrayList list); //设置长按事件
    }
    public static void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    /**
     * 构造器
     * @param context
     */
    /*public TeachListAdapter2(Context context, ArrayList dataList,ScrollParallaxImageView.ParallaxStyle parallaxStyle) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.datalist = dataList;
        this.parallaxStyle = parallaxStyle;
    }*/

    public TeachListAdapter2(Context context, ScrollParallaxImageView.ParallaxStyle parallaxStyle) {
        this.context = context;
        this.parallaxStyle = parallaxStyle;
        this.mInflater = LayoutInflater.from(context);
    }

    /**
     * item显示类型
     * 引入布局
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflater.inflate(R.layout.cardview_item, parent, false);
        ItemViewHolder holder = new ItemViewHolder(view);
        return holder;
    }


    /**
     * 数据的绑定显示
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        if (mOnItemClickListener != null) {
            ((ItemViewHolder) holder).myImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(((ItemViewHolder) holder).myImage, position,travelList);
                }
            });
            ((ItemViewHolder) holder).myImage.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mOnItemClickListener.onItemLongClick(holder.itemView, position,travelList);
                    return false;
                }
            });
        }
         ((ItemViewHolder) holder).myImage.setParallaxStyles(parallaxStyle);
        Picasso.with(context)
                .load(Default.urlPic + ((NewTeachResult.NewTeachBeanBean) travelList.get(position)).getTeach_image())
                .placeholder(R.mipmap.zhanwei)
                .error(R.mipmap.zhanwei)
                .config(Bitmap.Config.RGB_565)
                .into(((ItemViewHolder) holder).myImage);
        ((ItemViewHolder) holder).myImage.setTag(position);
        ((ItemViewHolder) holder).title.setText(((NewTeachResult.NewTeachBeanBean) travelList.get(position)).getTeachName());
        ((ItemViewHolder) holder).hot.setText("完成人数: " + ((NewTeachResult.NewTeachBeanBean) travelList.get(position)).getTeach_userhad());

    }

    /**
     * 要显示的item数目
     * @return
     */
    @Override
    public int getItemCount() {
        return travelList.size();
    }



    public void addAllData(ArrayList dataList) {
        travelList.addAll(dataList);
        notifyDataSetChanged();
    }

    public void clearData() {
        this.travelList.clear();
    }

    public void changeData(ArrayList moreList) {
        travelList = moreList;
        notifyDataSetChanged();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public ScrollParallaxImageView myImage;
        public TextView title;
        public TextView hot;

        public ItemViewHolder(View view) {
            super(view);
            myImage = (ScrollParallaxImageView) view.findViewById(R.id.myImage);
            title = (TextView) view.findViewById(R.id.title);
            hot = (TextView) view.findViewById(R.id.hot);
        }
    }


}