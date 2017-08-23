package com.weiaibenpao.demo.chislim.adater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weiaibenpao.demo.chislim.Interface.GetInterfaceVideoListener;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.bean.NewTeachPlanResut;
import com.weiaibenpao.demo.chislim.util.GetIntentData;

import java.util.ArrayList;

/**
 * Created by lenovo on 2016/10/11.
 */

public class TeachPlanAdapter extends RecyclerView.Adapter<TeachPlanAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private int num;
    private ArrayList travelList;
    Context context;

    GetIntentData intentData = new GetIntentData();;

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
    public TeachPlanAdapter(Context context, ArrayList dataList, int num) {
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
        View view = mInflater.inflate(R.layout.item_teachplan, parent, false);
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
            holder.item_TV.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onItemClick(holder.item_TV, position,travelList);
                }
            });
            holder.item_TV.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mOnItemClickListener.onItemLongClick(holder.itemView, position,travelList);
                    return false;
                }
            });
        }
        holder.item_TV.setText(((NewTeachPlanResut.NewTeachMenuBean)travelList.get(position)).getTeach_menu_name());

        intentData.getAllTeachGifIGifImage(context,"getAllTeachGif",((NewTeachPlanResut.NewTeachMenuBean)travelList.get(position)).getTeach_menu_id());
        holder.rePlan.setLayoutManager(new StaggeredGridLayoutManager(1,StaggeredGridLayoutManager.HORIZONTAL));
        intentData.setGetIntentDataListener(new GetInterfaceVideoListener() {
            @Override
            public void getDateList(ArrayList dateList) {
                TeachPlanGifAdapter mAdapter1 = new TeachPlanGifAdapter(context,dateList , 6);
                holder.rePlan.setAdapter(mAdapter1);
            }
        });

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
        public TextView item_TV;
        public RecyclerView rePlan;

        public ViewHolder(View view) {
            super(view);
            item_TV = (TextView) view.findViewById(R.id.myText);
            rePlan = (RecyclerView) view.findViewById(R.id.rePlan);
        }
    }
}