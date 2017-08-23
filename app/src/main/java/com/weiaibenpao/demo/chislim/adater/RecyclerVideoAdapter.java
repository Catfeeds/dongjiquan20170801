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
import com.weiaibenpao.demo.chislim.bean.TeachResult;

import java.util.ArrayList;

import static com.squareup.picasso.MemoryPolicy.NO_CACHE;
import static com.squareup.picasso.MemoryPolicy.NO_STORE;

/**
 * Created by lenovo on 2016/10/11.
 */
public class RecyclerVideoAdapter extends RecyclerView.Adapter<RecyclerVideoAdapter.ViewHolder> {
    private ScrollParallaxImageView.ParallaxStyle parallaxStyle;
    private LayoutInflater mInflater;
    private int num;
    private ArrayList travelList;
    Context context;

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
     * @param num
     */
    public RecyclerVideoAdapter(Context context, ArrayList dataList, int num,ScrollParallaxImageView.ParallaxStyle parallaxStyle) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        travelList = dataList;
        this.num = num;
        this.parallaxStyle = parallaxStyle;
    }

    /**
     * item显示类型
     * 引入布局
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_picture_video, parent, false);
        //view.setBackgroundColor(Color.RED);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    /**
     * 数据的绑定显示
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        //绑定事件
        if (mOnItemClickListener != null) {
            holder.iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(holder.iv, position,travelList);
                }
            });
            holder.iv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mOnItemClickListener.onItemLongClick(holder.itemView, position,travelList);
                    return false;
                }
            });
        }
        holder.iv.setParallaxStyles(parallaxStyle);
        //绑定数据
/*        Picasso.with(context)
                .load(((TeachResult.TeachBean)(travelList.get(position))).getTeachImage())
                .placeholder(R.mipmap.noshow)
                .error(R.mipmap.zhanwei)
                .config(Bitmap.Config.RGB_565)
                .into(holder.iv);*/
                Picasso.with(context).load(((TeachResult.TeachBean)(travelList.get(position))).getTeachImage()) .placeholder(R.mipmap.zhanwei).error(R.mipmap.zhanwei) .config(Bitmap.Config.RGB_565).memoryPolicy(NO_CACHE,NO_STORE) .into(holder.iv);

        /*switch (position % 5) {
            case 0 :
                Picasso.with(context).load(R.mipmap.pic1) .placeholder(R.mipmap.zhanwei).error(R.mipmap.zhanwei) .config(Bitmap.Config.RGB_565).memoryPolicy(NO_CACHE,NO_STORE) .into(holder.iv);
                 break;
            case 1 : holder.iv.setImageResource(R.mipmap.pic2);
                Picasso.with(context).load(R.mipmap.pic2) .placeholder(R.mipmap.zhanwei).error(R.mipmap.zhanwei) .config(Bitmap.Config.RGB_565).memoryPolicy(NO_CACHE,NO_STORE) .into(holder.iv);
                break;
            case 2 :
                Picasso.with(context).load(R.mipmap.pic3) .placeholder(R.mipmap.zhanwei).error(R.mipmap.zhanwei) .config(Bitmap.Config.RGB_565).memoryPolicy(NO_CACHE,NO_STORE) .into(holder.iv);
                break;
            case 3 :
                Picasso.with(context).load(R.mipmap.pic4) .placeholder(R.mipmap.zhanwei).error(R.mipmap.zhanwei) .config(Bitmap.Config.RGB_565).memoryPolicy(NO_CACHE,NO_STORE) .into(holder.iv);
                break;
            case 4 :
                Picasso.with(context).load(R.mipmap.pic5) .placeholder(R.mipmap.zhanwei).error(R.mipmap.zhanwei) .config(Bitmap.Config.RGB_565).memoryPolicy(NO_CACHE,NO_STORE) .into(holder.iv);
                break;
        }*/

        holder.item_TV.setText(((TeachResult.TeachBean)(travelList.get(position))).getTeachName());
    }

    /**
     * 要显示的item数目
     * @return
     */
    @Override
    public int getItemCount() {
        return travelList.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView item_TV;
        ScrollParallaxImageView iv;
        public ViewHolder(View view) {
            super(view);
            item_TV = (TextView) view.findViewById(R.id.item_TV);
            iv = (ScrollParallaxImageView) itemView.findViewById(R.id.tralPic1);
        }
    }
}