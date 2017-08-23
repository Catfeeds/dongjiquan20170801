package com.weiaibenpao.demo.chislim.adater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.weiaibenpao.demo.chislim.R;

import java.util.ArrayList;

/**
 * Created by lenovo on 2016/10/11.
 */

public class RecyclerTravelPictureAdapter extends RecyclerView.Adapter<RecyclerTravelPictureAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private int num;
    private ArrayList travelList;
    Context context;
    String[] temp;

    private static OnItemClickListener mOnItemClickListener;
    //设置点击和长按事件

    public interface OnItemClickListener {
        void onItemClick(View view, int position, String[] temp);            //设置点击事件
        void onItemLongClick(View view, int position, String[] temp);        //设置长按事件
    }
    public static void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    /**
     * 构造器
     * @param context
     * @param num
     */
    public RecyclerTravelPictureAdapter(Context context, String[] temp, int num) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.temp = temp;
        this.num = num;
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
        View view = mInflater.inflate(R.layout.item_big_picture, parent, false);
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
            holder.item_IM.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(holder.item_IM, position,temp);
                }
            });
            holder.item_IM.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mOnItemClickListener.onItemLongClick(holder.itemView, position,temp);
                    return false;
                }
            });
        }

        //绑定数据
       /* Picasso.with(context)
                .load(temp[position])
                .placeholder(R.mipmap.zhanwei)
                .error(R.mipmap.zhanwei)
                .memoryPolicy(NO_CACHE, NO_STORE)
                .config(Bitmap.Config.RGB_565)
                .into(holder.item_IM);
*/
        //心情图片
        Glide.with(context)
                .load(temp[position])
                .placeholder(R.mipmap.zhanwei)
                .error(R.mipmap.zhanwei)
                .crossFade()
                .skipMemoryCache(false)
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(holder.item_IM);
    }

    /**
     * 要显示的item数目
     * @return
     */
    @Override
    public int getItemCount() {
        return temp.length;
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView item_IM;

        public ViewHolder(View view) {
            super(view);
            item_IM = (ImageView) view.findViewById(R.id.myPic);
        }
    }
}