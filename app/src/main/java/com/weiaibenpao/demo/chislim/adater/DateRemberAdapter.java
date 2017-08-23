package com.weiaibenpao.demo.chislim.adater;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.bean.SportHistoryResultBean;
import com.weiaibenpao.demo.chislim.util.DateUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by lenovo on 2017/2/11.
 */

public class DateRemberAdapter extends RecyclerView.Adapter<DateRemberAdapter.ViewHolder> {
    private LayoutInflater mInflater;
    Context context;
    List<SportHistoryResultBean.DataBean.MonthRecord> dataList;

    ArrayList monthList;
    HashMap<String, String> hashMap;
    DateUtil dateUtil;
    DecimalFormat decimalFormat;

    private ArrayList newArrayList;   //临时的集合

//    double valueMonth; //当天的值

//    String month;

    private  OnItemClickListener mOnItemClickListener;

    //设置点击和长按事件
    public interface OnItemClickListener {
//        void onItemClick(View view, ArrayList monthList, HashMap<String, String> hashMap);        //设置点击事件
        void onItemClick(List<SportHistoryResultBean.DataBean.SportRecord> sportRecords);
    }

    public  void setOnItemClickListener(OnItemClickListener listener) {
        mOnItemClickListener = listener;
    }

    /**
     * 构造器
     *
     * @param context
     */
    public DateRemberAdapter(Context context, List<SportHistoryResultBean.DataBean.MonthRecord> arrayList) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        dataList = arrayList;

        monthList = new ArrayList();
        hashMap = new HashMap<>();
        dateUtil = new DateUtil();
        if (decimalFormat == null) {
            decimalFormat = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        }

        newArrayList = new ArrayList();

        for (int i = 0; i < dataList.size(); i++) {
            try {
                String month = dataList.get(i).getMonth();
                double monthTotal = (dataList.get(i).getTotalDistance());
                hashMap.put(month, decimalFormat.format( monthTotal));
//                List<SportHistoryResultBean.DataBean.SportRecord> sportRecordList = dataList.get(i).getSportHistoryList();
//                int size = sportRecordList.size();
//                for (int j = 0; j < size; j++) {
//                    month = dateUtil.getTime(sportRecordList.get(j).getSportDate());
//                    valueMonth += (sportRecordList.get(j).getTotalDistance());
//                    hashMap.put(month, decimalFormat.format( valueMonth));
////                    Log.e("inside for loop","month is "+ month+"\nvalue is "+valueMonth);
//                }
//                valueMonth = 0;
                monthList.add(month);
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("DateRememberAdapter","Exception occurs :"+e.getMessage());
            }
        }
    }

    /**
     * item显示类型
     * 引入布局
     *
     * @param parent
     * @param viewType
     * @return
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_monthrember, parent, false);
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
        if (position % 2 == 0) {
            holder.viewLayout.setBackgroundColor(Color.parseColor("#CC358F"));
        } else {
            holder.viewLayout.setBackgroundColor(Color.parseColor("#18A0F6"));
        }

        holder.myText.setText(monthList.get(position) + "月份累计跑步" + hashMap.get(monthList.get(position)) + "公里");

        //绑定事件
        if (mOnItemClickListener != null) {
            holder.viewLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickListener.onItemClick(dataList.get(position).getSportHistoryList());
                }
            });
        }
    }

    /**
     * 要显示的item数目
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return monthList.size();
    }

    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView myText;
        public LinearLayout viewLayout;

        public ViewHolder(View view) {
            super(view);
            myText = (TextView) view.findViewById(R.id.myText);
            viewLayout = (LinearLayout) view.findViewById(R.id.viewLayout);
        }
    }
}