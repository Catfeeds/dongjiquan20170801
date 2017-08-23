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
import com.weiaibenpao.demo.chislim.bean.SportDistrictBean;

import java.util.ArrayList;

/**
 * Created by zhangxing on 2017/1/21.
 */

public class SportDisStyleAdapter extends RecyclerView.Adapter<SportDisStyleAdapter.MyViewHolder> {
    private Context mContext;
    private ArrayList dataList;
    public static OnItemClickListener mListerner;

    public SportDisStyleAdapter(Context context, ArrayList list){
        mContext = context;
        dataList = list;
    }

    public static void setOnItemClickListener(OnItemClickListener listener){

        mListerner = listener;
    }

    public interface OnItemClickListener{
        void OnItemClick(View v,int position,ArrayList list);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_district,parent,false);
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

        holder.provinceTv.setText( ((SportDistrictBean) dataList.get(position)).getName());
        holder.englishTv.setText(((SportDistrictBean) dataList.get(position)).getAllDis()+"");

        Glide.with(mContext)
                .load(((SportDistrictBean) dataList.get(position)).getImage())
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .error(R.mipmap.zhanwei)
                .placeholder(R.mipmap.zhanwei)
                .into(holder.provinceIv);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView provinceTv,englishTv;
        private ImageView provinceIv;

        public MyViewHolder(View itemView) {
            super(itemView);
            provinceTv = (TextView) itemView.findViewById(R.id.provincesTv);
            englishTv = (TextView) itemView.findViewById(R.id.englishTv);
            provinceIv = (ImageView) itemView.findViewById(R.id.provinceIv);
        }
    }
}
