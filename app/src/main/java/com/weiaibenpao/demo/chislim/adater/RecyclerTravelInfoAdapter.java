package com.weiaibenpao.demo.chislim.adater;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.bean.TravelInfoResult;
import com.weiaibenpao.demo.chislim.util.Default;

import java.util.ArrayList;

/**
 * Created by lenovo on 2016/10/11.
 */

public class RecyclerTravelInfoAdapter extends RecyclerView.Adapter<RecyclerTravelInfoAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private int num;
    private ArrayList travelList;
    Context context;
    String[] temp = null;
    String travelImage=null;

    private static OnItemClickListener mOnItemClickListener;
    //设置点击和长按事件

    public interface OnItemClickListener {
        void onItemClick(View view, int position, String[] temp); //设置点击事件
        void onItemLongClick(View view, int position, String[] temp); //设置长按事件
    }
    public static void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    /**
     * 构造器
     * @param context
     * @param num
     */
    public RecyclerTravelInfoAdapter(Context context, ArrayList dataList, int num) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_picture_travel, parent, false);
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
        temp = null;
        travelImage = ((TravelInfoResult.TravelInfoBean)travelList.get(position)).getTi_image();
        temp = travelImage.split(",");
        Log.i("旅游",temp.toString() + "+++position");

        //绑定事件
        if (mOnItemClickListener != null) {
            holder.tralPic1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    travelImage = ((TravelInfoResult.TravelInfoBean)travelList.get(position)).getTi_image();
                    temp = travelImage.split(",");
                    mOnItemClickListener.onItemClick(holder.tralPic1, position,temp);
                }
            });
            holder.tralPic1.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mOnItemClickListener.onItemLongClick(holder.itemView, position,temp);
                    return false;
                }
            });
        }

        //绑定数据
        Picasso.with(context)
                .load(Default.urlPic+temp[0])
                .placeholder(R.mipmap.zhanwei)
                .error(R.mipmap.zhanwei)
                .config(Bitmap.Config.RGB_565)
                .into(holder.tralPic1);
        holder.item_TV.setText(((TravelInfoResult.TravelInfoBean)travelList.get(position)).getTi_name());
    }

    public void addMoreData(ArrayList<String> moreList) {
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
    public  class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView tralPic1;
        public TextView item_TV;

        public ViewHolder(View view) {
            super(view);
            tralPic1 = (ImageView) view.findViewById(R.id.tralPic1);
            item_TV = (TextView) view.findViewById(R.id.item_TV);
        }
    }
}