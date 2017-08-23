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

public class RecyclerMarkAdapter extends RecyclerView.Adapter<RecyclerMarkAdapter.ViewHolder> {
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
    public RecyclerMarkAdapter(Context context, int num) {
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
        View view = mInflater.inflate(R.layout.item_mark, parent, false);

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
            holder.item_date.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(holder.item_date, position);
                }
            });
            holder.item_date.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mOnItemClickListener.onItemLongClick(holder.itemView, position);
                    return false;
                }
            });
        }
        /**
         * http://ofplk6att.bkt.clouddn.com/shop1.jpg
         * http://ofplk6att.bkt.clouddn.com/shop2.jpg
         * http://ofplk6att.bkt.clouddn.com/shop3.jpg
         * http://ofplk6att.bkt.clouddn.com/shop4.jpg
         * http://ofplk6att.bkt.clouddn.com/shop5.jpg
         *
         */
        Picasso.with(context).load("http://ofplk6att.bkt.clouddn.com/shop1.jpg").into(holder.myImage);
        holder.item_date.setText("启迈斯跑步机");
        holder.item_intro.setText("T600性价比之王，限时抢购");
        holder.itemNum.setText("20000" + "积分");
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
        public ImageView myImage;
        public TextView item_date;
        public TextView item_intro;
        public TextView itemNum;

        public ViewHolder(View view) {
            super(view);
            myImage = (ImageView) view.findViewById(R.id.myImage);
            item_date = (TextView) view.findViewById(R.id.date);
            item_intro = (TextView) view.findViewById(R.id.text);
            itemNum = (TextView) view.findViewById(R.id.itemNum);
        }
    }
}