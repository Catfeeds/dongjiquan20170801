package com.weiaibenpao.demo.chislim.adater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.bean.GetInterestSportResult;

import java.util.ArrayList;


/**
 * Created by zhangxing on 2017/1/21.
 */

public class SportDisStyleAdapter2 extends RecyclerView.Adapter<SportDisStyleAdapter2.MyViewHolder> {
    private Context mContext;
    private ArrayList dataList;
    public static OnItemClickListener mListerner;

    public SportDisStyleAdapter2(Context context, ArrayList list){
        mContext = context;
        dataList = list;

    }


    public static void setOnItemClickListener(OnItemClickListener listener){

        mListerner = listener;
    }

    public interface OnItemClickListener{
        void OnItemClick(View v, int position, ArrayList list);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_distence,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListerner.OnItemClick(v,position,dataList);
            }
        });

        holder.currentCity.setText( ((GetInterestSportResult.DataBean) dataList.get(position)).getCityName());
        holder.completeNum.setText(((GetInterestSportResult.DataBean) dataList.get(position)).getFinishPercent());
        holder.currentDistance.setText(((GetInterestSportResult.DataBean) dataList.get(position)).getNowDis());
        holder.allDistance.setText(((GetInterestSportResult.DataBean) dataList.get(position)).getAllDis());
        holder.former_timer.setText( ((GetInterestSportResult.DataBean) dataList.get(position)).getSportTime());

        Glide.with(mContext)
                .load("http://n.sinaimg.cn/transform/20150713/tZgd-fxewnie5457812.jpg")
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .error(R.mipmap.zhanwei)
                .placeholder(R.mipmap.zhanwei)
                .into(holder.myBackground);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView currentCity,completeNum,currentDistance,allDistance,former_timer;
        private ImageView myBackground;

        public MyViewHolder(View itemView) {
            super(itemView);
            currentCity = (TextView) itemView.findViewById(R.id.currentCity);
            completeNum = (TextView) itemView.findViewById(R.id.completeNum);
            currentDistance = (TextView) itemView.findViewById(R.id.currentDistance);
            allDistance = (TextView) itemView.findViewById(R.id.allDistance);
            former_timer = (TextView) itemView.findViewById(R.id.former_timer);
            myBackground = (ImageView) itemView.findViewById(R.id.myBackground);
        }
    }
}
