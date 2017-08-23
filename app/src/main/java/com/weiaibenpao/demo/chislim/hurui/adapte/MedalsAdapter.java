package com.weiaibenpao.demo.chislim.hurui.adapte;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.weiaibenpao.demo.chislim.R;
import com.weiaibenpao.demo.chislim.bean.MyMedal;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/16.
 */

public class MedalsAdapter extends RecyclerView.Adapter<MedalsAdapter.Holder > {
    private final LayoutInflater inflater;
    private List<MyMedal.DataBean.BadgeListBean> list;
    Context context;
    private int totalMedal;
    int icon[]={R.mipmap.gray_5,R.mipmap.gray_10,R.mipmap.gray_21,R.mipmap.gray_42,R.mipmap.gray_50,R.mipmap.gray_100};
    public MedalsAdapter(Context context) {
        list = new ArrayList<>();
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void refreshData(List<MyMedal.DataBean.BadgeListBean> list,int totalMedal) {
        this.list.addAll(list);
        this.totalMedal=totalMedal;
        Log.e("wlx", "onBindViewHolder: "+totalMedal );
        notifyDataSetChanged();
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view= inflater.inflate(R.layout.item_medal,null,false);

        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(Holder holder, int i) {
        //展示数据
        MyMedal.DataBean.BadgeListBean bean = list.get(i);
        holder.medalTv.setText(bean.getBadgeName());
        if (totalMedal!=0){
            if (bean.getBadgeNumber() == 0) {//勋章==0 显示灰色勋章
                holder.count_btn.setVisibility(View.GONE);
                Glide.with(context).load(bean.getBadgewhiteIconUrl()).error(icon[bean.getId()-1]).placeholder(icon[bean.getId()-1]).into(holder.medalImg);
            } else if (bean.getBadgeNumber() > 1) {//勋章>1
                holder.count_btn.setVisibility(View.VISIBLE);//显示勋章的数量
                holder.count_btn.setText(bean.getBadgeNumber() + "");
                Glide.with(context).load(bean.getBadgeIconUrl()).error(icon[bean.getId()-1]).placeholder(icon[bean.getId()-1]).into(holder.medalImg);
            } else
                Glide.with(context).load(bean.getBadgeIconUrl()).error(icon[bean.getId()-1]).placeholder(icon[bean.getId()-1]).into(holder.medalImg);
        }else  {

            Glide.with(context).load(icon[i]).error(icon[i]).placeholder(icon[i]).into(holder.medalImg);

        }
//
    }

    @Override
    public int getItemCount() {
        return list!=null?list.size():0;
    }

//

   class Holder extends RecyclerView.ViewHolder{

       ImageView medalImg;
      TextView medalTv;
       Button count_btn;
       public Holder(View itemView) {
           super(itemView);
           medalImg=((ImageView) itemView.findViewById(R.id.medal_img));
           medalTv= ((TextView) itemView.findViewById(R.id.medal_tv));
           count_btn= ((Button) itemView.findViewById(R.id.count_btn));
       }
   }
}
