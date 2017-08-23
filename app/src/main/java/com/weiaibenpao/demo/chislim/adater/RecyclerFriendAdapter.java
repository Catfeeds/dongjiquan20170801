package com.weiaibenpao.demo.chislim.adater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.weiaibenpao.demo.chislim.R;

/**
 * Created by lenovo on 2016/10/11.
 */

public class RecyclerFriendAdapter extends RecyclerView.Adapter<RecyclerFriendAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private int num;
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
    public RecyclerFriendAdapter(Context context, int num) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
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
        View view = mInflater.inflate(R.layout.item_fragment_find, parent, false);

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
                    mOnItemClickListener.onItemClick(holder.item_IM, position);
                }
            });
            holder.item_IM.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mOnItemClickListener.onItemLongClick(holder.itemView, position);
                    return false;
                }
            });
        }
        //绑定数据
        Picasso.with(context)
                .load(R.mipmap.zhanwei)
                .error(R.mipmap.zhanwei)
                .into(holder.item_IM);
        holder.item_title.setText("柏林赛道梦游记");
        holder.item_text.setText("2016年柏林马拉松赛，柏林马拉松赛");
        holder.item_type.setText("跑步故事");
    }

    /**
     * 要显示的item数目
     * @return
     */
    @Override
    public int getItemCount() {
        return num;
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView item_IM;
        public TextView item_title;
        public TextView item_text;
        public TextView item_type;

        public ViewHolder(View view) {
            super(view);
            item_IM = (ImageView) view.findViewById(R.id.myImage);
            item_title = (TextView) view.findViewById(R.id.myTitle);
            item_text = (TextView) view.findViewById(R.id.myText);
            item_type = (TextView) view.findViewById(R.id.myType);
        }
    }
}