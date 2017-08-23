package com.weiaibenpao.demo.chislim.adater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.bean.SportHistoryResultBean;
import com.weiaibenpao.demo.chislim.util.DateUtil;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by lenovo on 2016/10/11.
 */

public class RecyclerHisAdapter extends RecyclerView.Adapter<RecyclerHisAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    private List<SportHistoryResultBean.DataBean.SportRecord> arrayList;
    private Context context;
    private DecimalFormat decimalFormat;


    private static OnItemClickListener mOnItemClickListener;
    //设置点击和长按事件
    public interface OnItemClickListener {
        void onItemClick(View view, int position, List<SportHistoryResultBean.DataBean.SportRecord> data);        //设置点击事件
        void onItemLongClick(View view, int position, List<SportHistoryResultBean.DataBean.SportRecord> data);    //设置长按事件
    }
    public static void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    /**
     * 构造器
     * @param context
     * @param
     */
    public RecyclerHisAdapter(Context context, List<SportHistoryResultBean.DataBean.SportRecord> arrayList) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.arrayList = arrayList;

        if (decimalFormat == null) {
            decimalFormat = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        }
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
        View view = mInflater.inflate(R.layout.item_history, parent, false);

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
            holder.clickLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(holder.clickLayout, position,arrayList);
                }
            });
            holder.clickLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    mOnItemClickListener.onItemLongClick(holder.itemView, position,arrayList);
                    return false;
                }
            });
        }

        holder.item_myMonth.setText(DateUtil.getTimeYMD((arrayList.get(position)).getSportDate()));   //日期

        holder.myDis.setText(decimalFormat.format(((arrayList.get(position)).getTotalDistance()))+"公里"  );
        holder.myTime.setText((arrayList.get(position)).getTotalTime()+"");
        holder.myCor.setText((arrayList.get(position)).getCalories()+"大卡");
    }

    public void refreshData(List<SportHistoryResultBean.DataBean.SportRecord> sportRecords){
        arrayList = sportRecords ;
        notifyDataSetChanged();
    }


    /**
     * 要显示的item数目
     * @return
     */
    @Override
    public int getItemCount() {
        return arrayList == null ? 0 :arrayList.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public  class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout clickLayout;
        public TextView item_myMonth;
        public TextView myDis;
        public TextView myTime;
        public TextView myCor;

        public ViewHolder(View view) {
            super(view);
            item_myMonth = (TextView) view.findViewById(R.id.myMonth);
            myDis = (TextView) view.findViewById(R.id.myDis);
            myTime = (TextView) view.findViewById(R.id.myTime);
            myCor = (TextView) view.findViewById(R.id.myCor);
            clickLayout = (LinearLayout) view.findViewById(R.id.click_layout);
        }
    }
}