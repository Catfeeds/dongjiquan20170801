package com.weiaibenpao.demo.chislim.adater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.weiaibenpao.demo.chislim.R;

import java.util.ArrayList;

/**
 * Created by lenovo on 2016/10/11.
 */

public class RecyclerSuccessAdapter extends RecyclerView.Adapter<RecyclerSuccessAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private int num;
    ArrayList imageList;
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
    public RecyclerSuccessAdapter(Context context, ArrayList imageList,int num) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.imageList = imageList;
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
        View view = mInflater.inflate(R.layout.item_success, parent, false);

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
            holder.myImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(holder.myImage, position,imageList);
                }
            });
            holder.myImage.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mOnItemClickListener.onItemLongClick(holder.itemView, position,imageList);
                    return false;
                }
            });
        }
        Log.i("测试" + position,imageList.get(position).toString());
        Picasso.with(context)
                .load(imageList.get(position).toString())
                .error(R.mipmap.zhanwei)
                .into(holder.myImage);

       // holder.myText.setText("累计" + imageList.get(position).toString());
    }

    /**
     * 要显示的item数目
     * @return
     */
    @Override
    public int getItemCount() {
        return imageList.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView myImage;
        public TextView myText;

        public ViewHolder(View view) {
            super(view);
            myImage = (ImageView) view.findViewById(R.id.myImageSuccess);
            myText = (TextView) view.findViewById(R.id.myTextSuccess);
        }
    }
}