package com.weiaibenpao.demo.chislim.adater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.weiaibenpao.demo.chislim.R;

import java.util.ArrayList;

/**
 * Created by lenovo on 2016/10/11.
 */

public class RecyclerSportSelectAdapter extends RecyclerView.Adapter<RecyclerSportSelectAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private ArrayList<String> arrayList;
    Context context;

    private static OnItemClickListener mOnItemClickListener;
    //设置点击和长按事件

    public interface OnItemClickListener {
        void onItemClick(View view, int position); //设置点击事件
    }
    public static void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    /**
     * 构造器
     * @param context
     */
    public RecyclerSportSelectAdapter(Context context, ArrayList dataList) {
        this.context = context;
        this.arrayList = dataList;
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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_sport_select_travel, parent, false);
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
            holder.itemLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(holder.itemLayout, position);
                }
            });
        }

        //绑定数据
        holder.item_num.setText(arrayList.get(position));
    }

    /**
     * 要显示的item数目
     * @return
     */
    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public  class ViewHolder extends RecyclerView.ViewHolder {
        public TextView item_num;
        LinearLayout itemLayout;

        public ViewHolder(View view) {
            super(view);
            item_num = (TextView) view.findViewById(R.id.item_num);
            itemLayout  = (LinearLayout) view.findViewById(R.id.itemLayout);
        }
    }
}