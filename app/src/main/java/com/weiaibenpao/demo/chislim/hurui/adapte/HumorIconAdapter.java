package com.weiaibenpao.demo.chislim.hurui.adapte;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.bean.Moment;
import com.weiaibenpao.demo.chislim.map.customview.CircleImageView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by lenovo on 2017/4/24.
 */

public class HumorIconAdapter extends RecyclerView.Adapter<HumorIconAdapter.HumorIconHodler>{

    private Context mContext ;
    private List<Moment.PraiselistBean> lists ;

    public HumorIconAdapter(Context mContext  , List<Moment.PraiselistBean> lists){
        this.mContext = mContext ;
        this.lists = lists ;
    }

    @Override
    public HumorIconHodler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.humor_icon_item , parent , false) ;
        return new HumorIconHodler(view);
    }

    @Override
    public void onBindViewHolder(HumorIconHodler holder, int position) {
        try{
            Picasso.with(mContext).load(lists.get(position).getUserIcon()).into(holder.par_icon_iv);
        }catch (Exception e){}

    }

    @Override
    public int getItemCount() {
        return lists==null?0:lists.size();
    }

    public class HumorIconHodler extends RecyclerView.ViewHolder{
        @BindView(R.id.par_icon_iv)
        CircleImageView par_icon_iv ;
        public HumorIconHodler(View itemView) {
            super(itemView);
            ButterKnife.bind(this , itemView);
        }
    }
}
