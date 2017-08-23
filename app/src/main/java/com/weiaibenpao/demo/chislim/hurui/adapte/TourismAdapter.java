package com.weiaibenpao.demo.chislim.hurui.adapte;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.hurui.activity.HWebViewActivity;
import com.weiaibenpao.demo.chislim.hurui.bean.TravelBean;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lenovo on 2017/4/13.
 */

public class TourismAdapter extends RecyclerView.Adapter<TourismAdapter.ViewHolder> {

    private Context mContext ;
    private List<TravelBean.DataBean.TravelistBean> lists ;

    public TourismAdapter(Context mContext , List<TravelBean.DataBean.TravelistBean> lists){
        this.mContext = mContext ;
        this.lists = lists ;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.tourism_item , parent ,false) ;
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        Picasso.with(mContext).load(lists.get(position).getTravelImgUrl()).into(holder.img_iv);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext , HWebViewActivity.class);
                intent.putExtra("urlpath" , lists.get(position).getTravelContentUrl());
                intent.putExtra("title" , lists.get(position).getTravelTitle() );
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.img_iv)
        ImageView img_iv ;

        public ViewHolder(View view){
            super(view);
            ButterKnife.bind(this , view);
        }
    }
}
