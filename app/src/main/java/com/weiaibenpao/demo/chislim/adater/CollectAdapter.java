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
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.bean.GetCollectionResult;
import com.weiaibenpao.demo.chislim.util.GetIntentData;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by lenovo on 2016/10/11.
 */

public class CollectAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_ITEM = 0;                  //普通Item View
    private static final int TYPE_FOOTER = 1;                //顶部FootView

    //上拉加载更多状态-默认为
    private int load_more_status = 0;

    //上拉加载更多
    public static final int PULLUP_LOAD_MORE = 0;
    //正在加载中
    public static final int LOADING_MORE = 1;

    GetIntentData getIntentData = new GetIntentData();
    private LayoutInflater mInflater;
    private ArrayList activityList;
    Context context;
    private ScrollParallaxImageView.ParallaxStyle parallaxStyle;
    DateFormat formatter;
    long time;
    Calendar calendar;

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
    public CollectAdapter(Context context, ArrayList activityList,ScrollParallaxImageView.ParallaxStyle parallaxStyle) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.activityList = activityList;
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
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //进行判断显示类型，来创建返回不同的View
        if (viewType == TYPE_ITEM) {
            //进行判断显示类型，来创建返回不同的View
            View view = mInflater.inflate(R.layout.item_collect, parent, false);
            //view.setBackgroundColor(Color.RED);
            ItemViewHolder itemViewHolder = new ItemViewHolder(view);
            return itemViewHolder;
        } else if (viewType == TYPE_FOOTER) {
            View foot_view = LayoutInflater.from(context).inflate(R.layout.item_foot_view, parent, false);
            //这边可以做一些属性设置，甚至事件监听绑定
            //view.setBackgroundColor(Color.RED);
            FootViewHolder footViewHolder = new FootViewHolder(foot_view);
            return footViewHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        //根据holder的不同设置不同的数据
        if (holder instanceof ItemViewHolder) {
            formatter = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
            time = ((GetCollectionResult.DataBean.CollectionlistBean)(activityList.get(position))).getCollectionTime();
            calendar = Calendar.getInstance();
            calendar.setTimeInMillis(time);

            //绑定事件
            //绑定数据
            Picasso.with(context)
                    .load(((GetCollectionResult.DataBean.CollectionlistBean)(activityList.get(position))).getImgUrl())
                    .placeholder(R.mipmap.zhanwei)
                    .error(R.mipmap.zhanwei)
                    .config(Bitmap.Config.RGB_565)
                    .into(((ItemViewHolder) holder).collectImg);
            ((ItemViewHolder) holder).collectImg.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mOnItemClickListener.onItemClick(((ItemViewHolder) holder).collectImg, position, activityList);
                        }
                    }
            );

            ((ItemViewHolder) holder).collectTitle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(((ItemViewHolder) holder).collectTitle, position, activityList);
                }
            });
            //收藏标题
            ((ItemViewHolder) holder).collectTitle.setText(((GetCollectionResult.DataBean.CollectionlistBean)(activityList.get(position))).getTitle());
            //收藏时间
            ((ItemViewHolder) holder).collectTime.setText(formatter.format(calendar.getTime()) + "");

            //收藏类别
            ((ItemViewHolder) holder).collectTab.setText("        " + ((GetCollectionResult.DataBean.CollectionlistBean)(activityList.get(position))).getCollectionType());
            ((ItemViewHolder) holder).collectImg.setParallaxStyles(parallaxStyle);

        }else if (holder instanceof FootViewHolder) {
            FootViewHolder footViewHolder = (FootViewHolder) holder;
            switch (load_more_status) {
                //这个可以使用2个也可使用1个
                case PULLUP_LOAD_MORE:
                    footViewHolder.foot_view_item_tv.setText("上拉加载更多...");
                    break;
                case LOADING_MORE:
                    footViewHolder.foot_view_item_tv.setText("正在加载更多数据...");
                    load_more_status = PULLUP_LOAD_MORE;
                    break;
            }
        }
    }

    /**
     * 要显示的item数目
     * @return
     */
    @Override
    public int getItemCount() {

        //由于添加了footer所以返回的值都要加1个
        if (activityList == null || activityList.size() < 1) {
            return 1;
        }
        return activityList.size()+1;
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

    /**
     * 底部FootView布局
     */
    public class FootViewHolder extends RecyclerView.ViewHolder {
        private TextView foot_view_item_tv;

        public FootViewHolder(View view) {
            super(view);
            foot_view_item_tv = (TextView) view.findViewById(R.id.foot_view_item_tv);
        }
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public  ScrollParallaxImageView collectImg;
        public TextView collectTitle;
        public TextView collectTime;
        public TextView collectTab;

        public ItemViewHolder(View view) {
            super(view);
            collectImg = (ScrollParallaxImageView) view.findViewById(R.id.collectImg);
            collectTitle = (TextView) view.findViewById(R.id.collectTitle);
            collectTime = (TextView) view.findViewById(R.id.collectTime);
            collectTab = (TextView) view.findViewById(R.id.collectTab);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position + 1 == getItemCount()) {
            //如果当前位置再加1为总长度。就返回footer
            //假如list.size == 6 。则最大position为5  由于都加了1个 最大position为6 ， getItemCount()为7-----当6+1==7时，就是最末尾了
            return TYPE_FOOTER;
        } else {
            //否则返回正常的item
            return TYPE_ITEM;
        }
    }
}