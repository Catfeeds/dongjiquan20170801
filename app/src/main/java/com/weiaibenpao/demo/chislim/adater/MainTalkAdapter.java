package com.weiaibenpao.demo.chislim.adater;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.gjiazhe.scrollparallaximageview.ScrollParallaxImageView;
import com.squareup.picasso.Picasso;
import com.view.jameson.library.CardAdapterHelper;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.bean.ThemeResule;

import java.util.ArrayList;

import static com.squareup.picasso.MemoryPolicy.NO_CACHE;
import static com.squareup.picasso.MemoryPolicy.NO_STORE;

/**
 * 游记横向图片适配
 * Created by lenovo on 2016/10/11.
 */

public class MainTalkAdapter extends RecyclerView.Adapter<MainTalkAdapter.ViewHolder> {
    private ScrollParallaxImageView.ParallaxStyle parallaxStyle;
    private LayoutInflater mInflater;
    private ArrayList talkList;
    private CardAdapterHelper mCardAdapterHelper = new CardAdapterHelper();
    Context context;

    private static OnItemClickListener mOnItemClickListener;
    //设置点击和长按事件

    public interface OnItemClickListener {
        void onItemClick(View view, int position, ArrayList talkList); //设置点击事件
        void onItemLongClick(View view, int position, ArrayList talkList); //设置长按事件
    }
    public static void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    /**
     * 构造器
     * @param context
     */
    public MainTalkAdapter(Context context, ArrayList talkList,ScrollParallaxImageView.ParallaxStyle parallaxStyle) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.talkList = talkList;
        this.parallaxStyle = parallaxStyle;
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
        View view = mInflater.inflate(R.layout.item_maintalk, parent, false);
        //view.setBackgroundColor(Color.RED);
        mCardAdapterHelper.onCreateViewHolder(parent, view);
        return  new ViewHolder(view);
    }

    /**
     * 数据的绑定显示
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.iv.setParallaxStyles(parallaxStyle);
        mCardAdapterHelper.onBindViewHolder(holder.itemView, position, getItemCount());
        if (mOnItemClickListener != null) {
            holder.iv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(holder.iv, position,talkList);
                }
            });
            holder.iv.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mOnItemClickListener.onItemLongClick(holder.itemView, position,talkList);
                    return false;
                }
            });
        }

        //绑定数据

       /* Picasso.with(context)
                .load(((ThemeResule.DataBean.ThemelistBean)(talkList.get(position))).getThemeImgUrl())
                .placeholder(R.mipmap.zhanwei)
                .config(Bitmap.Config.RGB_565)
                .error(R.mipmap.zhanwei)
                .memoryPolicy(NO_CACHE)
                .resize(280,180)
                .into(holder.iv);
*/

        Glide.with(context)
                .load(((ThemeResule.DataBean.ThemelistBean)(talkList.get(position))).getThemeImgUrl())
                .placeholder(R.mipmap.zhanwei)
                .error(R.mipmap.zhanwei)
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .override(280,180)
                .into(holder.iv);

        holder.talkTitle.setText(((ThemeResule.DataBean.ThemelistBean)talkList.get(position)).getThemeTitle());
    }

    /**
     * 要显示的item数目
     * @return
     */
    @Override
    public int getItemCount() {
        return talkList.size();
    }

    public void changeData(ArrayList talkList) {
        //上啦加载更多的数据是加载到末尾的
        this.talkList = talkList;
        notifyDataSetChanged();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView talkTitle;
        ScrollParallaxImageView iv;

        public ViewHolder(View view) {
            super(view);
            talkTitle = (TextView) view.findViewById(R.id.talkTitle);
            iv = (ScrollParallaxImageView) itemView.findViewById(R.id.myImage);
        }
    }
}