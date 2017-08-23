package com.weiaibenpao.demo.chislim.adater;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gjiazhe.scrollparallaximageview.ScrollParallaxImageView;
import com.squareup.picasso.Picasso;
import com.view.jameson.library.CardAdapterHelper;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.bean.TravelResult;
import com.weiaibenpao.demo.chislim.util.Default;

import java.util.ArrayList;

import static com.squareup.picasso.MemoryPolicy.NO_CACHE;
import static com.squareup.picasso.MemoryPolicy.NO_STORE;

/**
 * Created by lenovo on 2016/10/11.
 */

public class RecyclerTravelAdapter extends RecyclerView.Adapter<RecyclerTravelAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private ScrollParallaxImageView.ParallaxStyle parallaxStyle;
    private int num;
    private CardAdapterHelper mCardAdapterHelper = new CardAdapterHelper();
    private ArrayList travelList;
    Context context;

    private static OnItemClickListener mOnItemClickListener;
    //设置点击和长按事件

    public interface OnItemClickListener {
        void onItemClick(View view, int position,ArrayList list); //设置点击事件
        void onItemLongClick(View view, int position,ArrayList list); //设置长按事件
    }
    public static void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    /**
     * 构造器
     * @param context
     * @param num
     */
    public RecyclerTravelAdapter(Context context, ArrayList dataList,ScrollParallaxImageView.ParallaxStyle parallaxStyle,int num) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        travelList = dataList;
        this.parallaxStyle = parallaxStyle;
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
        View view = mInflater.inflate(R.layout.item_picture_travel, parent, false);
        mCardAdapterHelper.onCreateViewHolder(parent, view);
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
        holder.item_IM.setParallaxStyles(parallaxStyle);
        mCardAdapterHelper.onBindViewHolder(holder.itemView, position, getItemCount());
        //绑定事件
        if (mOnItemClickListener != null) {
            holder.item_IM.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(holder.item_IM, position,travelList);
                }
            });
            holder.item_IM.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mOnItemClickListener.onItemLongClick(holder.itemView, position,travelList);
                    return false;
                }
            });
        }


        Glide.with(context)
                .load(Default.urlPic+((TravelResult.TravelBean)travelList.get(position)).getT_image())
                .placeholder(R.mipmap.zhanwei)
                .error(R.mipmap.zhanwei)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .override(360, 220)
                .into(holder.item_IM);




        holder.item_TV.setText(((TravelResult.TravelBean)travelList.get(position)).getT_name());
        holder.item_hot.setText("#热度:  " + ((TravelResult.TravelBean)travelList.get(position)).getT_hot());
    }

    public void changeMoreData(ArrayList<String> moreList) {
        //上啦加载更多的数据是加载到末尾的
        travelList = moreList;
        notifyDataSetChanged();
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
        public   ScrollParallaxImageView item_IM;
        public TextView item_TV;
        public TextView item_hot;

        public ViewHolder(View view) {
            super(view);
            item_IM = (ScrollParallaxImageView) view.findViewById(R.id.tralPic1);
            item_TV = (TextView) view.findViewById(R.id.item_TV);
            item_hot = (TextView) view.findViewById(R.id.item_hot);
        }
    }
}