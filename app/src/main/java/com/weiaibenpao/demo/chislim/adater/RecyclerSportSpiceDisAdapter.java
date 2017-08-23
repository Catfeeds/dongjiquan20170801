package com.weiaibenpao.demo.chislim.adater;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.bean.SportDisSpiceBean;

import java.util.ArrayList;

import static com.squareup.picasso.MemoryPolicy.NO_CACHE;
import static com.squareup.picasso.MemoryPolicy.NO_STORE;

/**
 * Created by lenovo on 2016/10/11.
 */
public class RecyclerSportSpiceDisAdapter extends RecyclerView.Adapter<RecyclerSportSpiceDisAdapter.ViewHolder> {
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
     */
    public RecyclerSportSpiceDisAdapter(Context context, ArrayList dataList) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        travelList = dataList;
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
        View view = mInflater.inflate(R.layout.item_spicesportdis, parent, false);
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
            holder.myLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(holder.myLayout, position,travelList);
                }
            });
            holder.myLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mOnItemClickListener.onItemLongClick(holder.itemView, position,travelList);
                    return false;
                }
            });
        }
        //绑定数据
        Picasso.with(context)
                .load(((SportDisSpiceBean)(travelList.get(position))).image)
                .placeholder(R.mipmap.zhanwei)
                .error(R.mipmap.zhanwei)
                .config(Bitmap.Config.RGB_565)
                .memoryPolicy(NO_CACHE,NO_STORE)
                .into(holder.myImage);

        holder.myTitle.setText(((SportDisSpiceBean)(travelList.get(position))).title);
        holder.myHot.setText("热度: " + ((SportDisSpiceBean)(travelList.get(position))).hot + "");
        holder.myCon.setText("        " + ((SportDisSpiceBean)(travelList.get(position))).con);
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
        public ImageView myImage;
        public TextView myTitle;
        public TextView myHot;
        public TextView myCon;
        public RelativeLayout myLayout;
        public ViewHolder(View view) {
            super(view);
            myImage = (ImageView) view.findViewById(R.id.myImage);
            myTitle = (TextView) view.findViewById(R.id.myTitle);
            myHot = (TextView) view.findViewById(R.id.myHot);
            myCon = (TextView) view.findViewById(R.id.myCon);
            myLayout = (RelativeLayout) view.findViewById(R.id.myLayout);
        }
    }
}