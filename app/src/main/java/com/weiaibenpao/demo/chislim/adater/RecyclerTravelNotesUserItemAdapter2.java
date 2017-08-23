package com.weiaibenpao.demo.chislim.adater;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.bean.TravelNotesUserItemResult;
import com.weiaibenpao.demo.chislim.ui.TravelPictureActivity;
import com.weiaibenpao.demo.chislim.util.GetIntentData;

import java.util.ArrayList;

/**
 * 用户的一个游记主题界面适配器
 * Created by lenovo on 2016/10/11.
 */

public class RecyclerTravelNotesUserItemAdapter2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_ITEM = 0;  //普通Item View
    private static final int TYPE_FOOTER = 1;  //顶部FootView

    //上拉加载更多状态-默认为0
    private int load_more_status = 0;

    //上拉加载更多
    public static final int PULLUP_LOAD_MORE = 0;
    //正在加载中
    public static final int LOADING_MORE = 1;


    private LayoutInflater mInflater;
    private ArrayList travelList;
    boolean flag = false;
    Context context;
    GetIntentData getIntent = new GetIntentData();
    /**
     * 构造器
     * @param context
     */
    public RecyclerTravelNotesUserItemAdapter2(Context context, ArrayList travelNotesList) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);

        travelList = travelNotesList;
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
            View view = mInflater.inflate(R.layout.item_traveluser, parent, false);
            //view.setBackgroundColor(Color.RED);
            ItemViewHolder viewHolder = new ItemViewHolder(view);
            return viewHolder;
        } else if (viewType == TYPE_FOOTER) {
            View foot_view = LayoutInflater.from(context).inflate(R.layout.item_foot_view, parent, false);
            //这边可以做一些属性设置，甚至事件监听绑定
            //view.setBackgroundColor(Color.RED);
            FootViewHolder footViewHolder = new FootViewHolder(foot_view);
            return footViewHolder;
        }
        return null;
    }

    /**
     * 数据的绑定显示
     *
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        //根据holder的不同设置不同的数据
        if (holder instanceof ItemViewHolder) {
            ((ItemViewHolder) holder).textTitle.setText(((TravelNotesUserItemResult.TravelNotesItemBean)travelList.get(position)).getTn_item_name()+"标题");
            ((ItemViewHolder) holder).textTime.setText(((TravelNotesUserItemResult.TravelNotesItemBean)travelList.get(position)).getTn_item_time()+"时间");

            ((ItemViewHolder) holder).noteWord.setText(((TravelNotesUserItemResult.TravelNotesItemBean)travelList.get(position)).getTn_item_text()+"描述");

            String image = ((TravelNotesUserItemResult.TravelNotesItemBean) travelList.get(position)).getTn_item_image();
            String[] temp = image.split(",");


            ((ItemViewHolder) holder).travelNotesImage.setLayoutManager(new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL));
            RecyclerTravelNotesUserItemPicAdapter mAdapter = new RecyclerTravelNotesUserItemPicAdapter(context,temp);
            ((ItemViewHolder) holder).travelNotesImage.setAdapter(mAdapter);

            RecyclerTravelNotesUserItemPicAdapter.setOnItemClickListener(new RecyclerTravelNotesUserItemPicAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position, String[] travelList) {

                    String[] temp1 = new String[1];
                    temp1[0] = travelList[position];

                    Intent intent = new Intent(context, TravelPictureActivity.class);
                    intent.putExtra("picture", temp1);
                    context.startActivity(intent);

                }

                @Override
                public void onItemLongClick(View view, int position, String[] travelList) {

                }
            });
        } else if (holder instanceof FootViewHolder) {
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

    public void changeData(ArrayList<String> moreList) {
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
        //由于添加了footer所以返回的值都要加1个
        if (travelList == null || travelList.size() < 1) {
            return 1;
        }
        return travelList.size()+1;
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
        public TextView textTitle;
        public TextView textTime;
        public RecyclerView travelNotesImage;
        public TextView noteWord;

        public ItemViewHolder(View view) {
            super(view);
            textTitle = (TextView) view.findViewById(R.id.textTitle);
            textTime = (TextView) view.findViewById(R.id.textTime);
            noteWord = (TextView) view.findViewById(R.id.noteWord);
            travelNotesImage = (RecyclerView) view.findViewById(R.id.travelNotesImage);
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