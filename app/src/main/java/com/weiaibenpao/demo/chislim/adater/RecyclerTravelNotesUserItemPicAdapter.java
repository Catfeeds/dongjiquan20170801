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
import com.weiaibenpao.demo.chislim.util.Default;

/**
 * 游记横向图片适配
 * Created by lenovo on 2016/10/11.
 */

public class RecyclerTravelNotesUserItemPicAdapter extends RecyclerView.Adapter<RecyclerTravelNotesUserItemPicAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private String[] travelList;
    Context context;

    private static OnItemClickListener mOnItemClickListener;
    //设置点击和长按事件

    public interface OnItemClickListener {
        void onItemClick(View view, int position, String[] travelList); //设置点击事件
        void onItemLongClick(View view, int position,String[] travelList); //设置长按事件
    }
    public static void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    /**
     * 构造器
     * @param context
     */
    public RecyclerTravelNotesUserItemPicAdapter(Context context, String[] travelNotes) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);

        travelList = travelNotes;
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
        View view = mInflater.inflate(R.layout.item_notesuseritem, parent, false);
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
        if (mOnItemClickListener != null) {
            holder.myImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(holder.myImage, position,travelList);
                }
            });
            holder.myImage.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mOnItemClickListener.onItemLongClick(holder.itemView, position,travelList);
                    return false;
                }
            });
        }

        //绑定数据
        Picasso.with(context)
                .load(Default.urlPic+travelList[position])
                .placeholder(R.mipmap.zhanwei)
                .error(R.mipmap.zhanwei)
                .config(Bitmap.Config.RGB_565)
                .into(holder.myImage);
    }

    /**
     * 要显示的item数目
     * @return
     */
    @Override
    public int getItemCount() {
        return travelList.length;
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView myImage;

        public ViewHolder(View view) {
            super(view);
            myImage = (ImageView) view.findViewById(R.id.myImage);
        }
    }
}