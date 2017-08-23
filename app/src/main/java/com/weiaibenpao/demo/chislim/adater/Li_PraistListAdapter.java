package com.weiaibenpao.demo.chislim.adater;


import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.bean.Li_Praise_Result;
import com.weiaibenpao.demo.chislim.map.customview.CircleImageView;

import java.util.ArrayList;

/**
 * 教程界面
 * Created by lenovo on 2016/10/11.
 */

public class Li_PraistListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private LayoutInflater mInflater;
    private ArrayList praiseList = new ArrayList();
    Context context;


    private static OnItemClickListener mOnItemClickListener;
    //设置点击和长按事件

    public interface OnItemClickListener {
        void onItemClick(View view, int userId); //设置点击事件
    }
    public static void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }


    public Li_PraistListAdapter(Context context) {
        this.context = context;
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

        View view = mInflater.inflate(R.layout.item_praiselist, parent, false);
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
            ((ItemViewHolder) holder).userLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(((ItemViewHolder) holder).userLayout, ((Li_Praise_Result.DataBean.PraiselistBean)praiseList.get(position)).getUserId());
                }
            });

        }
        Picasso.with(context)
                .load(((Li_Praise_Result.DataBean.PraiselistBean)praiseList.get(position)).getUserIcon())
                .placeholder(R.mipmap.zhanwei)
                .error(R.mipmap.zhanwei)
                .config(Bitmap.Config.RGB_565)
                .into(((ItemViewHolder) holder).userIcon);

        String username = ((Li_Praise_Result.DataBean.PraiselistBean)praiseList.get(position)).getUserNickName();
        if(username.length() > 2){
            username = username.substring(0,2) + "...";
        }
        ((ItemViewHolder) holder).userName.setText(username);
    }

    /**
     * 要显示的item数目
     * @return
     */
    @Override
    public int getItemCount() {
        return praiseList.size();
    }


    public void loadMoreData(ArrayList moreList) {
        praiseList.addAll(moreList);
        notifyDataSetChanged();
    }

    public void refreshData(ArrayList dataList){
        praiseList = dataList;
        notifyDataSetChanged();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public CircleImageView userIcon;
        public TextView userName;
        LinearLayout userLayout;

        public ItemViewHolder(View view) {
            super(view);
            userIcon = (CircleImageView) view.findViewById(R.id.user_icon);
            userName = (TextView) view.findViewById(R.id.user_name);
            userLayout = (LinearLayout) view.findViewById(R.id.userLayout);
        }
    }
}