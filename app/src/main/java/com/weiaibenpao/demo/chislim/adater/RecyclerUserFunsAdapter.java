package com.weiaibenpao.demo.chislim.adater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.bean.Li_funsResult;
import com.weiaibenpao.demo.chislim.util.GlideCircleTransform;

import java.util.ArrayList;

/**
 * Created by lenovo on 2016/10/11.
 */

public class RecyclerUserFunsAdapter extends RecyclerView.Adapter<RecyclerUserFunsAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private ArrayList funsList;
    Context context;

    private static OnItemClickListener mOnItemClickListener;
    //设置点击和长按事件

    public interface OnItemClickListener {
        void onItemClick(View view, int id);            //设置点击事件
        void onItemLongClick(View view, int id);        //设置长按事件
    }
    public static void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    /**
     * 构造器
     * @param context
     */
    public RecyclerUserFunsAdapter(Context context, ArrayList funsList) {
        this.context = context;
        this.funsList = funsList;
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
        View view = mInflater.inflate(R.layout.li_item_funs, parent, false);
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
            holder.funsitem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(holder.funsitem, ((Li_funsResult.DataBean.ListBean)funsList.get(position)).getId());
                }
            });
            holder.funsitem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mOnItemClickListener.onItemLongClick(holder.itemView, ((Li_funsResult.DataBean.ListBean)funsList.get(position)).getId());
                    return false;
                }
            });
        }

        //心情图片
        Glide.with(context)
                .load(((Li_funsResult.DataBean.ListBean)funsList.get(position)).getUserIcon())
                .placeholder(R.mipmap.zhanwei)
                .error(R.mipmap.zhanwei)
                .crossFade()
                .skipMemoryCache(false)
                .transform(new GlideCircleTransform(context,10))
                .diskCacheStrategy(DiskCacheStrategy.RESULT)
                .into(holder.userIcon);
        holder.userName.setText(((Li_funsResult.DataBean.ListBean)funsList.get(position)).getUserName());
    }

    /**
     * 要显示的item数目
     * @return
     */
    @Override
    public int getItemCount() {
        return funsList.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView userIcon;
        public TextView userName;
        LinearLayout funsitem;

        public ViewHolder(View view) {
            super(view);
            userIcon = (ImageView) view.findViewById(R.id.userIcon);
            userName = (TextView) view.findViewById(R.id.userName);
            funsitem = (LinearLayout) view.findViewById(R.id.funsitem);
        }
    }
}