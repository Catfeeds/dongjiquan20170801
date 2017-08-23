package com.weiaibenpao.demo.chislim.adater;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;
import com.weiaibenpao.demo.chislim.Interface.GetInterfaceBooleanListener;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.bean.HumorResult;
import com.weiaibenpao.demo.chislim.bean.UserBean;
import com.weiaibenpao.demo.chislim.util.ACache;
import com.weiaibenpao.demo.chislim.util.GetIntentData;
import com.wx.goodview.GoodView;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import static com.squareup.picasso.MemoryPolicy.NO_CACHE;
import static com.squareup.picasso.MemoryPolicy.NO_STORE;

/**
 * Created by lenovo on 2016/10/11.
 */

public class HumorAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

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
    URL url;
    DateFormat formatter;
    long time;
    Calendar calendar;
    ACache aCache;
    GoodView mGoodView;

    private static OnItemClickListener mOnItemClickListener;
    //设置点击和长按事件

    public interface OnItemClickListener {
        void onItemClick(View view, int position, ArrayList list); //设置点击事件
        void onShareItemClick(View view, int position, ArrayList list); //设置点击事件
        void onImageItemClick(View view, int position, ArrayList list); //设置点击事件
        void onItemLongClick(View view, int position, ArrayList list); //设置长按事件
    }
    public static void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    /**
     * 构造器
     * @param context
     */
    public HumorAdapter(Context context, ArrayList activityList) {

        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.activityList = activityList;
        aCache = ACache.get(context);
        mGoodView = new GoodView(context);
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
            View view = mInflater.inflate(R.layout.item_humor, parent, false);
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
            time = ((HumorResult.DataBean.HumorlistBean) activityList.get(position)).getHumorDate();
            calendar = Calendar.getInstance();
            calendar.setTimeInMillis(time);

            //绑定事件
            //绑定数据
            //用户头像
           /* Glide.with(context)
                    .load(((HumorResult.DataBean.HumorListBean) activityList.get(position)).getUserIcon())
                    .placeholder(R.mipmap.zhanwei)
                    .error(R.mipmap.zhanwei)
                    .override(30,30)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(((ItemViewHolder) holder).userIcon);*/
            Picasso.with(context)
                    .load(((HumorResult.DataBean.HumorlistBean) activityList.get(position)).getUserIcon())
                    .placeholder(R.mipmap.zhanwei)
                    .error(R.mipmap.zhanwei)
                    .config(Bitmap.Config.RGB_565)
                    .resize(30,30)
                    .memoryPolicy(NO_CACHE,NO_STORE)
                    .into(((ItemViewHolder) holder).userIcon);
            //用户名称
            ((ItemViewHolder) holder).userName.setText(((HumorResult.DataBean.HumorlistBean) activityList.get(position)).getUserNickName());
            //心情发表日期
            ((ItemViewHolder) holder).humorDate.setText(formatter.format(calendar.getTime()) + "");

            //心情内容
            ((ItemViewHolder) holder).humor_text.setText("        " + ((HumorResult.DataBean.HumorlistBean) activityList.get(position)).getHumorContent());

            String imageUrl = ((HumorResult.DataBean.HumorlistBean) activityList.get(position)).getHumorImgUrl();

            if(!imageUrl.equals(((ItemViewHolder) holder).humorImg.getTag())) {


                Picasso.with(context)
                        .load(((HumorResult.DataBean.HumorlistBean) activityList.get(position)).getHumorImgUrl())
                        .placeholder(R.mipmap.zhanwei)
                        .error(R.mipmap.zhanwei)
                        .config(Bitmap.Config.RGB_565)
                        .memoryPolicy(NO_CACHE, NO_STORE)
                        .into(((ItemViewHolder) holder).humorImg);
                ((ItemViewHolder) holder).humorImg.setTag(((HumorResult.DataBean.HumorlistBean) activityList.get(position)).getHumorImgUrl());
            }

            ((ItemViewHolder) holder).humorImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onImageItemClick(((ItemViewHolder) holder).humorImg, position, activityList);
                }
            });

            if (((HumorResult.DataBean.HumorlistBean) activityList.get(position)).getIsLike().equals("YES")) {
                // ((ItemViewHolder) holder).humorSupport.setImageResource(R.mipmap.startb);
                Glide.with(context)
                        .load(R.mipmap.good_checked)
                        .into(((ItemViewHolder) holder).humorSupport);


            } else {
                Glide.with(context)
                        .load(R.mipmap.good)
                        .into(((ItemViewHolder) holder).humorSupport);
            }
            //点赞
            ((ItemViewHolder) holder).humorSupport.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    getIntentData.getPraise(context, UserBean.getUserBean().userId, ((HumorResult.DataBean.HumorlistBean) activityList.get(position)).getId());
                    getIntentData.setGetInterfaceBooleanListener(new GetInterfaceBooleanListener() {
                        @Override
                        public void getBoolean(boolean flag) {
                            if (flag) {
                                if (((HumorResult.DataBean.HumorlistBean) activityList.get(position)).getIsLike().equals("YES")) {
                                    //((ItemViewHolder) holder).humorSupport.setImageResource(R.mipmap.startd);
                                    Glide.with(context)
                                            .load(R.mipmap.good)
                                            .into(((ItemViewHolder) holder).humorSupport);

                                    ((HumorResult.DataBean.HumorlistBean) activityList.get(position)).setIsLike("NO");
                                    //点赞人数减一
                                    ((HumorResult.DataBean.HumorlistBean) activityList.get(position)).setLikeNum(((HumorResult.DataBean.HumorlistBean) activityList.get(position)).getLikeNum() -1);
                                    ((ItemViewHolder) holder).likeNum.setText(((HumorResult.DataBean.HumorlistBean) activityList.get(position)).getLikeNum() + "");
                                } else {
                                    //((ItemViewHolder) holder).humorSupport.setImageResource(R.mipmap.startb);
                                    Glide.with(context)
                                            .load(R.mipmap.good_checked)
                                            .into(((ItemViewHolder) holder).humorSupport);
                                    mGoodView.setText("+1");
                                    mGoodView.setTextInfo("+1", Color.parseColor("#f66467"), 16);
                                    mGoodView.show(((ItemViewHolder) holder).humorSupport);
                                    //  mGoodView.dismiss();
                                    ((HumorResult.DataBean.HumorlistBean) activityList.get(position)).setIsLike("YES");
                                    //点赞人数加一
                                    ((HumorResult.DataBean.HumorlistBean) activityList.get(position)).setLikeNum(((HumorResult.DataBean.HumorlistBean) activityList.get(position)).getLikeNum() + 1);
                                    ((ItemViewHolder) holder).likeNum.setText(((HumorResult.DataBean.HumorlistBean) activityList.get(position)).getLikeNum() + "");
                                }
                            }
                        }
                    });
                }
            });

            //点赞人数
            ((ItemViewHolder) holder).likeNum.setText(((HumorResult.DataBean.HumorlistBean) activityList.get(position)).getLikeNum() + "");

            //评论
            ((ItemViewHolder) holder).comentImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ((ItemViewHolder) holder).comentImg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            mOnItemClickListener.onItemClick(((ItemViewHolder) holder).comentImg, ((HumorResult.DataBean.HumorlistBean) activityList.get(position)).getId(), activityList);
                        }
                    });
                }
            });
            //评论人数
            ((ItemViewHolder) holder).comentNum.setText(((HumorResult.DataBean.HumorlistBean) activityList.get(position)).getComentNum() + "");


            //分享回调，在适配器外边执行
            ((ItemViewHolder) holder).shareHumor.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onShareItemClick(((ItemViewHolder) holder).shareHumor, position, activityList);
                }
            });

            //话题
            ((ItemViewHolder) holder).textTitle.setText("#" + ((HumorResult.DataBean.HumorlistBean) activityList.get(position)).getThemeTitleStr() + "#");


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
        public ImageView userIcon;
        public TextView userName;
        public TextView humorDate;
        public TextView humor_text;
        public ImageView humorImg;
        public ImageView humorSupport;
        public TextView likeNum;
        public ImageView comentImg;
        public TextView comentNum;
        public ImageView shareHumor;
        public TextView textTitle;

        public ItemViewHolder(View view) {
            super(view);
            userIcon = (ImageView) view.findViewById(R.id.userIcon);
            userName = (TextView) view.findViewById(R.id.userName);
            humorDate = (TextView) view.findViewById(R.id.humorDate);
            humor_text = (TextView) view.findViewById(R.id.humor_text);
            humorImg = (ImageView) view.findViewById(R.id.humorImg);
            humorSupport = (ImageView) view.findViewById(R.id.humorSupport);
            likeNum = (TextView) view.findViewById(R.id.likeNum);
            comentImg = (ImageView) view.findViewById(R.id.comentImg);
            comentNum = (TextView) view.findViewById(R.id.comentNum);
            shareHumor = (ImageView) view.findViewById(R.id.shareHumor);
            textTitle = (TextView) view.findViewById(R.id.textTitle);
        }
    }

    @Override
    public int getItemViewType(int position) {
        /*if (position + 1 ==getItemCount()) {
            //如果当前位置再加1为总长度。就返回footer
            //假如list.size == 6 。则最大position为5  由于都加了1个 最大position为6 ， getItemCount()为7-----当6+1==7时，就是最末尾了
            return TYPE_FOOTER;
        } else {
            //否则返回正常的item
            return TYPE_ITEM;
        }*/
        return TYPE_ITEM;
    }
}