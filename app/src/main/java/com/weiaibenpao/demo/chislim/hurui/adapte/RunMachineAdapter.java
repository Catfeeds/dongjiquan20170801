package com.weiaibenpao.demo.chislim.hurui.adapte;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.hurui.bean.RunMachineBean;
import com.weiaibenpao.demo.chislim.hurui.utils.Utils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Created by lenovo on 2017/4/12.
 */

public class RunMachineAdapter extends RecyclerView.Adapter<RunMachineAdapter.RunHolder> {
    private Context mContext ;
    private List<RunMachineBean.DataBean.TreadmillBean> lists ;

    public RunMachineAdapter(Context context , List<RunMachineBean.DataBean.TreadmillBean> lists){
        this.mContext  = context ;
        this.lists     = lists ;
    }

    @Override
    public RunHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.run_machine_item , parent , false) ;
        return new RunHolder(view);
    }

    @Override
    public void onBindViewHolder(RunHolder holder, final int position) {
        holder.title_tv.setText(lists.get(position).getTreadmillTitle());
        holder.intro_tv.setText(lists.get(position).getTreadmillIntro());
        Picasso.with(mContext).load(lists.get(position).getTreadmillImgUrl()).into(holder.runm_icon);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.gotoTmailShop(mContext , lists.get(position).getTmallID() , lists.get(position).getTmallUrl());
            }
        });
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

    public class RunHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.runm_icon)
        ImageView runm_icon ;

        @BindView(R.id.treadmillIntro)
        TextView intro_tv ;
        @BindView(R.id.treadmillTitle)
        TextView title_tv;


        public RunHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}
