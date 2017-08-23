package com.weiaibenpao.demo.chislim.adater;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.bean.MenuBean;

import java.util.ArrayList;

import static com.squareup.picasso.MemoryPolicy.NO_CACHE;

/**
 * Created by lenovo on 2016/10/11.
 */

public class RecyclerTeachAdapter extends RecyclerView.Adapter<RecyclerTeachAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private int num;
    ArrayList menuList;
    Context context;

    private static OnItemClickListener mOnItemClickListener;
    //设置点击和长按事件

    public interface OnItemClickListener {
        void onItemClick(View view, int position); //设置点击事件
        void onItemLongClick(View view, int position); //设置长按事件
    }
    public static void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    /**
     * 构造器
     * @param context
     * @param num
     */
    public RecyclerTeachAdapter(Context context, ArrayList menuList,int num) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.menuList = menuList;
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
        View view = mInflater.inflate(R.layout.item_teach, parent, false);

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
            holder.myTvClass.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(holder.myTvClass, position);
                }
            });
            holder.myTvClass.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mOnItemClickListener.onItemLongClick(holder.itemView, position);
                    return false;
                }
            });
        }
        Picasso.with(context)
                .load(((MenuBean)menuList.get(position)).getMenuImage())
                .config(Bitmap.Config.RGB_565)
                .memoryPolicy(NO_CACHE)
                .into(holder.myTvClass);
    }

    /**
     * 要显示的item数目
     * @return
     */
    @Override
    public int getItemCount() {
        return menuList.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView myTvClass;

        public ViewHolder(View view) {
            super(view);
            myTvClass = (ImageView) view.findViewById(R.id.myTvClass);
        }
    }
}