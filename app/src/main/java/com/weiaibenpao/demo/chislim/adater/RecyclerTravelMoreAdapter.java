package com.weiaibenpao.demo.chislim.adater;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gjiazhe.scrollparallaximageview.ScrollParallaxImageView;
import com.squareup.picasso.Picasso;
import com.view.jameson.library.CardAdapterHelper;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.bean.TravelResult;
import com.weiaibenpao.demo.chislim.util.Default;

import java.util.ArrayList;

/**
 * Created by lenovo on 2016/10/11.
 */

public class RecyclerTravelMoreAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    private LayoutInflater mInflater;
    private int num;
    private ArrayList travelList;
    Context context;
    private ScrollParallaxImageView.ParallaxStyle parallaxStyle;
    private CardAdapterHelper mCardAdapterHelper = new CardAdapterHelper();
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
    public RecyclerTravelMoreAdapter(Context context, ArrayList dataList,ScrollParallaxImageView.ParallaxStyle parallaxStyle, int num) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.parallaxStyle = parallaxStyle;
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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        //进行判断显示类型，来创建返回不同的View
            View view = LayoutInflater.from(context).inflate(R.layout.item_picture_travel_more, parent, false);
            //view.setBackgroundColor(Color.RED);
          mCardAdapterHelper.onCreateViewHolder(parent, view);
            ItemViewHolder itemViewHolder = new ItemViewHolder(view);
            return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        //绑定事件
        ((ItemViewHolder) holder).item_IM.setParallaxStyles(parallaxStyle);
        mCardAdapterHelper.onBindViewHolder(((ItemViewHolder) holder).itemView, position, getItemCount());

            //绑定数据
            Picasso.with(context)
                    .load(Default.urlPic+((TravelResult.TravelBean)travelList.get(position)).getT_image())
                    .placeholder(R.mipmap.zhanwei)
                    .error(R.mipmap.zhanwei)
                    .config(Bitmap.Config.RGB_565)
                    .into(((ItemViewHolder) holder).item_IM);
            ((ItemViewHolder) holder).item_TV.setText(((TravelResult.TravelBean)travelList.get(position)).getT_name());
            ((ItemViewHolder) holder).item_people.setText("#热度: " + ((TravelResult.TravelBean)travelList.get(position)).getT_hot());
            ((ItemViewHolder) holder).item_IM.setTag(position);

            if (mOnItemClickListener != null) {
                ((ItemViewHolder) holder).item_IM.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mOnItemClickListener.onItemClick(((ItemViewHolder) holder).item_IM, position,travelList);
                    }
                });
                ((ItemViewHolder) holder).item_IM.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        mOnItemClickListener.onItemLongClick(holder.itemView, position,travelList);
                        return false;
                    }
                });
            }
    }

    /**
     * 要显示的item数目
     * @return
     */
    @Override
    public int getItemCount() {

        return travelList.size();
    }

    //用网络获取的新数据替换缓存的数据
    public void changeData(ArrayList<String> moreList) {
        //上啦加载更多的数据是加载到末尾的
        travelList = moreList;
        notifyDataSetChanged();
    }

    public void refreshItem(ArrayList<String> newDatas) {
        //这里下拉刷新的数据是加到头部的
        ArrayList<String> a1 = new ArrayList<>();
        a1.addAll(newDatas);
        a1.addAll(travelList);
        travelList = a1;
        notifyDataSetChanged();
    }

    public void addMoreData(ArrayList<String> moreList) {
        //上啦加载更多的数据是加载到末尾的
        travelList.addAll(moreList);
        notifyDataSetChanged();
    }


    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public ScrollParallaxImageView item_IM;
        public TextView item_TV;
        public  TextView item_people;

        public ItemViewHolder(View view) {
            super(view);
            item_IM = (ScrollParallaxImageView) view.findViewById(R.id.tralPic1);
            item_TV = (TextView) view.findViewById(R.id.item_TV);
            item_people = (TextView) view.findViewById(R.id.item_people);

        }
    }

}