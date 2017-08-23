package com.weiaibenpao.demo.chislim.adater;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.weiaibenpao.demo.chislim.R;

import java.util.ArrayList;

/**
 * Created by zhangxing on 2017/1/8.
 */

public class EquitySpeedAdapter extends RecyclerView.Adapter<EquitySpeedAdapter.MyViewHolder> {
    private ArrayList<String> mKiloList,mSpeedList;
    private Context mContext;
    private LayoutInflater inflater;

   public EquitySpeedAdapter(Context context, ArrayList kiloList, ArrayList speedList){
       mContext = context;
       mKiloList = kiloList;
       mSpeedList = speedList;
       inflater = LayoutInflater.from(mContext);
   }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View  view = inflater.inflate(R.layout.equity_speed,parent,false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.kiloTv.setText(mKiloList.get(position));
        holder.spdTv.setText(mSpeedList.get(position).replaceAll("\\.","'")+"''");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return mKiloList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder{
  private TextView kiloTv,spdTv;
        public MyViewHolder(View itemView) {
            super(itemView);
            kiloTv = (TextView) itemView.findViewById(R.id.kiloTv);
            spdTv = (TextView) itemView.findViewById(R.id.spdTv);

        }
    }
}
