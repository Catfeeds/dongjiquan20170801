package com.weiaibenpao.demo.chislim.adater;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.bean.MainBeanResult;

import java.util.ArrayList;

import static com.squareup.picasso.MemoryPolicy.NO_CACHE;

/**
 * Created by lenovo on 2016/10/11.
 */

public class ActivityMainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater mInflater;
    private ArrayList activityList;
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
    public ActivityMainAdapter(Context context, ArrayList activityList) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.activityList = activityList;
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
            View view = LayoutInflater.from(context).inflate(R.layout.item_activitylist, parent, false);
            //view.setBackgroundColor(Color.RED);
            ItemViewHolder itemViewHolder = new ItemViewHolder(view);
            return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        //绑定事件
            //绑定数据
            Picasso.with(context)
                    .load(((MainBeanResult.ActivityBean)activityList.get(position)).getAImage())
                    .placeholder(R.mipmap.zhanwei)
                    .error(R.mipmap.zhanwei)
                    .config(Bitmap.Config.RGB_565)
                    .memoryPolicy(NO_CACHE)
                    .resize(360,200)
                    .into(((ItemViewHolder) holder).item_activityimage);
            ((ItemViewHolder) holder).item_Title.setText(((MainBeanResult.ActivityBean)activityList.get(position)).getATitle());
            ((ItemViewHolder) holder).item_Num.setText("参与人数： " + String.valueOf(((MainBeanResult.ActivityBean)activityList.get(position)).getAPeople()));

            ((ItemViewHolder) holder).item_activityimage.setTag(position);

            if (mOnItemClickListener != null) {
                ((ItemViewHolder) holder).item_activityimage.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mOnItemClickListener.onItemClick(((ItemViewHolder) holder).item_activityimage, position,activityList);
                    }
                });
                ((ItemViewHolder) holder).item_activityimage.setOnLongClickListener(new View.OnLongClickListener() {
                    @Override
                    public boolean onLongClick(View view) {
                        mOnItemClickListener.onItemLongClick(holder.itemView, position,activityList);
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

        //由于添加了footer所以返回的值都要加1个
        return activityList.size();
    }

    public void refreshItem(ArrayList<String> newDatas) {
        //这里下拉刷新的数据是加到头部的
        ArrayList<String> a1 = new ArrayList<>();
        a1.addAll(newDatas);
        a1.addAll(activityList);
        activityList = a1;
        notifyDataSetChanged();
    }

    public void addMoreData(ArrayList<String> moreList) {
        //上啦加载更多的数据是加载到末尾的
        activityList.addAll(moreList);
        notifyDataSetChanged();
    }

    //用网络获取的新数据替换缓存的数据
    public void changeData(ArrayList<String> moreList) {
        //上啦加载更多的数据是加载到末尾的
        activityList = moreList;
        notifyDataSetChanged();
    }


    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView item_activityimage;
        public TextView item_Title;
        public TextView item_Num;

        public ItemViewHolder(View view) {
            super(view);
            item_activityimage = (ImageView) view.findViewById(R.id.item_activityimage);
            item_Title = (TextView) view.findViewById(R.id.item_Title);
            item_Num = (TextView) view.findViewById(R.id.item_Num);
        }
    }
}