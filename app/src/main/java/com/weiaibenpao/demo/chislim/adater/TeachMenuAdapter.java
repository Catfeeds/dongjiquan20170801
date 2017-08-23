package com.weiaibenpao.demo.chislim.adater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weiaibenpao.demo.chislim.R;

import java.util.ArrayList;

/**
 * Created by lenovo on 2016/10/11.
 */

public class TeachMenuAdapter extends RecyclerView.Adapter<TeachMenuAdapter.ViewHolder> {
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
    public TeachMenuAdapter(Context context, ArrayList dataList, int num) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        travelList = dataList;
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
        View view = mInflater.inflate(R.layout.item_teachmenu, parent, false);
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
            holder.item_TV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(holder.item_TV, position,travelList);
                    /*for(int i=0;i<travelList.size();i++){
                        notifyItemChanged(i);
                    }
                    holder.item_TV.setBackgroundResource(R.drawable.stoptype);*/
                }
            });
            holder.item_TV.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mOnItemClickListener.onItemLongClick(holder.itemView, position,travelList);
                    return false;
                }
            });
        }
        holder.item_TV.setText(travelList.get(position).toString());

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

        public ViewHolder(View view) {
            super(view);
            item_TV = (TextView) view.findViewById(R.id.myText);
        }
    }
}