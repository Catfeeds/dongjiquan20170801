package com.weiaibenpao.demo.chislim.adater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.bean.SportTypeBean;

import java.util.ArrayList;

/**
 * Created by lenovo on 2016/10/11.
 */

public class SportHomeTypeAdapter extends RecyclerView.Adapter<SportHomeTypeAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private int num;
    ArrayList sportList;
    Context context;

    private static OnItemClickListener mOnItemClickListener;
    //设置点击和长按事件

    public interface OnItemClickListener {
        void onItemClick(View view, SportTypeBean sportTypeBean); //设置点击事件
        void onItemLongClick(View view, SportTypeBean sportTypeBean); //设置长按事件
    }
    public static void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    /**
     * 构造器
     * @param context
     * @param num
     */
    public SportHomeTypeAdapter(Context context, ArrayList sportList, int num) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.sportList = sportList;
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
        View view = mInflater.inflate(R.layout.item_sporthometype, parent, false);

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
            holder.muLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(holder.muLayout, (SportTypeBean)sportList.get(position));
                }
            });
            holder.muLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mOnItemClickListener.onItemLongClick(holder.itemView, (SportTypeBean)sportList.get(position));
                    return false;
                }
            });
        }
        Picasso.with(context)
                .load(((SportTypeBean)sportList.get(position)).getTypeImage())
                .error(R.mipmap.zhanwei)
                .into(holder.typeimage);
        holder.typeintro.setText(((SportTypeBean)sportList.get(position)).getTypeIntro());
        holder.typetime.setText(((SportTypeBean)sportList.get(position)).getTypeTime());
    }

    /**
     * 要显示的item数目
     * @return
     */
    @Override
    public int getItemCount() {
        return sportList.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView typeimage;
        public TextView typeintro;
        public TextView typetime;
        public LinearLayout muLayout;

        public ViewHolder(View view) {
            super(view);
            typeimage = (ImageView) view.findViewById(R.id.typeimage);
            typeintro = (TextView) view.findViewById(R.id.typeintro);
            typetime = (TextView) view.findViewById(R.id.typetime);
            muLayout = (LinearLayout) view.findViewById(R.id.muLayout);
        }
    }
}